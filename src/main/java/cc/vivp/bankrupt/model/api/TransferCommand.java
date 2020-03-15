package cc.vivp.bankrupt.model.api;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferCommand {

  @NotBlank
  String source;
  @NotBlank
  String target;
  @Min(1)
  Long amount;
  String description;

}
