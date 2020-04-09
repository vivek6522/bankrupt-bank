package cc.vivp.bankrupt.model.db;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "Customer")
@Table(name = "customers")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {

  public CustomerEntity(final String email, final String name) {
    this(null, email, name, new HashSet<>(0));
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String email;
  String name;

  @NonNull
  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  Set<AccountEntity> accounts = new HashSet<>();
}
