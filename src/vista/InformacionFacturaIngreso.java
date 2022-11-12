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
public class InformacionFacturaIngreso extends javax.swing.JFrame {

    String usuario;
    String factura_actualizada;
    public static int ID;
    javax.swing.JTable tablaOperacionFacturas;
    DefaultTableModel modelo;
    int Fila;
    
    private final Logger log = Logger.getLogger(InformacionFacturaIngreso.class);
    private URL url = InformacionFacturaIngreso.class.getResource("Log4j.properties");
    
          
    /**
     * Creates new form LiquidacionVehiculo
     */
    public InformacionFacturaIngreso() {
        initComponents();
        usuario = Login.usuario;
        factura_actualizada = GestionarFacturas.codigoFactura_update;
        tablaOperacionFacturas = GestionarFacturas.table_listaFacturas;
        modelo = GestionarFacturas.modelo;
        
        setSize(337,369);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Información de factura");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        Fila = tablaOperacionFacturas.getSelectedRow();
        
        
        //Hace la consulta de los daos de la factura a la base de datos
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "SELECT Fac.Id_factura, Fac.Placa, Fac.Propietario, Fac.Tipo_vehiculo, Fac.Estado_fctra, Fac.Hora_ingreso, Parq.Nombre_parqueadero, Conv.Nombre_convenio, Tar.Nombre_tarifa from facturas Fac INNER JOIN parqueaderos Parq ON Fac.No_parqueadero = Parq.Id_parqueadero INNER JOIN convenios Conv ON Fac.Id_convenio = Conv.Id_convenio INNER JOIN tarifas Tar ON Fac.Id_tarifa = Tar.Id_tarifa AND Fac.Codigo = '" + factura_actualizada + "' AND Fac.Estado_fctra = 'Abierta'");

            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                ID = rs.getInt("Fac.Id_factura");
                lbl_placa.setText(rs.getString("Fac.Placa"));
                lbl_propietario.setText(rs.getString("Fac.Propietario"));
                lbl_tipoVehiculo.setText(rs.getString("Fac.Tipo_vehiculo"));
                lbl_noParqueadero.setText(rs.getString("Parq.Nombre_parqueadero"));
                lbl_horaIngreso.setText(rs.getString("Fac.Hora_ingreso"));
                lbl_convenio.setText(rs.getString("Conv.Nombre_convenio"));
                lbl_tarifa.setText(rs.getString("Tar.Nombre_tarifa"));
                
