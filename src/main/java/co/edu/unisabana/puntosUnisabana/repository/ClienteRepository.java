package co.edu.unisabana.puntosUnisabana.repository;

import co.edu.unisabana.puntosUnisabana.model.ClienteModelo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepository extends JpaRepository<ClienteModelo, Integer> {
}
