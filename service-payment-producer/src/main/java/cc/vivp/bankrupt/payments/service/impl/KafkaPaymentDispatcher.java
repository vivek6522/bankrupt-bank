package cc.vivp.bankrupt.payments.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.springframework.kafka.core.KafkaTemplate;
import cc.vivp.bankrupt.contracts.models.db.Contract;
import cc.vivp.bankrupt.payments.models.api.Payment;
import cc.vivp.bankrupt.payments.models.api.PaymentDispatchException;
import cc.vivp.bankrupt.payments.service.IPaymentDispatcher;
import cc.vivp.bankrupt.repositories.ContractRepository;
import cc.vivp.bankrupt.repositories.PaymentRepository;

public class KafkaPaymentDispatcher implements IPaymentDispatcher {

  private final ContractRepository contractRepository;
  private final PaymentRepository paymentRepository;
  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final String topicName;

  public KafkaPaymentDispatcher(ContractRepository contractRepository,
      PaymentRepository paymentRepository, KafkaTemplate<String, Object> kafkaTemplate,
      String topicName) {
    this.contractRepository = contractRepository;
    this.paymentRepository = paymentRepository;
    this.kafkaTemplate = kafkaTemplate;
    this.topicName = topicName;
  }

  @Override
  public Payment dispatchPayment(Payment payment) throws PaymentDispatchException {

    if (!doesPassPreScreening(payment)) {
      throw new PaymentDispatchException(
          "Debtor account not owned by logged in customer or does not exist");
    }

    String paymentId = UUID.randomUUID().toString();
    Payment paymentConfirmation =
        new Payment(paymentId, payment.getDebtorIban(), payment.getCreditorIban(),
            payment.getAmount(), payment.getDescription(), payment.getCreditorName());

    paymentRepository.save(new cc.vivp.bankrupt.payments.models.db.Payment(paymentId,
        payment.getDebtorIban(), payment.getCreditorIban(), payment.getAmount(),
        payment.getDescription(), payment.getCreditorName()));

    kafkaTemplate.send(topicName, paymentConfirmation);

    return paymentConfirmation;
  }

  private boolean doesPassPreScreening(Payment payment) {

    Optional<Contract> contract = contractRepository.findById(payment.getDebtorIban());

    /*
     * For now, simply check if the contract exists in the database. Later complex business logic
     * will be added to check if the customer in-fact owns the contract.
     */
    return contract.isPresent();
  }

}