                //Verifica si el vehiculo en cuestion esta registrado para asi bloquear btn editar
                validarSiEsVehiculoRegistrado();
            }  
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar factura!!, contacte al administrador.");
        }

        //Traemos los datos del Jtable del gestionar facturas
        lbl_codigo.setText(tablaOperacionFacturas.getValueAt(Fila, 0).toString());
        lbl_facturadoPor.setText(tablaOperacionFacturas.getValueAt(Fila, 2).toString());
        
    }
    
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/bill_icon.png"));
        return retValue;
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_imprimirFactura = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbl_codigo = new javax.swing.JLabel();
        lbl_placa = new javax.swing.JLabel();
        lbl_tipoVehiculo = new javax.swing.JLabel();
        lbl_horaIngreso = new javax.swing.JLabel();
        lbl_noParqueadero = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbl_propietario = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbl_facturadoPor = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lbl_convenio = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbl_tarifa = new javax.swing.JLabel();
        btn_editar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());

        btn_imprimirFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/preview.png"))); // NOI18N
        btn_imprimirFactura.setText("Vista Previa");
        btn_imprimirFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimirFacturaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Cod. Factura:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Placa:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Tipo Vehiculo:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Hora Ingreso:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Numero de Parqueadero:");

        lbl_codigo.setText("codigo_factura");

        lbl_placa.setText("placa_vehiculo");

        lbl_tipoVehiculo.setText("tipo_vehiculo");

        lbl_horaIngreso.setText("hora_entrada");

        lbl_noParqueadero.setText("numero_parqueadero");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Propietario:");

        lbl_propietario.setText("propietario_vehiculo");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Facturado por:");

        lbl_facturadoPor.setText("usuarioQueFactura");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Convenio:");

        lbl_convenio.setText("convenioDelVehiculo");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Tarifa:");

        lbl_tarifa.setText("tarifaDelVehiculo");

        btn_editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit-validated_40458.png"))); // NOI18N
        btn_editar.setText("Editar");
        btn_editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editarActionPerformed(evt);
            }
        });

        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ic_delete_128_28267.png"))); // NOI18N
        btn_eliminar.setText("Eliminar");
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_codigo)
                            .addComponent(lbl_placa)
                            .addComponent(lbl_propietario)
                            .addComponent(lbl_tipoVehiculo)
                            .addComponent(lbl_noParqueadero)
                            .addComponent(lbl_facturadoPor)
                            .addComponent(lbl_convenio)
                            .addComponent(lbl_tarifa)
                            .addComponent(lbl_horaIngreso)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_imprimirFactura)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_editar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_eliminar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbl_codigo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lbl_placa))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lbl_propietario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lbl_tipoVehiculo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lbl_noParqueadero))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lbl_facturadoPor))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lbl_convenio))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lbl_tarifa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lbl_horaIngreso))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_imprimirFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_imprimirFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirFacturaActionPerformed
        String placa = lbl_placa.getText();
        generarDuplicadoFactura(placa);       
    }//GEN-LAST:event_btn_imprimirFacturaActionPerformed

    private void btn_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editarActionPerformed
        dispose();
        new EditarFacturaIngreso().setVisible(true);
    }//GEN-LAST:event_btn_editarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        
        int decision = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea eliminar?", "Eliminar factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if(decision == JOptionPane.YES_OPTION){    
            try {
                Connection cn = Conexion.conectar();
                PreparedStatement pst = cn.prepareStatement("delete from facturas where Codigo= '"+ factura_actualizada + "'");
                pst.executeUpdate(); 
                
                int filaSelec = tablaOperacionFacturas.getSelectedRow();
                                
                modelo.removeRow(filaSelec);
                    
                JOptionPane.showMessageDialog(null, "La factura ha sido eliminada satisfactoriamente.");            
                
                dispose();
           
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "¡¡ERROR al eliminar!!, contacte al administrador.");
            }
        }
    }//GEN-LAST:event_btn_eliminarActionPerformed

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
            java.util.logging.Logger.getLogger(InformacionFacturaIngreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InformacionFacturaIngreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InformacionFacturaIngreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InformacionFacturaIngreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                    new InformacionFacturaIngreso().setVisible(true);
                }catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    //Logger.getLogger(InformacionFacturaIngreso.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_editar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_imprimirFactura;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    public static javax.swing.JLabel lbl_codigo;
    public static javax.swing.JLabel lbl_convenio;
    public static javax.swing.JLabel lbl_facturadoPor;
    public static javax.swing.JLabel lbl_horaIngreso;
    public static javax.swing.JLabel lbl_noParqueadero;
    public static javax.swing.JLabel lbl_placa;
    public static javax.swing.JLabel lbl_propietario;
    public static javax.swing.JLabel lbl_tarifa;
    public static javax.swing.JLabel lbl_tipoVehiculo;
    // End of variables declaration//GEN-END:variables

    
    //Metodo que imprime el ticket de salida
    public void generarDuplicadoFactura(String placa_tick){
        
         try{
            Connection cn3 = Conexion.conectar();

            Map parametro = new HashMap();
            parametro.clear();
            parametro.put("placa_vehiculo", placa_tick);
            
            JasperReport reporte = null;
            //String path = "src\\Reportes\\DuplicadoFacturaIngreso.jasper";

            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/DuplicadoFacturaIngreso.jasper"));

            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, cn3);

            //Da una vista previa del ticket
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            view.setIconImage(obtenerIconoReporte());
            view.setTitle("Duplicado de factura");
           
            
        }catch(JRException ex){
            //Logger.getLogger(PanelUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "¡¡ERROR al generar Factura, contacte al administrador!!");
        }
    }
    
    //Metodo que valida si el vehiculo esta registrado y bloquea el boton editar
    public void validarSiEsVehiculoRegistrado(){
        
         //Valida si el vehiculo esta registrado
        try {
            String placa_im = lbl_placa.getText();
            
            Connection cn1 = Conexion.conectar();
            PreparedStatement pst1;
            pst1 = cn1.prepareStatement(
                        "SELECT Ve.Propietario, Ve.Clase, Parq.Nombre_parqueadero, Conv.Nombre_convenio, Tar.Nombre_tarifa FROM vehiculos Ve INNER JOIN parqueaderos Parq ON Ve.Id_parqueadero = Parq.Id_parqueadero INNER JOIN convenios Conv ON Ve.Id_convenio = Conv.Id_convenio INNER JOIN tarifas Tar ON Ve.Id_tarifa = Tar.Id_tarifa AND Ve.Placa='"+placa_im+"'");    
            
            ResultSet rs1 = pst1.executeQuery();

            if (rs1.next()) {
                btn_editar.setEnabled(false);
            }else{
                btn_editar.setEnabled(true);
            }
        
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al validar existencia de vehiculo!!, contacte al administrador.");
        }
    }
    
     public Image obtenerIconoReporte() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagenes/bill_icon.png"));
        return retValue;
    }
}
