package co.edu.unisabana.puntosUnisabana.integration;

import co.edu.unisabana.puntosUnisabana.controller.DTO.BeneficioDTO;
import co.edu.unisabana.puntosUnisabana.controller.DTO.RespuestaDTO;
import co.edu.unisabana.puntosUnisabana.logic.BeneficiosLogica;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class BeneficioControllerTest {

    @Autowired
    TestRestTemplate rest;
    @Autowired
    BeneficiosLogica beneficio;
    public static BeneficioDTO nuevoBeneficio1 = new BeneficioDTO("Nuevo Beneficio 1", 1, 1);
    public static BeneficioDTO nuevoBeneficio2 = new BeneficioDTO("Nuevo Beneficio 2", 100, 2);

    @Test
    void Cuando_agregue_Entonces_dtoExitoso() {
        ResponseEntity<RespuestaDTO> respuestaDTO = rest.postForEntity("/beneficio/agregar",
                nuevoBeneficio1, RespuestaDTO.class);
        assertEquals("Beneficio agregado correctamente", respuestaDTO.getBody().getMensaje());

    }

    @Test
    void Dado_2Beneficos_Cuando_busqueBeneficios_Entonces_listaBeneficios() {
        ResponseEntity<Void> respuestaAgregarBeneficio1 = rest.postForEntity("/beneficio/agregar", nuevoBeneficio1, Void.class);
        ResponseEntity<Void> respuestaAgregarBeneficio2 = rest.postForEntity("/beneficio/agregar", nuevoBeneficio2, Void.class);
        assertEquals(HttpStatus.OK, respuestaAgregarBeneficio1.getStatusCode());
        assertEquals(HttpStatus.OK, respuestaAgregarBeneficio2.getStatusCode());
        ResponseEntity<List<BeneficioDTO>> respuestaBuscarBeneficios = rest.exchange("/beneficios", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        List<BeneficioDTO> beneficios = respuestaBuscarBeneficios.getBody();
        assertNotNull(beneficios);
        assertTrue(beneficios.stream().anyMatch(b -> b.getNombreBeneficio().equals("Nuevo Beneficio 1")));
        assertTrue(beneficios.stream().anyMatch(b -> b.getNombreBeneficio().equals("Nuevo Beneficio 2")));
    }

    @Test
    void Cuando_busqueBeneficiosEstaVacio_Entonces_listaVacia() {
        ResponseEntity<List<BeneficioDTO>> response = rest.exchange("/beneficios", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        List<BeneficioDTO> beneficios = response.getBody();
        assertNotNull(beneficios);
        assertTrue(beneficios.isEmpty());
    }
}
