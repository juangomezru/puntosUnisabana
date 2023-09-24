package co.edu.unisabana.puntosUnisabana.unit.logica;

import co.edu.unisabana.puntosUnisabana.controller.DTO.ClienteDTO;
import co.edu.unisabana.puntosUnisabana.logic.ClienteLogica;
import co.edu.unisabana.puntosUnisabana.model.ClienteModelo;
import co.edu.unisabana.puntosUnisabana.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ClienteLogicaTest {

    @InjectMocks
    ClienteLogica clienteLogica;
    @Mock
    ClienteRepository clienteRepository;

    @Test
    void Dado_cliente_Cuando_guarde() {
        ClienteDTO dto = new ClienteDTO();
        ClienteModelo clienteModelo = new ClienteModelo();
        dto.setCedula(666);
        dto.setNombre("jean");
        dto.setEmail("jean@gmail.com");
        clienteLogica.guardarCliente(dto);
        Mockito.verify(clienteRepository);
    }

}
