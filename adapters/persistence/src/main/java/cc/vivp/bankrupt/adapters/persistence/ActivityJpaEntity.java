package cc.vivp.bankrupt.adapters.persistence;

import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "activity")
@Data
@AllArgsConstructor
@NoArgsConstructor
class ActivityJpaEntity {

  @Id private String id;
  @Column private LocalDateTime timestamp;
  @Column private String sourceAccountId;
  @Column private String targetAccountId;
  @Column private BigInteger amount;
}
