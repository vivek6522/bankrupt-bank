package cc.vivp.bankrupt.contracts.models.db;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
//Add a private default constructor used through reflection
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
@Table(name = "contracts")
public class Contract {

  @Id
  String iban;
  String type;
  BigDecimal balance;
  String clientId;
}
