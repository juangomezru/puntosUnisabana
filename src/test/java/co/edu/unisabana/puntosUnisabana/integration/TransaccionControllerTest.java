package co.edu.unisabana.puntosUnisabana.integration;

import co.edu.unisabana.puntosUnisabana.controller.DTO.BeneficioDTO;
import co.edu.unisabana.puntosUnisabana.controller.DTO.ClienteDTO;
import co.edu.unisabana.puntosUnisabana.controller.DTO.RespuestaDTO;
import co.edu.unisabana.puntosUnisabana.controller.DTO.TransaccionDTO;
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
public class TransaccionControllerTest {
    @Autowired
    TestRestTemplate rest;
    private BeneficioDTO beneficioDTO1 = BeneficioControllerTest.nuevoBeneficio1;

    @Test
    void Dado_cliente_Cuando_existen2Transacciones_Entonces_todasLasTransacciones() {
        int valorCompra = 10000;
        ClienteDTO cliente = new ClienteDTO(155, "Juan Diego", "juangoru@gmail.com", null);
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
        ResponseEntity<RespuestaDTO> respuestaRedimirBeneficioDTO1 = rest.exchange("/cliente/redimir?cedulaCliente="
                        + cliente.getCedula() + "&idBeneficio=" + beneficioDTO1.getCodigo(), HttpMethod.PUT, null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.OK, respuestaRedimirBeneficioDTO1.getStatusCode());
        ResponseEntity<RespuestaDTO> respuestaRedimirBeneficioDTO2 = rest.exchange("/cliente/redimir?cedulaCliente="
                        + cliente.getCedula() + "&idBeneficio=" + beneficioDTO1.getCodigo(), HttpMethod.PUT, null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.OK, respuestaRedimirBeneficioDTO2.getStatusCode());
        ResponseEntity<List<TransaccionDTO>> respuestaTraerTransaccionesDTO = rest.exchange("/transacciones",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.OK, respuestaTraerTransaccionesDTO.getStatusCode());
        List<TransaccionDTO> respuesta = respuestaTraerTransaccionesDTO.getBody();
        assertNotNull(respuesta);
        assertEquals(3, respuesta.size());
        TransaccionDTO transaccion1 = respuesta.get(1);
        assertEquals(cliente.getCedula(), (transaccion1.getCedula()));
        assertEquals(beneficioDTO1.getNombreBeneficio(), transaccion1.getNombreBeneficio());
        TransaccionDTO transaccion2 = respuesta.get(2);
        assertEquals(cliente.getCedula(), (transaccion2.getCedula()));
        assertEquals(beneficioDTO1.getNombreBeneficio(), transaccion2.getNombreBeneficio());
    }

    @Test
    void Dado_cliente_Cuando_existenTransacciones_Entonces_Transacciones() {
        int valorCompra = 1000;
        ClienteDTO cliente = new ClienteDTO(10, "Juan Diego", "juangoru@gmail.com", null);
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
        assertEquals(HttpStatus.OK, respuestaRedimirBeneficioDTO.getStatusCode());
        ResponseEntity<RespuestaDTO<List<TransaccionDTO>>> respuestaTraerTransaccionesDTO = rest.exchange("/transacciones/clientes/buscar?cedula=" + cliente.getCedula(),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.OK, respuestaTraerTransaccionesDTO.getStatusCode());
        RespuestaDTO<List<TransaccionDTO>> respuesta = respuestaTraerTransaccionesDTO.getBody();
        assertNotNull(respuesta);
        assertEquals("Transacciones encontradas", respuesta.getMensaje());
        List<TransaccionDTO> transacciones = respuesta.getData();
        assertNotNull(transacciones);
        assertEquals(1, transacciones.size());
        TransaccionDTO transaccion = transacciones.get(0);
        assertEquals(cliente.getCedula(), (transaccion.getCedula()));
        assertEquals(beneficioDTO1.getNombreBeneficio(), transaccion.getNombreBeneficio());
    }

    @Test
    void Dado_cliente_Cuando_noExistenTransacciones_Entonces_Transacciones() {
        int valorCompra = 1000;
        ClienteDTO cliente = new ClienteDTO(16, "Juan Diego", "juangoru@gmail.com", null);
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
        ResponseEntity<RespuestaDTO<List<TransaccionDTO>>> respuestaTraerTransaccionesDTO = rest.exchange("/transacciones/clientes/buscar?cedula=" + cliente.getCedula(),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.OK, respuestaTraerTransaccionesDTO.getStatusCode());
        RespuestaDTO<List<TransaccionDTO>> respuesta = respuestaTraerTransaccionesDTO.getBody();
        assertNotNull(respuesta);
        assertEquals("No se obtuvieron las transacciones: No existen transacciones para este usuario", respuesta.getMensaje());
    }
}
