package co.edu.unisabana.puntosUnisabana.controller.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionDTO {

    private int idTransaccion;
    private int cedula;
    private String nombreBeneficio;
    private float cantidadPuntosGastados;
    private LocalDate fechaTransaccion;

}
