package co.edu.unisabana.puntosUnisabana.logic;

import co.edu.unisabana.puntosUnisabana.controller.DTO.TransaccionDTO;
import co.edu.unisabana.puntosUnisabana.model.TransaccionModelo;
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

    public List<TransaccionModelo> obtenerTransacciones() {
        return transaccionRepository.findAll();
    }


    public List<TransaccionDTO> obtenerTransaccionesDTO() {
        return transaccionRepository.findAll().stream().map(transaccionModelo ->
                new TransaccionDTO(transaccionModelo.getId(), transaccionModelo.getCliente().getCedula(), transaccionModelo.getNombreBeneficio(), transaccionModelo.getCantidadPuntosGastados(), transaccionModelo.getFechaTransaccion())).toList();
    }

    public List<TransaccionDTO> consultarTransaccion(int cedula) {
        boolean editad = false;
        List<TransaccionModelo> transaccionModelos = new ArrayList<>();

        for (TransaccionModelo transacciones : obtenerTransacciones()) {
            if (transacciones.getCliente().getCedula() == cedula) {
                transaccionModelos.add(transacciones);
                editad = true;
            }
        }
        if (!editad) {
            throw new NoSuchElementException("No existen transacciones para este usuario");
        }
        return transaccionModelos.stream().map(transaccionModelo ->
                new TransaccionDTO(transaccionModelo.getId(), transaccionModelo.getCliente().getCedula(), transaccionModelo.getNombreBeneficio(), transaccionModelo.getCantidadPuntosGastados(), transaccionModelo.getFechaTransaccion())).toList();
    }

}
