package co.edu.unisabana.puntosUnisabana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class PuntosUnisabanaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PuntosUnisabanaApplication.class, args);
	}

}
