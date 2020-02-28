package cc.vivp.bankrupt.app;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cc.vivp.bankrupt.model.api.TransferReceipt;
import cc.vivp.bankrupt.model.api.TransferSpec;
import cc.vivp.bankrupt.model.db.AccountEntity;
import cc.vivp.bankrupt.repository.AccountRepository;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class TransferController {
  
  private final AccountRepository accountRepository;

  @PostMapping("transfer")
  @Transactional
  public TransferReceipt initiateFundsTransfer(@RequestBody TransferSpec transferSpec) {
    AccountEntity source = accountRepository.findByAccountNumber(transferSpec.getSource());
    AccountEntity destination = accountRepository.findByAccountNumber(transferSpec.getDestination());
    
    source.setBalance(source.getBalance() - transferSpec.getAmount());
    destination.setBalance(destination.getBalance() + transferSpec.getAmount());
    
    accountRepository.save(source);
    accountRepository.save(destination);
    
    return new TransferReceipt(RandomStringUtils.randomNumeric(16));
  }
}
