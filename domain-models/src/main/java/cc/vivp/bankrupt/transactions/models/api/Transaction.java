package cc.vivp.bankrupt.transactions.models.api;

import java.math.BigDecimal;
import java.util.Date;
import cc.vivp.bankrupt.payments.models.api.Payment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
//Add a private default constructor used through reflection
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Transaction {

  public Transaction(cc.vivp.bankrupt.transactions.models.db.Transaction transaction) {
    this.id = transaction.getId();
    this.payment = new Payment(transaction.getPayment());
    this.status = transaction.getStatus();
    this.submittedOn = transaction.getSubmittedOn();
    this.finalBalance = transaction.getFinalBalance();
  }

  String id;
  Payment payment;
  String status;
  Date submittedOn;
  BigDecimal finalBalance;
}
