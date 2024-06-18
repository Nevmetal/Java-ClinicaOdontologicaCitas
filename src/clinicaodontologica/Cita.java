/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicaodontologica;

import AccesosArchivo.AccesoAleatorioCita;
import java.io.File;
import java.io.IOException;


/**
 *
 * @author William
 */
public class Cita {
    private String Codigo;
    private String Fecha;
    private String Hora;
    private String CodigoD;
    private String CedulaP;
    private String Motivo;
    private double Precio;
    private boolean activo;
    public Cita(){}
    public Cita(String Codigo,String Fecha,String Hora,String CodigoD,String CedulaP,String Motivo,double Precio,boolean activo){
        this.Codigo=Codigo;
        this.Fecha=Fecha;
        this.Hora=Hora;
        this.CodigoD=CodigoD;
        this.CedulaP=CedulaP;
        this.Motivo=Motivo;
        this.Precio=Precio;
        this.activo=activo;
    }
    public String getCodigo(){
        return this.Codigo;
    }
    public String getFecha(){
        return this.Fecha;
    }
    public String getHora(){
        return this.Hora;
    }
    public String getUsuarioD(){
        return this.CodigoD;
    }
    public String getCedulaP(){
        return this.CedulaP;
    }
    public String getMotivo(){
        return this.Motivo;
    }
    public double getPrecio(){
        return this.Precio;
    }
    public boolean getActivo(){
        return this.activo;
    }
    public void setCodigo(String Codigo){
        this.Codigo=Codigo;
    }
    public void setFecha(String Fecha){
        this.Fecha=Fecha;
    }
    public void setHora(String Hora){
        this.Hora=Hora;
    }
    public void setUsuarioD(String UsuarioD){
        this.CodigoD=UsuarioD;
    }
    public void setCedulaP(String CedulaP){
        this.CedulaP=CedulaP;
    }
    public void setMotivo(String Motivo){
        this.Motivo=Motivo;
    }
    public void setActivo(boolean activo){
        this.activo=activo;
    }
    public void setPrecio(double Precio){
        this.Precio=Precio;
    }
    public int getTamaño(){
        return this.getCodigo().length()*2+this.getFecha().length()*2+this.getHora().length()*2+this.getUsuarioD().length()*2+this.getCodigo().length()*2+this.getMotivo().length()*2+8+1;
    }
    public boolean Registrar(String Codigo,String Fecha,String Hora,String CodigoD,String CedulaP,String Motivo, double Precio){
        boolean cond;
        try {
            AccesoAleatorioCita.crearFileCita( new File("Cita.dat") );
            AccesoAleatorioCita.AñadirCita(new Cita ( Codigo, Fecha, Hora, CodigoD, CedulaP,Motivo,Precio,true) );
            AccesoAleatorioCita.cerrar();
            cond=true;
        } catch (IOException ex) {
            cond=false;
        }
        return cond;
    }
    public boolean Eliminar(String Codigo){
        boolean cond;
        try {
            AccesoAleatorioCita.crearFileCita( new File("Cita.dat") );
            if( AccesoAleatorioCita.eliminarCita(Codigo) ){
                 cond=true;
            }else{
                cond=false;
            }
        } catch (IOException ex) {
            cond=false;
        } 
        return cond;
    }
    public boolean Actualizar(String Codigo,String Fecha,String Hora,String CodigoD,String CedulaP,String Motivo,double Precio){
        boolean cond;
        try {
            int posicion;
            AccesoAleatorioCita.crearFileCita( new File("Cita.dat") );
            posicion=AccesoAleatorioCita.buscarRegistroID(Codigo);
            Cita C = new Cita ( Codigo, Fecha, Hora, CodigoD, CedulaP,Motivo,Precio,true);
            AccesoAleatorioCita.setCita(posicion, C );
            AccesoAleatorioCita.cerrar();
            cond=true;
            
        } catch (IOException ex) {
            cond=false;
        } 
        return cond;
    }
    public boolean Verificar(String Fecha,String Hora){
        boolean cond= false;
        try {
            AccesoAleatorioCita.crearFileCita( new File("Cita.dat") );
            for (int i = 0; i < AccesoAleatorioCita.getNumeroRegistros() ; i++) {
            Cita CM =AccesoAleatorioCita.getCita(i);
            if(CM.getFecha().equals(Fecha) && CM.getHora().equals(Hora)&& CM.getActivo()){
                cond= true;
                break;
            }
            }
            
            AccesoAleatorioCita.cerrar();
            
        } catch (IOException ex) {
            cond= false;
        }
        return cond;
    }
    public boolean Verificar1(String Fecha,String Hora,String Codigo){
        boolean cond= false;
        try {
            AccesoAleatorioCita.crearFileCita( new File("Cita.dat") );
            for (int i = 0; i < AccesoAleatorioCita.getNumeroRegistros() ; i++) {
            Cita CM =AccesoAleatorioCita.getCita(i);
            if(CM.getFecha().equals(Fecha) && CM.getHora().equals(Hora)&& CM.getActivo()&& !CM.getCodigo().equals(Codigo)){
                cond= true;
                break;
            }
            }
            
            AccesoAleatorioCita.cerrar();
            
        } catch (IOException ex) {
            cond= false;
        }
        return cond;
    }
    
}
