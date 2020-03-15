package cc.vivp.bankrupt.model.api;

import cc.vivp.bankrupt.model.AccountType;
import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class AccountCommand {

    AccountType accountType;
    @Min(1)
    Long customerId;

}
