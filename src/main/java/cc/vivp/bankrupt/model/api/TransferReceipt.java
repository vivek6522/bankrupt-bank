package cc.vivp.bankrupt.model.api;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferReceipt {

  String paymentReference;
  String sourceAccountNumber;
  String sourceCustomerName;
  Long amount;
  String targetAccountNumber;
  String targetCustomerName;
  String description;
  LocalDateTime timestamp;
}
