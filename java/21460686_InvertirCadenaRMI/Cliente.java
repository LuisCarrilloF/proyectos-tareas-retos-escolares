import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            // Conectar al registro RMI en localhost y obtener la referencia del objeto remoto
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            GestorCadenaInterface gestorCadena = (GestorCadenaInterface) registry.lookup("GestorCadena");
            Scanner leer = new Scanner(System.in);

            // Llamar al método remoto para invertir una cadena
            System.out.println("Escribe tu cadena a invertir: ");
            String cadena = leer.nextLine();
            String resultado = gestorCadena.invertirCadena(cadena);
            System.out.println("Cadena original: " + cadena);
            System.out.println("Cadena invertida: " + resultado);
        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}
