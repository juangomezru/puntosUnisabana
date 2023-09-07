package co.edu.unisabana.puntosUnisabana.modelo;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class BeneficioModelo {

    @Id
    @Column
    private int id;

    @Column
    private String nombreBeneficio;

    @Column
    private int puntosRequeridos;

    @ManyToMany
    @JoinTable(name = "cliente_beneficio", joinColumns = @JoinColumn(name = "cliente_id"), inverseJoinColumns = @JoinColumn(name = "beneficio_id"))
    private List<ClienteModelo> cliente;

}
