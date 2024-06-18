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
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author William
 */
public class InterfazModificarConsulta extends javax.swing.JFrame {

    /**
     * Creates new form InterfazModificarConsulta
     */
    public InterfazModificarConsulta() {
        initComponents();
        txtCodigoC.setVisible(false);
        txtNombreP.setVisible(false);
        txtMotivoC.setVisible(false);
        txtFechaC.setVisible(false);
        txtHoraC.setVisible(false);
        jScrollPane2.setVisible(false);
        jLabel8.setVisible(false);
        jLabel5.setVisible(false);
        jLabel6.setVisible(false);
        jLabel7.setVisible(false);
        jLabel9.setVisible(false);
        jLabel10.setVisible(false);
        jButton3.setVisible(false);
        btnCancelar.setVisible(false);
    }
    public void listar(){
        DefaultTableModel modelo=(DefaultTableModel)TablaConsulta.getModel();
        int a = TablaConsulta.getRowCount()-1;
        for(int i=a;i>=0;i--){
            modelo.removeRow(modelo.getRowCount()-1);
        }
        Object[] fila =new Object[8];
        try {
            AccesoAleatorioConsulta.crearFileConsulta( new File("Consulta.dat") );
            for (int i = 0; i < AccesoAleatorioConsulta.getNumeroRegistros() ; i++) {
            Consulta C =AccesoAleatorioConsulta.getConsulta(i);
            if(C.getActivo()&& txtCIPa.getText().trim().equals(C.getCIPa())){
                fila[0]=C.getCodigo().trim();
                fila[1]=C.getNombreD().trim();
                fila[2]=C.getCIDoc().trim();
                fila[3]=C.getEspecialidad().trim();
                fila[4]=C.getFecha().trim();
                fila[5]=C.getCIPa().trim();
                fila[6]=C.getNombrePa().trim();
                fila[7]=C.getTelefono().trim();
                
            
                modelo.addRow(fila);
                TablaConsulta.setModel(modelo);
            }
            }
            AccesoAleatorioConsulta.cerrar();
            
        } catch (IOException ex) {
            Logger.getLogger(RegistroD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void ListarCodigo(){
        try {
            
            boxCodConsulta.removeAllItems();
            AccesoAleatorioConsulta.crearFileConsulta( new File("Consulta.dat") );
            AccesoAleatorioPaciente.crearFilePaciente( new File("Paciente.dat") );
            if(AccesoAleatorioPaciente.buscarRegistroC(txtCIPa.getText().trim())>-1){
                for (int i = 0; i < AccesoAleatorioConsulta.getNumeroRegistros() ; i++) {
                Consulta C =AccesoAleatorioConsulta.getConsulta(i);
                if(C.getActivo() && TablaConsulta.getValueAt(0, 5).equals(C.getCIPa())){
                    boxCodConsulta.addItem(C.getCodigo());
                }
                                        
                }
            }else{
                JOptionPane.showMessageDialog(this, "Numero de CI ingresado invalido.", "Notificación", JOptionPane.INFORMATION_MESSAGE);
            }
            
            AccesoAleatorioPaciente.cerrar();
            AccesoAleatorioCita.cerrar();
            
        } catch (IOException ex) {
            Logger.getLogger(RegistroD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setCampos(){
        try {
            txtCodigoC.setVisible(true);
            txtNombreP.setVisible(true);
            txtMotivoC.setVisible(true);
            txtFechaC.setVisible(true);
            txtHoraC.setVisible(true);
            jScrollPane2.setVisible(true);
            jLabel8.setVisible(true);
            jLabel5.setVisible(true);
            jLabel6.setVisible(true);
            jLabel7.setVisible(true);
            jLabel9.setVisible(true);
            jLabel10.setVisible(true);
            jButton3.setVisible(true);
            btnCancelar.setVisible(true);
            AccesoAleatorioConsulta.crearFileConsulta( new File("Consulta.dat") );
            AccesoAleatorioCita.crearFileCita( new File("Cita.dat") );
            int pos= AccesoAleatorioConsulta.buscarRegistroID(boxCodConsulta.getSelectedItem().toString());
            Consulta C = AccesoAleatorioConsulta.getConsulta(pos);
            int posC= AccesoAleatorioCita.buscarRegistroID(C.getCodigoC());
            Cita CT = AccesoAleatorioCita.getCita(posC);
            txtCodigoC.setText(C.getCodigo());
            txtNombreP.setText(C.getNombrePa());
            txtMotivoC.setText(C.getMotivo());
            txtFechaC.setText(C.getFecha());
            txtHoraC.setText(CT.getHora());
            
            AccesoAleatorioConsulta.cerrar();
            AccesoAleatorioCita.cerrar();
            
        } catch (IOException ex) {
            Logger.getLogger(InterfazConsulta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void GenerarReporte(){
         
        try {
            int j=0;
            Document documento = new Document(PageSize.A4);
            AccesoAleatorioConsulta.crearFileConsulta( new File("Consulta.dat") );
            int pos=AccesoAleatorioConsulta.buscarRegistroID(txtCodigoC.getText().trim());
            Consulta C1 =AccesoAleatorioConsulta.getConsulta(pos);
           for (int i = 0; i < AccesoAleatorioConsulta.getNumeroRegistros() ; i++) {
            Consulta C =AccesoAleatorioConsulta.getConsulta(i);
            if(C.getActivo() && C.getCIPa().equals(C1.getCIPa())){
                
                Paragraph Titulo = new Paragraph("Reporte Consulta",FontFactory.getFont(FontFactory.TIMES_ROMAN,18, Font.BOLD, BaseColor.BLACK));
                Paragraph SubTitulo = new Paragraph("Observacion",FontFactory.getFont(FontFactory.TIMES_ROMAN,14, Font.BOLD, BaseColor.BLACK));
                Paragraph Fecha = new Paragraph("Fecha: "+C.getFecha(),FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLACK));
                Paragraph Observacion= new Paragraph(C.getObservacion(),FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.PLAIN, BaseColor.BLACK));
                Observacion.setAlignment(Element.ALIGN_JUSTIFIED);
                Fecha.setAlignment(Element.ALIGN_RIGHT);
                SubTitulo.setAlignment(1);
                Image header = Image.getInstance("src/Imagenes/FondoP.jpeg");
                header.scaleAbsolute(600, 150);
                header.setAlignment(Chunk.ALIGN_CENTER);
                Titulo.setAlignment(1);
                if(j==0){
                    PdfWriter.getInstance(documento, new FileOutputStream(C.getNombrePa()+"_"+C.getCIPa()+".pdf"));
                }
                j++;
                documento.open();
                documento.add(header);
                documento.add(new Paragraph(Titulo));
                documento.add(new Paragraph("Medico: "+C.getNombreD(),FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLACK)));
                documento.add(new Paragraph("Cedula: "+C.getCIDoc(),FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLACK)));
                documento.add(new Paragraph("Especialidad: "+C.getEspecialidad(),FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLACK)));
                documento.add(new Paragraph("Cod: "+C.getCodigo(),FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLACK)));
                documento.add(Chunk.NEWLINE);
                documento.add(new Paragraph(Fecha));
                documento.add(new Paragraph("Cedula: "+C.getCIPa(),FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.PLAIN, BaseColor.BLACK)));
                documento.add(new Paragraph("Paciente: "+C.getNombrePa(),FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.PLAIN, BaseColor.BLACK)));
                documento.add(new Paragraph("Telefono: "+C.getTelefono(),FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.PLAIN, BaseColor.BLACK)));
                documento.add(new Paragraph("Fecha Nacimiento: "+C.getFechaNacimiento(),FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.PLAIN, BaseColor.BLACK)));
                documento.add(new Paragraph("Codido Cita: "+C.getCodigoC(),FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.PLAIN, BaseColor.BLACK)));
                documento.add(new Paragraph("Motivo de la Cita: "+C.getMotivo(),FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.PLAIN, BaseColor.BLACK)));
                documento.add(Chunk.NEWLINE);
                documento.add(new Paragraph(SubTitulo));
                documento.add(new Paragraph(Observacion));
                documento.newPage();
            }
            }
            documento.close();
            
            
            
            AccesoAleatorioConsulta.cerrar();
            
        } catch (DocumentException | HeadlessException | FileNotFoundException e) {
        }catch (IOException ex) {
                 Logger.getLogger(InterfazConsulta.class.getName()).log(Level.SEVERE, null, ex);
             }
    }
    public void ActualizarConsulta(){
        String Codigo=txtCodigoC.getText();
        String Observacion= txtObservacion.getText();
        Consulta C = new Consulta();
        if(C.Actualizar(Codigo, Observacion)){
            JOptionPane.showMessageDialog(this, "Se  actualizo correctamente el registro.", "Modificacion correcta", JOptionPane.INFORMATION_MESSAGE);
            GenerarReporte();
        }else{
            JOptionPane.showMessageDialog(this, "Error en la modificacion de registros.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        /*
        try {
            int posicion;
            AccesoAleatorioConsulta.crearFileConsulta( new File("Consulta.dat") );
            AccesoAleatorioCita.crearFileCita( new File("Cita.dat") );
            posicion=AccesoAleatorioConsulta.buscarRegistroID(Codigo);
            Consulta C = AccesoAleatorioConsulta.getConsulta(posicion);
            Consulta C1= new Consulta(C.getCodigo(),C.getNombreD(),C.getCIDoc(),C.getEspecialidad(),C.getFecha(),C.getCIPa(),C.getNombrePa(),C.getTelefono(),C.getFechaNacimiento(),C.getCodigoC(),C.getMotivo(),Observacion,true);
            AccesoAleatorioConsulta.setConsulta(posicion, C1);
            AccesoAleatorioConsulta.cerrar();
            AccesoAleatorioCita.cerrar();
            JOptionPane.showMessageDialog(this, "Se  actualizo correctamente el registro.", "Modificacion correcta", JOptionPane.INFORMATION_MESSAGE);
            GenerarReporte();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error en la modificacion de registros.", "Error", JOptionPane.ERROR_MESSAGE);
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
        jLabel2 = new javax.swing.JLabel();
        txtCIPa = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaConsulta = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        boxCodConsulta = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtCodigoC = new javax.swing.JTextField();
        txtNombreP = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMotivoC = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtFechaC = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtHoraC = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObservacion = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Modificar Consulta");

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Modificar Consulta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setText("C.I. Paciente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        txtCIPa.setMinimumSize(new java.awt.Dimension(100, 20));
        txtCIPa.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(txtCIPa, gridBagConstraints);

        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jButton1, gridBagConstraints);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(600, 275));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(600, 275));

        TablaConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo Consulta", "Nombre Doctor", "C.I. Doctor", "Especialidad", "Fecha", "C.I. Paciente", "Nombre Paciente", "Telefono"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TablaConsulta);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jButton2.setText("Aceptar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        jPanel1.add(jButton2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        jPanel1.add(boxCodConsulta, gridBagConstraints);

        jLabel3.setText("Codigo Consulta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        jLabel4.setText("Seleccione el Codigo de la Consulta para Modificar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel4, gridBagConstraints);

        jLabel8.setText("Codigo Consulta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel8, gridBagConstraints);

        txtCodigoC.setEditable(false);
        txtCodigoC.setMinimumSize(new java.awt.Dimension(100, 20));
        txtCodigoC.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(txtCodigoC, gridBagConstraints);

        txtNombreP.setEditable(false);
        txtNombreP.setMinimumSize(new java.awt.Dimension(150, 20));
        txtNombreP.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        jPanel1.add(txtNombreP, gridBagConstraints);

        jLabel5.setText("Nombre Paciente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel5, gridBagConstraints);

        txtMotivoC.setEditable(false);
        txtMotivoC.setMinimumSize(new java.awt.Dimension(150, 20));
        txtMotivoC.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        jPanel1.add(txtMotivoC, gridBagConstraints);

        jLabel6.setText("Motivo de la Cita");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel6, gridBagConstraints);

        jLabel7.setText("Fecha");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel7, gridBagConstraints);

        txtFechaC.setEditable(false);
        txtFechaC.setMinimumSize(new java.awt.Dimension(100, 20));
        txtFechaC.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(txtFechaC, gridBagConstraints);

        jLabel9.setText("Hora");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel9, gridBagConstraints);

        txtHoraC.setEditable(false);
        txtHoraC.setMinimumSize(new java.awt.Dimension(50, 20));
        txtHoraC.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        jPanel1.add(txtHoraC, gridBagConstraints);

        jLabel10.setText("Observacion:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel10, gridBagConstraints);

        jScrollPane2.setMinimumSize(new java.awt.Dimension(200, 100));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(200, 100));

        txtObservacion.setColumns(20);
        txtObservacion.setRows(5);
        jScrollPane2.setViewportView(txtObservacion);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jScrollPane2, gridBagConstraints);

        jButton3.setText("Modificar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.insets = new java.awt.Insets(14, 0, 14, 0);
        jPanel1.add(jButton3, gridBagConstraints);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 16;
        jPanel1.add(btnCancelar, gridBagConstraints);

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        listar();
        ListarCodigo();
        txtCIPa.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        setCampos();
        jLabel2.setVisible(false);
        txtCIPa.setVisible(false);
        jButton1.setVisible(false);
        jScrollPane1.setVisible(false);
        jLabel4.setVisible(false);
        jLabel3.setVisible(false);
        boxCodConsulta.setVisible(false);
        jButton2.setVisible(false);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        ActualizarConsulta();
        InterfazConsulta Co = new InterfazConsulta();
        Co.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        InterfazConsulta Co = new InterfazConsulta();
        Co.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(InterfazModificarConsulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfazModificarConsulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfazModificarConsulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazModificarConsulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfazModificarConsulta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaConsulta;
    private javax.swing.JComboBox<String> boxCodConsulta;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtCIPa;
    private javax.swing.JTextField txtCodigoC;
    private javax.swing.JTextField txtFechaC;
    private javax.swing.JTextField txtHoraC;
    private javax.swing.JTextField txtMotivoC;
    private javax.swing.JTextField txtNombreP;
    private javax.swing.JTextArea txtObservacion;
    // End of variables declaration//GEN-END:variables
}
