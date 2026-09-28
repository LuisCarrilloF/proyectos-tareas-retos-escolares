package server;

import shared.IGeneradorNC;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashSet;

public class GeneradorNC extends UnicastRemoteObject implements IGeneradorNC {

    private static final String ARCHIVO_NC = "numeros_control.txt";
    private ConcurrentHashMap<String, Integer> contadorPorTecAnio;

    protected GeneradorNC() throws RemoteException {
        super();
        this.contadorPorTecAnio = new ConcurrentHashMap<>();
        cargarNumerosControl();
    }

    @Override
    public String generarNC(int anio, int tec) throws RemoteException {
        // Tomar los últimos dos dígitos del número de tecnológico
        int tecUltimosDos = tec % 100;
        
        // Formar la clave para identificar año-tecnológico
        String clave = String.format("%02d%02d", anio % 100, tecUltimosDos);
        
        // Consecutivo basado en la clave (año + tecnológico)
        // Si no existe, inicializamos a 0
        int consecutivo = contadorPorTecAnio.getOrDefault(clave, 0);

        // Generar el número de control
        String numeroControl = String.format("%02d%02d%04d", anio % 100, tecUltimosDos, consecutivo + 1);

        // Verificar si ya existe este número de control en el archivo
        while (numerosControlExistentes().contains(numeroControl)) {
            consecutivo++;
            numeroControl = String.format("%02d%02d%04d", anio % 100, tecUltimosDos, consecutivo + 1);
        }

        // Guardar el nuevo número de control en el archivo y actualizar el contador
        guardarNumeroControl(clave, numeroControl);
        
        return numeroControl;
    }

    private void cargarNumerosControl() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_NC))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String clave = linea.substring(0, 4);  // Año y Tec
                contadorPorTecAnio.put(clave, contadorPorTecAnio.getOrDefault(clave, 0) + 1);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de números de control: " + e.getMessage());
        }
    }

    private void guardarNumeroControl(String clave, String numeroControl) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_NC, true))) {
            writer.write(numeroControl);
            writer.newLine();
            // Actualizar el contador para esa clave
            contadorPorTecAnio.put(clave, contadorPorTecAnio.getOrDefault(clave, 0) + 1);
        } catch (IOException e) {
            System.out.println("Error al guardar el número de control: " + e.getMessage());
        }
    }

    private HashSet<String> numerosControlExistentes() {
        HashSet<String> numeros = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_NC))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                numeros.add(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de números de control: " + e.getMessage());
        }
        return numeros;
    }
}
