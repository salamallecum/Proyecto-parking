package vista;

import com.sun.glass.events.KeyEvent;
import controlador.ConvenioControlador;
import controlador.FacturaControlador;
import controlador.ParqueaderoControlador;
import controlador.TarifaControlador;
import controlador.VehiculoControlador;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import modelo.Convenio;
import modelo.Factura;
import modelo.Tarifa;
import modelo.Vehiculo;
import org.apache.log4j.Logger;



/**
 *
 * @author ALEJO
 */
public class EditarFacturaFinal extends javax.swing.JFrame {

    String factura_actualizada;
    int ID;
    javax.swing.JTable tablafacturas;
    int filas;
    int montoDelaTarifa;
    String codigo_factura;
    int validacion = 0;
    int dineroEnEfectivoBackupParaCalculos;
    String usuario;
    String efectivoBackup;
    long diferenciaDeFechasEnMilisegundos; 
    
    Convenio conv = new Convenio(0, "", "", "");
    Convenio convenioAAplicar = new Convenio(0, "", "", "");
    Tarifa tarif = new Tarifa(0, "", "", "", "", "", "", "", "", "", "");
    Tarifa tarifaACobrar = new Tarifa(0, "", "", "", "", "", "", "", "", "", ""); 
    Factura facturaAEditar = new Factura (0, "", "", "", "", "", 0, "", "", "", 0, 0, "", 0, "", "", "", "", "");
    Factura facturaAActualizar = new Factura (0, "", "", "", "", "", 0, "", "", "", 0, 0, "", 0, "", "", "", "", "");
    
    FacturaControlador facturaControla = new FacturaControlador();
    ParqueaderoControlador parqControla = new ParqueaderoControlador();
    TarifaControlador tarifaControla = new TarifaControlador();
    ConvenioControlador convControla = new ConvenioControlador();
    VehiculoControlador vehiControla = new VehiculoControlador();
    
    String vueltas = "";
    String montoAPagarParaCalculoPago = "";
       
    DefaultTableModel modelo;
     
    String placa_back;
    
    private final Logger log = Logger.getLogger(EditarFacturaFinal.class);
    private URL url = EditarFacturaFinal.class.getResource("Log4j.properties");
    
