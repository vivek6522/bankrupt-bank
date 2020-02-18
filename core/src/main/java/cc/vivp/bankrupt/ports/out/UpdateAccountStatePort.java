package cc.vivp.bankrupt.ports.out;

import cc.vivp.bankrupt.domain.Account;

public interface UpdateAccountStatePort {

  void updateAccountState(Account account);
}
