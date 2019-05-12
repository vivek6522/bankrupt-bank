package cc.vivp.bankrupt.contracts.models.api;

import java.math.BigDecimal;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
//Add a private default constructor used through reflection
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Contract {

  @Pattern(regexp = "^[a-zA-Z]{2}[0-9]{2}[a-zA-Z0-9]{4}[0-9]{7}([a-zA-Z0-9]?){0,16}$")
  String iban;
  @Pattern(regexp = "^[a-zA-Z0-9]{0,128}$")
  String type;
  BigDecimal balance;
  @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")
  String clientId;
}
