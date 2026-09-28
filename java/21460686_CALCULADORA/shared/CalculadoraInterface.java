package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CalculadoraInterface extends Remote {
    double sumar(double n1, double n2) throws RemoteException;
    double restar(double n1, double n2) throws RemoteException;
    double multiplicar(double n1, double n2) throws RemoteException;
    double dividir(double dividiendo, double divisor) throws RemoteException;
}
