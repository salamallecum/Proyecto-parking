package vista;

import clasesDeApoyo.Conexion;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.Logger;

/**
 *
 * @author ALEJO
 */
public class InformacionCierre extends javax.swing.JFrame {

    String usuario;
    int cierre_actualizado;
    public static int ID;
    javax.swing.JTable tablaOperacionCierres;
    DefaultTableModel modelo;
    int Fila;
    
    private final Logger log = Logger.getLogger(InformacionCierre.class);
    private URL url = InformacionCierre.class.getResource("Log4j.properties");
    
          
    /**
     * Creates new form LiquidacionVehiculo
     */
    public InformacionCierre(){
        initComponents();
        usuario = Login.usuario;
        cierre_actualizado = GestionarCierres.codigoCierre_update;
        tablaOperacionCierres = GestionarCierres.table_listaCierres;
        modelo = GestionarCierres.modelo;
      
        
        setSize(420,500);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Información de cierre");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        Fila = tablaOperacionCierres.getSelectedRow();      
        
        //Hace la consulta de los datos del cierre a la base de datos
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "SELECT Codigo, Fecha_cierre, Nombre_usuario, Producido, Dinero_en_caja, Diferencia, No_facturas, Observaciones from cierres where Codigo='"+ cierre_actualizado + "'");

            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                lbl_codigo.setText(rs.getString("Codigo"));
                lbl_fecha.setText(rs.getString("Fecha_cierre"));
                lbl_usuario.setText(rs.getString("Nombre_usuario"));
                lbl_producido.setText(rs.getString("Producido"));
                lbl_dineroCaja.setText(rs.getString("Dinero_en_caja"));
                lbl_diferencia.setText(rs.getString("Diferencia"));
                lbl_noFacturas.setText(rs.getString("No_facturas"));
                jTextArea_observaciones.setText(rs.getString("Observaciones"));              
            }  
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar cierre1!, contacte al administrador.");
        }
    }
    
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/tarif.png"));
        return retValue;
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_imprimirCierre = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbl_codigo = new javax.swing.JLabel();
        lbl_fecha = new javax.swing.JLabel();
        lbl_producido = new javax.swing.JLabel();
        lbl_dineroCaja = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbl_usuario = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbl_diferencia = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lbl_noFacturas = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btn_eliminar = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea_observaciones = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());

        btn_imprimirCierre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/preview.png"))); // NOI18N
        btn_imprimirCierre.setText("Vista Previa");
        btn_imprimirCierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimirCierreActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Cod. Cierre:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Fecha:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Producido:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Dinero en Caja:");

        lbl_codigo.setText("codigo_cierre");

        lbl_fecha.setText("fecha_del_ cierre");

        lbl_producido.setText("Produc");

        lbl_dineroCaja.setText("Dinero_Caja");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Usuario:");

        lbl_usuario.setText("Usuario");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Diferencia:");

        lbl_diferencia.setText("Dif_con_Caja");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("N° facturas:");

        lbl_noFacturas.setText("NFacturas");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Observaciones:");

        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ic_delete_128_28267.png"))); // NOI18N
        btn_eliminar.setText("Eliminar");
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

        btn_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Cancelar.png"))); // NOI18N
        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });

        jTextArea_observaciones.setEditable(false);
        jTextArea_observaciones.setColumns(20);
        jTextArea_observaciones.setLineWrap(true);
        jTextArea_observaciones.setRows(5);
        jTextArea_observaciones.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextArea_observaciones);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_codigo)
                            .addComponent(lbl_fecha)
                            .addComponent(lbl_usuario)
                            .addComponent(lbl_producido)
                            .addComponent(lbl_dineroCaja)
                            .addComponent(lbl_diferencia)
                            .addComponent(lbl_noFacturas)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(btn_imprimirCierre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_eliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cancelar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbl_codigo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lbl_fecha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lbl_usuario))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lbl_producido))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lbl_dineroCaja))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lbl_diferencia))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_noFacturas)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_imprimirCierre, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_imprimirCierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirCierreActionPerformed
        String codigo_str = lbl_codigo.getText();
        int codigo = Integer.parseInt(codigo_str);
        generarDuplicadoCierre(codigo);       
    }//GEN-LAST:event_btn_imprimirCierreActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        
        int decision = JOptionPane.showConfirmDialog(this, "Una vez eliminado el cierre, se borrarán todas las facturas implicadas con el mismo, ¿Desea continuar?", "Eliminar cierre", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if(decision == JOptionPane.YES_OPTION){    
            try {
                Connection cn = Conexion.conectar();
                PreparedStatement pst = cn.prepareStatement("delete from cierres where Codigo= '"+ cierre_actualizado + "'");
                pst.executeUpdate(); 
                
                int filaSelec = tablaOperacionCierres.getSelectedRow();
                                
                modelo.removeRow(filaSelec);
                    
                JOptionPane.showMessageDialog(null, "Cierre eliminado satisfactoriamente.");            
                
                dispose();
           
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "¡¡ERROR al eliminar!!, contacte al administrador.");
            }
        }
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        GestionarCierres.hayCierreAbierto = false;
        dispose();
        
    }//GEN-LAST:event_btn_cancelarActionPerformed

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
            java.util.logging.Logger.getLogger(InformacionCierre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InformacionCierre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InformacionCierre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InformacionCierre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                    new InformacionCierre().setVisible(true);
                }catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    //Logger.getLogger(InformacionCierre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_imprimirCierre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea_observaciones;
    public static javax.swing.JLabel lbl_codigo;
    public static javax.swing.JLabel lbl_diferencia;
    public static javax.swing.JLabel lbl_dineroCaja;
    public static javax.swing.JLabel lbl_fecha;
    public static javax.swing.JLabel lbl_noFacturas;
    public static javax.swing.JLabel lbl_producido;
    public static javax.swing.JLabel lbl_usuario;
    // End of variables declaration//GEN-END:variables

    
    //Metodo que imprime duplicado de un cierre
    public void generarDuplicadoCierre(int cod_tick){
        
         try{
            Connection cn3 = Conexion.conectar();

            Map parametro = new HashMap();
            parametro.clear();
            parametro.put("codigo", cod_tick);
            
            JasperReport reporte = null;
            //String path = "src\\Reportes\\DuplicadoDeCierre.jasper";

            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/DuplicadoDeCierre.jasper"));

            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, cn3);

            //Da una vista previa del ticket
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            view.setIconImage(obtenerIconoReporte());
            view.setTitle("Duplicado de cierre");
            
            
        }catch(JRException ex){
            //Logger.getLogger(InformacionCierre.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "¡¡ERROR al generar duplicado de Cierre, contacte al administrador!!");
        }
    }
    
    public Image obtenerIconoReporte() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagenes/tarif.png"));
        return retValue;
    }
}
