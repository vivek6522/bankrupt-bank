package cc.vivp.bankrupt.event;

import static cc.vivp.bankrupt.util.Generic.throwEntityNotFoundExceptionIfNotPresentElseReturnValue;

import cc.vivp.bankrupt.exception.DomainException;
import cc.vivp.bankrupt.exception.EntityNotFoundException;
import cc.vivp.bankrupt.exception.TransferException;
import cc.vivp.bankrupt.model.api.TransferCommand;
import cc.vivp.bankrupt.model.api.TransferReceipt;
import cc.vivp.bankrupt.model.db.AccountEntity;
import cc.vivp.bankrupt.model.db.TransferEntity;
import cc.vivp.bankrupt.repository.AccountsRepository;
import cc.vivp.bankrupt.repository.TransfersRepository;
import cc.vivp.bankrupt.util.MessageKeys;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransferEvent extends DomainEvent<TransferReceipt> {

    private final TransferCommand transferCommand;
    private final AccountsRepository accountsRepository;
    private final TransfersRepository transfersRepository;

    public TransferEvent(final LocalDateTime occurred, final TransferCommand transferCommand,
        final AccountsRepository accountsRepository, final TransfersRepository transfersRepository) {
        super(occurred);
        this.transferCommand = transferCommand;
        this.accountsRepository = accountsRepository;
        this.transfersRepository = transfersRepository;
    }

    @Override
    @Transactional
    public TransferReceipt process() throws DomainException {

        AccountEntity source = null;
        AccountEntity target = null;
        try {

            source = throwEntityNotFoundExceptionIfNotPresentElseReturnValue(
                accountsRepository.findByAccountNumber(transferCommand.getSource()));

            target = throwEntityNotFoundExceptionIfNotPresentElseReturnValue(
                accountsRepository.findByAccountNumber(transferCommand.getTarget()));

        } catch (EntityNotFoundException e) {
            log.error("Account not found.", e);
            throw new TransferException(MessageKeys.ACCOUNT_NOT_FOUND);
        }

        source.setBalance(source.getBalance() - transferCommand.getAmount());
        target.setBalance(target.getBalance() + transferCommand.getAmount());

        String paymentReference = UUID.randomUUID().toString();

        accountsRepository.save(source);
        TransferEntity transferEntity = new TransferEntity(paymentReference, transferCommand.getSource(),
            Math.negateExact(transferCommand.getAmount()), transferCommand.getTarget(),
            transferCommand.getDescription(), recorded);
        transfersRepository.save(transferEntity);
        log.info("{};{};{};{};{};{};{}", occurred, recorded, paymentReference, transferCommand.getSource(),
            Math.negateExact(transferCommand.getAmount()), transferCommand.getTarget(),
            transferCommand.getDescription());

        accountsRepository.save(target);
        transferEntity = new TransferEntity(paymentReference, transferCommand.getTarget(),
            transferCommand.getAmount(), transferCommand.getSource(), transferCommand.getDescription(), recorded);
        transfersRepository.save(transferEntity);
        log.info("{};{};{};{};{};{};{}", occurred, recorded, paymentReference, transferCommand.getTarget(),
            transferCommand.getAmount(),
            transferCommand.getSource(), transferCommand.getDescription());

        return new TransferReceipt(paymentReference, transferCommand.getSource(), transferCommand.getAmount(),
            transferCommand.getTarget(), transferCommand.getDescription(), recorded);
    }
}
