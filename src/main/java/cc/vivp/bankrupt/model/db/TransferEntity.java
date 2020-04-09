package cc.vivp.bankrupt.model.db;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "Transfer")
@Table(name = "transfers")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferEntity {

    public TransferEntity(String paymentReference, AccountEntity source, Long amount, AccountEntity target,
        String description,
        LocalDateTime timestamp) {
        this(null, paymentReference, source, amount, target, description, timestamp);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String paymentReference;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    AccountEntity source;
    Long amount;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    AccountEntity target;
    String description;
    LocalDateTime timestamp;
}
