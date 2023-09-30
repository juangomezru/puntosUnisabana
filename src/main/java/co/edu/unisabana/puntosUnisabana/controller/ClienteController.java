package co.edu.unisabana.puntosUnisabana.controller;

import co.edu.unisabana.puntosUnisabana.controller.DTO.ClienteDTO;
import co.edu.unisabana.puntosUnisabana.controller.DTO.RespuestaDTO;
import co.edu.unisabana.puntosUnisabana.logic.ClienteLogica;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Tag(name = "Cliente")
public class ClienteController {
    private ClienteLogica clienteLogica;


    public ClienteController(ClienteLogica clienteLogica) {
        this.clienteLogica = clienteLogica;
    }

    @Operation(
            description = "Se agrega un cliente a la base de datos, se toman los valores por medio de un JSON",
            summary = "Agregar un cliente",
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
                                                                    "  \"mensaje\": \"Cliente agregado correctamente\",\n" +
                                                                    "  \"data\": \"null\"\n" +
                                                                    "}"
                                                    ),
                                                    @ExampleObject(
                                                            name = "ERROR",
                                                            value = "{\n" +
                                                                    "  \"mensaje\": \"El cliente no se pudo agregar Ya se encuentra registrado el cliente\",\n" +
                                                                    "  \"data\": \"null\"\n" +
                                                                    "}"
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @PostMapping(path = "/cliente/agregar")
    public RespuestaDTO<String> guardarCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            clienteLogica.guardarCliente(clienteDTO);
            return new RespuestaDTO<>("Cliente agregado correctamente");
        } catch (IllegalArgumentException e) {
            return new RespuestaDTO<>("El cliente no se pudo agregar " + e.getMessage());
        }

    }

    @Operation(
            description = "El cliente redime un beneficio si tiene los puntos necesarios para ese beneficio",
            summary = "El cliente puede redimir un beneficio",
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
                                                                    "  \"mensaje\": \"Beneficio se pudo redimir\",\n" +
                                                                    "  \"data\": \"null\"\n" +
                                                                    "}"
                                                    ),
                                                    @ExampleObject(
                                                            name = "ERROR_CLIENTE",
                                                            value = "{\n" +
                                                                    "  \"mensaje\": \"El beneficio no se pudo redimir: No tiene puntos para ese beneficio\",\n" +
                                                                    "  \"data\": \"null\"\n" +
                                                                    "}"
                                                    ),
                                                    @ExampleObject(
                                                            name = "ERROR_CANJEO",
                                                            value = "{\n" +
                                                                    "  \"mensaje\": \"El beneficio no se pudo redimir: El cliente no esta registrado en puntos o no existe el beneficio\",\n" +
                                                                    "  \"data\": \"null\"\n" +
                                                                    "}"
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @PutMapping(path = "/cliente/redimir")
    public RespuestaDTO<String> redimirBeneficio(@RequestParam int cedulaCliente, @RequestParam int idBeneficio) {
        try {
            clienteLogica.redimirBeneficio(cedulaCliente, idBeneficio);
            return new RespuestaDTO<>("Beneficio se pudo redimir");
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return new RespuestaDTO<>("El beneficio no se pudo redimir: " + e.getMessage());
        }
    }

    @Operation(
            description = "Se afilia un cliente por medio de su cedula, para que obtenga puntos",
            summary = "Afiliar un cliente",
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
                                                                    "  \"mensaje\": \"El cliente se afilio correctamente\",\n" +
                                                                    "  \"data\": \"null\"\n" +
                                                                    "}"
                                                    ),
                                                    @ExampleObject(
                                                            name = "ERROR_DUPLICADO",
                                                            value = "{\n" +
                                                                    "  \"mensaje\": \"El cliente no se pudo afiliar: El cliente ya se encuentra afiliado\",\n" +
                                                                    "  \"data\": \"null\"\n" +
                                                                    "}"
                                                    ),
                                                    @ExampleObject(
                                                            name = "ERROR_NOREGISTRADO",
                                                            value = "{\n" +
                                                                    "  \"mensaje\": \"El cliente no se pudo afiliar: El cliente no se encuentra registrado\",\n" +
                                                                    "  \"data\": \"null\"\n" +
                                                                    "}"
                                                    ),
                                            }
                                    )
                            }
                    )
            }
    )
    @PostMapping(path = "/cliente/afiliacion/{cedula}")
    public RespuestaDTO<String> afiliarCliente(@PathVariable int cedula) {
        try {
            clienteLogica.existeClienteEnPuntos(cedula);
            return new RespuestaDTO<>("El cliente se afilio correctamente");

        } catch (IllegalArgumentException | NoSuchElementException e) {
            return new RespuestaDTO<>("El cliente no se pudo afiliar: " + e.getMessage());
        }
    }

    @Operation(
            description = "El cliente hace una compra y de acuerdo al valor de la compra obtiene puntos",
            summary = "El cliente hace una compra",
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
                                                                    "  \"mensaje\": \"Compra realizada exitosamente\",\n" +
                                                                    "  \"data\": \"null\"\n" +
                                                                    "}"
                                                    ),
                                                    @ExampleObject(
                                                            name = "ERROR_NOEXISTE",
                                                            value = "{\n" +
                                                                    "  \"mensaje\": \"Compra no realizada: Esta cedula no esta registrada en puntos\",\n" +
                                                                    "  \"data\": \"null\"\n" +
                                                                    "}"
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @PutMapping(path = "/cliente/compra/puntos")
    public RespuestaDTO<String> clientPuntosCompra(@RequestParam int cedulaCliente, @RequestParam int valorCompra) {
        try {
            clienteLogica.acumularPuntos(cedulaCliente, valorCompra);
            return new RespuestaDTO<>("Compra realizada exitosamente");
        } catch (IllegalArgumentException e) {
            return new RespuestaDTO<>("Compra no realizada: " + e.getMessage());
        }
    }

    @Operation(
            description = "Se hace la busqueda del cliente en la base de datos con su cedula",
            summary = "Se busca el cliente",
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
                                                                    "  \"mensaje\": \"Se encontro a cliente\",\n" +
                                                                    "  \"data\": {\n" +
                                                                    "  \"cedula\": 0,\n" +
                                                                    "  \"nombre\": \"string\",\n" +
                                                                    "  \"email\": \"string\",\n" +
                                                                    "  \"beneficios\": [\n" +
                                                                    "    {\n" +
                                                                    "      \"nombreBeneficio\": \"string\",\n" +
                                                                    "      \"puntosRequeridos\": 0,\n" +
                                                                    "      \"codigo\": 0\n" +
                                                                    "    }\n" +
                                                                    "  ]\n" +
                                                                    "}\n" +
                                                                    "}"
                                                    ),
                                                    @ExampleObject(
                                                            name = "ERROR_NOEXISTE",
                                                            value = "{\n" +
                                                                    "  \"mensaje\": \"No se encontro un cliente\",\n" +
                                                                    "  \"data\": \"null\"\n" +
                                                                    "}"
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @GetMapping(path = "/cliente/{cedula}")
    public RespuestaDTO<List<ClienteDTO>> buscarCliente(@PathVariable int cedula) {
        try {
            return new RespuestaDTO<>("Se encontro a cliente", clienteLogica.buscarClienteDTO(cedula));
        } catch (Exception e) {
            return new RespuestaDTO<>("No se encontro un cliente");
        }
    }
}
