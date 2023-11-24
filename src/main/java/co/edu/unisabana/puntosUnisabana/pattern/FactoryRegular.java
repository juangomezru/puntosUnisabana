package co.edu.unisabana.puntosUnisabana.pattern;


public class FactoryRegular implements ClienteFactory {

    @Override
    public iCliente crearCliente() {
        return new ClienteRegular();
    }

}
