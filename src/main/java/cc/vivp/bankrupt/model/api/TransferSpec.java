package cc.vivp.bankrupt.model.api;

import lombok.Data;

@Data
public class TransferSpec {
  
  String source;
  String destination;
  Long amount;
  String description;
  
}
