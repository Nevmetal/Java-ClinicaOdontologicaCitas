/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicaodontologica;

import AccesosArchivo.AccesoAleatorioPaciente;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author William
 */
public class RegistroP extends javax.swing.JFrame {

    int clic_tabla;
    /**
     * Creates new form RegistroP
     */
    public RegistroP() {
        initComponents();
        listar();
        cargarcodigo();
        this.setLocationRelativeTo(null);
    }
    public boolean VerifcarCodigo(){
        try {
            AccesoAleatorioPaciente.crearFilePaciente( new File("Paciente.dat") );
            for (int i = 0; i < AccesoAleatorioPaciente.getNumeroRegistros() ; i++) {
            Paciente Pa =AccesoAleatorioPaciente.getPaciente(i);
            if(Pa.getActivo()&&Pa.getCodigo().equals(txtCodigo.getText().trim())){
                AccesoAleatorioPaciente.cerrar();
                return true;
            }
            }
            AccesoAleatorioPaciente.cerrar();
            
        } catch (IOException ex) {
            Logger.getLogger(RegistroP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean VerifcarCedula(){
        try {
            AccesoAleatorioPaciente.crearFilePaciente( new File("Paciente.dat") );
            for (int i = 0; i < AccesoAleatorioPaciente.getNumeroRegistros() ; i++) {
            Paciente Pa =AccesoAleatorioPaciente.getPaciente(i);
                if(Pa.getActivo() && Pa.getCedula().equals(txtCedula.getText().trim())){
                     AccesoAleatorioPaciente.cerrar();
                return true;
                }
            }
            AccesoAleatorioPaciente.cerrar();
            
        } catch (IOException ex) {
            Logger.getLogger(RegistroP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    public void RegistroPaciente(){
        String Codigo = txtCodigo.getText().trim();
        if(Codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso el codigo del paciente!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }else if(VerifcarCodigo()) {
            JOptionPane.showMessageDialog(this, "¡Ya existe este Codigo!", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            
        }
        String Cedula = txtCedula.getText().trim();
        if(Cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso la cedula del paciente!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }else if(Cedula.length()<10 || Cedula.length()>10 ){
            JOptionPane.showMessageDialog(this, "¡El numero de cedula necesita tener 10 caracteres!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }else if(VerifcarCedula()){
            JOptionPane.showMessageDialog(this, "¡Ya existe este Número de Cedula!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int ci= Integer.parseInt(Cedula);
        } catch (NumberFormatException ex) {
            
            JOptionPane.showMessageDialog(this, "¡El deben ingresar solo numeros en la Cedula !", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String Nombre = txtNombre.getText().trim();
        if(Nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso el nombre de la persona!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }    
        String Apellido = txtApellido.getText().trim();
        if(Apellido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso el nombre de la persona!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }    
        SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
        String Fecha= dFormat.format(FechaP.getDate());
        if(Fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso la fecha de nacimiento del paciente!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String Telefono = txtTelefono.getText().trim();
        if(Telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso el numero de telefono de la persona!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }  
        
        char Genero =boxGenero.getSelectedItem().toString().trim().charAt(0);
        Paciente Pa = new Paciente();
        if(Pa.Registrar(Cedula, Nombre, Apellido, Fecha, Genero, Codigo, Telefono)){
            JOptionPane.showMessageDialog(this, "El registro se realizó correctamente.", "Notificación", JOptionPane.INFORMATION_MESSAGE);
            listar();
        }else{
            JOptionPane.showMessageDialog(this, "Error en la escritura de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        /*
        try {
            AccesoAleatorioPaciente.crearFilePaciente( new File("Paciente.dat") );
            AccesoAleatorioPaciente.AñadirPaciente(new Paciente (Cedula, Nombre, Apellido, Fecha, Genero, Codigo, Telefono,true) );
            AccesoAleatorioPaciente.cerrar();
            JOptionPane.showMessageDialog(this, "El registro se realizó correctamente.", "Notificación", JOptionPane.INFORMATION_MESSAGE);
            listar();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error en la escritura de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }*/
        
        
    }
    public void listar(){
        DefaultTableModel modelo=(DefaultTableModel)TablaPaciente.getModel();
        int a = TablaPaciente.getRowCount()-1;
        for(int i=a;i>=0;i--){
            modelo.removeRow(modelo.getRowCount()-1);
        }
        Object[] fila =new Object[7];
        try {
            AccesoAleatorioPaciente.crearFilePaciente( new File("Paciente.dat") );
            for (int i = 0; i < AccesoAleatorioPaciente.getNumeroRegistros() ; i++) {
            Paciente Pa =AccesoAleatorioPaciente.getPaciente(i);
            if(Pa.getActivo()){
                fila[0]=Pa.getCedula().trim();
                fila[1]=Pa.getNombre().trim();
                fila[2]=Pa.getApellido().trim();
                fila[3]=Pa.getFechaNacimiento().trim();
                fila[4]=Pa.getGenero();
                fila[5]=Pa.getCodigo().trim();
                fila[6]=Pa.getTelefono().trim();
            
                modelo.addRow(fila);
                TablaPaciente.setModel(modelo);
            }
            
            }
            AccesoAleatorioPaciente.cerrar();
        } catch (IOException ex) {
            Logger.getLogger(RegistroD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void EliminarRegistro(){
        String Cedula=txtCedula.getText().trim();
        Paciente Pa = new Paciente();
        int resp= JOptionPane.showConfirmDialog(null, "Realmente desea eliminar el registro con CI:"+Cedula+"?", "Confirmar salida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (resp) {
        case 0:
            if(Pa.Eliminar(Cedula)){
                JOptionPane.showMessageDialog(this, "El registro correspondiente fue eliminado correctamente.", "Eliminación correcta", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this, "Error al intentar eliminar un registro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            break;
        case 1:
            JOptionPane.showMessageDialog(this, "No se realizo ningun cambio.", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
            break;
        }
        /*try {
            AccesoAleatorioPaciente.crearFilePaciente( new File("Paciente.dat") );
            if( AccesoAleatorioPaciente.eliminarPaciente(Cedula) )
                JOptionPane.showMessageDialog(this, "El registro correspondiente fue eliminado correctamente.", "Eliminación correcta", JOptionPane.INFORMATION_MESSAGE);
            else JOptionPane.showMessageDialog(this, "Error al intentar eliminar un registro inexistente.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error en la eliminación de registros.", "Error", JOptionPane.ERROR_MESSAGE);
        } */
    }
    public void Actualizar(){
        String Codigo = txtCodigo.getText().trim();
        if(Codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso el codigo del paciente!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String Cedula = txtCedula.getText().trim();
        if(Cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso la cedula del paciente!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }else if(Cedula.length()<10 || Cedula.length()>10 ){
            JOptionPane.showMessageDialog(this, "¡El numero de cedula necesita tener 10 caracteres!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String Nombre = txtNombre.getText().trim();
        if(Nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso el nombre de la persona!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }    
        String Apellido = txtApellido.getText().trim();
        if(Apellido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso el nombre de la persona!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }    
        SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
        String Fecha= dFormat.format(FechaP.getDate());
        if(Fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso la fecha de nacimiento del paciente!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String Telefono = txtTelefono.getText().trim();
        if(Telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso el numero de telefono de la persona!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        } 
        
        char Genero =boxGenero.getSelectedItem().toString().trim().charAt(0);
        Paciente Pa = new Paciente();
        if(Pa.Actualizar(Cedula, Nombre, Apellido, Fecha, Genero, Codigo, Telefono)){
            JOptionPane.showMessageDialog(this, "El registro se actualizo correctamente.", "Notificación", JOptionPane.INFORMATION_MESSAGE);
            listar();
        }else{
            JOptionPane.showMessageDialog(this, "Error en la modificacion de registros.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        /*
        try {
            int posicion;
            AccesoAleatorioPaciente.crearFilePaciente( new File("Paciente.dat") );
            posicion=AccesoAleatorioPaciente.buscarRegistroC(txtCedula.getText());
            if(posicion==-1){
               posicion=AccesoAleatorioPaciente.buscarRegistroID(txtCodigo.getText());
            }
            Paciente Pa= new Paciente (Cedula, Nombre, Apellido, Fecha, Genero,Codigo, Telefono,true);
            AccesoAleatorioPaciente.setPaciente(posicion, Pa);
            AccesoAleatorioPaciente.cerrar();
            JOptionPane.showMessageDialog(this, "El registro se actualizo correctamente.", "Notificación", JOptionPane.INFORMATION_MESSAGE);
            listar();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error en la modificacion de registros.", "Error", JOptionPane.ERROR_MESSAGE);
        } */
    }
    public void cargarcodigo(){
        int j;
        String num="";
        String c = "";
        try {
            boolean cond=false;
            AccesoAleatorioPaciente.crearFilePaciente( new File("Paciente.dat") );
            for (int i = 0; i < AccesoAleatorioPaciente.getNumeroRegistros() ; i++) {
            Paciente Pa =AccesoAleatorioPaciente.getPaciente(i);
            if(!Pa.getActivo()){
               c = Pa.getCodigo();
               cond=true;
               break;
            }else if(i==AccesoAleatorioPaciente.getNumeroRegistros()-1){
                c = Pa.getCodigo();
            }
            }
       if(c=="")
            {
                txtCodigo.setText("P00001");
            }else {
                if(cond){
                    char r1=c.charAt(1);
                    char r2=c.charAt(2);
                    char r3=c.charAt(3);
                    char r4=c.charAt(4);
                    char r5=c.charAt(5);
                    String r ="";
                    r = "" +r1+r2+r3+r4+r5;
                    j=Integer.parseInt(r);
                    GenerarCodigo gen = new GenerarCodigo();
                    gen.generar(j-1);
                    txtCodigo.setText("P"+gen.serie());
                }else{
                    char r1=c.charAt(1);
                    char r2=c.charAt(2);
                    char r3=c.charAt(3);
                    char r4=c.charAt(4);
                    char r5=c.charAt(5);
                    String r ="";
                    r = "" +r1+r2+r3+r4+r5;
                    j=Integer.parseInt(r);
                    GenerarCodigo gen = new GenerarCodigo();
                    gen.generar(j);
                    txtCodigo.setText("P"+gen.serie());
                }
                
                
            }
       AccesoAleatorioPaciente.cerrar();
        } catch (IOException ex) {
            Logger.getLogger(InterfazCita.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }
    public void Limpiar(){
        txtCedula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        
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
        jLabel4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtCedula = new javax.swing.JTextField();
        boxGenero = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        txtCodigo = new javax.swing.JTextField();
        btnModificar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnIngreso = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaPaciente = new javax.swing.JTable();
        FechaP = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        btnLimpiar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro Paciente");
        setMinimumSize(new java.awt.Dimension(685, 700));
        setPreferredSize(new java.awt.Dimension(685, 700));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel4.setText("APELLIDO:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel4, gridBagConstraints);

        jLabel12.setForeground(new java.awt.Color(0, 153, 153));
        jLabel12.setText("M: Masculino");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        jPanel1.add(jLabel12, gridBagConstraints);

        txtNombre.setMinimumSize(new java.awt.Dimension(100, 20));
        txtNombre.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(txtNombre, gridBagConstraints);

        txtCedula.setMinimumSize(new java.awt.Dimension(100, 20));
        txtCedula.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(txtCedula, gridBagConstraints);

        boxGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "F" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        jPanel1.add(boxGenero, gridBagConstraints);

        jLabel11.setForeground(new java.awt.Color(0, 153, 153));
        jLabel11.setText("F: Femenino");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.ABOVE_BASELINE;
        jPanel1.add(jLabel11, gridBagConstraints);

        jLabel5.setText("FECHA  NACIMIENTO:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel5, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("REGISTRO PACIENTE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 17, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel6.setText("Codigo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel6, gridBagConstraints);

        jLabel10.setText("Genero");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel10, gridBagConstraints);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(btnEliminar, gridBagConstraints);

        txtCodigo.setEditable(false);
        txtCodigo.setMinimumSize(new java.awt.Dimension(100, 20));
        txtCodigo.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        jPanel1.add(txtCodigo, gridBagConstraints);

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(btnModificar, gridBagConstraints);

        jLabel3.setText("NOMBRE:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        txtApellido.setMinimumSize(new java.awt.Dimension(100, 20));
        txtApellido.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(txtApellido, gridBagConstraints);

        jLabel2.setText("CEDULA:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        btnIngreso.setText("Ingresar");
        btnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(btnIngreso, gridBagConstraints);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(500, 300));
        jScrollPane1.setName(""); // NOI18N
        jScrollPane1.setPreferredSize(new java.awt.Dimension(500, 300));

        TablaPaciente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cedula", "Nombre", "Apellido", "Fecha Nacimiento", "Genero", "Codigo", "Telefono"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaPaciente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaPacienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaPaciente);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.gridwidth = 11;
        jPanel1.add(jScrollPane1, gridBagConstraints);

        FechaP.setMinimumSize(new java.awt.Dimension(100, 20));
        FechaP.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        jPanel1.add(FechaP, gridBagConstraints);

        jLabel7.setText("Telefono");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel7, gridBagConstraints);

        txtTelefono.setMinimumSize(new java.awt.Dimension(100, 20));
        txtTelefono.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        jPanel1.add(txtTelefono, gridBagConstraints);

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.insets = new java.awt.Insets(0, 60, 0, 20);
        jPanel1.add(btnLimpiar, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        EliminarRegistro();
        Limpiar();
        listar();
        cargarcodigo();
        
        
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        Actualizar();
        cargarcodigo();
        Limpiar();

    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresoActionPerformed
        // TODO add your handling code here:
        RegistroPaciente();
        cargarcodigo();
        Limpiar();
        
    }//GEN-LAST:event_btnIngresoActionPerformed

    private void TablaPacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaPacienteMouseClicked
        try {
            // TODO add your handling code here:
            clic_tabla = TablaPaciente.rowAtPoint(evt.getPoint());
            String Cedula = ""+TablaPaciente.getValueAt(clic_tabla, 0);
            String Nombre =""+TablaPaciente.getValueAt(clic_tabla, 1);
            String Apellido = ""+TablaPaciente.getValueAt(clic_tabla, 2);
            String Fecha = ""+TablaPaciente.getValueAt(clic_tabla, 3);
            String Genero = ""+TablaPaciente.getValueAt(clic_tabla, 4);
            String Codigo = ""+TablaPaciente.getValueAt(clic_tabla, 5);
            String Telefono = ""+TablaPaciente.getValueAt(clic_tabla, 6);
            Date F = new SimpleDateFormat("dd/MM/yyyy").parse(Fecha);
            txtCodigo.setText(Codigo);
            txtCedula.setText(Cedula);
            txtNombre.setText(Nombre);
            txtApellido.setText(Apellido);
            boxGenero.setSelectedItem(Genero);
            FechaP.setDate(F);
            txtTelefono.setText(Telefono);
        } catch (ParseException ex) {
            Logger.getLogger(RegistroP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_TablaPacienteMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_formWindowClosed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        Limpiar();
        cargarcodigo();
    }//GEN-LAST:event_btnLimpiarActionPerformed

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
            java.util.logging.Logger.getLogger(RegistroP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroP().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser FechaP;
    private javax.swing.JTable TablaPaciente;
    private javax.swing.JComboBox<String> boxGenero;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnIngreso;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
