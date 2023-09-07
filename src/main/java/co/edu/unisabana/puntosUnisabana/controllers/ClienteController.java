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
    public RespuestaDTO redimirBeneficio(@RequestBody int cedulaCliente,@RequestBody int idBeneficio){
        try {
            clienteLogica.redimirBeneficio(cedulaCliente, idBeneficio);
            return new RespuestaDTO("Beneficio se pudo redimir");
        } catch (IllegalArgumentException e) {
            return new RespuestaDTO("El beneficio no se pudo redimir: " + e.getMessage());
        }
    }
}
