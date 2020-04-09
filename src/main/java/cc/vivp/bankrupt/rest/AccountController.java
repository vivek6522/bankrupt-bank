package cc.vivp.bankrupt.rest;

import cc.vivp.bankrupt.exception.AccountCreationException;
import cc.vivp.bankrupt.exception.EntityNotFoundException;
import cc.vivp.bankrupt.model.api.Account;
import cc.vivp.bankrupt.model.api.AccountCommand;
import cc.vivp.bankrupt.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AccountController {

  private final AccountService accountService;

  @PostMapping("accounts")
  public Account createAccount(@RequestBody AccountCommand accountCommand) throws AccountCreationException {
    return accountService.createAccount(accountCommand);
  }

  @GetMapping("accounts/{accountNumber}")
  public Account fetchCompleteAccountDetails(@PathVariable("accountNumber") String accountNumber)
      throws EntityNotFoundException {
    return accountService.fetchAccountDetails(accountNumber, true);
  }

}
