/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicaodontologica;

import AccesosArchivo.AccesoAleatorioFacCab;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author William
 */
public class Factura {
    private int NFac;//Tamaño 4 bytes
    private String CIRuc;//Tamaño 26 bytes
    private String Nombre;//Tamaño 200 bytes
    private String Direccion;//Tamaño 200 bytes
    private String Telefono;//Tamaño 30 bytes
    private String Email;//Tamaño 200 bytes
    private String Fecha;//Tamaño 20 bytes
    private double Descuento;//Tamaño 8 bytes
    
    private String FPago;//Tamaño 20 bytes
    private boolean Activo;
    public Factura(){
        
    }
    public Factura(int NFac,String CIRuc,String Nombre,String Direccion,String Telefono,String Email,String Fecha,double Descuento,String FPago,boolean Activo){
        this.NFac=NFac;
        this.CIRuc=CIRuc;
        this.Nombre=Nombre;
        this.Direccion=Direccion;
        this.Telefono=Telefono;
        this.Email=Email;
        this.Fecha=Fecha;
        this.Descuento=Descuento;
        this.FPago=FPago;
        this.Activo=Activo;
        
    }
    public void setNFac(int NFac){
        this.NFac=NFac;
    }
    public void setCIRuc(String CIRuc){
        this.CIRuc=CIRuc;
    }
    public void setNombre(String Nombre){
        this.Nombre=Nombre;
    }
    public void setDireccion(String Direccion){
        this.Direccion=Direccion;
    }
    public void setTelefono(String Telefono){
        this.Telefono=Telefono;
    }
    public void setEmail(String Email){
        this.Email=Email;
    }
    public void setFecha(String Fecha){
        this.Fecha= Fecha;
    }
    public void setDescuento(double Descuento){
        this.Descuento=Descuento;
    }
    public void setFPago(String FPago){
        this.FPago=FPago;
    }
    public void setActivo(boolean Activo){
        this.Activo=Activo;
    }
    public int getNFac(){
        return this.NFac;
    }
    public String getCIRuc(){
        return this.CIRuc;
    }
    public String getNombre(){
        return this.Nombre;
    }
    public String getDireccion(){
        return this.Direccion;
    }
    public String getTelefono(){
        return this.Telefono;
    }
    public String getEmail(){
        return this.Email;
    }
    public String getFecha(){
        return this.Fecha;
    }
    public double getDescuento(){
        return this.Descuento;
    }
    public String getFPago( ){
        return this.FPago;
    }
    public boolean getactivo(){
        return this.Activo;
    }
    public int getTamaño(){
        return this.getCIRuc().length()*2+this.getNombre().length()*2+this.getDireccion().length()*2+this.getTelefono().length()*2+this.getEmail().length()*2+this.getFecha().length()*2+this.getFPago().length()*2+4+8+1;
    }
    public boolean Registrar(int NFac,String CIRuc,String Nombre,String Direccion,String Telefono,String Email,String Fecha,double Descuento,String FPago){
        boolean cond;
        try {
            
            AccesoAleatorioFacCab.crearFileFacCab( new File("FacCab.dat") );
            AccesoAleatorioFacCab.AñadirFacCab(new Factura (NFac, CIRuc,Nombre, Direccion, Telefono, Email, Fecha, Descuento,FPago,true) );
            AccesoAleatorioFacCab.cerrar();
            cond=true;
        } catch (IOException ex) {
            cond=false;
        }
        return cond;
    }
    
}
