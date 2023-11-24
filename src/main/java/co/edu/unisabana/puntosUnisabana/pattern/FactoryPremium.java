package co.edu.unisabana.puntosUnisabana.pattern;

public class FactoryPremium implements ClienteFactory {

    @Override
    public iCliente crearCliente() {
        return new ClientePremium();
    }

}
