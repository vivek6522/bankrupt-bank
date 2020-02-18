package cc.vivp.bankrupt.adapters.persistence;

import java.math.BigInteger;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cc.vivp.bankrupt.domain.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@Data
@AllArgsConstructor
@NoArgsConstructor
class AccountJpaEntity {

  @Id private String id;

  @Convert(converter = AccountTypeAttributeConverter.class)
  private AccountType type;

  private BigInteger balance;
}
