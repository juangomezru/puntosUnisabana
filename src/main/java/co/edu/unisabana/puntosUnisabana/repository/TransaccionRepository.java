package co.edu.unisabana.puntosUnisabana.repository;

import co.edu.unisabana.puntosUnisabana.modelo.TransaccionModelo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository<TransaccionModelo,Integer> {

}
