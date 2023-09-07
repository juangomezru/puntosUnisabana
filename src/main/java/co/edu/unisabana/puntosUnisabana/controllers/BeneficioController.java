package co.edu.unisabana.puntosUnisabana.controllers;

import co.edu.unisabana.puntosUnisabana.logica.BeneficiosLogica;
import co.edu.unisabana.puntosUnisabana.controllers.DTO.BeneficioDTO;
import co.edu.unisabana.puntosUnisabana.controllers.DTO.RespuestaDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeneficioController {
    private BeneficiosLogica beneficioLogica;

    public BeneficioController(BeneficiosLogica beneficioLogica) {
        this.beneficioLogica = beneficioLogica;
    }

    @PostMapping(path = "/beneficio/agregar")
    public RespuestaDTO agregarBeneficio(@RequestBody BeneficioDTO beneficioDTO){
        try {
            beneficioLogica.guardarBeneficio(beneficioDTO);
            return new RespuestaDTO("Beneficio agregado correctamente");
        } catch (IllegalArgumentException e) {
            return new RespuestaDTO("El beneficio no se pudo agregar " + e.getMessage());
        }
    }
}
