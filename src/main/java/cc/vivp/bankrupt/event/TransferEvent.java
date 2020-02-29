package cc.vivp.bankrupt.event;

import cc.vivp.bankrupt.model.api.TransferCommand;
import cc.vivp.bankrupt.model.api.TransferReceipt;
import cc.vivp.bankrupt.model.db.AccountEntity;
import cc.vivp.bankrupt.model.db.TransferEntity;
import cc.vivp.bankrupt.repository.AccountsRepository;
import cc.vivp.bankrupt.repository.TransfersRepository;
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
    public TransferReceipt process() {

        AccountEntity source = accountsRepository.findByAccountNumber(transferCommand.getSource());
        AccountEntity destination = accountsRepository.findByAccountNumber(transferCommand.getDestination());

        source.setBalance(source.getBalance() - transferCommand.getAmount());
        destination.setBalance(destination.getBalance() + transferCommand.getAmount());

        String paymentReference = UUID.randomUUID().toString();

        accountsRepository.save(source);
        log.info("{} {} {} {} {} {} {}", occurred, recorded, paymentReference, transferCommand.getSource(),
            Math.negateExact(transferCommand.getAmount()), transferCommand.getDestination(),
            transferCommand.getDescription());
        TransferEntity transferEntity = new TransferEntity(paymentReference, transferCommand.getSource(),
            Math.negateExact(transferCommand.getAmount()), transferCommand.getDestination(),
            transferCommand.getDescription(), recorded);
        transfersRepository.save(transferEntity);

        accountsRepository.save(destination);
        log.info("{} {} {} {} {} {} {}", occurred, recorded, paymentReference, transferCommand.getDestination(),
            transferCommand.getAmount(),
            transferCommand.getSource(), transferCommand.getDescription());
        transferEntity = new TransferEntity(paymentReference, transferCommand.getDestination(),
            transferCommand.getAmount(), transferCommand.getSource(), transferCommand.getDescription(), recorded);
        transfersRepository.save(transferEntity);

        return new TransferReceipt(paymentReference, transferCommand.getSource(), transferCommand.getAmount(),
            transferCommand.getDestination(), transferCommand.getDescription(), recorded);
    }
}
