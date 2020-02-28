package cc.vivp.bankrupt.app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Configuration
@EntityScan(basePackages = {"cc.vivp.bankrupt.model.db"})
@EnableJpaRepositories(basePackages = {"cc.vivp.bankrupt.repository"})
public class BankruptBankApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(BankruptBankApplication.class, args);
  }

  @Bean
  ModelMapper modelMapper() {
    return new ModelMapper();
  }

}
