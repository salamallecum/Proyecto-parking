package vista;

import controlador.ConvenioControlador;
import controlador.FacturaControlador;
import controlador.ParametroControlador;
import controlador.ParqueaderoControlador;
import controlador.TarifaControlador;
import controlador.UsuarioControlador;
import controlador.VehiculoControlador;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import modelo.Factura;
import modelo.Vehiculo;
import org.apache.log4j.Logger;

/**
 *
 * @author ALEJO
 */
public class InformacionFacturaIngreso extends javax.swing.JFrame {

    String usuario;
    String codigoFactura_actualizada;
    public static int ID;
    javax.swing.JTable tablaOperacionFacturas;
    DefaultTableModel modelo;
    int Fila;
    int idDelVehiculo;
    String placaDelVehiculo;
    String tipoIdentifVehiculo;
    String numIdentifVehiculo;
            
    Factura facturaAbiertaConsultada = new Factura(0, "", "", 0, "", "", "", "", "", 0, 0, "", "", 0, 0, "", 0, "", "", "", "", "", "");
    Vehiculo vehiculoConsultado = new Vehiculo(0, "", "", "", "", "", "", "", 0, 0, 0);
    FacturaControlador facturaControla = new FacturaControlador();
    ParqueaderoControlador parqControla = new ParqueaderoControlador();
    TarifaControlador tarifaControla = new TarifaControlador();
    ConvenioControlador convControla = new ConvenioControlador();
    VehiculoControlador vehiControlador = new VehiculoControlador(); 
    UsuarioControlador usuarioControla = new UsuarioControlador();
    ParametroControlador paramControla = new ParametroControlador();
    
