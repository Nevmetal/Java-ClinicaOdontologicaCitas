/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicaodontologica;


import AccesosArchivo.AccesoAleatorioFacDet;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author William
 */
public class Registro {
    private int NFac;//Tamaño 4 bytes
    private String Descripcion;//Tamaño 200 bytes
    private int Cantidad;//Tamaño 4 bytes
    private double PrecioU;//Tamaño 8 bytes
    private boolean Activo;
    public Registro(){
        
    }
    public Registro(int NFac,String Descripcion,int Cantidad,double PrecioU,boolean Activo){
        this.NFac=NFac;
        this.Descripcion=Descripcion;
        this.Cantidad=Cantidad;
        this.PrecioU=PrecioU;
        this.Activo=Activo;
    }
    public void setNFac(int NFac){
        this.NFac=NFac;
    }
    public void setDescripcion(String Descripcion){
        this.Descripcion=Descripcion;
    }
    public void setCantidad(int Cantidad){
        this.Cantidad=Cantidad;
    }
    public void setPrecioU(double PrecioU){
        this.PrecioU=PrecioU;
    }
    public void setActivo(boolean Activo){
        this.Activo=Activo;
    }
    public int getNFac(){
        return this.NFac;
    }
    public String getDescripcion(){
        return this.Descripcion;
    }
    public int getCantidad(){
        return this.Cantidad;
    }
    public double getPrecioU(){
        return this.PrecioU;
    }
    public boolean getactivo(){
        return this.Activo;
    }
    public int getTamaño(){
        return this.getDescripcion().length()*2+4+4+8+1;
    }
    public boolean Registrar(int NFac,String Descripcion,int Cantidad,double PrecioU){
        boolean cond;
        try {
            
            AccesoAleatorioFacDet.crearFileFacDet( new File("FacDet.dat") );
            AccesoAleatorioFacDet.AñadirFacDet(new Registro ( NFac, Descripcion, Cantidad, PrecioU,true) );
            AccesoAleatorioFacDet.cerrar();
            cond=true;
        } catch (IOException ex) {
            cond=false;
        }
        return cond;
    }
}
