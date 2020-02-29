package cc.vivp.bankrupt.model.db;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Transfer")
@Table(name = "transfers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferEntity {

    public TransferEntity(String paymentReference, String source, Long amount, String destination, String description, LocalDateTime timestamp) {
        this(null, paymentReference, source, amount, destination, description, timestamp);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String paymentReference;
    String source;
    Long amount;
    String destination;
    String description;
    LocalDateTime timestamp;
}
