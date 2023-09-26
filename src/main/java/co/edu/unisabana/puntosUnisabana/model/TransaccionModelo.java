package co.edu.unisabana.puntosUnisabana.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String nombreBeneficio;

    @ManyToOne
    private ClienteModelo cliente;

    @Column
    private float cantidadPuntosGastados;
    @Column
    private LocalDate fechaTransaccion;


}
