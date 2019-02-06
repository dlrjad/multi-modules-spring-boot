package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = { "persistence", "interes" })
@EntityScan(basePackages = { "model" })
@ComponentScan(basePackages = { "persistence", "model", "exceptions" })
public class ApiDataApp {

    public static void main(String[] args) {
        SpringApplication.run(ApiDataApp.class, args);
    }

}
