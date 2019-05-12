package cc.vivp.bankrupt.payments.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import cc.vivp.bankrupt.payments.models.api.Payment;

public class KafkaPaymentCollector {

  @KafkaListener(topics = "${kafka.payments.topic-name}",
      containerFactory = "kafkaListenerContainerFactory",
      groupId = "${spring.kafka.consumer.group-id}")
  void listenAsObject(ConsumerRecord<String, Payment> consumerRecord, @Payload Payment payment) {
    System.out.println("Payment " + payment.getId() + " received");
  }
}
