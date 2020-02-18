package cc.vivp.bankrupt.adapters.persistence;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cc.vivp.bankrupt.domain.Account;
import cc.vivp.bankrupt.ports.out.LoadAccountPort;
import cc.vivp.bankrupt.ports.out.UpdateAccountStatePort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AccountGraphAdapter implements LoadAccountPort, UpdateAccountStatePort {

  private final AccountMapper accountMapper;
  private final AccountRepository accountRepository;

  @Override
  @Transactional
  public void updateAccountState(Account account) {

    accountRepository.save(accountMapper.mapToNodeEntity(account));
  }

  @Override
  @Transactional(readOnly = true)
  public Account loadAccount(String accountId) throws AccountNotFoundException {

    return accountMapper.mapToDomainEntity(
        accountRepository
            .findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException(accountId)));
  }
}
