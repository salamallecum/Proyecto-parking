package vista;

import com.sun.glass.events.KeyEvent;
import controlador.ConvenioControlador;
import controlador.FacturaControlador;
import controlador.ParametroControlador;
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
import modelo.Parqueadero;
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
    int convenioBack;
    int tarifaBack;
    int montoDelaTarifa;
    String codigo_factura;
    String tipVehi_back;
    String propietario_back;
    String efectivo_back;  
    String parqueaderoBack;
    int validacion = 0;
    int dineroEnEfectivoBackupParaCalculos;
    String usuario;
    long diferenciaDeFechasEnMilisegundos;
    int idBDTarifaAAplicar;
    int idBDConvenioAAplicar;
    boolean vehiculoDelBackupExiste = false;
    boolean seRealizaronValidaciones = false;
    boolean ingresoDesconocido = false;
    
    Convenio conv = new Convenio();
    Convenio convenioAAplicar = new Convenio();
    Tarifa tarif = new Tarifa();
    Tarifa tarifaACobrar = new Tarifa(); 
    Factura facturaAEditar = new Factura (0, "", "", "", "", "", 0, "", "", "", 0, 0, "", 0, "", "", "", "", "", "");
    Factura facturaAActualizar = new Factura (0, "", "", "", "", "", 0, "", "", "", 0, 0, "", 0, "", "", "", "", "", "");
    
    FacturaControlador facturaControla = new FacturaControlador();
    ParqueaderoControlador parqControla = new ParqueaderoControlador();
    TarifaControlador tarifaControla = new TarifaControlador();
    ConvenioControlador convControla = new ConvenioControlador();
    VehiculoControlador vehiControla = new VehiculoControlador();
    ParametroControlador paramControla = new ParametroControlador();
    
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
        
        setSize(644,463);
        setResizable(false);
        setTitle("Editar factura");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        //Avisamos que esta ventana se encuentra abierta para que no deje cerrar sesion al usuario
        MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = true;
        
        //Ocultamos el combobox de parqueaderos
        cmb_parqVisitantesFinal.setVisible(false);
        
        DefaultComboBoxModel modeloConv = new DefaultComboBoxModel(conv.mostrarConveniosDisponibles());
        cmb_convenios.setModel(modeloConv);
        conv.almacenarNombresConvenio();

        DefaultComboBoxModel modeloTarif = new DefaultComboBoxModel(tarif.mostrarTarifasDisponibles());
        cmb_tarifas.setModel(modeloTarif);
        tarif.almacenarNombresTarifa();      
              
        //Cargamos la informacion de la facturacerrada en el frame
        facturaAEditar = facturaControla.consultarInformacionDeUnaFacturaCerrada(factura_actualizada);
        
        ID = facturaAEditar.getId();
        codigo_factura = facturaAEditar.getCodigo();
        
        //Hacemos un respaldo de la placa del vehiculo y demas datos por si el usuario se equivoca
        placa_back = facturaAEditar.getPlaca();        
        txt_placa.setText(placa_back);
        
        //Evaluamos la existencia del vehiculo backup en bd para futura consulta al actualizar y bloqueamos el formaulario en caso de que exista
        vehiculoDelBackupExiste = vehiControla.evaluarExistenciaDelVehiculo(placa_back);
        
        if(vehiculoDelBackupExiste == true){
            txt_propietario.setEditable(false);
            cmb_tipVehi.setEnabled(false);
            cmb_convenios.setEnabled(false);
            cmb_tarifas.setEnabled(false);
        }
        
        propietario_back = facturaAEditar.getPropietario();
        txt_propietario.setText(propietario_back);
        
        tipVehi_back = facturaAEditar.getClaseDeVehiculo();
        cmb_tipVehi.setSelectedItem(tipVehi_back);
        
        parqueaderoBack = parqControla.consultarNombreDeParqueaderoMedianteID(facturaAEditar.getId_parqueadero());
        lbl_noParq.setText(parqueaderoBack);
        
        convenioBack = facturaAEditar.getId_convenio();
        
        //Buscamos el nombre del convenio que le pertenece a ese id para pintarlo en el combobox
        String nom_convenio = convControla.consultarNombreDeConvenioMedianteID(convenioBack);    
                                     
        cmb_convenios.setSelectedIndex(buscarConvenioEnComboBox(nom_convenio));
        
        tarifaBack = facturaAEditar.getId_tarifa();
        
        //Buscamos el nombre de la tarifa que le pertenece a ese id para pintarlo en el combobox
        String nom_tarifa = tarifaControla.consultarNombreDeTarifaMedianteID(tarifaBack);    
                                     
        cmb_tarifas.setSelectedIndex(buscarTarifaEnComboBox(nom_tarifa));
        
        lbl_fechaIngreso.setText(facturaAEditar.getFechaDeIngresoVehiculo());
        lbl_fechaSalida.setText(facturaAEditar.getFechaDeSalidaVehiculo());
        lbl_totalAPagar.setText(facturaAEditar.getValorAPagar());
        lbl_diferencia.setText(facturaAEditar.getDiferencia());
        
        //Guardamos un backup del efectivo entregado por el usuario para la factura originalmente
        efectivo_back = facturaControla.quitarFormatoMoneda(facturaAEditar.getEfectivo());
        dineroEnEfectivoBackupParaCalculos = Integer.parseInt(efectivo_back);
        txt_efectivo.setText(efectivo_back);
        
        lbl_cambio.setText(facturaAEditar.getCambio());
        
        lbl_impuesto.setText(paramControla.consultarValorDeUnParametro("IMPUESTO"));
       
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
        btn_estimarTarifa = new javax.swing.JButton();
        cmb_parqVisitantesFinal = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        lbl_impuesto = new javax.swing.JLabel();
        lbl_diferencia2 = new javax.swing.JLabel();

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
        cmb_tarifas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmb_tarifasKeyPressed(evt);
            }
        });

        cmb_convenios.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_conveniosItemStateChanged(evt);
            }
        });
        cmb_convenios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmb_conveniosKeyPressed(evt);
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

        txt_efectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_efectivoActionPerformed(evt);
            }
        });
        txt_efectivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_efectivoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_efectivoKeyTyped(evt);
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
        cmb_tipVehi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmb_tipVehiKeyPressed(evt);
            }
        });

        lbl_fechaIngreso.setText("fecha_ingreso");

        lbl_noParq.setText("num");

        lbl_fechaSalida.setText("fecha_salida");

        lbl_totalAPagar.setText("totalAPagar");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Tiempo total:");

        lbl_diferencia.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_diferencia.setForeground(new java.awt.Color(0, 0, 255));
        lbl_diferencia.setText("diferencia");

        btn_estimarTarifa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/calculator-icon_34473.png"))); // NOI18N
        btn_estimarTarifa.setText("Estimar tarifa");
        btn_estimarTarifa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_estimarTarifa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_estimarTarifaActionPerformed(evt);
            }
        });

        cmb_parqVisitantesFinal.setAutoscrolls(true);
        cmb_parqVisitantesFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_parqVisitantesFinalActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("Impuesto:");

        lbl_impuesto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_impuesto.setForeground(new java.awt.Color(204, 0, 153));
        lbl_impuesto.setText("impuesto");

        lbl_diferencia2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_diferencia2.setForeground(new java.awt.Color(204, 0, 153));
        lbl_diferencia2.setText("%");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_estimarTarifa)
                                .addGap(29, 29, 29)
                                .addComponent(btn_actualizar))
                            .addComponent(lbl_cambio))
                        .addContainerGap())
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
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbl_impuesto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_diferencia2)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_propietario)
                                    .addComponent(cmb_convenios, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmb_tarifas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_diferencia)
                                    .addComponent(txt_placa, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmb_tipVehi, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_fechaIngreso)
                                    .addComponent(lbl_fechaSalida)
                                    .addComponent(lbl_totalAPagar)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbl_noParq)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmb_parqVisitantesFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(12, 12, 12)
                                .addComponent(lbl_imgEditUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30))))))
            .addGroup(layout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addComponent(txt_efectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(lbl_noParq)
                            .addComponent(cmb_parqVisitantesFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_impuesto)
                    .addComponent(jLabel16)
                    .addComponent(lbl_diferencia2))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_actualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_estimarTarifa, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    //Metodo boton Actualizar
    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
                             
        //Hacemos la estimacion al convenio o ala tarifa seleccionada, para asi generar el nuevo monto a pagar y demas datos de cobro de factura
        estimarConvenioOTarifa();
        
        Parqueadero parqSeleccionado = new Parqueadero();
        
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
            txt_efectivo.setText("0");
        }
        
        if (validacion == 0) {
            
            if(seRealizaronValidaciones == true){
                
                JOptionPane.showMessageDialog(null, "Validaciones realizadas satisfactoriamente.");
                efectivo = facturaControla.agregarFormatoMoneda(efectivo);
            
                //Encapsulamos el objeto factura a actualizar
                facturaAActualizar.setId(ID);
                facturaAActualizar.setPlaca(placa);
                facturaAActualizar.setPropietario(dueño);
                facturaAActualizar.setClaseDeVehiculo(tipoVehi_string);
                
                //El vehiculo analizado ya existe en el sistema y el ingresado tambien existe
                if(vehiculoDelBackupExiste == true && ingresoDesconocido == false){
                    
                    //Modificamos el id del parqueadero por el del vehiculo ingresado en el objeto factura
                    facturaAActualizar.setId_parqueadero(parqControla.consultarIdParqueadero(no_parq));
                                        
                }
                
                //El vehiculo analizado no existe en el sistema y el ingresado tampoco existe
                else if(vehiculoDelBackupExiste == false && ingresoDesconocido == true){
                    
                    parqSeleccionado = (Parqueadero)cmb_parqVisitantesFinal.getSelectedItem();
                             
                    //Validamos el verdadero id del convenio y de la tarifa en bd y lo editamos en el objeto factura
                    int idRealDelParqSeleccionado = parqControla.consultarIdParqueadero(parqSeleccionado.getNombre());
                    facturaAActualizar.setId_parqueadero(idRealDelParqSeleccionado);
                                         
                }                
                
                //El vehiculo analizado ya existe en el sistema pero el ingresado es desconocido
                if(vehiculoDelBackupExiste == true && ingresoDesconocido == true){
                    
                    parqSeleccionado = (Parqueadero)cmb_parqVisitantesFinal.getSelectedItem();
                             
                    //Validamos el verdadero id del convenio y de la tarifa en bd
                    int idRealDelParqSeleccionado = parqControla.consultarIdParqueadero(parqSeleccionado.getNombre());
                    facturaAActualizar.setId_parqueadero(idRealDelParqSeleccionado);
                                                
                }               
                
                //El vehiculo analizado no existe en el sistema pero el ingresado es conocido
                else if(vehiculoDelBackupExiste == false && ingresoDesconocido == false){
                    
                    //Modificamos el id del parqueadero por el del vehiculo ingresado en el objeto factura
                    facturaAActualizar.setId_parqueadero(parqControla.consultarIdParqueadero(no_parq));
                    
                }
                
                facturaAActualizar.setFacturadoPor(usuario);
                facturaAActualizar.setId_convenio(idBDConvenioAAplicar);
                facturaAActualizar.setId_tarifa(idBDTarifaAAplicar);
                facturaAActualizar.setDiferencia(diferencia);
                facturaAActualizar.setImpuesto(lbl_impuesto.getText());
                facturaAActualizar.setValorAPagar(valorPagar);
                facturaAActualizar.setEfectivo(efectivo);
                facturaAActualizar.setCambio(cambio);
                
                //Capturamos el id del cierre al que pertenece la factura
                int idCierreFctraEditada = facturaControla.obtenerIdCierreConCodigoFactura(factura_actualizada);

                if(idCierreFctraEditada != 1){
                   facturaControla.descontarFacturaDeUnCierre(factura_actualizada);
                   facturaControla.agregarFacturaActualizadaAUnCierre(valorPagar, idCierreFctraEditada);

                   //Actualizamos la factura
                   facturaControla.actualizarFacturaSalida(facturaAActualizar);
                }else{
                    //Actualizamos la factura
                    facturaControla.actualizarFacturaSalida(facturaAActualizar);
                }   
                
                //Aqui modificamos la fila existente y que fue seleccionada en la tabla gestionar facturas
                Object Fila[] = new Object[4];
                Fila[0] = codigo_factura;
                Fila[1] = facturaControla.fecha_de_factura();
                Fila[2] = usuario;
                Fila[3] = valorPagar;

                for(int i=0; i < tablafacturas.getColumnCount(); i++){
                    modelo.setValueAt(Fila[i], filas, i);
                }

                JOptionPane.showMessageDialog(null, "Factura actualizada satisfactoriamente.");
                this.dispose();
                new InformacionFacturaFinal().setVisible(true);
                
            }else{
                 JOptionPane.showMessageDialog(null, "Presione la tecla F1 para continuar.");
                 txt_placa.requestFocus();
            }
        
        }else{
            JOptionPane.showMessageDialog(null, "Debes llenar todos los campos.");
            Normalizar();
        } 
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void txt_placaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_placaKeyTyped
        
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 6;
        char validar = evt.getKeyChar();
        if(txt_placa.getText().length()== numeroCaracteres && !Character.isISOControl(validar)){
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

    }//GEN-LAST:event_txt_placaFocusLost

    private void txt_placaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_placaKeyPressed
        eventosDelFormulario(evt);
    }//GEN-LAST:event_txt_placaKeyPressed

    private void cmb_tipVehiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_tipVehiItemStateChanged
      
    }//GEN-LAST:event_cmb_tipVehiItemStateChanged

    private void txt_propietarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_propietarioKeyPressed
       eventosDelFormulario(evt);
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

    }//GEN-LAST:event_cmb_conveniosItemStateChanged
  
    private void cmb_tarifasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_tarifasItemStateChanged

    }//GEN-LAST:event_cmb_tarifasItemStateChanged

    private void txt_efectivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_efectivoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            calcularVueltas(txt_efectivo.getText());
        }
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            recargarInfoOriginal();
            estimarConvenioOTarifa();
            txt_placa.requestFocus();
        }
    }//GEN-LAST:event_txt_efectivoKeyPressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        cerrarEdicionDeFacturaFinal();
    }//GEN-LAST:event_formWindowClosing

    private void txt_efectivoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_efectivoKeyTyped
        //Evalua que se digiten numeros no letras
        char validar = evt.getKeyChar();
        
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(rootPane, "Ingrese solo numeros.");
        }
    }//GEN-LAST:event_txt_efectivoKeyTyped

    private void btn_estimarTarifaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_estimarTarifaActionPerformed
        estimarConvenioOTarifa();
    }//GEN-LAST:event_btn_estimarTarifaActionPerformed

    private void txt_efectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_efectivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_efectivoActionPerformed

    private void cmb_parqVisitantesFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_parqVisitantesFinalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_parqVisitantesFinalActionPerformed

    private void cmb_tipVehiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmb_tipVehiKeyPressed
        eventosDelFormulario(evt);
    }//GEN-LAST:event_cmb_tipVehiKeyPressed

    private void cmb_conveniosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmb_conveniosKeyPressed
        eventosDelFormulario(evt);
    }//GEN-LAST:event_cmb_conveniosKeyPressed

    private void cmb_tarifasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmb_tarifasKeyPressed
        eventosDelFormulario(evt);
    }//GEN-LAST:event_cmb_tarifasKeyPressed

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
    private javax.swing.JButton btn_estimarTarifa;
    private javax.swing.JComboBox<String> cmb_convenios;
    public static javax.swing.JComboBox<String> cmb_parqVisitantesFinal;
    private javax.swing.JComboBox<String> cmb_tarifas;
    private javax.swing.JComboBox<String> cmb_tipVehi;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JLabel lbl_diferencia2;
    private javax.swing.JLabel lbl_fechaIngreso;
    private javax.swing.JLabel lbl_fechaSalida;
    private javax.swing.JLabel lbl_imgEditUsuario;
    private javax.swing.JLabel lbl_impuesto;
    private javax.swing.JLabel lbl_noParq;
    private javax.swing.JLabel lbl_totalAPagar;
    private javax.swing.JTextField txt_efectivo;
    private javax.swing.JTextField txt_placa;
    private javax.swing.JTextField txt_propietario;
    // End of variables declaration//GEN-END:variables

    //Metodo que se invoca al cerrar el jFrame
    private void cerrarEdicionDeFacturaFinal(){
        
        String botones[] = {"Si", "No"};
        int eleccion = JOptionPane.showOptionDialog(this, "¿Está seguro que desea cerrar?", "Editar factura", 0, 3, null, botones, this);
        
        if(eleccion == JOptionPane.YES_OPTION){
            dispose();
            new InformacionFacturaFinal().setVisible(true);
        }
    }
    
    //Metodo que estima el convenio o la tarifa seleccionada a la factura a editar
    private void estimarConvenioOTarifa(){
        
        //Traemos la fecha en la que ingreso el vehiculo y la fecha en la que salió y las convertimos a Date y luego a Calendar
        String str_fechaIngresoVehiculo = facturaAEditar.getFechaDeIngresoVehiculo();
        Date fechaDeIngreso = facturaControla.convertidorDeFechasADate(str_fechaIngresoVehiculo); 
        Calendar calendar_fechaIngreso = facturaControla.convertidorDeFechasDeDateACalendar(fechaDeIngreso);
        String efectivo = txt_efectivo.getText();

        String str_fechaSalidaVehiculo = facturaAEditar.getFechaDeSalidaVehiculo();
        Date fechaDeSalida = facturaControla.convertidorDeFechasADate(str_fechaSalidaVehiculo);            
        
        //Obtenemos los nombres dela tarifa y el convenio escogidos para la consulta de su id en base de datos
        Convenio convSeleccionado = new Convenio();
        Tarifa tarifSeleccionada = new Tarifa();
        
        convSeleccionado = (Convenio)cmb_convenios.getSelectedItem();
        tarifSeleccionada = (Tarifa)cmb_tarifas.getSelectedItem();
                      
        idBDTarifaAAplicar = tarifaControla.consultarIdDeunaTarifa(tarifSeleccionada.getNombreTarifa());
        idBDConvenioAAplicar = convControla.consultarIdDeunConvenio(convSeleccionado.getNombre());              
        
        //Traemos el objeto tarifa que se va a aplicar
        tarifaACobrar = tarifaControla.consultarUnaTarifaMedianteID(idBDTarifaAAplicar);

        //Traemos el objeto convenio que se va a aplicar
        convenioAAplicar = convControla.consultarUnConvenioMedianteID(idBDConvenioAAplicar);
        
        //Validamos si la tarifa se encuentra anulada, si es asi, no generará cobro alguno
        String tarifaEstaAnulada = tarifaACobrar.getTarifaAnulada();
        if(tarifaEstaAnulada.equals("Si")){
            lbl_diferencia.setText("N/A");
            lbl_totalAPagar.setText("0");
            txt_efectivo.setText("");
            lbl_cambio.setText(efectivo);

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
                txt_efectivo.setText("0");
                lbl_cambio.setText(efectivo);

            }else if(!convenioAAplicar.getNombre().equals("NINGUNO") && tarifaACobrar.getNombreTarifa().equals("NINGUNA")){
                lbl_diferencia.setText("N/A");
                lbl_totalAPagar.setText("0");
                txt_efectivo.setText("0");
                lbl_cambio.setText(efectivo);

            }else if(convenioAAplicar.getNombre().equals("NINGUNO") && !tarifaACobrar.getNombreTarifa().equals("NINGUNA")){

                long long_montoTarifa = Long.parseLong(montoDeTarifa);
                long diferencia = 0;
                long diferenciaAntesDeDescuento = 0;
                String descuento_str;
                long descuento = 0;
                String dif_str = "";
                String montoAPagar = "";

                //Calculamos la diferencia el milisegundo que existe entre la fecha de ingreso y la fecha desalida del vehiculo
                diferenciaDeFechasEnMilisegundos = facturaControla.calcularDiferenciaDeFechasEnMilisegundos(calendar_fechaIngreso, fechaDeSalida);

                //Evaluamos la frecuencia de la tarifa a aplicar
                if(frecuenciaTarifa.equals("MINUTO")){

                    diferencia = TimeUnit.MILLISECONDS.toMinutes(diferenciaDeFechasEnMilisegundos);

                    if(aplicarDescuento.equals("Si")){
                        descuento_str = tarifaACobrar.getTiempoDelDescuento();
                        descuento = Long.parseLong(descuento_str);
                        diferencia = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento); 
                        dif_str = Long.toString(diferencia) + " minutos";
                        lbl_diferencia.setText(dif_str);   
                        lbl_diferencia.setVisible(true);

                        //Damos formato de moneda al monto a pagar
                        montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferencia);
                        montoAPagar = facturaControla.agregarFormatoMoneda(montoAPagarParaCalculoPago);
                        lbl_totalAPagar.setText(montoAPagar);
                        lbl_totalAPagar.setVisible(true);   

                    }else{
                        dif_str = Long.toString(diferencia);
                        lbl_diferencia.setText(dif_str + " minutos");
                        lbl_diferencia.setVisible(true);

                       //Damos formato de moneda al monto a pagar
                       montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferencia);
                        montoAPagar = facturaControla.agregarFormatoMoneda(facturaControla.calcularPago(long_montoTarifa, diferencia));
                        lbl_totalAPagar.setText(montoAPagar);
                        lbl_totalAPagar.setVisible(true); 
                    }
                    
                    //Actualizamos el cambio a dar de acuerdo con el monto dado por el usuario con anterioridad para la factura
                    if(!efectivo.equals("0") || !efectivo.equals("")){
                        calcularVueltas(efectivo);
                    }

                }else if(frecuenciaTarifa.equals("HORA")){

                    diferencia = TimeUnit.MILLISECONDS.toHours(diferenciaDeFechasEnMilisegundos);
                    long diferenciaEnMin = TimeUnit.MILLISECONDS.toMinutes(diferenciaDeFechasEnMilisegundos);

                    if(aplicarDescuento.equals("Si")){
                        descuento_str = tarifaACobrar.getTiempoDelDescuento();
                        descuento = Long.parseLong(descuento_str);
                        diferenciaAntesDeDescuento = diferencia;

                        //Solo calcula el descuento si la diferencia de fechas es superior a cero, de lo contrario no
                        if(diferenciaAntesDeDescuento > 0){

                            if(diferenciaAntesDeDescuento == descuento){

                                diferencia = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento);

                                //Convertimos la hora de descuento en minutos
                                long descuentoEnMin = facturaControla.convertirHorasAMinutos(descuento);

                                //Hallamos la diferencia en minutos final entre la diferencia de fechas en minutos y el descuento en minutos y la convertimos a milisegundos
                                long diferenciaRealistaEnMinutos = diferenciaEnMin - descuentoEnMin;
                                diferenciaDeFechasEnMilisegundos = facturaControla.convertirDeMinutosAMilisegundos(diferenciaEnMin, diferenciaDeFechasEnMilisegundos, diferenciaRealistaEnMinutos);

                                dif_str = "0 horas";
                                lbl_diferencia.setText(dif_str);   
                                lbl_diferencia.setVisible(true);

                            }else{
                                diferencia = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento);
                                diferenciaDeFechasEnMilisegundos = facturaControla.aplicarDescuentoADiferenciaEnMilisegundos(diferenciaDeFechasEnMilisegundos, diferenciaAntesDeDescuento, diferencia);
                                dif_str = Long.toString(diferencia) + " horas";
                                lbl_diferencia.setText(dif_str);   
                                lbl_diferencia.setVisible(true);
                            }      
                        }else{
                            dif_str = Long.toString(diferencia) + " horas";
                            lbl_diferencia.setText(dif_str);   
                            lbl_diferencia.setVisible(true);
                        }        

                    }else{                   
                        dif_str = Long.toString(diferencia) + " horas";
                        lbl_diferencia.setText(dif_str);
                        lbl_diferencia.setVisible(true); 
                    }            

                    if(aplicarCostoAdicional.equals("Si") && aplicarDescuento.equals("No")){

                        dif_str = diferencia + facturaControla.calcularPagoTeniendoEnCuentaMinutosUtilizados(long_montoTarifa, diferencia, tarifaACobrar, diferenciaDeFechasEnMilisegundos);
                        lbl_diferencia.setText(dif_str);
                        lbl_diferencia.setVisible(true);
                        montoAPagarParaCalculoPago = facturaControla.quitarFormatoMoneda(facturaControla.obtenervalorAPagarPorDiferenciaAdicional());
                        //Mostramos el total a pagar en pantalla
                        lbl_totalAPagar.setText(facturaControla.obtenervalorAPagarPorDiferenciaAdicional());
                        lbl_totalAPagar.setVisible(true);

                    }else if(aplicarCostoAdicional.equals("Si") && aplicarDescuento.equals("Si")){    

                        if(diferenciaAntesDeDescuento >= descuento){
                            dif_str = diferencia + facturaControla.calcularPagoTeniendoEnCuentaMinutosUtilizados(long_montoTarifa, diferencia, tarifaACobrar, diferenciaDeFechasEnMilisegundos);
                            lbl_diferencia.setText(dif_str);
                            lbl_diferencia.setVisible(true);
                            montoAPagarParaCalculoPago = facturaControla.quitarFormatoMoneda(facturaControla.obtenervalorAPagarPorDiferenciaAdicional());
                            lbl_totalAPagar.setText(facturaControla.obtenervalorAPagarPorDiferenciaAdicional());
                            lbl_totalAPagar.setVisible(true);
                        }else{
                            lbl_totalAPagar.setText("$0,00");
                            lbl_totalAPagar.setVisible(true);
                        }

                    }else{
                        //Damos formato de moneda al monto a pagar
                        montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferencia);
                        montoAPagar = facturaControla.agregarFormatoMoneda(montoAPagarParaCalculoPago);
                        lbl_totalAPagar.setText(montoAPagar);
                        lbl_totalAPagar.setVisible(true); 
                    }
                    
                    //Actualizamos el cambio a dar de acuerdo con el monto dado por el usuario con anterioridad para la factura
                    if(!efectivo.equals("0") || !efectivo.equals("")){
                        calcularVueltas(efectivo);
                    }

                }else if(frecuenciaTarifa.equals("DIA")){

                    diferencia = TimeUnit.MILLISECONDS.toDays(diferenciaDeFechasEnMilisegundos);
                    long diferenciaEnHrs = TimeUnit.MILLISECONDS.toDays(diferenciaDeFechasEnMilisegundos);

                    if(aplicarDescuento.equals("Si")){
                        descuento_str = tarifaACobrar.getTiempoDelDescuento();
                        descuento = Long.parseLong(descuento_str);
                        diferenciaAntesDeDescuento = diferencia;

                        //Solo calcula el descuento si la diferencia de fechas es superior a cero, de lo contrario no
                        if(diferenciaAntesDeDescuento > 0){

                            if(diferenciaAntesDeDescuento == descuento){

                                diferencia = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento);

                                //Convertimos el dia de descuento en horas
                                long descuentoEnHrs = facturaControla.convertirDiasAHoras(descuento);

                                //Hallamos la diferencia en horas final entre la diferencia de fechas en horas y el descuento en horas y lo convertimos a milisegundos
                                long diferenciaRealistaEnHoras = diferenciaEnHrs - descuentoEnHrs;
                                diferenciaDeFechasEnMilisegundos = facturaControla.convertirDeHorasAMilisegundos(diferenciaEnHrs, diferenciaDeFechasEnMilisegundos, diferenciaRealistaEnHoras);

                                dif_str = "0 días";
                                lbl_diferencia.setText(dif_str);
                                lbl_diferencia.setVisible(true);

                            }else{
                                diferencia = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento);
                                diferenciaDeFechasEnMilisegundos = facturaControla.aplicarDescuentoADiferenciaEnMilisegundos(diferenciaDeFechasEnMilisegundos, diferenciaAntesDeDescuento, diferencia);
                                dif_str = Long.toString(diferencia) + " días";
                                lbl_diferencia.setText(dif_str);
                                lbl_diferencia.setVisible(true);
                            }
                        }else{
                            dif_str = Long.toString(diferencia) + " días";
                            lbl_diferencia.setText(dif_str);   
                            lbl_diferencia.setVisible(true);
                        }

                    }else{                   
                        dif_str = Long.toString(diferencia) + " días";
                        lbl_diferencia.setText(dif_str);
                        lbl_diferencia.setVisible(true); 
                    }

                    //Damos formato de moneda al monto a pagar
                    montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferencia);
                    montoAPagar = facturaControla.agregarFormatoMoneda(montoAPagarParaCalculoPago);
                    lbl_totalAPagar.setText(montoAPagar);
                    lbl_totalAPagar.setVisible(true);
                    
                    //Actualizamos el cambio a dar de acuerdo con el monto dado por el usuario con anterioridad para la factura
                    if(!efectivo.equals("0") || !efectivo.equals("")){
                        calcularVueltas(efectivo);
                    }
                }
            }else if(!convenioAAplicar.getNombre().equals("NINGUNO") && !tarifaACobrar.getNombreTarifa().equals("NINGUNA")){
                //En este caso, asi tenga un convenio asignado, si la tarifa no es ninguna, predominará la tarifa    
                long long_montoTarifa = Long.parseLong(montoDeTarifa);
                long diferencia = 0;
                long diferenciaAntesDeDescuento = 0;
                String descuento_str;
                long descuento = 0;
                String dif_str = "";
                String montoAPagar = "";

                //Calculamos la diferencia el milisegundo que existe entre la fecha de ingreso y la fecha desalida del vehiculo
                diferenciaDeFechasEnMilisegundos = facturaControla.calcularDiferenciaDeFechasEnMilisegundos(calendar_fechaIngreso, fechaDeSalida);

                //Evaluamos la frecuencia de la tarifa a aplicar
                if(frecuenciaTarifa.equals("MINUTO")){

                    diferencia = TimeUnit.MILLISECONDS.toMinutes(diferenciaDeFechasEnMilisegundos);

                    if(aplicarDescuento.equals("Si")){
                        descuento_str = tarifaACobrar.getTiempoDelDescuento();
                        descuento = Long.parseLong(descuento_str);
                        diferencia = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento); 
                        dif_str = Long.toString(diferencia) + " minutos";
                        lbl_diferencia.setText(dif_str);   
                        lbl_diferencia.setVisible(true);

                        //Damos formato de moneda al monto a pagar
                        montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferencia);
                        montoAPagar = facturaControla.agregarFormatoMoneda(montoAPagarParaCalculoPago);
                        lbl_totalAPagar.setText(montoAPagar);
                        lbl_totalAPagar.setVisible(true);   

                    }else{
                        dif_str = Long.toString(diferencia);
                        lbl_diferencia.setText(dif_str + " minutos");
                        lbl_diferencia.setVisible(true);

                       //Damos formato de moneda al monto a pagar
                       montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferencia);
                        montoAPagar = facturaControla.agregarFormatoMoneda(facturaControla.calcularPago(long_montoTarifa, diferencia));
                        lbl_totalAPagar.setText(montoAPagar);
                        lbl_totalAPagar.setVisible(true); 
                    }
                    
                    //Actualizamos el cambio a dar de acuerdo con el monto dado por el usuario con anterioridad para la factura
                    if(!efectivo.equals("0") || !efectivo.equals("")){
                        calcularVueltas(efectivo);
                    }

                }else if(frecuenciaTarifa.equals("HORA")){

                    diferencia = TimeUnit.MILLISECONDS.toHours(diferenciaDeFechasEnMilisegundos);
                    long diferenciaEnMin = TimeUnit.MILLISECONDS.toMinutes(diferenciaDeFechasEnMilisegundos);

                    if(aplicarDescuento.equals("Si")){
                        descuento_str = tarifaACobrar.getTiempoDelDescuento();
                        descuento = Long.parseLong(descuento_str);
                        diferenciaAntesDeDescuento = diferencia;

                        //Solo calcula el descuento si la diferencia de fechas es superior a cero, de lo contrario no
                        if(diferenciaAntesDeDescuento > 0){

                            if(diferenciaAntesDeDescuento == descuento){

                                diferencia = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento);

                                //Convertimos la hora de descuento en minutos
                                long descuentoEnMin = facturaControla.convertirHorasAMinutos(descuento);

                                //Hallamos la diferencia en minutos final entre la diferencia de fechas en minutos y el descuento en minutos y la convertimos a milisegundos
                                long diferenciaRealistaEnMinutos = diferenciaEnMin - descuentoEnMin;
                                diferenciaDeFechasEnMilisegundos = facturaControla.convertirDeMinutosAMilisegundos(diferenciaEnMin, diferenciaDeFechasEnMilisegundos, diferenciaRealistaEnMinutos);

                                dif_str = "0 horas";
                                lbl_diferencia.setText(dif_str);   
                                lbl_diferencia.setVisible(true);

                            }else{
                                diferencia = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento);
                                diferenciaDeFechasEnMilisegundos = facturaControla.aplicarDescuentoADiferenciaEnMilisegundos(diferenciaDeFechasEnMilisegundos, diferenciaAntesDeDescuento, diferencia);
                                dif_str = Long.toString(diferencia) + " horas";
                                lbl_diferencia.setText(dif_str);   
                                lbl_diferencia.setVisible(true);
                            }      
                        }else{
                            dif_str = Long.toString(diferencia) + " horas";
                            lbl_diferencia.setText(dif_str);   
                            lbl_diferencia.setVisible(true);
                        }        

                    }else{                   
                        dif_str = Long.toString(diferencia) + " horas";
                        lbl_diferencia.setText(dif_str);
                        lbl_diferencia.setVisible(true); 
                    }            

                    if(aplicarCostoAdicional.equals("Si") && aplicarDescuento.equals("No")){

                        dif_str = diferencia + facturaControla.calcularPagoTeniendoEnCuentaMinutosUtilizados(long_montoTarifa, diferencia, tarifaACobrar, diferenciaDeFechasEnMilisegundos);
                        lbl_diferencia.setText(dif_str);
                        lbl_diferencia.setVisible(true);
                        montoAPagarParaCalculoPago = facturaControla.quitarFormatoMoneda(facturaControla.obtenervalorAPagarPorDiferenciaAdicional());
                        //Mostramos el total a pagar en pantalla
                        lbl_totalAPagar.setText(facturaControla.obtenervalorAPagarPorDiferenciaAdicional());
                        lbl_totalAPagar.setVisible(true);

                    }else if(aplicarCostoAdicional.equals("Si") && aplicarDescuento.equals("Si")){    

                        if(diferenciaAntesDeDescuento >= descuento){
                            dif_str = diferencia + facturaControla.calcularPagoTeniendoEnCuentaMinutosUtilizados(long_montoTarifa, diferencia, tarifaACobrar, diferenciaDeFechasEnMilisegundos);
                            lbl_diferencia.setText(dif_str);
                            lbl_diferencia.setVisible(true);
                            montoAPagarParaCalculoPago = facturaControla.quitarFormatoMoneda(facturaControla.obtenervalorAPagarPorDiferenciaAdicional());
                            lbl_totalAPagar.setText(facturaControla.obtenervalorAPagarPorDiferenciaAdicional());
                            lbl_totalAPagar.setVisible(true);
                        }else{
                            lbl_totalAPagar.setText("$0,00");
                            lbl_totalAPagar.setVisible(true);
                        }

                    }else{
                        //Damos formato de moneda al monto a pagar
                        montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferencia);
                        montoAPagar = facturaControla.agregarFormatoMoneda(montoAPagarParaCalculoPago);
                        lbl_totalAPagar.setText(montoAPagar);
                        lbl_totalAPagar.setVisible(true); 
                    }
                    
                    //Actualizamos el cambio a dar de acuerdo con el monto dado por el usuario con anterioridad para la factura
                    if(!efectivo.equals("0") || !efectivo.equals("")){
                        calcularVueltas(efectivo);
                    }

                }else if(frecuenciaTarifa.equals("DIA")){

                    diferencia = TimeUnit.MILLISECONDS.toDays(diferenciaDeFechasEnMilisegundos);
                    long diferenciaEnHrs = TimeUnit.MILLISECONDS.toDays(diferenciaDeFechasEnMilisegundos);

                    if(aplicarDescuento.equals("Si")){
                        descuento_str = tarifaACobrar.getTiempoDelDescuento();
                        descuento = Long.parseLong(descuento_str);
                        diferenciaAntesDeDescuento = diferencia;

                        //Solo calcula el descuento si la diferencia de fechas es superior a cero, de lo contrario no
                        if(diferenciaAntesDeDescuento > 0){

                            if(diferenciaAntesDeDescuento == descuento){

                                diferencia = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento);

                                //Convertimos el dia de descuento en horas
                                long descuentoEnHrs = facturaControla.convertirDiasAHoras(descuento);

                                //Hallamos la diferencia en horas final entre la diferencia de fechas en horas y el descuento en horas y lo convertimos a milisegundos
                                long diferenciaRealistaEnHoras = diferenciaEnHrs - descuentoEnHrs;
                                diferenciaDeFechasEnMilisegundos = facturaControla.convertirDeHorasAMilisegundos(diferenciaEnHrs, diferenciaDeFechasEnMilisegundos, diferenciaRealistaEnHoras);

                                dif_str = "0 días";
                                lbl_diferencia.setText(dif_str);
                                lbl_diferencia.setVisible(true);

                            }else{
                                diferencia = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento);
                                diferenciaDeFechasEnMilisegundos = facturaControla.aplicarDescuentoADiferenciaEnMilisegundos(diferenciaDeFechasEnMilisegundos, diferenciaAntesDeDescuento, diferencia);
                                dif_str = Long.toString(diferencia) + " días";
                                lbl_diferencia.setText(dif_str);
                                lbl_diferencia.setVisible(true);
                            }
                        }else{
                            dif_str = Long.toString(diferencia) + " días";
                            lbl_diferencia.setText(dif_str);   
                            lbl_diferencia.setVisible(true);
                        }

                    }else{                   
                        dif_str = Long.toString(diferencia) + " días";
                        lbl_diferencia.setText(dif_str);
                        lbl_diferencia.setVisible(true); 
                    }

                    //Damos formato de moneda al monto a pagar
                    montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferencia);
                    montoAPagar = facturaControla.agregarFormatoMoneda(montoAPagarParaCalculoPago);
                    lbl_totalAPagar.setText(montoAPagar);
                    lbl_totalAPagar.setVisible(true);
                    
                    //Actualizamos el cambio a dar de acuerdo con el monto dado por el usuario con anterioridad para la factura
                    if(!efectivo.equals("0") || !efectivo.equals("")){
                        calcularVueltas(efectivo);
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
        
        if(placa.length() > 0 && placa.length() < 6){
            JOptionPane.showMessageDialog(null, "Placa no válida.");
            txt_placa.setText(placa_back);
        }else{
            boolean vehiculoYaExisteEnSistema = vehiControla.evaluarExistenciaDelVehiculo(placa);
             
            if(vehiculoYaExisteEnSistema == true){
                 
                String botones[] = {"Si", "No"};
                int eleccion = JOptionPane.showOptionDialog(this, "La placa ingresada corresponde a un vehiculo previamente registrado en el sistema ¿Desea continuar?", "Mensaje", 0, 3, null, botones, this);

                if(eleccion == JOptionPane.YES_OPTION){
                    Vehiculo infoVehiculo = vehiControla.consultarInformacionDeUnVehiculo(placa);
                                        
                    txt_propietario.setText(infoVehiculo.getPropietario());
                    cmb_tipVehi.setSelectedItem(infoVehiculo.getClase());
                    lbl_noParq.setText(parqControla.consultarNombreDeParqueaderoMedianteID(infoVehiculo.getId_parqueadero()));
                    cmb_convenios.setSelectedIndex(buscarConvenioEnComboBox(convControla.consultarNombreDeConvenioMedianteID(infoVehiculo.getId_convenio())));
                    cmb_tarifas.setSelectedIndex(buscarTarifaEnComboBox(tarifaControla.consultarNombreDeTarifaMedianteID(infoVehiculo.getId_tarifa())));
                                       
                    txt_placa.setEditable(false);
                    txt_propietario.setEditable(false);
                    cmb_tipVehi.setEnabled(false);
                    cmb_convenios.setEnabled(false);
                    cmb_tarifas.setEnabled(false);
                    lbl_noParq.setVisible(true);
                    cmb_parqVisitantesFinal.setVisible(false);
                                    
                }else{
                    txt_placa.setText(placa_back);
                    seRealizaronValidaciones = false;
                }
            
            }else{                    
                JOptionPane.showMessageDialog(null, "Vehiculo desconocido.");
                ingresoDesconocido = true;
                parqControla.ejecutarHiloParqueaderosVisitantesDisponiblesEditarFacturaFinal();
                txt_propietario.setText("");
                cmb_tipVehi.setSelectedIndex(0);
                lbl_noParq.setText("");
                lbl_noParq.setVisible(false);
                cmb_parqVisitantesFinal.setVisible(true);
                cmb_convenios.setSelectedIndex(0);
                cmb_tarifas.setSelectedIndex(0);

                txt_placa.setEditable(true);
                txt_propietario.setEditable(true);
                cmb_tipVehi.setEnabled(true);
                cmb_convenios.setEnabled(true);
                cmb_tarifas.setEnabled(true);
               
            }   
        }
    }
    
    //Metodo que se encarga de recargar la informacion original de la factura
    public void recargarInfoOriginal(){
        
        txt_placa.setText(placa_back);
        txt_propietario.setText(propietario_back);
        cmb_tipVehi.setSelectedItem(tipVehi_back);
        lbl_noParq.setText(parqueaderoBack);
        cmb_convenios.setSelectedIndex(buscarConvenioEnComboBox(convControla.consultarNombreDeConvenioMedianteID(convenioBack)));
        cmb_tarifas.setSelectedIndex(buscarTarifaEnComboBox(tarifaControla.consultarNombreDeTarifaMedianteID(tarifaBack)));
        txt_efectivo.setText(efectivo_back);
        cmb_parqVisitantesFinal.setVisible(false);
        lbl_noParq.setVisible(true);
        txt_placa.setEditable(true);
        txt_propietario.setEditable(true);
        cmb_tipVehi.setEnabled(true);
        cmb_convenios.setEnabled(true);
        cmb_tarifas.setEnabled(true);
        
        
       if(vehiculoDelBackupExiste == true){
            txt_placa.setEditable(true);
            txt_propietario.setEditable(false);
            cmb_tipVehi.setEnabled(false);
            cmb_convenios.setEnabled(false);
            cmb_tarifas.setEnabled(false);
            lbl_noParq.setVisible(true);
       }else{
           txt_placa.setEditable(true);
            txt_propietario.setEditable(true);
            cmb_tipVehi.setEnabled(true);
            cmb_convenios.setEnabled(true);
            cmb_tarifas.setEnabled(true);
            lbl_noParq.setVisible(true);
       }
        
        estimarConvenioOTarifa();
      
    }
    
    //Metodo que se encarga de buscar una tarifa en el combobox mediante su id
    public int buscarTarifaEnComboBox(String nom_tarifa){
        //Iteramos el combobox en busca del nomTarifa que tiene la factura previamente registrada para asi obtener su verdadero id
        int idVerdaderoDeTarif = 0;
        int tamañoArregloTarifas = Tarifa.listadoNombresTarifa.size();
        for(int i=0; i<tamañoArregloTarifas; i++){
           String nomTarif = Tarifa.listadoNombresTarifa.get(i);
           if(nom_tarifa.equals(nomTarif)){
               idVerdaderoDeTarif = (tamañoArregloTarifas - i) - 1;
           }
        }
        return idVerdaderoDeTarif;
    }
    
    //Metodo que se encarga de buscar un convenio en el combobox mediante su id
    public int buscarConvenioEnComboBox(String nom_convenio){
        //Iteramos el combobox en busca del nomConvenio que tiene la factura previamente registrada para asi obtener su verdadero id
        int idVerdaderoDelConv = 0;
        int tamañoArregloConvenios = Convenio.listadoNombresConvenio.size();
        for(int i=0; i<tamañoArregloConvenios; i++){
           String nomConv = Convenio.listadoNombresConvenio.get(i);
           if(nom_convenio.equals(nomConv)){
               idVerdaderoDelConv = (tamañoArregloConvenios - i) - 1;
           }
        }
        return idVerdaderoDelConv;
    }
    
    //Metodo que contiene los eventos del formulario
    public void eventosDelFormulario(java.awt.event.KeyEvent evt){
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            recargarInfoOriginal();
            seRealizaronValidaciones = false;
            parqControla.detenerHilosParqueaderosVisitantesDisponiblesEdicionFacturas();
            ingresoDesconocido = false;
        }
        
        if(evt.getKeyCode() == KeyEvent.VK_F1){
            String placa = txt_placa.getText();
            if(!placa.equals(placa_back)){
                validacionesAntesDeActualizar();
            }
            
            seRealizaronValidaciones = true;
        }
    }
}


