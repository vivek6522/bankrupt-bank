package cc.vivp.bankrupt.model.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Customer")
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {

  public CustomerEntity(final String email, final String name) {
    this(null, email, name);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  public String email;
  public String name;
}
