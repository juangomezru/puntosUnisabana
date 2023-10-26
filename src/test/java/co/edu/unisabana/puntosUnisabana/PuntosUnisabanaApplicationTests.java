package co.edu.unisabana.puntosUnisabana;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@EnableWebSecurity
class PuntosUnisabanaApplicationTests {

	@Test
	void contextLoads() {
	}

}
