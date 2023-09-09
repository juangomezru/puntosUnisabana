package co.edu.unisabana.puntosUnisabana.controllers;

import co.edu.unisabana.puntosUnisabana.controllers.DTO.RespuestaDTO;
import co.edu.unisabana.puntosUnisabana.controllers.DTO.TransaccionDTO;
import co.edu.unisabana.puntosUnisabana.logica.TransaccionLogica;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class TransaccionController {

    private TransaccionLogica transaccionLogica;

    public TransaccionController(TransaccionLogica transaccionLogica) {
        this.transaccionLogica = transaccionLogica;
    }

    @GetMapping(path = "/transacciones")
    public List<TransaccionDTO> verTransacciones() {
        try {
            return transaccionLogica.obtenerTransaccionesDTO();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(path = "/transacciones/clientes/buscar")
    public RespuestaDTO<List<TransaccionDTO>> verTransaccionesPorCliente(@RequestParam int cedula) {
        try {
            List<TransaccionDTO> transacciones = transaccionLogica.consultarTransaccion(cedula);
            return new RespuestaDTO<>("Transacciones encontradas", transacciones);

        } catch (NoSuchElementException e) {
            return new RespuestaDTO<>("No se obtuvieron las trasacciones: " + e.getMessage());
        }
    }

}
