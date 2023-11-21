package co.edu.unisabana.puntosUnisabana.controller;

import co.edu.unisabana.puntosUnisabana.controller.DTO.ClienteDTO;
import co.edu.unisabana.puntosUnisabana.controller.DTO.RespuestaDTO;
import co.edu.unisabana.puntosUnisabana.logic.ClienteLogica;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Slf4j
@Tag(name = "Cliente")
public class ClienteController {
    private final ClienteLogica clienteLogica;


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
                                                            value = """
                                                                    {
                                                                      "mensaje": "Cliente agregado correctamente",
                                                                      "data": "null"
                                                                    }
                                                                    """
                                                    ),
                                                    @ExampleObject(
                                                            name = "ERROR",
                                                            value = """
                                                                    {
                                                                      "mensaje": "El cliente no se pudo agregar Ya se encuentra registrado el cliente",
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
    @PostMapping(path = "/cliente/agregar")
    public RespuestaDTO<String> guardarCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            clienteLogica.guardarCliente(clienteDTO);
            log.info("Se agregó un nuevo cliente con cédula: {}, y correo: {}", clienteDTO.getCedula(), clienteDTO.getEmail());
            return new RespuestaDTO<>("Cliente agregado correctamente");
        } catch (IllegalArgumentException e) {
            log.warn("No se pudo agregar el cliente debido a: {}", e.getMessage());
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
                                                            value = """
                                                                    {
                                                                      "mensaje": "Beneficio se pudo redimir",
                                                                      "data": "null"
                                                                    }
                                                                    """
                                                    ),
                                                    @ExampleObject(
                                                            name = "ERROR_CLIENTE",
                                                            value = """
                                                                    {
                                                                      "mensaje": "El beneficio no se pudo redimir: No tiene puntos para ese beneficio",
                                                                      "data": "null"
                                                                    }
                                                                    """
                                                    ),
                                                    @ExampleObject(
                                                            name = "ERROR_CANJEO",
                                                            value = """
                                                                    {
                                                                      "mensaje": "El beneficio no se pudo redimir: El cliente no esta registrado en puntos o no existe el beneficio",
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
    @PutMapping(path = "/cliente/redimir")
    public RespuestaDTO<String> redimirBeneficio(@RequestParam int cedulaCliente, @RequestParam int idBeneficio) {
        try {
            clienteLogica.redimirBeneficio(cedulaCliente, idBeneficio);
            log.info("Se remidió el beneficio con ID: {} para el cliente con cédula: {}", idBeneficio, cedulaCliente);
            return new RespuestaDTO<>("Beneficio se pudo redimir");
        } catch (IllegalArgumentException | NoSuchElementException e) {
            log.warn("El beneficio no se pudo redimir con ID: {} para el cliente con cédula: {} debido a: {}", idBeneficio, cedulaCliente, e.getMessage());
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
                                                            value = """
                                                                    {
                                                                      "mensaje": "El cliente se afilio correctamente",
                                                                      "data": "null"
                                                                    }
                                                                    """
                                                    ),
                                                    @ExampleObject(
                                                            name = "ERROR_DUPLICADO",
                                                            value = """
                                                                    {
                                                                      "mensaje": "El cliente no se pudo afiliar: El cliente ya se encuentra afiliado",
                                                                      "data": "null"
                                                                    }
                                                                    """
                                                    ),
                                                    @ExampleObject(
                                                            name = "ERROR_NOREGISTRADO",
                                                            value = """
                                                                    {
                                                                      "mensaje": "El cliente no se pudo afiliar: El cliente no se encuentra registrado",
                                                                      "data": "null"
                                                                    }
                                                                    """
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
            log.info("Se ha afiliado a puntos al cliente con cédula: {}", cedula);
            return new RespuestaDTO<>("El cliente se afilio correctamente");

        } catch (IllegalArgumentException | NoSuchElementException e) {
            log.warn("No se pudo afiliar al cliente con cédula: {}, debido a : {}", cedula, e.getMessage());
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
                                                            value = """
                                                                    {
                                                                      "mensaje": "Compra realizada exitosamente",
                                                                      "data": "null"
                                                                    }
                                                                    """
                                                    ),
                                                    @ExampleObject(
                                                            name = "ERROR_NOEXISTE",
                                                            value = """
                                                                    {
                                                                      "mensaje": "Compra no realizada: Esta cedula no esta registrada en puntos",
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
    @PutMapping(path = "/cliente/compra/puntos")
    public RespuestaDTO<String> clientPuntosCompra(@RequestParam int cedulaCliente, @RequestParam int valorCompra) {
        try {
            clienteLogica.acumularPuntos(cedulaCliente, valorCompra);
            log.info("Se ha hecho un compra por valor de ${}, para el cliente con cédula: {}", valorCompra, cedulaCliente);
            return new RespuestaDTO<>("Compra realizada exitosamente");
        } catch (IllegalArgumentException e) {
            log.warn("No se pudo realizar la compra para el cliente con cédula: {}, debido a: {} ", cedulaCliente, e.getMessage());
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
                                                            value = """
                                                                    {
                                                                      "mensaje": "Se encontro a cliente",
                                                                      "data": {
                                                                        "cedula": 0,
                                                                        "nombre": "string",
                                                                        "email": "string",
                                                                        "beneficios": [
                                                                          {
                                                                            "nombreBeneficio": "string",
                                                                            "puntosRequeridos": 0,
                                                                            "codigo": 0
                                                                          }
                                                                        ]
                                                                      }
                                                                    }
                                                                    """
                                                    ),
                                                    @ExampleObject(
                                                            name = "ERROR_NOEXISTE",
                                                            value = """
                                                                    {
                                                                      "mensaje": "No se encontro un cliente",
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
    @GetMapping(path = "/cliente/{cedula}")
    public RespuestaDTO<List<ClienteDTO>> buscarCliente(@PathVariable int cedula) {
        return new RespuestaDTO<>("Se encontro a cliente", clienteLogica.buscarClienteDTO(cedula));
    }
}
