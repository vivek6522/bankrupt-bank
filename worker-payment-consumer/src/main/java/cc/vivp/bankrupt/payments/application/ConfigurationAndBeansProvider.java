package cc.vivp.bankrupt.payments.application;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import cc.vivp.bankrupt.payments.consumer.KafkaPaymentCollector;
import cc.vivp.bankrupt.repositories.ContractRepository;
import cc.vivp.bankrupt.repositories.TransactionRepository;

@Configuration
public class ConfigurationAndBeansProvider {

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
      KafkaProperties kafkaProperties) {

    JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
    jsonDeserializer.addTrustedPackages("cc.vivp.bankrupt.payments.models.api");

    ConsumerFactory<String, Object> consumerFactory = new DefaultKafkaConsumerFactory<>(
        kafkaProperties.buildConsumerProperties(), new StringDeserializer(), jsonDeserializer);

    ConcurrentKafkaListenerContainerFactory<String, Object> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);

    return factory;
  }

  @Bean
  public KafkaPaymentCollector enableKafkaPaymentCollector(
      TransactionRepository transactionRepository, ContractRepository contractRepository) {
    return new KafkaPaymentCollector(transactionRepository, contractRepository);
  }
}
