package co.edu.unisabana.puntosUnisabana.modelo;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class TransaccionModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @ManyToOne
    private ClienteModelo cliente;

    @Column
    private float cantidadPuntos;
    @Column
    private LocalDate fechaTransaccion;


}
