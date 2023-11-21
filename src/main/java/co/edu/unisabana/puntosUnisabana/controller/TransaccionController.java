package co.edu.unisabana.puntosUnisabana.controller;

import co.edu.unisabana.puntosUnisabana.controller.DTO.RespuestaDTO;
import co.edu.unisabana.puntosUnisabana.controller.DTO.TransaccionDTO;
import co.edu.unisabana.puntosUnisabana.logic.TransaccionLogica;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Slf4j
@Tag(name = "Transaccion")
public class TransaccionController {

    private final TransaccionLogica transaccionLogica;

    public TransaccionController(TransaccionLogica transaccionLogica) {
        this.transaccionLogica = transaccionLogica;
    }

    @Operation(
            description = "Se obtienen todas las transacciones hasta el momento",
            summary = "Ver transacciones",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping(path = "/transacciones")
    public List<TransaccionDTO> verTransacciones() {
        return transaccionLogica.obtenerTransaccionesDTO();
    }

    @Operation(
            description = "Se obtinen las transacciones hechas por el cliente",
            summary = "Ver transacciones del cliente",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = {
                                                    @ExampleObject(
                                                            name = "OK",
                                                            value = """
                                                                    {
                                                                      "mensaje": "Transacciones encontradas",
                                                                      "data": [
                                                                      {
                                                                        "idTransaccion": 0,
                                                                        "cedula": 0,
                                                                        "nombreBeneficio": "string",
                                                                        "cantidadPuntosGastados": 0,
                                                                        "fechaTransaccion": "2023-09-30"
                                                                      }
                                                                      ]
                                                                    }
                                                                    """
                                                    ),
                                                    @ExampleObject(
                                                            name = "ERROR",
                                                            value = """
                                                                    {
                                                                        "mensaje": "No se obtuvieron las trasacciones: No existen transacciones para este usuario",
                                                                        "data": "null"
                                                                    }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @GetMapping(path = "/transacciones/clientes/buscar")
    public RespuestaDTO<List<TransaccionDTO>> verTransaccionesPorCliente(@RequestParam int cedula) {
        try {
            List<TransaccionDTO> transacciones = transaccionLogica.consultarTransaccion(cedula);
            log.info("Se hallaron las transacciones para el cliente con cédula: {}, las mismas son: {}", cedula, transacciones);
            return new RespuestaDTO<>("Transacciones encontradas", transacciones);

        } catch (NoSuchElementException e) {
            log.warn("No se hallaron transacciones para el cliente con cédula: {}, debido a: {}", cedula, e.getMessage());
            return new RespuestaDTO<>("No se obtuvieron las transacciones: " + e.getMessage());
        }
    }

}
