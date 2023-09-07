package co.edu.unisabana.puntosUnisabana.controllers.DTO;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class BeneficioDTO {
    private String nombreBeneficio;
    private int puntosRequeridos;
}
