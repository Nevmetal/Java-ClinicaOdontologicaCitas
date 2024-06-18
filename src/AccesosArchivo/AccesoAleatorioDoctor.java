package AccesosArchivo;

import clinicaodontologica.Doctor;
import java.io.*;

public class AccesoAleatorioDoctor {

    private static RandomAccessFile flujo;
    private static int numeroRegistros;
    private static int tamañoRegistro = 220;

    public static void crearFileDoctor(File archivo) throws IOException {
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

    public static boolean setDoctor(int i, Doctor D) throws IOException {
        if(i >= 0 && i <= getNumeroRegistros()) {
            if(D.getTamaño() > tamañoRegistro) {
                System.out.println("\nTamaño de registro excedido.");
            } else {
                flujo.seek(i*tamañoRegistro);
                flujo.writeUTF(D.getCedula());
                flujo.writeUTF(D.getNombre());
                flujo.writeUTF(D.getApellido());
                flujo.writeUTF(D.getFechaNacimiento());
                flujo.writeChar(D.getGenero());
                flujo.writeUTF(D.getCodigo());
                flujo.writeUTF(D.getEspecialidad());
                flujo.writeBoolean(D.getActivo());
                return true;
            }
        } else {
            System.out.println("\nNúmero de registro fuera de límites.");
        }
        return false;
    }

    private static int buscarRegistroInactivoD() throws IOException {
        
        for(int i=0; i<getNumeroRegistros(); i++) 
        {
            flujo.seek(i * tamañoRegistro);
            if(!getDoctor(i).getActivo()) 
                return i;
        }
        return -1;        
    }
    
    public static boolean eliminarDoctor(String aEliminar) throws IOException {
        int pos = buscarRegistroC(aEliminar);
        if(pos == -1) return false;
        Doctor DEliminada = getDoctor(pos);
        DEliminada.setActivo(false);
        setDoctor(pos, DEliminada);
        return true;
    }
    
    public static void compactarArchivo(File archivo) throws IOException {
        crearFileDoctor(archivo); // Abrimos el flujo.
        Doctor[] listado = new Doctor[numeroRegistros];
        for(int i=0; i<numeroRegistros; ++i)
            listado[i] = getDoctor(i);
        cerrar(); // Cerramos el flujo.
        archivo.delete(); // Borramos el archivo.

        File tempo = new File("tempDoc.dat");
        crearFileDoctor(tempo); // Como no existe se crea.
        for(Doctor D : listado)
            if(D.getActivo())
                AñadirDoctor(D);
        cerrar();
        
        tempo.renameTo(archivo); // Renombramos.
    }
    
    public static void AñadirDoctor(Doctor D) throws IOException {
        int inactivo = buscarRegistroInactivoD();
        if(setDoctor(inactivo==-1?numeroRegistros:inactivo, D)) 
            numeroRegistros++; 
    }
    
    public static int getNumeroRegistros() {
        return numeroRegistros;
    }

    public static Doctor getDoctor(int i) throws IOException {
        if(i >= 0 && i <= getNumeroRegistros()) {
            flujo.seek(i * tamañoRegistro);
            
            return new Doctor(flujo.readUTF(), flujo.readUTF(), flujo.readUTF(), flujo.readUTF(), flujo.readChar(), flujo.readUTF(), flujo.readUTF(),flujo.readBoolean());
        } else {
            System.out.println("\nNúmero de registro fuera de límites.");
            return null;
        }
    }

    public static int buscarRegistroC(String buscado) throws IOException {
        Doctor D;
        if (buscado == null) {
            return -1;
        }
        for(int i=0; i<getNumeroRegistros(); i++) {
            flujo.seek(i * tamañoRegistro);
            D = getDoctor(i);
            if(D.getCedula().equals(buscado)&& D.getActivo()) {
                return i;
            }
        }
        return -1;
    }
    public static int buscarRegistroEn(String buscado,int i) throws IOException {
        Doctor D;
        if (buscado == null) {
            return -1;
        }
        flujo.seek(i * tamañoRegistro);
        D = getDoctor(i);
        if(D.getEspecialidad().equals(buscado)&& D.getActivo()) {
            return i;
        }
        
        return -1;
    }
    public static int buscarRegistroID(String buscado) throws IOException {
        Doctor D;
        if (buscado == null) {
            return -1;
        }
        for(int i=0; i<getNumeroRegistros(); i++) {
            flujo.seek(i * tamañoRegistro);
            D = getDoctor(i);
            if(D.getCodigo().equals(buscado)&& D.getActivo()) {
                return i;
            }
        }
        return -1;
    }
    public static Doctor[] buscarRegistroT() throws IOException {
        Doctor[] D= new Doctor[numeroRegistros];
        
        for(int i=0; i<getNumeroRegistros(); i++) {
            flujo.seek(i * tamañoRegistro);
            if(getDoctor(i).getActivo()){
                D[i] = getDoctor(i);
            }
           
        }
        return D;
    }
    
}