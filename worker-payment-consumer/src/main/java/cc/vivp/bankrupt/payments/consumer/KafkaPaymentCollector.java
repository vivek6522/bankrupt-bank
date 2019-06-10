package cc.vivp.bankrupt.payments.consumer;

import java.math.BigDecimal;
import java.util.Optional;
import javax.transaction.Transactional;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import cc.vivp.bankrupt.contracts.models.db.Contract;
import cc.vivp.bankrupt.payments.models.api.Payment;
import cc.vivp.bankrupt.repositories.ContractRepository;
import cc.vivp.bankrupt.repositories.TransactionRepository;
import cc.vivp.bankrupt.transactions.models.db.Transaction;

public class KafkaPaymentCollector {

  private final TransactionRepository transactionRepository;
  private final ContractRepository contractRepository;

  public KafkaPaymentCollector(TransactionRepository transactionRepository,
      ContractRepository contractRepository) {
    this.transactionRepository = transactionRepository;
    this.contractRepository = contractRepository;
  }

  @Transactional
  @KafkaListener(topics = "${kafka.payments.topic-name}",
      containerFactory = "kafkaListenerContainerFactory",
      groupId = "${spring.kafka.consumer.group-id}")
  public void listenForPayment(ConsumerRecord<String, Payment> consumerRecord,
      @Payload Payment payment) {

    Optional<Contract> debtor = contractRepository.findById(payment.getDebtorIban());
    Optional<Contract> creditor = contractRepository.findById(payment.getCreditorIban());

    if (creditor.isPresent()) {

      if (debtor.isPresent()) {

        Contract debtorContract = debtor.get();

        BigDecimal updatedBalance = debtorContract.getBalance().subtract(payment.getAmount());

        contractRepository.save(new Contract(debtorContract.getIban(), debtorContract.getType(),
            updatedBalance, debtorContract.getClientId()));

        transactionRepository
            .save(new Transaction(payment.getId(), null, "COMPLETED", null, updatedBalance));

      } else {
        throw new IllegalStateException(
            "Debtor account not owned by logged in customer or does not exist. Database might be in an inconsistent state as this check must have been performed before this payment was put in the queue.");
      }

    } else if (debtor.isPresent()) {
      transactionRepository.save(new Transaction(payment.getId(), null, "CREDITOR_NOT_FOUND", null,
          debtor.get().getBalance()));

    } else {
      throw new IllegalStateException(
          "Neither of debtor or creditor account found. Database might be in an inconsistent state as this check must have been performed before this payment was put in the queue.");
    }

  }
}
