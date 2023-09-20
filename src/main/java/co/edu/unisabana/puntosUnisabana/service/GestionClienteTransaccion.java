package co.edu.unisabana.puntosUnisabana.gestion;

import co.edu.unisabana.puntosUnisabana.modelo.BeneficioModelo;
import co.edu.unisabana.puntosUnisabana.modelo.ClienteModelo;
import co.edu.unisabana.puntosUnisabana.modelo.TransaccionModelo;
import co.edu.unisabana.puntosUnisabana.repository.TransaccionRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class GestionClienteTransaccion implements IGestionClienteTransaccion {


    private final TransaccionRepository transaccionRepository;

    public GestionClienteTransaccion(TransaccionRepository transaccionRepository) {

        this.transaccionRepository = transaccionRepository;
    }

    @Override
    public void transaccion(ClienteModelo cliente, BeneficioModelo beneficio) {

        TransaccionModelo transaccionModelo = new TransaccionModelo();
        transaccionModelo.setCliente(cliente);
        transaccionModelo.setNombreBeneficio(beneficio.getNombreBeneficio());
        transaccionModelo.setCantidadPuntosGastados(beneficio.getPuntosRequeridos());
        transaccionModelo.setFechaTransaccion(LocalDate.now());
        transaccionRepository.save(transaccionModelo);
    }
}