    private final Logger log = Logger.getLogger(InformacionFacturaIngreso.class);
    private URL url = InformacionFacturaIngreso.class.getResource("Log4j.properties");
    
          
    /**
     * Creates new form LiquidacionVehiculo
     */
    public InformacionFacturaIngreso() {
        initComponents();
        usuario = Login.usuario;
        codigoFactura_actualizada = GestionarFacturas.codigoFactura_update;
        tablaOperacionFacturas = GestionarFacturas.table_listaFacturas;
        modelo = GestionarFacturas.modelo;
        lbl_numIdentificacion1.setVisible(false);
        lbl_numIdentificacion2.setVisible(false);
        
        setSize(420,400);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Información de factura");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        //Avisamos que esta ventana se encuentra abierta para que no deje cerrar sesion al usuario
        MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = true;
        
        Fila = tablaOperacionFacturas.getSelectedRow();
        
        //Cargamos la informacion de la factura abierta en el frame
        facturaAbiertaConsultada = facturaControla.consultarInformacionDeUnaFacturaAbiertaParaSuEdicion(codigoFactura_actualizada);
        ID = facturaAbiertaConsultada.getId();
        lbl_codigo.setText(facturaAbiertaConsultada.getCodigo());
        idDelVehiculo = facturaAbiertaConsultada.getIdDelVehiculo();
        placaDelVehiculo = facturaAbiertaConsultada.getPlaca();
        tipoIdentifVehiculo = facturaAbiertaConsultada.getTipoIdentificacion();
        numIdentifVehiculo = facturaAbiertaConsultada.getNumIdentificacion();
        
        //Evaluamos si la factura es de un carro/moto/bicicleta u otro
        if(idDelVehiculo != 0){
            vehiculoConsultado = vehiControlador.consultarInformacionDeUnVehiculo(null, idDelVehiculo, null, null, null);
            
            if(vehiculoConsultado.getTipoVehiculo().equals("AUTOMOVIL") || vehiculoConsultado.getTipoVehiculo().equals("MOTO")){
                lbl_placa2.setText(vehiculoConsultado.getPlaca());
                lbl_propietario.setText(vehiculoConsultado.getPropietario());
                lbl_tipoVehiculo.setText(vehiculoConsultado.getTipoVehiculo());
                lbl_noParqueadero.setText(parqControla.consultarNombreDeParqueaderoMedianteID(vehiculoConsultado.getId_parqueadero()));
                lbl_facturadoPor.setText(usuarioControla.consultarUsuarioMedianteID(facturaAbiertaConsultada.getFacturadoPor()));
                lbl_convenio.setText(convControla.consultarNombreDeConvenioMedianteID(vehiculoConsultado.getId_convenio()));
                lbl_tarifa.setText(tarifaControla.consultarNombreDeTarifaMedianteID(vehiculoConsultado.getId_tarifa()));
        
            }else if(vehiculoConsultado.getTipoVehiculo().equals("BICICLETA") || vehiculoConsultado.getTipoVehiculo().equals("PATINETA") || vehiculoConsultado.getTipoVehiculo().equals("OTRO")){
                lbl_placa1.setText("Tipo Identif:");
                lbl_placa2.setText(vehiculoConsultado.getTipoIdentificacion());
                lbl_numIdentificacion1.setVisible(true);
                lbl_numIdentificacion2.setText(vehiculoConsultado.getNumIdentificacion());
                lbl_numIdentificacion2.setVisible(true);
                lbl_propietario.setText(vehiculoConsultado.getPropietario());
                lbl_tipoVehiculo.setText(vehiculoConsultado.getTipoVehiculo());
                lbl_noParqueadero.setText(parqControla.consultarNombreDeParqueaderoMedianteID(vehiculoConsultado.getId_parqueadero()));
                lbl_facturadoPor.setText(usuarioControla.consultarUsuarioMedianteID(facturaAbiertaConsultada.getFacturadoPor()));
                lbl_convenio.setText(convControla.consultarNombreDeConvenioMedianteID(vehiculoConsultado.getId_convenio()));
                lbl_tarifa.setText(tarifaControla.consultarNombreDeTarifaMedianteID(vehiculoConsultado.getId_tarifa()));
            }
            
        }else if(placaDelVehiculo != null){
            lbl_placa2.setText(placaDelVehiculo);
            lbl_propietario.setText(facturaAbiertaConsultada.getPropietario());
            lbl_tipoVehiculo.setText(facturaAbiertaConsultada.getTipoDeVehiculo());
            lbl_noParqueadero.setText(parqControla.consultarNombreDeParqueaderoMedianteID(facturaAbiertaConsultada.getId_parqueadero()));
            lbl_facturadoPor.setText(usuarioControla.consultarUsuarioMedianteID(facturaAbiertaConsultada.getFacturadoPor()));
            lbl_convenio.setText(convControla.consultarNombreDeConvenioMedianteID(facturaAbiertaConsultada.getId_convenio()));
            lbl_tarifa.setText(tarifaControla.consultarNombreDeTarifaMedianteID(facturaAbiertaConsultada.getId_tarifa()));
            
        }else if(tipoIdentifVehiculo != null && numIdentifVehiculo != null){
            lbl_placa1.setText("Tipo Identif:");
            lbl_placa2.setText(tipoIdentifVehiculo);
            lbl_numIdentificacion1.setVisible(true);
            lbl_numIdentificacion2.setText(numIdentifVehiculo);
            lbl_numIdentificacion2.setVisible(true);
            lbl_propietario.setText(facturaAbiertaConsultada.getPropietario());
            lbl_tipoVehiculo.setText(facturaAbiertaConsultada.getTipoDeVehiculo());
            lbl_noParqueadero.setText(parqControla.consultarNombreDeParqueaderoMedianteID(facturaAbiertaConsultada.getId_parqueadero()));
            lbl_facturadoPor.setText(usuarioControla.consultarUsuarioMedianteID(facturaAbiertaConsultada.getFacturadoPor()));
            lbl_convenio.setText(convControla.consultarNombreDeConvenioMedianteID(facturaAbiertaConsultada.getId_convenio()));
            lbl_tarifa.setText(tarifaControla.consultarNombreDeTarifaMedianteID(facturaAbiertaConsultada.getId_tarifa()));
        }
        
        lbl_horaIngreso.setText(facturaAbiertaConsultada.getFechaDeIngresoVehiculo());
        
        //Deshabilitamos la edicion de las facturas de primer ingreso
        if(lbl_horaIngreso.getText().equals("1990-01-01 23:59:00.0")){
            lbl_horaIngreso.setText("Registro 1er vez en sistema.");
            btn_editar.setEnabled(false);
            btn_eliminar.setEnabled(false);
        }else{
            btn_editar.setEnabled(true);
            btn_eliminar.setEnabled(true);
        }               
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
        lbl_placa1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbl_codigo = new javax.swing.JLabel();
        lbl_placa2 = new javax.swing.JLabel();
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
        lbl_numIdentificacion1 = new javax.swing.JLabel();
        lbl_numIdentificacion2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        btn_imprimirFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/preview.png"))); // NOI18N
        btn_imprimirFactura.setText("Vista Previa");
        btn_imprimirFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimirFacturaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Cod. Factura:");

        lbl_placa1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_placa1.setText("Placa:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Tipo Vehiculo:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Hora Ingreso:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Numero de Parqueadero:");

        lbl_codigo.setText("codigo_factura");

        lbl_placa2.setText("placa_vehiculo");

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

        lbl_numIdentificacion1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_numIdentificacion1.setText("N° Identif:");

        lbl_numIdentificacion2.setText("no_identificacion");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_placa1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_tarifa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_facturadoPor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_convenio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(24, 24, 24))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_codigo)
                            .addComponent(lbl_propietario)
                            .addComponent(lbl_tipoVehiculo)
                            .addComponent(lbl_noParqueadero, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_horaIngreso)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbl_placa2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_numIdentificacion1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_numIdentificacion2)))
                        .addGap(0, 17, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btn_imprimirFactura)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_editar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_eliminar)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbl_codigo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_placa1)
                    .addComponent(lbl_placa2)
                    .addComponent(lbl_numIdentificacion1)
                    .addComponent(lbl_numIdentificacion2))
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
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_imprimirFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_imprimirFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirFacturaActionPerformed
        String codigo = lbl_codigo.getText();
        String tipoDeVehiculo = lbl_tipoVehiculo.getText();
        
        if(idDelVehiculo != 0){
            facturaControla.generarTicketIngreso(true, tipoDeVehiculo, idDelVehiculo, null, null, null, codigo, true); 
        }else if(placaDelVehiculo != null){
            facturaControla.generarTicketIngreso(false, tipoDeVehiculo, 0, placaDelVehiculo, null, null, codigo, true); 
        }else if(tipoIdentifVehiculo != null && numIdentifVehiculo != null){
            facturaControla.generarTicketIngreso(false, tipoDeVehiculo, 0, null, tipoIdentifVehiculo, numIdentifVehiculo, codigo, true); 
        }
        btn_imprimirFactura.setEnabled(false);
    }//GEN-LAST:event_btn_imprimirFacturaActionPerformed

    private void btn_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editarActionPerformed
        dispose();
        new EditarFacturaIngreso().setVisible(true);
    }//GEN-LAST:event_btn_editarActionPerformed
    
    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        String botones[] = {"Si", "No"};
        int decision = JOptionPane.showOptionDialog(this, "¿Está seguro que desea eliminar?", "Eliminar factura", 0, JOptionPane.QUESTION_MESSAGE, paramControla.getIcon("/icons/pregunta.png", 32, 32), botones, this);
        if(decision == JOptionPane.YES_OPTION){    
           
            int idParq = facturaAbiertaConsultada.getId_parqueadero();
            String tipoDeParqueadero = parqControla.consultarTipoParqueaderoMedianteID(idParq);
            if(tipoDeParqueadero.equals("RESIDENTE")){
                facturaControla.borrarFactura(codigoFactura_actualizada);
                if(lbl_tipoVehiculo.getText().equals("AUTOMOVIL") || lbl_tipoVehiculo.getText().equals("MOTO")){
                    parqControla.actualizarEstadoDeParqueadero(lbl_tipoVehiculo.getText(), lbl_placa2.getText(), null, null, lbl_propietario.getText(), idParq, "No");
                }else if(lbl_tipoVehiculo.getText().equals("BICICLETA") || lbl_tipoVehiculo.getText().equals("PATINETA") || lbl_tipoVehiculo.getText().equals("OTRO")){
                    parqControla.actualizarEstadoDeParqueadero(lbl_tipoVehiculo.getText(), null, lbl_placa2.getText(), lbl_numIdentificacion2.getText(), lbl_propietario.getText(), idParq, "No");
                }
                    
            }else{
                facturaControla.borrarFactura(codigoFactura_actualizada);
                if(lbl_tipoVehiculo.getText().equals("AUTOMOVIL") || lbl_tipoVehiculo.getText().equals("MOTO")){
                    parqControla.liberarParqueadero(lbl_tipoVehiculo.getText(), lbl_placa2.getText(), null, null);
                }else if(lbl_tipoVehiculo.getText().equals("BICICLETA") || lbl_tipoVehiculo.getText().equals("PATINETA") || lbl_tipoVehiculo.getText().equals("OTRO")){
                    parqControla.liberarParqueadero(lbl_tipoVehiculo.getText(), null, lbl_placa2.getText(), lbl_numIdentificacion2.getText());
                }  
            }
            
            int filaSelec = tablaOperacionFacturas.getSelectedRow();
            modelo.removeRow(filaSelec);
            JOptionPane.showMessageDialog(null, "La factura ha sido eliminada satisfactoriamente.", "Confirmación", JOptionPane.INFORMATION_MESSAGE, paramControla.getIcon("/icons/exitoso.png", 32, 32));
            dispose();
            GestionarFacturas.hayFacturaVisualizandose = false;
        }
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
         cerrarInformacionFactura();
    }//GEN-LAST:event_formWindowClosing

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
    public static javax.swing.JButton btn_imprimirFactura;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    public static javax.swing.JLabel lbl_codigo;
    public static javax.swing.JLabel lbl_convenio;
    public static javax.swing.JLabel lbl_facturadoPor;
    public static javax.swing.JLabel lbl_horaIngreso;
    public static javax.swing.JLabel lbl_noParqueadero;
    private javax.swing.JLabel lbl_numIdentificacion1;
    private javax.swing.JLabel lbl_numIdentificacion2;
    private javax.swing.JLabel lbl_placa1;
    public static javax.swing.JLabel lbl_placa2;
    public static javax.swing.JLabel lbl_propietario;
    public static javax.swing.JLabel lbl_tarifa;
    public static javax.swing.JLabel lbl_tipoVehiculo;
    // End of variables declaration//GEN-END:variables

    //Metodo que se invoca al cerrar el jFrame
    private void cerrarInformacionFactura(){
        GestionarFacturas.hayFacturaVisualizandose = false;
        dispose();
    } 
}
