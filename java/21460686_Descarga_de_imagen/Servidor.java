import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Servidor {
    
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(3000)) {
            do {
                Socket socket = server.accept();
                Thread hilo = new Thread(new ServidorHilo(socket));
                hilo.start();
            } while (true);
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        }
        
    }
    
}

class ServidorHilo implements Runnable {
    private static final String CARPETA_IMAGENES = "Servidor_imagenes/"; // Carpeta donde están las imágenes
    Socket socket;
    Scanner sc = new Scanner(System.in);
    
    public ServidorHilo(Socket socket) {
        this.socket = socket;
    }
    
    public void run() {
        /**
         * NOTA: Por alguna extraña razon, en el Servidor se inprime un [null]
         * cuando el cliente se desconecta
         */
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            // Por cada write, debe a ver un read, de ser necesario print(read).
            
            System.out.println("Cliente Conectado.");
            int opc = dis.readInt();
            Thread.sleep(200);
            switch (opc) {
                case 0:
                    System.out.println("Cliente desconectado.");
                    break;
                case 1:
                System.out.println("Enviando lista de imagenes.");
                    // Listar imágenes
                    File carpeta = new File(CARPETA_IMAGENES);
                    File[] archivos = carpeta.listFiles();
                    if (archivos != null) {
                        List<String> listaImagenes = new ArrayList<>();
                        for (File archivo : archivos) {
                            if (archivo.isFile()) {
                                listaImagenes.add(archivo.getName());
                            }
                        }

                        // Enviar el número de imágenes al cliente
                        dos.writeInt(listaImagenes.size());

                        // Enviar el nombre de cada imagen al cliente
                        for (String nombreImagen : listaImagenes) {
                            dos.writeUTF(nombreImagen);
                        }
                    } else {
                        dos.writeInt(0); // No hay imágenes
                    }
                    break;
                
                case 2:
                    // Descargar imagen
                    String nombreImagen = dis.readUTF();
                    File archivoImagen = new File(CARPETA_IMAGENES + nombreImagen);

                    if (archivoImagen.exists() && archivoImagen.isFile()) {
                        dos.writeBoolean(true); // Indicar al cliente que la imagen existe

                        // Leer y enviar el archivo
                        try (FileInputStream fis = new FileInputStream(archivoImagen)) {
                            byte[] buffer = new byte[4096];
                            int bytesLeidos;

                            while ((bytesLeidos = fis.read(buffer)) != -1) {
                                dos.write(buffer, 0, bytesLeidos);
                            }

                            System.out.println("Archivo " + nombreImagen + " enviado al cliente.");
                        }
                    } else {
                        dos.writeBoolean(false); // La imagen no existe
                        System.out.println("El archivo " + nombreImagen + " no existe.");
                    }
                    break;
                default:
                    System.out.println("El cliente eligio una ocpion invalida.");
                    break;
                }

        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        } catch (InterruptedException iex) {
            System.out.println(iex.getMessage());
        }
    }

}