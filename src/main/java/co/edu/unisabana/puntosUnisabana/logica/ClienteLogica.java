package co.edu.unisabana.puntosUnisabana.logica;

import co.edu.unisabana.puntosUnisabana.controllers.DTO.ClienteDTO;
import co.edu.unisabana.puntosUnisabana.modelo.ClienteModelo;
import co.edu.unisabana.puntosUnisabana.repository.ClienteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

public class ClienteLogica {

    private final ClienteRepository clienteRepository;


    public ClienteLogica(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    public void guardarCliente(ClienteDTO clienteDTO){
        ClienteModelo cliente= new ClienteModelo();
        cliente.setCedula(clienteDTO.getCedula());
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setBeneficios(null);

    }




}
