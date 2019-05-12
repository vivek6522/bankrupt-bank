package cc.vivp.bankrupt.payments.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "cc.vivp.bankrupt.contracts.repository")
@EntityScan(basePackages = "cc.vivp.bankrupt.contracts.models.db")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
