package perso.alten.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableConfigurationProperties
@EnableTransactionManagement
@EnableJpaRepositories("perso.alten.test.data.repository")
@ComponentScan(basePackages = "perso.alten.test")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}