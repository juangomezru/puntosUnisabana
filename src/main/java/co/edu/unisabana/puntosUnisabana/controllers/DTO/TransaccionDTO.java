package co.edu.unisabana.puntosUnisabana.controllers.DTO;

import co.edu.unisabana.puntosUnisabana.modelo.ClienteModelo;
import lombok.Data;

import java.time.LocalDate;


@Data
public class TransaccionDTO {

    private ClienteModelo cliente;
    private float cantidadPuntos;
    private LocalDate fechaTransaccion;
}
