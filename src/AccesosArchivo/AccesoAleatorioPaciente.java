package AccesosArchivo;

import clinicaodontologica.Paciente;
import java.io.*;

public class AccesoAleatorioPaciente {

    private static RandomAccessFile flujo;
    private static int numeroRegistros;
    private static int tamañoRegistro = 190;

    public static void crearFilePaciente(File archivo) throws IOException {
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

    public static boolean setPaciente(int i, Paciente Pa) throws IOException {
        if(i >= 0 && i <= getNumeroRegistros()) {
            if(Pa.getTamaño() > tamañoRegistro) {
                System.out.println("\nTamaño de registro excedido.");
            } else {
                flujo.seek(i*tamañoRegistro);
                flujo.writeUTF(Pa.getCedula());
                flujo.writeUTF(Pa.getNombre());
                flujo.writeUTF(Pa.getApellido());
                flujo.writeUTF(Pa.getFechaNacimiento());
                flujo.writeChar(Pa.getGenero());
                flujo.writeUTF(Pa.getCodigo());
                flujo.writeUTF(Pa.getTelefono());
                flujo.writeBoolean(Pa.getActivo());
                return true;
            }
        } else {
            System.out.println("\nNúmero de registro fuera de límites.");
        }
        return false;
    }
    private static int buscarRegistroInactivoP() throws IOException {
        String Cedula;
        for(int i=0; i<getNumeroRegistros(); i++) 
        {
            flujo.seek(i * tamañoRegistro);
            if(!getPaciente(i).getActivo()) 
                return i;
        }
        return -1;        
    }
    
    public static boolean eliminarPaciente(String aEliminar) throws IOException {
        int pos = buscarRegistroC(aEliminar);
        if(pos == -1) return false;
        Paciente PEliminada = getPaciente(pos);
        PEliminada.setActivo(false);
        setPaciente(pos, PEliminada);
        return true;
    }
    
    public static void compactarArchivo(File archivo) throws IOException {
        crearFilePaciente(archivo); // Abrimos el flujo.
        Paciente[] listado = new Paciente[numeroRegistros];
        for(int i=0; i<numeroRegistros; ++i)
            listado[i] = getPaciente(i);
        cerrar(); // Cerramos el flujo.
        archivo.delete(); // Borramos el archivo.

        File tempo = new File("tempPa.dat");
        crearFilePaciente(tempo); // Como no existe se crea.
        for(Paciente Pa : listado)
            if(Pa.getActivo())
                AñadirPaciente(Pa);
        cerrar();
        
        tempo.renameTo(archivo); // Renombramos.
    }
    public static void AñadirPaciente(Paciente Pa) throws IOException {
        int inactivo = buscarRegistroInactivoP();
        if(setPaciente(inactivo==-1?numeroRegistros:inactivo, Pa)) 
            numeroRegistros++; 
    }
    public static int getNumeroRegistros() {
        return numeroRegistros;
    }

    public static Paciente getPaciente(int i) throws IOException {
        if(i >= 0 && i <= getNumeroRegistros()) {
            flujo.seek(i * tamañoRegistro);
            return new Paciente(flujo.readUTF(), flujo.readUTF(), flujo.readUTF(), flujo.readUTF(), flujo.readChar(), flujo.readUTF(),flujo.readUTF(),flujo.readBoolean());
        } else {
            System.out.println("\nNúmero de registro fuera de límites.");
            return null;
        }
    }

    public static int buscarRegistroC(String buscado) throws IOException {
        Paciente Pa;
        if (buscado == null) {
            return -1;
        }
        for(int i=0; i<getNumeroRegistros(); i++) {
            flujo.seek(i * tamañoRegistro);
            Pa = getPaciente(i);
            if(Pa.getCedula().equals(buscado)&& Pa.getActivo()) {
                return i;
            }
        }
        return -1;
    }
    
    public static int buscarRegistroID(String buscado) throws IOException {
        Paciente Pa;
        if (buscado == null) {
            return -1;
        }
        for(int i=0; i<getNumeroRegistros(); i++) {
            flujo.seek(i * tamañoRegistro);
            Pa = getPaciente(i);
            if(Pa.getCodigo().equals(buscado)&& Pa.getActivo()) {
                return i;
            }
        }
        return -1;
    }
    public static Paciente[] buscarRegistroT() throws IOException {
        Paciente[] Pa= new Paciente[numeroRegistros];
        
        for(int i=0; i<getNumeroRegistros(); i++) {
            flujo.seek(i * tamañoRegistro);
            Pa[i] = getPaciente(i);
        }
        return Pa;
    }
    
}