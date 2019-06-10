package cc.vivp.bankrupt.transactions.models.db;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import cc.vivp.bankrupt.payments.models.db.Payment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
//Add a private default constructor used through reflection
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
@Table(name = "transactions")
public class Transaction {

  @Id
  String id;
  @OneToOne
  @JoinColumn(name = "id", unique = true)
  Payment payment;
  String status;
  @Basic(optional = true)
  @Column(insertable = false)
  @Temporal(TemporalType.TIMESTAMP)
  Date submittedOn;
  BigDecimal finalBalance;
}
