package cc.vivp.bankrupt.model.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cc.vivp.bankrupt.model.AccountType;
import lombok.Data;

@Entity(name = "Account")
@Table(name = "accounts")
@Data
public class AccountEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String accountNumber;
  AccountType accountType;
  Long customerId;
  Long balance;
}
