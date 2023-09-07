package co.edu.unisabana.puntosUnisabana.modelo;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PuntosModelo {

    @Id
    @Column
    private int id;

    @ManyToOne
    @JoinColumn
    private ClienteModelo cliente;

    @Column
    private int puntos;
}
