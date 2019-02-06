package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import model.Banca;

@SpringBootApplication
@EntityScan(basePackages = { "model" })
@ComponentScan(basePackages = { "model", "exceptions" })
public class ApiModelApp {

    public static void main(String[] args) {
        SpringApplication.run(ApiModelApp.class, args);
    }

}
