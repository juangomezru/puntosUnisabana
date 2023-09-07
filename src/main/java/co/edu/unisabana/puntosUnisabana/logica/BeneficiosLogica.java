package co.edu.unisabana.puntosUnisabana.logica;

import co.edu.unisabana.puntosUnisabana.modelo.BeneficioModelo;
import co.edu.unisabana.puntosUnisabana.modelo.ClienteModelo;
import co.edu.unisabana.puntosUnisabana.modelo.PuntosModelo;
import co.edu.unisabana.puntosUnisabana.repository.BeneficioRepository;
import org.springframework.stereotype.Service;
import co.edu.unisabana.puntosUnisabana.controllers.DTO.RespuestaDTO;
import co.edu.unisabana.puntosUnisabana.controllers.DTO.BeneficioDTO;

import java.util.List;

@Service
public class BeneficiosLogica {
    private BeneficioRepository beneficioRepository;
    public List<BeneficioModelo> listaBeneficios(){
        return beneficioRepository.findAll();
    }
    public BeneficioModelo buscarBeneficio(int id){
        for (BeneficioModelo beneficio : listaBeneficios()){
            if (beneficio.getId() == id){
                return beneficio;
            }
        }
        return null;
    }
    public int obtenerPuntosBeneficio(int idBeneficio){
        return buscarBeneficio(idBeneficio).getPuntosRequeridos();
    }
    public void guardarBeneficio(BeneficioDTO beneficioDTO){
        BeneficioModelo beneficio= new BeneficioModelo();
        beneficio.setId(beneficioDTO.getId());
        beneficio.setNombreBeneficio(beneficioDTO.getNombreBeneficio());
        beneficio.setCliente(null);
        beneficioRepository.save(beneficio);
    }
}
