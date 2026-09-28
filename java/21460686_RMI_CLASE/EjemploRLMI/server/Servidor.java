package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import server.Saludador;

import shared.SaludadorInterface;

public class Servidor {
    public static void main(String[] args) {
        try {
            Registry registro = LocateRegistry.createRegistry(1099);
            Saludador saludador = new Saludador();
            SaludadorInterface stub = (SaludadorInterface) UnicastRemoteObject.exportObject(saludador, 0);
            registro.bind("Saludador", stub);
        } catch (RemoteException rex) {
            System.err.println(rex.getMessage());
        } catch (AlreadyBoundException abex) {
            System.err.println(abex.getMessage());
        }
    }
}
