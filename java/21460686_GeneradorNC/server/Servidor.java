package server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
    public static void main(String[] args) {
        try {
            // Crear y exportar el objeto remoto
            GeneradorNC generador = new GeneradorNC();
            
            // Crear el registro RMI
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // Registrar el objeto en el registro
            Naming.rebind("generadornc", generador);
            
            System.out.println("Servidor listo y esperando peticiones...");
        } catch (Exception e) {
            System.out.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
