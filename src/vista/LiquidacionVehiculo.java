package vista;

import clasesDeApoyo.Conexion;
import com.sun.glass.events.KeyEvent;
import controlador.ConvenioControlador;
import controlador.FacturaControlador;
import controlador.ParqueaderoControlador;
import controlador.TarifaControlador;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import modelo.Factura;
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
    DefaultTableModel modeloCaja;
    int Fila;
    public static String fecha_movVehiculo;
    
    Factura facturaALiquidar = new Factura (0, "", "", "", "", "", 0, "", "", "", 0, 0, "", 0);
    FacturaControlador facturaControla = new FacturaControlador();
    ParqueaderoControlador parqControla = new ParqueaderoControlador();
    TarifaControlador tarifaControla = new TarifaControlador();
    ConvenioControlador convControla = new ConvenioControlador();
    
    
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
        modeloCaja = PanelCaja.modeloCaja;
        
        lbl_diferencia1.setVisible(false);
        lbl_tipoDiferencia1.setVisible(false);
        lbl_conectorY.setVisible(false);
        lbl_diferencia2.setVisible(false);
        lbl_tipoDiferencia2.setVisible(false);
        lbl_totalAPagar.setVisible(false);
        lbl_dineroCambio.setVisible(false);
       
        setSize(488, 480);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Liquidación de vehiculo");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        Fila = tablaOperacionParqueadero.getSelectedRow();
        
        if(parqueadero_actualizado!=null){
        
            facturaALiquidar = facturaControla.consultarInformacionDeUnaFacturaAbierta(parqueadero_actualizado);
            
            //Cargamos la información en el frame
            ID = facturaALiquidar.getId();
            lbl_codigo.setText(facturaALiquidar.getCodigo());
            lbl_placa.setText(facturaALiquidar.getPlaca());
            lbl_propietario.setText(facturaALiquidar.getPropietario());
            lbl_tipoVehiculo.setText(facturaALiquidar.getClaseDeVehiculo());
            lbl_noParqueadero.setText(parqControla.consultarNombreDeParqueaderoMedianteID(facturaALiquidar.getId_parqueadero()));
            lbl_facturadoPor.setText(facturaALiquidar.getFacturadoPor());
            lbl_horaIngreso.setText(facturaALiquidar.getFechaDeIngresoVehiculo());
            lbl_convenio.setText(convControla.consultarNombreDeConvenioMedianteID(facturaALiquidar.getId_convenio()));
            lbl_tarifa.setText(tarifaControla.consultarNombreDeTarifaMedianteID(facturaALiquidar.getId_tarifa()));
              
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
        jLabel14 = new javax.swing.JLabel();
        lbl_diferencia1 = new javax.swing.JLabel();
        lbl_tipoDiferencia1 = new javax.swing.JLabel();
        lbl_conectorY = new javax.swing.JLabel();
        lbl_diferencia2 = new javax.swing.JLabel();
        lbl_tipoDiferencia2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconImage(getIconImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        btn_imprimirFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/print_15107.png"))); // NOI18N
        btn_imprimirFactura.setText("Imprimir Comprobante");
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

        txt_dineroRecibido.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_dineroRecibidoFocusLost(evt);
            }
        });
        txt_dineroRecibido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dineroRecibidoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_dineroRecibidoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dineroRecibidoKeyTyped(evt);
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

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Tiempo total:");

        lbl_diferencia1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_diferencia1.setForeground(new java.awt.Color(0, 0, 255));
        lbl_diferencia1.setText("numero1");

        lbl_tipoDiferencia1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_tipoDiferencia1.setForeground(new java.awt.Color(0, 0, 255));
        lbl_tipoDiferencia1.setText("nombreFrecuencia1");

        lbl_conectorY.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_conectorY.setForeground(new java.awt.Color(0, 0, 255));
        lbl_conectorY.setText("y");

        lbl_diferencia2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_diferencia2.setForeground(new java.awt.Color(0, 0, 255));
        lbl_diferencia2.setText("numero2");

        lbl_tipoDiferencia2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_tipoDiferencia2.setForeground(new java.awt.Color(0, 0, 255));
        lbl_tipoDiferencia2.setText("nombreFrecuencia2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_codigo)
                    .addComponent(lbl_placa)
                    .addComponent(lbl_propietario)
                    .addComponent(lbl_tipoVehiculo)
                    .addComponent(lbl_noParqueadero, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_facturadoPor)
                    .addComponent(lbl_convenio)
                    .addComponent(lbl_tarifa)
                    .addComponent(lbl_horaIngreso)
                    .addComponent(lbl_horaSalida)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_diferencia1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_tipoDiferencia1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_conectorY)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_diferencia2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_tipoDiferencia2))
                    .addComponent(lbl_totalAPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_imprimirFactura)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_dineroRecibido, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_dineroCambio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_calcular, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbl_codigo))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lbl_placa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_propietario)
                    .addComponent(jLabel10))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lbl_tipoVehiculo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lbl_noParqueadero))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lbl_facturadoPor))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lbl_convenio))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lbl_tarifa))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lbl_horaIngreso))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lbl_horaSalida))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(lbl_diferencia1)
                    .addComponent(lbl_tipoDiferencia1)
                    .addComponent(lbl_conectorY)
                    .addComponent(lbl_diferencia2)
                    .addComponent(lbl_tipoDiferencia2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lbl_totalAPagar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8)
                    .addComponent(txt_dineroRecibido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_calcular, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lbl_dineroCambio))
                .addGap(26, 26, 26)
                .addComponent(btn_imprimirFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
 
    }//GEN-LAST:event_txt_dineroRecibidoKeyPressed

    private void txt_dineroRecibidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dineroRecibidoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dineroRecibidoKeyReleased

    private void txt_dineroRecibidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dineroRecibidoKeyTyped
        //Evalua que se digiten numeros no letras
        char validar = evt.getKeyChar();
        
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            
            JOptionPane.showMessageDialog(rootPane, "Ingrese solo numeros.");
        }
    }//GEN-LAST:event_txt_dineroRecibidoKeyTyped

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        cerrarLiquidacionVehiculo();
    }//GEN-LAST:event_formWindowClosing

    private void txt_dineroRecibidoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_dineroRecibidoFocusLost
       calcularVueltas();
    }//GEN-LAST:event_txt_dineroRecibidoFocusLost

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
    private javax.swing.JButton btn_imprimirFactura;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public static javax.swing.JLabel lbl_codigo;
    private javax.swing.JLabel lbl_conectorY;
    public static javax.swing.JLabel lbl_convenio;
    private javax.swing.JLabel lbl_diferencia1;
    private javax.swing.JLabel lbl_diferencia2;
    public static javax.swing.JLabel lbl_dineroCambio;
    public static javax.swing.JLabel lbl_facturadoPor;
    public static javax.swing.JLabel lbl_horaIngreso;
    public static javax.swing.JLabel lbl_horaSalida;
    public static javax.swing.JLabel lbl_noParqueadero;
    public static javax.swing.JLabel lbl_placa;
    public static javax.swing.JLabel lbl_propietario;
    public static javax.swing.JLabel lbl_tarifa;
    private javax.swing.JLabel lbl_tipoDiferencia1;
    private javax.swing.JLabel lbl_tipoDiferencia2;
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
            
