package cc.vivp.bankrupt.adapters.persistence;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import cc.vivp.bankrupt.domain.Account;
import cc.vivp.bankrupt.domain.Activity;
import cc.vivp.bankrupt.domain.Money;

@Component
class AccountMapper {

  Account mapToDomainEntity(AccountJpaEntity account) {
    return new Account(
        account.getId(),
        account.getType(),
        Money.of(account.getBalance()),
        new ArrayList<Activity>());
  }

  Account mapToDomainEntity(AccountNodeEntity account) {
    return new Account(
        account.getId(),
        account.getType(),
        Money.of(account.getBalance()),
        account.getActivities());
  }

  AccountJpaEntity mapToJpaEntity(Account account) {
    return new AccountJpaEntity(
        account.getId(), account.getType(), account.getBalance().getAmount());
  }

  AccountNodeEntity mapToNodeEntity(Account account) {
    return new AccountNodeEntity(
        account.getId(),
        account.getType(),
        account.getBalance().getAmount(),
        account.getActivities());
  }
}
