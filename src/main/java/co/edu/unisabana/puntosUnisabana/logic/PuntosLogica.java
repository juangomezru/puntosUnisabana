package co.edu.unisabana.puntosUnisabana.logic;

import co.edu.unisabana.puntosUnisabana.model.ClienteModelo;
import co.edu.unisabana.puntosUnisabana.model.PuntosModelo;
import co.edu.unisabana.puntosUnisabana.repository.PuntosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PuntosLogica {
    private final PuntosRepository puntosRepository;


    public PuntosLogica(PuntosRepository puntosRepository) {
        this.puntosRepository = puntosRepository;

    }

    public List<PuntosModelo> listaPuntos() {
        return puntosRepository.findAll();
    }

    public boolean existeClienteEnPuntos(ClienteModelo cliente) {
        for (PuntosModelo clientePuntos : listaPuntos()) {
            if (clientePuntos.getCliente() == cliente) {
                return true;
            }
        }
        return false;
    }

    public int buscarClientePuntos(ClienteModelo cliente) {
        for (PuntosModelo puntos : listaPuntos()) {
            if (puntos.getCliente() == cliente) {
                return puntos.getPuntos();
            }
        }
        return 0;
    }

    public void actualizarPuntos(int puntosFinales, ClienteModelo cliente) {
        for (PuntosModelo puntos : listaPuntos()) {
            if (puntos.getCliente() == cliente) {
                puntos.setPuntos(puntosFinales);
                puntosRepository.save(puntos);
            }
        }
    }
}

