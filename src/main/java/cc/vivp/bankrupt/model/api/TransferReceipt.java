package cc.vivp.bankrupt.model.api;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class TransferReceipt {
  
  String paymentReference;
}
