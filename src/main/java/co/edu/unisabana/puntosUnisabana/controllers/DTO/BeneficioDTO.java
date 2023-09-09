package co.edu.unisabana.puntosUnisabana.controllers.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeneficioDTO {

    private String nombreBeneficio;
    private int puntosRequeridos;
    private int codigo;
}
