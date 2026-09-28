package client;

import shared.IGeneradorNC;

import java.rmi.Naming;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // Mover fuera del ciclo
        try {
            menu: while (true) {
                // Conectar al servidor RMI
                IGeneradorNC generador = (IGeneradorNC) Naming.lookup("//localhost/generadornc");

                // Solicitar datos al usuario
                System.out.println("\nINTRODUCE EN EL AÑO [0000] PARA SALIR");
                System.out.print("Ingrese el año de ingreso (4 dígitos): ");
                int anio = scanner.nextInt();
                int salir = anio;
                if (salir == 0000) {
                    System.out.println("Gracias por usar el programa.");
                    break menu;
                }
                System.out.print("Ingrese el número de tecnológico (hasta 3 dígitos): ");
                int tec = scanner.nextInt();
                
                

                // Generar el número de control
                String numeroControl = generador.generarNC(anio, tec);

                // Mostrar el resultado
                System.out.println("Número de control generado: " + numeroControl);
            }
        } catch (Exception e) {
            System.out.println("Error en el cliente: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();  // Cerrar el scanner fuera del ciclo
        }
    }
}
