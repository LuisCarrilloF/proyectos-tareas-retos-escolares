package modelo;
import java.io.DataInputStream;
import  java.io.InputStream;
import java.io.DataOutputStream;
import java.io.IOError;
import java.io.IOException;

public class Estudiante {
    public final int MAX_SEMESTRE = 12;

    private String numControl;
    private  String nombre;
    private int semestre=1;
    private float  promedio;

    public Estudiante(String numControl)
    {
        this.numControl = numControl;
    }
    public String getnumControl(){
        return this.numControl;

    }
    public String getNombre(){
      return this.nombre;
    }

    public String setNombre(String nombre){
        return this.nombre = nombre;
    }

    public void incrementarSemestre(){
        this.semestre++;
    }
    
    public int getSemestre(){
        return this.semestre;
    }

    /**
     * Establecer el valor del primedio
     * @param promedio nuevo promedio a establecer
     */
    public void setPromedio(float promedio){
        this.promedio = promedio;

    }


    /**
     * Obtiene el valor promedio
     * @return El valor de promedio
     */
    public float getPromedio(){
        return this.promedio;
    }

    public void write(DataOutputStream dos)
        throws IOException
    {
        dos.writeUTF(this.numControl);
        dos.writeUTF(this.nombre);
        dos.writeInt(this.semestre);
        dos.writeFloat(this.promedio);
    }


    public static Estudiante fromDis(DataInputStream dis) 
        throws IOException
    {
        Estudiante e = new Estudiante(dis.readUTF());
        e.nombre = dis.readUTF();
        e.semestre = dis.readInt();
        e.promedio = dis.readFloat();

        return e;
    }

}
