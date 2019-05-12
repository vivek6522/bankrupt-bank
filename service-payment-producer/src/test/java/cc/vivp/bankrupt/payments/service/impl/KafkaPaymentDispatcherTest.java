package cc.vivp.bankrupt.payments.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import cc.vivp.bankrupt.contracts.models.db.Contract;
import cc.vivp.bankrupt.contracts.repository.ContractRepository;
import cc.vivp.bankrupt.payments.models.api.Payment;
import cc.vivp.bankrupt.payments.models.api.PaymentDispatchException;
import cc.vivp.bankrupt.payments.service.IPaymentDispatcher;

@ExtendWith(MockitoExtension.class)
@DisplayName("Kafka payment dispatcher")
public class KafkaPaymentDispatcherTest {

  private static final String TOPIC_NAME = "dummy-topic";

  @Mock
  private ContractRepository contractRepository;
  @Mock
  private KafkaTemplate<String, Object> kafkaTemplate;

  private IPaymentDispatcher underTest;

  @BeforeEach
  void setup() {
    underTest = new KafkaPaymentDispatcher(contractRepository, kafkaTemplate, TOPIC_NAME);
  }

  @Test
  @DisplayName("should dispatch payment if pre-screening successful")
  void shouldDispatchPaymentIfPreScreeningSuccessful() throws PaymentDispatchException {

    String debtorIban = "NL80ABNA0419499482";
    Payment payment =
        new Payment(null, debtorIban, "NL95ABNA0547637861", BigDecimal.TEN, null, "Beneficiary");

    Optional<Contract> contract =
        Optional.of(new Contract(debtorIban, "current", BigDecimal.TEN, "123"));
    when(contractRepository.findById(debtorIban)).thenReturn(contract);

    ListenableFuture<SendResult<String, Object>> listenableFuture = null;
    when(kafkaTemplate.send(anyString(), any(Payment.class))).thenReturn(listenableFuture);

    underTest.dispatchPayment(payment);

    verify(contractRepository, times(1)).findById(anyString());
    verify(kafkaTemplate, times(1)).send(anyString(), any(Payment.class));

  }

  @Test
  @DisplayName("should not dispatch payment if pre-screening unsuccessful")
  void shouldNotDispatchPaymentIfPreScreeningUnsuccessful() throws PaymentDispatchException {

    String debtorIban = "NL80ABNA0419499482";
    Payment payment =
        new Payment(null, debtorIban, "NL95ABNA0547637861", BigDecimal.TEN, null, "Beneficiary");

    Optional<Contract> contract = Optional.empty();
    when(contractRepository.findById(debtorIban)).thenReturn(contract);

    assertThrows(PaymentDispatchException.class, () -> {
      underTest.dispatchPayment(payment);
    });

    verify(contractRepository, times(1)).findById(anyString());

  }

}
