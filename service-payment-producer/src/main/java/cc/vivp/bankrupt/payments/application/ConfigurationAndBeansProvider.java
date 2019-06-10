package cc.vivp.bankrupt.payments.application;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import cc.vivp.bankrupt.payments.service.IPaymentDispatcher;
import cc.vivp.bankrupt.payments.service.impl.KafkaPaymentDispatcher;
import cc.vivp.bankrupt.repositories.ContractRepository;
import cc.vivp.bankrupt.repositories.PaymentRepository;

@Configuration
public class ConfigurationAndBeansProvider {

  @Bean
  IPaymentDispatcher providePaymentDispatcherIntegration(ContractRepository contractRepository,
      PaymentRepository paymentRepository, KafkaTemplate<String, Object> kafkaTemplate,
      @Value("${kafka.payments.topic-name}") String topicName) {
    return new KafkaPaymentDispatcher(contractRepository, paymentRepository, kafkaTemplate,
        topicName);
  }

  @Bean
  KafkaTemplate<String, Object> provideKafkaTemplate(KafkaProperties kafkaProperties) {

    Map<String, Object> properties = new HashMap<>(kafkaProperties.buildProducerProperties());
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

    ProducerFactory<String, Object> producerFactory = new DefaultKafkaProducerFactory<>(properties);

    return new KafkaTemplate<>(producerFactory);
  }

}
