package vista;

import clasesDeApoyo.generadorClavesYCodigos;
import controlador.ConvenioControlador;
import controlador.FacturaControlador;
import controlador.ParqueaderoControlador;
import controlador.TarifaControlador;
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


/**
 *
 * @author ALEJO
 */
public class EditarVehiculo extends javax.swing.JFrame{

    String vehiculo_actualizado="";
    String placaBack;
    String propietariaBack;
    int noParqueaderoBack;
    String claseBack;
    int convenioBack;
    int tarifaBack;
    String estaEnParqBack;
    String user;
    
   
    boolean elVehiculoEstaEnParqueadero = false;
    boolean elvehiculoTieneFacturaPrimerIngresoPrevRegistrada = false;
    boolean elVehiculoTieneFacturaAbierta = false;
    
    int ID;
    javax.swing.JTable tablaVehi;
    int FilaAnterior;
    DefaultTableModel modelo;
    int seleccionParq;
   
    Vehiculo vehiculoConsultado = new Vehiculo(0, "", "", "", 0, 0, 0);
    Vehiculo vehiculoEditado = new Vehiculo(0, "", "", "", 0, 0, 0);
    Factura nuevaFactura = new Factura(0, "", "", "", "", "", 0, "", "", "", 0, 0, "", 0, "", "", "", "", "");
    Factura facturaEditada = new Factura(0, "", "", "", "", "", 0, "", "", "", 0, 0, "", 0, "", "", "", "", "");
    
    VehiculoControlador vehicontrolador = new VehiculoControlador();
    ParqueaderoControlador parqControla = new ParqueaderoControlador();
    ConvenioControlador convenioControla = new ConvenioControlador();
    TarifaControlador tarifaControla = new TarifaControlador();
    FacturaControlador facturaControla = new FacturaControlador();
        
    //Declaramos los objetos  y se los aprovisionamos a su combobox
    Parqueadero parq = new Parqueadero();
    Convenio conv = new Convenio(0, "", "", "");
    Tarifa tarif = new Tarifa(0, "", "", "", "", "", "", "", "", "", "");
      
    private final Logger log = Logger.getLogger(EditarVehiculo.class);
    private URL url = EditarVehiculo.class.getResource("Log4j.properties");
    
