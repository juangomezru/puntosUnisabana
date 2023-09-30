package co.edu.unisabana.puntosUnisabana.unit.logica;

import co.edu.unisabana.puntosUnisabana.controller.DTO.BeneficioDTO;
import co.edu.unisabana.puntosUnisabana.controller.DTO.ClienteDTO;
import co.edu.unisabana.puntosUnisabana.logic.BeneficiosLogica;
import co.edu.unisabana.puntosUnisabana.logic.ClienteLogica;
import co.edu.unisabana.puntosUnisabana.logic.PuntosLogica;
import co.edu.unisabana.puntosUnisabana.model.BeneficioModelo;
import co.edu.unisabana.puntosUnisabana.model.ClienteModelo;
import co.edu.unisabana.puntosUnisabana.model.PuntosModelo;
import co.edu.unisabana.puntosUnisabana.repository.BeneficioRepository;
import co.edu.unisabana.puntosUnisabana.repository.ClienteRepository;
import co.edu.unisabana.puntosUnisabana.repository.PuntosRepository;
import co.edu.unisabana.puntosUnisabana.service.IGestionClienteTransaccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteLogicaTest {

    @Mock
    private ClienteRepository clienteRepo;
    @Mock
    private PuntosLogica puntosLogica;
    @Mock
    private PuntosRepository puntosRepo;
    @Mock
    private BeneficiosLogica beneficiosLogica;
    @Mock
    private BeneficioRepository beneficioRepo;
    @Mock
    private IGestionClienteTransaccion gestionClienteTransaccion;
    @Mock
    private ClienteLogica logica;


    @BeforeEach
    public void setUp() {
        clienteRepo = mock(ClienteRepository.class);
        puntosLogica = mock(PuntosLogica.class);
        puntosRepo = mock(PuntosRepository.class);
        beneficiosLogica = mock(BeneficiosLogica.class);
        beneficioRepo = mock(BeneficioRepository.class);
        gestionClienteTransaccion = mock(IGestionClienteTransaccion.class);

        logica = new ClienteLogica(clienteRepo, puntosLogica, puntosRepo, beneficiosLogica, beneficioRepo, gestionClienteTransaccion);
    }

    @Test
    public void Dado_cedulaCliente_Entonces_buscarCliente() {

        ClienteModelo cliente1 = new ClienteModelo(1234, "Juan", "juan@example.com", new ArrayList<>());

        when(clienteRepo.findById(1234)).thenReturn(Optional.of(cliente1));
        when(clienteRepo.findById(9999)).thenReturn(Optional.empty());


        ClienteModelo resultado1 = logica.buscarCliente(1234);


        ClienteModelo resultado2 = logica.buscarCliente(9999);


        verify(clienteRepo, times(2)).findById(anyInt());

        assertEquals(cliente1, resultado1);
        assertNull(resultado2);
    }

    @Test
    public void testBuscarClienteDTO() {

        ClienteModelo cliente1 = new ClienteModelo(1234, "Juan", "juan@example.com", new ArrayList<>());
        List<ClienteModelo> clientes= new ArrayList<>();
        clientes.add(cliente1);
        cliente1.getBeneficios().add(new BeneficioModelo(1,"Beneficio 1", 100, clientes));



        when(clienteRepo.findById(1234)).thenReturn(Optional.of(cliente1));
        when(clienteRepo.findById(9999)).thenReturn(Optional.empty());

        List<ClienteDTO> resultado1 = logica.buscarClienteDTO(1234);

        List<ClienteDTO> resultado2 = logica.buscarClienteDTO(9999);

        verify(clienteRepo, times(2)).findById(anyInt());

        List<ClienteDTO> esperado1 = new ArrayList<>();
        esperado1.add(new ClienteDTO(1234, "Juan", "juan@example.com", new ArrayList<>()));
        esperado1.get(0).getBeneficios().add(new BeneficioDTO("Beneficio 1", 100, 1));
        assertEquals(esperado1, resultado1);
        assertTrue(resultado2.isEmpty());
    }

    @Test
    public void Dado_clienteNoExistente_entonces_Guardar() {

        ClienteDTO clienteDTO = new ClienteDTO(1234, "Juan", "juan@example.com", null);

        when(clienteRepo.findById(1234)).thenReturn(Optional.empty());

        logica.guardarCliente(clienteDTO);

        verify(clienteRepo).findById(1234);
        verify(clienteRepo).save(any(ClienteModelo.class));
    }

    @Test
    public void Dado_clienteExistente_entonces_DenegarGuardado() {
        ClienteDTO clienteDTO = new ClienteDTO(1234, "Juan", "juan@example.com", null);

        when(clienteRepo.findById(1234)).thenReturn(Optional.of(new ClienteModelo()));

        assertThrows(IllegalArgumentException.class, () -> logica.guardarCliente(clienteDTO));

        verify(clienteRepo).findById(1234);

        verify(clienteRepo, never()).save(any(ClienteModelo.class));
    }

    @Test
    public void Dado_() {
        BeneficioModelo beneficioModelo = new BeneficioModelo(1, "Beneficio 1", 100, null);
        List<BeneficioModelo> beneficios= new ArrayList<>();
        beneficios.add(beneficioModelo);
        ClienteModelo clienteModelo = new ClienteModelo(1234, "Juan", "juan@example.com", beneficios);

        when(clienteRepo.findById(1234)).thenReturn(Optional.of(clienteModelo));
        when(beneficioRepo.findById(1)).thenReturn(Optional.of(beneficioModelo));
        when(clienteRepo.save(any(ClienteModelo.class))).thenReturn(clienteModelo);

        logica.guardarBeneficios(1234, 1);

        verify(clienteRepo).findById(1234);
        verify(beneficioRepo).findById(1);
        verify(clienteRepo).save(clienteModelo);

        assertEquals(2, clienteModelo.getBeneficios().size());
        assertEquals(beneficioModelo, clienteModelo.getBeneficios().get(0));
    }

    @Test
    public void Dado_cliente_Entonces_afiliar()
    {
        ClienteModelo clienteModelo = new ClienteModelo(1234, "Juan", "juan@example.com", null);

        when(clienteRepo.findById(1234)).thenReturn(Optional.of(clienteModelo));


        logica.afiliarCliente(1234);

        verify(clienteRepo).findById(1234);

        ArgumentCaptor<PuntosModelo> puntosCaptor = ArgumentCaptor.forClass(PuntosModelo.class);
        verify(puntosRepo).save(puntosCaptor.capture());
        assertEquals(clienteModelo, puntosCaptor.getValue().getCliente());
        assertEquals(0, puntosCaptor.getValue().getPuntos());
    }

    @Test
    public void Dado_clienteYBeneficio_Entonces_acumularPuntos() {

        int cedulaCliente = 1234;
        int puntosAcumulados = 50;
        ClienteModelo cliente = new ClienteModelo(cedulaCliente, "Juan", "juan@example.com", new ArrayList<>());

        when(clienteRepo.findById(cedulaCliente)).thenReturn(Optional.of(cliente));
        when(puntosLogica.existeClienteEnPuntos(cliente)).thenReturn(true);
        when(puntosLogica.buscarClientePuntos(cliente)).thenReturn(100);

        logica.acumularPuntos(cedulaCliente, puntosAcumulados);

        verify(clienteRepo).findById(cedulaCliente);
        verify(puntosLogica).existeClienteEnPuntos(cliente);
        verify(puntosLogica).buscarClientePuntos(cliente);
        verify(puntosLogica).actualizarPuntos(100, cliente);
    }

    @Test
    public void Dado_cliente_Entonces_VerificarAfiliacion() {
        ClienteModelo cliente = new ClienteModelo(1234, "Juan", "juan@example.com", new ArrayList<>());
        when(clienteRepo.findById(1234)).thenReturn(Optional.of(cliente));
        when(puntosLogica.existeClienteEnPuntos(cliente)).thenReturn(false);

        logica.existeClienteEnPuntos(1234);

        verify(puntosLogica).existeClienteEnPuntos(cliente);

    }



}
