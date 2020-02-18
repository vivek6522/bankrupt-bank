package cc.vivp.bankrupt.adapters.persistence;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cc.vivp.bankrupt.domain.Account;
import cc.vivp.bankrupt.ports.out.LoadAccountPort;
import cc.vivp.bankrupt.ports.out.UpdateAccountStatePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
class AccountPersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort {

  private final AccountRepository accountRepository;
  private final ActivityRepository activityRepository;
  private final AccountMapper accountMapper;

  @Override
  public Account loadAccount(String accountId) {

//    AccountJpaEntity account =
//        accountRepository.findById(accountId).orElseThrow(EntityNotFoundException::new);
//
//    return accountMapper.mapToDomainEntity(account);
    return null;
  }

  @Override
  public void updateAccountState(Account account) {

//    accountRepository.save(accountMapper.mapToJpaEntity(account));
  }
}
