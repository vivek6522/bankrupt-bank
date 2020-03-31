package cc.vivp.bankrupt.service;

import static cc.vivp.bankrupt.util.Generic.throwEntityNotFoundExceptionIfNotPresentElseReturnValue;

import cc.vivp.bankrupt.event.AccountCreationEvent;
import cc.vivp.bankrupt.exception.AccountCreationException;
import cc.vivp.bankrupt.exception.EntityNotFoundException;
import cc.vivp.bankrupt.model.api.Account;
import cc.vivp.bankrupt.model.api.AccountCommand;
import cc.vivp.bankrupt.model.db.AccountEntity;
import cc.vivp.bankrupt.repository.AccountsRepository;
import cc.vivp.bankrupt.repository.CustomersRepository;
import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Validated
public class AccountService {

    private final AccountsRepository accountsRepository;
    private final CustomersRepository customersRepository;
    private final ModelMapper modelMapper;

    public Account createAccount(@Valid AccountCommand accountCommand) throws AccountCreationException {
        return new AccountCreationEvent(LocalDateTime.now(), accountCommand, accountsRepository, customersRepository,
            modelMapper).process();
    }

    public Account fetchAccountDetails(@NotBlank String accountNumber) throws EntityNotFoundException {
        AccountEntity foundAccount = throwEntityNotFoundExceptionIfNotPresentElseReturnValue(
            accountsRepository.findByAccountNumber(accountNumber));
        return modelMapper.map(foundAccount, Account.class);
    }
}
