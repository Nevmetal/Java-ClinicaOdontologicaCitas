/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesosArchivo;

import clinicaodontologica.Cita;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author William
 */
public class AccesoAleatorioCita {
    private static RandomAccessFile flujo;
    private static int numeroRegistros;
    private static int tamañoRegistro = 210;

    public static void crearFileCita(File archivo) throws IOException {
        if (archivo.exists() && !archivo.isFile()) {
            throw new IOException(archivo.getName() + " no es un archivo");
        }
        flujo = new RandomAccessFile(archivo, "rw");
        numeroRegistros = (int) Math.ceil(
                (double) flujo.length() / (double) tamañoRegistro);
       
    }

    public static void cerrar() throws IOException {
        flujo.close();
    }

    public static boolean setCita(int i, Cita CM) throws IOException {
        if(i >= 0 && i <= getNumeroRegistros()) {
            if(CM.getTamaño() > tamañoRegistro) {
                System.out.println("\nTamaño de registro excedido.");
            } else {
                //String Codigo,String Fecha,String Hora,String UsuarioD,String CedulaP
                flujo.seek(i*tamañoRegistro);
                flujo.writeUTF(CM.getCodigo());
                flujo.writeUTF(CM.getFecha());
                flujo.writeUTF(CM.getHora());
                flujo.writeUTF(CM.getUsuarioD());
                flujo.writeUTF(CM.getCedulaP());
                flujo.writeUTF(CM.getMotivo());
                flujo.writeDouble(CM.getPrecio());
                flujo.writeBoolean(CM.getActivo());
                
                return true;
            }
        } else {
            System.out.println("\nNúmero de registro fuera de límites.");
        }
        return false;
    }

    private static int buscarRegistroInactivoC() throws IOException {
        
        for(int i=0; i<getNumeroRegistros(); i++) 
        {
            flujo.seek(i * tamañoRegistro);
            if(!getCita(i).getActivo()) 
                return i;
        }
        return -1;        
    }
    
    public static boolean eliminarCita(String aEliminar) throws IOException {
        int pos = buscarRegistroID(aEliminar);
        if(pos == -1) return false;
        Cita CEliminada = getCita(pos);
        CEliminada.setActivo(false);
        setCita(pos, CEliminada);
        return true;
    }
    
    public static void compactarArchivo(File archivo) throws IOException {
        crearFileCita(archivo); // Abrimos el flujo.
        Cita[] listado = new Cita[numeroRegistros];
        for(int i=0; i<numeroRegistros; ++i)
            listado[i] = getCita(i);
        cerrar(); // Cerramos el flujo.
        archivo.delete(); // Borramos el archivo.

        File tempo = new File("tempCita.dat");
        crearFileCita(tempo); // Como no existe se crea.
        for(Cita CM : listado)
            if(CM.getActivo())
                AñadirCita(CM);
        cerrar();
        
        tempo.renameTo(archivo); // Renombramos.
    }
    
    public static void AñadirCita(Cita CM) throws IOException {
        int inactivo = buscarRegistroInactivoC();
        if(setCita(inactivo==-1?numeroRegistros:inactivo, CM)) 
            numeroRegistros++; 
    }
    
    public static int getNumeroRegistros() {
        return numeroRegistros;
    }

    public static Cita getCita(int i) throws IOException {
        if(i >= 0 && i <= getNumeroRegistros()) {
            flujo.seek(i * tamañoRegistro);
            
            return new Cita(flujo.readUTF(), flujo.readUTF(), flujo.readUTF(), flujo.readUTF(), flujo.readUTF(),flujo.readUTF(),flujo.readDouble(), flujo.readBoolean());
        } else {
            System.out.println("\nNúmero de registro fuera de límites.");
            return null;
        }
    }

       
    public static int buscarRegistroID(String buscado) throws IOException {
        Cita CM;
        if (buscado == null) {
            return -1;
        }
        for(int i=0; i<getNumeroRegistros(); i++) {
            flujo.seek(i * tamañoRegistro);
            CM = getCita(i);
            if(CM.getCodigo().equals(buscado)&& CM.getActivo()) {
                return i;
            }
        }
        return -1;
    }
    public static Cita[] buscarRegistroT() throws IOException {
        Cita[] CM= new Cita[numeroRegistros];
        
        for(int i=0; i<getNumeroRegistros(); i++) {
            flujo.seek(i * tamañoRegistro);
            if(getCita(i).getActivo()){
                CM[i] = getCita(i);
            }
           
        }
        return CM;
    }
}
