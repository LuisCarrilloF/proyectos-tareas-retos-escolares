import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    private static final String CARPETA_IMAGENES = "Cliente_imagenes/"; // Carpeta donde se guardarán las imágenes

    public static void main(String[] args) {
        int opc;
        Scanner sc = new Scanner(System.in);

        menuLoop:
        while (true) {
            try (Socket socket = new Socket("127.0.0.1", 3000)) { // Dirección a la que se va conectar y puerto
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());

                System.out.println("\n------ MENU ----- ");
                System.out.println("[0] Salir ");
                System.out.println("[1] Listar Imágenes ");
                System.out.println("[2] Descargar Imagen.");
                System.out.print("Elegir opción: ");
                opc = sc.nextInt();

                // Enviar la opción seleccionada al servidor
                dos.writeInt(opc);

                switch (opc) {
                    case 0:
                        System.out.println("Gracias por usar el programa.");
                        break menuLoop;

                    case 1:
                        System.out.println("\nLISTA DE IMÁGENES DISPONIBLES:");
                        // Leer la cantidad de imágenes del servidor
                        int numImagenes = dis.readInt();
                        for (int i = 0; i < numImagenes; i++) {
                            String nombreImagen = dis.readUTF();
                            System.out.println((i + 1) + ". " + nombreImagen);
                        }
                        break;

                    case 2:
                        System.out.println("\n---DESCARGA DE IMAGEN---");
                        System.out.print("Nombre Imagen [Con extension]: ");
                        String nombreImagen = sc.next();
                        // Enviar el nombre de la imagen al servidor
                        dos.writeUTF(nombreImagen);

                        // Verificar si el archivo existe en el servidor
                        boolean existe = dis.readBoolean();
                        if (!existe) {
                            System.out.println("La imagen solicitada no existe en el servidor.");
                        } else {
                            // Recibir el archivo
                            String nuevoNombre = verificarNombreArchivo(CARPETA_IMAGENES + nombreImagen);
                            try (FileOutputStream fos = new FileOutputStream(nuevoNombre)) {
                                byte[] buffer = new byte[4096];
                                int bytesLeidos;

                                System.out.println("Descargando imagen...");

                                // Recibir datos del archivo
                                while ((bytesLeidos = dis.read(buffer)) != -1) {
                                    fos.write(buffer, 0, bytesLeidos);
                                }

                                System.out.println("Imagen descargada como: " + nuevoNombre);
                            }
                        }
                        break;

                    default:
                        System.out.println("\nOpción incorrecta, selecciona una opción válida.");
                        break;
                }

            } catch (IOException e) {
                System.out.println("Problema del cliente, try SOCKET: " + e);
            }
        }

        sc.close();
    }

    // Método para verificar si ya existe el archivo y añadir un número si es necesario
    private static String verificarNombreArchivo(String nombreOriginal) {
        File archivo = new File(nombreOriginal);
        String nuevoNombre = nombreOriginal;
        int contador = 1;

        while (archivo.exists()) {
            String nombreSinExtension = nombreOriginal.substring(0, nombreOriginal.lastIndexOf('.'));
            String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf('.'));
            nuevoNombre = nombreSinExtension + "(" + contador + ")" + extension;
            archivo = new File(nuevoNombre);
            contador++;
        }

        return nuevoNombre;
    }
}
