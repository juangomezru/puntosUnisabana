package co.edu.unisabana.puntosUnisabana.gestion;

import co.edu.unisabana.puntosUnisabana.modelo.BeneficioModelo;
import co.edu.unisabana.puntosUnisabana.modelo.ClienteModelo;


public interface IGestionClienteTransaccion {

    void transaccion(ClienteModelo cliente, BeneficioModelo beneficio);
}
