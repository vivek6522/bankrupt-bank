package cc.vivp.bankrupt.model.api;

import cc.vivp.bankrupt.model.AccountType;
import lombok.Data;

@Data
public class AccountCommand {

    AccountType accountType;
    Long customerId;

}
