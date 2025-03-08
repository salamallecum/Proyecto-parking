package vista;

import controlador.ConvenioControlador;
import controlador.FacturaControlador;
import controlador.ParametroControlador;
import controlador.ParqueaderoControlador;
import controlador.TarifaControlador;
import controlador.UsuarioControlador;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import controlador.VehiculoControlador;
import javax.swing.DefaultComboBoxModel;
import modelo.Convenio;
import modelo.Factura;
import modelo.Parqueadero;
import modelo.Tarifa;
import modelo.Vehiculo;
import org.apache.log4j.Logger;
import static vista.GestionarBicisyOtros.dueñoBiciUOtro;
import static vista.GestionarBicisyOtros.hayBiciUOtroEnEdicion;
import static vista.GestionarBicisyOtros.identificacion;
import static vista.GestionarBicisyOtros.tipoIdentificacion;
import static vista.GestionarBicisyOtros.tipoVehiculoBiciUOtro;


/**
 *
 * @author ALEJO
 */
public class EditarBicisYOtros extends javax.swing.JFrame{

    String biciYOtros_TipoIdentificacionActualizado="";
    String biciYOtros_NumIdentificacionActualizado="";
    String tipoIdentifBack;
    String numeroIdentifBack;
    String propietariaBack;
    String tipoVehiculoBack;
    String colorBack;
    int noParqueaderoBack;
    int convenioBack;
    int tarifaBack;
    String estaEnParqBack;
    String user;
       
    boolean laBiciUOtroEstaEnParqueadero = false;
    boolean laBiciUOtroTieneFacturaPrimerIngresoPrevRegistrada = false;
    int laBiciUOtroTieneFacturaAbierta;
    
    int ID;
    javax.swing.JTable tablaBicis;
    int FilaAnterior;
    DefaultTableModel modeloBicis;
    int seleccionParq;
   
    Vehiculo vehiculoConsultado = new Vehiculo(0, "", "", "", "", "", "", "", 0, 0, 0);
    Vehiculo vehiculoEditado = new Vehiculo(0, "", "", "", "", "", "", "", 0, 0, 0);
    Factura nuevaFactura = new Factura(0, "", "", 0, "", "", "", "", "", 0, 0, "", "", 0, 0, "", 0, "", "", "", "", "", "");
    Factura facturaEditada = new Factura(0, "", "", 0, "", "", "", "", "", 0, 0, "", "", 0, 0, "", 0, "", "", "", "", "", "");
    
    VehiculoControlador vehicontrolador = new VehiculoControlador();
    ParqueaderoControlador parqControla = new ParqueaderoControlador();
    ConvenioControlador convenioControla = new ConvenioControlador();
    TarifaControlador tarifaControla = new TarifaControlador();
    FacturaControlador facturaControla = new FacturaControlador();
    ParametroControlador parametroControla = new ParametroControlador();
    UsuarioControlador usuarioControla = new UsuarioControlador();
        
    //Declaramos los objetos  y se los aprovisionamos a su combobox
    Parqueadero parq = new Parqueadero();
    Convenio conv = new Convenio();
    Tarifa tarif = new Tarifa();
      
    private final Logger log = Logger.getLogger(EditarBicisYOtros.class);
    private URL url = EditarBicisYOtros.class.getResource("Log4j.properties");
    
