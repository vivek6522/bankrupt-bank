package cc.vivp.bankrupt.service;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.vivp.bankrupt.domain.Account;
import cc.vivp.bankrupt.ports.in.TransferMoneyUseCase;
import cc.vivp.bankrupt.ports.out.LoadAccountPort;
import cc.vivp.bankrupt.ports.out.UpdateAccountStatePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = { @Autowired })
@Transactional
@Service
public class TransferMoneyService implements TransferMoneyUseCase {

	private final LoadAccountPort loadAccountPort;
	private final UpdateAccountStatePort updateAccountStatePort;
	private final MoneyTransferProperties moneyTransferProperties;

	@Override
	public boolean transferMoney(TransferMoneyCommand command) throws AccountNotFoundException {

		checkThreshold(command);

		Account sourceAccount = loadAccountPort.loadAccount(command.getSourceAccountId());
		Account targetAccount = loadAccountPort.loadAccount(command.getTargetAccountId());

		if (!sourceAccount.withdraw(command.getMoney())) {
			return false;
		}

		if (!targetAccount.deposit(command.getMoney())) {
			return false;
		}

		updateAccountStatePort.updateAccountState(sourceAccount);
		updateAccountStatePort.updateAccountState(targetAccount);

		return true;
	}

	private void checkThreshold(TransferMoneyCommand command) {
		if(command.getMoney().isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())){
			throw new ThresholdExceededException(moneyTransferProperties.getMaximumTransferThreshold(), command.getMoney());
		}
	}

}




