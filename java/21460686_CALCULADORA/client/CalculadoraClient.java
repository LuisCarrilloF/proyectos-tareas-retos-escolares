package client;

import java.awt.Menu;
import shared.CalculadoraInterface;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class CalculadoraClient {
    public static void main(String[] args) {
        menuloop: while (true){
            try {
                // Obtener el registro RMI
                Registry registry = LocateRegistry.getRegistry("localhost", 1099);
                
            // Obtener el objeto remoto
            CalculadoraInterface calculadora = (CalculadoraInterface) registry.lookup("Calculadora");
            
            Scanner sc = new Scanner(System.in);
            System.out.println("\n----MENU----");
            System.out.println("[0] Salir");
            System.out.println("[1] Sumar");
            System.out.println("[2] Restar");
            System.out.println("[3] Multiplicar");
            System.out.println("[4] Dividir");
            System.out.print("Elegir: ");
            int opcion = sc.nextInt();
            // Realizar operaciones
            if (opcion==0){
                System.out.println("\nGRACIAS POR USAR EL PROGRAMA.");
                break menuloop;
            }
            System.out.print("Ingrese el primer número: ");
            double a = sc.nextDouble();
            System.out.print("Ingrese el segundo número: ");
            double b = sc.nextDouble();
            
            System.out.println("\n");
            switch (opcion){
                case 1:
                System.out.println("Suma: " + calculadora.sumar(a, b));
                break;
                case 2:
                System.out.println("Resta: " + calculadora.restar(a, b));
                break;
                case 3:
                System.out.println("Multiplicación: " + calculadora.multiplicar(a, b));
                break;
                case 4:
                System.out.println("División: " + calculadora.dividir(a, b));
                break;
            default:
             System.out.println("Opcion invalida, elija una opcion correcta.");
             break;
            }
        } catch (Exception e) {
            System.out.println("Error en el cliente: " + e);
        }
    };
    }
}
