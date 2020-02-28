package cc.vivp.bankrupt.app;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cc.vivp.bankrupt.model.api.Account;
import cc.vivp.bankrupt.model.db.AccountEntity;
import cc.vivp.bankrupt.repository.AccountRepository;
import cc.vivp.bankrupt.repository.CustomerRepository;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AccountController {

  private final AccountRepository accountRepository;
  private final CustomerRepository customerRepository;
  private final ModelMapper modelMapper;

  @PostMapping("accounts")
  public Account createAccount(@RequestBody Account newAccount) {
    if (customerRepository.existsById(newAccount.getCustomerId())) {
      newAccount.setAccountNumber(RandomStringUtils.randomNumeric(16));
      return modelMapper.map(
          accountRepository.save(modelMapper.map(newAccount, AccountEntity.class)), Account.class);
    } else {
      throw new IllegalStateException("Customer does not exist");
    }
  }

  @GetMapping("accounts/{accountNumber}")
  public Account getAccountDetails(@PathVariable("accountNumber") String accountNumber) {
    return modelMapper.map(accountRepository.findByAccountNumber(accountNumber), Account.class);
  }
}
