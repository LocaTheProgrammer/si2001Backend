package it.si2001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "it.si2001.entity")
public class Si2001Application {

	public static void main(String[] args) {
		SpringApplication.run(Si2001Application.class, args);
	}

}
