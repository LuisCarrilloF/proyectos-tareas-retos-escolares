package server;

import shared.ListaFigurasInterface;
import shared.FiguraInterface;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ListaFiguras extends UnicastRemoteObject implements ListaFigurasInterface {
    private ArrayList<FiguraInterface> figuras;

    public ListaFiguras() throws RemoteException {
        super();
        figuras = new ArrayList<>();
    }

    @Override
    public void agregar(FiguraInterface figura) throws RemoteException {
        figuras.add(figura);
    }

    @Override
    public double getAreaTotal() throws RemoteException {
        double totalArea = 0;
        for (FiguraInterface figura : figuras) {
            totalArea += figura.calcularArea();
        }
        return totalArea;
    }

    @Override
    public double getPerimetroTotal() throws RemoteException {
        double totalPerimetro = 0;
        for (FiguraInterface figura : figuras) {
            totalPerimetro += figura.calcularPerimetro();
        }
        return totalPerimetro;
    }
}
