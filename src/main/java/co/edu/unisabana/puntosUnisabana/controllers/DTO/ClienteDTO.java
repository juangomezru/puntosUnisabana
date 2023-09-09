package co.edu.unisabana.puntosUnisabana.controllers.DTO;

import co.edu.unisabana.puntosUnisabana.modelo.BeneficioModelo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private int cedula;
    private String nombre;
    private String email;
    private List<BeneficioDTO> beneficios;
}
