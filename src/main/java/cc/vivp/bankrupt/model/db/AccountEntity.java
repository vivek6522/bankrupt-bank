package cc.vivp.bankrupt.model.db;

import cc.vivp.bankrupt.model.AccountType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "Account")
@Table(name = "accounts")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

  public AccountEntity(final String accountNumber, final AccountType accountType, final Long balance,
      final CustomerEntity customer) {
    this(null, accountNumber, accountType, balance, customer);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String accountNumber;
  AccountType accountType;
  Long balance;

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  CustomerEntity customer;
}
