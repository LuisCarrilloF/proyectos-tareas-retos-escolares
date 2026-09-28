package server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Servidor {
    public static void main(String[] args) {
        try {
            // Crear y exportar el objeto remoto
            ListaFiguras listaFiguras = new ListaFiguras();
            
            // Registrar el objeto remoto en el registro RMI
            LocateRegistry.createRegistry(1099); // Si el registro no está en ejecución
            Naming.rebind("rmi://localhost/figuras", listaFiguras);

            System.out.println("Servidor listo para recibir peticiones...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
