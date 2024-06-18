/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicaodontologica;

import AccesosArchivo.AccesoAleatorioDoctor;
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
public class RegistroD extends javax.swing.JFrame {

    int clic_tabla;
    /**
     * Creates new form RegistroD
     */
    public RegistroD() {
        initComponents();
        listar();
        cargarcodigo();
        this.setLocationRelativeTo(null);
    }
    public boolean VerifcarCodigo(){
        try {
            AccesoAleatorioDoctor.crearFileDoctor( new File("Doctor.dat") );
            for (int i = 0; i < AccesoAleatorioDoctor.getNumeroRegistros() ; i++) {
            Doctor D =AccesoAleatorioDoctor.getDoctor(i);
            if(D.getActivo()&&D.getCodigo().equals(txtCodigo.getText().trim())){
                AccesoAleatorioDoctor.cerrar();
                return true;
            }
            }
            AccesoAleatorioDoctor.cerrar();
            
        } catch (IOException ex) {
            Logger.getLogger(RegistroP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean VerifcarCedula(){
        try {
            AccesoAleatorioDoctor.crearFileDoctor( new File("Doctor.dat") );
            for (int i = 0; i < AccesoAleatorioDoctor.getNumeroRegistros() ; i++) {
            Doctor D =AccesoAleatorioDoctor.getDoctor(i);
                if(D.getActivo() && D.getCedula().equals(txtCedula.getText().trim())){
                     AccesoAleatorioDoctor.cerrar();
                return true;
                }
            }
            AccesoAleatorioDoctor.cerrar();
            
        } catch (IOException ex) {
            Logger.getLogger(RegistroP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    public void RegistroDoctor(){
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
        }else if(Cedula.length()<10 || Cedula.length()>10){
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
        String Fecha= dFormat.format(FechaD.getDate());
        if(Fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso la fecha de nacimiento del paciente!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        char Genero =boxGenero.getSelectedItem().toString().trim().charAt(0);
        String Especialidad= boxEspecialidad.getSelectedItem().toString();
        Doctor D =new Doctor();
        if(D.Registrar(Cedula, Nombre, Apellido, Fecha, Genero, Codigo, Especialidad)){
            JOptionPane.showMessageDialog(this, "El registro se realizó correctamente.", "Notificación", JOptionPane.INFORMATION_MESSAGE);
            Limpiar();
            listar();
        }else{
            JOptionPane.showMessageDialog(this, "Error en la escritura de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        /*try {
            AccesoAleatorioDoctor.crearFileDoctor( new File("Doctor.dat") );
            AccesoAleatorioDoctor.AñadirDoctor(new Doctor (Cedula, Nombre, Apellido, Fecha, Genero,Codigo, Especialidad,true) );
            AccesoAleatorioDoctor.cerrar();
            JOptionPane.showMessageDialog(this, "El registro se realizó correctamente.", "Notificación", JOptionPane.INFORMATION_MESSAGE);
            Limpiar();
            listar();
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error en la escritura de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }*/
    }
    public void listar(){
        DefaultTableModel modelo=(DefaultTableModel)TablaMedico.getModel();
        int a = TablaMedico.getRowCount()-1;
        for(int i=a;i>=0;i--){
            modelo.removeRow(modelo.getRowCount()-1);
        }
        Object[] fila =new Object[7];
        try {
            AccesoAleatorioDoctor.crearFileDoctor( new File("Doctor.dat") );
            for (int i = 0; i < AccesoAleatorioDoctor.getNumeroRegistros() ; i++) {
            Doctor D =AccesoAleatorioDoctor.getDoctor(i);
            if(D.getActivo()){
                fila[0]=D.getCedula().trim();
                fila[1]=D.getNombre().trim();
                fila[2]=D.getApellido().trim();
                fila[3]=D.getFechaNacimiento().trim();
                fila[4]=D.getGenero();
                fila[5]=D.getCodigo().trim();
                fila[6]=D.getEspecialidad().trim();
                
            
                modelo.addRow(fila);
                TablaMedico.setModel(modelo);
            }
                
            
            
            }
            
            AccesoAleatorioDoctor.cerrar();
            
        } catch (IOException ex) {
            Logger.getLogger(RegistroD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void Limpiar(){
        txtCedula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        
        
    }
    public void EliminarRegistro(){
        String Cedula= txtCedula.getText().trim();
        Doctor D = new Doctor();
        int resp= JOptionPane.showConfirmDialog(null, "Realmente desea eliminar el registro con CI:"+Cedula+"?", "Confirmar salida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (resp) {
        case 0:
            if(D.Eliminar(Cedula)){
                JOptionPane.showMessageDialog(this, "El registro correspondiente fue eliminado correctamente.", "Eliminación correcta", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this, "Error al intentar eliminar un registro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            break;
        case 1:
            JOptionPane.showMessageDialog(this, "No se realizo ningun cambio.", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
            break;
        }
        
        /*
        Doctor D = new Doctor();
        if(D.Eliminar(Cedula)){
            JOptionPane.showMessageDialog(this, "El registro correspondiente fue eliminado correctamente.", "Eliminación correcta", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this, "Error al intentar eliminar un registro.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        try {
           
            AccesoAleatorioDoctor.crearFileDoctor( new File("Doctor.dat") );
            if( AccesoAleatorioDoctor.eliminarDoctor(Cedula) ){
                 JOptionPane.showMessageDialog(this, "El registro correspondiente fue eliminado correctamente.", "Eliminación correcta", JOptionPane.INFORMATION_MESSAGE);
            }
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
        }else if(Cedula.length()<10 || Cedula.length()>10){
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
        String Fecha= dFormat.format(FechaD.getDate());
        if(Fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡No se ingreso la fecha de nacimiento del paciente!", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        char Genero =boxGenero.getSelectedItem().toString().trim().charAt(0);
        String Especialidad= boxEspecialidad.getSelectedItem().toString();
        Doctor D = new Doctor();
        if(D.Actualizar(Cedula, Nombre, Apellido, Fecha, Genero, Codigo, Especialidad)){
            JOptionPane.showMessageDialog(this, "El registro correspondiente fue modificado correctamente.", "Modificacion correcta", JOptionPane.INFORMATION_MESSAGE);
            Limpiar();
            listar();
        }else{
            JOptionPane.showMessageDialog(this, "Error en la modificacion de registros.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        /*try {
            int posicion;
            AccesoAleatorioDoctor.crearFileDoctor( new File("Doctor.dat") );
            posicion=AccesoAleatorioDoctor.buscarRegistroC(Cedula);
            if(posicion==-1){
               posicion=AccesoAleatorioDoctor.buscarRegistroID(Codigo);
            }
            Doctor D= new Doctor (Cedula, Nombre, Apellido, Fecha, Genero,Codigo, Especialidad,true);
            AccesoAleatorioDoctor.setDoctor(posicion, D);
            AccesoAleatorioDoctor.cerrar();
            Limpiar();
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
            AccesoAleatorioDoctor.crearFileDoctor( new File("Doctor.dat") );
            for (int i = 0; i < AccesoAleatorioDoctor.getNumeroRegistros() ; i++) {
            Doctor D =AccesoAleatorioDoctor.getDoctor(i);
            if(!D.getActivo()){
               c = D.getCodigo();
               cond=true;
               break;
            }else if(i==AccesoAleatorioDoctor.getNumeroRegistros()-1){
                c = D.getCodigo();
            }
            }
       if(c=="")
            {
                txtCodigo.setText("D00001");
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
                    txtCodigo.setText("D"+gen.serie());
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
                    txtCodigo.setText("D"+gen.serie());
                }
                
                
            }
       AccesoAleatorioDoctor.cerrar();
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
        txtCedula = new javax.swing.JTextField();
        boxEspecialidad = new javax.swing.JComboBox<>();
        btnIngreso = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaMedico = new javax.swing.JTable();
        FechaD = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        boxGenero = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        btnLimpiar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro Medico");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(685, 700));
        setPreferredSize(new java.awt.Dimension(685, 700));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridBagLayout());

        txtCedula.setMinimumSize(new java.awt.Dimension(100, 20));
        txtCedula.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(txtCedula, gridBagConstraints);

        boxEspecialidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rehabilitación oral", "Endodoncia", "Odontopediatría", "Ortodoncia", "Prostodoncia" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(boxEspecialidad, gridBagConstraints);

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

        jLabel4.setText("APELLIDO:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel4, gridBagConstraints);

        jLabel2.setText("CEDULA:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        jLabel7.setText("ESPECIALIDAD");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel7, gridBagConstraints);

        txtApellido.setMinimumSize(new java.awt.Dimension(100, 20));
        txtApellido.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(txtApellido, gridBagConstraints);

        jLabel3.setText("NOMBRE:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("REGISTRO MEDICO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 17, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel5.setText("FECHA  NACIMIENTO:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel5, gridBagConstraints);

        txtNombre.setMinimumSize(new java.awt.Dimension(100, 20));
        txtNombre.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(txtNombre, gridBagConstraints);

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

        jScrollPane1.setMinimumSize(new java.awt.Dimension(600, 300));
        jScrollPane1.setName(""); // NOI18N
        jScrollPane1.setPreferredSize(new java.awt.Dimension(600, 300));

        TablaMedico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cedula", "Nombre", "Apellido", "Fecha Nacimiento", "Genero", "Codigo", "Especialidad"
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
        TablaMedico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaMedicoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaMedico);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.gridwidth = 11;
        jPanel1.add(jScrollPane1, gridBagConstraints);

        FechaD.setMinimumSize(new java.awt.Dimension(100, 20));
        FechaD.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        jPanel1.add(FechaD, gridBagConstraints);

        jLabel10.setText("Genero");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel10, gridBagConstraints);

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

        jLabel12.setForeground(new java.awt.Color(0, 153, 153));
        jLabel12.setText("M: Masculino");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        jPanel1.add(jLabel12, gridBagConstraints);

        jLabel6.setText("Codigo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 13, 0);
        jPanel1.add(jLabel6, gridBagConstraints);

        txtCodigo.setEditable(false);
        txtCodigo.setMinimumSize(new java.awt.Dimension(100, 20));
        txtCodigo.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        jPanel1.add(txtCodigo, gridBagConstraints);

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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresoActionPerformed
        // TODO add your handling code here:
        RegistroDoctor();
        cargarcodigo();
    }//GEN-LAST:event_btnIngresoActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        Actualizar();
        cargarcodigo();
        
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        EliminarRegistro();
        Limpiar();
        listar();
        cargarcodigo();

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void TablaMedicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaMedicoMouseClicked

        try {
            // TODO add your handling code here:
            clic_tabla = TablaMedico.rowAtPoint(evt.getPoint());
            String Cedula = ""+TablaMedico.getValueAt(clic_tabla, 0);
            String Nombre = ""+TablaMedico.getValueAt(clic_tabla, 1);
            String Apellido =""+TablaMedico.getValueAt(clic_tabla, 2);
            String Fecha = ""+TablaMedico.getValueAt(clic_tabla, 3);
            String Genero= ""+TablaMedico.getValueAt(clic_tabla, 4);
            String Codigo= ""+TablaMedico.getValueAt(clic_tabla, 5);
            String Especialidad = ""+TablaMedico.getValueAt(clic_tabla, 6);
            Date F = new SimpleDateFormat("dd/MM/yyyy").parse(Fecha);
            txtCedula.setText(Cedula);
            txtNombre.setText(Nombre);
            txtApellido.setText(Apellido);
            txtCodigo.setText(Codigo);
            FechaD.setDate(F);
            boxGenero.setSelectedItem(Genero);
            boxEspecialidad.setSelectedItem(Especialidad);
            
        } catch (ParseException ex) {
            Logger.getLogger(RegistroD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_TablaMedicoMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        /* try {
            AccesoAleatorioDoctor.compactarArchivo(new File("Doctor.dat"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de compactar el archivo: "+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }*/
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
            java.util.logging.Logger.getLogger(RegistroD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroD().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser FechaD;
    private javax.swing.JTable TablaMedico;
    private javax.swing.JComboBox<String> boxEspecialidad;
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
    // End of variables declaration//GEN-END:variables
}
