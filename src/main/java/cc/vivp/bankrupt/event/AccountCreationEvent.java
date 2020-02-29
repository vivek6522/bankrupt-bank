package cc.vivp.bankrupt.event;

import cc.vivp.bankrupt.exception.AccountCreationException;
import cc.vivp.bankrupt.model.api.Account;
import cc.vivp.bankrupt.model.api.AccountCommand;
import cc.vivp.bankrupt.model.db.AccountEntity;
import cc.vivp.bankrupt.repository.AccountsRepository;
import cc.vivp.bankrupt.repository.CustomersRepository;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;

@Slf4j
public class AccountCreationEvent extends DomainEvent<Account> {

    private final AccountCommand accountCommand;
    private final AccountsRepository accountsRepository;
    private final CustomersRepository customersRepository;
    private final ModelMapper modelMapper;

    public AccountCreationEvent(final LocalDateTime occurred,
        final AccountCommand accountCommand, final AccountsRepository accountsRepository,
        final CustomersRepository customersRepository, final ModelMapper modelMapper) {
        super(occurred);
        this.accountCommand = accountCommand;
        this.accountsRepository = accountsRepository;
        this.customersRepository = customersRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Account process() throws AccountCreationException {

        if (!customersRepository.existsById(accountCommand.getCustomerId())) {
            throw new AccountCreationException("Account not linked to an existing customer.");
        }

        AccountEntity newAccount = new AccountEntity();
        newAccount.setAccountNumber(RandomStringUtils.randomNumeric(16));
        newAccount.setAccountType(accountCommand.getAccountType());
        newAccount.setBalance(0L);
        newAccount.setCustomerId(accountCommand.getCustomerId());

        accountsRepository.save(newAccount);
        log.info("{} {} {} {} {}", occurred, recorded, newAccount.getAccountNumber(), newAccount.getAccountType(),
            newAccount.getCustomerId());

        return modelMapper.map(newAccount, Account.class);
    }
}
