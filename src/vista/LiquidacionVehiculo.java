package vista;

import clasesDeApoyo.Conexion;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Logger;

/**
 *
 * @author ALEJO
 */
public class LiquidacionVehiculo extends javax.swing.JFrame {

    String usuario;
    String parqueadero_actualizado;
    public static int ID;
    javax.swing.JTable tablaOperacionParqueadero;
    DefaultTableModel modelo;
    int Fila;
    public static String fecha_movVehiculo;
    
    public static Date hora_ingr;
    public static double montoDelaTarifa;
    
    private final Logger log = Logger.getLogger(LiquidacionVehiculo.class);
    private URL url = LiquidacionVehiculo.class.getResource("Log4j.properties");
          
    /**
     * Creates new form LiquidacionVehiculo
     */
    public LiquidacionVehiculo() {
        initComponents();
        usuario = Login.usuario;
        parqueadero_actualizado = PanelCaja.parqueadero_update;
        tablaOperacionParqueadero = PanelCaja.table_operacionParqueadero;
        modelo = PanelCaja.modelo;
       
        
        setSize(375,562);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Liquidación de vehiculo");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        Fila = tablaOperacionParqueadero.getSelectedRow();
        
        if(parqueadero_actualizado!=null){
        
            //Hace la consulta del codigo,tipo de vehiculo , Facturado por, a la base de datos
            try {
                Connection cn = Conexion.conectar();
                PreparedStatement pst = cn.prepareStatement(
                    "SELECT Fac.Id_factura, Fac.Codigo, Fac.Placa, Fac.Propietario, Fac.Tipo_vehiculo, Fac.Facturado_por, Fac.Estado_fctra, Fac.Hora_ingreso, Parq.Nombre_parqueadero, Conv.Nombre_convenio, Tar.Nombre_tarifa from facturas Fac INNER JOIN parqueaderos Parq ON Fac.No_parqueadero = Parq.Id_parqueadero INNER JOIN convenios Conv ON Fac.Id_convenio = Conv.Id_convenio INNER JOIN tarifas Tar ON Fac.Id_tarifa = Tar.Id_tarifa AND Fac.Placa = '" + parqueadero_actualizado + "' AND Fac.Estado_fctra = 'Abierta'");

                ResultSet rs = pst.executeQuery();

                if(rs.next()){
                    ID = rs.getInt("Fac.Id_factura");
                    lbl_codigo.setText(rs.getString("Fac.Codigo"));
                    lbl_placa.setText(rs.getString("Fac.Placa"));
                    lbl_propietario.setText(rs.getString("Fac.Propietario"));
                    lbl_tipoVehiculo.setText(rs.getString("Fac.Tipo_vehiculo"));
                    lbl_noParqueadero.setText(rs.getString("Parq.Nombre_parqueadero"));
                    lbl_facturadoPor.setText(rs.getString("Fac.Facturado_por"));
                    lbl_horaIngreso.setText(rs.getString("Fac.Hora_ingreso"));
                    lbl_convenio.setText(rs.getString("Conv.Nombre_convenio"));
                    lbl_tarifa.setText(rs.getString("Tar.Nombre_tarifa"));
                }  
                cn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar liquidación de vehiculo!!, contacte al administrador.");
            }
                  
            lbl_horaSalida.setText(fecha_Salidavehiculo());

            String tarifa = lbl_tarifa.getText();
            String convenio = lbl_convenio.getText();
            calcularTarifa(convenio, tarifa);
                   
        }
   }
    
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/Caja.png"));
        return retValue;
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_imprimirFactura = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_dineroRecibido = new javax.swing.JTextField();
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
        btn_calcular = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        lbl_tarifa = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());

        btn_imprimirFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/print_15107.png"))); // NOI18N
        btn_imprimirFactura.setText("Imprimir Comprobante");
        btn_imprimirFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimirFacturaActionPerformed(evt);
            }
        });

        btn_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Cancelar.png"))); // NOI18N
        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
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

        txt_dineroRecibido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dineroRecibidoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_dineroRecibidoKeyReleased(evt);
            }
        });

        lbl_codigo.setText("codigo_factura");

        lbl_placa.setText("placa_vehiculo");

        lbl_tipoVehiculo.setText("tipo_vehiculo");

        lbl_horaIngreso.setText("hora_entrada");

        lbl_horaSalida.setText("hora_salida");

        lbl_noParqueadero.setText("numero_parqueadero");

        lbl_totalAPagar.setText("valor_a_pagar");

        lbl_dineroCambio.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
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

        btn_calcular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/calculator-icon_34473.png"))); // NOI18N
        btn_calcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_calcularActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Tarifa:");

        lbl_tarifa.setText("tarifaDelVehiculo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_totalAPagar)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txt_dineroRecibido, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_calcular, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_dineroCambio))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(btn_imprimirFactura)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
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
                    .addComponent(lbl_totalAPagar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8)
                    .addComponent(txt_dineroRecibido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_calcular, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lbl_dineroCambio))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_imprimirFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(100, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Metodo del boton cancelar
    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        PanelCaja.hayVehiculoLiquidandose = false;
        dispose();
    }//GEN-LAST:event_btn_cancelarActionPerformed

    //Metodo del boton calcular
    private void btn_calcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_calcularActionPerformed
        calcularVueltas();    
    }//GEN-LAST:event_btn_calcularActionPerformed

    private void btn_imprimirFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirFacturaActionPerformed
        
        String horaSalida = lbl_horaSalida.getText();
        String placa = lbl_placa.getText();
        String valor_a_pagar = lbl_totalAPagar.getText();
        String efectivo = txt_dineroRecibido.getText();
        String cambio = lbl_dineroCambio.getText();
        
        if(efectivo.equals("")){
            txt_dineroRecibido.setBackground(Color.red);
            JOptionPane.showMessageDialog(null, "Digite el efectivo recibido para hacer el calculo correspondiente.");
            txt_dineroRecibido.setBackground(Color.white);
        }else{
            try{
                int valor_pagar_int = Integer.parseInt(valor_a_pagar);
                int efectivo_int = Integer.parseInt(efectivo);
                int cambio_int = Integer.parseInt(cambio);

                Connection cn = Conexion.conectar();
                PreparedStatement pst = cn.prepareStatement("update facturas set Hora_salida ='"+horaSalida+"',Valor_a_pagar='"+valor_pagar_int+"',Efectivo='"+efectivo_int+"',Cambio='"+cambio_int+"'where Placa ='"+placa+"' AND Estado_fctra = 'Abierta'");

                pst.executeUpdate();
                cn.close();

                JOptionPane.showMessageDialog(null, "Vehiculo liquidado satisfactoriamente");
                dispose();
                liberarParqueadero(placa);
                generarTicketSalida(placa);
                cerrarFactura(placa);
                PanelCaja.hayVehiculoLiquidandose = false;
                               

            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "¡¡ERROR al imprimir comprobante!!, contacte al administrador.");
            }
        }
        
             
    }//GEN-LAST:event_btn_imprimirFacturaActionPerformed

    private void txt_dineroRecibidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dineroRecibidoKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            calcularVueltas();
        }
        
    }//GEN-LAST:event_txt_dineroRecibidoKeyPressed

    private void txt_dineroRecibidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dineroRecibidoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dineroRecibidoKeyReleased

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
            java.util.logging.Logger.getLogger(LiquidacionVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LiquidacionVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LiquidacionVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LiquidacionVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    //Esto cambia la apariencia de la app para que se acomode al Siste Operativo
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    new LiquidacionVehiculo().setVisible(true);
                }catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    //Logger.getLogger(LiquidacionVehiculo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btn_calcular;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_imprimirFactura;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    public static javax.swing.JLabel lbl_facturadoPor;
    public static javax.swing.JLabel lbl_horaIngreso;
    public static javax.swing.JLabel lbl_horaSalida;
    public static javax.swing.JLabel lbl_noParqueadero;
    public static javax.swing.JLabel lbl_placa;
    public static javax.swing.JLabel lbl_propietario;
    public static javax.swing.JLabel lbl_tarifa;
    public static javax.swing.JLabel lbl_tipoVehiculo;
    public static javax.swing.JLabel lbl_totalAPagar;
    public static javax.swing.JTextField txt_dineroRecibido;
    // End of variables declaration//GEN-END:variables

    //Metodo que genera la fecha de salida del vehiculo       
    public static String fecha_Salidavehiculo(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        fecha_movVehiculo = dateFormat.format(date);
        return fecha_movVehiculo;
    }

    //Metodo que calcula la tarifa a pagar de parqueadero
    public static void calcularTarifa(String variableConvenio, String variableTarifa){
        
        if(variableConvenio.equals("NINGUNO") && variableTarifa.equals("NINGUNA")){
            lbl_totalAPagar.setText("0");
            txt_dineroRecibido.setEnabled(false);
            txt_dineroRecibido.setText("0");
            btn_calcular.setEnabled(false);
            lbl_dineroCambio.setText("0");
        }else if(!variableConvenio.equals("NINGUNO") && variableTarifa.equals("NINGUNA")){
            lbl_totalAPagar.setText("0");
            txt_dineroRecibido.setEnabled(false);
            txt_dineroRecibido.setText("0");
            btn_calcular.setEnabled(false);
            lbl_dineroCambio.setText("0");
        }else if(variableConvenio.equals("NINGUNO") && !variableTarifa.equals("NINGUNA")){
            
            //Hace la consulta de la tarifa asignada al vehiculo
            try {
                Connection cn2 = Conexion.conectar();
                PreparedStatement pst2 = cn2.prepareStatement(
                    "SELECT Monto from tarifas where Nombre_tarifa ='" + variableTarifa + "'");
                ResultSet rs2 = pst2.executeQuery();

                if(rs2.next()){
                    String monto = rs2.getString("Monto");
                    montoDelaTarifa = Double.parseDouble(monto);
                }
                cn2.close();
            } catch (SQLException e) {
               JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar tarifa de vehiculo!!, contacte al administrador.");
            }
            
            try{
                //Calculamos el valor a pagar por el vehiculo
                double valorAPagar = 0.0;
                long valorPagarDefinitivo;
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar cal  = Calendar.getInstance();
                Date date = cal.getTime();
                String fechaHora = dateFormat.format(date);

                //Obtenemos la hora de ingreso y la convertimos a Date
                String hora_entradaVehiculo = lbl_horaIngreso.getText();

                hora_ingr = dateFormat.parse(hora_entradaVehiculo);

                //Calculamos la diferencia de tiempos (tiempo de ingreso vs tiempo de salida)
                int minutosACobrar = (int) (date.getTime()-hora_ingr.getTime())/60000;

                //Aplicamos la tarifa al tiempo estimado
                valorAPagar = minutosACobrar * montoDelaTarifa;
                
                
                //Redondeamos el valor a pagar
                valorPagarDefinitivo = Math.round(valorAPagar);
                
                String valor_Pagar = Long.toString(valorPagarDefinitivo);
                lbl_totalAPagar.setText(valor_Pagar);
                    
                    
            }catch (ParseException ex) {  
                //Logger.getLogger(LiquidacionVehiculo.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else if(!variableConvenio.equals("NINGUNO") && !variableTarifa.equals("NINGUNA")){
            //En este caso, asi tenga un convenio aignado, si la tarifa no es ninguna, predominará la tarifa
            //Hace la consulta de la tarifa asignada al vehiculo
             //Hace la consulta de la tarifa asignada al vehiculo
            try {
                Connection cn2 = Conexion.conectar();
                PreparedStatement pst2 = cn2.prepareStatement(
                    "SELECT Monto from tarifas where Nombre_tarifa ='" + variableTarifa + "'");
                ResultSet rs2 = pst2.executeQuery();

                if(rs2.next()){
                    String monto = rs2.getString("Monto");
                    montoDelaTarifa = Integer.parseInt(monto);
                }
                cn2.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar tarifa de vehiculo!!, contacte al administrador.");
            }
            
            try{
                //Calculamos el valor a pagar por el vehiculo
                double valorAPagar = 0.0;
                long valorPagarDefinitivo;
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar cal  = Calendar.getInstance();
                Date date = cal.getTime();
                String fechaHora = dateFormat.format(date);

                //Obtenemos la hora de ingreso y la convertimos a Date
                String hora_entradaVehiculo = lbl_horaIngreso.getText();

                hora_ingr = dateFormat.parse(hora_entradaVehiculo);

                //Calculamos la diferencia de tiempos (tiempo de ingreso vs tiempo de salida)
                int minutosACobrar = (int) (date.getTime()-hora_ingr.getTime())/60000;

                //Aplicamos la tarifa al tiempo estimado
                valorAPagar = minutosACobrar * montoDelaTarifa;
                System.out.println("Valor original: " + valorAPagar);
                
                //Redondeamos el valor a pagar
                valorPagarDefinitivo = Math.round(valorAPagar);
                
                String valor_Pagar = Long.toString(valorPagarDefinitivo);
                lbl_totalAPagar.setText(valor_Pagar);
                    
                    
            }catch (ParseException ex) {  
                //Logger.getLogger(LiquidacionVehiculo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    //Metodo que imprime el ticket de salida
    public void generarTicketSalida(String placa_tick){
        
         try{
            Connection cn3 = Conexion.conectar();

            Map parametro = new HashMap();
            parametro.clear();
            parametro.put("placa", placa_tick);
            
            JasperReport reporte = null;
            //String path = "src\\Reportes\\TicketSalida.jasper";

            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/TicketSalida.jasper"));

            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, cn3);
            
            //Da una vista previa del ticket
            /*
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setTitle("Ticket de salida vehiculo " + placa_tick);
            view.setVisible(true);
            */
            
            //Hace que se imprima directamente
            JasperPrintManager.printReport(jprint, false);

        }catch(JRException ex){
            //Logger.getLogger(PanelUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "¡¡ERROR al generar Ticket de Salida, revise la conexión de la impresora o contacte al administrador!!");
        }
        
    }
    
    //Metodo que cierra la factura
    public void cerrarFactura(String placa){
        try{
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("update facturas set Estado_fctra = 'Cerrada' where Placa ='"+placa+"' AND Estado_fctra = 'Abierta'");

            pst.executeUpdate();
            cn.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al actualizar factura!!, contacte al administrador.");
        } 
    }
    
    //Metodo que calcula las vueltas que hay que darle al cliente
    public void calcularVueltas(){
        
        String efectivo = txt_dineroRecibido.getText();
        if(efectivo.equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el dinero recibido para calcular.");
        }else{
            String cuentaAPagar = lbl_totalAPagar.getText();
            
            int efectivo_int = Integer.parseInt(efectivo); 
            int cuentaPagar_int = Integer.parseInt(cuentaAPagar);
            int cambio = efectivo_int - cuentaPagar_int;
            
            if(cambio >= 0){
                String cambio_str = String.valueOf(cambio);
                lbl_dineroCambio.setForeground(Color.green);
                lbl_dineroCambio.setText(cambio_str);
            }else if(cambio < 0){
                String cambio_str = String.valueOf(cambio);
                lbl_dineroCambio.setForeground(Color.red);
                lbl_dineroCambio.setText(cambio_str);
            }
        }
        
    }
    
    //Metodo que me libera el parqueadero
    public void liberarParqueadero(String placa){
        
        //Verifica si el vehiculo esta registrado en el sistema
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst;
            pst = cn.prepareStatement(
                        "select Placa from vehiculos where Placa = '" + placa + "'");
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                
               //Actualizamos el estado del parqueadero seleccionado de Ocupado a Disponible
                try{
                    Connection cn3 = Conexion.conectar();
                    PreparedStatement pst3 = cn3.prepareStatement("update parqueaderos set Esta_en_parqueadero='No' where Placa='"+placa+"'");

                    pst3.executeUpdate();
                    cn3.close();

                }catch(SQLException e){
                    JOptionPane.showMessageDialog(null, "¡¡ERROR al actualizar parqueadero!!, contacte al administrador.");
                } 
                
            } else { 
                //Aqui va el procedimiento si el vehiculo no esta registrado y se va a liberar el parqueadero
                //Actualizamos el estado del parqueadero seleccionado de Ocupado a Disponible para vehiculo desconocido
                try{
                    Connection cn6 = Conexion.conectar();
                    PreparedStatement pst6 = cn6.prepareStatement("update parqueaderos set Estado='Disponible', Placa='', Propietario='',  Esta_en_parqueadero='' where Placa='"+placa+"'");

                    pst6.executeUpdate();
                    cn6.close();

                }catch(SQLException e){
                    JOptionPane.showMessageDialog(null, "¡¡ERROR al actualizar parqueadero vno registrado!!, contacte al administrador.");
                }
            }
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al consultar placa en memoria!!, contacte al administrador.");
        }
    }
}
