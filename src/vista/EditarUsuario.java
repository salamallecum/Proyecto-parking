package vista;

import controlador.UsuarioControlador;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import modelo.Usuario;
import org.apache.log4j.Logger;


/**
 *
 * @author ALEJO
 */
public class EditarUsuario extends javax.swing.JFrame {

    String usuario_actualizado="";
    String usuario;
    javax.swing.JTable tablaUsuarios;
    int FilaAnterior;
    DefaultTableModel modelo;
    int validacion = 0;
    int ID;
        
    Usuario usuarioConsultado = new Usuario();
    Usuario usuarioAModificar = new Usuario();
    UsuarioControlador usuControlador = new UsuarioControlador();
      
    
    private final Logger log = Logger.getLogger(EditarUsuario.class);
    private URL url = EditarUsuario.class.getResource("Log4j.properties");
    
    /**
     * Creates new form nuevoUsuario
     */
    public EditarUsuario() {
        initComponents();
        usuario = Login.usuario;
        usuario_actualizado = PanelUsuarios.usuario_update;
        tablaUsuarios = PanelUsuarios.Table_listaUsuarios;
        modelo = PanelUsuarios.modelo;
                
        setSize(522,429);
        setResizable(false);
        setTitle("Editar usuario ");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        //Avisamos que esta ventana se encuentra abierta para que no deje cerrar sesion al usuario
        MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = true;
         
        //Consultamos la info del usuario en la BD
        usuarioConsultado = usuControlador.consultarUsuario(usuario_actualizado);
        
        //Desempaquetamos el objeto usuario y lo mostramos en el frame
        ID = usuarioConsultado.getId();
        txt_nombres.setText(usuarioConsultado.getNombres());
        txt_apellidos.setText(usuarioConsultado.getApellidos());
        txt_celular.setText(usuarioConsultado.getCelular());
        txt_telefono.setText(usuarioConsultado.getTelefono());
        txt_nombreUsuario.setText(usuarioConsultado.getUsuario());
        txt_clave.setText(usuarioConsultado.getClave());
        
        if(usuarioConsultado.getRol().equals("Administra")){
            cmb_Rol.setSelectedIndex(1);
        }else if (usuarioConsultado.getRol().equals("Usuario")){
            cmb_Rol.setSelectedIndex(2);
        }
        
        if(usuarioConsultado.getActivo().equals("Si")){
            cmb_usuarioActivo.setSelectedIndex(1);
        }else if (usuarioConsultado.getActivo().equals("No")){
            cmb_usuarioActivo.setSelectedIndex(2);
        }   
    }
    
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/editarUsuario.png"));
        return retValue;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_nombres = new javax.swing.JTextField();
        txt_apellidos = new javax.swing.JTextField();
        txt_celular = new javax.swing.JTextField();
        txt_telefono = new javax.swing.JTextField();
        txt_clave = new javax.swing.JTextField();
        cmb_Rol = new javax.swing.JComboBox<>();
        btn_actualizar = new javax.swing.JButton();
        lbl_iconoEditUsuario = new javax.swing.JLabel();
        txt_nombreUsuario = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cmb_usuarioActivo = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconImage(getIconImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Nombres:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Apellidos:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Celular:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Telefono:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Usuario:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Clave:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Perfil:");

        txt_nombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombresKeyTyped(evt);
            }
        });

        txt_apellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_apellidosKeyTyped(evt);
            }
        });

        txt_celular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_celularKeyTyped(evt);
            }
        });

        txt_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telefonoKeyTyped(evt);
            }
        });

        txt_clave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_claveKeyTyped(evt);
            }
        });

        cmb_Rol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "Administra", "Operario" }));

        btn_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh256_24854.png"))); // NOI18N
        btn_actualizar.setText("Actualizar");
        btn_actualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });

        lbl_iconoEditUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_iconoEditUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit-user.png"))); // NOI18N

        txt_nombreUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombreUsuarioKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Activo?:");

        cmb_usuarioActivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "Si", "No" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txt_nombres, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_celular, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_apellidos)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addGap(24, 24, 24)
                            .addComponent(cmb_usuarioActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cmb_Rol, 0, 93, Short.MAX_VALUE)
                                .addComponent(txt_clave, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                                .addComponent(txt_telefono, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                                .addComponent(txt_nombreUsuario)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(lbl_iconoEditUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
            .addGroup(layout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addComponent(btn_actualizar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel1)
                            .addComponent(txt_nombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel2)
                            .addComponent(txt_apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txt_celular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txt_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel6)
                            .addComponent(txt_clave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(cmb_Rol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(lbl_iconoEditUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cmb_usuarioActivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(btn_actualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    //Metodo boton Guardar
    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        
        int roles_cmb, activo_cmb = 0;
        String nombres, apellidos, celular, telefono, usu, pass, roles_string = "";
        String activo_string = "";
        
        nombres = txt_nombres.getText().trim();
        apellidos = txt_apellidos.getText().trim();
        celular = txt_celular.getText().trim();
        telefono = txt_telefono.getText().trim();
        usu = txt_nombreUsuario.getText().trim();
        pass = txt_clave.getText().trim();
        roles_cmb = cmb_Rol.getSelectedIndex() + 1;
        activo_cmb = cmb_usuarioActivo.getSelectedIndex() + 1;
        
        if(nombres.equals("")){
            txt_nombres.setBackground(Color.red);
            validacion++;
        }
        if(apellidos.equals("")){
            txt_apellidos.setBackground(Color.red);
            validacion++;
        }
        if(celular.equals("")){
            txt_celular.setBackground(Color.red);
            validacion++;
        }
        if(telefono.equals("")){
            txt_telefono.setBackground(Color.red);
            validacion++;
        }
        
        if(usu.equals("")){
            txt_nombreUsuario.setBackground(Color.red);
            validacion++;
        }
        
        if(pass.equals("")){
            txt_clave.setBackground(Color.red);
            validacion++;
        }   
        
        if(roles_cmb == 1){
            roles_string = "Seleccione";
            cmb_Rol.setBackground(Color.red);
            validacion++;
            
        } else if(roles_cmb == 2){
            roles_string = "Administra";
        } else if(roles_cmb == 3){
            roles_string = "Operario";
        }
        
        if(activo_cmb == 1){
            activo_string = "Seleccione";
            cmb_Rol.setBackground(Color.red);
            validacion++;
            
        } else if(activo_cmb == 2){
            activo_string = "Si";
        } else if(activo_cmb == 3){
            activo_string = "No";
        } 
        
        if (validacion == 0) {

            FilaAnterior = tablaUsuarios.getSelectedRow();
            //String nombre = tablaUsuarios.getValueAt(FilaAnterior, 0).toString();
            
            //Encapsulamos el objeto
            usuarioAModificar.setId(ID);
            usuarioAModificar.setNombres(nombres);
            usuarioAModificar.setApellidos(apellidos);
            usuarioAModificar.setCelular(celular);
            usuarioAModificar.setTelefono(telefono);
            usuarioAModificar.setUsuario(usu);
            usuarioAModificar.setClave(pass);
            usuarioAModificar.setRol(roles_string);
            usuarioAModificar.setActivo(activo_string);
            
            //Moficamos el usuario en la BD
            usuControlador.modificarUsuario(usuarioAModificar);
            
            //Modificamos el registro en la tabla en tiempo real
            Object Fila[] = new Object[6];
            Fila[0] = nombres;
            Fila[1] = apellidos;
            Fila[2] = celular;
            Fila[3] = telefono;
            Fila[4] = usu;
            Fila[5] = roles_string;

            modelo.addRow(Fila);
            modelo.removeRow(FilaAnterior);

            JOptionPane.showMessageDialog(null, "Usuario actualizado satisfactoriamente.");
            this.dispose();  
            PanelUsuarios.hayUsuarioAbierto = false;
            MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = false;

        } else {
            JOptionPane.showMessageDialog(null, "Debes llenar todos los campos.");
            Normalizar();
        } 
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void txt_nombresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombresKeyTyped
        ///Forza a escribir en mayuscula
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            evt.setKeyChar(Character.toUpperCase(c));
        }
        
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 29;
        if(txt_nombres.getText().length() > numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Solo 30 caracteres");
        }
    }//GEN-LAST:event_txt_nombresKeyTyped

    private void txt_apellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_apellidosKeyTyped
        ////Forza a escribir en mayuscula
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            evt.setKeyChar(Character.toUpperCase(c));
            
        }
        
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 29;
        if(txt_apellidos.getText().length() > numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Solo 30 caracteres");  
        }
    }//GEN-LAST:event_txt_apellidosKeyTyped

    private void txt_celularKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_celularKeyTyped
       //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 9;
        if(txt_celular.getText().length() > numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Solo 10 caracteres");  
        }
        
        //Evalua que se digiten numeros no letras
        char validar = evt.getKeyChar();
        
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            
            JOptionPane.showMessageDialog(rootPane, "Ingrese solo numeros.");
        }
    }//GEN-LAST:event_txt_celularKeyTyped

    private void txt_telefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telefonoKeyTyped
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 9;
        if(txt_telefono.getText().length() > numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Solo 10 caracteres");  
        }
        
        //Evalua que se digiten numeros no letras
        char validar = evt.getKeyChar();
        
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            
            JOptionPane.showMessageDialog(rootPane, "Ingrese solo numeros.");
        }
    }//GEN-LAST:event_txt_telefonoKeyTyped

    private void txt_nombreUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombreUsuarioKeyTyped
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 9;
        if(txt_nombreUsuario.getText().length() > numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Solo 10 caracteres");  
        }
    }//GEN-LAST:event_txt_nombreUsuarioKeyTyped

    private void txt_claveKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_claveKeyTyped
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 9;
        if(txt_telefono.getText().length() > numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Solo 10 caracteres");  
        }
    }//GEN-LAST:event_txt_claveKeyTyped

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        cerrarEdicionUsuario();
    }//GEN-LAST:event_formWindowClosing

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowDeactivated

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
            java.util.logging.Logger.getLogger(EditarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                try {
                    //Esto cambia la apariencia de la app para que se acomode al Siste Operativo
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    new EditarUsuario().setVisible(true);
                }catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    //Logger.getLogger(EditarUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_actualizar;
    private javax.swing.JComboBox<String> cmb_Rol;
    public static javax.swing.JComboBox<String> cmb_usuarioActivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lbl_iconoEditUsuario;
    private javax.swing.JTextField txt_apellidos;
    private javax.swing.JTextField txt_celular;
    private javax.swing.JTextField txt_clave;
    private javax.swing.JTextField txt_nombreUsuario;
    private javax.swing.JTextField txt_nombres;
    private javax.swing.JTextField txt_telefono;
    // End of variables declaration//GEN-END:variables

    //Metodo que limpia el formulario
    public void Limpiar(){
        txt_nombres.setText("");
        txt_apellidos.setText("");
        txt_celular.setText("");
        txt_telefono.setText("");
        txt_nombreUsuario.setText("");
        txt_clave.setText("");
        cmb_Rol.setSelectedIndex(0);
    }
    
    //Metodo que normaliza el formulario
    public void Normalizar(){
        txt_nombres.setBackground(Color.WHITE);
        txt_apellidos.setBackground(Color.WHITE);
        txt_celular.setBackground(Color.WHITE);
        txt_telefono.setBackground(Color.WHITE);
        txt_nombreUsuario.setBackground(Color.WHITE);
        txt_clave.setBackground(Color.WHITE);
    }
    
    //Metodo que se invoca al cerrar el jFrame
    private void cerrarEdicionUsuario(){
        
        String botones[] = {"Si", "No"};
        int eleccion = JOptionPane.showOptionDialog(this, "¿Está seguro que desea cerrar?", "Editar usuario", 0, 3, null, botones, this);
        
        if(eleccion == JOptionPane.YES_OPTION){
            dispose();
            PanelUsuarios.hayUsuarioAbierto = false;
            MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = false;
        }
    }
}
