package cc.vivp.bankrupt.ports.out;

import javax.security.auth.login.AccountNotFoundException;

import cc.vivp.bankrupt.domain.Account;

public interface LoadAccountPort {

  Account loadAccount(String accountId) throws AccountNotFoundException;
}
