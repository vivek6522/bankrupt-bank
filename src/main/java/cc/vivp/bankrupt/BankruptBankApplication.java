package cc.vivp.bankrupt;

import cc.vivp.bankrupt.model.api.Account;
import cc.vivp.bankrupt.model.api.Customer;
import cc.vivp.bankrupt.model.db.AccountEntity;
import cc.vivp.bankrupt.model.db.CustomerEntity;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
@Configuration
public class BankruptBankApplication {

  public static void main(String[] args) {
    SpringApplication.run(BankruptBankApplication.class, args);
  }

  @Bean
  @Primary
  ModelMapper modelMapperDefault() {
    return new ModelMapper();
  }

  @Bean
  @Qualifier("CustomerComplete")
  ModelMapper modelMapperCustomerComplete() {
    ModelMapper modelMapper = new ModelMapper();
    TypeMap<CustomerEntity, Customer> customerTypeMap = modelMapper
        .typeMap(CustomerEntity.class, Customer.class);
    customerTypeMap.addMappings(mapper -> mapper.skip(Customer::setAccounts));
    return modelMapper;
  }

  @Bean
  @Qualifier("CustomerPartial")
  ModelMapper modelMapperCustomerPartial() {
    ModelMapper modelMapper = new ModelMapper();
    TypeMap<CustomerEntity, Customer> customerTypeMap = modelMapper
        .typeMap(CustomerEntity.class, Customer.class);
    customerTypeMap.addMappings(mapper -> mapper.skip(Customer::setAccounts))
        .addMappings(mapper -> mapper.skip(Customer::setId))
        .addMappings(mapper -> mapper.skip(Customer::setEmail));
    return modelMapper;
  }

  @Bean
  ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(Include.NON_NULL);
    return objectMapper;
  }

}
