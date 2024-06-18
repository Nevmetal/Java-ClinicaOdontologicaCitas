package AccesosArchivo;

import clinicaodontologica.Factura;
import java.io.*;

public class AccesoAleatorioFacCab {

    private static RandomAccessFile flujo;
    private static int numeroRegistros;
    private static int tamañoRegistro = 710;

    public static void crearFileFacCab(File archivo) throws IOException {
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

    public static boolean setFacturaCab(int i, Factura Cab) throws IOException {
        if(i >= 0 && i <= getNumeroRegistros()) {
            if(Cab.getTamaño() > tamañoRegistro) {
                System.out.println("\nTamaño de registro excedido.");
            } else {
                flujo.seek(i*tamañoRegistro);
                flujo.writeInt(Cab.getNFac());
                flujo.writeUTF(Cab.getCIRuc());
                flujo.writeUTF(Cab.getNombre());
                flujo.writeUTF(Cab.getDireccion());
                flujo.writeUTF(Cab.getTelefono());
                flujo.writeUTF(Cab.getEmail());
                flujo.writeUTF(Cab.getFecha());
                flujo.writeDouble(Cab.getDescuento());
                flujo.writeUTF(Cab.getFPago());
                flujo.writeBoolean(Cab.getactivo());
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
            if(!getFacturaCab(i).getactivo()) 
                return i;
        }
        return -1;        
    }
    
    public static boolean eliminarFacCab(int aEliminar) throws IOException {
        int pos = buscarRegistroN(aEliminar);
        if(pos == -1) return false;
        Factura PEliminada = getFacturaCab(pos);
        PEliminada.setActivo(false);
        setFacturaCab(pos, PEliminada);
        return true;
    }
    
    public static void compactarArchivo(File archivo) throws IOException {
        crearFileFacCab(archivo); // Abrimos el flujo.
        Factura[] listado = new Factura[numeroRegistros];
        for(int i=0; i<numeroRegistros; ++i)
            listado[i] = getFacturaCab(i);
        cerrar(); // Cerramos el flujo.
        archivo.delete(); // Borramos el archivo.

        File tempo = new File("tempCab.dat");
        crearFileFacCab(tempo); // Como no existe se crea.
        for(Factura Cab : listado)
            if(Cab.getactivo())
                AñadirFacCab(Cab);
        cerrar();
        
        tempo.renameTo(archivo); // Renombramos.
    }
    public static void AñadirFacCab(Factura Cab) throws IOException {
        int inactivo = buscarRegistroInactivoP();
        if(setFacturaCab(inactivo==-1?numeroRegistros:inactivo, Cab)) 
            numeroRegistros++; 
    }
    public static int getNumeroRegistros() {
        return numeroRegistros;
    }

    public static Factura getFacturaCab(int i) throws IOException {
        if(i >= 0 && i <= getNumeroRegistros()) {
            flujo.seek(i * tamañoRegistro);
            return new Factura(flujo.readInt(),flujo.readUTF(),flujo.readUTF(),flujo.readUTF(),flujo.readUTF(),flujo.readUTF(),flujo.readUTF(),flujo.readDouble(),flujo.readUTF(),flujo.readBoolean());
        } else {
            System.out.println("\nNúmero de registro fuera de límites.");
            return null;
        }
    }

    public static int buscarRegistroN(int buscado) throws IOException {
        Factura Cab;
        if (buscado ==-1 ) {
            return -1;
        }
        for(int i=0; i<getNumeroRegistros(); i++) {
            flujo.seek(i * tamañoRegistro);
            Cab = getFacturaCab(i);
            if(Cab.getNFac()==buscado&& Cab.getactivo()) {
                return i;
            }
        }
        return -1;
    }
    
    

    
}