package AccesosArchivo;

import clinicaodontologica.Registro;
import java.io.*;

public class AccesoAleatorioFacDet {

   private static RandomAccessFile flujo;
    private static int numeroRegistros;
    private static int tamañoRegistro = 220;

    public static void crearFileFacDet(File archivo) throws IOException {
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

    public static boolean setFacturaDet(int i, Registro Det) throws IOException {
        if(i >= 0 && i <= getNumeroRegistros()) {
            if(Det.getTamaño() > tamañoRegistro) {
                System.out.println("\nTamaño de registro excedido.");
            } else {
                flujo.seek(i*tamañoRegistro);
                //int NFac,String Descripcion,int Cantidad,double PrecioU,boolean Activo
                flujo.writeInt(Det.getNFac());
                flujo.writeUTF(Det.getDescripcion());
                flujo.writeInt(Det.getCantidad());
                flujo.writeDouble(Det.getPrecioU());
                flujo.writeBoolean(Det.getactivo());
                return true;
            }
        } else {
            System.out.println("\nNúmero de registro fuera de límites.");
        }
        return false;
    }
    
    private static int buscarRegistroInactivoP() throws IOException {
        
        for(int i=0; i<getNumeroRegistros(); i++) 
        {
            flujo.seek(i * tamañoRegistro);
            if(!getFacturaDet(i).getactivo()) 
                return i;
        }
        return -1;        
    }
    
    public static boolean eliminarFacCab(int aEliminar) throws IOException {
        int pos = buscarRegistroN(aEliminar);
        if(pos == -1) return false;
        Registro PEliminada = getFacturaDet(pos);
        PEliminada.setActivo(false);
        setFacturaDet(pos, PEliminada);
        return true;
    }
    
    public static void compactarArchivo(File archivo) throws IOException {
        crearFileFacDet(archivo); // Abrimos el flujo.
        Registro[] listado = new Registro[numeroRegistros];
        for(int i=0; i<numeroRegistros; ++i)
            listado[i] = getFacturaDet(i);
        cerrar(); // Cerramos el flujo.
        archivo.delete(); // Borramos el archivo.

        File tempo = new File("tempDet.dat");
        crearFileFacDet(tempo); // Como no existe se crea.
        for(Registro Det : listado)
            if(Det.getactivo())
                AñadirFacDet(Det);
        cerrar();
        
        tempo.renameTo(archivo); // Renombramos.
    }
    public static void AñadirFacDet(Registro Det) throws IOException {
        int inactivo = buscarRegistroInactivoP();
        if(setFacturaDet(inactivo==-1?numeroRegistros:inactivo, Det)) 
            numeroRegistros++; 
    }
    public static int getNumeroRegistros() {
        return numeroRegistros;
    }

    public static Registro getFacturaDet(int i) throws IOException {
        if(i >= 0 && i <= getNumeroRegistros()) {
            flujo.seek(i * tamañoRegistro);
            //int NFac,String Descripcion,int Cantidad,double PrecioU,boolean Activo
            return new Registro(flujo.readInt(),flujo.readUTF(),flujo.readInt(),flujo.readDouble(),flujo.readBoolean());
        } else {
            System.out.println("\nNúmero de registro fuera de límites.");
            return null;
        }
    }

    public static int buscarRegistroN(int buscado) throws IOException {
        Registro Det;
        if (buscado ==-1 ) {
            return -1;
        }
        for(int i=0; i<getNumeroRegistros(); i++) {
            flujo.seek(i * tamañoRegistro);
            Det = getFacturaDet(i);
            if(Det.getNFac()==buscado&& Det.getactivo()) {
                return i;
            }
        }
        return -1;
    }
    
}