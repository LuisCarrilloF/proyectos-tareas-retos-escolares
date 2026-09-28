import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente 
{
    
    
    public static void main(String[] args) {
        int cadena=Cadena(); //Llamamos a la funcion para ver la longitud de la cadena.
        try (Socket socket = new Socket("127.0.0.1", 3000)) { //Direccion a la que se va conectar y porque puerto.
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            dos.writeInt(cadena); //Se la manda al seridor la cadena.
            System.out.println(dis.readUTF());
        } catch (IOException e){
            System.out.println(e);
        }
    }

    /**
     * Regresa la longitud de la cadena aleatoria.
     * @return i cadena aleatoria.
     */
    public static  int Cadena(){ 
        Scanner sc = new Scanner(System.in);
        System.out.print("Tamaño de la cadena:");
        int i= sc.nextInt();
        return i;
    }

}
