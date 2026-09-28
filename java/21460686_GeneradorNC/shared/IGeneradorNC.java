package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGeneradorNC extends Remote {
    String generarNC(int anio, int tec) throws RemoteException;
}
