package co.edu.unisabana.puntosUnisabana.integration;

import co.edu.unisabana.puntosUnisabana.controller.DTO.BeneficioDTO;
import co.edu.unisabana.puntosUnisabana.controller.DTO.ClienteDTO;
import co.edu.unisabana.puntosUnisabana.controller.DTO.RespuestaDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
class ClienteControllerTest {
    @Autowired
    TestRestTemplate rest;
    private BeneficioDTO beneficioDTO1 = BeneficioControllerTest.nuevoBeneficio1;
    private BeneficioDTO beneficioDTO2 = BeneficioControllerTest.nuevoBeneficio2;

    @Test
    void Dado_clienteDTO_Cuando_noExisteCLiente_Entonces_dtoExitoso() {
        ClienteDTO cliente = new ClienteDTO(12345, "Juan Diego", "juangoru@gmail.com", null);
        ResponseEntity<RespuestaDTO> respuestaDTO = rest.postForEntity("/cliente/agregar",
                cliente, RespuestaDTO.class);
        Assertions.assertEquals("Cliente agregado correctamente", respuestaDTO.getBody().getMensaje());
    }

    @Test
    void Dado_clienteDTO_Cuando_ExisteCLiente_Entonces_dtoFallido() {
        ClienteDTO cliente = new ClienteDTO(1234, "Juan Diego", "juangoru@gmail.com", null);
        ResponseEntity<Void> respuestaAgregarDTO = rest.postForEntity("/cliente/agregar",
                cliente, Void.class);
        assertEquals(HttpStatus.OK, respuestaAgregarDTO.getStatusCode());

        ClienteDTO cliente2 = new ClienteDTO(1234, "Juan Diego", "juangoru@gmail.com", null);
        ResponseEntity<RespuestaDTO> respuestaDTO = rest.postForEntity("/cliente/agregar",
                cliente, RespuestaDTO.class);
        Assertions.assertEquals("El cliente no se pudo agregar Ya se encuentra registrado el cliente", respuestaDTO.getBody().getMensaje());
    }

    @Test
    void Dado_cedulaCliente_Cuando_clienteNoEstaAfiliado_Entonces_dtoExitoso() {

        ClienteDTO cliente = new ClienteDTO(1234, "Juan Diego", "juangoru@gmail.com", null);
        ResponseEntity<Void> respuestaAgregarDTO = rest.postForEntity("/cliente/agregar",
                cliente, Void.class);
        assertEquals(HttpStatus.OK, respuestaAgregarDTO.getStatusCode());

        ResponseEntity<RespuestaDTO> respuestaDTO = rest.postForEntity("/cliente/afiliacion/" + cliente.getCedula(),
                null, RespuestaDTO.class);
        Assertions.assertEquals("El cliente se afilio correctamente", respuestaDTO.getBody().getMensaje());
    }

    @Test
    void Dado_cedulaCliente_Cuando_clienteEstaAfiliado_Entonces_dtoFallido() {

        ClienteDTO cliente = new ClienteDTO(1234, "Juan Diego", "juangoru@gmail.com", null);
        ResponseEntity<Void> respuestaAgregarDTO = rest.postForEntity("/cliente/agregar",
                cliente, Void.class);
        assertEquals(HttpStatus.OK, respuestaAgregarDTO.getStatusCode());

        ResponseEntity<Void> respuestaDTOAfiliar1 = rest.postForEntity("/cliente/afiliacion/" + cliente.getCedula(),
                null, Void.class);
        assertEquals(HttpStatus.OK, respuestaDTOAfiliar1.getStatusCode());
        ResponseEntity<RespuestaDTO> respuestaDTOAfiliar2 = rest.postForEntity("/cliente/afiliacion/" + cliente.getCedula(),
                null, RespuestaDTO.class);
        Assertions.assertEquals("El cliente no se pudo afiliar: El cliente ya se encuentra afiliado", respuestaDTOAfiliar2.getBody().getMensaje());
    }