//            //Hace la consulta de la tarifa asignada al vehiculo
//            try {
//                Connection cn2 = Conexion.conectar();
//                PreparedStatement pst2 = cn2.prepareStatement(
//                    "SELECT Monto from tarifas where Nombre_tarifa ='" + variableTarifa + "'");
//                ResultSet rs2 = pst2.executeQuery();
//
//                if(rs2.next()){
//                    String monto = rs2.getString("Monto");
//                    montoDelaTarifa = Double.parseDouble(monto);
//                }
//                cn2.close();
//            } catch (SQLException e) {
//               JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar tarifa de vehiculo!!, contacte al administrador.");
//            }
//            
//            try{
//                //Calculamos el valor a pagar por el vehiculo
//                double valorAPagar = 0.0;
//                long valorPagarDefinitivo;
//                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Calendar cal  = Calendar.getInstance();
//                Date date = cal.getTime();
//                String fechaHora = dateFormat.format(date);
//
//                //Obtenemos la hora de ingreso y la convertimos a Date
//                String hora_entradaVehiculo = lbl_horaIngreso.getText();
//
//                hora_ingr = dateFormat.parse(hora_entradaVehiculo);
//
//                //Calculamos la diferencia de tiempos (tiempo de ingreso vs tiempo de salida)
//                int minutosACobrar = (int) (date.getTime()-hora_ingr.getTime())/60000;
//
//                //Aplicamos la tarifa al tiempo estimado
//                valorAPagar = minutosACobrar * montoDelaTarifa;
//                
//                
//                //Redondeamos el valor a pagar
//                valorPagarDefinitivo = Math.round(valorAPagar);
//                
//                String valor_Pagar = Long.toString(valorPagarDefinitivo);
//                lbl_totalAPagar.setText(valor_Pagar);
//                    
//                    
//            }catch (ParseException ex) {  
//                //Logger.getLogger(LiquidacionVehiculo.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
        }else if(!variableConvenio.equals("NINGUNO") && !variableTarifa.equals("NINGUNA")){
            //En este caso, asi tenga un convenio aignado, si la tarifa no es ninguna, predominará la tarifa
            //Hace la consulta de la tarifa asignada al vehiculo
             //Hace la consulta de la tarifa asignada al vehiculo
//            try {
//                Connection cn2 = Conexion.conectar();
//                PreparedStatement pst2 = cn2.prepareStatement(
//                    "SELECT Monto from tarifas where Nombre_tarifa ='" + variableTarifa + "'");
//                ResultSet rs2 = pst2.executeQuery();
//
//                if(rs2.next()){
//                    String monto = rs2.getString("Monto");
//                    montoDelaTarifa = Integer.parseInt(monto);
//                }
//                cn2.close();
//            } catch (SQLException e) {
//                JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar tarifa de vehiculo!!, contacte al administrador.");
//            }
//            
//            try{
//                //Calculamos el valor a pagar por el vehiculo
//                double valorAPagar = 0.0;
//                long valorPagarDefinitivo;
//                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Calendar cal  = Calendar.getInstance();
//                Date date = cal.getTime();
//                String fechaHora = dateFormat.format(date);
//
//                //Obtenemos la hora de ingreso y la convertimos a Date
//                String hora_entradaVehiculo = lbl_horaIngreso.getText();
//
//                hora_ingr = dateFormat.parse(hora_entradaVehiculo);
//
//                //Calculamos la diferencia de tiempos (tiempo de ingreso vs tiempo de salida)
//                int minutosACobrar = (int) (date.getTime()-hora_ingr.getTime())/60000;
//
//                //Aplicamos la tarifa al tiempo estimado
//                valorAPagar = minutosACobrar * montoDelaTarifa;
//                System.out.println("Valor original: " + valorAPagar);
//                
//                //Redondeamos el valor a pagar
//                valorPagarDefinitivo = Math.round(valorAPagar);
//                
//                String valor_Pagar = Long.toString(valorPagarDefinitivo);
//                lbl_totalAPagar.setText(valor_Pagar);
//                    
//                    
//            }catch (ParseException ex) {  
//                //Logger.getLogger(LiquidacionVehiculo.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
        
    }
    
  
    
    
