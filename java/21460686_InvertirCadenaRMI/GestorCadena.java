import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GestorCadena extends UnicastRemoteObject implements GestorCadenaInterface {

    // Constructor
    protected GestorCadena() throws RemoteException {
        super();
    }

    // Implementación del método para invertir la cadena
    @Override
    public String invertirCadena(String cadena) throws RemoteException {
        return new StringBuilder(cadena).reverse().toString();
    }
}
