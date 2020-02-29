package cc.vivp.bankrupt.app;

import cc.vivp.bankrupt.event.AccountCreationEvent;
import cc.vivp.bankrupt.exception.AccountCreationException;
import cc.vivp.bankrupt.exception.EntityNotFoundException;
import cc.vivp.bankrupt.model.api.Account;
import cc.vivp.bankrupt.model.api.AccountCommand;
import cc.vivp.bankrupt.model.db.AccountEntity;
import cc.vivp.bankrupt.repository.AccountsRepository;
import cc.vivp.bankrupt.repository.CustomersRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AccountController {

  private final AccountsRepository accountsRepository;
  private final CustomersRepository customersRepository;
  private final ModelMapper modelMapper;

  @PostMapping("accounts")
  public Account createAccount(@RequestBody AccountCommand accountCommand) throws AccountCreationException {
    return new AccountCreationEvent(LocalDateTime.now(), accountCommand, accountsRepository, customersRepository,
        modelMapper).process();
  }

  @GetMapping("accounts/{accountNumber}")
  public Account getAccountDetails(@PathVariable("accountNumber") String accountNumber) throws EntityNotFoundException {
    AccountEntity foundAccount = accountsRepository.findByAccountNumber(accountNumber);
    if (foundAccount != null) {
      return modelMapper.map(foundAccount, Account.class);
    }
    throw new EntityNotFoundException(Collections.singletonMap("accountNumber", accountNumber));
  }
}