    /**
     * Creates new form nuevoUsuario
     */
    public EditarBicisYOtros() {
        initComponents();
        biciYOtros_TipoIdentificacionActualizado = GestionarBicisyOtros.biciUOtroTipoIdentificacion_update;
        biciYOtros_NumIdentificacionActualizado = GestionarBicisyOtros.biciUOtroNumIdentificacion_update;
        tablaBicis = GestionarBicisyOtros.Table_listaBicisYOtros;
        user = Login.usuario;
        modeloBicis = GestionarBicisyOtros.modeloBicisUOtros;
        
        //Cargamos el proceso de actualizacion de qrs de carros y motos
        vehicontrolador.cargarProcesoDeActualizacionQRBicisYOtros();
        
        setSize(628,370);
        setResizable(false);
        setTitle("Editar bicicletas y otros");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        //Avisamos que esta ventana se encuentra abierta para que no deje cerrar sesion al usuario
        MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = true;
        
        DefaultComboBoxModel modeloParq = new DefaultComboBoxModel(parq.mostrarParqueaderosTipoResidente("'BICICLETA','PATINETA','OTRO'"));
        cmb_noParqueadero.setModel(modeloParq);
        parq.almacenarNombresParqueadero("'BICICLETA','PATINETA','OTRO'");
        
        DefaultComboBoxModel modeloConv = new DefaultComboBoxModel(conv.mostrarConveniosDisponibles());
        cmb_conveniosEditar.setModel(modeloConv);
        conv.almacenarNombresConvenio();

        DefaultComboBoxModel modeloTarif = new DefaultComboBoxModel(tarif.mostrarTarifasDisponibles());
        cmb_tarifasEditar.setModel(modeloTarif);
        tarif.almacenarNombresTarifa();
                               
        //Traemos el objeto tipo vehiculo con la info del vehiculo a editar
        vehiculoConsultado = vehicontrolador.consultarInformacionDeUnVehiculo(null, null, biciYOtros_TipoIdentificacionActualizado, biciYOtros_NumIdentificacionActualizado);
         
        //Colocamos la infromacion del objeto vehiculo en la interfaz
        ID = vehiculoConsultado.getId();
        tipoIdentifBack = vehiculoConsultado.getTipoIdentificacion();
        if(tipoIdentifBack.equals("CC")){
            cmb_tipoIdentificacionEditar.setSelectedIndex(1);
        }else if(tipoIdentifBack.equals("TI")){
            cmb_tipoIdentificacionEditar.setSelectedIndex(2);
        }else if(tipoIdentifBack.equals("RC")){
           cmb_tipoIdentificacionEditar.setSelectedIndex(3);
        }else if(tipoIdentifBack.equals("PA")){
            cmb_tipoIdentificacionEditar.setSelectedIndex(4);
        }else if(tipoIdentifBack.equals("TE")){
            cmb_tipoIdentificacionEditar.setSelectedIndex(5);
        }else if(tipoIdentifBack.equals("NIT")){
            cmb_tipoIdentificacionEditar.setSelectedIndex(6);
        }else if(tipoIdentifBack.equals("PP")){
            cmb_tipoIdentificacionEditar.setSelectedIndex(7);
        }else if(tipoIdentifBack.equals("DIE")){
            cmb_tipoIdentificacionEditar.setSelectedIndex(8);
        }
        
        numeroIdentifBack = vehiculoConsultado.getNumIdentificacion();
        txt_noIdentificacionEditar.setText(numeroIdentifBack);
        
        propietariaBack = vehiculoConsultado.getPropietario();
        txt_dueñoEditar.setText(propietariaBack);

        tipoVehiculoBack = vehiculoConsultado.getTipoVehiculo();
        cmb_tipoVehiculoEditar.setSelectedItem(tipoVehiculoBack);
        
        colorBack = vehiculoConsultado.getColor();
        txt_colorVehiculoEditar.setText(colorBack);

        noParqueaderoBack = vehiculoConsultado.getId_parqueadero();       
                
        //Buscamos el nombre del parqueadero que le pertenece a ese id para pintarlo en el combobox
        String nom_parqueadero = parqControla.consultarNombreDeParqueaderoMedianteID(noParqueaderoBack);    
                                     
        //Iteramos el combobox en busca del nomParqueadero que tiene el vehiculo previamente registrado para asi obtener su verdadero id
        int idVerdaderoDelParq = 0;
        int tamañoArregloParqueaderos = Parqueadero.listadoNombresParqueadero.size();
        for(int i=0; i<tamañoArregloParqueaderos; i++){
           String nomPark = Parqueadero.listadoNombresParqueadero.get(i);
           if(nom_parqueadero.equals(nomPark)){
               idVerdaderoDelParq = tamañoArregloParqueaderos - i;
           }
        }
       
        cmb_noParqueadero.setSelectedIndex(idVerdaderoDelParq);
        
        convenioBack = vehiculoConsultado.getId_convenio();
        //Buscamos el nombre del convenio que le pertenece a ese id para pintarlo en el combobox
        String nom_convenio = convenioControla.consultarNombreDeConvenioMedianteID(convenioBack);    
                                     
        //Iteramos el combobox en busca del nom_convenio que tiene el vehiculo previamente registrado para asi obtener su verdadero id
        int idVerdaderoDelConv = 0;
        int tamañoArregloConvenios = Convenio.listadoNombresConvenio.size();
        for(int i=0; i<tamañoArregloConvenios; i++){
           String nomConv = Convenio.listadoNombresConvenio.get(i);
           if(nom_convenio.equals(nomConv)){
               idVerdaderoDelConv = (tamañoArregloConvenios - i) - 1;
           }
        }
           
        cmb_conveniosEditar.setSelectedIndex(idVerdaderoDelConv);

        tarifaBack = vehiculoConsultado.getId_tarifa();
        //Buscamos el nombre de la tarifa que le pertenece a ese id para pintarlo en el combobox
        String nom_tarifa = tarifaControla.consultarNombreDeTarifaMedianteID(tarifaBack);    
                                     
        //Iteramos el combobox en busca del nom_tarifa que tiene el vehiculo previamente registrado para asi obtener su verdadero id
        int idVerdaderoDeTarifa = 0;
        int tamañoArregloTarifas = Tarifa.listadoNombresTarifa.size();
        for(int i=0; i<tamañoArregloTarifas; i++){
           String nomTarifa = Tarifa.listadoNombresTarifa.get(i);
           if(nom_tarifa.equals(nomTarifa)){
               idVerdaderoDeTarifa = (tamañoArregloTarifas - i) - 1;
           }
        }       
        
        cmb_tarifasEditar.setSelectedIndex(idVerdaderoDeTarifa);
        
        laBiciUOtroEstaEnParqueadero = vehicontrolador.verificarSiVehiculoEstaEnParqueadero(null, null, biciYOtros_TipoIdentificacionActualizado, biciYOtros_NumIdentificacionActualizado);
                
        if(laBiciUOtroEstaEnParqueadero){
            check_editEstaVehiculoEnParqueadero.setSelected(true);
            estaEnParqBack = "Si";
        }else{
            check_editEstaVehiculoEnParqueadero.setSelected(false);
            estaEnParqBack = "No";
        }        
        
        laBiciUOtroTieneFacturaAbierta = vehicontrolador.consultarSiVehiculoTieneFacturasAbiertas(ID, null, null, null);
        parqControla.liberarParqueadero(tipoVehiculoBack, null, tipoIdentifBack, numeroIdentifBack);
        vehicontrolador.liberarVehiculo(null, tipoIdentifBack, numeroIdentifBack);
            
    }
    
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/IcoCycle.png"));
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_dueñoEditar = new javax.swing.JTextField();
        btn_actualizar = new javax.swing.JButton();
        lbl_imgEditUsuario = new javax.swing.JLabel();
        cmb_noParqueadero = new javax.swing.JComboBox<>();
        cmb_conveniosEditar = new javax.swing.JComboBox<>();
        cmb_tarifasEditar = new javax.swing.JComboBox<>();
        check_editEstaVehiculoEnParqueadero = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_colorVehiculoEditar = new javax.swing.JTextField();
        txt_noIdentificacionEditar = new javax.swing.JTextField();
        cmb_tipoVehiculoEditar = new javax.swing.JComboBox<>();
        cmb_tipoIdentificacionEditar = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconImage(getIconImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Tipo de identificación:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Propietario:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Tipo de vehiculo:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("N° de parqueadero:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Convenio:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Tarifa:");

        txt_dueñoEditar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dueñoEditarKeyTyped(evt);
            }
        });

        btn_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh256_24854.png"))); // NOI18N
        btn_actualizar.setText("Actualizar");
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });

        lbl_imgEditUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_imgEditUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/EditCycle.png"))); // NOI18N

        cmb_noParqueadero.setAutoscrolls(true);
        cmb_noParqueadero.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_noParqueaderoItemStateChanged(evt);
            }
        });

        cmb_conveniosEditar.setAutoscrolls(true);
        cmb_conveniosEditar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_conveniosEditarItemStateChanged(evt);
            }
        });

        cmb_tarifasEditar.setAutoscrolls(true);

        check_editEstaVehiculoEnParqueadero.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        check_editEstaVehiculoEnParqueadero.setText("Está en parqueadero");
        check_editEstaVehiculoEnParqueadero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_editEstaVehiculoEnParqueaderoActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("N° documento:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Color:");

        txt_colorVehiculoEditar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_colorVehiculoEditarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_colorVehiculoEditarFocusLost(evt);
            }
        });
        txt_colorVehiculoEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_colorVehiculoEditarActionPerformed(evt);
            }
        });
        txt_colorVehiculoEditar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_colorVehiculoEditarKeyTyped(evt);
            }
        });

        txt_noIdentificacionEditar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_noIdentificacionEditarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_noIdentificacionEditarFocusLost(evt);
            }
        });
        txt_noIdentificacionEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_noIdentificacionEditarActionPerformed(evt);
            }
        });
        txt_noIdentificacionEditar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_noIdentificacionEditarKeyTyped(evt);
            }
        });

        cmb_tipoVehiculoEditar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "BICICLETA", "PATINETA", "OTRO" }));

        cmb_tipoIdentificacionEditar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "Cedula de ciudadanía (CC)", "Targeta de identidad (TI)", "Registro civil (RC)", "Pasaporte (PA)", "Targeta de extrangería (TE)", "NIT ", "Permiso permanencia (PP)", "DIE" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_colorVehiculoEditar, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                    .addComponent(cmb_noParqueadero, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_conveniosEditar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_tarifasEditar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmb_tipoIdentificacionEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_noIdentificacionEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_dueñoEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmb_tipoVehiculoEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addComponent(check_editEstaVehiculoEnParqueadero)
                        .addGap(51, 51, 51))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 32, Short.MAX_VALUE)
                        .addComponent(lbl_imgEditUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(225, 225, 225)
                .addComponent(btn_actualizar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cmb_tipoIdentificacionEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(check_editEstaVehiculoEnParqueadero)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lbl_imgEditUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel7))
                            .addComponent(txt_noIdentificacionEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel2)
                            .addComponent(txt_dueñoEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cmb_tipoVehiculoEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_colorVehiculoEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel4)
                            .addComponent(cmb_noParqueadero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cmb_conveniosEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel6)
                            .addComponent(cmb_tarifasEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(btn_actualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    //Metodo boton Actualizar
    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed

        
        int tipoIdentif_cmb = 0, tipoVehiculo_cmb = 0, parqueadero_cmb, validacion = 0;
        String qrInfo, color = "";                
        tipoIdentif_cmb = cmb_tipoIdentificacionEditar.getSelectedIndex();
        identificacion = txt_noIdentificacionEditar.getText().trim();
        dueñoBiciUOtro = txt_dueñoEditar.getText().trim();
        tipoVehiculo_cmb = cmb_tipoVehiculoEditar.getSelectedIndex();
        parqueadero_cmb = cmb_noParqueadero.getSelectedIndex();
        color = txt_colorVehiculoEditar.getText().trim();
        
        Parqueadero parqSeleccionado = new Parqueadero();
        Convenio convSeleccionado = new Convenio();
        Tarifa tarifSeleccionada = new Tarifa();
       
        //Capturamos el objeto parqueadero para validar le verdadero id en base de datos
        parqSeleccionado = (Parqueadero)cmb_noParqueadero.getSelectedItem();
        convSeleccionado = (Convenio)cmb_conveniosEditar.getSelectedItem();
        tarifSeleccionada = (Tarifa)cmb_tarifasEditar.getSelectedItem();
        
        boolean biciUOtroEnParqueadero = check_editEstaVehiculoEnParqueadero.isSelected();
        String biciUOtroEstaEnParqueo = "";
                                    
       if(tipoIdentif_cmb == 0){
            tipoIdentificacion = "Seleccione";
            cmb_tipoIdentificacionEditar.setBackground(Color.red);
            validacion++;
        }else if(tipoIdentif_cmb == 1){
            tipoIdentificacion = "CC";
        }else if(tipoIdentif_cmb == 2){
            tipoIdentificacion = "TI";
        }else if(tipoIdentif_cmb == 3){
            tipoIdentificacion = "RC";
        }else if(tipoIdentif_cmb == 4){
            tipoIdentificacion = "PA";
        }else if(tipoIdentif_cmb == 5){
            tipoIdentificacion = "TE";
        }else if(tipoIdentif_cmb == 6){
            tipoIdentificacion = "NIT";
        }else if(tipoIdentif_cmb == 7){
            tipoIdentificacion = "PP";
        }else if(tipoIdentif_cmb == 8){
            tipoIdentificacion = "DIE";
        }
                                          
        if(identificacion.equals("")){
            txt_noIdentificacionEditar.setBackground(Color.red);
            validacion++;
        }
        if(dueñoBiciUOtro.equals("")){
            txt_dueñoEditar.setBackground(Color.red);
            validacion++;
        }
        
        if(tipoVehiculo_cmb == 0){
            tipoVehiculoBiciUOtro = "Seleccione";
            cmb_tipoVehiculoEditar.setBackground(Color.red);
            validacion++;
        }else if(tipoVehiculo_cmb == 1){
            tipoVehiculoBiciUOtro = "BICICLETA";
        }else if(tipoVehiculo_cmb == 2){
            tipoVehiculoBiciUOtro = "PATINETA";
        }else if(tipoVehiculo_cmb == 3){
            tipoVehiculoBiciUOtro = "OTRO";
        }
        
        if(color.equals("")){
            txt_colorVehiculoEditar.setBackground(Color.red);
            validacion++;
        }
        
        if(parqueadero_cmb==0){
            cmb_noParqueadero.setBackground(Color.red);
            validacion++;
        }      
               
        if(biciUOtroEnParqueadero){
            biciUOtroEstaEnParqueo = "Si";
        }else{
            biciUOtroEstaEnParqueo = "No";
        }
        
        int vehiculoYaPreviamenteRegistrado = vehicontrolador.evaluarExistenciaDelVehiculo(null, null, tipoIdentificacion, identificacion);
        
        if(vehiculoYaPreviamenteRegistrado == 1){
            JOptionPane.showMessageDialog(null, "El vehiculo ya se encuentra registrado.", "Validación", JOptionPane.INFORMATION_MESSAGE, parametroControla.getIcon("/icons/advertencia.png", 32, 32));
            txt_noIdentificacionEditar.setText("");
            validacion++;
        }   
        
        //Validamos el verdadero id del parqueadero, del convenio y de la tarifa en bd
        int idRealDelParqueaderoSeleccionado = parqControla.consultarIdParqueadero(parqSeleccionado.getNombre());
        int idRealDelConvenioSeleccionado = convenioControla.consultarIdDeunConvenio(convSeleccionado.getNombre());
        int idRealDeTarifaSeleccionada = tarifaControla.consultarIdDeunaTarifa(tarifSeleccionada.getNombreTarifa());
                        
        boolean parqueaderoEstaOcupado = parqControla.consultarDisponibilidadDeParqueaderoMedianteID(idRealDelParqueaderoSeleccionado);
               
        if(parqueaderoEstaOcupado == true){
            JOptionPane.showMessageDialog(null, "El parqueadero indicado ya se encuentra ocupado.", "Validación", JOptionPane.INFORMATION_MESSAGE, parametroControla.getIcon("/icons/advertencia.png", 32, 32));
            validacion++;
        }else{
            boolean parqueaderoEsCompatible = parqControla.consultarCompatibilidadDeParqueaderoMedianteID(idRealDelParqueaderoSeleccionado, tipoVehiculoBiciUOtro);
            if(!parqueaderoEsCompatible){
               JOptionPane.showMessageDialog(null, "El parqueadero indicado no es compatible con el vehiculo.", "Validación", JOptionPane.INFORMATION_MESSAGE, parametroControla.getIcon("/icons/advertencia.png", 32, 32));
               cmb_noParqueadero.setSelectedIndex(0);
               validacion++;  
            }
        }
        
        if (validacion == 0) {           
            FilaAnterior = tablaBicis.getSelectedRow();
            
            //Encapsulamos el objeto vehiculo 
            vehiculoEditado.setId(ID);
            
            //Definimos el contenido que tendra el qr garanizando que siempre tenga 16 caracteres
            qrInfo = tipoIdentificacion+identificacion;
            if(qrInfo.length() < 16){
                qrInfo = qrInfo+parametroControla.generarConsecutivo(16-qrInfo.length());
            }
            vehiculoEditado.setQr_consecutivo(qrInfo);
            vehiculoEditado.setTipoIdentificacion(tipoIdentificacion);
            vehiculoEditado.setNumIdentificacion(identificacion);
            vehiculoEditado.setPropietario(dueñoBiciUOtro);
            vehiculoEditado.setTipoVehiculo(tipoVehiculoBiciUOtro);
            vehiculoEditado.setColor(color);
            vehiculoEditado.setId_parqueadero(idRealDelParqueaderoSeleccionado);
            vehiculoEditado.setId_convenio(idRealDelConvenioSeleccionado);
            vehiculoEditado.setId_tarifa(idRealDeTarifaSeleccionada); 
                       
            if(biciUOtroEstaEnParqueo.equals("Si")){
                if(laBiciUOtroTieneFacturaAbierta == 1){
                    int idFctra = facturaControla.consultarIdDeUnaFacturaAbierta(null, tipoIdentifBack, numeroIdentifBack);
                    facturaControla.actualizarFacturaAbierta(idFctra, ID, "", "", "", "", "", 0, 0, 0);
                
                }else{
                     //Modelamos la factura de primer ingreso
                    nuevaFactura.setId(0);
                    nuevaFactura.setCodigo("FAC" + parametroControla.generarConsecutivo(10));
                    nuevaFactura.setFechaDeFactura(facturaControla.fecha_de_factura());
                    nuevaFactura.setIdDelVehiculo(ID);
                    nuevaFactura.setFacturadoPor(usuarioControla.consultarIdDeunUsuario(user));
                    nuevaFactura.setEstadoDeFactura("Abierta");
                    nuevaFactura.setEstaContabilizada("No");
                    nuevaFactura.setFechaDeIngresoVehiculo("1990-01-01 23:59:00"); //No generará cobro pues no estamos teniendo en cuenta la hora en que fue ingresado
                    nuevaFactura.setId_cierre(1);
                    
                    //Creamos el objeto Factura de primer ingreso
                    facturaControla.crearFactura(nuevaFactura, true);
                }
            }else{
                if(laBiciUOtroTieneFacturaAbierta == 1){
                    
                    int vehiculoTieneFactPrimerIngreso = facturaControla.consultarSiVehiculoTieneFacturaDePrimerIngreso(ID);
                    
                    if(vehiculoTieneFactPrimerIngreso == 1){
                        facturaControla.eliminarFacturaAbierta(ID, null, null, null);
                    }else{
                        int idFctra = facturaControla.consultarIdDeUnaFacturaAbierta(null, tipoIdentificacion, identificacion);
                        facturaControla.actualizarFacturaAbierta(idFctra, ID, "", "", "", "", "", 0, 0, 0);
                        biciUOtroEstaEnParqueo = "Si";
                        JOptionPane.showMessageDialog(null, "El vehiculo si está en parqueadero, pero tiene un proceso de liquidación pendiente.", "Validación", JOptionPane.INFORMATION_MESSAGE, parametroControla.getIcon("/icons/advertencia.png", 32, 32));
                    }   
                }
            }  
            
            vehicontrolador.actualizarVehiculo(vehiculoEditado);
            parqControla.actualizarEstadoDeParqueadero(tipoVehiculoBiciUOtro, null, tipoIdentificacion, identificacion, dueñoBiciUOtro, idRealDelParqueaderoSeleccionado, biciUOtroEstaEnParqueo);
                                    
            //Agregamos el objeto vehiculo a la tabla de vehiculos
            Object[] fila = new Object[8];
            fila[0] = tipoIdentificacion;
            fila[1] = identificacion;
            fila[2] = dueñoBiciUOtro;
            fila[3] = tipoVehiculoBiciUOtro;
            fila[4] = parqSeleccionado.getNombre();
            fila[5] = color;
            fila[6] = convSeleccionado.getNombre();
            fila[7] = tarifSeleccionada.getNombreTarifa();
            modeloBicis.addRow(fila);
            modeloBicis.removeRow(FilaAnterior);
                        
            
            JOptionPane.showMessageDialog(null, "Vehiculo actualizado satisfactoriamente.", "Confirmación", JOptionPane.INFORMATION_MESSAGE, parametroControla.getIcon("/icons/exitoso.png", 32, 32));
            //Actualizamos el codigo QR del vehiculo y lo imprimimos
            vehicontrolador.actualizarQR(tipoVehiculoBack, tipoVehiculoBiciUOtro, null, null, qrInfo, tipoIdentifBack, numeroIdentifBack, tipoIdentificacion, identificacion, propietariaBack, dueñoBiciUOtro);
            this.dispose();  
            GestionarBicisyOtros.hayBiciUOtroEnEdicion = false;
            MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = false;
            
        }else{
            JOptionPane.showMessageDialog(null, "Debes de llenar todos los campos.", "Validación", JOptionPane.INFORMATION_MESSAGE, parametroControla.getIcon("/icons/advertencia.png", 32, 32));
            Normalizar();
        } 

                            
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void txt_dueñoEditarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dueñoEditarKeyTyped
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 30;
        if(txt_dueñoEditar.getText().length()== numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Solo 30 caracteres", "Validación", JOptionPane.INFORMATION_MESSAGE, parametroControla.getIcon("/icons/advertencia.png", 32, 32));
        }


        ////Forza a escribir en mayuscula
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            evt.setKeyChar(Character.toUpperCase(c));
            
        }
    }//GEN-LAST:event_txt_dueñoEditarKeyTyped

    private void cmb_noParqueaderoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_noParqueaderoItemStateChanged
        
    }//GEN-LAST:event_cmb_noParqueaderoItemStateChanged

    private void cmb_conveniosEditarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_conveniosEditarItemStateChanged
    
    }//GEN-LAST:event_cmb_conveniosEditarItemStateChanged

    private void check_editEstaVehiculoEnParqueaderoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_editEstaVehiculoEnParqueaderoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_check_editEstaVehiculoEnParqueaderoActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        cerrarEdicionVehiculo();
    }//GEN-LAST:event_formWindowClosing

    private void txt_colorVehiculoEditarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_colorVehiculoEditarFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_colorVehiculoEditarFocusGained

    private void txt_colorVehiculoEditarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_colorVehiculoEditarFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_colorVehiculoEditarFocusLost

    private void txt_colorVehiculoEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_colorVehiculoEditarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_colorVehiculoEditarActionPerformed

    private void txt_colorVehiculoEditarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_colorVehiculoEditarKeyTyped
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 29;
        if(txt_colorVehiculoEditar.getText().length()> numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Solo 30 caracteres.", "Validación", JOptionPane.INFORMATION_MESSAGE, parametroControla.getIcon("/icons/advertencia.png", 32, 32));
        }

        //Forza a escribir en mayuscula
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            evt.setKeyChar(Character.toUpperCase(c));

        }
    }//GEN-LAST:event_txt_colorVehiculoEditarKeyTyped

    private void txt_noIdentificacionEditarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_noIdentificacionEditarFocusGained

    }//GEN-LAST:event_txt_noIdentificacionEditarFocusGained

    private void txt_noIdentificacionEditarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_noIdentificacionEditarFocusLost

    }//GEN-LAST:event_txt_noIdentificacionEditarFocusLost

    private void txt_noIdentificacionEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_noIdentificacionEditarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_noIdentificacionEditarActionPerformed

    private void txt_noIdentificacionEditarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_noIdentificacionEditarKeyTyped
        //Forza aescribir en mayuscula
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            evt.setKeyChar(Character.toUpperCase(c));
        }

       //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 14;
        if(txt_noIdentificacionEditar.getText().length()== numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Solo 14 caracteres.", "Validación", JOptionPane.INFORMATION_MESSAGE, parametroControla.getIcon("/icons/advertencia.png", 32, 32));
            txt_noIdentificacionEditar.setText("");
        }
    }//GEN-LAST:event_txt_noIdentificacionEditarKeyTyped

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
            java.util.logging.Logger.getLogger(EditarBicisYOtros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarBicisYOtros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarBicisYOtros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarBicisYOtros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                    new EditarBicisYOtros().setVisible(true);
                }   catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    //Logger.getLogger(EditarVehiculo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_actualizar;
    private javax.swing.JCheckBox check_editEstaVehiculoEnParqueadero;
    private javax.swing.JComboBox<String> cmb_conveniosEditar;
    private javax.swing.JComboBox<String> cmb_noParqueadero;
    private javax.swing.JComboBox<String> cmb_tarifasEditar;
    private javax.swing.JComboBox<String> cmb_tipoIdentificacionEditar;
    private javax.swing.JComboBox<String> cmb_tipoVehiculoEditar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lbl_imgEditUsuario;
    private javax.swing.JTextField txt_colorVehiculoEditar;
    private javax.swing.JTextField txt_dueñoEditar;
    private javax.swing.JTextField txt_noIdentificacionEditar;
    // End of variables declaration//GEN-END:variables

    //Metodo que limpia el formulario
    public void Limpiar(){
        cmb_tipoIdentificacionEditar.setSelectedIndex(0);
        txt_noIdentificacionEditar.setText("");
        txt_dueñoEditar.setText("");
        cmb_tipoVehiculoEditar.setSelectedIndex(0);
        txt_colorVehiculoEditar.setText("");
        cmb_noParqueadero.setSelectedIndex(0);
        cmb_conveniosEditar.setSelectedIndex(0);
        cmb_tarifasEditar.setSelectedIndex(0);
        check_editEstaVehiculoEnParqueadero.setSelected(false);
    }
    
    //Metodo que normaliza el formulario
    public void Normalizar(){
        cmb_tipoIdentificacionEditar.setBackground(Color.WHITE);
        txt_noIdentificacionEditar.setBackground(Color.WHITE);
        txt_dueñoEditar.setBackground(Color.WHITE);
        cmb_tipoVehiculoEditar.setBackground(Color.WHITE);
        txt_colorVehiculoEditar.setBackground(Color.WHITE);
        cmb_noParqueadero.setBackground(Color.WHITE);
        cmb_conveniosEditar.setBackground(Color.WHITE);
        cmb_tarifasEditar.setBackground(Color.WHITE);
    }
    
    //Metodo que se invoca al cerrar el jFrame
    private void cerrarEdicionVehiculo(){
        
        String botones[] = {"Si", "No"};
        int eleccion = JOptionPane.showOptionDialog(this, "¿Está seguro que desea cerrar?", "Editar bici u otro", 0, JOptionPane.QUESTION_MESSAGE, parametroControla.getIcon("/icons/pregunta.png", 32, 32), botones, this);
               
        if(eleccion == JOptionPane.YES_OPTION){
            dispose();
            MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = false;
            hayBiciUOtroEnEdicion = false;
            vehicontrolador.recargarVehiculo(ID, null, tipoIdentifBack, numeroIdentifBack);
            parqControla.actualizarEstadoDeParqueadero(tipoVehiculoBack, null, tipoIdentifBack, numeroIdentifBack, propietariaBack, noParqueaderoBack, estaEnParqBack);
        }
    }
      
}
