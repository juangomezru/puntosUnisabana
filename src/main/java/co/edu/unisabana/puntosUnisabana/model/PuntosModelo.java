package co.edu.unisabana.puntosUnisabana.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PuntosModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @ManyToOne
    @JoinColumn
    private ClienteModelo cliente;

    @Column
    private int puntos;
}
