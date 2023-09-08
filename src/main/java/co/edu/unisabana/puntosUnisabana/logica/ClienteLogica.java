package co.edu.unisabana.puntosUnisabana.logica;

import co.edu.unisabana.puntosUnisabana.controllers.DTO.ClienteDTO;
import co.edu.unisabana.puntosUnisabana.modelo.BeneficioModelo;
import co.edu.unisabana.puntosUnisabana.modelo.ClienteModelo;
import co.edu.unisabana.puntosUnisabana.modelo.PuntosModelo;
import co.edu.unisabana.puntosUnisabana.modelo.TransaccionModelo;
import co.edu.unisabana.puntosUnisabana.repository.BeneficioRepository;
import co.edu.unisabana.puntosUnisabana.repository.ClienteRepository;
import co.edu.unisabana.puntosUnisabana.repository.PuntosRepository;
import co.edu.unisabana.puntosUnisabana.repository.TransaccionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ClienteLogica {

    private final ClienteRepository clienteRepository;
    private final PuntosLogica puntosLogica;
    private final PuntosRepository puntosRepository;
    private final BeneficiosLogica beneficiosLogica;
    private final BeneficioRepository beneficioRepository;
    private final TransaccionRepository transaccionRepository;
    public ClienteLogica(ClienteRepository clienteRepository, PuntosLogica puntosLogica, PuntosRepository puntosRepository, BeneficiosLogica beneficiosLogica, BeneficioRepository beneficioRepository, TransaccionRepository transaccionRepository) {
        this.clienteRepository = clienteRepository;
        this.puntosLogica = puntosLogica;
        this.puntosRepository = puntosRepository;
        this.beneficiosLogica = beneficiosLogica;
        this.beneficioRepository = beneficioRepository;
        this.transaccionRepository = transaccionRepository;
    }
    public List<ClienteModelo> listaClientes(){
        return clienteRepository.findAll();
    }

    public ClienteModelo buscarCliente(int cedula){
        return clienteRepository.findById(cedula).orElse(null);
    }

    public void guardarCliente(ClienteDTO clienteDTO){
        if(clienteRepository.findById(clienteDTO.getCedula()).isEmpty()){
            ClienteModelo cliente = new ClienteModelo();
            cliente.setCedula(clienteDTO.getCedula());
            cliente.setNombre(clienteDTO.getNombre());
            cliente.setEmail(clienteDTO.getEmail());
            cliente.setBeneficios(null);
            clienteRepository.save(cliente);
        }else
            throw new IllegalArgumentException("Ya se encuentra registrado el cliente");
    }
    public void redimirBeneficio(int cedulaCliente, int idBeneficio){
        transaccion(cedulaCliente, idBeneficio);
        if(verificarPuntos(cedulaCliente, idBeneficio)){
            descontarPuntos(cedulaCliente, idBeneficio);
            guardarBeneficios(cedulaCliente, idBeneficio);
        }else
            throw new IllegalArgumentException("No tiene puntos para ese beneficio");
    }
    private boolean verificarPuntos(int cedulaCliente, int idBeneficio){
        if(puntosLogica.buscarClientePuntos(buscarCliente(cedulaCliente)) >= beneficiosLogica.obtenerPuntosBeneficio(idBeneficio))
            return true;
        else
            return false;
    }

    public void descontarPuntos(int cedulaCliente, int idBeneficio){
        int puntosFinales = puntosLogica.buscarClientePuntos(buscarCliente(cedulaCliente)) - beneficiosLogica.obtenerPuntosBeneficio(idBeneficio);
        puntosLogica.actualizarPuntos(puntosFinales, buscarCliente(cedulaCliente));
    }
    public void afiliarCliente(int cedulaCliente){
        PuntosModelo puntosModelo = new PuntosModelo();
        if(buscarCliente(cedulaCliente)==null || puntosLogica.buscarClienteEnPuntos(buscarCliente(cedulaCliente)).getCliente().getCedula()==(buscarCliente(cedulaCliente).getCedula()) )
            throw new IllegalArgumentException("No se encuentra el cliente registrado o ya esta afiliado");
        else {
            puntosModelo.setCliente(buscarCliente(cedulaCliente));
            puntosModelo.setPuntos(0);
            puntosRepository.save(puntosModelo);
        }
    }
    public void acumularPuntos(int cedulaCliente, int valorCompra){
        int numeroPuntos = Math.round(valorCompra / 1000) + puntosLogica.buscarClientePuntos(buscarCliente(cedulaCliente));
        puntosLogica.actualizarPuntos(numeroPuntos,buscarCliente(cedulaCliente));
    }

    public void guardarBeneficios(int cedulaCliente, int idBeneficio)
    {
        BeneficiosLogica beneficiosLogica1 = new BeneficiosLogica(beneficioRepository);
        ClienteModelo cliente = buscarCliente(cedulaCliente);
        BeneficioModelo beneficio= beneficiosLogica1.buscarBeneficio(idBeneficio);

        cliente.getBeneficios().add(beneficio);

        clienteRepository.save(cliente);
    }

    public void transaccion(int cedulaCliente, int idBeneficio){
        TransaccionModelo transaccionModelo = new TransaccionModelo();
        transaccionModelo.setCliente(buscarCliente(cedulaCliente));
        transaccionModelo.setCantidadPuntos(beneficiosLogica.obtenerPuntosBeneficio(idBeneficio));
        transaccionModelo.setFechaTransaccion(LocalDate.now());
        transaccionRepository.save(transaccionModelo);
    }
}
