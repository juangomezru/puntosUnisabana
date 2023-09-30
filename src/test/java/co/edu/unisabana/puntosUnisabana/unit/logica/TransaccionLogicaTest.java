package co.edu.unisabana.puntosUnisabana.unit.logica;

import co.edu.unisabana.puntosUnisabana.controller.DTO.ClienteDTO;
import co.edu.unisabana.puntosUnisabana.controller.DTO.TransaccionDTO;
import co.edu.unisabana.puntosUnisabana.logic.TransaccionLogica;
import co.edu.unisabana.puntosUnisabana.model.ClienteModelo;
import co.edu.unisabana.puntosUnisabana.model.TransaccionModelo;
import co.edu.unisabana.puntosUnisabana.repository.TransaccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransaccionLogicaTest {
    private TransaccionRepository repo;
    private TransaccionLogica logica;

    @BeforeEach
    public void setUp() {
        repo = mock(TransaccionRepository.class);

        logica = new TransaccionLogica(repo);
    }

    @Test
    public void Entonces_todasTransacciones() {
        List<TransaccionModelo> transacciones = new ArrayList<>();
        transacciones.add(new TransaccionModelo(1, "Beneficio 1", new ClienteModelo(1234, "Juan","sssss",null), 100, LocalDate.now()));
        transacciones.add(new TransaccionModelo(2,"Beneficio 2", new ClienteModelo(5678, "Maria","ddd",null),  50, LocalDate.now()));
        when(repo.findAll()).thenReturn(transacciones);
        List<TransaccionModelo> resultado = logica.obtenerTransacciones();
        verify(repo).findAll();
        assertEquals(transacciones, resultado);
    }

    @Test
    public void Entonces_transaccionesDTO() {

        List<TransaccionModelo> transacciones = new ArrayList<>();
        transacciones.add(new TransaccionModelo(1, "Beneficio 1", new ClienteModelo(1234, "Juan","sssss",null), 100, LocalDate.now()));
        transacciones.add(new TransaccionModelo(2,"Beneficio 2", new ClienteModelo(5678, "Maria","ddd",null),  50, LocalDate.now()));

        when(repo.findAll()).thenReturn(transacciones);

        List<TransaccionDTO> resultado = logica.obtenerTransaccionesDTO();

        verify(repo).findAll();

        List<TransaccionDTO> esperado = new ArrayList<>();
        esperado.add(new TransaccionDTO(1, 1234, "Beneficio 1", 100, LocalDate.now()));
        esperado.add(new TransaccionDTO(2, 5678, "Beneficio 2", 50, LocalDate.now()));
        assertEquals(esperado, resultado);
    }

    @Test
    public void Dado_cedulaCliente_Entonces_consultarTransaccion() {
        List<TransaccionModelo> transacciones = new ArrayList<>();
        transacciones.add(new TransaccionModelo(1, "Beneficio 1", new ClienteModelo(1234, "Juan","sssss",null), 100, LocalDate.now()));
        transacciones.add(new TransaccionModelo(2,"Beneficio 2", new ClienteModelo(5678, "Maria","ddd",null),  50, LocalDate.now()));

        when(repo.findAll()).thenReturn(transacciones);

        List<TransaccionDTO> resultado = logica.consultarTransaccion(1234);

        verify(repo).findAll();

        List<TransaccionDTO> esperado = new ArrayList<>();
        esperado.add(new TransaccionDTO(1, 1234, "Beneficio 1", 100, LocalDate.now()));
        assertEquals(esperado, resultado);
        assertThrows(NoSuchElementException.class, () -> logica.consultarTransaccion(9999));
    }
}
