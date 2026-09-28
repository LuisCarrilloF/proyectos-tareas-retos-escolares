package client;

import shared.FiguraInterface;
import java.io.Serializable;  // Importa Serializable
import java.rmi.RemoteException;

public class Circulo implements FiguraInterface, Serializable {  // Implementa Serializable
    private double radio;

    public Circulo(double radio) {
        this.radio = radio;
    }

    @Override
    public double calcularArea() throws RemoteException {
        return Math.PI * radio * radio;
    }

    @Override
    public double calcularPerimetro() throws RemoteException {
        return 2 * Math.PI * radio;
    }
}
