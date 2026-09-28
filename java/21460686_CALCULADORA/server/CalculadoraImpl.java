package server;

import shared.CalculadoraInterface;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class CalculadoraImpl extends UnicastRemoteObject implements CalculadoraInterface {

    public CalculadoraImpl() throws RemoteException {
        super();
    }

    @Override
    public double sumar(double n1, double n2) throws RemoteException {
        return n1 + n2;
    }

    @Override
    public double restar(double n1, double n2) throws RemoteException {
        return n1 - n2;
    }

    @Override
    public double multiplicar(double n1, double n2) throws RemoteException {
        return n1 * n2;
    }

    @Override
    public double dividir(double dividiendo, double divisor) throws RemoteException {
        if (divisor == 0) {
            throw new RemoteException("No se puede dividir entre cero.");
        }
        return dividiendo / divisor;
    }
}
