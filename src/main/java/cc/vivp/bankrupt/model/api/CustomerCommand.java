package cc.vivp.bankrupt.model.api;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerCommand {

    @Email
    String email;
    @NotBlank
    String name;

}
