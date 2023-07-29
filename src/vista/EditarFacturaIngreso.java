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
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import modelo.Convenio;
import modelo.Factura;
import modelo.Parqueadero;
import modelo.Tarifa;
import modelo.Vehiculo;
import org.apache.log4j.Logger;
import static vista.PanelCaja.txt_convenio;
import static vista.PanelCaja.txt_tarifa;


/**
 *
 * @author ALEJO
 */
public class EditarFacturaIngreso extends javax.swing.JFrame{

    String factura_actualizada;
    int ID;
    javax.swing.JTable tablafacturas;
    int FilaAnterior;
    int montoDelaTarifa;
    int validacion = 0;
    DefaultTableModel modelo;
    
    Convenio conv = new Convenio();
    Tarifa tarif = new Tarifa();
    
    Factura facturaAEditar = new Factura (0, "", "", "", "", "", 0, "", "", "", 0, 0, "", 0, "", "", "", "", "", "");
    Factura facturaAActualizar = new Factura (0, "", "", "", "", "", 0, "", "", "", 0, 0, "", 0, "", "", "", "", "", "");
    
    FacturaControlador facturaControla = new FacturaControlador();
    ParqueaderoControlador parqControla = new ParqueaderoControlador();
    TarifaControlador tarifaControla = new TarifaControlador();
    ConvenioControlador convControla = new ConvenioControlador();
    VehiculoControlador vehiControla = new VehiculoControlador();
          
    Date hora_ingr;
    String codigo_factura;
    String usuario;
    String placa_back;
    String propietario_back;
    String tipVehi_back;
    String noParq_back;
    int filas;
    int tarifaBack;
    int convenioBack;
    boolean vehiculoDelBackupExiste = false;
    boolean seRealizaronValidaciones = false;
    boolean ingresoDesconocido = false;
    
    Convenio nomConvenio;
    Tarifa nomTarifa;
    
