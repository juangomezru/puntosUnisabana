package co.edu.unisabana.puntosUnisabana.service;

import co.edu.unisabana.puntosUnisabana.model.BeneficioModelo;
import co.edu.unisabana.puntosUnisabana.model.ClienteModelo;


public interface IGestionClienteTransaccion {

    void transaccion(ClienteModelo cliente, BeneficioModelo beneficio);
}
