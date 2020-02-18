package cc.vivp.bankrupt.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication(scanBasePackages = "cc.vivp.bankrupt")
@EnableNeo4jRepositories(basePackages = {"cc.vivp.bankrupt.adapters.persistence"})
@EntityScan(basePackages = {"cc.vivp.bankrupt.adapters.persistence"})
@EnableDiscoveryClient
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
