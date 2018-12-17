package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"persistence","interes"})
@EntityScan(basePackages = {"model"})
@ComponentScan(basePackages = {"persistence", "model", "controller", "services","interes"})
public class BancaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BancaApplication.class, args);
	}
}
