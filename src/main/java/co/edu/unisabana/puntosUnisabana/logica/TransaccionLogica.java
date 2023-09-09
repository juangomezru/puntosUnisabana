package co.edu.unisabana.puntosUnisabana.logica;

import co.edu.unisabana.puntosUnisabana.controllers.DTO.TransaccionDTO;
import co.edu.unisabana.puntosUnisabana.modelo.TransaccionModelo;
import co.edu.unisabana.puntosUnisabana.repository.TransaccionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


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
        return transaccionRepository.findAll().stream().map(TransaccionModelo ->
                new TransaccionDTO(TransaccionModelo.getId(), TransaccionModelo.getCliente().getCedula(), TransaccionModelo.getNombreBeneficio(), TransaccionModelo.getCantidadPuntosGastados(), TransaccionModelo.getFechaTransaccion())).collect(Collectors.toList());
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
        return transaccionModelos.stream().map(TransaccionModelo ->
                new TransaccionDTO(TransaccionModelo.getId(), TransaccionModelo.getCliente().getCedula(), TransaccionModelo.getNombreBeneficio(), TransaccionModelo.getCantidadPuntosGastados(), TransaccionModelo.getFechaTransaccion())).collect(Collectors.toList());
    }

}
