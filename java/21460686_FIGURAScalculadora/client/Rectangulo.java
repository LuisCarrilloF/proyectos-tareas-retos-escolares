package client;

import shared.FiguraInterface;
import java.io.Serializable;  // Importa Serializable
import java.rmi.RemoteException;

public class Rectangulo implements FiguraInterface, Serializable {  // Implementa Serializable
    private double base;
    private double altura;

    public Rectangulo(double base, double altura) {
        this.base = base;
        this.altura = altura;
    }

    @Override
    public double calcularArea() throws RemoteException {
        return base * altura;
    }

    @Override
    public double calcularPerimetro() throws RemoteException {
        return 2 * (base + altura);
    }
}
