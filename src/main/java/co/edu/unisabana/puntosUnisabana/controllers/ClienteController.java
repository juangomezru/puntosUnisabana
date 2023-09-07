package co.edu.unisabana.puntosUnisabana.controllers;

import co.edu.unisabana.puntosUnisabana.controllers.DTO.ClienteDTO;
import co.edu.unisabana.puntosUnisabana.controllers.DTO.RespuestaDTO;
import co.edu.unisabana.puntosUnisabana.logica.ClienteLogica;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


public class ClienteController {


    private ClienteLogica clienteLogica;

    public ClienteController(ClienteLogica clienteLogica) {this.clienteLogica = clienteLogica;}

    @PostMapping
    public RespuestaDTO guardarCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            clienteLogica.guardarCliente(clienteDTO);
            return new RespuestaDTO("Cliente agregado correctamente");
        } catch (IllegalArgumentException e) {
            return new RespuestaDTO("El cliente no se pudo agregar " + e.getMessage());
        }

    }
}
