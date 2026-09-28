package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FiguraInterface extends Remote {
    double calcularArea() throws RemoteException;
    double calcularPerimetro() throws RemoteException;
}
