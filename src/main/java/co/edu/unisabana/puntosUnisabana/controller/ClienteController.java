package co.edu.unisabana.puntosUnisabana.controller;

import co.edu.unisabana.puntosUnisabana.controller.DTO.ClienteDTO;
import co.edu.unisabana.puntosUnisabana.controller.DTO.RespuestaDTO;
import co.edu.unisabana.puntosUnisabana.logic.ClienteLogica;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class ClienteController {
    private ClienteLogica clienteLogica;


    public ClienteController(ClienteLogica clienteLogica) {
        this.clienteLogica = clienteLogica;
    }

    @PostMapping(path = "/cliente/agregar")
    public RespuestaDTO<String> guardarCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            clienteLogica.guardarCliente(clienteDTO);
            return new RespuestaDTO<>("Cliente agregado correctamente");
        } catch (IllegalArgumentException e) {
            return new RespuestaDTO<>("El cliente no se pudo agregar " + e.getMessage());
        }

    }

    @PutMapping(path = "/cliente/redimir")
    public RespuestaDTO<String> redimirBeneficio(@RequestParam int cedulaCliente, @RequestParam int idBeneficio) {
        try {
            clienteLogica.redimirBeneficio(cedulaCliente, idBeneficio);
            return new RespuestaDTO<>("Beneficio se pudo redimir");
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return new RespuestaDTO<>("El beneficio no se pudo redimir: " + e.getMessage());
        }
    }

    @PostMapping(path = "/cliente/afiliacion/{cedula}")
    public RespuestaDTO<String> afiliarCliente(@PathVariable int cedula) {

        try {
            clienteLogica.existeClienteEnPuntos(cedula);
            return new RespuestaDTO<>("El cliente se afilio correctamente");

        } catch (IllegalArgumentException | NoSuchElementException e) {
            return new RespuestaDTO<>("El cliente no se pudo afiliar: " + e.getMessage());
        }
    }

    @PutMapping(path = "/cliente/compra/puntos")
    public RespuestaDTO<String> clientPuntosCompra(@RequestParam int cedulaCliente, @RequestParam int valorCompra) {
        try {
            clienteLogica.acumularPuntos(cedulaCliente, valorCompra);
            return new RespuestaDTO<>("Compra realizada exitosamente");
        } catch (IllegalArgumentException e) {
            return new RespuestaDTO<>("Compra no realizada: " + e.getMessage());
        }
    }

    @GetMapping(path = "/cliente/{cedula}")
    public RespuestaDTO<List<ClienteDTO>> buscarCliente(@PathVariable int cedula) {
        try {
            return new RespuestaDTO<>("Se encontro a cliente", clienteLogica.buscarClienteDTO(cedula));
        } catch (Exception e) {
            return new RespuestaDTO<>("No se encontro un cliente");
        }
    }
}
