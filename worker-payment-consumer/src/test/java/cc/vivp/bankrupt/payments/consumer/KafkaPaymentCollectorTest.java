package cc.vivp.bankrupt.payments.consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import cc.vivp.bankrupt.contracts.models.db.Contract;
import cc.vivp.bankrupt.payments.models.api.Payment;
import cc.vivp.bankrupt.repositories.ContractRepository;
import cc.vivp.bankrupt.repositories.TransactionRepository;
import cc.vivp.bankrupt.transactions.models.db.Transaction;

@ExtendWith(MockitoExtension.class)
@DisplayName("Kafka payment collector")
public class KafkaPaymentCollectorTest {

  private static final String CURRENT = "CURRENT";

  @Mock
  private ContractRepository contractRepository;
  @Mock
  private TransactionRepository transactionRepository;

  private KafkaPaymentCollector underTest;

  @BeforeEach
  void setup() {
    underTest = new KafkaPaymentCollector(transactionRepository, contractRepository);
  }

  @Test
  @DisplayName("should fulfill a payment when details are valid")
  void shouldFulfillPaymentWhenDetailsValid() {

    String debtorIban = "NL80ABNA0419499482";
    String creditorIban = "NL95ABNA0547637861";

    Optional<Contract> debtor = Optional.of(new Contract(debtorIban, CURRENT, BigDecimal.TEN,
        "ec0ce38a-ec2c-4475-9cbc-a66689b21a22"));
    doReturn(debtor).when(contractRepository).findById(debtorIban);

    Optional<Contract> creditor = Optional.of(new Contract(creditorIban, CURRENT, BigDecimal.TEN,
        "ec0ce38a-ec2c-4475-9cbc-a66689b21a21"));
    doReturn(creditor).when(contractRepository).findById(creditorIban);

    doReturn(null).when(contractRepository).save(any(Contract.class));
    doReturn(null).when(transactionRepository).save(any(Transaction.class));

    Payment incomingPayment = new Payment("fc0ce38a-ec2c-4475-9cbc-a66689b21a21", debtorIban,
        creditorIban, BigDecimal.TEN, "Test Payment", "Creditor");

    underTest.listenForPayment(null, incomingPayment);
  }

  @Test
  @DisplayName("should not fulfill payment when creditor is not found")
  void shouldNotFulfillPaymentWhenCreditorNotFound() {

    String debtorIban = "NL80ABNA0419499482";
    String creditorIban = "NL95ABNA0547637861";

    Optional<Contract> debtor = Optional.of(new Contract(debtorIban, CURRENT, BigDecimal.TEN,
        "ec0ce38a-ec2c-4475-9cbc-a66689b21a22"));
    doReturn(debtor).when(contractRepository).findById(debtorIban);

    doReturn(Optional.empty()).when(contractRepository).findById(creditorIban);

    doReturn(null).when(transactionRepository).save(any(Transaction.class));

    Payment incomingPayment = new Payment("ec0ce38a-ec2c-4475-9cbc-a66689b21a25", debtorIban,
        creditorIban, BigDecimal.TEN, "Test Payment", "Creditor");

    underTest.listenForPayment(null, incomingPayment);
  }

}
