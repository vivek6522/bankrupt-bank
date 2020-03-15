package cc.vivp.bankrupt;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class BankruptBankApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(BankruptBankApplication.class, args);
  }

  @Bean
  ModelMapper modelMapper() {
    return new ModelMapper();
  }

}
