package cc.vivp.bankrupt.model.db;

import cc.vivp.bankrupt.model.AccountType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Account")
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

  public AccountEntity(final String accountNumber, final AccountType accountType, final Long customerId,
      final Long balance) {
    this(null, accountNumber, accountType, customerId, balance);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String accountNumber;
  AccountType accountType;
  Long customerId;
  Long balance;
}
