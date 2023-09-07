package co.edu.unisabana.puntosUnisabana.modelo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
public class TransaccionModelo {

    @Id
    @Column
    private int id;

    @ManyToOne
    private ClienteModelo cliente;

    @Column
    private float cantidadPuntos;


}
