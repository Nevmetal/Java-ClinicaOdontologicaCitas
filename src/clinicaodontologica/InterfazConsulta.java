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
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author William
 */
public class InterfazConsulta extends javax.swing.JFrame {

    /**
     * Creates new form Consulta
     */
    public InterfazConsulta() {
        initComponents();
        ListarCodigo();
       
    }
    public void ListarCodigo(){
        try {
            SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date fecha = new Date();
            String FechaActual= dFormat.format(fecha);
            boxCodCita.removeAllItems();
            AccesoAleatorioCita.crearFileCita( new File("Cita.dat") );
            for (int i = 0; i < AccesoAleatorioCita.getNumeroRegistros() ; i++) {
            Cita CM =AccesoAleatorioCita.getCita(i);
            if(CM.getActivo()&& FechaActual.equals(CM.getFecha())){
                boxCodCita.addItem(CM.getCodigo());
            }
                                        
            }
            
            AccesoAleatorioCita.cerrar();
            
        } catch (IOException ex) {
            Logger.getLogger(RegistroD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setCampos(){
        try {
            AccesoAleatorioCita.crearFileCita( new File("Cita.dat") );
            AccesoAleatorioPaciente.crearFilePaciente( new File("Paciente.dat") );
            int pos= AccesoAleatorioCita.buscarRegistroID(boxCodCita.getSelectedItem().toString());
            Cita CM= AccesoAleatorioCita.getCita(AccesoAleatorioCita.buscarRegistroID(boxCodCita.getSelectedItem().toString()));
            Paciente Pa=AccesoAleatorioPaciente.getPaciente(AccesoAleatorioPaciente.buscarRegistroC(CM.getCedulaP()));
            txtNombreP.setText(Pa.getNombre()+" "+Pa.getApellido());
            txtMotivoC.setText(CM.getMotivo());
            txtFechaC.setText(CM.getFecha());
            txtHoraC.setText(CM.getHora());
            AccesoAleatorioCita.cerrar();
            AccesoAleatorioPaciente.cerrar();
            
        } catch (IOException ex) {
            Logger.getLogger(InterfazConsulta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void cargarcodigo(){
        int j;
        String num="";
        String c = "";
        try {
            boolean cond=false;
            AccesoAleatorioConsulta.crearFileConsulta( new File("Consulta.dat") );
            for (int i = 0; i < AccesoAleatorioConsulta.getNumeroRegistros() ; i++) {
            Consulta C =AccesoAleatorioConsulta.getConsulta(i);
            if(!C.getActivo()){
               c = C.getCodigo();
               cond=true;
               break;
            }else if(i==AccesoAleatorioConsulta.getNumeroRegistros()-1){
                c = C.getCodigo();
            }
            }
            
       if(c=="")
            {
                txtCodigoC.setText("C00001");
            }else {
                String r ="";
                if(cond){
                    char r1=c.charAt(1);
                    char r2=c.charAt(2);
                    char r3=c.charAt(3);
                    char r4=c.charAt(4);
                    char r5=c.charAt(5);
                    
                    r = "" +r1+r2+r3+r4+r5;
                    j=Integer.parseInt(r);
                    GenerarCodigo gen = new GenerarCodigo();
                    gen.generar(j-1);
                    txtCodigoC.setText("C"+gen.serie());
                }else{
                    char r1=c.charAt(1);
                    char r2=c.charAt(2);
                    char r3=c.charAt(3);
                    char r4=c.charAt(4);
                    char r5=c.charAt(5);
                    r = "" +r1+r2+r3+r4+r5;
                    j=Integer.parseInt(r);
                    GenerarCodigo gen = new GenerarCodigo();
                    gen.generar(j);
                    txtCodigoC.setText("C"+gen.serie());
                }
                
                
            }
       AccesoAleatorioConsulta.cerrar();
        } catch (IOException ex) {
            Logger.getLogger(InterfazCita.class.getName()).log(Level.SEVERE, null, ex);
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
                //PdfWriter.getInstance(documento, new FileOutputStream(C.getNombrePa()+"_"+C.getCIPa()+"_"+j+".pdf"));
                if(j==0){
                    PdfWriter.getInstance(documento, new FileOutputStream(C.getNombrePa()+"_"+C.getCIPa()+".pdf"));
                }
                
                //PdfWriter writer = PdfWriter.getInstance(documento,new FileOutputStream(C.getNombrePa()+"_"+C.getCIPa()+".pdf"));
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
                
                //documento.close();
                //documento.add(Chunk.NEXTPAGE);
                
            }
            
            //documento.close();
            }
            documento.close();
            
            
            
            AccesoAleatorioConsulta.cerrar();
            JOptionPane.showMessageDialog(null, "Reporte creado.");
        } catch (DocumentException | HeadlessException | FileNotFoundException e) {
        }catch (IOException ex) {
                 Logger.getLogger(InterfazConsulta.class.getName()).log(Level.SEVERE, null, ex);
             }
    }
    public void RegistrarConsulta(){
        
        String Codigo= txtCodigoC.getText().trim();
        String Observacion=txtObservacion.getText();
        String CodigoLC= boxCodCita.getSelectedItem().toString();
        Consulta C = new Consulta();
        if(C.Registrar(Codigo, Observacion, CodigoLC)){
            JOptionPane.showMessageDialog(this, "El registro se realizó correctamente.", "Notificación", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this, "Error en la escritura de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        /*
        try {
            AccesoAleatorioConsulta.crearFileConsulta( new File("Consulta.dat") );
            AccesoAleatorioCita.crearFileCita( new File("Cita.dat") );
            AccesoAleatorioPaciente.crearFilePaciente( new File("Paciente.dat") );
            AccesoAleatorioDoctor.crearFileDoctor( new File("Doctor.dat") );
            Cita CM= AccesoAleatorioCita.getCita(AccesoAleatorioCita.buscarRegistroID(CodigoLC));
            Paciente Pa=AccesoAleatorioPaciente.getPaciente(AccesoAleatorioPaciente.buscarRegistroC(CM.getCedulaP()));
            Doctor D =AccesoAleatorioDoctor.getDoctor(AccesoAleatorioDoctor.buscarRegistroID(CM.getUsuarioD()));
            //String Codigo= txtCodigoC.getText().trim();
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
            //String Observacion=txtObservacion.getText();
            AccesoAleatorioConsulta.AñadirConsulta(new Consulta (Codigo, NombreD, CIDoc, Especialidad, Fecha, CIPa, NombrePa, Telefono, FechaNacimiento, CodigoC, Motivo, Observacion,true) );
            AccesoAleatorioCita.cerrar();
            AccesoAleatorioPaciente.cerrar();
            AccesoAleatorioDoctor.cerrar();
            AccesoAleatorioConsulta.cerrar();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error en la escritura de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }*/
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
        boxCodCita = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNombreP = new javax.swing.JTextField();
        txtMotivoC = new javax.swing.JTextField();
        txtFechaC = new javax.swing.JTextField();
        txtHoraC = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservacion = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtCodigoC = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consulta");

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("CONSULTA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setText("CODIGO CITA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(boxCodCita, gridBagConstraints);

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

        jLabel3.setText("Fecha");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        jLabel4.setText("Hora");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel4, gridBagConstraints);

        jLabel5.setText("Nombre Paciente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel5, gridBagConstraints);

        jLabel6.setText("Motivo de la Cita");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel6, gridBagConstraints);

        txtNombreP.setEditable(false);
        txtNombreP.setMinimumSize(new java.awt.Dimension(150, 20));
        txtNombreP.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        jPanel1.add(txtNombreP, gridBagConstraints);

        txtMotivoC.setEditable(false);
        txtMotivoC.setMinimumSize(new java.awt.Dimension(150, 20));
        txtMotivoC.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        jPanel1.add(txtMotivoC, gridBagConstraints);

        txtFechaC.setEditable(false);
        txtFechaC.setMinimumSize(new java.awt.Dimension(100, 20));
        txtFechaC.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(txtFechaC, gridBagConstraints);

        txtHoraC.setEditable(false);
        txtHoraC.setMinimumSize(new java.awt.Dimension(50, 20));
        txtHoraC.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        jPanel1.add(txtHoraC, gridBagConstraints);

        jLabel7.setText("Observacion:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel7, gridBagConstraints);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(200, 100));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(200, 100));

        txtObservacion.setColumns(20);
        txtObservacion.setRows(5);
        jScrollPane1.setViewportView(txtObservacion);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 0, 0);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jButton2.setText("Generar Reporte");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jButton2, gridBagConstraints);

        jLabel8.setText("Codigo Consulta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel8, gridBagConstraints);

        txtCodigoC.setEditable(false);
        txtCodigoC.setMinimumSize(new java.awt.Dimension(100, 20));
        txtCodigoC.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(txtCodigoC, gridBagConstraints);

        jButton3.setText("Modificar Consulta");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        jPanel1.add(jButton3, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        setCampos();
        cargarcodigo();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        cargarcodigo();
        RegistrarConsulta();
        GenerarReporte();
        
        

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        InterfazModificarConsulta IC = new InterfazModificarConsulta();
        IC.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(InterfazConsulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfazConsulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfazConsulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazConsulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfazConsulta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> boxCodCita;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtCodigoC;
    private javax.swing.JTextField txtFechaC;
    private javax.swing.JTextField txtHoraC;
    private javax.swing.JTextField txtMotivoC;
    private javax.swing.JTextField txtNombreP;
    private javax.swing.JTextArea txtObservacion;
    // End of variables declaration//GEN-END:variables
}
