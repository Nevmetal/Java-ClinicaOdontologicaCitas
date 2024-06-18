/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicaodontologica;

/**
 *
 * @author William
 */
public class Persona{
    protected String Cedula;//Tamaño 20 bytes
    protected String Nombre;//Tamaño 50 bytes
    protected String Apellido;//Tamaño 50 bytes
    protected String FechaNacimiento;//Tamaño 20 bytes
    protected char Genero;//Tamaño 2 Bytes
    protected boolean activo;
    public Persona(){
        Cedula ="NN";
        Nombre = "NN";
        Apellido="NN";
        FechaNacimiento="NN";
        Genero='N';
        activo = true;
    }
    public Persona(String Cedula,String Nombre, String Apellido, String FechaNacimiento,char Genero, Boolean activo){
        this.Cedula=Cedula;
        this.Nombre=Nombre;
        this.Apellido=Apellido;
        this.FechaNacimiento=FechaNacimiento;
        this.Genero=Genero;
        this.activo=activo;
    }
    public String getCedula(){
        return this.Cedula;
    }
    public String getNombre(){
        return this.Nombre;
    }
    public String getApellido(){
        return this.Apellido;
    }
    public String getFechaNacimiento(){
        return this.FechaNacimiento;
    }
    public char getGenero(){
        return this.Genero;
    }
    public boolean getActivo(){
        return this.activo;
    }
    public void setCedula(String Cedula){
        this.Cedula=Cedula;
    }
    public void setNombre(String Nombre){
        this.Nombre=Nombre;
    }
    public void setApellido(String Apellido){
       this.Apellido= Apellido;
    }
    public void setFechaNacimiento(String FechaNacimiento){
       this.FechaNacimiento=FechaNacimiento;
    }
    public void setGenero(char Genero){
       this.Genero=Genero;
    }
    public void setActivo(boolean activo){
       this.activo=activo;
    }
    
}
