package vista;

import controlador.ParqueaderoControlador;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import modelo.Parqueadero;
import org.apache.log4j.Logger;


/**
 *
 * @author ALEJO
 */
public class GestionarParqueaderos extends javax.swing.JFrame {
      
    Parqueadero nuevoParqueadero = new Parqueadero();
    ParqueaderoControlador parqControla = new ParqueaderoControlador();
    
    public static DefaultTableModel modeloParq;
    int Fila;
       
    private final Logger log = Logger.getLogger(GestionarParqueaderos.class);
    private URL url = GestionarParqueaderos.class.getResource("Log4j.properties");
    
    /**
     * Creates new form nuevoUsuario
     */
    public GestionarParqueaderos() {       
        initComponents();
        setSize(713, 535);
        setResizable(false);
        setTitle("Gestionar parqueaderos");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        //Avisamos que esta ventana se encuentra abierta para que no deje cerrar sesion al usuario
        MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = true;
        
        parqControla.cargarTablaDeParqueaderos();
    }
    
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/gestParking.png"));
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

        jLabel3 = new javax.swing.JLabel();
        btn_eliminar = new javax.swing.JButton();
        btn_ingresar = new javax.swing.JButton();
        txt_nombreParqueadero = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_listaParqueaderos = new javax.swing.JTable();
        btn_generaPDF = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cmb_tipoParq = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconImage(getIconImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Nombre de parqueadero:");

        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ic_delete_128_28267.png"))); // NOI18N
        btn_eliminar.setText("Eliminar");
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

        btn_ingresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Save_icon-icons.com_73702.png"))); // NOI18N
        btn_ingresar.setText("Ingresar");
        btn_ingresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ingresarActionPerformed(evt);
            }
        });

        txt_nombreParqueadero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_nombreParqueaderoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_nombreParqueaderoFocusLost(evt);
            }
        });
        txt_nombreParqueadero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nombreParqueaderoActionPerformed(evt);
            }
        });
        txt_nombreParqueadero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombreParqueaderoKeyTyped(evt);
            }
        });

        table_listaParqueaderos.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Estado", "Tipo", "Placa", "Propietario", "Está en parqueo"
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
        jScrollPane1.setViewportView(table_listaParqueaderos);

        btn_generaPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/generarPDF.png"))); // NOI18N
        btn_generaPDF.setText("Generar Informe PDF");
        btn_generaPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_generaPDFActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Tipo:");

        cmb_tipoParq.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "RESIDENTE", "VISITANTE" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_generaPDF)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txt_nombreParqueadero, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_ingresar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_eliminar))
                            .addComponent(cmb_tipoParq, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_nombreParqueadero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_tipoParq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_generaPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    //Metodo del boton cancelar
    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        
        Fila = table_listaParqueaderos.getSelectedRow();
        int cantidadFilas = table_listaParqueaderos.getSelectedRowCount();
        
                 
        if(cantidadFilas == 0){
            JOptionPane.showMessageDialog(null, "Seleccione el parqueadero que desea eliminar.");
        }else{    
            
            int decision = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea eliminar?", "Eliminar parqueadero", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            String nombreParqueadero = table_listaParqueaderos.getValueAt(Fila, 0).toString();
            
            if(decision == JOptionPane.YES_OPTION){
                
                boolean propietarioEnParqueadero = parqControla.evaluarSiEstaPropietarioEnParqueadero(nombreParqueadero);
                
                if(propietarioEnParqueadero){
                    JOptionPane.showMessageDialog(null, "El parqueadero indicado tiene cupo actualmente en parqueadero.");
                }else{
                    parqControla.eliminarParqueadero(nombreParqueadero);
                    modeloParq.removeRow(Fila);
                }
            }else if(decision == JOptionPane.NO_OPTION){}
        }       
    }//GEN-LAST:event_btn_eliminarActionPerformed

    //Metodo boton Ingresar
    private void btn_ingresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ingresarActionPerformed
        registrarParqueadero();
    }//GEN-LAST:event_btn_ingresarActionPerformed

    private void txt_nombreParqueaderoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombreParqueaderoKeyTyped
        //Forza aescribir en mayuscula
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            evt.setKeyChar(Character.toUpperCase(c));
        }
        
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 30;
        if(txt_nombreParqueadero.getText().length()== numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Solo 30 caracteres");
            txt_nombreParqueadero.setText("");
        } 
    }//GEN-LAST:event_txt_nombreParqueaderoKeyTyped

    //Metodo boton generar pdf
    private void btn_generaPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_generaPDFActionPerformed
        parqControla.generarPDFParqueaderosRegistrados();
        btn_generaPDF.setEnabled(false);
    }//GEN-LAST:event_btn_generaPDFActionPerformed

    private void txt_nombreParqueaderoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nombreParqueaderoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombreParqueaderoActionPerformed

    private void txt_nombreParqueaderoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_nombreParqueaderoFocusGained
        btn_eliminar.setEnabled(false);
        btn_ingresar.setEnabled(true);
    }//GEN-LAST:event_txt_nombreParqueaderoFocusGained

    //Aqui programamos lo que queremos quehaga al cerrar el jframe
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        cerrarGestorParqueaderos();
    }//GEN-LAST:event_formWindowClosing

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowActivated

    private void txt_nombreParqueaderoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_nombreParqueaderoFocusLost
        btn_eliminar.setEnabled(true);
    }//GEN-LAST:event_txt_nombreParqueaderoFocusLost

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
            java.util.logging.Logger.getLogger(GestionarParqueaderos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionarParqueaderos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionarParqueaderos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionarParqueaderos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                    new GestionarParqueaderos().setVisible(true);
                }catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    //Logger.getLogger(GestionarParqueaderos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_eliminar;
    public static javax.swing.JButton btn_generaPDF;
    private javax.swing.JButton btn_ingresar;
    private javax.swing.JComboBox<String> cmb_tipoParq;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable table_listaParqueaderos;
    private javax.swing.JTextField txt_nombreParqueadero;
    // End of variables declaration//GEN-END:variables
 
    //Metodo que registra un tablero compartido
    public void registrarParqueadero(){
       
        int validacion = 0;
        String nombreParqueadero;
        String tipo_parqstr = "";
        
        nombreParqueadero = txt_nombreParqueadero.getText().trim();
        int tipo_parq = cmb_tipoParq.getSelectedIndex();
                       
        if(nombreParqueadero.equals("")){
            txt_nombreParqueadero.setBackground(Color.red);
            validacion++;
        }
        
        if(tipo_parq == 0){
            tipo_parqstr = "Seleccione";
            cmb_tipoParq.setBackground(Color.red);
            validacion++;
        }        
        else if(tipo_parq == 1){
            tipo_parqstr = "RESIDENTE";
        }else if(tipo_parq == 2){
            tipo_parqstr = "VISITANTE";
        }
        
        //Valida si el usuario en cuestion ya fue creado con anterioridad
        boolean elParqueaderoExiste = parqControla.evaluarExistenciaDeParqueadero(nombreParqueadero);
        
        if(elParqueaderoExiste){
            txt_nombreParqueadero.setBackground(Color.red);
            JOptionPane.showMessageDialog(null, "Nombre de parqueadero no disponible.");
            Normalizar();
        }else{
            
            if(validacion == 0){
                //Encapsulamos el objeto Parqueadero
                nuevoParqueadero.setId(0);
                nuevoParqueadero.setNombre(nombreParqueadero);
                nuevoParqueadero.setTipoParqueadero(tipo_parqstr);
                nuevoParqueadero.setEstado("Disponible");
                
                parqControla.crearParqueadero(nuevoParqueadero);
                
                Object[] fila = new Object[6];
                fila[0] = nombreParqueadero;
                fila[1] = "Disponible";
                fila[2] = tipo_parqstr;
                modeloParq.addRow(fila);

                JOptionPane.showMessageDialog(null, "Parqueadero registrado satisfactoriamente.");
                Limpiar();
                Normalizar();
                txt_nombreParqueadero.requestFocus();
            
            }else {
                JOptionPane.showMessageDialog(null, "Debes de llenar todos los campos.");
                Normalizar();
            }
        }           
    }     
 
    //Metodo que limpia el formulario en caso de ingresar tablero principal
    public void Limpiar(){
        txt_nombreParqueadero.setText("");
        cmb_tipoParq.setSelectedIndex(0);
    }
    
    //Metodo que normaliza el formulario en caso tablero compartido
    public void Normalizar(){
        txt_nombreParqueadero.setBackground(Color.WHITE);
    }
    
    //Metodo que se invoca al cerrar el jFrame
    private void cerrarGestorParqueaderos(){
        
        String botones[] = {"Si", "No"};
        int eleccion = JOptionPane.showOptionDialog(this, "¿Está seguro que desea cerrar?", "Administrador de parqueaderos", 0, 3, null, botones, this);
        
        if(eleccion == JOptionPane.YES_OPTION){
            dispose();
            //Avisamos que esta ventana se encuentra cerrada 
            MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = false;
            PanelParametros.btn_parqueaderos.setEnabled(true);
        }
    }
}


