import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import modelo.Estudiante;


public class Principal {
    
    public static void leerArchivoBinario()
    {
        System.out.println("ARCHIVO BINARIO LEIDO");
        String rutaArchivo = "data.bin";
        System.out.printf("%-9S | %-52S | %-4S |%-6S\n", "N.C.", "Nombre", "Sem.", "Prom.");
        try (FileInputStream fis = new FileInputStream(rutaArchivo)) {
            DataInputStream dis = new DataInputStream(fis);
            //while (dis.available()>0){}
            while (true) {
                try {
                    System.out.printf("%-9s | %-52s | %-4d | %6.2f\n",
                    dis.readUTF(), dis.readUTF(), dis.readInt(), dis.readFloat());
                } catch (EOFException e) {
                    // Se ha alcanzado el final del archivo
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String... args) {
        Scanner sc = new Scanner(System.in);
        LinkedList<Estudiante> estudiantes = new LinkedList<Estudiante>();

        Estudiante estudiante;
        String numControl, nombre;
        float promedio;
        System.out.println("\n=== TECNOLOGICO NACIONAL DE COLIMA ===");

        int opc = 0;
        menuLoop: do {
            System.out.println("\n=== CONTROL ESCOLAR ===");
            System.out.println("[1] Agregar estudiante");
            System.out.println("[2] Mostrar alumnos");
            System.out.println("[3] Leer Archivo binario");
            System.out.println("[0] Salir");
            System.out.print("Escoger opcion:");
            opc = sc.nextInt();
            sc.nextLine();

            switch (opc) {
                case 0:
                    System.out.println("Gracias por usar el programa");
                    break menuLoop;
                case 1:
                    System.out.println("--- AGREGAR ESTUDIANTE ---");
                    System.out.print("Número Control: ");
                    numControl = sc.nextLine();
                    System.out.print("Nombre:");
                    nombre = sc.nextLine();
                    System.out.print("promedio:");
                    promedio = sc.nextFloat();

                    estudiante = new Estudiante(numControl);
                    estudiante.setNombre(nombre);
                    estudiante.setPromedio(promedio);
                    estudiantes.add(estudiante);

                    try (FileOutputStream fos = new FileOutputStream("data.bin", true)) {
                        DataOutputStream dos = new DataOutputStream(fos);
                        estudiante.write(dos);
                    } catch (IOException ioex) {
                        System.out.println(ioex.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("---- CONSULTA GENERAL ----");
                    System.out.printf("%-9S | %-52S | %-4S |%-6S\n", "N.C.", "Nombre", "Sem.", "Prom.");

                    for (Estudiante e : estudiantes) {
                        System.out.printf("%-9S | %-52S | %-4d | %6.2f\n",
                                e.getnumControl(),
                                e.getNombre(),
                                e.getSemestre(),
                                e.getPromedio());
                    }
                    break;
                case 3:
                    System.out.println("----- LEER ARCHIVO BINARIO -----");
                    leerArchivoBinario();
            }
        } while (true);
    }
}
