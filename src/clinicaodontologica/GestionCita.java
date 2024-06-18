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
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author William
 */
public class GestionCita extends javax.swing.JFrame {

    /**
     * Creates new form GestionCita
     */
    public GestionCita() {
        initComponents();
        
    }
    public void ListarBoxCodigo(){
      try {
            boxCitaM.removeAllItems();
            boxCitaE.removeAllItems();
            SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
            String Fecha= dFormat.format(JCita.getDate());
            AccesoAleatorioCita.crearFileCita( new File("Cita.dat") );
            for (int i = 0; i < AccesoAleatorioCita.getNumeroRegistros() ; i++) {
            Cita CM =AccesoAleatorioCita.getCita(i);
            if(CM.getActivo() && CM.getFecha().equals(Fecha)){
                boxCitaM.addItem(CM.getCodigo());
                boxCitaE.addItem(CM.getCodigo());
            }
            }
            AccesoAleatorioCita.cerrar();
            
        } catch (IOException ex) {
            Logger.getLogger(RegistroD.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
     public void listar(){
        DefaultTableModel modelo=(DefaultTableModel)TablaCita.getModel();
        int a = TablaCita.getRowCount()-1;
        for(int i=a;i>=0;i--){
            modelo.removeRow(modelo.getRowCount()-1);
        }
        Object[] fila =new Object[10];
        try {
             SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
            String Fecha= dFormat.format(JCita.getDate());
            AccesoAleatorioDoctor.crearFileDoctor( new File("Doctor.dat") );
            AccesoAleatorioPaciente.crearFilePaciente( new File("Paciente.dat") );
            AccesoAleatorioCita.crearFileCita( new File("Cita.dat") );
            for (int i = 0; i < AccesoAleatorioCita.getNumeroRegistros() ; i++) {
            Cita CM =AccesoAleatorioCita.getCita(i);
            if(CM.getActivo() && CM.getFecha().equals(Fecha)){
                //String Codigo,String Fecha,String Hora,String UsuarioD,String CedulaP,boolean activo
                fila[0]=CM.getCodigo().trim();
                fila[1]=CM.getCedulaP().trim();
                Paciente Pa =AccesoAleatorioPaciente.getPaciente(AccesoAleatorioPaciente.buscarRegistroC(CM.getCedulaP().trim()));
                fila[2]=Pa.getNombre()+" "+Pa.getApellido();
                fila[3]=Pa.getTelefono().trim();
                fila[4]=CM.getMotivo().trim();
                fila[5]=CM.getUsuarioD().trim();
                Doctor D =AccesoAleatorioDoctor.getDoctor(AccesoAleatorioDoctor.buscarRegistroID(CM.getUsuarioD().trim()));
                fila[6]=D.getNombre()+" "+D.getApellido();
                fila[7]=D.getEspecialidad().trim();
                fila[8]=CM.getFecha();
                fila[9]=CM.getHora();
                
            
                modelo.addRow(fila);
                TablaCita.setModel(modelo);
            }
            }
            
            RowSorter<TableModel> sorter = new TableRowSorter<>(modelo);
            TablaCita.setRowSorter(sorter);
            AccesoAleatorioPaciente.cerrar();
            AccesoAleatorioDoctor.cerrar();
            AccesoAleatorioCita.cerrar();
            
        } catch (IOException ex) {
            Logger.getLogger(GestionCita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void EliminarRegistro(){
        String Codigo=boxCitaE.getSelectedItem().toString();
        Cita CM =new Cita();
        if(CM.Eliminar(Codigo)){
            JOptionPane.showMessageDialog(this, "El registro correspondiente fue eliminado correctamente.", "Eliminación correcta", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this, "Error en la eliminación de registros.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        /*
        try {
            AccesoAleatorioCita.crearFileCita( new File("Cita.dat") );
            if( AccesoAleatorioCita.eliminarCita(Codigo) ){
                 JOptionPane.showMessageDialog(this, "El registro correspondiente fue eliminado correctamente.", "Eliminación correcta", JOptionPane.INFORMATION_MESSAGE);
            }
            else JOptionPane.showMessageDialog(this, "Error al intentar eliminar un registro inexistente.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error en la eliminación de registros.", "Error", JOptionPane.ERROR_MESSAGE);
        } */
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
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaCita = new javax.swing.JTable();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        boxCitaM = new javax.swing.JComboBox<>();
        boxCitaE = new javax.swing.JComboBox<>();
        JCita = new com.toedter.calendar.JCalendar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestion de Citas");

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Control De Citas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 15;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 20, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(900, 300));
        jScrollPane1.setName(""); // NOI18N
        jScrollPane1.setPreferredSize(new java.awt.Dimension(900, 300));

        TablaCita.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo Cita", "C.I. Paciente", "Nombre Paciente", "Telefono", "Motivo", "Codigo Medico", "Nombre Medico", "Especialidad", "Fecha", "Hora"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaCita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaCitaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaCita);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 15;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 50, 100);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 13, 0, 0);
        jPanel1.add(btnModificar, gridBagConstraints);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 4;
        jPanel1.add(btnEliminar, gridBagConstraints);

        jLabel2.setText("Codigo Cita: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 20, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        jLabel3.setText("Codigo Cita");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 200, 0, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        boxCitaM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxCitaMActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        jPanel1.add(boxCitaM, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(boxCitaE, gridBagConstraints);

        JCita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JCitaMouseClicked(evt);
            }
        });
        JCita.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                JCitaPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 100);
        jPanel1.add(JCita, gridBagConstraints);

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

    private void TablaCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaCitaMouseClicked

    }//GEN-LAST:event_TablaCitaMouseClicked

    private void JCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JCitaMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_JCitaMouseClicked

    private void JCitaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_JCitaPropertyChange
        // TODO add your handling code here:
        listar();
        ListarBoxCodigo();
        //Ejemp();
    }//GEN-LAST:event_JCitaPropertyChange

    private void boxCitaMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxCitaMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxCitaMActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        if(boxCitaM.getSelectedItem()== null){
            JOptionPane.showMessageDialog(this, "¡Es necesario escoger el Codigo Cita para realizar la modificacion!", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }else{
            InterfazModificarCita IM = new InterfazModificarCita();
            IM.setVisible(true);
            this.dispose();
        }
        
        
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        int resp= JOptionPane.showConfirmDialog(null, "Realmente desea eliminar la cita con el codigo "+ boxCitaE.getSelectedItem().toString()+"?", "Confirmar salida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (resp) {
        case 0:
            EliminarRegistro();
            listar();
            ListarBoxCodigo();
            break;
        case 1:
            JOptionPane.showMessageDialog(this, "No se realizo ningun cambio.", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
            break;
        }
        
    }//GEN-LAST:event_btnEliminarActionPerformed

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
            java.util.logging.Logger.getLogger(GestionCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionCita().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JCalendar JCita;
    private javax.swing.JTable TablaCita;
    private javax.swing.JComboBox<String> boxCitaE;
    public static javax.swing.JComboBox<String> boxCitaM;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
