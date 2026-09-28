package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import shared.CalculadoraInterface;

public class CalculadoraServer {
    public static void main(String[] args) {
        try {
            // Crear el objeto de la calculadora
            CalculadoraImpl calculadora = new CalculadoraImpl();
            
            // Crear un registro RMI en el puerto 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // Registrar el objeto calculadora
            registry.rebind("Calculadora", calculadora);
            
            System.out.println("Servidor de calculadora listo.");
        } catch (Exception e) {
            System.out.println("Error en el servidor: " + e);
        }
    }
}
