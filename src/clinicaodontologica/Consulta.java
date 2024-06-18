/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicaodontologica;

import AccesosArchivo.AccesoAleatorioCita;
import AccesosArchivo.AccesoAleatorioConsulta;
import AccesosArchivo.AccesoAleatorioDoctor;
import AccesosArchivo.AccesoAleatorioPaciente;
import java.io.File;
import java.io.IOException;


/**
 *
 * @author William
 */
public class Consulta {
    private String Codigo;//Tamaño 100 bytes
    private String NombreD;//Tamaño 100 bytes
    private String CIDoc;//Tamaño 20 bytes
    private String Especialidad;//Tamaño 50 bytes
    private String Fecha;//Tamaño 20 bytes
    private String CIPa;//Tamaño 20 bytes
    private String NombrePa;//Tamaño 100 bytes
    private String Telefono;//Tamaño 20 bytes
    private String FechaNacimiento;//Tamaño 20 bytes
    private String CodigoC;//Tamaño 20 bytes
    private String Motivo;//Tamaño 100 bytes
    private String Observacion;//Tamaño 400 bytes
    private boolean realizado;
    private boolean activo;//Tamaño 400 bytes
    public Consulta(){
        
    }
    public Consulta(String Codigo,String NombreD,String CIDoc,String Especialidad,String Fecha,String CIPa,String NombrePa,String Telefono,String FechaNacimiento,String CodigoC,String Motivo,String Observacion,boolean realizado,boolean activo){
        this.Codigo=Codigo;
        this.NombreD=NombreD;
        this.CIDoc=CIDoc;
        this.Especialidad=Especialidad;
        this.Fecha=Fecha;
        this.CIPa=CIPa;
        this.NombrePa=NombrePa;
        this.Telefono=Telefono;
        this.FechaNacimiento=FechaNacimiento;
        this.CodigoC=CodigoC;
        this.Motivo=Motivo;
        this.Observacion=Observacion;
        this.realizado=realizado;
        this.activo=activo;
    }
    public void setCodigo(String Codigo){
        this.Codigo=Codigo;
    }
    public void setNombreD(String NombreD){
        this.NombreD=NombreD;
    }
    public void setCIDoc(String CIDoc){
        this.CIDoc=CIDoc;
    }
    public void setEspecialidad(String Especialidad){
        this.Especialidad=Especialidad;
    }
    public void setFecha(String Fecha){
        this.Fecha=Fecha;
    }
    public void setCIPa(String CIPa){
        this.CIPa=CIPa;
    }
    public void setNombrePa(String NombrePa){
        this.NombrePa=NombrePa;
    }
    public void setTelefono(String Telefono){
        this.Telefono=Telefono;
    }
    public void setFechaNacimiento(String FechaNacimiento){
        this.FechaNacimiento=FechaNacimiento;
    }
    public void setCodigoC(String CodigoC){
        this.CodigoC=CodigoC;
    }
    public void setMotivo(String Motivo){
        this.Motivo=Motivo;
    }
    public void setObservacion(String Observacion){
        this.Observacion=Observacion;
    }
    public void setRealizado(boolean realizado){
        this.realizado=realizado;
    }
    public void setActivo(boolean activo){
        this.activo=activo;
    }
    public String getCodigo(){
        return this.Codigo;
    }
    public String getNombreD(){
        return this.NombreD;
    }
    public String getCIDoc(){
        return this.CIDoc;
    }
    public String getEspecialidad(){
        return this.Especialidad;
    }
    public String getFecha(){
        return this.Fecha;
    }
    public String getCIPa(){
        return this.CIPa;
    }
    public String getNombrePa(){
        return this.NombrePa;
    }
    public String getTelefono(){
        return this.Telefono;
    }
    public String getFechaNacimiento(){
        return this.FechaNacimiento;
    }
    public String getCodigoC(){
        return this.CodigoC;
    }
    public String getMotivo(){
        return this.Motivo;
    }
    public String getObservacion(){
        return this.Observacion;
    }
    public boolean getRealizado(){
        return this.realizado;
    }
    public boolean getActivo(){
        return this.activo;
    }
    public int getTamaño(){
        return this.getCodigo().length()*2+this.getNombreD().length()*2+this.CIDoc.length()*2+this.getEspecialidad().length()*2+this.getFecha().length()*2+this.getCIPa().length()*2+this.getNombrePa().length()*2+this.getTelefono().length()*2+this.getFechaNacimiento().length()*2+this.getCodigoC().length()*2+this.getMotivo().length()*2+this.getObservacion().length()*2+1+1;
    }
    public boolean Registrar( String Codigo,String Observacion, String CodigoLC){
        boolean cond;
        try {
            AccesoAleatorioConsulta.crearFileConsulta( new File("Consulta.dat") );
            AccesoAleatorioCita.crearFileCita( new File("Cita.dat") );
            AccesoAleatorioPaciente.crearFilePaciente( new File("Paciente.dat") );
            AccesoAleatorioDoctor.crearFileDoctor( new File("Doctor.dat") );
            Cita CM= AccesoAleatorioCita.getCita(AccesoAleatorioCita.buscarRegistroID(CodigoLC));
            Paciente Pa=AccesoAleatorioPaciente.getPaciente(AccesoAleatorioPaciente.buscarRegistroC(CM.getCedulaP()));
            Doctor D =AccesoAleatorioDoctor.getDoctor(AccesoAleatorioDoctor.buscarRegistroID(CM.getUsuarioD()));
            String NombreD= D.getNombre().trim()+" "+D.getApellido().trim();
            String CIDoc= D.getCedula().trim();
            String Especialidad= D.getEspecialidad().trim();
            String Fecha=CM.getFecha().trim();
            String CIPa= Pa.getCedula().trim();
            String NombrePa= Pa.getNombre().trim()+" "+Pa.getApellido().trim();
            String Telefono= Pa.getTelefono().trim();
            String FechaNacimiento= Pa.getFechaNacimiento().trim();
            String CodigoC= CM.getCodigo().trim();
            String Motivo= CM.getMotivo().trim();
            AccesoAleatorioConsulta.AñadirConsulta(new Consulta (Codigo, NombreD, CIDoc, Especialidad, Fecha, CIPa, NombrePa, Telefono, FechaNacimiento, CodigoC, Motivo, Observacion,true,true) );
            AccesoAleatorioCita.cerrar();
            AccesoAleatorioPaciente.cerrar();
            AccesoAleatorioDoctor.cerrar();
            AccesoAleatorioConsulta.cerrar();
            cond=true;

        } catch (IOException ex) {
            cond=false;
        }
        return cond;
    }
    public boolean Actualizar(String Codigo,String Observacion){
        boolean cond;
        try {
            int posicion;
            AccesoAleatorioConsulta.crearFileConsulta( new File("Consulta.dat") );
            AccesoAleatorioCita.crearFileCita( new File("Cita.dat") );
            posicion=AccesoAleatorioConsulta.buscarRegistroID(Codigo);
            Consulta C = AccesoAleatorioConsulta.getConsulta(posicion);
            Consulta C1= new Consulta(C.getCodigo(),C.getNombreD(),C.getCIDoc(),C.getEspecialidad(),C.getFecha(),C.getCIPa(),C.getNombrePa(),C.getTelefono(),C.getFechaNacimiento(),C.getCodigoC(),C.getMotivo(),Observacion,true,true);
            AccesoAleatorioConsulta.setConsulta(posicion, C1);
            AccesoAleatorioConsulta.cerrar();
            AccesoAleatorioCita.cerrar();
            cond=true;
        } catch (IOException ex) {
            cond=false;
        } 
        return cond;
    }
    
}
