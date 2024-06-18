/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesosArchivo;

import clinicaodontologica.Consulta;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author William
 */
public class AccesoAleatorioConsulta {
    private static RandomAccessFile flujo;
    private static int numeroRegistros;
    private static int tamañoRegistro = 870;

    public static void crearFileConsulta(File archivo) throws IOException {
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

    public static boolean setConsulta(int i, Consulta C) throws IOException {
        if(i >= 0 && i <= getNumeroRegistros()) {
            if(C.getTamaño() > tamañoRegistro) {
                System.out.println("\nTamaño de registro excedido.");
            } else {
                //String Codigo,String Fecha,String Hora,String UsuarioD,String CedulaP
                flujo.seek(i*tamañoRegistro);
                flujo.writeUTF(C.getCodigo());
                flujo.writeUTF(C.getNombreD());
                flujo.writeUTF(C.getCIDoc());
                flujo.writeUTF(C.getEspecialidad());
                flujo.writeUTF(C.getFecha());
                flujo.writeUTF(C.getCIPa());
                flujo.writeUTF(C.getNombrePa());
                flujo.writeUTF(C.getTelefono());
                flujo.writeUTF(C.getFechaNacimiento());
                flujo.writeUTF(C.getCodigoC());
                flujo.writeUTF(C.getMotivo());
                flujo.writeUTF(C.getObservacion());
                flujo.writeBoolean(C.getRealizado());
                flujo.writeBoolean(C.getActivo());
                
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
            if(!getConsulta(i).getActivo()) 
                return i;
        }
        return -1;        
    }
    
    public static boolean eliminarCita(String aEliminar) throws IOException {
        int pos = buscarRegistroID(aEliminar);
        if(pos == -1) return false;
        Consulta CEliminada = getConsulta(pos);
        CEliminada.setActivo(false);
        setConsulta(pos, CEliminada);
        return true;
    }
    
    public static void compactarArchivo(File archivo) throws IOException {
        crearFileConsulta(archivo); // Abrimos el flujo.
        Consulta[] listado = new Consulta[numeroRegistros];
        for(int i=0; i<numeroRegistros; ++i)
            listado[i] = getConsulta(i);
        cerrar(); // Cerramos el flujo.
        archivo.delete(); // Borramos el archivo.

        File tempo = new File("tempCon.dat");
        crearFileConsulta(tempo); // Como no existe se crea.
        for(Consulta C : listado)
            if(C.getActivo())
                AñadirConsulta(C);
        cerrar();
        
        tempo.renameTo(archivo); // Renombramos.
    }
    
    public static void AñadirConsulta(Consulta C) throws IOException {
        int inactivo = buscarRegistroInactivoC();
        if(setConsulta(inactivo==-1?numeroRegistros:inactivo, C)) 
            numeroRegistros++; 
    }
    
    public static int getNumeroRegistros() {
        return numeroRegistros;
    }

    public static Consulta getConsulta(int i) throws IOException {
        if(i >= 0 && i <= getNumeroRegistros()) {
            flujo.seek(i * tamañoRegistro);
            
            return new Consulta(flujo.readUTF(),flujo.readUTF(), flujo.readUTF(), flujo.readUTF(), flujo.readUTF(), flujo.readUTF(),flujo.readUTF(),flujo.readUTF(),flujo.readUTF(),flujo.readUTF(),flujo.readUTF(),flujo.readUTF(), flujo.readBoolean(),flujo.readBoolean());
        } else {
            System.out.println("\nNúmero de registro fuera de límites.");
            return null;
        }
    }

       
    public static int buscarRegistroID(String buscado) throws IOException {
        Consulta C;
        if (buscado == null) {
            return -1;
        }
        for(int i=0; i<getNumeroRegistros(); i++) {
            flujo.seek(i * tamañoRegistro);
            C = getConsulta(i);
            if(C.getCodigo().equals(buscado)&& C.getActivo()) {
                return i;
            }
        }
        return -1;
    }
    public static int buscarRegistroCI(String buscado) throws IOException {
        Consulta C;
        if (buscado == null) {
            return -1;
        }
        for(int i=0; i<getNumeroRegistros(); i++) {
            flujo.seek(i * tamañoRegistro);
            C = getConsulta(i);
            if(C.getCIPa().equals(buscado)&& C.getActivo()) {
                return i;
            }
        }
        return -1;
    }
    public static Consulta[] buscarRegistroT() throws IOException {
        Consulta[] C= new Consulta[numeroRegistros];
        
        for(int i=0; i<getNumeroRegistros(); i++) {
            flujo.seek(i * tamañoRegistro);
            if(getConsulta(i).getActivo()){
                C[i] = getConsulta(i);
            }
           
        }
        return C;
    }
}
