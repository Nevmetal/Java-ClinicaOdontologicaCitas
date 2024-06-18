/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicaodontologica;

import AccesosArchivo.AccesoAleatorioPaciente;
import java.io.File;
import java.io.IOException;


/**
 *
 * @author William
 */
public class Paciente extends Persona {
    private String Codigo;//Tamaño 20 bytes
    private String Telefono;//Tamaño 20 bytes
    public Paciente(){}
    public Paciente(String Cedula, String Nombre, String Apellido, String FechaNacimiento,char Genero,String Codigo,String Telefono, boolean activo) {
        super(Cedula, Nombre, Apellido, FechaNacimiento,Genero,activo);
        this.Codigo=Codigo;
        this.Telefono=Telefono;
        
        
    }
    public String getCodigo(){
        return this.Codigo;
    }
    
    public void setCodigo(String Codigo){
        this.Codigo=Codigo;
    }
    public String getTelefono(){
        return this.Telefono;
    }
    public void setTelefono(String Telefono){
        this.Telefono=Telefono;
    }
    
    public int getTamaño() {
        return this.getCedula().length()*2+ this.getNombre().length()*2+ this.getApellido().length()*2+this.getFechaNacimiento().length()*2+this.getCodigo().length()*2+this.getTelefono().length()*2+2+1;
    }
    public boolean Registrar(String Cedula,String Nombre,String Apellido,String Fecha,char Genero,String Codigo,String Telefono){
        boolean cond;
        try {
            AccesoAleatorioPaciente.crearFilePaciente( new File("Paciente.dat") );
            AccesoAleatorioPaciente.AñadirPaciente(new Paciente (Cedula, Nombre, Apellido, Fecha, Genero, Codigo, Telefono,true) );
            AccesoAleatorioPaciente.cerrar();
            cond=true;
        } catch (IOException ex) {
            cond=false;
        }
        return cond;
    }
    public boolean Eliminar(String Cedula){
        boolean cond;
        try {
            AccesoAleatorioPaciente.crearFilePaciente( new File("Paciente.dat") );
            if( AccesoAleatorioPaciente.eliminarPaciente(Cedula) ){
                cond=true;
            }else{
                cond=false;
            }
        } catch (IOException ex) {
            cond=false;
        } 
        return cond;
    }
    public boolean Actualizar(String Cedula,String Nombre,String Apellido,String Fecha,char Genero,String Codigo,String Telefono){
        boolean cond;
        try {
            int posicion;
            AccesoAleatorioPaciente.crearFilePaciente( new File("Paciente.dat") );
            posicion=AccesoAleatorioPaciente.buscarRegistroC(Cedula);
            if(posicion==-1){
               posicion=AccesoAleatorioPaciente.buscarRegistroID(Codigo);
            }
            Paciente Pa= new Paciente (Cedula, Nombre, Apellido, Fecha, Genero,Codigo, Telefono,true);
            AccesoAleatorioPaciente.setPaciente(posicion, Pa);
            AccesoAleatorioPaciente.cerrar();
            cond=true;
        } catch (IOException ex) {
            cond=false;
        } 
        return cond;
    }
}
