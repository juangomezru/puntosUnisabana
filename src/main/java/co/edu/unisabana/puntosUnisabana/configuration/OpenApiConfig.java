package co.edu.unisabana.puntosUnisabana.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Equipo FlipFlops",
                        email = "jeanvaes@unisabana.edu.co",
                        url = "https://github.com/juangomezru/puntosUnisabana.git"
                ),
                description = "Documentación para la aplicación de puntos Unisabana",
                title = "OpenApi - FlipFlops",
                version = "1.0.0",
                license = @License(
                        name = "MIT Licence"
                )
        ),
        servers = {
                @Server(
                        description = "Ambiente Local",
                        url = "http://localhost:8080"
                )
        }
)
public class OpenApiConfig {
}
