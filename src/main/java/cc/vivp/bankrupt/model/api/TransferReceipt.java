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
  String source;
  Long amount;
  String target;
  String description;
  LocalDateTime timestamp;
}
