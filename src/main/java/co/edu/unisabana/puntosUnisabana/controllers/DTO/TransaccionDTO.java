package co.edu.unisabana.puntosUnisabana.controllers.DTO;

import co.edu.unisabana.puntosUnisabana.modelo.ClienteModelo;
import lombok.Data;


@Data
public class TransaccionDTO {
    private ClienteModelo cliente;
    private float cantidadPuntos;
}