    /**
     * Creates new form nuevoUsuario
     */
    public EditarVehiculo() {
        initComponents();
        vehiculo_actualizado = PanelVehiculos.vehiculo_update;
        tablaVehi = PanelVehiculos.Table_listaVehiculos;
        user = Login.usuario;
        modelo = PanelVehiculos.modelo;
        
        setSize(620,358);
        setResizable(false);
        setTitle("Editar vehiculo");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        //Avisamos que esta ventana se encuentra abierta para que no deje cerrar sesion al usuario
        MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = true;
        
        DefaultComboBoxModel modeloParq = new DefaultComboBoxModel(parq.mostrarParqueaderosTipoResidente());
        cmb_noParqueadero.setModel(modeloParq);
        parq.almacenarNombresParqueadero();
        
        DefaultComboBoxModel modeloConv = new DefaultComboBoxModel(conv.mostrarConveniosDisponibles());
        cmb_convenios.setModel(modeloConv);

        DefaultComboBoxModel modeloTarif = new DefaultComboBoxModel(tarif.mostrarTarifasDisponibles());
        cmb_tarifas.setModel(modeloTarif);
                       
        //Traemos el objeto tipo vehiculo con la info del vehiculo a editar
        vehiculoConsultado = vehicontrolador.consultarInformacionDeUnVehiculo(vehiculo_actualizado);
         
        //Colocamos la infromacion del objeto vehiculo en la interfaz
        ID = vehiculoConsultado.getId();
        placaBack = vehiculoConsultado.getPlaca();
        txt_placa.setText(placaBack);
        
        propietariaBack = vehiculoConsultado.getPropietario();
        txt_dueño.setText(propietariaBack);

        claseBack = vehiculoConsultado.getClase();
        cmb_clase.setSelectedItem(claseBack);

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
        cmb_convenios.setSelectedIndex(convenioBack);

        tarifaBack = vehiculoConsultado.getId_tarifa();
        cmb_tarifas.setSelectedIndex(tarifaBack);

        elVehiculoEstaEnParqueadero = vehicontrolador.verificarSiVehiculoEstaEnParqueadero(vehiculo_actualizado);
        
        if(elVehiculoEstaEnParqueadero){
            check_editEstaVehiculoEnParqueadero.setSelected(true);
            estaEnParqBack = "Si";
        }else{
            check_editEstaVehiculoEnParqueadero.setSelected(false);
            estaEnParqBack = "No";
        }        
        
        elVehiculoTieneFacturaAbierta = vehicontrolador.consultarSiVehiculoTieneFacturasAbiertas(vehiculo_actualizado);
        parqControla.liberarParqueadero(vehiculo_actualizado);
        vehicontrolador.liberarVehiculo(ID);
            
    }
    
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/IcoCar.png"));
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
        txt_placa = new javax.swing.JTextField();
        txt_dueño = new javax.swing.JTextField();
        btn_actualizar = new javax.swing.JButton();
        lbl_imgEditUsuario = new javax.swing.JLabel();
        cmb_noParqueadero = new javax.swing.JComboBox<>();
        cmb_convenios = new javax.swing.JComboBox<>();
        cmb_tarifas = new javax.swing.JComboBox<>();
        cmb_clase = new javax.swing.JComboBox<>();
        check_editEstaVehiculoEnParqueadero = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconImage(getIconImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Placa:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Propietario:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Clase:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("N° de parqueadero:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Convenio:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Tarifa:");

        txt_placa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_placaKeyTyped(evt);
            }
        });

        txt_dueño.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dueñoKeyTyped(evt);
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
        lbl_imgEditUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/EditCar.png"))); // NOI18N

        cmb_noParqueadero.setAutoscrolls(true);
        cmb_noParqueadero.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_noParqueaderoItemStateChanged(evt);
            }
        });

        cmb_convenios.setAutoscrolls(true);
        cmb_convenios.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_conveniosItemStateChanged(evt);
            }
        });

        cmb_tarifas.setAutoscrolls(true);

        cmb_clase.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "AUTOMOVIL", "MOTO" }));
        cmb_clase.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_claseItemStateChanged(evt);
            }
        });

        check_editEstaVehiculoEnParqueadero.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        check_editEstaVehiculoEnParqueadero.setText("Está en parqueadero");
        check_editEstaVehiculoEnParqueadero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_editEstaVehiculoEnParqueaderoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_dueño, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmb_clase, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cmb_tarifas, javax.swing.GroupLayout.Alignment.LEADING, 0, 240, Short.MAX_VALUE)
                                .addComponent(cmb_convenios, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmb_noParqueadero, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addComponent(lbl_imgEditUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(24, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txt_placa, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(check_editEstaVehiculoEnParqueadero)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(224, 224, 224)
                .addComponent(btn_actualizar)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel1)
                            .addComponent(txt_placa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(check_editEstaVehiculoEnParqueadero)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lbl_imgEditUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel2)
                            .addComponent(txt_dueño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel3)
                            .addComponent(cmb_clase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel4)
                            .addComponent(cmb_noParqueadero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel5)
                            .addComponent(cmb_convenios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel6)
                            .addComponent(cmb_tarifas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(btn_actualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    //Metodo boton Actualizar
    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
       
        int tipVehi_cmb, validacion = 0;
        String placa, dueño = "";
        String tipoVehi_string="";
        Parqueadero parqSeleccionado = new Parqueadero();
        
        placa = txt_placa.getText().trim();
        dueño = txt_dueño.getText().trim();
        tipVehi_cmb = cmb_clase.getSelectedIndex();
        int parqueadero_cmb = cmb_noParqueadero.getSelectedIndex();
        int convenio_cmb = cmb_convenios.getSelectedIndex();
        int tarifa_cmb = cmb_tarifas.getSelectedIndex();
        boolean vehiculoEnParqueadero = check_editEstaVehiculoEnParqueadero.isSelected();
        String vehiculoEstaEnParqueo = "";
        
        //Capturamos el objeto parqueadero para validar le verdadero id en base de datos
        parqSeleccionado = (Parqueadero)cmb_noParqueadero.getSelectedItem();
        
        String logError = "";
                    
       int minimoCaracteres = 6;
        
        if(placa.equals("")){
            txt_placa.setBackground(Color.red);
            validacion++;
        }
        
        if(dueño.equals("")){
            txt_dueño.setBackground(Color.red);
            validacion++;
        }
        
        if(tipVehi_cmb == 0){
            tipoVehi_string = "Seleccione";
            cmb_clase.setBackground(Color.red);
            validacion++;
        }        
        else if(tipVehi_cmb == 1){
            tipoVehi_string = "AUTOMOVIL";
        }else if(tipVehi_cmb == 2){
            tipoVehi_string = "MOTO";
        }
        
        if(parqueadero_cmb==0){
            cmb_noParqueadero.setBackground(Color.red);
            validacion++;
        }
        
        if(convenio_cmb==0){
            cmb_convenios.setBackground(Color.red);
            validacion++;
        }
        
        if(tarifa_cmb==0){
            cmb_tarifas.setBackground(Color.red);
            validacion++;
        }
              
        if(txt_placa.getText().length() < minimoCaracteres){
            txt_placa.setBackground(Color.red);
            JOptionPane.showMessageDialog(null,"Placa no válida.");
            txt_placa.setText("");
            validacion++;
        }
        
        if(vehiculoEnParqueadero){
            vehiculoEstaEnParqueo = "Si";
        }else{
            vehiculoEstaEnParqueo = "No";
        }
        
        boolean vehiculoYaPreviamenteRegistrado = vehicontrolador.evaluarExistenciaDelVehiculo(placa);
        
        if(vehiculoYaPreviamenteRegistrado == true){
            JOptionPane.showMessageDialog(null, "El vehiculo ya se encuentra registrado.");
            txt_placa.setText("");
            validacion++;
        }   
        
        //Validamos el verdadero id del parqueadero en bd
        int idRealDelParqueaderoSeleccionado = parqControla.consultarIdParqueadero(parqSeleccionado.getNombre());
                
        boolean parqueaderoEstaOcupado = parqControla.consultarDisponibilidadDeParqueaderoMedianteID(idRealDelParqueaderoSeleccionado);
               
        if(parqueaderoEstaOcupado == true){
            JOptionPane.showMessageDialog(null, "El parqueadero indicado ya se encuentra ocupado.");
            cmb_noParqueadero.setSelectedIndex(0);
            validacion++;
        }
        
        if (validacion == 0) {
            
            FilaAnterior = tablaVehi.getSelectedRow();
            
            //Encapsulamos el objeto vehiculo 
            vehiculoEditado.setId(ID);
            vehiculoEditado.setPlaca(placa);
            vehiculoEditado.setPropietario(dueño);
            vehiculoEditado.setClase(tipoVehi_string);
            vehiculoEditado.setId_parqueadero(idRealDelParqueaderoSeleccionado);
            vehiculoEditado.setId_convenio(convenio_cmb);
            vehiculoEditado.setId_tarifa(tarifa_cmb); 
                       
            if(vehiculoEstaEnParqueo.equals("Si")){
                if(elVehiculoTieneFacturaAbierta == true){
                    int idFctra = facturaControla.consultarIdDeUnaFacturaAbierta(placa);
                    facturaControla.actualizarFacturaAbierta(idFctra, placa, dueño, tipoVehi_string, idRealDelParqueaderoSeleccionado, convenio_cmb, tarifa_cmb);
                
                }else{
                    //Modelamos la factura de primer ingreso
                    nuevaFactura.setId(0);
                    nuevaFactura.setCodigo(generadorClavesYCodigos.generarRandomString(10));
                    nuevaFactura.setFechaDeFactura(facturaControla.fecha_de_factura());
                    nuevaFactura.setPlaca(placa);
                    nuevaFactura.setPropietario(dueño);
                    nuevaFactura.setClaseDeVehiculo(tipoVehi_string);
                    nuevaFactura.setId_parqueadero(idRealDelParqueaderoSeleccionado);
                    nuevaFactura.setFacturadoPor(user);
                    nuevaFactura.setEstadoDeFactura("Abierta");
                    nuevaFactura.setEstaContabilizada("No");
                    nuevaFactura.setId_convenio(convenio_cmb);
                    nuevaFactura.setId_tarifa(tarifa_cmb);
                    nuevaFactura.setFechaDeIngresoVehiculo("1990-01-01 23:59:00"); //No generará cobro pues no estamos teniendo en cuenta la hora en que fue ingresado
                    nuevaFactura.setId_cierre(1);

                    //Creamos el objeto Factura de primer ingreso
                    facturaControla.crearFactura(nuevaFactura);
                }
            }else{
                if(elVehiculoTieneFacturaAbierta == true){
                    
                    boolean vehiculoTieneFactPrimerIngreso = facturaControla.consultarSiVehiculoTieneFacturaDePrimerIngreso(placa);
                    
                    if(vehiculoTieneFactPrimerIngreso == true){
                        facturaControla.eliminarFacturaAbierta(placa);
                    }else{
                        int idFctra = facturaControla.consultarIdDeUnaFacturaAbierta(placa);
                        facturaControla.actualizarFacturaAbierta(idFctra, placa, dueño, tipoVehi_string, idRealDelParqueaderoSeleccionado, convenio_cmb, tarifa_cmb);
                        vehiculoEstaEnParqueo = "Si";
                        JOptionPane.showMessageDialog(null, "El vehiculo si está en parqueadero, tiene un proceso de liquidación pendiente.");
                    }   
                }
            }  
            
            vehicontrolador.actualizarVehiculo(vehiculoEditado);
            parqControla.actualizarEstadoDeParqueadero(placa, dueño, idRealDelParqueaderoSeleccionado, vehiculoEstaEnParqueo);
                                    
            //Agregamos el objeto vehiculo a la tabla de vehiculos
            Object[] fila = new Object[6];
            fila[0] = placa;
            fila[1] = dueño;
            fila[2] = tipoVehi_string;
            fila[3] = parqSeleccionado.getNombre();;
            fila[4] = convenioControla.consultarNombreDeConvenioMedianteID(convenio_cmb);
            fila[5] = tarifaControla.consultarNombreDeTarifaMedianteID(tarifa_cmb);
            
            modelo.addRow(fila);
            modelo.removeRow(FilaAnterior);
                        
            JOptionPane.showMessageDialog(null, "Vehiculo actualizado satisfactoriamente.");
            this.dispose();  
            PanelVehiculos.hayVehiculoEnEdicion = false;
            
            log.info("INFO - Se ha actualizado un vehiculo en el sistema.");
            
        }else{
            JOptionPane.showMessageDialog(null, "Debes de llenar todos los campos." + logError);
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

        ///Forza a escribir en mayuscula
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            evt.setKeyChar(Character.toUpperCase(c));
            
        }
    }//GEN-LAST:event_txt_placaKeyTyped

    private void txt_dueñoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dueñoKeyTyped
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 30;
        if(txt_dueño.getText().length()== numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Solo 30 caracteres");
        }


        ////Forza a escribir en mayuscula
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            evt.setKeyChar(Character.toUpperCase(c));
            
        }
    }//GEN-LAST:event_txt_dueñoKeyTyped

    private void cmb_noParqueaderoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_noParqueaderoItemStateChanged
        
    }//GEN-LAST:event_cmb_noParqueaderoItemStateChanged

    private void cmb_claseItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_claseItemStateChanged
    
    }//GEN-LAST:event_cmb_claseItemStateChanged

    private void cmb_conveniosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_conveniosItemStateChanged
    
    }//GEN-LAST:event_cmb_conveniosItemStateChanged

    private void check_editEstaVehiculoEnParqueaderoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_editEstaVehiculoEnParqueaderoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_check_editEstaVehiculoEnParqueaderoActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        cerrarEdicionVehiculo();
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
            java.util.logging.Logger.getLogger(EditarVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                    new EditarVehiculo().setVisible(true);
                }   catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    //Logger.getLogger(EditarVehiculo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_actualizar;
    private javax.swing.JCheckBox check_editEstaVehiculoEnParqueadero;
    private javax.swing.JComboBox<String> cmb_clase;
    private javax.swing.JComboBox<String> cmb_convenios;
    private javax.swing.JComboBox<String> cmb_noParqueadero;
    private javax.swing.JComboBox<String> cmb_tarifas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lbl_imgEditUsuario;
    private javax.swing.JTextField txt_dueño;
    private javax.swing.JTextField txt_placa;
    // End of variables declaration//GEN-END:variables

    //Metodo que limpia el formulario
    public void Limpiar(){
        txt_placa.setText("");
        txt_dueño.setText("");
        cmb_clase.setSelectedIndex(0);
        cmb_noParqueadero.setSelectedIndex(0);
        cmb_convenios.setSelectedIndex(0);
        cmb_tarifas.setSelectedIndex(0);
        check_editEstaVehiculoEnParqueadero.setSelected(false);
    }
    
    //Metodo que normaliza el formulario
    public void Normalizar(){
        txt_placa.setBackground(Color.WHITE);
        txt_dueño.setBackground(Color.WHITE);
        cmb_clase.setBackground(Color.WHITE);
        cmb_noParqueadero.setBackground(Color.WHITE);
        cmb_convenios.setBackground(Color.WHITE);
        cmb_tarifas.setBackground(Color.WHITE);
    }
    
    //Metodo que se invoca al cerrar el jFrame
    private void cerrarEdicionVehiculo(){
        
        String botones[] = {"Si", "No"};
        int eleccion = JOptionPane.showOptionDialog(this, "¿Está seguro que desea cerrar?", "Editar vehiculo", 0, 3, null, botones, this);
        
        if(eleccion == JOptionPane.YES_OPTION){
            dispose();
            MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = false;
            PanelVehiculos.hayVehiculoEnEdicion = false;
            vehicontrolador.recargarVehiculo(ID, placaBack);
            parqControla.actualizarEstadoDeParqueadero(placaBack, propietariaBack, noParqueaderoBack, estaEnParqBack);
        }
    }
      
}
