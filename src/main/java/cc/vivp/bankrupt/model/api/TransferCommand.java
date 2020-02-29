package cc.vivp.bankrupt.model.api;

import lombok.Data;

@Data
public class TransferCommand {
  
  String source;
  String destination;
  Long amount;
  String description;
  
}
