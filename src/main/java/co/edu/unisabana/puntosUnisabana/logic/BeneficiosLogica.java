package co.edu.unisabana.puntosUnisabana.logic;

import co.edu.unisabana.puntosUnisabana.controller.DTO.BeneficioDTO;
import co.edu.unisabana.puntosUnisabana.model.BeneficioModelo;
import co.edu.unisabana.puntosUnisabana.repository.BeneficioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeneficiosLogica {
    private final BeneficioRepository beneficioRepository;

    public BeneficiosLogica(BeneficioRepository beneficioRepository) {
        this.beneficioRepository = beneficioRepository;
    }

    public List<BeneficioDTO> listaBeneficios() {
        return beneficioRepository.findAll().stream().map(beneficioModelo ->
                new BeneficioDTO(beneficioModelo.getNombreBeneficio(), beneficioModelo.getPuntosRequeridos(), beneficioModelo.getId())).toList();
    }

    public BeneficioModelo buscarBeneficio(int id) {
        return beneficioRepository.findById(id).orElse(null);
    }

    public int obtenerPuntosBeneficio(int idBeneficio) {
        return buscarBeneficio(idBeneficio).getPuntosRequeridos();
    }

    public void guardarBeneficio(BeneficioDTO beneficioDTO) {
        BeneficioModelo beneficio = new BeneficioModelo();
        beneficio.setNombreBeneficio(beneficioDTO.getNombreBeneficio());
        beneficio.setPuntosRequeridos(beneficioDTO.getPuntosRequeridos());
        beneficio.setCliente(null);
        beneficioRepository.save(beneficio);
    }
}