    @Test
    void Dado_cedulaCliente_Cuando_clienteExiste_Entonces_cliente() {

        ClienteDTO cliente = new ClienteDTO(1234, "Juan Diego", "juangoru@gmail.com", null);
        ResponseEntity<Void> respuestaAgregarDTO = rest.postForEntity("/cliente/agregar",
                cliente, Void.class);
        assertEquals(HttpStatus.OK, respuestaAgregarDTO.getStatusCode());

        ResponseEntity<RespuestaDTO<List<ClienteDTO>>> respuestaDTO = rest.exchange("/cliente/" + cliente.getCedula(), HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        assertEquals(HttpStatus.OK, respuestaDTO.getStatusCode());
        RespuestaDTO<List<ClienteDTO>> respuesta = respuestaDTO.getBody();
        assertNotNull(respuesta);
        assertEquals("Se encontro a cliente", respuesta.getMensaje());
        List<ClienteDTO> clientes = respuesta.getData();
        assertNotNull(clientes);
        assertEquals(1, clientes.size());
        ClienteDTO clienteDTO = clientes.get(0);
        assertEquals(cliente.getCedula(), clienteDTO.getCedula());
        assertEquals("Juan Diego", clienteDTO.getNombre());
    }

    @Test
    void Dado_cedulaClienteRegistradoPuntos_Cuando_compra_Entonces_dtoExitoso() {
        int valorCompra = 1000;
        ClienteDTO cliente = new ClienteDTO(1234, "Juan Diego", "juangoru@gmail.com", null);
        ResponseEntity<Void> respuestaAgregarDTO = rest.postForEntity("/cliente/agregar",
                cliente, Void.class);
        assertEquals(HttpStatus.OK, respuestaAgregarDTO.getStatusCode());

        ResponseEntity<Void> respuestaAfiliarDTO = rest.postForEntity("/cliente/afiliacion/" + cliente.getCedula(),
                null, Void.class);
        assertEquals(HttpStatus.OK, respuestaAfiliarDTO.getStatusCode());
        ResponseEntity<RespuestaDTO> respuestaCompraDTO = rest.exchange("/cliente/compra/puntos?cedulaCliente=" +
                cliente.getCedula() + "&valorCompra=" + valorCompra, HttpMethod.PUT, null, new ParameterizedTypeReference<>() {
        });
        assertEquals("Compra realizada exitosamente", respuestaCompraDTO.getBody().getMensaje());
    }

    @Test
    void Dado_cedulaClienteNoRegistradoPuntos_Cuando_compra_Entonces_dtoFallido() {
        int valorCompra = 1000;
        ClienteDTO cliente = new ClienteDTO(123456, "Juan Diego", "juangoru@gmail.com", null);
        ResponseEntity<Void> respuestaAgregarDTO = rest.postForEntity("/cliente/agregar",
                cliente, Void.class);
        assertEquals(HttpStatus.OK, respuestaAgregarDTO.getStatusCode());

        ResponseEntity<RespuestaDTO> respuestaCompraDTO = rest.exchange("/cliente/compra/puntos?cedulaCliente=" +
                cliente.getCedula() + "&valorCompra=" + valorCompra, HttpMethod.PUT, null, new ParameterizedTypeReference<>() {
        });
        assertEquals("Compra no realizada: Esta cedula no esta registrada en puntos", respuestaCompraDTO.getBody().getMensaje());
    }

    @Test
    void Dado_cedulaClienteExistenteyBeneficioExistente_Cuando_redime_Entonces_dtoExitoso() {
        int valorCompra = 1000;
        ClienteDTO cliente = new ClienteDTO(12, "Juan Diego", "juangoru@gmail.com", null);
        ResponseEntity<Void> respuestaAgregarDTO = rest.postForEntity("/cliente/agregar",
                cliente, Void.class);
        assertEquals(HttpStatus.OK, respuestaAgregarDTO.getStatusCode());
        ResponseEntity<Void> respuestaAfiliarDTO = rest.postForEntity("/cliente/afiliacion/" + cliente.getCedula(),
                null, Void.class);
        assertEquals(HttpStatus.OK, respuestaAfiliarDTO.getStatusCode());
        ResponseEntity<Void> respuestaCompraDTO = rest.exchange("/cliente/compra/puntos?cedulaCliente=" +
                cliente.getCedula() + "&valorCompra=" + valorCompra, HttpMethod.PUT, null, new ParameterizedTypeReference<>() {
        });
        assertEquals(HttpStatus.OK, respuestaCompraDTO.getStatusCode());
        ResponseEntity<Void> respuestaAgregarBeneficioDTO = rest.postForEntity("/beneficio/agregar",
                beneficioDTO1, Void.class);
        assertEquals(HttpStatus.OK, respuestaAgregarBeneficioDTO.getStatusCode());
        ResponseEntity<RespuestaDTO> respuestaRedimirBeneficioDTO = rest.exchange("/cliente/redimir?cedulaCliente="
                        + cliente.getCedula() + "&idBeneficio=" + beneficioDTO1.getCodigo(), HttpMethod.PUT, null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals("Beneficio se pudo redimir", respuestaRedimirBeneficioDTO.getBody().getMensaje());
    }

    @Test
    void Dado_cedulaClienteExistenteyBeneficioNoExistente_Cuando_redime_Entonces_dtoFallido() {
        int valorCompra = 1000;
        ClienteDTO cliente = new ClienteDTO(12, "Juan Diego", "juangoru@gmail.com", null);
        ResponseEntity<Void> respuestaAgregarDTO = rest.postForEntity("/cliente/agregar",
                cliente, Void.class);
        assertEquals(HttpStatus.OK, respuestaAgregarDTO.getStatusCode());
        ResponseEntity<Void> respuestaAfiliarDTO = rest.postForEntity("/cliente/afiliacion/" + cliente.getCedula(),
                null, Void.class);
        assertEquals(HttpStatus.OK, respuestaAfiliarDTO.getStatusCode());
        ResponseEntity<Void> respuestaCompraDTO = rest.exchange("/cliente/compra/puntos?cedulaCliente=" +
                cliente.getCedula() + "&valorCompra=" + valorCompra, HttpMethod.PUT, null, new ParameterizedTypeReference<>() {
        });
        assertEquals(HttpStatus.OK, respuestaCompraDTO.getStatusCode());
        ResponseEntity<RespuestaDTO> respuestaRedimirBeneficioDTO = rest.exchange("/cliente/redimir?cedulaCliente="
                        + cliente.getCedula() + "&idBeneficio=5", HttpMethod.PUT, null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals("El beneficio no se pudo redimir: El cliente no esta registrado en puntos o no existe el beneficio", respuestaRedimirBeneficioDTO.getBody().getMensaje());
    }

    @Test
    void Dado_cedulaClienteNoExistenteyBeneficioExistente_Cuando_redime_Entonces_dtoFallido() {
        int valorCompra = 1000;
        ResponseEntity<Void> respuestaAgregarBeneficioDTO = rest.postForEntity("/beneficio/agregar",
                beneficioDTO1, Void.class);
        assertEquals(HttpStatus.OK, respuestaAgregarBeneficioDTO.getStatusCode());
        ResponseEntity<RespuestaDTO> respuestaRedimirBeneficioDTO = rest.exchange("/cliente/redimir?cedulaCliente=1"
                        + "&idBeneficio=" + beneficioDTO1.getCodigo(), HttpMethod.PUT, null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals("El beneficio no se pudo redimir: El cliente no esta registrado en puntos o no existe el beneficio", respuestaRedimirBeneficioDTO.getBody().getMensaje());
    }

    @Test
    void Dado_cedulaClienteExistenteyBeneficioExistente_Cuando_redimePeroNoTieneSaldo_Entonces_dtoFallido() {
        int valorCompra = 1000;
        ClienteDTO cliente = new ClienteDTO(1245, "Juan Diego", "juangoru@gmail.com", null);
        ResponseEntity<Void> respuestaAgregarDTO = rest.postForEntity("/cliente/agregar",
                cliente, Void.class);
        assertEquals(HttpStatus.OK, respuestaAgregarDTO.getStatusCode());
        ResponseEntity<Void> respuestaAfiliarDTO = rest.postForEntity("/cliente/afiliacion/" + cliente.getCedula(),
                null, Void.class);
        assertEquals(HttpStatus.OK, respuestaAfiliarDTO.getStatusCode());
        ResponseEntity<Void> respuestaCompraDTO = rest.exchange("/cliente/compra/puntos?cedulaCliente=" +
                cliente.getCedula() + "&valorCompra=" + valorCompra, HttpMethod.PUT, null, new ParameterizedTypeReference<>() {
        });
        assertEquals(HttpStatus.OK, respuestaCompraDTO.getStatusCode());
        ResponseEntity<Void> respuestaAgregarBeneficioDTO = rest.postForEntity("/beneficio/agregar",
                beneficioDTO2, Void.class);
        assertEquals(HttpStatus.OK, respuestaAgregarBeneficioDTO.getStatusCode());
        ResponseEntity<RespuestaDTO> respuestaRedimirBeneficioDTO = rest.exchange("/cliente/redimir?cedulaCliente="
                        + cliente.getCedula() + "&idBeneficio=" + beneficioDTO2.getCodigo(), HttpMethod.PUT, null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals("El beneficio no se pudo redimir: No tiene puntos para ese beneficio", respuestaRedimirBeneficioDTO.getBody().getMensaje());
    }


}
