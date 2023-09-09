package co.edu.unisabana.puntosUnisabana.controllers.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
@Getter
@Setter

public class RespuestaDTO<T> {
    private String mensaje;
    private T data;

    public RespuestaDTO(String mensaje) {
        this.mensaje = mensaje;
    }

    public RespuestaDTO(String mensaje, T data) {
        this.mensaje = mensaje;
        this.data = data;
    }
}

