package co.edu.unisabana.puntosUnisabana.modelo;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class ClienteModelo {
    @Id
    @Column
    private int cedula;
    @Column
    private String nombre;
    @Column
    private String email;

    @ManyToMany
    @JoinTable(name = "cliente_beneficio", joinColumns = @JoinColumn(name = "cliente_id"), inverseJoinColumns = @JoinColumn(name = "beneficio_id"))
    private List<BeneficioModelo> beneficios;


}
