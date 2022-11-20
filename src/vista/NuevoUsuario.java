package vista;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import static vista.MenuAdministrador.panelUsu;
import java.net.URL;
import modelo.Usuario;
import controlador.UsuarioControlador;
import org.apache.log4j.Logger;

/**
 *
 * @author ALEJO
 */
public class NuevoUsuario extends javax.swing.JFrame {
       
    Usuario nuevoUsuario = new Usuario(0, "", "", "", "", "", "", "", "");
    UsuarioControlador usuControlador = new UsuarioControlador();
    
    private final Logger log = Logger.getLogger(NuevoUsuario.class);
    private URL url = NuevoUsuario.class.getResource("Log4j.properties");
    /**
     * Creates new form nuevoUsuario
     */
    public NuevoUsuario() {
        initComponents();
        setSize(503,442);
        setResizable(false);
        setTitle("Nuevo usuario");
        setLocationRelativeTo(null);
        
        //Avisamos que esta ventana se encuentra abierta para que no deje cerrar sesion al usuario
        MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = true;
    }
    
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/adduser.png"));
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
        txt_nombreUsuario = new javax.swing.JTextField();
        txt_clave = new javax.swing.JTextField();
        cmb_Rol = new javax.swing.JComboBox<>();
        btn_cancelar = new javax.swing.JButton();
        btn_guardar = new javax.swing.JButton();
        lbl_iconoCrearUsuario = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmb_usuarioActivo = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Nuevo usuario");
        setIconImage(getIconImage());

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
        jLabel7.setText("Rol:");

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

        txt_nombreUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombreUsuarioKeyTyped(evt);
            }
        });

        txt_clave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_claveKeyTyped(evt);
            }
        });

        cmb_Rol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "Administra", "Usuario" }));

        btn_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Cancelar.png"))); // NOI18N
        btn_cancelar.setText("Cancelar");
        btn_cancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });

        btn_guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Save_icon-icons.com_73702.png"))); // NOI18N
        btn_guardar.setText("Guardar");
        btn_guardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });

        lbl_iconoCrearUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_iconoCrearUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new_user.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Activo?:");

        cmb_usuarioActivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "Si", "No" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_nombres, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_celular, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_clave, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_Rol, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_usuarioActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lbl_iconoCrearUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_guardar)
                .addGap(18, 18, 18)
                .addComponent(btn_cancelar)
                .addGap(72, 72, 72))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(lbl_iconoCrearUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel1)
                            .addComponent(txt_nombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel2)
                            .addComponent(txt_apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel3)
                            .addComponent(txt_celular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel4)
                            .addComponent(txt_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel5)
                            .addComponent(txt_nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel6)
                            .addComponent(txt_clave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel7)
                            .addComponent(cmb_Rol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(cmb_usuarioActivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    //Metodo del boton cancelar
    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        PanelUsuarios.btn_nuevoUsuario.setEnabled(true);
        dispose(); 
        //Avisamos que esta ventana se encuentra cerrada 
        MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = false;
    }//GEN-LAST:event_btn_cancelarActionPerformed

    //Metodo boton Guardar
    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        
        int roles_cmb, activo_cmb, validacion = 0;
        String nombres, apellidos, celular, telefono, usuario, pass, roles_string = "";
        String activo_string = "";
        
        nombres = txt_nombres.getText().trim();
        apellidos = txt_apellidos.getText().trim();
        celular = txt_celular.getText().trim();
        telefono = txt_telefono.getText().trim();
        usuario = txt_nombreUsuario.getText().trim();
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
        if(usuario.equals("")){
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
            roles_string = "Usuario";
        } 
        
        if(activo_cmb == 1){
            activo_string = "Seleccione";
            cmb_usuarioActivo.setBackground(Color.red);
            validacion++;
            
        } else if(activo_cmb == 2){
            activo_string = "Si";
        } else if(activo_cmb == 3){
            activo_string = "No";
        } 
        
        //Valida si el usuario en cuestion ya fue creado con anterioridad
        boolean elUsuarioExiste = usuControlador.validarExistenciaDeUsuarioEnBD(usuario);
        
        if(elUsuarioExiste){
            txt_nombreUsuario.setBackground(Color.red);
            JOptionPane.showMessageDialog(null, "Nombre de usuario no disponible.");
            Normalizar();
        }else{
            
            if (validacion == 0) {
                //Encapsulamos el objeto Usuario
                nuevoUsuario.setId(0);
                nuevoUsuario.setNombres(nombres);
                nuevoUsuario.setApellidos(apellidos);
                nuevoUsuario.setCelular(celular);
                nuevoUsuario.setTelefono(telefono);
                nuevoUsuario.setUsuario(usuario);
                nuevoUsuario.setClave(pass);
                nuevoUsuario.setRol(roles_string);
                nuevoUsuario.setActivo(activo_string);
                
                //Creamos el objeto usuario
                usuControlador.crearUsuario(nuevoUsuario);
                
                //Agregamos el objeto usuario a la tabla de usuarios
                Object[] fila = new Object[6];
                fila[0] = nombres;
                fila[1] = apellidos;
                fila[2] = celular;
                fila[3] = telefono;
                fila[4] = usuario;
                fila[5] = roles_string;
                PanelUsuarios.modelo.addRow(fila);
                
                txt_nombres.setBackground(Color.green);
                txt_apellidos.setBackground(Color.green);
                txt_celular.setBackground(Color.green);
                txt_telefono.setBackground(Color.green);
                txt_nombreUsuario.setBackground(Color.green);
                txt_clave.setBackground(Color.green);

                JOptionPane.showMessageDialog(null, "Registro exitoso.");
                log.info("INFO - Se ha registrado un nuevo usuario en el sistema.");
                Limpiar();
                this.dispose();
                panelUsu.setVisible(true);
                PanelUsuarios.btn_nuevoUsuario.setEnabled(true);
                
            } else {
                JOptionPane.showMessageDialog(null, "Debes de llenar todos los campos.");
                Normalizar();
            }
        }   
    }//GEN-LAST:event_btn_guardarActionPerformed

    private void txt_nombresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombresKeyTyped
        //Forza aescribir en mayuscula
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
        //Forza aescribir en mayuscula
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
        if(txt_clave.getText().length() > numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Solo 10 caracteres");  
        }
    }//GEN-LAST:event_txt_claveKeyTyped
       

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
            java.util.logging.Logger.getLogger(NuevoUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NuevoUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NuevoUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NuevoUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NuevoUsuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_guardar;
    public static javax.swing.JComboBox<String> cmb_Rol;
    public static javax.swing.JComboBox<String> cmb_usuarioActivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lbl_iconoCrearUsuario;
    public static javax.swing.JTextField txt_apellidos;
    public static javax.swing.JTextField txt_celular;
    public static javax.swing.JTextField txt_clave;
    public static javax.swing.JTextField txt_nombreUsuario;
    public static javax.swing.JTextField txt_nombres;
    public static javax.swing.JTextField txt_telefono;
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
        cmb_Rol.setBackground(Color.WHITE);
    }
}