//    
//    //Metodo que me libera el parqueadero
//    public void liberarParqueadero(String placa){
//        
//        //Verifica si el vehiculo esta registrado en el sistema
//        try {
//            Connection cn = Conexion.conectar();
//            PreparedStatement pst;
//            pst = cn.prepareStatement(
//                        "select Placa from vehiculos where Placa = '" + placa + "'");
//            
//            ResultSet rs = pst.executeQuery();
//            
//            if (rs.next()) {
//                
//               //Actualizamos el estado del parqueadero seleccionado de Ocupado a Disponible
//                try{
//                    Connection cn3 = Conexion.conectar();
//                    PreparedStatement pst3 = cn3.prepareStatement("update parqueaderos set Esta_en_parqueadero='No' where Placa='"+placa+"'");
//
//                    pst3.executeUpdate();
//                    cn3.close();
//
//                }catch(SQLException e){
//                    JOptionPane.showMessageDialog(null, "¡¡ERROR al actualizar parqueadero!!, contacte al administrador.");
//                } 
//                
//            } else { 
//                //Aqui va el procedimiento si el vehiculo no esta registrado y se va a liberar el parqueadero
//                //Actualizamos el estado del parqueadero seleccionado de Ocupado a Disponible para vehiculo desconocido
//                try{
//                    Connection cn6 = Conexion.conectar();
//                    PreparedStatement pst6 = cn6.prepareStatement("update parqueaderos set Estado='Disponible', Placa='', Propietario='',  Esta_en_parqueadero='' where Placa='"+placa+"'");
//
//                    pst6.executeUpdate();
//                    cn6.close();
//
//                }catch(SQLException e){
//                    JOptionPane.showMessageDialog(null, "¡¡ERROR al actualizar parqueadero vno registrado!!, contacte al administrador.");
//                }
//            }
//        }catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "¡¡ERROR al consultar placa en memoria!!, contacte al administrador.");
//        }
//    }
    
    //Metodo que se invoca al cerrar el jFrame
    private void cerrarLiquidacionVehiculo(){
        
        String botones[] = {"Cerrar", "Cancelar"};
        int eleccion = JOptionPane.showOptionDialog(this, "¿Está seguro que desea cerrar?", "Liquidar vehiculo", 0, 3, null, botones, this);
        
        if(eleccion == JOptionPane.YES_OPTION){
            PanelCaja.hayVehiculoLiquidandose = false;
            dispose();
        }
    }
}
