package co.edu.unisabana.puntosUnisabana.controllers;

import co.edu.unisabana.puntosUnisabana.controllers.DTO.ClienteDTO;
import co.edu.unisabana.puntosUnisabana.controllers.DTO.RespuestaDTO;
import co.edu.unisabana.puntosUnisabana.logica.ClienteLogica;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClienteController {
    private ClienteLogica clienteLogica;

    public ClienteController(ClienteLogica clienteLogica) {this.clienteLogica = clienteLogica;}

    @PostMapping(path = "/cliente/agregar")
    public RespuestaDTO guardarCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            clienteLogica.guardarCliente(clienteDTO);
            return new RespuestaDTO("Cliente agregado correctamente");
        } catch (IllegalArgumentException e) {
            return new RespuestaDTO("El cliente no se pudo agregar " + e.getMessage());
        }

    }
    @PutMapping(path = "/cliente/redimir")
    public RespuestaDTO redimirBeneficio(@RequestParam int cedulaCliente,@RequestParam int idBeneficio){
        try {
            clienteLogica.redimirBeneficio(cedulaCliente, idBeneficio);
            return new RespuestaDTO("Beneficio se pudo redimir");
        } catch (IllegalArgumentException e) {
            return new RespuestaDTO("El beneficio no se pudo redimir: " + e.getMessage());
        }
    }
    @PostMapping(path = "/cliente/afiliacion/{cedula}")
    public RespuestaDTO afiliarCliente(@PathVariable int cedula){
        try {
            clienteLogica.afiliarCliente(cedula);
            return new RespuestaDTO("El cliente se afilio exitosamente");
        } catch (IllegalArgumentException e) {
            return new RespuestaDTO("El cliente no se pudo afiliar: " + e.getMessage());
        }
    }
    @PutMapping(path = "/cliente/compra/puntos")
    public RespuestaDTO clientPuntosCompra(@RequestParam int cedulaCliente,@RequestParam int valorCompra){
        try {
            clienteLogica.acumularPuntos(cedulaCliente, valorCompra);
            return new RespuestaDTO("Compra realizada exitosamente");
        } catch (IllegalArgumentException e) {
            return new RespuestaDTO("Compra no realizada: " + e.getMessage());
        }
    }
}
