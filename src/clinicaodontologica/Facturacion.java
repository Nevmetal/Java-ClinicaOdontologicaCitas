/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicaodontologica;



import AccesosArchivo.AccesoAleatorioCita;
import AccesosArchivo.AccesoAleatorioConsulta;
import AccesosArchivo.AccesoAleatorioFacCab;
import AccesosArchivo.AccesoAleatorioFacDet;
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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author William
 */
public class Facturacion extends javax.swing.JFrame {

    private String FPago="";
    /**
     * Creates new form Facturacion
     */
    public Facturacion() {
        initComponents();
        cargarcodigo();
        cargarFechaActual();
    }
    public void cargarFechaActual(){
        SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = new Date();
        String FechaActual= dFormat.format(fecha);
        txtFecha.setText(FechaActual);
    }
    public void cargarcodigo(){
        int j;
        String num="";
        int c = 0;
        try {
            boolean cond=false;
            AccesoAleatorioFacCab.crearFileFacCab( new File("FacCab.dat") );
            for (int i = 0; i < AccesoAleatorioFacCab.getNumeroRegistros() ; i++) {
            Factura Cab =AccesoAleatorioFacCab.getFacturaCab(i);
            if(i==AccesoAleatorioFacCab.getNumeroRegistros()-1){
                c = Cab.getNFac();
            }
            
            }
       if(c==0)
            {
                txtNFactura.setText("00001");
            }else {
                GenerarCodigo gen = new GenerarCodigo();
                gen.generar(c);
                txtNFactura.setText(gen.serie());     
            }
        AccesoAleatorioFacCab.cerrar();
        } catch (IOException ex) {
            Logger.getLogger(InterfazCita.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }
    public void limpiarTabla(){
        DefaultTableModel modelo=(DefaultTableModel)TableDet.getModel();
        int a = TableDet.getRowCount()-1;
        for(int i=a;i>=0;i--){
            modelo.removeRow(modelo.getRowCount()-1);
        }
        for(int i=0;i<15;i++){
            modelo.addRow(new Object[]{"",null,null,null});
            TableDet.setModel(modelo);
        }
        
    }
    public void LimpiarCampos(){
        txtCI.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtTotalP.setText("");
        txtDescuento.setText("0");
        txtSubtotal.setText("");
        txtIVA.setText("");
        txtTotal.setText("");
        CheckE.setEnabled(true);
        CheckCo.setEnabled(true);
        CheckC.setEnabled(true);
        CheckE.setSelected(false);
        CheckCo.setSelected(false);
        CheckC.setSelected(false);
    }
    public void realizarEvento(){
         try{
            TableModel model =TableDet.getModel();
            
            double total=0;
            for(int i=0; i<TableDet.getRowCount();i++){
                String comp1 =model.getValueAt(i, 1).toString();
                String comp2 =model.getValueAt(i, 2).toString();
                if(!comp1.equals("") || !comp2.equals("")){
                    int Cantidad=Integer.parseInt(comp1);
                    double PrecioU=Double.parseDouble(comp2);
                    model.setValueAt(Cantidad*PrecioU, i, 3);
                    total+=Double.parseDouble(model.getValueAt(i, 3).toString());
                    txtTotalP.setText(String.valueOf(total));
                    double _descuento=(Double.parseDouble(txtDescuento.getText().trim())/100)*total;
                    BigDecimal bd1 = new BigDecimal(_descuento).setScale(2, RoundingMode.HALF_UP);
                    double descuento =bd1.doubleValue();
                    BigDecimal bd5 = new BigDecimal(total-descuento).setScale(2, RoundingMode.HALF_UP);
                    double ST =bd5.doubleValue();
                    txtSubtotal.setText(String.valueOf(ST));
                    double _Sub=Double.parseDouble(txtSubtotal.getText().trim());
                    BigDecimal bd2 = new BigDecimal(_Sub).setScale(2, RoundingMode.HALF_UP);
                    double Sub= bd2.doubleValue();
                    BigDecimal bd4 = new BigDecimal(Sub*0.12).setScale(2, RoundingMode.HALF_UP);
                    double Iv= bd4.doubleValue();
                    txtIVA.setText(String.valueOf(Iv));
                    double _IVA=Double.parseDouble(txtIVA.getText().trim());
                    BigDecimal bd3 = new BigDecimal(_IVA).setScale(2, RoundingMode.HALF_UP);
                    //BigDecimal bd3 = new BigDecimal(_IVA).setScale(3, RoundingMode.UNNECESSARY);
                    double IVA= bd3.doubleValue();
                    BigDecimal bd6 = new BigDecimal(Sub+IVA).setScale(2, RoundingMode.HALF_UP);
                    double T= bd6.doubleValue();
                    txtTotal.setText(String.valueOf(T));
                    //txtTotal.setText(String.valueOf(Sub+IVA));
                }

            }
            
            
        }catch (NullPointerException ex){
            
        }
    }
    public void SetDatos(){
        try {
            String Codigoc="";
            AccesoAleatorioConsulta.crearFileConsulta( new File("Consulta.dat") );
            AccesoAleatorioCita.crearFileCita(new File("Cita.dat"));
            AccesoAleatorioPaciente.crearFilePaciente(new File("Paciente.dat"));
            for (int i = 0; i < AccesoAleatorioConsulta.getNumeroRegistros() ; i++) {
            Consulta C=AccesoAleatorioConsulta.getConsulta(i);
            if(C.getRealizado() && C.getCIPa().equals(txtCIPA.getText().trim())){
                Codigoc= C.getCodigoC();
            }
            }
            int pos1=AccesoAleatorioCita.buscarRegistroID(Codigoc);
            Cita CM= AccesoAleatorioCita.getCita(pos1);
            Paciente Pa= AccesoAleatorioPaciente.getPaciente(AccesoAleatorioPaciente.buscarRegistroC(txtCIPA.getText().trim()));
            txtCI.setText(Pa.getCedula());
            txtNombre.setText(Pa.getNombre()+" "+Pa.getApellido());
            txtTelefono.setText(Pa.getTelefono());
            
            DefaultTableModel modelo=(DefaultTableModel)TableDet.getModel();
            int a = TableDet.getRowCount()-1;
            for(int i=a;i>=0;i--){
                modelo.removeRow(modelo.getRowCount()-1);
            }
            Object[] fila =new Object[4];
            
            
            if(CM.getActivo()){
                fila[0]=CM.getMotivo();
                fila[1]=1;
                fila[2]=CM.getPrecio();
                modelo.addRow(fila);
                TableDet.setModel(modelo);
            }
            
       
        } catch (IOException ex) {
            Logger.getLogger(Facturacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void GenerarReporte(){
         
        try {
            int j=0;
            Document documento = new Document(PageSize.A4);
             AccesoAleatorioFacCab.crearFileFacCab( new File("FacCab.dat") );
             AccesoAleatorioFacDet.crearFileFacDet( new File("FacDet.dat") );
            Factura Cab =AccesoAleatorioFacCab.getFacturaCab(AccesoAleatorioFacCab.buscarRegistroN(Integer.parseInt(txtNFactura.getText().trim())));
            Paragraph Titulo = new Paragraph("FACTURA",FontFactory.getFont(FontFactory.TIMES_ROMAN,24, Font.BOLD, BaseColor.BLACK));
                
                Paragraph Fecha = new Paragraph("Fecha: "+Cab.getFecha(),FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLACK));
                
                Fecha.setAlignment(Element.ALIGN_RIGHT);
                Image header = Image.getInstance("src/Imagenes/Diente.png");
                header.scaleAbsolute(50, 50);
                header.setAlignment(Chunk.ALIGN_RIGHT);
                Titulo.setAlignment(Chunk.ALIGN_LEFT);
                PdfWriter.getInstance(documento, new FileOutputStream("F"+txtNFactura.getText().trim()+".pdf"));
                //PdfWriter.getInstance(documento, new FileOutputStream(C.getNombrePa()+"_"+C.getCIPa()+"_"+j+".pdf"));
                /*if(j==0){
                    PdfWriter.getInstance(documento, new FileOutputStream(C.getNombrePa()+"_"+C.getCIPa()+".pdf"));
                }
                */
               
                documento.open();
                documento.add(header);
                documento.add(Titulo);
                documento.add(new Paragraph("PUCEDENT",FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLACK)));
                documento.add(new Paragraph("N° Factura: "+txtNFactura.getText().trim(),FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLACK)));
                documento.add(new Paragraph("Quito ",FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLACK)));
                documento.add(new Paragraph("Telefono: 09XXXXXXXX",FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLACK)));
                documento.add(Fecha);
                documento.add(Chunk.NEWLINE);
                documento.add(new Paragraph("FACTURA A",FontFactory.getFont(FontFactory.TIMES_ROMAN,18, Font.BOLD, BaseColor.BLACK)));
                documento.add(new Paragraph("CI/Ruc: "+Cab.getCIRuc(),FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.PLAIN, BaseColor.BLACK)));
                documento.add(new Paragraph(Cab.getNombre(),FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.PLAIN, BaseColor.BLACK)));
                documento.add(new Paragraph(Cab.getEmail(),FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.PLAIN, BaseColor.BLACK)));
                documento.add(new Paragraph(Cab.getTelefono(),FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.PLAIN, BaseColor.BLACK)));
                documento.add(new Paragraph(Cab.getDireccion(),FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.PLAIN, BaseColor.BLACK)));
                documento.add(Chunk.NEWLINE);
                int column =TableDet.getColumnCount();
                PdfPTable pt= new PdfPTable(column);
                PdfPCell c = new PdfPCell(new Paragraph("Descripcion"));
                c.setColspan(1);
                c.setHorizontalAlignment (Element.ALIGN_CENTER);
                c.setBackgroundColor(BaseColor.LIGHT_GRAY);
                c.setPaddingTop(5);
                c.setPaddingBottom(5);
                pt.addCell(c);
                c = new PdfPCell(new Paragraph("Cantidad"));
                c.setColspan(1);
                c.setHorizontalAlignment (Element.ALIGN_CENTER);
                c.setBackgroundColor(BaseColor.LIGHT_GRAY);
                c.setPaddingTop(5);
                c.setPaddingBottom(5);
                pt.addCell(c);
                c = new PdfPCell(new Paragraph("Precio Unitario"));
                c.setColspan(1);
                c.setHorizontalAlignment (Element.ALIGN_CENTER);
                c.setBackgroundColor(BaseColor.LIGHT_GRAY);
                c.setPaddingTop(5);
                c.setPaddingBottom(5);
                pt.addCell(c);
                c = new PdfPCell(new Paragraph("Total"));
                c.setColspan(1);
                c.setHorizontalAlignment (Element.ALIGN_CENTER);
                c.setBackgroundColor(BaseColor.LIGHT_GRAY);
                c.setPaddingTop(5);
                c.setPaddingBottom(5);
                pt.addCell(c);
                //documento.add(pt);
                
           for (int i = 0; i < AccesoAleatorioFacDet.getNumeroRegistros() ; i++) {
            Registro Det =AccesoAleatorioFacDet.getFacturaDet(i);
            if(Det.getactivo() && Det.getNFac()==Integer.parseInt(txtNFactura.getText().trim())){
                c = new PdfPCell(new Paragraph(Det.getDescripcion()));
                c.setColspan(1);
                c.setPaddingTop(10);
                c.setPaddingBottom(10);
                pt.addCell(c);
                c = new PdfPCell(new Paragraph(""+Det.getCantidad()));
                c.setColspan(1);
                c.setHorizontalAlignment (Element.ALIGN_RIGHT);
                c.setPaddingTop(10);
                c.setPaddingBottom(10);
                pt.addCell(c);
                c = new PdfPCell(new Paragraph("$ "+Det.getPrecioU()));
                c.setColspan(1);
                c.setHorizontalAlignment (Element.ALIGN_RIGHT);
                c.setPaddingTop(10);
                c.setPaddingBottom(10);
                pt.addCell(c);
                c = new PdfPCell(new Paragraph("$ "+TableDet.getValueAt(j, 3)));
                c.setColspan(1);
                c.setHorizontalAlignment (Element.ALIGN_RIGHT);
                c.setPaddingTop(10);
                c.setPaddingBottom(10);
                pt.addCell(c);
                
                j++;
                
            }
            
            
            }
            //documento.add(pt);
            PdfPCell cF1 = new PdfPCell(new Paragraph("Total Parcial",FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLACK)));
            PdfPCell cF2 = new PdfPCell(new Paragraph("$ "+txtTotalP.getText()));
            cF1.setColspan(3);
            cF2.setColspan(1);
            cF2.setHorizontalAlignment (Element.ALIGN_RIGHT);
            pt.addCell(cF1);
            pt.addCell(cF2);
            cF1 = new PdfPCell(new Paragraph("Descuento(%)",FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLACK)));
            cF2 = new PdfPCell(new Paragraph(Cab.getDescuento()+"%"));
            cF1.setColspan(3);
            cF2.setColspan(1);
            cF2.setHorizontalAlignment (Element.ALIGN_RIGHT);
            pt.addCell(cF1);
            pt.addCell(cF2);
            cF1 = new PdfPCell(new Paragraph("Subtotal menos el descuento",FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLACK)));
            cF2 = new PdfPCell(new Paragraph("$ "+txtSubtotal.getText()));
            cF1.setColspan(3);
            cF2.setColspan(1);
            cF2.setHorizontalAlignment (Element.ALIGN_RIGHT);
            pt.addCell(cF1);
            pt.addCell(cF2);
            cF1 = new PdfPCell(new Paragraph("IVA(12%)",FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLACK)));
            cF2 = new PdfPCell(new Paragraph("$ "+txtIVA.getText()));
            cF1.setColspan(3);
            cF2.setColspan(1);
            cF2.setHorizontalAlignment (Element.ALIGN_RIGHT);
            pt.addCell(cF1);
            pt.addCell(cF2);
            cF1 = new PdfPCell(new Paragraph("Total",FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLACK)));
            cF2 = new PdfPCell(new Paragraph("$ "+txtTotal.getText()));
            cF1.setColspan(3);
            cF2.setColspan(1);
            cF2.setHorizontalAlignment (Element.ALIGN_RIGHT);
            pt.addCell(cF1);
            pt.addCell(cF2);
            
            documento.add(pt);
            documento.add(Chunk.NEWLINE);
            documento.add(Chunk.NEWLINE);
            documento.add(new Paragraph("_________________________________                                  ________________________",FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLACK)));
            documento.add(new Paragraph("Firma de la Empresa                                                               Firma del Cliente",FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLACK)));
            documento.add(Chunk.NEWLINE);
            documento.add(new Paragraph("NOTA",FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLACK)));
            documento.add(new Paragraph("Forma de Pago: "+Cab.getFPago(),FontFactory.getFont(FontFactory.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLACK)));
            documento.close();
            
            
            
            AccesoAleatorioFacCab.cerrar();
            AccesoAleatorioFacDet.cerrar();
            JOptionPane.showMessageDialog(null, "Reporte creado.");
        } catch (DocumentException | HeadlessException | FileNotFoundException e) {
        }catch (IOException ex) {
                 Logger.getLogger(InterfazConsulta.class.getName()).log(Level.SEVERE, null, ex);
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
        txtNFactura = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCI = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        txtCIPA = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableDet = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        CheckE = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        CheckCo = new javax.swing.JCheckBox();
        CheckC = new javax.swing.JCheckBox();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtTotalP = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtIVA = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("No. Factura:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        txtNFactura.setEditable(false);
        txtNFactura.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNFactura.setMinimumSize(new java.awt.Dimension(100, 20));
        txtNFactura.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(txtNFactura, gridBagConstraints);

        jLabel2.setText("Fecha");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 300, 0, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        txtFecha.setEditable(false);
        txtFecha.setMinimumSize(new java.awt.Dimension(100, 20));
        txtFecha.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        jPanel1.add(txtFecha, gridBagConstraints);

        jLabel3.setText("C.I. Paciente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        txtCI.setMinimumSize(new java.awt.Dimension(100, 20));
        txtCI.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(txtCI, gridBagConstraints);

        jLabel4.setText("Dirección:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(jLabel4, gridBagConstraints);

        txtDireccion.setMinimumSize(new java.awt.Dimension(200, 20));
        txtDireccion.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        jPanel1.add(txtDireccion, gridBagConstraints);

        jLabel5.setText("Nombre y Apellido: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(jLabel5, gridBagConstraints);

        txtNombre.setMinimumSize(new java.awt.Dimension(200, 20));
        txtNombre.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        jPanel1.add(txtNombre, gridBagConstraints);

        jLabel6.setText("Teléfono:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(jLabel6, gridBagConstraints);

        txtTelefono.setMinimumSize(new java.awt.Dimension(100, 20));
        txtTelefono.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(txtTelefono, gridBagConstraints);

        jLabel7.setText("Email:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(jLabel7, gridBagConstraints);

        txtEmail.setMinimumSize(new java.awt.Dimension(200, 20));
        txtEmail.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        jPanel1.add(txtEmail, gridBagConstraints);

        jButton2.setText("Aceptar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel1.add(jButton2, gridBagConstraints);

        jLabel19.setText("C.I./Ruc:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(jLabel19, gridBagConstraints);

        txtCIPA.setMinimumSize(new java.awt.Dimension(100, 20));
        txtCIPA.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(txtCIPA, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setMinimumSize(new java.awt.Dimension(452, 402));

        TableDet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Descripcion", "Catidad", "Precio Unitario", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableDet.setToolTipText("");
        TableDet.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        TableDet.setGridColor(new java.awt.Color(153, 153, 153));
        TableDet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableDetMouseClicked(evt);
            }
        });
        TableDet.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                TableDetPropertyChange(evt);
            }
        });
        TableDet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TableDetKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(TableDet);
        if (TableDet.getColumnModel().getColumnCount() > 0) {
            TableDet.getColumnModel().getColumn(0).setPreferredWidth(400);
            TableDet.getColumnModel().getColumn(1).setPreferredWidth(10);
            TableDet.getColumnModel().getColumn(2).setPreferredWidth(10);
            TableDet.getColumnModel().getColumn(3).setPreferredWidth(10);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 437;
        gridBagConstraints.ipady = -90;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel8.setText("Forma de Pago:");
        jPanel3.add(jLabel8, new java.awt.GridBagConstraints());

        jLabel9.setText("Efectivo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel3.add(jLabel9, gridBagConstraints);

        CheckE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckEActionPerformed(evt);
            }
        });
        CheckE.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                CheckEPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel3.add(CheckE, gridBagConstraints);

        jLabel10.setText("Contado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel3.add(jLabel10, gridBagConstraints);

        jLabel11.setText("Credito");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel3.add(jLabel11, gridBagConstraints);

        CheckCo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckCoActionPerformed(evt);
            }
        });
        CheckCo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                CheckCoPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPanel3.add(CheckCo, gridBagConstraints);

        CheckC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckCActionPerformed(evt);
            }
        });
        CheckC.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                CheckCPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jPanel3.add(CheckC, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 500);
        jPanel3.add(jLabel12, gridBagConstraints);

        jLabel13.setText("Total Parcial");
        jPanel3.add(jLabel13, new java.awt.GridBagConstraints());

        txtTotalP.setEditable(false);
        txtTotalP.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalP.setMinimumSize(new java.awt.Dimension(100, 20));
        txtTotalP.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel3.add(txtTotalP, new java.awt.GridBagConstraints());

        jLabel14.setText("Descuento(%)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        jPanel3.add(jLabel14, gridBagConstraints);

        txtDescuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDescuento.setText("0");
        txtDescuento.setToolTipText("Luego de ingresar el % de descuento presionar \"ENTER\"");
        txtDescuento.setMinimumSize(new java.awt.Dimension(100, 20));
        txtDescuento.setPreferredSize(new java.awt.Dimension(100, 20));
        txtDescuento.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtDescuentoPropertyChange(evt);
            }
        });
        txtDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        jPanel3.add(txtDescuento, gridBagConstraints);

        jLabel15.setText("Subtotal menos el descuento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        jPanel3.add(jLabel15, gridBagConstraints);

        txtSubtotal.setEditable(false);
        txtSubtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSubtotal.setMinimumSize(new java.awt.Dimension(100, 20));
        txtSubtotal.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        jPanel3.add(txtSubtotal, gridBagConstraints);

        jLabel16.setText("IVA(12%)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        jPanel3.add(jLabel16, gridBagConstraints);

        txtIVA.setEditable(false);
        txtIVA.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIVA.setMinimumSize(new java.awt.Dimension(100, 20));
        txtIVA.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        jPanel3.add(txtIVA, gridBagConstraints);

        jLabel17.setText("Total");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        jPanel3.add(jLabel17, gridBagConstraints);

        txtTotal.setEditable(false);
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setMinimumSize(new java.awt.Dimension(100, 20));
        txtTotal.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        jPanel3.add(txtTotal, gridBagConstraints);

        jButton1.setText("Generar Factura");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        jPanel3.add(jButton1, gridBagConstraints);

        jLabel18.setText("%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        jPanel3.add(jLabel18, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String _NFac = txtNFactura.getText().trim();
        if(_NFac.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso el codigo del Factura!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int NFac;
        try {
            NFac = Integer.parseInt(_NFac);
        } catch(NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un número", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String CIRuc = txtCI.getText().trim();
        if(CIRuc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso el CIRuc!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }else if(CIRuc.length()<10 || CIRuc.length()>14){
            JOptionPane.showMessageDialog(this, "¡El numero de cedula necesita tener 10 o 13 caracteres!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int CI= Integer.parseInt(CIRuc);
        } catch (NumberFormatException ex) {
            
            JOptionPane.showMessageDialog(this, "¡El deben ingresar solo numeros en la Cedula !", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String Nombre = txtNombre.getText().trim();
        if(Nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso el Nombre!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String Direccion = txtDireccion.getText().trim();
        if(Direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso el Direccion!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String Telefono = txtTelefono.getText().trim();
        if(Telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso el Telefono!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String Email = txtEmail.getText().trim();
        if(Email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso el Email!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String Fecha = txtFecha.getText().trim();
        if(Fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso el Fecha!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String _Descuento = txtDescuento.getText().trim();
        if(_Descuento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso el Descuento!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        double Descuento;
        try {
            Descuento = Double.parseDouble(_Descuento);
        } catch(NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un número para el Descuento", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(FPago.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso el metodo de Pago!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        
        Factura Cab = new Factura();
        if(Cab.Registrar(NFac, CIRuc, Nombre, Direccion, Telefono, Email, Fecha, Descuento,FPago)){
            for(int i=0 ;i<TableDet.getRowCount();i++){
                Registro Det =new Registro();   
                String Descripcion=""+TableDet.getValueAt(i, 0);
                if(!Descripcion.isEmpty()){
                try{
                    if(!TableDet.getValueAt(i,1).equals(null)&&!TableDet.getValueAt(i, 2).equals(null)){
                        int Cantidad=Integer.parseInt(""+TableDet.getValueAt(i,1));
                        double PrecioU=Double.parseDouble(""+TableDet.getValueAt(i, 2));
                        Det.Registrar(NFac,Descripcion,Cantidad ,PrecioU );
                        
                    }
                }catch (NullPointerException ex){
            
                }
                    
                    
                }
                
            }
            GenerarReporte();
            cargarcodigo();
            LimpiarCampos();
            limpiarTabla();
            JOptionPane.showMessageDialog(this, "El registro se realizó correctamente.", "Notificación", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this, "Error en la escritura de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void TableDetPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_TableDetPropertyChange
        // TODO add your handling code here:
        realizarEvento();
       
       
    }//GEN-LAST:event_TableDetPropertyChange

    private void TableDetKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableDetKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_TableDetKeyTyped

    private void TableDetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableDetMouseClicked
        // TODO add your handling code here:
        try{
            int index = TableDet.getSelectedRow();
            TableModel model =TableDet.getModel();
            int Cantidad=Integer.parseInt(model.getValueAt(index, 1).toString());
            double PrecioU=Double.parseDouble(model.getValueAt(index, 2).toString());
            model.setValueAt(Cantidad*PrecioU, index, 3);
        }catch (NullPointerException ex){
            
        }
        
        //TableDet.setValueAt(Cantidad*PrecioU,i, 3);
        /*for (int i = 0; i<TableDet.getRowCount(); i++) {
            String comp1 =""+TableDet.getValueAt(i,1);
            String comp2 =""+TableDet.getValueAt(i,2);
            if(!comp1.equals("") || !comp2.equals("")){
                int Cantidad=Integer.parseInt(comp1);
                double PrecioU=Double.parseDouble(comp2);
                TableDet.setValueAt(Cantidad*PrecioU,i, 3);
                
            }else{
                break;
            }
        }*/
    }//GEN-LAST:event_TableDetMouseClicked

    private void txtDescuentoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtDescuentoPropertyChange
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtDescuentoPropertyChange

    private void txtDescuentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyTyped
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_txtDescuentoKeyTyped

    private void txtDescuentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyPressed
        // TODO add your handling code here:
        try{
            if(evt.getKeyCode() == KeyEvent.VK_ENTER){
                double total= Double.parseDouble(txtTotalP.getText());
        double _descuento=(Double.parseDouble(txtDescuento.getText().trim())/100)*total;
        BigDecimal bd1 = new BigDecimal(_descuento).setScale(2, RoundingMode.HALF_UP);
        double descuento =bd1.doubleValue();
        BigDecimal bd5 = new BigDecimal(total-descuento).setScale(2, RoundingMode.HALF_UP);
        double ST =bd5.doubleValue();
        txtSubtotal.setText(String.valueOf(ST));
        double _Sub=Double.parseDouble(txtSubtotal.getText().trim());
        BigDecimal bd2 = new BigDecimal(_Sub).setScale(2, RoundingMode.HALF_UP);
        double Sub= bd2.doubleValue();
        BigDecimal bd4 = new BigDecimal(Sub*0.12).setScale(2, RoundingMode.HALF_UP);
        double Iv= bd4.doubleValue();
        txtIVA.setText(String.valueOf(Iv));
        double _IVA=Double.parseDouble(txtIVA.getText().trim());
        BigDecimal bd3 = new BigDecimal(_IVA).setScale(2, RoundingMode.HALF_UP);
        //BigDecimal bd3 = new BigDecimal(_IVA).setScale(3, RoundingMode.UNNECESSARY);
        double IVA= bd3.doubleValue();
        BigDecimal bd6 = new BigDecimal(Sub+IVA).setScale(2, RoundingMode.HALF_UP);
        double T= bd6.doubleValue();
        txtTotal.setText(String.valueOf(T));
                
            }
        
        }catch (NumberFormatException ex){
            
        }
    }//GEN-LAST:event_txtDescuentoKeyPressed

    private void CheckEPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_CheckEPropertyChange
        // TODO add your handling code here:
       
    }//GEN-LAST:event_CheckEPropertyChange

    private void CheckCoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_CheckCoPropertyChange
        // TODO add your handling code here:
       
    }//GEN-LAST:event_CheckCoPropertyChange

    private void CheckCPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_CheckCPropertyChange
        // TODO add your handling code here:
       
    }//GEN-LAST:event_CheckCPropertyChange

    private void CheckEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckEActionPerformed
        // TODO add your handling code here:
         if(CheckE.isSelected()){
            CheckCo.setEnabled(false);
            CheckC.setEnabled(false);
            FPago="Efectivo";
        }else{
            CheckCo.setEnabled(true);
            CheckC.setEnabled(true);
            FPago="";
         }
    }//GEN-LAST:event_CheckEActionPerformed

    private void CheckCoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckCoActionPerformed
        // TODO add your handling code here:
         if(CheckCo.isSelected()){
            CheckE.setEnabled(false);
            CheckC.setEnabled(false);
            FPago="Contado";
        }else{
            CheckE.setEnabled(true);
            CheckC.setEnabled(true);
            FPago="";
         }
    }//GEN-LAST:event_CheckCoActionPerformed

    private void CheckCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckCActionPerformed
        // TODO add your handling code here:
         if(CheckC.isSelected()){
            CheckE.setEnabled(false);
            CheckCo.setEnabled(false);
            FPago="Credito";
        }else{
            CheckE.setEnabled(true);
            CheckCo.setEnabled(true);
            FPago="";
         }
    }//GEN-LAST:event_CheckCActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int resp= JOptionPane.showConfirmDialog(null, "Deseas Relizar la Factura con los datos del paciente o con otros datos", "Confirmar salida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (resp) {
        case 0:
            SetDatos();
            txtCI.setEditable(false);
            txtNombre.setEditable(false);
            txtTelefono.setEditable(false);
            break;
        case 1:
            SetDatos();
            txtCI.setText("");
            txtNombre.setText("");
            txtTelefono.setText("");
            txtCI.setEditable(true);
            txtNombre.setEditable(true);
            txtTelefono.setEditable(true);
            JOptionPane.showMessageDialog(this, "OK, Ingrese los datos de la factura", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
            break;
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(Facturacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Facturacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Facturacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Facturacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Facturacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CheckC;
    private javax.swing.JCheckBox CheckCo;
    private javax.swing.JCheckBox CheckE;
    private javax.swing.JTable TableDet;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtCI;
    private javax.swing.JTextField txtCIPA;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtIVA;
    private javax.swing.JTextField txtNFactura;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotalP;
    // End of variables declaration//GEN-END:variables
}
