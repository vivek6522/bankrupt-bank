package cc.vivp.bankrupt.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
public class Customer {

    Long id;
    String email;
    String name;
    @JsonIgnoreProperties("customer")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Set<Account> accounts;

}
