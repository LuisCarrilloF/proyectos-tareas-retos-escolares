import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;


public class Servidor 
{

    public static void main(String[] args)
    {
        try (ServerSocket server = new ServerSocket(3000)){
            do {
                Socket socket = server.accept();

                Thread hilo = new Thread(new ServidorHilo(socket));
                    hilo.start();
            } while (true);
        }catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        }
            
    }

}

 
class ServidorHilo implements Runnable
{
    Socket socket;
    Scanner sc = new Scanner(System.in);
    public static String randomString(int longitud, String alfabeto){
        Random r = new Random();
        StringBuilder cadenaAleatoria = new StringBuilder();
        for(int i=0; i<longitud; i++){
            int Aleatorio = r.nextInt(alfabeto.length());
            
            // Agregar el carácter aleatorio a la cadena
           cadenaAleatoria.append(alfabeto.charAt(Aleatorio));
        }
            return  cadenaAleatoria.toString();
    }
    public ServidorHilo(Socket socket)
    {
        this.socket = socket;
    }
   
    
    public void run()
    {
        
            try(DataOutputStream dos = new DataOutputStream(socket.getOutputStream()))
            {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
                //RECIBE LA LONGITUD DEL CLIENTE....
            int longitud=dis.readInt();
            String alfabeto = "ABCDEFGHIJKLMNOPQRDTUVWXYZ123456789";
                Thread.sleep(20000);
            dos.writeUTF(ServidorHilo.randomString(longitud,alfabeto));
        }catch (IOException ioex){
            System.out.println(ioex.getMessage());
        } catch (InterruptedException iex) {
            System.out.println(iex.getMessage());
        }
    }
    
    
}