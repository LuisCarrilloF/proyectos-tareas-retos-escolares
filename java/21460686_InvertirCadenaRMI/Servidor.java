import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
    public static void main(String[] args) {
        try {
            // Crear una instancia del objeto remoto
            GestorCadena gestorCadena = new GestorCadena();

            // Crear un registro en el puerto 1099 y registrar el objeto
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("GestorCadena", gestorCadena);

            System.out.println("Servidor RMI iniciado...");
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}
