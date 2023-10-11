package co.edu.unisabana.puntosUnisabana.logic;

import co.edu.unisabana.puntosUnisabana.model.ClienteModelo;
import co.edu.unisabana.puntosUnisabana.model.PuntosModelo;
import co.edu.unisabana.puntosUnisabana.repository.PuntosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PuntosLogicaTest {


    private PuntosRepository repo;
    private PuntosLogica logica;

    @BeforeEach
    public void setUp() {
        repo = mock(PuntosRepository.class);
        logica = new PuntosLogica(repo);
    }

    @Test
    void Cuando_buscarCliente_Entonces_mostrarPuntos() {
        List<PuntosModelo> puntos = new ArrayList<>();
        puntos.add(new PuntosModelo(1, new ClienteModelo(1234, "Juan", "sssss", null), 100));
        puntos.add(new PuntosModelo(2, new ClienteModelo(5678, "Maria", "ddd", null), 50));

        when(repo.findAll()).thenReturn(puntos);


        List<PuntosModelo> resultado = logica.listaPuntos();

        verify(repo).findAll();

        assertEquals(puntos, resultado);
    }

    @Test
    void Dado_cliente_Cuando_buscarCliente_Entonces_puntos() {
        ClienteModelo cliente1 = new ClienteModelo(1234, "Juan", "sssss", null);
        ClienteModelo cliente2 = new ClienteModelo(5678, "Maria", "ddd", null);
        List<PuntosModelo> puntos = new ArrayList<>();
        puntos.add(new PuntosModelo(1, cliente1, 100));
        puntos.add(new PuntosModelo(2, cliente2, 50));

        when(repo.findAll()).thenReturn(puntos);

        boolean resultado1 = logica.existeClienteEnPuntos(cliente1);

        boolean resultado2 = logica.existeClienteEnPuntos(new ClienteModelo(9999, "Pedro", "aaaa", null));

        verify(repo, times(2)).findAll();

        assertTrue(resultado1);
        assertFalse(resultado2);
    }

    @Test
    void Dado_cedulaCliente_Entonces_buscarAfiliacion() {
        ClienteModelo cliente1 = new ClienteModelo(1234, "Juan", "sssss", null);
        ClienteModelo cliente2 = new ClienteModelo(5678, "Maria", "ddd", null);
        List<PuntosModelo> puntos = new ArrayList<>();
        puntos.add(new PuntosModelo(1, cliente1, 100));
        puntos.add(new PuntosModelo(2, cliente2, 50));

        when(repo.findAll()).thenReturn(puntos);

        int resultado1 = logica.buscarClientePuntos(cliente1);

        int resultado2 = logica.buscarClientePuntos(new ClienteModelo(9999, "Pedro", "aaaa", null));

        verify(repo, times(2)).findAll();

        assertEquals(100, resultado1);
        assertEquals(0, resultado2);
    }

    @Test
    void Dado_cliente_Cuando_redime_Entonces_actualizarPuntos() {
        ClienteModelo cliente1 = new ClienteModelo(1234, "Juan", "sssss", null);
        ClienteModelo cliente2 = new ClienteModelo(5678, "Maria", "ddd", null);
        List<PuntosModelo> puntos = new ArrayList<>();
        puntos.add(new PuntosModelo(1, cliente1, 100));
        puntos.add(new PuntosModelo(2, cliente2, 50));

        when(repo.findAll()).thenReturn(puntos);

        logica.actualizarPuntos(200, cliente1);
        verify(repo).save(new PuntosModelo(1, cliente1, 200));
    }
}
