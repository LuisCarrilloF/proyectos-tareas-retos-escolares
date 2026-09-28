package client;

import shared.FiguraInterface;
import java.io.Serializable;  // Importa Serializable
import java.rmi.RemoteException;

public class Rombo implements FiguraInterface, Serializable {  // Implementa Serializable
    private double diagonalMayor;
    private double diagonalMenor;

    public Rombo(double diagonalMayor, double diagonalMenor) {
        this.diagonalMayor = diagonalMayor;
        this.diagonalMenor = diagonalMenor;
    }

    @Override
    public double calcularArea() throws RemoteException {
        return (diagonalMayor * diagonalMenor) / 2;
    }

    @Override
    public double calcularPerimetro() throws RemoteException {
        double lado = Math.sqrt(Math.pow(diagonalMayor / 2, 2) + Math.pow(diagonalMenor / 2, 2));
        return 4 * lado;
    }
}
