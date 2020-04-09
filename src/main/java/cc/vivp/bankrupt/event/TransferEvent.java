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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

@Slf4j
public class TransferEvent extends DomainEvent<TransferReceipt> {

    private final TransferCommand transferCommand;
    private final AccountsRepository accountsRepository;
    private final TransfersRepository transfersRepository;
    private final ModelMapper modelMapper;

    public TransferEvent(final LocalDateTime occurred, final TransferCommand transferCommand,
        final AccountsRepository accountsRepository, final TransfersRepository transfersRepository,
        final ModelMapper modelMapper) {
        super(occurred);
        this.transferCommand = transferCommand;
        this.accountsRepository = accountsRepository;
        this.transfersRepository = transfersRepository;
        this.modelMapper = modelMapper;
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

        long transferAmount = convertToCentsIfApplicable(transferCommand.getAmount(),
            transferCommand.isAmountInCents());
        source.setBalance(source.getBalance() - transferAmount);
        target.setBalance(target.getBalance() + transferAmount);

        String paymentReference = UUID.randomUUID().toString();

        accountsRepository.save(source);
        TransferEntity transferEntity = new TransferEntity(paymentReference, source,
            Math.negateExact(transferAmount), target,
            transferCommand.getDescription(), recorded);
        transfersRepository.save(transferEntity);
        log.info("{};{};{};{};{};{};{}", occurred, recorded, paymentReference, transferCommand.getSource(),
            Math.negateExact(transferAmount), transferCommand.getTarget(),
            transferCommand.getDescription());

        accountsRepository.save(target);
        transferEntity = new TransferEntity(paymentReference, target,
            transferAmount, source, transferCommand.getDescription(), recorded);
        transfersRepository.save(transferEntity);
        log.info("{};{};{};{};{};{};{}", occurred, recorded, paymentReference, transferCommand.getTarget(),
            transferAmount,
            transferCommand.getSource(), transferCommand.getDescription());

        return new TransferReceipt(paymentReference, source.getAccountNumber(), source.getCustomer().getName(),
            transferAmount,
            transferCommand.getTarget(), target.getCustomer().getName(), transferCommand.getDescription(), recorded);
    }

    private Long convertToCentsIfApplicable(final Long amount, final boolean isAmountInCents) {
        BigDecimal amountBigDecimal = BigDecimal.valueOf(amount);
        return isAmountInCents ? amount : amountBigDecimal.multiply(BigDecimal.valueOf(100L)).longValue();
    }
}
