package co.edu.unisabana.puntosUnisabana.logic;

import co.edu.unisabana.puntosUnisabana.controller.DTO.BeneficioDTO;
import co.edu.unisabana.puntosUnisabana.model.BeneficioModelo;
import co.edu.unisabana.puntosUnisabana.model.ClienteModelo;
import co.edu.unisabana.puntosUnisabana.repository.BeneficioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeneficioLogicaTest {
    private BeneficioRepository repo;
    private BeneficiosLogica logica;

    @BeforeEach
    public void setUp() {
        repo = mock(BeneficioRepository.class);

        logica = new BeneficiosLogica(repo);
    }

    @Test
    void Cuando_buscar_Entonces_mostrarListaBeneficios() {
        List<BeneficioModelo> beneficios = new ArrayList<>();
        List<ClienteModelo> clientes = new ArrayList<>();
        List<ClienteModelo> clientes2 = new ArrayList<>();
        ClienteModelo cliente1 = new ClienteModelo(1234, "Juan", "sssss", null);
        ClienteModelo cliente2 = new ClienteModelo(5678, "Maria", "ddd", null);
        clientes.add(cliente1);
        clientes2.add(cliente2);

        beneficios.add(new BeneficioModelo(1, "Beneficio 1", 100, clientes));
        beneficios.add(new BeneficioModelo(2, "Beneficio 2", 50, clientes2));

        when(repo.findAll()).thenReturn(beneficios);

        List<BeneficioDTO> resultado = logica.listaBeneficios();

        verify(repo).findAll();

        List<BeneficioDTO> esperado = new ArrayList<>();
        esperado.add(new BeneficioDTO("Beneficio 1", 100, 1));
        esperado.add(new BeneficioDTO("Beneficio 2", 50, 2));
        assertEquals(esperado, resultado);
    }

    @Test
    void Dado_cliente_Entonces_buscarBeneficio() {
        List<ClienteModelo> clientes = new ArrayList<>();
        ClienteModelo cliente1 = new ClienteModelo(1234, "Juan", "sssss", null);

        clientes.add(cliente1);

        BeneficioModelo beneficio1 = new BeneficioModelo(1, "Beneficio 1", 100, clientes);

        when(repo.findById(1)).thenReturn(Optional.of(beneficio1));
        when(repo.findById(3)).thenReturn(Optional.empty());

        BeneficioModelo resultado1 = logica.buscarBeneficio(1);

        BeneficioModelo resultado2 = logica.buscarBeneficio(3);

        verify(repo, times(2)).findById(anyInt());

        assertEquals(beneficio1, resultado1);
        assertNull(resultado2);
    }

    @Test
    void Dado_cliente_Entonces_mostrarPuntosBeneficio() {
        List<ClienteModelo> clientes = new ArrayList<>();
        ClienteModelo cliente1 = new ClienteModelo(1234, "Juan", "sssss", null);
        clientes.add(cliente1);


        BeneficioModelo beneficio1 = new BeneficioModelo(1, "Beneficio 1", 100, clientes);


        when(repo.findById(1)).thenReturn(Optional.of(beneficio1));


        int resultado1 = logica.obtenerPuntosBeneficio(1);


        verify(repo, times(1)).findById(anyInt());

        assertEquals(100, resultado1);
    }

    @Test
    void Cuando_guardar_Entonces_guardarBeneficio() {

        BeneficioDTO beneficioDTO = new BeneficioDTO("Beneficio 1", 100, 1);

        when(repo.save(any(BeneficioModelo.class))).thenAnswer(invocation -> {
            BeneficioModelo beneficioModelo = invocation.getArgument(0);
            beneficioModelo.setId(1);
            return beneficioModelo;
        });

        logica.guardarBeneficio(beneficioDTO);

        verify(repo).save(new BeneficioModelo(1, "Beneficio 1", 100, null));
    }
}

