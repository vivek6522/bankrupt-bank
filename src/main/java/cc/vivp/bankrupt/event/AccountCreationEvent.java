package cc.vivp.bankrupt.event;

import cc.vivp.bankrupt.exception.AccountCreationException;
import cc.vivp.bankrupt.model.api.Account;
import cc.vivp.bankrupt.model.api.AccountCommand;
import cc.vivp.bankrupt.model.db.AccountEntity;
import cc.vivp.bankrupt.repository.AccountsRepository;
import cc.vivp.bankrupt.repository.CustomersRepository;
import cc.vivp.bankrupt.util.Constants;
import cc.vivp.bankrupt.util.MessageKeys;
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
            throw new AccountCreationException(MessageKeys.ORPHAN_ACCOUNT);
        }

        AccountEntity newAccount = new AccountEntity();
        newAccount.setAccountNumber(RandomStringUtils.randomNumeric(Constants.ACCOUNT_NUMBER_LENGTH));
        newAccount.setAccountType(accountCommand.getAccountType());
        newAccount.setBalance(Constants.DEFAULT_BALANCE_CENTS);
        newAccount.setCustomerId(accountCommand.getCustomerId());

        accountsRepository.save(newAccount);
        log.info("{};{};{};{};{}", occurred, recorded, newAccount.getAccountNumber(), newAccount.getAccountType(),
            newAccount.getCustomerId());

        return modelMapper.map(newAccount, Account.class);
    }
}
