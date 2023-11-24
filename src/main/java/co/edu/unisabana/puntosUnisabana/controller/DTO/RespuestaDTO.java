package co.edu.unisabana.puntosUnisabana.controller.DTO;


import lombok.Getter;
import lombok.Setter;

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

