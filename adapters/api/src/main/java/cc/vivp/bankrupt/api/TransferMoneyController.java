package cc.vivp.bankrupt.api;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import cc.vivp.bankrupt.domain.Money;
import cc.vivp.bankrupt.ports.in.TransferMoneyUseCase;
import cc.vivp.bankrupt.ports.in.TransferMoneyUseCase.TransferMoneyCommand;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TransferMoneyController {

  private final TransferMoneyUseCase transferMoneyUseCase;

  @PostMapping(path = "/accounts/transfer/{sourceAccountId}/{targetAccountId}/{amount}")
  void sendMoney(
      @PathVariable("sourceAccountId") String sourceAccountId,
      @PathVariable("targetAccountId") String targetAccountId,
      @PathVariable("amount") Long amount) throws AccountNotFoundException {

    TransferMoneyCommand command =
        new TransferMoneyCommand(sourceAccountId, targetAccountId, Money.of(amount));

    transferMoneyUseCase.transferMoney(command);
  }
}
