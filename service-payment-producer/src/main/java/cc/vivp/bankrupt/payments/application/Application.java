package cc.vivp.bankrupt.payments.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "cc.vivp.bankrupt.repositories")
@EntityScan(basePackages = {"cc.vivp.bankrupt.contracts.models.db",
    "cc.vivp.bankrupt.payments.models.db", "cc.vivp.bankrupt.transactions.models.db"})
@EnableDiscoveryClient
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
