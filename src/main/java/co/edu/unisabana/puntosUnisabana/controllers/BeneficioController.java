package co.edu.unisabana.puntosUnisabana.controllers;
import co.edu.unisabana.puntosUnisabana.controllers.DTO.BeneficioDTO;
import co.edu.unisabana.puntosUnisabana.controllers.DTO.RespuestaDTO;
import co.edu.unisabana.puntosUnisabana.logica.BeneficiosLogica;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class BeneficioController {
    private BeneficiosLogica beneficioLogica;

    public BeneficioController(BeneficiosLogica beneficioLogica) {
        this.beneficioLogica = beneficioLogica;
    }

    @GetMapping(path = "/beneficios")
    public List<BeneficioDTO> buscarBeneficios() {
        return beneficioLogica.listaBeneficios();
    }

    @PostMapping(path = "/beneficio/agregar")
    public RespuestaDTO<String> agregarBeneficio(@RequestBody BeneficioDTO beneficioDTO) {
        try {
            beneficioLogica.guardarBeneficio(beneficioDTO);
            return new RespuestaDTO<>("Beneficio agregado correctamente");
        } catch (IllegalArgumentException e) {
            return new RespuestaDTO<>("El beneficio no se pudo agregar " + e.getMessage());
        }
    }

}
