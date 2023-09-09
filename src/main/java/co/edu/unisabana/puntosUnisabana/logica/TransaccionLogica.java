package co.edu.unisabana.puntosUnisabana.logica;

import co.edu.unisabana.puntosUnisabana.controllers.DTO.TransaccionDTO;
import co.edu.unisabana.puntosUnisabana.modelo.TransaccionModelo;
import co.edu.unisabana.puntosUnisabana.repository.TransaccionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class TransaccionLogica {

    private final TransaccionRepository transaccionRepository;

    public TransaccionLogica(TransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    }

    public void guardarTransaccion(TransaccionDTO transaccionDTO) {

        TransaccionModelo transaccion = new TransaccionModelo();
        transaccion.setCliente(transaccionDTO.getCliente());
        transaccion.setCantidadPuntos(transaccionDTO.getCantidadPuntos());
        transaccion.setFechaTransaccion(transaccionDTO.getFechaTransaccion());

    }

    public List<TransaccionModelo> obtenerTransacciones() {
        return transaccionRepository.findAll();
    }

    public List<TransaccionModelo> consultarTransaccion(int cedula){
        boolean editad = false;
        List<TransaccionModelo> transaccionModelos = new ArrayList<>();

        for (TransaccionModelo transacciones: obtenerTransacciones()){
            if (transacciones.getCliente().getCedula()==cedula){
                transaccionModelos.add(transacciones);
                editad = true;
            }
            }if(!editad){
            throw new NoSuchElementException("No existen transacciones para este usuario");
        }
        return transaccionModelos;
    }

}
