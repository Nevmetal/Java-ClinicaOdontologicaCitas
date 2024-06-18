/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicaodontologica;

import AccesosArchivo.AccesoAleatorioCita;
import AccesosArchivo.AccesoAleatorioDoctor;
import AccesosArchivo.AccesoAleatorioPaciente;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author William
 */
public class InterfazModificarCita extends javax.swing.JFrame {

    
    /**
     * Creates new form InterfazModificarCita
     */
    public InterfazModificarCita() {
        initComponents();
        txtMotivo.setVisible(false);
        ListarDoc();
        setCampos();
        
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
        return CM.Verificar1(Fecha, Hora,txtCodigoC.getText());
                
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
    public void Actualizar(){
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
        Cita CM = new Cita();
        if(CM.Actualizar(Codigo, Fecha, Hora, CodigoD, CedulaP, Motivo,Precio)){
            JOptionPane.showMessageDialog(this, "El registro se realizó correctamente.", "Notificación", JOptionPane.INFORMATION_MESSAGE);
            txtCodigoC.setText("");
            txtCedulaCita.setText("");
            txtNomP.setText("");
            txtNomD.setText("");
            txtMotivo.setText("");
        }else{
            JOptionPane.showMessageDialog(this, "Error en la modificacion de registros.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        /*
        try {
            int posicion;
            AccesoAleatorioCita.crearFileCita( new File("Cita.dat") );
            posicion=AccesoAleatorioCita.buscarRegistroID(Codigo);
            Cita C = new Cita ( Codigo, Fecha, Hora, CodigoD, CedulaP,Motivo,true);
            AccesoAleatorioCita.setCita(posicion, C );
            AccesoAleatorioCita.cerrar();
            JOptionPane.showMessageDialog(this, "El registro se realizó correctamente.", "Notificación", JOptionPane.INFORMATION_MESSAGE);
            txtCodigoC.setText("");
            txtCedulaCita.setText("");
            txtNomP.setText("");
            txtNomD.setText("");
            txtMotivo.setText("");
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error en la modificacion de registros.", "Error", JOptionPane.ERROR_MESSAGE);
        } */
    }
    public void setCampos(){
        txtCodigoC.setText(GestionCita.boxCitaM.getSelectedItem().toString());
        
        try {
            AccesoAleatorioCita.crearFileCita( new File("Cita.dat") );
            for (int i = 0; i < AccesoAleatorioCita.getNumeroRegistros() ; i++) {
            Cita CM =AccesoAleatorioCita.getCita(i);
            if(CM.getCodigo().equals(txtCodigoC.getText())){
                txtCedulaCita.setText(CM.getCedulaP());
                boxLDoctor.setSelectedItem(CM.getUsuarioD());
                Date F = new SimpleDateFormat("dd/MM/yyyy").parse(CM.getFecha());
                FechaCita.setDate(F);
                txtPrecio.setText(String.valueOf(CM.getPrecio()));
                boxHora.setSelectedItem(CM.getHora());
                for(int j=0; j<boxMotivo.getItemCount();j++){
                if(boxMotivo.getItemAt(j).trim().equals(CM.getMotivo().trim())){
                    boxMotivo.setSelectedItem(CM.getMotivo());
                    txtMotivo.setText("");
                    txtMotivo.setVisible(false);
                    CheckM.setSelected(false);
                    break;
                }else {
                    CheckM.setSelected(true);
                    txtMotivo.setVisible(true);
                    txtMotivo.setText(CM.getMotivo());
                    
                }
            }
                
                break;
            }
            }
           
            AccesoAleatorioCita.cerrar();
            
        } catch (IOException | ParseException ex) {
            Logger.getLogger(InterfazModificarCita.class.getName()).log(Level.SEVERE, null, ex);
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
        boxLDoctor = new javax.swing.JComboBox<>();
        btnGenerarC = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        boxHora = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtCedulaCita = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnVerificar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnReservas = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        FechaCita = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtNomD = new javax.swing.JTextField();
        txtNomP = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCodigoC = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        boxMotivo = new javax.swing.JComboBox<>();
        CheckM = new javax.swing.JCheckBox();
        txtMotivo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Modificar Cita");

        jPanel1.setLayout(new java.awt.GridBagLayout());

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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(boxLDoctor, gridBagConstraints);

        btnGenerarC.setText("Guardar");
        btnGenerarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarCActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        jPanel1.add(btnGenerarC, gridBagConstraints);

        jLabel6.setText("Nombre Paciente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel6, gridBagConstraints);

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
        jPanel1.add(boxHora, gridBagConstraints);

        jLabel4.setText("Asignar Fecha");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel4, gridBagConstraints);

        txtCedulaCita.setEditable(false);
        txtCedulaCita.setMinimumSize(new java.awt.Dimension(100, 20));
        txtCedulaCita.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jPanel1.add(txtCedulaCita, gridBagConstraints);

        jLabel3.setText("ID Doctor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(13, 13, 13, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

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

        jLabel2.setText("Cedula Paciente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Modificar Cita");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel5.setText("Hora");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel5, gridBagConstraints);

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

        jLabel7.setText("Nombre Doctor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel7, gridBagConstraints);

        txtNomD.setEditable(false);
        txtNomD.setMinimumSize(new java.awt.Dimension(100, 20));
        txtNomD.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        jPanel1.add(txtNomD, gridBagConstraints);

        txtNomP.setEditable(false);
        txtNomP.setMinimumSize(new java.awt.Dimension(100, 20));
        txtNomP.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        jPanel1.add(txtNomP, gridBagConstraints);

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

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 13;
        jPanel1.add(jButton2, gridBagConstraints);

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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boxLDoctorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxLDoctorActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_boxLDoctorActionPerformed

    private void btnGenerarCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarCActionPerformed
        // TODO add your handling code here:
        Actualizar();
        btnGenerarC.setEnabled(false);
        GestionCita GC = new GestionCita();
        GC.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_btnGenerarCActionPerformed

    private void boxHoraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_boxHoraItemStateChanged
        // TODO add your handling code here:
        btnGenerarC.setEnabled(false);
    }//GEN-LAST:event_boxHoraItemStateChanged

    private void boxHoraPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_boxHoraPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_boxHoraPropertyChange

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        ListaDoctor LD= new ListaDoctor();
        LD.setVisible(true);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        GestionCita GC = new GestionCita();
        GC.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(InterfazModificarCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfazModificarCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfazModificarCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazModificarCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfazModificarCita().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CheckM;
    private com.toedter.calendar.JDateChooser FechaCita;
    private javax.swing.JComboBox<String> boxHora;
    private javax.swing.JComboBox<String> boxLDoctor;
    private javax.swing.JComboBox<String> boxMotivo;
    private javax.swing.JButton btnGenerarC;
    private javax.swing.JButton btnReservas;
    private javax.swing.JButton btnVerificar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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
