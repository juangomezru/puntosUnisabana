package co.edu.unisabana.puntosUnisabana.logica;

import co.edu.unisabana.puntosUnisabana.controllers.DTO.BeneficioDTO;
import co.edu.unisabana.puntosUnisabana.modelo.BeneficioModelo;
import co.edu.unisabana.puntosUnisabana.repository.BeneficioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeneficiosLogica {
    private BeneficioRepository beneficioRepository;

    public BeneficiosLogica(BeneficioRepository beneficioRepository) {
        this.beneficioRepository = beneficioRepository;
    }

    public List<BeneficioModelo> listaBeneficios() {
        return beneficioRepository.findAll();
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
        beneficio.setCliente(null);
        beneficioRepository.save(beneficio);
    }
}
