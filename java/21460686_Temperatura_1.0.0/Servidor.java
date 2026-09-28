import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
    public static double  FAREN_CELSIUS(double numero)
    {
       double fahrenheit=numero;
        return (fahrenheit - 32) * 5.0 / 9.0;
    }

    public static double CELSIUS_FAREN(double numero)
    {
       double celsius=(numero * 9.0 / 5.0)+32;
        return celsius;
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
            
        int opc=dis.readInt();
        double numero=dis.readDouble();
        Thread.sleep(200);
        switch(opc){
            case 0:
                break;
            case 1:
                
                dos.writeDouble(ServidorHilo.CELSIUS_FAREN(numero));
                break;
            case 2:
           
            dos.writeDouble(ServidorHilo.FAREN_CELSIUS(numero));
                break;
            default:
                break;
        }   
    }catch (IOException ioex){
        System.out.println(ioex.getMessage());
    } catch (InterruptedException iex) {
        System.out.println(iex.getMessage());
    }
    }
    
    
}