    private final Logger log = Logger.getLogger(EditarFacturaIngreso.class);
    private URL url = EditarFacturaIngreso.class.getResource("Log4j.properties");
    
    
    /**
     * Creates new form nuevoUsuario
     */
    public EditarFacturaIngreso() {
        initComponents();
        usuario = Login.usuario;
        factura_actualizada = GestionarFacturas.codigoFactura_update;
        tablafacturas = GestionarFacturas.table_listaFacturas;
        modelo = GestionarFacturas.modelo;
        filas = GestionarFacturas.Filas;
        
        setSize(615,370);
        setResizable(false);
        setTitle("Editar factura");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        //Avisamos que esta ventana se encuentra abierta para que no deje cerrar sesion al usuario
        MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = true;
        
        //Ocultamos el combobox de parqueaderos
        cmb_parqVisitantes.setVisible(false);
        
        DefaultComboBoxModel modeloConv = new DefaultComboBoxModel(conv.mostrarConveniosDisponibles());
        cmb_convenios.setModel(modeloConv);
        conv.almacenarNombresConvenio();

        DefaultComboBoxModel modeloTarif = new DefaultComboBoxModel(tarif.mostrarTarifasDisponibles());
        cmb_tarifas.setModel(modeloTarif);
        tarif.almacenarNombresTarifa();
                     
        //Cargamos la informacion de la factura abierta en el frame
        facturaAEditar = facturaControla.consultarInformacionDeUnaFacturaAbiertaParaSuEdicion(factura_actualizada);
        
        ID = facturaAEditar.getId();
        codigo_factura = facturaAEditar.getCodigo();
        
        //Hacemos un respaldo de la informacion por si el usuario se equivoca
        placa_back = facturaAEditar.getPlaca();
        txt_placa.setText(placa_back);
        
        //Evaluamos la existencia del vehiculo backup en bd para su futura consulta al actualizar
        vehiculoDelBackupExiste = vehiControla.evaluarExistenciaDelVehiculo(placa_back);
        
        propietario_back = facturaAEditar.getPropietario(); 
        txt_propietario.setText(propietario_back);
        
        tipVehi_back = facturaAEditar.getClaseDeVehiculo();
        cmb_tipVehi.setSelectedItem(tipVehi_back);
        
        noParq_back = parqControla.consultarNombreDeParqueaderoMedianteID(facturaAEditar.getId_parqueadero());
        lbl_noParq.setText(noParq_back);
        
        convenioBack = facturaAEditar.getId_convenio();
        //Buscamos el nombre del convenio que le pertenece a ese id para pintarlo en el combobox
        String nom_convenio = convControla.consultarNombreDeConvenioMedianteID(convenioBack);    
                                     
        cmb_convenios.setSelectedIndex(buscarConvenioEnComboBox(nom_convenio));
    
        tarifaBack = facturaAEditar.getId_tarifa();
        //Buscamos el nombre de la tarifa que le pertenece a ese id para pintarlo en el combobox
        String nom_tarifa = tarifaControla.consultarNombreDeTarifaMedianteID(tarifaBack);    
        
        cmb_tarifas.setSelectedIndex(buscarTarifaEnComboBox(nom_tarifa));
               
        lbl_horaIngreso.setText(facturaAEditar.getFechaDeIngresoVehiculo());
        
        //Consultamos el propietario con el fin de ver si se encuentra registrado, de ser asi, deshabilitamos la edicion del formulario
        boolean vehiculoRegistrado = vehiControla.evaluarExistenciaDelVehiculo(placa_back);
        
        if(vehiculoRegistrado == true){
            txt_propietario.setEditable(false);
            cmb_tipVehi.setEnabled(false);
            cmb_convenios.setEnabled(false);
            cmb_tarifas.setEnabled(false);
        }
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
        cmb_tipVehi = new javax.swing.JComboBox<>();
        lbl_horaIngreso = new javax.swing.JLabel();
        lbl_noParq = new javax.swing.JLabel();
        cmb_parqVisitantes = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconImage(getIconImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
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
        txt_placa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_placaActionPerformed(evt);
            }
        });
        txt_placa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_placaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_placaKeyReleased(evt);
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
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });

        lbl_imgEditUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_imgEditUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/editFctra.png"))); // NOI18N

        cmb_tarifas.setAutoscrolls(true);
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

        cmb_convenios.setAutoscrolls(true);
        cmb_convenios.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_conveniosItemStateChanged(evt);
            }
        });
        cmb_convenios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_conveniosActionPerformed(evt);
            }
        });
        cmb_convenios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmb_conveniosKeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Hora Ingreso:");

        cmb_tipVehi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "AUTOMOVIL", "MOTO", "ESPECIAL" }));
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

        lbl_horaIngreso.setText("lbl_feha_ingreso");

        lbl_noParq.setText("lbl");

        cmb_parqVisitantes.setAutoscrolls(true);
        cmb_parqVisitantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_parqVisitantesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmb_tipVehi, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_placa, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_horaIngreso)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_noParq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmb_parqVisitantes, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cmb_tarifas, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmb_convenios, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_propietario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(lbl_imgEditUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addGroup(layout.createSequentialGroup()
                .addGap(236, 236, 236)
                .addComponent(btn_actualizar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_placa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txt_propietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cmb_tipVehi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(lbl_noParq)
                            .addComponent(cmb_parqVisitantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cmb_convenios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cmb_tarifas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(lbl_imgEditUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lbl_horaIngreso))
                .addGap(28, 28, 28)
                .addComponent(btn_actualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    //Metodo boton Actualizar
    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
                        
        Convenio convSeleccionado = new Convenio();
        Tarifa tarifSeleccionada = new Tarifa();
        Parqueadero parqSeleccionado = new Parqueadero();
        
        //Capturamos los datos del formulario
        String tipoVehi_string = "";       
        String placa = txt_placa.getText();
        String dueño = txt_propietario.getText();
        int tipVehi_cmb = cmb_tipVehi.getSelectedIndex();       
        String no_parq = lbl_noParq.getText();
        
        convSeleccionado = (Convenio)cmb_convenios.getSelectedItem();
        tarifSeleccionada = (Tarifa)cmb_tarifas.getSelectedItem();
                       
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
        }else if(tipVehi_cmb == 3){
            tipoVehi_string = "ESPECIAL";
        } 
        
                
        //Validamos el verdadero id del convenio y de la tarifa en bd
        int idRealDelConvenioSeleccionado = convControla.consultarIdDeunConvenio(convSeleccionado.getNombre());
        int idRealDeTarifaSeleccionada = tarifaControla.consultarIdDeunaTarifa(tarifSeleccionada.getNombreTarifa());
                             
        if (validacion == 0){
            
            if(seRealizaronValidaciones == true){
                
                JOptionPane.showMessageDialog(null, "Validaciones realizadas satisfactoriamente.");
                                
                //Encapsulamos el objeto factura a actualizar
                facturaAActualizar.setId(ID);
                facturaAActualizar.setPlaca(placa);
                facturaAActualizar.setPropietario(dueño);
                facturaAActualizar.setClaseDeVehiculo(tipoVehi_string);
                
                //El vehiculo analizado ya existe en el sistema y el ingresado tambien existe
                if(vehiculoDelBackupExiste == true && ingresoDesconocido == false){
                    
                    //Modificamos el id del parqueadero por el del vehiculo ingresado en el objeto factura
                    facturaAActualizar.setId_parqueadero(parqControla.consultarIdParqueadero(no_parq));
                    
                    //Cambiamos los estados de los parqueaderos para los vehiculos registrados involucrados
                    parqControla.actualizarEstadoDeParqueadero(placa_back, propietario_back, facturaAEditar.getId_parqueadero(), "No");
                    parqControla.actualizarEstadoDeParqueadero(placa, dueño, parqControla.consultarIdParqueadero(no_parq), "Si");
                    
                }
                
                //El vehiculo analizado no existe en el sistema y el ingresado tampoco existe
                else if(vehiculoDelBackupExiste == false && ingresoDesconocido == true){
                    
                    parqSeleccionado = (Parqueadero)cmb_parqVisitantes.getSelectedItem();
                             
                    //Validamos el verdadero id del convenio y de la tarifa en bd y lo editamos en el objeto factura
                    int idRealDelParqSeleccionado = parqControla.consultarIdParqueadero(parqSeleccionado.getNombre());
                    facturaAActualizar.setId_parqueadero(idRealDelParqSeleccionado);
                    
                    //Liberamos el parqueadero en donde estaba el vehiculo analizado y ocupamos el seleccionado para el vehiculo ingresado
                    parqControla.liberarParqueadero(placa_back);
                    parqControla.actualizarEstadoDeParqueadero(placa, dueño, idRealDelParqSeleccionado, "Si");
                     
                }                
                
                //El vehiculo analizado ya existe en el sistema pero el ingresado es desconocido
                if(vehiculoDelBackupExiste == true && ingresoDesconocido == true){
                    
                    parqSeleccionado = (Parqueadero)cmb_parqVisitantes.getSelectedItem();
                             
                    //Validamos el verdadero id del convenio y de la tarifa en bd
                    int idRealDelParqSeleccionado = parqControla.consultarIdParqueadero(parqSeleccionado.getNombre());
                    facturaAActualizar.setId_parqueadero(idRealDelParqSeleccionado);
                            
                    //Cambiamos los estados de los parqueaderos para los vehiculos registrados involucrados
                    parqControla.actualizarEstadoDeParqueadero(placa_back, propietario_back, facturaAEditar.getId_parqueadero(), "No");
                    parqControla.actualizarEstadoDeParqueadero(placa, dueño, idRealDelParqSeleccionado, "Si");
                    
                }               
                
                //El vehiculo analizado no existe en el sistema pero el ingresado es conocido
                else if(vehiculoDelBackupExiste == false && ingresoDesconocido == false){
                    
                    //Modificamos el id del parqueadero por el del vehiculo ingresado en el objeto factura
                    facturaAActualizar.setId_parqueadero(parqControla.consultarIdParqueadero(no_parq));
                    
                    //Liberamos el parqueadero en donde estaba el vehiculo analizado y ocupamos el seleccionado para el vehiculo ingresado
                    parqControla.liberarParqueadero(placa_back);
                    parqControla.actualizarEstadoDeParqueadero(placa, dueño, parqControla.consultarIdParqueadero(no_parq), "Si");
                }
                
                facturaAActualizar.setFacturadoPor(usuario);
                facturaAActualizar.setId_convenio(idRealDelConvenioSeleccionado);
                facturaAActualizar.setId_tarifa(idRealDeTarifaSeleccionada);

                //Actualizamos la factura
                facturaControla.actualizarFacturaIngreso(facturaAActualizar);

                //Aqui modificamos la fila existente y que fue seleccionada en la tabla gestionar facturas
                Object Fila[] = new Object[4];
                Fila[0] = codigo_factura;
                Fila[1] = facturaControla.fecha_de_factura();
                Fila[2] = usuario;
                Fila[3] = "0";

                for(int i=0; i < tablafacturas.getColumnCount(); i++){
                    modelo.setValueAt(Fila[i], filas, i);
                }

                JOptionPane.showMessageDialog(null, "Factura actualizada satisfactoriamente.");
                this.dispose();
                new InformacionFacturaIngreso().setVisible(true);
                
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
        
        String tipVehi_string = (String)cmb_tipVehi.getSelectedItem();
        
        if(tipVehi_string.equals("ESPECIAL")){
            cmb_convenios.setSelectedIndex(buscarConvenioEnComboBox("NINGUNO"));
            cmb_tarifas.setSelectedIndex(buscarTarifaEnComboBox("TARIF_PREFERENCIAL"));
        }
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

    private void txt_placaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_placaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_placaKeyReleased

    private void cmb_conveniosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_conveniosItemStateChanged
       
    }//GEN-LAST:event_cmb_conveniosItemStateChanged

    private void cmb_conveniosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_conveniosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_conveniosActionPerformed

    private void cmb_tarifasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_tarifasItemStateChanged
       
    }//GEN-LAST:event_cmb_tarifasItemStateChanged

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        cerrarEdicionDeFacturaIngreso();
    }//GEN-LAST:event_formWindowClosing

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
 
    }//GEN-LAST:event_formKeyPressed

    private void cmb_parqVisitantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_parqVisitantesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_parqVisitantesActionPerformed

    private void cmb_tipVehiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmb_tipVehiKeyPressed
        eventosDelFormulario(evt);
    }//GEN-LAST:event_cmb_tipVehiKeyPressed

    private void cmb_conveniosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmb_conveniosKeyPressed
        eventosDelFormulario(evt);
    }//GEN-LAST:event_cmb_conveniosKeyPressed

    private void cmb_tarifasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmb_tarifasKeyPressed
        eventosDelFormulario(evt);
    }//GEN-LAST:event_cmb_tarifasKeyPressed

    private void txt_placaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_placaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_placaActionPerformed

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
            java.util.logging.Logger.getLogger(EditarFacturaIngreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarFacturaIngreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarFacturaIngreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarFacturaIngreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new EditarFacturaIngreso().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_actualizar;
    private javax.swing.JComboBox<String> cmb_convenios;
    public static javax.swing.JComboBox<String> cmb_parqVisitantes;
    private javax.swing.JComboBox<String> cmb_tarifas;
    private javax.swing.JComboBox<String> cmb_tipVehi;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lbl_horaIngreso;
    private javax.swing.JLabel lbl_imgEditUsuario;
    private javax.swing.JLabel lbl_noParq;
    private javax.swing.JTextField txt_placa;
    private javax.swing.JTextField txt_propietario;
    // End of variables declaration//GEN-END:variables

    //Metodo que limpia el formulario
    public void Limpiar(){
        txt_placa.setText("");
        txt_propietario.setText("");
        cmb_tipVehi.setSelectedIndex(0);
        cmb_convenios.setSelectedIndex(0);
        cmb_tarifas.setSelectedIndex(0);
    }  
    
    //Metodo que normaliza el formulario
    public void Normalizar(){
        
        txt_placa.setBackground(Color.WHITE);
        txt_propietario.setBackground(Color.WHITE);
        cmb_tipVehi.setBackground(Color.WHITE);
        cmb_tarifas.setBackground(Color.WHITE);
        cmb_convenios.setBackground(Color.WHITE);
    } 
    
    //Metodo que se invoca al cerrar el jFrame
    private void cerrarEdicionDeFacturaIngreso(){
        
        String botones[] = {"Si", "No"};
        int eleccion = JOptionPane.showOptionDialog(this, "¿Está seguro que desea cerrar?", "Editar factura", 0, 3, null, botones, this);
        
        if(eleccion == JOptionPane.YES_OPTION){
            dispose();
            
            if(ingresoDesconocido == true){
                ingresoDesconocido = false;
                parqControla.actualizarEstadoDeParqueadero(placa_back, propietario_back, facturaAEditar.getId_parqueadero(), "Si");
            }
            new InformacionFacturaIngreso().setVisible(true);
        }
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
                    
                    //Validamos si el vehiculo tiene una factura previa en estado abierto
                    boolean vehiculoTieneFacturaAbiertaPrevia = vehiControla.consultarSiVehiculoTieneFacturasAbiertas(placa);
                    
                    if(vehiculoTieneFacturaAbiertaPrevia == true){
                        JOptionPane.showMessageDialog(null, "El vehiculo ya tiene un proceso de facturación previo.");
                        txt_placa.setText(placa_back);
                    }else{
                        Vehiculo infoVehiculo = vehiControla.consultarInformacionDeUnVehiculo(placa);
                                        
                        txt_propietario.setText(infoVehiculo.getPropietario());
                        cmb_tipVehi.setSelectedItem(infoVehiculo.getClase());
                        lbl_noParq.setText(parqControla.consultarNombreDeParqueaderoMedianteID(infoVehiculo.getId_parqueadero()));
                        cmb_convenios.setSelectedIndex(buscarConvenioEnComboBox(convControla.consultarNombreDeConvenioMedianteID(infoVehiculo.getId_convenio())));
                        cmb_tarifas.setSelectedIndex(buscarTarifaEnComboBox(tarifaControla.consultarNombreDeTarifaMedianteID(infoVehiculo.getId_tarifa())));
                        
                        txt_placa.setEditable(false);
                        txt_propietario.setEditable(false);
                        cmb_tipVehi.setEnabled(false);
                        lbl_noParq.setVisible(true);
                        cmb_parqVisitantes.setEnabled(false);
                        cmb_convenios.setEnabled(false);
                        cmb_tarifas.setEnabled(false);
                    }
                }else{
                    txt_placa.setText(placa_back);
                    seRealizaronValidaciones = false;
                }
            
            }else{
                //Validamos si el vehiculo tiene una factura previa en estado abierto
                boolean vehiculoTieneFacturaAbiertaPrevia = vehiControla.consultarSiVehiculoTieneFacturasAbiertas(placa);
                    
                if(vehiculoTieneFacturaAbiertaPrevia == true){
                    JOptionPane.showMessageDialog(null, "El vehiculo ya tiene un proceso de facturación previo.");
                    txt_placa.setText(placa_back);
                }else{
                    
                    JOptionPane.showMessageDialog(null, "Vehiculo desconocido.");
                    ingresoDesconocido = true;
                    parqControla.liberarParqueadero(placa_back);
                    parqControla.ejecutarHiloParqueaderosVisitantesDisponiblesEditarFacturaIngreso();
                    txt_propietario.setText("");
                    cmb_tipVehi.setSelectedIndex(0);
                    lbl_noParq.setText("");
                    lbl_noParq.setVisible(false);
                    cmb_parqVisitantes.setVisible(true);
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
    }
    
    //Metodo que se encarga de recargar la informacion original de la factura
    public void recargarInfoOriginal(){
        
        txt_placa.setText(placa_back);
        txt_propietario.setText(propietario_back);
        cmb_tipVehi.setSelectedItem(tipVehi_back);
        lbl_noParq.setText(noParq_back);
        cmb_convenios.setSelectedIndex(buscarConvenioEnComboBox(convControla.consultarNombreDeConvenioMedianteID(convenioBack)));
        cmb_tarifas.setSelectedIndex(buscarTarifaEnComboBox(tarifaControla.consultarNombreDeTarifaMedianteID(tarifaBack)));
        cmb_parqVisitantes.setVisible(false);
        lbl_noParq.setVisible(true);
        
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
            parqControla.actualizarEstadoDeParqueadero(placa_back, propietario_back, facturaAEditar.getId_parqueadero(), "Si");
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
