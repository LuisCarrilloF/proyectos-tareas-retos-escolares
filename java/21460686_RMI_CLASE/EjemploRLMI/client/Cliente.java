package client;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import shared.SaludadorInterface;

public class Cliente {
    public static void main(String[] args) {
        try {
            Registry registro = LocateRegistry.getRegistry("localhost", 1099);
            SaludadorInterface saludador = (SaludadorInterface) registro.lookup("Saludador");

            Scanner sc = new Scanner(System.in);
            System.out.print("Introduce el nombre:");
            String nombre = sc.nextLine();
            sc.close();

            System.out.println(saludador.diHola(nombre));

            System.out.println("Presione ENTER para continuar...");
            System.in.read();
        } catch (RemoteException rex) {
            System.err.println("Error en el cliente: " + rex.getMessage());
            rex.printStackTrace();
        } catch (NotBoundException nbex) {
            System.err.println(nbex.getMessage());
        } catch (IOException ioex) {
            System.err.println(ioex.getMessage());
        }
    }
}