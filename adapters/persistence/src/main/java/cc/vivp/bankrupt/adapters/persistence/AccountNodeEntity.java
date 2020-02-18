package cc.vivp.bankrupt.adapters.persistence;

import java.math.BigInteger;
import java.util.List;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import cc.vivp.bankrupt.domain.AccountType;
import cc.vivp.bankrupt.domain.Activity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity(label = "Account")
class AccountNodeEntity {

  @Id
  private String id;
  private AccountType type;
  private BigInteger balance;

  @Relationship(type = "INTERACTS_WITH")
  private List<Activity> activities;
}
