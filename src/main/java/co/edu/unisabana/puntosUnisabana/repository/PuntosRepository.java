package co.edu.unisabana.puntosUnisabana.repository;

import co.edu.unisabana.puntosUnisabana.modelo.ClienteModelo;
import co.edu.unisabana.puntosUnisabana.modelo.PuntosModelo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuntosRepository extends JpaRepository<PuntosModelo,Integer> {
}
