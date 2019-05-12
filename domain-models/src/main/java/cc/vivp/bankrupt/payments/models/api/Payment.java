package cc.vivp.bankrupt.payments.models.api;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
//Add a private default constructor used through reflection
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Payment {

  String id;
  @Pattern(regexp = "^[a-zA-Z]{2}[0-9]{2}[a-zA-Z0-9]{4}[0-9]{7}([a-zA-Z0-9]?){0,16}$")
  String debtorIban;
  @Pattern(regexp = "^[a-zA-Z]{2}[0-9]{2}[a-zA-Z0-9]{4}[0-9]{7}([a-zA-Z0-9]?){0,16}$")
  String creditorIban;
  @DecimalMin(value = "0.00")
  BigDecimal amount;
  @Pattern(regexp = "^[a-zA-Z0-9]{4,70}$")
  String description;
  @Pattern(regexp = "^[a-zA-Z0-9]{4,70}$")
  String creditorName;
}
