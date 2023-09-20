package co.edu.unisabana.puntosUnisabana.controller;
import co.edu.unisabana.puntosUnisabana.controller.DTO.BeneficioDTO;
import co.edu.unisabana.puntosUnisabana.controller.DTO.RespuestaDTO;
import co.edu.unisabana.puntosUnisabana.logic.BeneficiosLogica;
import org.springframework.web.bind.annotation.*;


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
