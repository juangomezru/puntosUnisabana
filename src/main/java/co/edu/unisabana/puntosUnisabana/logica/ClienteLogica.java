package co.edu.unisabana.puntosUnisabana.logica;

import co.edu.unisabana.puntosUnisabana.controllers.DTO.BeneficioDTO;
import co.edu.unisabana.puntosUnisabana.controllers.DTO.ClienteDTO;
import co.edu.unisabana.puntosUnisabana.modelo.BeneficioModelo;
import co.edu.unisabana.puntosUnisabana.modelo.ClienteModelo;
import co.edu.unisabana.puntosUnisabana.modelo.PuntosModelo;
import co.edu.unisabana.puntosUnisabana.repository.BeneficioRepository;
import co.edu.unisabana.puntosUnisabana.repository.ClienteRepository;
import co.edu.unisabana.puntosUnisabana.repository.PuntosRepository;
import co.edu.unisabana.puntosUnisabana.gestion.IGestionClienteTransaccion;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ClienteLogica {

    private final ClienteRepository clienteRepository;
    private final PuntosLogica puntosLogica;
    private final PuntosRepository puntosRepository;
    private final BeneficiosLogica beneficiosLogica;
    private final BeneficioRepository beneficioRepository;


    private final IGestionClienteTransaccion gestionClienteTransaccion;



    public ClienteLogica(ClienteRepository clienteRepository, PuntosLogica puntosLogica, PuntosRepository puntosRepository, BeneficiosLogica beneficiosLogica, BeneficioRepository beneficioRepository, IGestionClienteTransaccion gestionClienteTransaccion) {
        this.clienteRepository = clienteRepository;
        this.puntosLogica = puntosLogica;
        this.puntosRepository = puntosRepository;
        this.beneficiosLogica = beneficiosLogica;
        this.beneficioRepository = beneficioRepository;
        this.gestionClienteTransaccion= gestionClienteTransaccion;
    }

    public List<ClienteModelo> listaClientes() {
        return clienteRepository.findAll();
    }

    public ClienteModelo buscarCliente(int cedula) {
        return clienteRepository.findById(cedula).orElse(null);
    }

    public List<ClienteDTO> buscarClienteDTO(int cedula) {
        return clienteRepository.findById(cedula).stream().map(ClienteModelo ->
                new ClienteDTO(ClienteModelo.getCedula(), ClienteModelo.getNombre(), ClienteModelo.getEmail(),
                        ClienteModelo.getBeneficios().stream()
                                .map(beneficioModelo -> new BeneficioDTO(
                                        beneficioModelo.getNombreBeneficio(),
                                        beneficioModelo.getPuntosRequeridos(),
                                        beneficioModelo.getId()))
                                .collect(Collectors.toList()))).collect(Collectors.toList());
    }

    public void guardarCliente(ClienteDTO clienteDTO) {
        if (clienteRepository.findById(clienteDTO.getCedula()).isEmpty()) {
            ClienteModelo cliente = new ClienteModelo();
            cliente.setCedula(clienteDTO.getCedula());
            cliente.setNombre(clienteDTO.getNombre());
            cliente.setEmail(clienteDTO.getEmail());
            cliente.setBeneficios(null);
            clienteRepository.save(cliente);
        } else throw new IllegalArgumentException("Ya se encuentra registrado el cliente");
    }

    public void redimirBeneficio(int cedulaCliente, int idBeneficio) {

        if (puntosLogica.existeClienteEnPuntos(buscarCliente(cedulaCliente)) && (beneficiosLogica.buscarBeneficio(idBeneficio) != null)) {
            if (verificarPuntos(cedulaCliente, idBeneficio)) {
                descontarPuntos(cedulaCliente, idBeneficio);
                guardarBeneficios(cedulaCliente, idBeneficio);
                gestionClienteTransaccion.transaccion(buscarCliente(cedulaCliente), beneficiosLogica.buscarBeneficio(idBeneficio));
               /* transaccion(cedulaCliente, idBeneficio);*/
            } else throw new IllegalArgumentException("No tiene puntos para ese beneficio");
        } else {
            throw new NoSuchElementException("El cliente no esta registrado en puntos o no existe el beneficio");
        }

    }

    private boolean verificarPuntos(int cedulaCliente, int idBeneficio) {
        if (puntosLogica.buscarClientePuntos(buscarCliente(cedulaCliente)) >= beneficiosLogica.obtenerPuntosBeneficio(idBeneficio))
            return true;
        else return false;
    }

    public void descontarPuntos(int cedulaCliente, int idBeneficio) {
        int puntosFinales = puntosLogica.buscarClientePuntos(buscarCliente(cedulaCliente)) - beneficiosLogica.obtenerPuntosBeneficio(idBeneficio);
        puntosLogica.actualizarPuntos(puntosFinales, buscarCliente(cedulaCliente));
    }


    public void afiliarCliente(int cedulaCliente) {
        PuntosModelo puntosModelo = new PuntosModelo();
        puntosModelo.setCliente(buscarCliente(cedulaCliente));
        puntosModelo.setPuntos(0);
        puntosRepository.save(puntosModelo);
    }

    public void acumularPuntos(int cedulaCliente, int valorCompra) {
        final int ratioPuntos = 1000;
        if (puntosLogica.existeClienteEnPuntos(buscarCliente(cedulaCliente))) {
            int numeroPuntos = Math.round((float) valorCompra / ratioPuntos) + puntosLogica.buscarClientePuntos(buscarCliente(cedulaCliente));
            puntosLogica.actualizarPuntos(numeroPuntos, buscarCliente(cedulaCliente));
        } else {
            throw new IllegalArgumentException("Esta cedula no esta registrada en puntos");
        }
    }

    public void guardarBeneficios(int cedulaCliente, int idBeneficio) {
        BeneficiosLogica beneficiosLogica1 = new BeneficiosLogica(beneficioRepository);
        ClienteModelo cliente = buscarCliente(cedulaCliente);
        BeneficioModelo beneficio = beneficiosLogica1.buscarBeneficio(idBeneficio);

        cliente.getBeneficios().add(beneficio);

        clienteRepository.save(cliente);
    }



    public void existeClienteEnPuntos(int cedula) {
        ClienteModelo cliente = buscarCliente(cedula);
        if (puntosLogica.existeClienteEnPuntos(cliente)) {
            throw new IllegalArgumentException("El cliente ya se encuentra afiliado");
        } else if (buscarCliente(cedula) != null) {
            afiliarCliente(cedula);
        } else throw new NoSuchElementException("El cliente no se encuentra registrado");
    }
}
