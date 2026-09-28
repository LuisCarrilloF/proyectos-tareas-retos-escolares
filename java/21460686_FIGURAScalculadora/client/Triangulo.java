package client;

import shared.FiguraInterface;
import java.io.Serializable;
import java.rmi.RemoteException;

public class Triangulo implements FiguraInterface, Serializable {
    private double ladoA;
    private double ladoB;
    private double ladoC;

    public Triangulo(double ladoA, double ladoB, double ladoC) {
        this.ladoA = ladoA;
        this.ladoB = ladoB;
        this.ladoC = ladoC;
    }

    @Override
    public double calcularArea() throws RemoteException {
        // Cálculo del semiperímetro (s)
        double s = (ladoA + ladoB + ladoC) / 2;
        // Fórmula de Herón para el área
        return Math.sqrt(s * (s - ladoA) * (s - ladoB) * (s - ladoC));
    }

    @Override
    public double calcularPerimetro() throws RemoteException {
        // El perímetro es la suma de los tres lados
        return ladoA + ladoB + ladoC;
    }
}
