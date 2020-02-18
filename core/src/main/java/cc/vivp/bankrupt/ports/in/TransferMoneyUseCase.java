package cc.vivp.bankrupt.ports.in;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.constraints.NotNull;

import cc.vivp.bankrupt.common.SelfValidating;
import cc.vivp.bankrupt.domain.Money;
import lombok.EqualsAndHashCode;
import lombok.Value;

public interface TransferMoneyUseCase {

  boolean transferMoney(TransferMoneyCommand command) throws AccountNotFoundException;

  @Value
  @EqualsAndHashCode(callSuper = false)
  class TransferMoneyCommand extends SelfValidating<TransferMoneyCommand> {

    @NotNull private final String sourceAccountId;

    @NotNull private final String targetAccountId;

    @NotNull private final Money money;

    public TransferMoneyCommand(String sourceAccountId, String targetAccountId, Money money) {
      this.sourceAccountId = sourceAccountId;
      this.targetAccountId = targetAccountId;
      this.money = money;
      this.validateSelf();
    }
  }
}
