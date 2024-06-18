/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicaodontologica;

import AccesosArchivo.AccesoAleatorioFacCab;
import AccesosArchivo.AccesoAleatorioFacDet;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author William
 */
public class FacturacionModi extends javax.swing.JFrame {

    String FPago="";
    /**
     * Creates new form FacturacionModi
     */
    public FacturacionModi() {
        initComponents();
        ListarN();
    }
    public void ListarN(){
        try {
            BoxNFac.removeAllItems();
            AccesoAleatorioFacCab.crearFileFacCab( new File("FacCab.dat") );
            for (int i = 0; i < AccesoAleatorioFacCab.getNumeroRegistros() ; i++) {
            Factura Cab =AccesoAleatorioFacCab.getFacturaCab(i);
            if(Cab.getactivo()){
                BoxNFac.addItem(""+Cab.getNFac());
            }
                                        
            }
            
            AccesoAleatorioFacCab.cerrar();
            
        } catch (IOException ex) {
            Logger.getLogger(RegistroD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setCampos(){
        try {
            AccesoAleatorioFacCab.crearFileFacCab( new File("FacCab.dat") );
            AccesoAleatorioFacDet.crearFileFacDet( new File("FacDet.dat") );
            int p=AccesoAleatorioFacCab.buscarRegistroN(Integer.parseInt(BoxNFac.getSelectedItem().toString()));
            Factura Cab= AccesoAleatorioFacCab.getFacturaCab(p);
            txtCI.setText(Cab.getCIRuc());
            txtNombre.setText(Cab.getNombre());
            txtDireccion.setText(Cab.getDireccion());
            txtTelefono.setText(Cab.getTelefono());
            txtEmail.setText(Cab.getEmail());
            txtFecha.setText(Cab.getFecha());
            txtDescuento.setText(""+Cab.getDescuento());
            String Pago = Cab.getFPago();
            switch(Pago){
                case "Efectivo":
                    CheckE.setSelected(true);
                    CheckCo.setEnabled(false);
                    CheckC.setEnabled(false);
                    break;
                case "Contado":    
                    CheckCo.setSelected(true);
                    CheckE.setEnabled(false);
                    CheckC.setEnabled(false);
                    break;
                case "Credito":
                    CheckC.setSelected(true);
                    CheckCo.setEnabled(false);
                    CheckE.setEnabled(false);
                    break;
            }
            DefaultTableModel modelo=(DefaultTableModel)TableDet.getModel();
            int a = TableDet.getRowCount()-1;
            for(int i=a;i>=0;i--){
                modelo.removeRow(modelo.getRowCount()-1);
            }
            Object[] fila =new Object[4];
            for (int i = 0; i < AccesoAleatorioFacDet.getNumeroRegistros() ; i++) {
            Registro Det =AccesoAleatorioFacDet.getFacturaDet(i);
            if(Det.getactivo() && Det.getNFac()==Integer.parseInt(BoxNFac.getSelectedItem().toString())){
                fila[0]=Det.getDescripcion();
                fila[1]=Det.getCantidad();
                fila[2]=Det.getPrecioU();
                fila[3]=Det.getCantidad()*Det.getPrecioU();
            
                modelo.addRow(fila);
                TableDet.setModel(modelo);
            }
                
            
            
            }
        
            
            
            AccesoAleatorioFacCab.cerrar();
            
        } catch (IOException ex) {
            Logger.getLogger(RegistroD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void GenerarF(){
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
        BoxNFac = new javax.swing.JComboBox<>();
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
        jLabel18 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("No. Factura:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

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

        jLabel3.setText("C.I./Ruc:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        txtCI.setEditable(false);
        txtCI.setMinimumSize(new java.awt.Dimension(100, 20));
        txtCI.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(txtCI, gridBagConstraints);

        jLabel4.setText("Dirección:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(jLabel4, gridBagConstraints);

        txtDireccion.setEditable(false);
        txtDireccion.setMinimumSize(new java.awt.Dimension(200, 20));
        txtDireccion.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        jPanel1.add(txtDireccion, gridBagConstraints);

        jLabel5.setText("Nombre y Apellido: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(jLabel5, gridBagConstraints);

        txtNombre.setEditable(false);
        txtNombre.setMinimumSize(new java.awt.Dimension(200, 20));
        txtNombre.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jPanel1.add(txtNombre, gridBagConstraints);

        jLabel6.setText("Teléfono:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(jLabel6, gridBagConstraints);

        txtTelefono.setEditable(false);
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
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(jLabel7, gridBagConstraints);

        txtEmail.setEditable(false);
        txtEmail.setMinimumSize(new java.awt.Dimension(200, 20));
        txtEmail.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        jPanel1.add(txtEmail, gridBagConstraints);

        jButton2.setText("Aceptar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel1.add(jButton2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(BoxNFac, gridBagConstraints);

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
        TableDet.setEnabled(false);
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

        txtDescuento.setEditable(false);
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TableDetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableDetMouseClicked
        // TODO add your handling code here:
        /*try{
            int index = TableDet.getSelectedRow();
            TableModel model =TableDet.getModel();
            int Cantidad=Integer.parseInt(model.getValueAt(index, 1).toString());
            double PrecioU=Double.parseDouble(model.getValueAt(index, 2).toString());
            model.setValueAt(Cantidad*PrecioU, index, 3);
        }catch (NullPointerException ex){

        }*/

        
    }//GEN-LAST:event_TableDetMouseClicked

    private void TableDetPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_TableDetPropertyChange
        // TODO add your handling code here:

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

    }//GEN-LAST:event_TableDetPropertyChange

    private void TableDetKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableDetKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_TableDetKeyTyped

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

    private void CheckEPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_CheckEPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_CheckEPropertyChange

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

    private void CheckCoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_CheckCoPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_CheckCoPropertyChange

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

    private void CheckCPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_CheckCPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_CheckCPropertyChange

    private void txtDescuentoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtDescuentoPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_txtDescuentoPropertyChange

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

    private void txtDescuentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_txtDescuentoKeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        setCampos();
        GenerarF();
        
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
            java.util.logging.Logger.getLogger(FacturacionModi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FacturacionModi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FacturacionModi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FacturacionModi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FacturacionModi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> BoxNFac;
    private javax.swing.JCheckBox CheckC;
    private javax.swing.JCheckBox CheckCo;
    private javax.swing.JCheckBox CheckE;
    private javax.swing.JTable TableDet;
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
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtIVA;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotalP;
    // End of variables declaration//GEN-END:variables
}
