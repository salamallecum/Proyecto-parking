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
public class InformacionFacturaFinal extends javax.swing.JFrame {

    String usuario;
    String factura_actualizada;
    public static int ID;
    javax.swing.JTable tablaOperacionFacturas;
    DefaultTableModel modelo;
    int Fila;
    
    boolean facturaCont;
    
    private final Logger log = Logger.getLogger(InformacionFacturaFinal.class);
    private URL url = InformacionFacturaFinal.class.getResource("Log4j.properties");
          
    /**
     * Creates new form LiquidacionVehiculo
     */
    public InformacionFacturaFinal(){
        initComponents();
        usuario = Login.usuario;
        factura_actualizada = GestionarFacturas.codigoFactura_update;
        tablaOperacionFacturas = GestionarFacturas.table_listaFacturas;
        modelo = GestionarFacturas.modelo;
        
        setSize(400,595);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Información de factura");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        Fila = tablaOperacionFacturas.getSelectedRow();      
        
        //Hace la consulta de los datos de la factura a la base de datos
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "SELECT Fac.Id_factura, Fac.Placa, Fac.Propietario, Fac.Tipo_vehiculo, Fac.Estado_fctra, Fac.Hora_ingreso, Fac.hora_salida, Fac.Efectivo, Fac.Cambio, Parq.Nombre_parqueadero, Conv.Nombre_convenio, Tar.Nombre_tarifa from facturas Fac INNER JOIN parqueaderos Parq ON Fac.No_parqueadero = Parq.Id_parqueadero INNER JOIN convenios Conv ON Fac.Id_convenio = Conv.Id_convenio INNER JOIN tarifas Tar ON Fac.Id_tarifa = Tar.Id_tarifa AND Fac.Codigo = '" + factura_actualizada + "' AND Fac.Estado_fctra = 'Cerrada'");

            
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                ID = rs.getInt("Fac.Id_factura");
                lbl_placa.setText(rs.getString("Fac.Placa"));
                lbl_propietario.setText(rs.getString("Fac.Propietario"));
                lbl_tipoVehiculo.setText(rs.getString("Fac.Tipo_vehiculo"));
                lbl_noParqueadero.setText(rs.getString("Parq.Nombre_parqueadero"));
                lbl_horaIngreso.setText(rs.getString("Fac.Hora_ingreso"));
                lbl_horaSalida.setText(rs.getString("Fac.Hora_salida"));
                lbl_efectivo.setText(rs.getString("Fac.Efectivo"));
                lbl_dineroCambio.setText(rs.getString("Fac.Cambio"));
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
        lbl_totalAPagar.setText(tablaOperacionFacturas.getValueAt(Fila, 3).toString());
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
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbl_codigo = new javax.swing.JLabel();
        lbl_placa = new javax.swing.JLabel();
        lbl_tipoVehiculo = new javax.swing.JLabel();
        lbl_horaIngreso = new javax.swing.JLabel();
        lbl_horaSalida = new javax.swing.JLabel();
        lbl_noParqueadero = new javax.swing.JLabel();
        lbl_totalAPagar = new javax.swing.JLabel();
        lbl_dineroCambio = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbl_propietario = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbl_facturadoPor = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lbl_convenio = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbl_tarifa = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lbl_efectivo = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btn_editar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();

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

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Hora Salida:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Numero de Parqueadero:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Valor a pagar ($):");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Efectivo ($):");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Cambio ($):");

        lbl_codigo.setText("codigo_factura");

        lbl_placa.setText("placa_vehiculo");

        lbl_tipoVehiculo.setText("tipo_vehiculo");

        lbl_horaIngreso.setText("hora_entrada");

        lbl_horaSalida.setText("hora_salida");

        lbl_noParqueadero.setText("numero_parqueadero");

        lbl_totalAPagar.setText("valor_a_pagar");

        lbl_dineroCambio.setText("0");

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

        jLabel14.setText("pesos");

        lbl_efectivo.setText("0");

        jLabel16.setText("pesos");

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

        btn_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Cancelar.png"))); // NOI18N
        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lbl_horaSalida))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lbl_horaIngreso))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_totalAPagar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(18, 18, 18)
                                        .addComponent(lbl_dineroCambio))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lbl_efectivo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel16))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(lbl_placa))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(lbl_codigo)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_tipoVehiculo)
                                    .addComponent(lbl_propietario)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_noParqueadero))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_facturadoPor)
                                    .addComponent(lbl_convenio)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_tarifa))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(btn_imprimirFactura)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_editar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_eliminar))
                            .addComponent(btn_cancelar))))
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
                    .addComponent(lbl_placa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lbl_propietario))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lbl_tipoVehiculo))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lbl_noParqueadero))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lbl_facturadoPor))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_convenio)
                    .addComponent(jLabel12))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_tarifa)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lbl_horaIngreso))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lbl_horaSalida))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lbl_totalAPagar)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lbl_efectivo)
                    .addComponent(jLabel16))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lbl_dineroCambio))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_imprimirFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_imprimirFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirFacturaActionPerformed
        String placa = lbl_placa.getText();
        generarDuplicadoFactura(placa);       
    }//GEN-LAST:event_btn_imprimirFacturaActionPerformed

    private void btn_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editarActionPerformed
        dispose();
        new EditarFacturaFinal().setVisible(true);
    }//GEN-LAST:event_btn_editarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        
        int decision = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea eliminar?", "Eliminar factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if(decision == JOptionPane.YES_OPTION){    
            
            verificarSifacturaFueContabilizada();
            if(facturaCont == true){
                descontarFacturaDelCierre();
                borrarFactura();
            }else if(facturaCont == false){
                borrarFactura();
            }
        }
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        GestionarFacturas.hayFacturaAbierta = false;
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
            java.util.logging.Logger.getLogger(InformacionFacturaFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InformacionFacturaFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InformacionFacturaFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InformacionFacturaFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                    new InformacionFacturaFinal().setVisible(true);
                }catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    //Logger.getLogger(InformacionFacturaFinal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_editar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_imprimirFactura;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public static javax.swing.JLabel lbl_codigo;
    public static javax.swing.JLabel lbl_convenio;
    public static javax.swing.JLabel lbl_dineroCambio;
    private javax.swing.JLabel lbl_efectivo;
    public static javax.swing.JLabel lbl_facturadoPor;
    public static javax.swing.JLabel lbl_horaIngreso;
    public static javax.swing.JLabel lbl_horaSalida;
    public static javax.swing.JLabel lbl_noParqueadero;
    public static javax.swing.JLabel lbl_placa;
    public static javax.swing.JLabel lbl_propietario;
    public static javax.swing.JLabel lbl_tarifa;
    public static javax.swing.JLabel lbl_tipoVehiculo;
    public static javax.swing.JLabel lbl_totalAPagar;
    // End of variables declaration//GEN-END:variables

    
    //Metodo que imprime el ticket de salida
    public void generarDuplicadoFactura(String placa_tick){
        
         try{
            Connection cn3 = Conexion.conectar();

            Map parametro = new HashMap();
            parametro.clear();
            parametro.put("placa", placa_tick);
            
            JasperReport reporte = null;
            //String path = "src\\Reportes\\DuplicadoFacturaFinal.jasper";

            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/DuplicadoFacturaFinal.jasper"));

            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, cn3);

            //Da una vista previa del ticket
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            view.setIconImage(obtenerIconoReporte());
            view.setTitle("Duplicado de factura");
            
        }catch(JRException ex){
            //Logger.getLogger(InformacionFacturaFinal.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "¡¡ERROR al generar duplicado de Factura, contacte al administrador!!");
        }
    }
    
    //Metodo que averigua si la factura indicada fue contabilizada
    public boolean verificarSifacturaFueContabilizada(){
        
        try {
            Connection cn3 = Conexion.conectar();
            PreparedStatement pst3 = cn3.prepareStatement(
                "SELECT Contabilizada from facturas where Codigo='"+ factura_actualizada + "' AND Estado_fctra = 'Cerrada' AND Contabilizada='Si'");

            ResultSet rs3 = pst3.executeQuery();

            if(rs3.next()){
                facturaCont = true;
               
            }else{
                facturaCont = false;
            }
            cn3.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al validar contabilidad de factura!!, contacte al administrador.");
        }
        return facturaCont;
    }

    //Metodo que borra la factura
    public void borrarFactura(){

        //Eliminamos la factura
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
    
    //Metodo que descuenta la factura del cierre donde esta involucrada
    public void descontarFacturaDelCierre(){
        
        //Averiguamos el id del cierre al cual está registrado la factura
        try {
            Connection cn4 = Conexion.conectar();
            PreparedStatement pst4 = cn4.prepareStatement(
                "SELECT Id_cierre, Valor_a_pagar from facturas where Codigo='"+ factura_actualizada + "'");

            ResultSet rs4 = pst4.executeQuery();

            if(rs4.next()){
                int cierreImplicado = rs4.getInt("Id_cierre");
                int valorAPagarFactura = rs4.getInt("Valor_a_pagar");
               
                //Traemos los datos del cierre implicado(codigo, producido, dinero en caja y no de facturas)
                try {
                    Connection cn5 = Conexion.conectar();
                    PreparedStatement pst5 = cn5.prepareStatement(
                        "SELECT Codigo, Producido, Dinero_en_caja, No_facturas from cierres where Id_cierre='"+ cierreImplicado + "'");

                    ResultSet rs5 = pst5.executeQuery();

                    if(rs5.next()){
                        int codCierreImpl = rs5.getInt("Codigo");
                        int producidoImpl = rs5.getInt("Producido");
                        int dinCaja_Impl = rs5.getInt("Dinero_en_caja");
                        int noFacturas_impl = rs5.getInt("No_facturas");
                        
                        //Hacemos el calculo correspondiente (restamos el valor de la factura al producido del cierre y restamos 1a factura al total de facturas)
                        int nuevoProducido = producidoImpl - valorAPagarFactura;
                        int nuevaDiferencia = nuevoProducido - dinCaja_Impl;
                        int nuevaCantFacturas = noFacturas_impl - 1;
                        
                        //Actualizamos el cierre implicado con los nuevos datos
                        try{
                            Connection cn6 = Conexion.conectar();
                            PreparedStatement pst6 = cn6.prepareStatement("update cierres set Producido='"+nuevoProducido+"',Diferencia ='"+nuevaDiferencia+"',No_facturas='"+nuevaCantFacturas+"'where Id_cierre ='"+cierreImplicado+"'");

                            pst6.executeUpdate();
                            cn6.close();
                            
                            int infoAcercaCierre = JOptionPane.showConfirmDialog(this, "El cierre de codigo "+codCierreImpl+" fue actualizado satisfactoriamente, ¿desea un duplicado?" , "Duplicado cierre", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            
                            if(infoAcercaCierre == JOptionPane.YES_OPTION){
                                generarDuplicadoCierre(nuevoProducido);
                            
                            }else if(infoAcercaCierre == JOptionPane.NO_OPTION){
                                this.dispose();
                            }
                            
                        }catch(SQLException e){
                            JOptionPane.showMessageDialog(null, "¡¡ERROR al actualizar!!, contacte al administrador.");
                        }
                    }
                    cn5.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "¡¡ERROR al validar datos de cierre!!, contacte al administrador.");
                }
            }
            cn4.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar!!, contacte al administrador.");
        }   
    }
    
    public void generarDuplicadoCierre(int producidoEsp){
        
        try{
            Connection cn3 = Conexion.conectar();

            Map parametro = new HashMap();
            parametro.clear();
            parametro.put("producido", producidoEsp);
            
            JasperReport reporte = null;
            String path = "src\\Reportes\\DuplicadoCierreDeCaja.jasper";

            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);

            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, cn3);

            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            view.setIconImage(obtenerIconoReporte());
            view.setTitle("Duplicado de factura");

        }catch(JRException ex){
            //Logger.getLogger(PanelUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "¡¡ERROR al generar Duplicado de cierre, contacte al administrador!!");
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
            JOptionPane.showMessageDialog(null, "¡¡ERROR al validar!!, contacte al administrador.");
        }
    }
    
    public Image obtenerIconoReporte() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagenes/bill_icon.png"));
        return retValue;
    }
}
