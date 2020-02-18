package cc.vivp.bankrupt.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Account {

  private final String id;
  private final AccountType type;
  private Money balance;
  private List<Activity> activities;

  public boolean withdraw(Money money) {

    if (mayWithdraw(money)) {
      this.balance = this.balance.minus(money);
      return true;
    }
    return false;
  }

  public boolean deposit(Money money) {
    this.balance = this.balance.plus(money);
    return true;
  }

  private boolean mayWithdraw(Money money) {
    return Money.add(this.balance, money.negate()).isPositiveOrZero();
  }
}
