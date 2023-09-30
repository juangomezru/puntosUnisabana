package co.edu.unisabana.puntosUnisabana.controller;

import co.edu.unisabana.puntosUnisabana.controller.DTO.RespuestaDTO;
import co.edu.unisabana.puntosUnisabana.controller.DTO.TransaccionDTO;
import co.edu.unisabana.puntosUnisabana.logic.TransaccionLogica;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Tag(name = "Transaccion")
public class TransaccionController {

    private TransaccionLogica transaccionLogica;

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
        try {
            return transaccionLogica.obtenerTransaccionesDTO();
        } catch (Exception e) {
            return null;
        }
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
                                                            value = "{\n" +
                                                                    "  \"mensaje\": \"Transacciones encontradas\",\n" +
                                                                    "  \"data\": [\n" +
                                                                    "  {\n" +
                                                                    "    \"idTransaccion\": 0,\n" +
                                                                    "    \"cedula\": 0,\n" +
                                                                    "    \"nombreBeneficio\": \"string\",\n" +
                                                                    "    \"cantidadPuntosGastados\": 0,\n" +
                                                                    "    \"fechaTransaccion\": \"2023-09-30\"\n" +
                                                                    "  }\n" +
                                                                    "]\n" +
                                                                    "}"
                                                    ),
                                                    @ExampleObject(
                                                            name = "ERROR",
                                                            value = "{\n" +
                                                                    "  \"mensaje\": \"No se obtuvieron las trasacciones: No existen transacciones para este usuario\",\n" +
                                                                    "  \"data\": \"null\"\n" +
                                                                    "}"
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
            return new RespuestaDTO<>("Transacciones encontradas", transacciones);

        } catch (NoSuchElementException e) {
            return new RespuestaDTO<>("No se obtuvieron las trasacciones: " + e.getMessage());
        }
    }

}
