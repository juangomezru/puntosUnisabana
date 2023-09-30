package co.edu.unisabana.puntosUnisabana.controller;

import co.edu.unisabana.puntosUnisabana.controller.DTO.BeneficioDTO;
import co.edu.unisabana.puntosUnisabana.controller.DTO.RespuestaDTO;
import co.edu.unisabana.puntosUnisabana.logic.BeneficiosLogica;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Beneficio")
public class BeneficioController {
    private BeneficiosLogica beneficioLogica;

    public BeneficioController(BeneficiosLogica beneficioLogica) {
        this.beneficioLogica = beneficioLogica;
    }

    @Operation(
            description = "Se buscan todos los beneficios presentes en la base de datos y se muestran",
            summary = "Se muestran todos los beneficios",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping(path = "/beneficios")
    public List<BeneficioDTO> buscarBeneficios() {
        return beneficioLogica.listaBeneficios();
    }

    @Operation(
            description = "Se agrega un beneficio a la base de datos, se toman los valores por medio de un JSON",
            summary = "Agregar un beneficio",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = {
                                                    @ExampleObject(
                                                            name = "OK",
                                                            value = "{\n" +
                                                                    "  \"mensaje\": \"Beneficio agregado correctamente\",\n" +
                                                                    "  \"data\": \"null\"\n" +
                                                                    "}"
                                                    ),
                                                    @ExampleObject(
                                                            name = "ERROR",
                                                            value = "{\n" +
                                                                    "  \"mensaje\": \"El beneficio no se pudo agregar\",\n" +
                                                                    "  \"data\": \"null\"\n" +
                                                                    "}"
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
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
