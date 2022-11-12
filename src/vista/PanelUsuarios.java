package vista;

import controlador.UsuarioControlador;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;

/**
 *
 * @author ALEJO
 */
public class PanelUsuarios extends javax.swing.JPanel {

    
    public static String usuario_update;
    public static DefaultTableModel modelo;
    public static boolean hayUsuarioAbierto = false;
    
    UsuarioControlador usuControlador = new UsuarioControlador();
    
    private final Logger log = Logger.getLogger(PanelUsuarios.class);
    private URL url = PanelUsuarios.class.getResource("Log4j.properties");
    
    
    /**
     * Creates new form PanelUsuarios
     */
    public PanelUsuarios() {
        initComponents();
        
        usuControlador.cargarTablaDeUsuariosPorDefault();
           
        //Agregamos la funcion de ver informacion del cliente al hacer click sobre el registro de la tabla
        Table_listaUsuarios.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e){
            int fila_point = Table_listaUsuarios.rowAtPoint(e.getPoint());
            int columna_point = 0;

            if(fila_point > -1){
                usuario_update = (String) modelo.getValueAt(fila_point, columna_point);
                
                if(hayUsuarioAbierto == true){
                }else{
                    hayUsuarioAbierto = true;
                    InformacionUsuario informacion_usuario = new InformacionUsuario();
                    informacion_usuario.setVisible(true);
                }
            }
        }
    });
          
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
        btn_nuevoUsuario = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_listaUsuarios = new javax.swing.JTable();
        btn_generaPDF = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_busqueda = new javax.swing.JTextField();

        btn_nuevoUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/adduser.png"))); // NOI18N
        btn_nuevoUsuario.setText("Nuevo Usuario");
        btn_nuevoUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_nuevoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoUsuarioActionPerformed(evt);
            }
        });

        Table_listaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Nombres", "Apellidos", "Celular", "Telefono", "Usuario", "Rol"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_listaUsuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.setViewportView(Table_listaUsuarios);

        btn_generaPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/generarPDF.png"))); // NOI18N
        btn_generaPDF.setText("Generar Informe PDF");
        btn_generaPDF.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_generaPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_generaPDFActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar por"));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Nombre:");

        txt_busqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_busquedaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_busquedaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 22, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(16, 16, 16)
                .addComponent(txt_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btn_nuevoUsuario)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(591, 591, 591)
                            .addComponent(btn_generaPDF)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 762, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(48, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_nuevoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(199, 199, 199))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_generaPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(16, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    //Metodo del boton Generar Reporte PDF
    private void btn_generaPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_generaPDFActionPerformed
        usuControlador.generarReportePDFdeUsuariosRegistrados();
    }//GEN-LAST:event_btn_generaPDFActionPerformed

    //Metodo boton "Nuevo Usuario"
    private void btn_nuevoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoUsuarioActionPerformed
        new NuevoUsuario().setVisible(true);
        btn_nuevoUsuario.setEnabled(false);
        
    }//GEN-LAST:event_btn_nuevoUsuarioActionPerformed

    private void txt_busquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_busquedaKeyReleased
        String buscar = txt_busqueda.getText();
        usuControlador.busquedaUsuario(buscar);
    }//GEN-LAST:event_txt_busquedaKeyReleased

    private void txt_busquedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_busquedaKeyTyped
        //Forza aescribir en mayuscula
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            evt.setKeyChar(Character.toUpperCase(c));
        }
    }//GEN-LAST:event_txt_busquedaKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTable Table_listaUsuarios;
    private javax.swing.JButton btn_generaPDF;
    public static javax.swing.JButton btn_nuevoUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txt_busqueda;
    // End of variables declaration//GEN-END:variables

       
}
