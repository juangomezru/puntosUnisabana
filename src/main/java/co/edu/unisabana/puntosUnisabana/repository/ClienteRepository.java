package co.edu.unisabana.puntosUnisabana.repository;

import co.edu.unisabana.puntosUnisabana.modelo.ClienteModelo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepository extends JpaRepository<ClienteModelo, Integer> {
}