    /**
     * Creates new form nuevoUsuario
     */
    public EditarFacturaFinal() {
        initComponents();
        factura_actualizada = GestionarFacturas.codigoFactura_update;
        tablafacturas = GestionarFacturas.table_listaFacturas;
        modelo = GestionarFacturas.modelo;
        usuario = Login.usuario;
        filas = GestionarFacturas.Filas;
        
        setSize(644,445);
        setResizable(false);
        setTitle("Editar factura");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        //Avisamos que esta ventana se encuentra abierta para que no deje cerrar sesion al usuario
        MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = true;
        
        DefaultComboBoxModel modeloConv = new DefaultComboBoxModel(conv.mostrarConveniosDisponibles());
        cmb_convenios.setModel(modeloConv);

        DefaultComboBoxModel modeloTarif = new DefaultComboBoxModel(tarif.mostrarTarifasDisponibles());
        cmb_tarifas.setModel(modeloTarif);
              
        //Cargamos la informacion de la facturacerrada en el frame
        facturaAEditar = facturaControla.consultarInformacionDeUnaFacturaCerrada(factura_actualizada);
        
        ID = facturaAEditar.getId();
        codigo_factura = facturaAEditar.getCodigo();
        //Hacemos un respaldo de la placa del vehiculo por si el usuario se equivoca
        placa_back = facturaAEditar.getPlaca();        
        
        txt_placa.setText(placa_back);
        txt_propietario.setText(facturaAEditar.getPropietario());
        cmb_tipVehi.setSelectedItem(facturaAEditar.getClaseDeVehiculo());
        lbl_noParq.setText(parqControla.consultarNombreDeParqueaderoMedianteID(facturaAEditar.getId_parqueadero()));
        cmb_convenios.setSelectedIndex(facturaAEditar.getId_convenio());
        cmb_tarifas.setSelectedIndex(facturaAEditar.getId_tarifa());
        lbl_fechaIngreso.setText(facturaAEditar.getFechaDeIngresoVehiculo());
        lbl_fechaSalida.setText(facturaAEditar.getFechaDeSalidaVehiculo());
        
        //Guardamos un backup del efectivo entregado por el usuario para la factura originalmente
        efectivoBackup = facturaControla.quitarFormatoMoneda(facturaAEditar.getEfectivo());
        dineroEnEfectivoBackupParaCalculos = Integer.parseInt(efectivoBackup);
        txt_efectivo.setText(efectivoBackup);
        
        //Obtenemos el convenio y la tarifa que trae la factura
        int tarifa_escogida = facturaAEditar.getId_tarifa();
        int convenio_escogido = facturaAEditar.getId_convenio();
        
        //Hacemos la estimiacion del convenio o tarifa del usuario
        estimarConvenioOTarifa(tarifa_escogida, convenio_escogido);
        
        
    }
    
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/editFctraIco.png"));
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

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_placa = new javax.swing.JTextField();
        txt_propietario = new javax.swing.JTextField();
        btn_actualizar = new javax.swing.JButton();
        lbl_imgEditUsuario = new javax.swing.JLabel();
        cmb_tarifas = new javax.swing.JComboBox<>();
        cmb_convenios = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_efectivo = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        lbl_cambio = new javax.swing.JLabel();
        cmb_tipVehi = new javax.swing.JComboBox<>();
        lbl_fechaIngreso = new javax.swing.JLabel();
        lbl_noParq = new javax.swing.JLabel();
        lbl_fechaSalida = new javax.swing.JLabel();
        lbl_totalAPagar = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbl_diferencia = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconImage(getIconImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Placa:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Propietario:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Clase:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("N° parqueadero:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Convenio:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Tarifa:");

        txt_placa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_placaFocusLost(evt);
            }
        });
        txt_placa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_placaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_placaKeyTyped(evt);
            }
        });

        txt_propietario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_propietarioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_propietarioKeyTyped(evt);
            }
        });

        btn_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh256_24854.png"))); // NOI18N
        btn_actualizar.setText("Actualizar");
        btn_actualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });

        lbl_imgEditUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_imgEditUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/editFctra.png"))); // NOI18N

        cmb_tarifas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_tarifasItemStateChanged(evt);
            }
        });

        cmb_convenios.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_conveniosItemStateChanged(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Hora Ingreso:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Hora Salida:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Valor a pagar:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Efectivo:");

        txt_efectivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_efectivoKeyPressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Cambio:");

        lbl_cambio.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_cambio.setText("Cambio ($):");

        cmb_tipVehi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "AUTOMOVIL", "MOTO" }));
        cmb_tipVehi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_tipVehiItemStateChanged(evt);
            }
        });

        lbl_fechaIngreso.setText("fecha_ingreso");

        lbl_noParq.setText("num_parqueadero");

        lbl_fechaSalida.setText("fecha_salida");

        lbl_totalAPagar.setText("totalAPagar");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Tiempo total:");

        lbl_diferencia.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_diferencia.setForeground(new java.awt.Color(0, 0, 255));
        lbl_diferencia.setText("diferencia");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_propietario)
                            .addComponent(lbl_noParq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmb_convenios, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmb_tarifas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_diferencia)
                                    .addComponent(txt_placa, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmb_tipVehi, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_fechaIngreso)
                                    .addComponent(lbl_fechaSalida)
                                    .addComponent(lbl_totalAPagar))
                                .addGap(0, 104, Short.MAX_VALUE)))
                        .addGap(24, 24, 24)
                        .addComponent(lbl_imgEditUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_cambio)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(259, 259, 259)
                        .addComponent(btn_actualizar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(180, 180, 180)
                        .addComponent(txt_efectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(lbl_imgEditUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_placa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_propietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cmb_tipVehi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(lbl_noParq))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cmb_convenios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmb_tarifas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(lbl_fechaIngreso))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(lbl_fechaSalida))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lbl_diferencia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lbl_totalAPagar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txt_efectivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lbl_cambio))
                .addGap(23, 23, 23)
                .addComponent(btn_actualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    //Metodo boton Actualizar
    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
     
        validacionesAntesDeActualizar();
        
        int convenio_cmb = cmb_convenios.getSelectedIndex();
        int tarifa_cmb = cmb_tarifas.getSelectedIndex();
        
        //Hacemos la estimacion al convenio o ala tarifa seleccionada, para asi generar el nuevo monto a pagar y demas datos de cobro de factura
        estimarConvenioOTarifa(tarifa_cmb, convenio_cmb);
        
        //Capturamos los datos del formulario
        String tipoVehi_string = "";       
        String placa = txt_placa.getText();
        String dueño = txt_propietario.getText();
        int tipVehi_cmb = cmb_tipVehi.getSelectedIndex();
        String no_parq = lbl_noParq.getText();
        String diferencia = lbl_diferencia.getText();
        String valorPagar = lbl_totalAPagar.getText();
        String efectivo = txt_efectivo. getText();
        String cambio = lbl_cambio.getText();
               
        if(placa.equals("")){
            txt_placa.setBackground(Color.red);
            validacion++;
        }
        if(dueño.equals("")){
            txt_propietario.setBackground(Color.red);
            validacion++;
        }
        
        if(tipVehi_cmb == 0){
            tipoVehi_string = "Seleccione"; 
            validacion++;
        }        
        else if(tipVehi_cmb == 1){
            tipoVehi_string = "AUTOMOVIL";
        }else if(tipVehi_cmb == 2){
            tipoVehi_string = "MOTO";
        } 
             
        if(efectivo.equals("")){
            txt_placa.setBackground(Color.red);
            validacion++;
        }
        
        if (validacion == 0) {
                        
            efectivo = facturaControla.agregarFormatoMoneda(efectivo);
            
            //Encapsulamos el objeto factura a actualizar
            facturaAActualizar.setId(ID);
            facturaAActualizar.setPlaca(placa);
            facturaAActualizar.setPropietario(dueño);
            facturaAActualizar.setClaseDeVehiculo(tipoVehi_string);
            facturaAActualizar.setFacturadoPor(usuario);
            facturaAActualizar.setId_convenio(convenio_cmb);
            facturaAActualizar.setId_tarifa(tarifa_cmb);
            facturaAActualizar.setDiferencia(diferencia);
            facturaAActualizar.setValorAPagar(valorPagar);
            facturaAActualizar.setEfectivo(efectivo);
            facturaAActualizar.setCambio(cambio);
            
            //Actualizamos la factura
            facturaControla.actualizarFacturaSalida(facturaAActualizar);
                 
            //Aqui modificamos la fila existente y que fue seleccionada en la tabla gestionar facturas
            Object Fila[] = new Object[4];
            Fila[0] = codigo_factura;
            Fila[1] = facturaControla.fecha_de_factura();
            Fila[2] = usuario;
            Fila[3] = valorPagar;
            
            for(int i=0; i < tablafacturas.getColumnCount(); i++){
                modelo.setValueAt(Fila[i], filas, i);
            }
                                
            //Actualizamos la informacion del propietario en la tabla de parqueaderos            
            parqControla.actualizarInfoDePropietarioEnParqueadero(placa, dueño, no_parq);
                           
            JOptionPane.showMessageDialog(null, "Factura actualizada satisfactoriamente.");
            this.dispose();
            new InformacionFacturaFinal().setVisible(true);

        }else{
            JOptionPane.showMessageDialog(null, "Debes llenar todos los campos.");
            Normalizar();
        } 
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void txt_placaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_placaKeyTyped
        
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 6;
        if(txt_placa.getText().length()== numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Solo 6 caracteres");
        }

        ////Forza a escribir en mayuscula
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            evt.setKeyChar(Character.toUpperCase(c));
            
        }
    }//GEN-LAST:event_txt_placaKeyTyped

    private void txt_placaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_placaFocusLost
        validacionesAntesDeActualizar();
        
    }//GEN-LAST:event_txt_placaFocusLost

    private void txt_placaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_placaKeyPressed
        
    }//GEN-LAST:event_txt_placaKeyPressed

    private void cmb_tipVehiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_tipVehiItemStateChanged
        String tipVehi_string = (String)cmb_tipVehi.getSelectedItem();
            
        if(tipVehi_string.equals("Seleccione")){
            cmb_convenios.setSelectedIndex(0);
            cmb_tarifas.setSelectedIndex(0);

        } else if(tipVehi_string.equals("AUTOMOVIL")){
            cmb_convenios.setSelectedIndex(1);
            cmb_tarifas.setSelectedIndex(2);

        } else if(tipVehi_string.equals("MOTO")){
            cmb_convenios.setSelectedIndex(1);
            cmb_tarifas.setSelectedIndex(3);
        }
    }//GEN-LAST:event_cmb_tipVehiItemStateChanged

    private void txt_propietarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_propietarioKeyPressed
       
    }//GEN-LAST:event_txt_propietarioKeyPressed

    private void txt_propietarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_propietarioKeyTyped
        
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 30;
        if(txt_propietario.getText().length()== numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Solo 30 caracteres");
        }

        //Forza aescribir en mayuscula
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            evt.setKeyChar(Character.toUpperCase(c));
            
        }
    }//GEN-LAST:event_txt_propietarioKeyTyped

    private void cmb_conveniosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_conveniosItemStateChanged
        
        int convenio_escogido = cmb_convenios.getSelectedIndex();
                 
        if(convenio_escogido == 0){
            cmb_convenios.setSelectedIndex(1);
        }
    }//GEN-LAST:event_cmb_conveniosItemStateChanged
  
    private void cmb_tarifasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_tarifasItemStateChanged

        int tarifa_escogida = cmb_tarifas.getSelectedIndex();
                 
        if(tarifa_escogida == 0){
            cmb_tarifas.setSelectedIndex(1);
        } 
    }//GEN-LAST:event_cmb_tarifasItemStateChanged

    private void txt_efectivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_efectivoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            calcularVueltas(txt_efectivo.getText());
        }
    }//GEN-LAST:event_txt_efectivoKeyPressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        cerrarEdicionDeFacturaFinal();
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
            java.util.logging.Logger.getLogger(EditarFacturaFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarFacturaFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarFacturaFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarFacturaFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                    new EditarFacturaFinal().setVisible(true);
                
                }catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    //Logger.getLogger(EditarFacturaFinal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_actualizar;
    private javax.swing.JComboBox<String> cmb_convenios;
    private javax.swing.JComboBox<String> cmb_tarifas;
    private javax.swing.JComboBox<String> cmb_tipVehi;
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
    private javax.swing.JLabel lbl_cambio;
    private javax.swing.JLabel lbl_diferencia;
    private javax.swing.JLabel lbl_fechaIngreso;
    private javax.swing.JLabel lbl_fechaSalida;
    private javax.swing.JLabel lbl_imgEditUsuario;
    private javax.swing.JLabel lbl_noParq;
    private javax.swing.JLabel lbl_totalAPagar;
    private javax.swing.JTextField txt_efectivo;
    private javax.swing.JTextField txt_placa;
    private javax.swing.JTextField txt_propietario;
    // End of variables declaration//GEN-END:variables

    //Metodo que se invoca al cerrar el jFrame
    private void cerrarEdicionDeFacturaFinal(){
        
        String botones[] = {"Cerrar", "Cancelar"};
        int eleccion = JOptionPane.showOptionDialog(this, "¿Está seguro que desea cerrar?", "Editar factura", 0, 3, null, botones, this);
        
        if(eleccion == JOptionPane.YES_OPTION){
            dispose();
            new InformacionFacturaFinal().setVisible(true);
        }
    }
    
    //Metodo que estima el convenio o la tarifa seleccionada a la factura a editar
    private void estimarConvenioOTarifa(int tarifaEscogida, int convenioEscogido){
        
        //Traemos la fecha en la que ingreso el vehiculo y la fecha en la que salió y las convertimos a Date y luego a Calendar
        String str_fechaIngresoVehiculo = facturaAEditar.getFechaDeIngresoVehiculo();
        Date fechaDeIngreso = facturaControla.convertidorDeFechasADate(str_fechaIngresoVehiculo); 
        Calendar calendar_fechaIngreso = facturaControla.convertidorDeFechasDeDateACalendar(fechaDeIngreso);

        String str_fechaSalidaVehiculo = facturaAEditar.getFechaDeSalidaVehiculo();
        Date fechaDeSalida = facturaControla.convertidorDeFechasADate(str_fechaSalidaVehiculo);            
        
        tarifaACobrar = tarifaControla.consultarUnaTarifaMedianteID(tarifaEscogida);

        //Traemos el objeto convenio que se va a aplicar
        convenioAAplicar = convControla.consultarUnConvenioMedianteID(convenioEscogido);
        
        //Validamos si la tarifa se encuentra anulada, si es asi, no generará cobro alguno
        String tarifaEstaAnulada = tarifaACobrar.getTarifaAnulada();
        if(tarifaEstaAnulada.equals("Si")){
            lbl_diferencia.setText("N/A");
            lbl_totalAPagar.setText("0");
            txt_efectivo.setText("");
            lbl_cambio.setText(efectivoBackup);

        }else{
            //Obtenemos los datos de la tarifa a cobrar
            String montoDeTarifa = tarifaACobrar.getMontoTarifa();
            String frecuenciaTarifa = tarifaACobrar.getFrecuenciaTarifa();
            String aplicarDescuento = tarifaACobrar.getTarifaTieneDescuento();
            String aplicarCostoAdicional = tarifaACobrar.getTarifaCobraTiempoAdicional();
            
            //Evaluamos el nombre del convenio y tarifa
            if(convenioAAplicar.getNombre().equals("NINGUNO") && tarifaACobrar.getNombreTarifa().equals("NINGUNA")){
                lbl_diferencia.setText("N/A");
                lbl_totalAPagar.setText("0");
                txt_efectivo.setText("");
                lbl_cambio.setText(efectivoBackup);

            }else if(!convenioAAplicar.getNombre().equals("NINGUNO") && tarifaACobrar.getNombreTarifa().equals("NINGUNA")){
                lbl_diferencia.setText("N/A");
                lbl_totalAPagar.setText("0");
                txt_efectivo.setText("");
                lbl_cambio.setText(efectivoBackup);

            }else if(convenioAAplicar.getNombre().equals("NINGUNO") && !tarifaACobrar.getNombreTarifa().equals("NINGUNA")){

                long long_montoTarifa = Long.parseLong(montoDeTarifa);
                long diferencia = 0;

                //Calculamos la diferencia el milisegundo que existe entre la fecha de ingreso y la fecha de salida del vehiculo
                String descuento_str;
                long descuento;
                long diferenciaConDescuento = 0;
                String dif_str;
                String montoAPagar = "";
                String difConDescuento_str = "";
                diferenciaDeFechasEnMilisegundos = facturaControla.calcularDiferenciaDeFechasEnMilisegundos(calendar_fechaIngreso, fechaDeSalida);

                //Evaluamos la frecuencia de la tarifa a aplicar
                if(frecuenciaTarifa.equals("MINUTO")){

                    diferencia = TimeUnit.MILLISECONDS.toMinutes(diferenciaDeFechasEnMilisegundos);

                    if(aplicarDescuento.equals("Si")){
                        descuento_str = tarifaACobrar.getTiempoDelDescuento();
                        descuento = Long.parseLong(descuento_str);
                        diferenciaConDescuento = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento); 
                        difConDescuento_str = Long.toString(diferenciaConDescuento) + " minutos";
                        lbl_diferencia.setText(difConDescuento_str);   
                        lbl_diferencia.setVisible(true);

                        //Damos formato de moneda al monto a pagar
                        montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferenciaConDescuento);
                        montoAPagar = facturaControla.agregarFormatoMoneda(montoAPagarParaCalculoPago);
                        lbl_totalAPagar.setText(montoAPagar);
                        lbl_totalAPagar.setVisible(true);

                        //Actualizamos el cambio a dar de acuerdo con el monto dado por el susuario con anterioridad para la factura
                        if(!efectivoBackup.equals("0")){
                            txt_efectivo.setText(efectivoBackup);
                            calcularVueltas(efectivoBackup);
                        }

                    }else{
                        dif_str = Long.toString(diferencia);
                        lbl_diferencia.setText(dif_str + " minutos");
                        lbl_diferencia.setVisible(true);

                       //Damos formato de moneda al monto a pagar
                       montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferencia);
                        montoAPagar = facturaControla.agregarFormatoMoneda(facturaControla.calcularPago(long_montoTarifa, diferencia));
                        lbl_totalAPagar.setText(montoAPagar);
                        lbl_totalAPagar.setVisible(true); 

                        //Actualizamos el cambio a dar de acuerdo con el monto dado por el susuario con anterioridad para la factura
                        if(!efectivoBackup.equals("0")){
                            txt_efectivo.setText(efectivoBackup);
                            calcularVueltas(efectivoBackup);
                        }   
                    }  

                }else if(frecuenciaTarifa.equals("HORA")){

                    diferencia = TimeUnit.MILLISECONDS.toHours(diferenciaDeFechasEnMilisegundos);

                    if(aplicarDescuento.equals("Si")){
                        descuento_str = tarifaACobrar.getTiempoDelDescuento();
                        descuento = Long.parseLong(descuento_str);
                        diferenciaConDescuento = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento); 
                        difConDescuento_str = Long.toString(diferenciaConDescuento) + " horas";
                        lbl_diferencia.setText(difConDescuento_str);   
                        lbl_diferencia.setVisible(true);

                        if(aplicarCostoAdicional.equals("Si")){
                            difConDescuento_str = diferenciaConDescuento + facturaControla.calcularPagoTeniendoEnCuentaMinutosUtilizados(long_montoTarifa, diferenciaConDescuento, tarifaACobrar, diferenciaDeFechasEnMilisegundos);
                            lbl_diferencia.setText(difConDescuento_str);   
                            lbl_diferencia.setVisible(true);
                            
                            montoAPagar = facturaControla.obtenervalorAPagarPorDiferenciaAdicional();
                            
                            //Mostramos el total a pagar en pantalla
                            lbl_totalAPagar.setText(montoAPagar);
                            lbl_totalAPagar.setVisible(true);
                            
                            montoAPagarParaCalculoPago = facturaControla.quitarFormatoMoneda(montoAPagar);                           

                            //Actualizamos el cambio a dar de acuerdo con el monto dado por el susuario con anterioridad para la factura
                            if(!efectivoBackup.equals("0")){
                                txt_efectivo.setText(efectivoBackup);
                                calcularVueltas(efectivoBackup);
                            }

                        }else{
                            //Damos formato de moneda al monto a pagar
                            montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferenciaConDescuento);
                            montoAPagar = facturaControla.agregarFormatoMoneda(montoAPagarParaCalculoPago);
                            lbl_totalAPagar.setText(montoAPagar);
                            lbl_totalAPagar.setVisible(true); 

                            //Actualizamos el cambio a dar de acuerdo con el monto dado por el susuario con anterioridad para la factura
                            if(!efectivoBackup.equals("0")){
                                txt_efectivo.setText(efectivoBackup);
                                calcularVueltas(efectivoBackup);
                            }
                        }

                    }else{
                        dif_str = Long.toString(diferencia) + " horas";
                        lbl_diferencia.setText(dif_str);
                        lbl_diferencia.setVisible(true);

                        if(aplicarCostoAdicional.equals("Si")){
                           dif_str = dif_str + facturaControla.calcularPagoTeniendoEnCuentaMinutosUtilizados(long_montoTarifa, diferencia, tarifaACobrar, diferenciaDeFechasEnMilisegundos);
                           lbl_diferencia.setText(dif_str);
                           lbl_diferencia.setVisible(true);
                            
                           montoAPagar = facturaControla.obtenervalorAPagarPorDiferenciaAdicional();
                            
                            //Mostramos el total a pagar en pantalla
                            lbl_totalAPagar.setText(montoAPagar);
                            lbl_totalAPagar.setVisible(true);
                            
                            montoAPagarParaCalculoPago = facturaControla.quitarFormatoMoneda(montoAPagar);
                            
                            //Actualizamos el cambio a dar de acuerdo con el monto dado por el susuario con anterioridad para la factura
                            if(!efectivoBackup.equals("0")){
                                txt_efectivo.setText(efectivoBackup);
                                calcularVueltas(efectivoBackup);
                            }

                        }else{
                            //Damos formato de moneda al monto a pagar
                            montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferencia);
                            montoAPagar = facturaControla.agregarFormatoMoneda(montoAPagarParaCalculoPago);
                            lbl_totalAPagar.setText(montoAPagar);
                            lbl_totalAPagar.setVisible(true);
                            
                            //Actualizamos el cambio a dar de acuerdo con el monto dado por el susuario con anterioridad para la factura
                            if(!efectivoBackup.equals("0")){
                                txt_efectivo.setText(efectivoBackup);
                                calcularVueltas(efectivoBackup);
                            }
                        }
                    }

                }else if(frecuenciaTarifa.equals("DIA")){

                    diferencia = TimeUnit.MILLISECONDS.toDays(diferenciaDeFechasEnMilisegundos);

                    if(aplicarDescuento.equals("Si")){
                        descuento_str = tarifaACobrar.getTiempoDelDescuento();
                        descuento = Long.parseLong(descuento_str);
                        diferenciaConDescuento = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento); 
                        difConDescuento_str = Long.toString(diferenciaConDescuento) + " días";
                        lbl_diferencia.setText(difConDescuento_str);   
                        lbl_diferencia.setVisible(true);

                        if(aplicarCostoAdicional.equals("Si")){
                           difConDescuento_str = difConDescuento_str + facturaControla.calcularPagoTeniendoEnCuentaMinutosUtilizados(long_montoTarifa, diferenciaConDescuento, tarifaACobrar, diferenciaDeFechasEnMilisegundos);
                           lbl_diferencia.setText(difConDescuento_str);   
                           lbl_diferencia.setVisible(true);
                           
                           montoAPagar = facturaControla.obtenervalorAPagarPorDiferenciaAdicional();
                            
                            //Mostramos el total a pagar en pantalla
                            lbl_totalAPagar.setText(montoAPagar);
                            lbl_totalAPagar.setVisible(true);
                            
                            montoAPagarParaCalculoPago = facturaControla.quitarFormatoMoneda(montoAPagar);
                            
                            //Actualizamos el cambio a dar de acuerdo con el monto dado por el susuario con anterioridad para la factura
                            if(!efectivoBackup.equals("0")){
                                txt_efectivo.setText(efectivoBackup);
                                calcularVueltas(efectivoBackup);
                            }

                        }else{
                            //Damos formato de moneda al monto a pagar
                            montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferenciaConDescuento);
                            montoAPagar = facturaControla.agregarFormatoMoneda(montoAPagarParaCalculoPago);
                            lbl_totalAPagar.setText(montoAPagar);
                            lbl_totalAPagar.setVisible(true); 
                            
                            //Actualizamos el cambio a dar de acuerdo con el monto dado por el susuario con anterioridad para la factura
                            if(!efectivoBackup.equals("0")){
                                txt_efectivo.setText(efectivoBackup);
                                calcularVueltas(efectivoBackup);
                            }
                        }

                    }else{
                        dif_str = Long.toString(diferencia) + " días";
                        lbl_diferencia.setText(dif_str);
                        lbl_diferencia.setVisible(true);

                        if(aplicarCostoAdicional.equals("Si")){
                           dif_str = dif_str + facturaControla.calcularPagoTeniendoEnCuentaMinutosUtilizados(long_montoTarifa, diferencia, tarifaACobrar, diferenciaDeFechasEnMilisegundos);
                           lbl_diferencia.setText(dif_str);
                           lbl_diferencia.setVisible(true);
                            
                           montoAPagar = facturaControla.obtenervalorAPagarPorDiferenciaAdicional();
                            
                            //Mostramos el total a pagar en pantalla
                            lbl_totalAPagar.setText(montoAPagar);
                            lbl_totalAPagar.setVisible(true);
                            
                            montoAPagarParaCalculoPago = facturaControla.quitarFormatoMoneda(montoAPagar);

                           //Actualizamos el cambio a dar de acuerdo con el monto dado por el susuario con anterioridad para la factura
                            if(!efectivoBackup.equals("0")){
                                txt_efectivo.setText(efectivoBackup);
                                calcularVueltas(efectivoBackup);
                            }
                        }else{
                            //Damos formato de moneda al monto a pagar
                            montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferencia);
                            montoAPagar = facturaControla.agregarFormatoMoneda(montoAPagarParaCalculoPago);
                            lbl_totalAPagar.setText(montoAPagar);
                            lbl_totalAPagar.setVisible(true); 
                            
                            //Actualizamos el cambio a dar de acuerdo con el monto dado por el susuario con anterioridad para la factura
                            if(!efectivoBackup.equals("0")){
                                txt_efectivo.setText(efectivoBackup);
                                calcularVueltas(efectivoBackup);
                            }
                        }
                    }
                }

            }else if(!convenioAAplicar.getNombre().equals("NINGUNO") && !tarifaACobrar.getNombreTarifa().equals("NINGUNA")){
                //En este caso, asi tenga un convenio asignado, si la tarifa no es ninguna, predominará la tarifa    
                long long_montoTarifa = Long.parseLong(montoDeTarifa);
                long diferencia = 0;

                String descuento_str;
                long descuento;
                long diferenciaConDescuento = 0;
                String dif_str;
                String montoAPagar = "";
                String difConDescuento_str = "";

                //Calculamos la diferencia el milisegundo que existe entre la fecha de ingreso y lafecha desalida del vehiculo
                diferenciaDeFechasEnMilisegundos = facturaControla.calcularDiferenciaDeFechasEnMilisegundos(calendar_fechaIngreso, fechaDeSalida);

                //Evaluamos la frecuencia de la tarifa a aplicar
                if(frecuenciaTarifa.equals("MINUTO")){

                    diferencia = TimeUnit.MILLISECONDS.toMinutes(diferenciaDeFechasEnMilisegundos);

                    if(aplicarDescuento.equals("Si")){
                        descuento_str = tarifaACobrar.getTiempoDelDescuento();
                        descuento = Long.parseLong(descuento_str);
                        diferenciaConDescuento = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento); 
                        difConDescuento_str = Long.toString(diferenciaConDescuento) + " minutos";
                        lbl_diferencia.setText(difConDescuento_str);   
                        lbl_diferencia.setVisible(true);

                        //Damos formato de moneda al monto a pagar
                        montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferenciaConDescuento);
                        montoAPagar = facturaControla.agregarFormatoMoneda(montoAPagarParaCalculoPago);
                        lbl_totalAPagar.setText(montoAPagar);
                        lbl_totalAPagar.setVisible(true);

                        //Actualizamos el cambio a dar de acuerdo con el monto dado por el susuario con anterioridad para la factura
                        if(!efectivoBackup.equals("0")){
                            txt_efectivo.setText(efectivoBackup);
                            calcularVueltas(efectivoBackup);
                        }

                    }else{
                        dif_str = Long.toString(diferencia);
                        lbl_diferencia.setText(dif_str + " minutos");
                        lbl_diferencia.setVisible(true);

                       //Damos formato de moneda al monto a pagar
                       montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferencia);
                        montoAPagar = facturaControla.agregarFormatoMoneda(facturaControla.calcularPago(long_montoTarifa, diferencia));
                        lbl_totalAPagar.setText(montoAPagar);
                        lbl_totalAPagar.setVisible(true); 

                        //Actualizamos el cambio a dar de acuerdo con el monto dado por el susuario con anterioridad para la factura
                        if(!efectivoBackup.equals("0")){
                            txt_efectivo.setText(efectivoBackup);
                            calcularVueltas(efectivoBackup);
                        }
                    }            

                }else if(frecuenciaTarifa.equals("HORA")){

                    diferencia = TimeUnit.MILLISECONDS.toHours(diferenciaDeFechasEnMilisegundos);

                    if(aplicarDescuento.equals("Si")){
                        descuento_str = tarifaACobrar.getTiempoDelDescuento();
                        descuento = Long.parseLong(descuento_str);
                        diferenciaConDescuento = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento); 
                        difConDescuento_str = Long.toString(diferenciaConDescuento) + " horas";
                        lbl_diferencia.setText(difConDescuento_str);   
                        lbl_diferencia.setVisible(true);

                        if(aplicarCostoAdicional.equals("Si")){
                            difConDescuento_str = diferenciaConDescuento + facturaControla.calcularPagoTeniendoEnCuentaMinutosUtilizados(long_montoTarifa, diferenciaConDescuento, tarifaACobrar, diferenciaDeFechasEnMilisegundos);
                            lbl_diferencia.setText(difConDescuento_str);   
                            lbl_diferencia.setVisible(true);
                            
                            montoAPagar = facturaControla.obtenervalorAPagarPorDiferenciaAdicional();
                            
                            //Mostramos el total a pagar en pantalla
                            lbl_totalAPagar.setText(montoAPagar);
                            lbl_totalAPagar.setVisible(true);
                            
                            montoAPagarParaCalculoPago = facturaControla.quitarFormatoMoneda(montoAPagar);
                            
                            //Actualizamos el cambio a dar de acuerdo con el monto dado por el susuario con anterioridad para la factura
                            if(!efectivoBackup.equals("0")){
                                txt_efectivo.setText(efectivoBackup);
                                calcularVueltas(efectivoBackup);
                            }

                        }else{
                            //Damos formato de moneda al monto a pagar
                            montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferenciaConDescuento);
                            montoAPagar = facturaControla.agregarFormatoMoneda(montoAPagarParaCalculoPago);
                            lbl_totalAPagar.setText(montoAPagar);
                            lbl_totalAPagar.setVisible(true); 
                            
                            //Actualizamos el cambio a dar de acuerdo con el monto dado por el susuario con anterioridad para la factura
                            if(!efectivoBackup.equals("0")){
                                txt_efectivo.setText(efectivoBackup);
                                calcularVueltas(efectivoBackup);
                            }
                        }

                    }else{
                        dif_str = Long.toString(diferencia) + " horas";
                        lbl_diferencia.setText(dif_str);
                        lbl_diferencia.setVisible(true);

                        if(aplicarCostoAdicional.equals("Si")){
                           dif_str = dif_str + facturaControla.calcularPagoTeniendoEnCuentaMinutosUtilizados(long_montoTarifa, diferencia, tarifaACobrar, diferenciaDeFechasEnMilisegundos);
                           lbl_diferencia.setText(dif_str);
                           lbl_diferencia.setVisible(true);
                            
                           montoAPagar = facturaControla.obtenervalorAPagarPorDiferenciaAdicional();
                            
                            //Mostramos el total a pagar en pantalla
                            lbl_totalAPagar.setText(montoAPagar);
                            lbl_totalAPagar.setVisible(true);
                            
                            montoAPagarParaCalculoPago = facturaControla.quitarFormatoMoneda(montoAPagar);
                            
                            //Actualizamos el cambio a dar de acuerdo con el monto dado por el susuario con anterioridad para la factura
                            if(!efectivoBackup.equals("0")){
                                txt_efectivo.setText(efectivoBackup);
                                calcularVueltas(efectivoBackup);
                            }
                            
                        }else{
                            //Damos formato de moneda al monto a pagar
                            montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferencia);
                            montoAPagar = facturaControla.agregarFormatoMoneda(montoAPagarParaCalculoPago);
                            lbl_totalAPagar.setText(montoAPagar);
                            lbl_totalAPagar.setVisible(true);
                            
                            //Actualizamos el cambio a dar de acuerdo con el monto dado por el susuario con anterioridad para la factura
                            if(!efectivoBackup.equals("0")){
                                txt_efectivo.setText(efectivoBackup);
                                calcularVueltas(efectivoBackup);
                            }
                        }
                    }

                }else if(frecuenciaTarifa.equals("DIA")){

                    diferencia = TimeUnit.MILLISECONDS.toDays(diferenciaDeFechasEnMilisegundos);

                    if(aplicarDescuento.equals("Si")){
                        descuento_str = tarifaACobrar.getTiempoDelDescuento();
                        descuento = Long.parseLong(descuento_str);
                        diferenciaConDescuento = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento); 
                        difConDescuento_str = Long.toString(diferenciaConDescuento) + " días";
                        lbl_diferencia.setText(difConDescuento_str);   
                        lbl_diferencia.setVisible(true);

                        if(aplicarCostoAdicional.equals("Si")){
                           difConDescuento_str = difConDescuento_str + facturaControla.calcularPagoTeniendoEnCuentaHorasUtilizadas(long_montoTarifa, diferenciaConDescuento, tarifaACobrar, diferenciaDeFechasEnMilisegundos);
                           lbl_diferencia.setText(difConDescuento_str);   
                           lbl_diferencia.setVisible(true);
                            
                           montoAPagar = facturaControla.obtenervalorAPagarPorDiferenciaAdicional();
                            
                            //Mostramos el total a pagar en pantalla
                            lbl_totalAPagar.setText(montoAPagar);
                            lbl_totalAPagar.setVisible(true);
                            
                            montoAPagarParaCalculoPago = facturaControla.quitarFormatoMoneda(montoAPagar);
                            
                            //Actualizamos el cambio a dar de acuerdo con el monto dado por el susuario con anterioridad para la factura
                            if(!efectivoBackup.equals("0")){
                                txt_efectivo.setText(efectivoBackup);
                                calcularVueltas(efectivoBackup);
                            }

                        }else{
                            //Damos formato de moneda al monto a pagar
                            montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferenciaConDescuento);
                            montoAPagar = facturaControla.agregarFormatoMoneda(montoAPagarParaCalculoPago);
                            lbl_totalAPagar.setText(montoAPagar);
                            lbl_totalAPagar.setVisible(true); 
                            
                            //Actualizamos el cambio a dar de acuerdo con el monto dado por el susuario con anterioridad para la factura
                            if(!efectivoBackup.equals("0")){
                                txt_efectivo.setText(efectivoBackup);
                                calcularVueltas(efectivoBackup);
                            }
                        }

                    }else{
                        dif_str = Long.toString(diferencia) + " días";
                        lbl_diferencia.setText(dif_str);
                        lbl_diferencia.setVisible(true);

                        if(aplicarCostoAdicional.equals("Si")){
                           dif_str = dif_str + facturaControla.calcularPagoTeniendoEnCuentaHorasUtilizadas(long_montoTarifa, diferencia, tarifaACobrar, diferenciaDeFechasEnMilisegundos);
                           lbl_diferencia.setText(dif_str);
                           lbl_diferencia.setVisible(true);
                            
                           montoAPagar = facturaControla.obtenervalorAPagarPorDiferenciaAdicional();
                            
                            //Mostramos el total a pagar en pantalla
                            lbl_totalAPagar.setText(montoAPagar);
                            lbl_totalAPagar.setVisible(true);
                            
                            montoAPagarParaCalculoPago = facturaControla.quitarFormatoMoneda(montoAPagar);
                            
                            //Actualizamos el cambio a dar de acuerdo con el monto dado por el susuario con anterioridad para la factura
                            if(!efectivoBackup.equals("0")){
                                txt_efectivo.setText(efectivoBackup);
                                calcularVueltas(efectivoBackup);
                            }
                           
                        }else{
                            //Damos formato de moneda al monto a pagar
                            montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferencia);
                            montoAPagar = facturaControla.agregarFormatoMoneda(montoAPagarParaCalculoPago);
                            lbl_totalAPagar.setText(montoAPagar);
                            lbl_totalAPagar.setVisible(true); 
                            
                            //Actualizamos el cambio a dar de acuerdo con el monto dado por el susuario con anterioridad para la factura
                            if(!efectivoBackup.equals("0")){
                                txt_efectivo.setText(efectivoBackup);
                                calcularVueltas(efectivoBackup);
                            }
                        }
                    }
                }
            }
        }
    }
    
    //Metodo que calcula las vueltas que hay que darle al cliente
    private void calcularVueltas(String dineroRecibido){
        vueltas = facturaControla.calcularVueltas(montoAPagarParaCalculoPago, dineroRecibido);
        lbl_cambio.setText(facturaControla.agregarFormatoMoneda(vueltas)); 
        lbl_cambio.setVisible(true);  
    }
    
    //Metodo que normaliza el formaulario
    private void Normalizar(){
        txt_placa.setBackground(Color.WHITE);
        txt_propietario.setBackground(Color.WHITE);
        txt_efectivo.setBackground(Color.WHITE);
    }
    
    //Validaciones antes de actualizar la factura
    private void validacionesAntesDeActualizar(){
        
        String placa = txt_placa.getText();
        
        if(placa.length() < 6){
            JOptionPane.showMessageDialog(null, "Placa no valida.");
            txt_placa.setText(placa_back);
        }else{
            boolean vehiculoPreviamenteRegistrado = vehiControla.evaluarExistenciaDelVehiculo(placa);
             
            if(vehiculoPreviamenteRegistrado == true){
                 
                String botones[] = {"Si", "No"};
                int eleccion = JOptionPane.showOptionDialog(this, "La placa ingresada corresponde a un vehiculo previamente registrado en el sistema ¿Desea continuar?", "Mensaje", 0, 3, null, botones, this);

                if(eleccion == JOptionPane.YES_OPTION){
                    Vehiculo infoVehiculo = vehiControla.consultarInformacionDeUnVehiculo(placa);
                                        
                    txt_propietario.setText(infoVehiculo.getPropietario());
                    cmb_tipVehi.setSelectedItem(infoVehiculo.getClase());
                    lbl_noParq.setText(parqControla.consultarNombreDeParqueaderoMedianteID(infoVehiculo.getId_parqueadero()));
                    cmb_convenios.setSelectedIndex(infoVehiculo.getId_convenio());
                    cmb_tarifas.setSelectedIndex(infoVehiculo.getId_tarifa());
                    
                    txt_placa.setEnabled(false);
                    txt_propietario.setEnabled(false);
                    cmb_tipVehi.setEnabled(false);
                    cmb_convenios.setEnabled(false);
                    cmb_tarifas.setEnabled(false);
                
                }else{
                    txt_placa.setText("");
                }
            }   
        }
    }
}
