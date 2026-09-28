import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GestorCadenaInterface extends Remote {
    public String invertirCadena(String cadena) throws RemoteException;
}
