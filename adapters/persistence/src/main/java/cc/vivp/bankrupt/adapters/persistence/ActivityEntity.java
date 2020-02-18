package cc.vivp.bankrupt.adapters.persistence;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import cc.vivp.bankrupt.domain.Account;

@NodeEntity
public class ActivityEntity {
  
  @Id
  @GeneratedValue
  private Long id;
  private Account account;
}
