package co.edu.unisabana.puntosUnisabana.pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClasificacionModelo {
    @Id
    @Column
    private int cedula;
    @Column
    private int multiplicador;
}
