package co.edu.unisabana.puntosUnisabana.controllers;

import co.edu.unisabana.puntosUnisabana.controllers.DTO.RespuestaDTO;
import co.edu.unisabana.puntosUnisabana.logica.TransaccionLogica;
import co.edu.unisabana.puntosUnisabana.modelo.TransaccionModelo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class TransaccionController {

    private TransaccionLogica transaccionLogica;

    public TransaccionController(TransaccionLogica transaccionLogica) {this.transaccionLogica = transaccionLogica;}

    @GetMapping(path = "/transacciones")
    public List<TransaccionModelo> verTransacciones(){
        try {
            return transaccionLogica.obtenerTransacciones();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(path = "/transacciones/clientes")
    public List<TransaccionModelo> verTransaccionesPorCliente(@RequestParam int cedula){
        try{
            return transaccionLogica.consultarTransaccion(cedula);

        }
        catch (Exception e){
            return null;
        }

    }

}
