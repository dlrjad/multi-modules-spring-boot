package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * EnableJpaRepositories se indica el nombre de la carpeta donde se encuentra el repositorio
 * dentro de su módulo api-data
 * 
 * EntityScan se indica el nombre de la carpeta donde se encuentra el modelo
 * dentro de su módulo api-model
 * 
 * ComponentScan se indica el nombre de las carpetas donde se encuentra los componentes
 * de cada módulo: api-model, api-data y web
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"persistence","interes"})
@EntityScan(basePackages = {"model"})
@ComponentScan(basePackages = {"persistence", "model", "controller", "services","interes"})
public class BancaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BancaApplication.class, args);
	}
}
