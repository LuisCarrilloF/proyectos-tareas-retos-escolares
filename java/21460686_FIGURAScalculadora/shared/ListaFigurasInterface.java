package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ListaFigurasInterface extends Remote {
    void agregar(FiguraInterface figura) throws RemoteException;
    double getAreaTotal() throws RemoteException;
    double getPerimetroTotal() throws RemoteException;
}
