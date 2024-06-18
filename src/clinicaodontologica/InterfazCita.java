/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicaodontologica;

import AccesosArchivo.AccesoAleatorioPaciente;
import AccesosArchivo.AccesoAleatorioDoctor;
import AccesosArchivo.AccesoAleatorioCita;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author William
 */
public class InterfazCita extends javax.swing.JFrame {

    /**
     * Creates new form Cita
     */
    public InterfazCita() {
        initComponents();
        ListarDoc();
        btnGenerarC.setEnabled(false);
        txtMotivo.setVisible(false);
        cargarcodigo();
        this.setLocationRelativeTo(null);
    }
    public void ListarDoc(){
        try {
            boxLDoctor.removeAllItems();
            AccesoAleatorioDoctor.crearFileDoctor( new File("Doctor.dat") );
            for (int i = 0; i < AccesoAleatorioDoctor.getNumeroRegistros() ; i++) {
            Doctor D =AccesoAleatorioDoctor.getDoctor(i);
            if(D.getActivo()){
                boxLDoctor.addItem(D.getCodigo());
            }
                                        
            }
            
            AccesoAleatorioDoctor.cerrar();
            
        } catch (IOException ex) {
            Logger.getLogger(RegistroD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean VerificarFecha(){
        SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
        String Fecha= dFormat.format(FechaCita.getDate());
        if(Fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso la fecha de la cita!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return true;
        }
        String Hora=boxHora.getSelectedItem().toString();
        Cita CM = new Cita();
        return CM.Verificar(Fecha, Hora);
                
        /*
        try {
            SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
            String Fecha= dFormat.format(FechaCita.getDate());
            if(Fecha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "¡No se ingreso la fecha de la cita!", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return true;
            }
            
            String Hora=boxHora.getSelectedItem().toString();
            AccesoAleatorioCita.crearFileCita( new File("Cita.dat") );
            for (int i = 0; i < AccesoAleatorioCita.getNumeroRegistros() ; i++) {
            Cita CM =AccesoAleatorioCita.getCita(i);
            if(CM.getFecha().equals(Fecha) && CM.getHora().equals(Hora)&& CM.getActivo()){
                return true;
            }
            
                                 
            }
            
            AccesoAleatorioDoctor.cerrar();
            
        } catch (IOException ex) {
            Logger.getLogger(InterfazCita.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
    }
    public void RegistrarCita(){
        String Codigo = txtCodigoC.getText().trim();
        if(Codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso el codigo del paciente!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String CedulaP = txtCedulaCita.getText().trim();
        if(CedulaP.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso la cedula del paciente!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }else if(CedulaP.length()<10 || CedulaP.length()>10){
            JOptionPane.showMessageDialog(this, "¡El numero de cedula necesita tener 10 caracteres!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }  
        SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
        String Fecha= dFormat.format(FechaCita.getDate());
        if(Fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso la fecha de la cita!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String Motivo="";
        if(CheckM.isSelected()){
            Motivo=txtMotivo.getText().trim();
            if(Motivo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso el Motivo de la Cita!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        }else{
            Motivo=boxMotivo.getSelectedItem().toString();
        }
        String _Precio = txtPrecio.getText().trim();
        if(_Precio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso el Precio de la  Cita!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        double Precio;
        
        try {
            Precio = Double.parseDouble(_Precio);
        } catch(NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un valor númerico para el precio de la cita.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
            
       
                
        String CodigoD= boxLDoctor.getSelectedItem().toString();
        String Hora=boxHora.getSelectedItem().toString();
        Cita CM = new Cita();
            
        if(CM.Registrar(Codigo, Fecha, Hora, CodigoD, CedulaP, Motivo,Precio)){
            JOptionPane.showMessageDialog(this, "El registro se realizó correctamente.", "Notificación", JOptionPane.INFORMATION_MESSAGE);
            txtCodigoC.setText("");
            txtCedulaCita.setText("");
            txtNomP.setText("");
            txtNomD.setText("");
            txtMotivo.setText("");
        }else{
            JOptionPane.showMessageDialog(this, "Error en la escritura de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        /*
        try {
            AccesoAleatorioCita.crearFileCita( new File("Cita.dat") );
            AccesoAleatorioCita.AñadirCita(new Cita ( Codigo, Fecha, Hora, CodigoD, CedulaP,Motivo,true) );
            AccesoAleatorioCita.cerrar();
            JOptionPane.showMessageDialog(this, "El registro se realizó correctamente.", "Notificación", JOptionPane.INFORMATION_MESSAGE);
            txtCodigoC.setText("");
            txtCedulaCita.setText("");
            txtNomP.setText("");
            txtNomD.setText("");
            txtMotivo.setText("");
            
            //listar();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error en la escritura de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }*/
    }
    public void VerInfo(){
        try {
            
            AccesoAleatorioDoctor.crearFileDoctor( new File("Doctor.dat") );
            for (int i = 0; i < AccesoAleatorioDoctor.getNumeroRegistros() ; i++) {
            Doctor D =AccesoAleatorioDoctor.getDoctor(i);
            if(D.getActivo() && D.getCodigo().equals(boxLDoctor.getSelectedItem().toString().trim())){
                txtNomD.setText(D.getNombre()+" "+D.getApellido());
                break;
            }
            
                                        
            }
            AccesoAleatorioDoctor.cerrar();
            AccesoAleatorioPaciente.crearFilePaciente( new File("Paciente.dat") );
            for (int i = 0; i < AccesoAleatorioPaciente.getNumeroRegistros() ; i++) {
            Paciente Pa =AccesoAleatorioPaciente.getPaciente(i);
               if(Pa.getActivo() && Pa.getCedula().equals(txtCedulaCita.getText().trim())){
                txtNomP.setText(Pa.getNombre()+" "+Pa.getApellido());
                break;
                }         
            
            }
            AccesoAleatorioPaciente.cerrar();
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(InterfazCita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void cargarcodigo(){
        int j;
        String num="";
        String c = "";
        try {
            boolean cond=false;
            AccesoAleatorioCita.crearFileCita( new File("Cita.dat") );
            for (int i = 0; i < AccesoAleatorioCita.getNumeroRegistros() ; i++) {
            Cita CM =AccesoAleatorioCita.getCita(i);
            if(!CM.getActivo()){
               c = CM.getCodigo();
               cond=true;
               break;
            }else if(i==AccesoAleatorioCita.getNumeroRegistros()-1){
                c = CM.getCodigo();
            }
            }
       if(c=="")
            {
                txtCodigoC.setText("CT00001");
            }else {
                if(cond){
                    char r1=c.charAt(2);
                    char r2=c.charAt(3);
                    char r3=c.charAt(4);
                    char r4=c.charAt(5);
                    char r5=c.charAt(6);
                    String r ="";
                    r = "" +r1+r2+r3+r4+r5;
                    j=Integer.parseInt(r);
                    GenerarCodigo gen = new GenerarCodigo();
                    gen.generar(j-1);
                    txtCodigoC.setText("CT"+gen.serie());
                }else{
                    char r1=c.charAt(2);
                    char r2=c.charAt(3);
                    char r3=c.charAt(4);
                    char r4=c.charAt(5);
                    char r5=c.charAt(6);
                    String r ="";
                    r = "" +r1+r2+r3+r4+r5;
                    j=Integer.parseInt(r);
                    GenerarCodigo gen = new GenerarCodigo();
                    gen.generar(j);
                    txtCodigoC.setText("CT"+gen.serie());
                }
                
                
            }
       AccesoAleatorioCita.cerrar();
        } catch (IOException ex) {
            Logger.getLogger(InterfazCita.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCedulaCita = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        FechaCita = new com.toedter.calendar.JDateChooser();
        btnVerificar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        boxHora = new javax.swing.JComboBox<>();
        boxLDoctor = new javax.swing.JComboBox<>();
        btnReservas = new javax.swing.JButton();
        btnGenerarC = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNomP = new javax.swing.JTextField();
        txtNomD = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCodigoC = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        boxMotivo = new javax.swing.JComboBox<>();
        CheckM = new javax.swing.JCheckBox();
        txtMotivo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Asignacion De Cita");

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Asignacion de Cita");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setText("Cedula Paciente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        jLabel3.setText("ID Doctor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(13, 13, 13, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        txtCedulaCita.setMinimumSize(new java.awt.Dimension(100, 20));
        txtCedulaCita.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jPanel1.add(txtCedulaCita, gridBagConstraints);

        jLabel4.setText("Asignar Fecha");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel4, gridBagConstraints);

        FechaCita.setMinimumSize(new java.awt.Dimension(100, 20));
        FechaCita.setPreferredSize(new java.awt.Dimension(100, 20));
        FechaCita.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                FechaCitaPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        jPanel1.add(FechaCita, gridBagConstraints);

        btnVerificar.setText("Verificar");
        btnVerificar.setToolTipText("Verifica si el horario de la Cita esta Libre, en caso de estar libre se muestra los nombres y se habilita el boton \"Generar Cita\"");
        btnVerificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerificarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 5;
        jPanel1.add(btnVerificar, gridBagConstraints);

        jLabel5.setText("Hora");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel5, gridBagConstraints);

        boxHora.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8:30 am", "9:00 am", "9:30 am", "10:00 am", "10:30 am", "11:00 am", "11:30 am", "2:00 pm", "2:30 pm", "3:00 pm", "3:30 pm", "4:00 pm", "4:30 pm", "5:00 pm", "5:30 pm", "6:00 pm" }));
        boxHora.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                boxHoraItemStateChanged(evt);
            }
        });
        boxHora.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                boxHoraPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(boxHora, gridBagConstraints);

        boxLDoctor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                boxLDoctorItemStateChanged(evt);
            }
        });
        boxLDoctor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxLDoctorActionPerformed(evt);
            }
        });
        boxLDoctor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                boxLDoctorPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(boxLDoctor, gridBagConstraints);

        btnReservas.setText("Ver Citas Reservadas");
        btnReservas.setToolTipText("");
        btnReservas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservasActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 13, 0, 0);
        jPanel1.add(btnReservas, gridBagConstraints);

        btnGenerarC.setText("Generar Cita");
        btnGenerarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarCActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        jPanel1.add(btnGenerarC, gridBagConstraints);

        jLabel6.setText("Nombre Paciente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel6, gridBagConstraints);

        jLabel7.setText("Nombre Doctor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel7, gridBagConstraints);

        txtNomP.setEditable(false);
        txtNomP.setMinimumSize(new java.awt.Dimension(100, 20));
        txtNomP.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        jPanel1.add(txtNomP, gridBagConstraints);

        txtNomD.setEditable(false);
        txtNomD.setMinimumSize(new java.awt.Dimension(100, 20));
        txtNomD.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 11;
        jPanel1.add(txtNomD, gridBagConstraints);

        jLabel8.setText("Codigo Cita");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel8, gridBagConstraints);

        txtCodigoC.setEditable(false);
        txtCodigoC.setMinimumSize(new java.awt.Dimension(100, 20));
        txtCodigoC.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPanel1.add(txtCodigoC, gridBagConstraints);

        jButton1.setText("VerMedicos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 13, 0, 0);
        jPanel1.add(jButton1, gridBagConstraints);

        jLabel9.setText("Motivo de la Cita");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel9, gridBagConstraints);

        boxMotivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ortodoncia", "Aclaramiento dental", "Ortopedia Funciona de los Maxilares", "Odontopediatria", "Implantes Dentales", "Cirugia Oral" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        jPanel1.add(boxMotivo, gridBagConstraints);

        CheckM.setText("Otro");
        CheckM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckMActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        jPanel1.add(CheckM, gridBagConstraints);

        txtMotivo.setMinimumSize(new java.awt.Dimension(100, 20));
        txtMotivo.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        jPanel1.add(txtMotivo, gridBagConstraints);

        jLabel10.setText("Precio Cita");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel10, gridBagConstraints);

        txtPrecio.setMinimumSize(new java.awt.Dimension(100, 20));
        txtPrecio.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        jPanel1.add(txtPrecio, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boxLDoctorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxLDoctorActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_boxLDoctorActionPerformed

    private void btnGenerarCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarCActionPerformed
        // TODO add your handling code here:
        RegistrarCita();
        btnGenerarC.setEnabled(false);
        cargarcodigo();
        
    }//GEN-LAST:event_btnGenerarCActionPerformed

    private void btnVerificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerificarActionPerformed
        // TODO add your handling code here:
        if(!VerificarFecha()){
            VerInfo();
            
          btnGenerarC.setEnabled(true);
          
          
          JOptionPane.showMessageDialog(this, "Fecha & Hora Disponible", "Notificación", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this, "Fecha & Hora Ocupado", "Notificación", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }//GEN-LAST:event_btnVerificarActionPerformed

    private void btnReservasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservasActionPerformed
        // TODO add your handling code here:
        RevisionCita RC =new RevisionCita();
        RC.setVisible(true);
    }//GEN-LAST:event_btnReservasActionPerformed

    private void FechaCitaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_FechaCitaPropertyChange
        // TODO add your handling code here:
        btnGenerarC.setEnabled(false);
        
    }//GEN-LAST:event_FechaCitaPropertyChange

    private void boxHoraPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_boxHoraPropertyChange
        // TODO add your handling code here:
      
        
    }//GEN-LAST:event_boxHoraPropertyChange

    private void boxHoraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_boxHoraItemStateChanged
        // TODO add your handling code here:
        btnGenerarC.setEnabled(false);
    }//GEN-LAST:event_boxHoraItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        ListaDoctor LD= new ListaDoctor();
        LD.setVisible(true);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void boxLDoctorPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_boxLDoctorPropertyChange
        // TODO add your handling code here:
        
    }//GEN-LAST:event_boxLDoctorPropertyChange

    private void boxLDoctorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_boxLDoctorItemStateChanged
        // TODO add your handling code here:
        btnGenerarC.setEnabled(false);
    }//GEN-LAST:event_boxLDoctorItemStateChanged

    private void CheckMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckMActionPerformed
        // TODO add your handling code here:
        if(!CheckM.isSelected()){
            txtMotivo.setVisible(false);
        }else{
            txtMotivo.setVisible(true);
        }
        
    }//GEN-LAST:event_CheckMActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfazCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfazCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfazCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfazCita().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CheckM;
    public com.toedter.calendar.JDateChooser FechaCita;
    private javax.swing.JComboBox<String> boxHora;
    private javax.swing.JComboBox<String> boxLDoctor;
    private javax.swing.JComboBox<String> boxMotivo;
    private javax.swing.JButton btnGenerarC;
    private javax.swing.JButton btnReservas;
    private javax.swing.JButton btnVerificar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtCedulaCita;
    private javax.swing.JTextField txtCodigoC;
    private javax.swing.JTextField txtMotivo;
    private javax.swing.JTextField txtNomD;
    private javax.swing.JTextField txtNomP;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
