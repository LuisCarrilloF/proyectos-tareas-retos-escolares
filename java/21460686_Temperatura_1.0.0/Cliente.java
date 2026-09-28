import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente 
{
    
    
    public static void main(String[] args) {
        
        try (Socket socket = new Socket("127.0.0.1", 3000)) { //Direccion a la que se va conectar y porque puerto.
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            Scanner sc = new Scanner(System.in);
            int opc;
        // do{
            double numero;
            System.out.println("\n------ MENU ----- ");
            System.out.println("[0] Salir ");
            System.out.println("[1] Celsius a Fahrenheit ");
            System.out.println("[2] Fahrenheit a Celsius.");
            System.out.print("Elegir:");
            opc= sc.nextInt();
            switch(opc){
                case 0:
                        System.out.println("Gracias por usar el programa.");
                    break;
                    case 1:
                        System.out.println("CELSIUS A FAHREMHEIT");
                        System.out.print("Introduzca la temperatura en Celsius: ");
                        numero=sc.nextDouble();
                        dos.writeInt(opc);
                        dos.writeDouble(numero);
                        System.out.println(dis.readDouble());
                    break;
                    case 2:
                        System.out.println("FAHERNHEIT A CELSIUS");
                        System.out.print("Introduzaca la temperatura en Faherenheit: ");
                        numero=sc.nextDouble();
                        dos.writeInt(opc);
                        dos.writeDouble(numero);
                        System.out.println(dis.readDouble());
                        
                    break;
                default:
                        System.out.println("Opcion incorrecta, cuelve a selecionar una opcion.");
                    break;
            }
         // }while(opc!=0);

        } catch (IOException e){
            System.out.println("Problema del cliente, try SOCKET:"+e);
        }
    }

}
