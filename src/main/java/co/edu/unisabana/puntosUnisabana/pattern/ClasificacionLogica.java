package co.edu.unisabana.puntosUnisabana.pattern;


import org.springframework.stereotype.Service;

@Service
public class ClasificacionLogica {

    ClasificacionRepository repository;

    public ClasificacionLogica(ClasificacionRepository repository) {
        this.repository = repository;
    }

    public void clasificar(int puntos, int cedula){
        ClienteFactory clienteFactory;
        ClasificacionModelo clasificacionModelo = new ClasificacionModelo();
        if (puntos > 10_000){
            clienteFactory = new FactoryPremium();
            iCliente cliente = clienteFactory.crearCliente();
            clasificacionModelo.setCedula(cedula);
            clasificacionModelo.setMultiplicador(cliente.multiplicadorPuntos());
        } else if (puntos > 2_000) {
            clienteFactory = new FactoryRegular();
            iCliente cliente = clienteFactory.crearCliente();
            clasificacionModelo.setCedula(cedula);
            clasificacionModelo.setMultiplicador(cliente.multiplicadorPuntos());
        }else {
            clienteFactory = new FactoryRegular();
            iCliente cliente = clienteFactory.crearCliente();
            clasificacionModelo.setCedula(cedula);
            clasificacionModelo.setMultiplicador(1);
        }
        repository.save(clasificacionModelo);
    }


}
