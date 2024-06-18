/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicaodontologica;

import AccesosArchivo.AccesoAleatorioDoctor;
import java.io.File;
import java.io.IOException;


/**
 *
 * @author William
 */
public class Doctor extends Persona {
    private String ID;//Tamaño 20 bytes
    private String Especialidad;//Tamaño 50 bytes

    public Doctor(){}
    
    public Doctor(String Cedula, String Nombre, String Apellido, String FechaNacimiento,char Genero,String ID,String Especialidad, boolean activo) {
        super(Cedula, Nombre, Apellido, FechaNacimiento,Genero, activo);
        this.ID=ID;
        this.Especialidad=Especialidad;
    }
    public String getCodigo(){
        return this.ID;
    }
    public String getEspecialidad(){
        return this.Especialidad;
    }
    public void setCodigo(String ID){
        this.ID=ID;
    }
    public void setEspecialidad(String Especialidad){
        this.Especialidad=Especialidad;
    }

    public int getTamaño() {
        return this.getCedula().length()*2+ this.getNombre().length()*2+ this.getApellido().length()*2+this.getFechaNacimiento().length()*2+this.getCodigo().length()*2+this.getEspecialidad().length()*2+2+1;
    }
    public boolean Registrar(String Cedula,String Nombre, String Apellido,String Fecha,char Genero,String Codigo,String Especialidad){
        boolean cond;
        try {
            
            AccesoAleatorioDoctor.crearFileDoctor( new File("Doctor.dat") );
            AccesoAleatorioDoctor.AñadirDoctor(new Doctor (Cedula, Nombre, Apellido, Fecha, Genero,Codigo, Especialidad,true) );
            AccesoAleatorioDoctor.cerrar();
            cond=true;
        } catch (IOException ex) {
            cond=false;
        }
        return cond;
    }
    public boolean Eliminar(String Cedula){
        boolean cond;
        try {
            AccesoAleatorioDoctor.crearFileDoctor( new File("Doctor.dat") );
            if( AccesoAleatorioDoctor.eliminarDoctor(Cedula) ){
                cond=true;
            }else{
                cond=false;
            }
        } catch (IOException ex) {
            cond =false;
        } 
        return cond;
    }
    public boolean Actualizar(String Cedula,String Nombre, String Apellido,String Fecha,char Genero,String Codigo,String Especialidad){
        boolean cond;
        try {
            int posicion;
            AccesoAleatorioDoctor.crearFileDoctor( new File("Doctor.dat") );
            posicion=AccesoAleatorioDoctor.buscarRegistroC(Cedula);
            if(posicion==-1){
               posicion=AccesoAleatorioDoctor.buscarRegistroID(Codigo);
            }
            Doctor D= new Doctor (Cedula, Nombre, Apellido, Fecha, Genero,Codigo, Especialidad,true);
            AccesoAleatorioDoctor.setDoctor(posicion, D);
            AccesoAleatorioDoctor.cerrar();
            cond=true;
            
        } catch (IOException ex) {
            cond=false;
        } 
        return cond;
    }
}
