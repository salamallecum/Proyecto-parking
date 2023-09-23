package vista;

import controlador.ArqueoControlador;
import controlador.FacturaControlador;
import controlador.UsuarioControlador;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.net.URL;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import modelo.Arqueo;
import org.apache.log4j.Logger;
import static vista.GestionarArqueos.lbl_numeroDeArqueos;
import static vista.GestionarArqueos.lbl_perdidasEnArqueos;


/**
 *
 * @author ALEJO
 */
public class EditarArqueoDeCaja extends javax.swing.JFrame{
    
    int baseDeCajaInt = 0;
    javax.swing.JTable tablaArqueos;
    DefaultTableModel modelo;
    String usuarioDelSistema = "";
    String baseDeCajaOriginal;
            
    int dineroEnCaja = 0;
    String dineroEnCajaString = "";
    String montoTotalCaja = "";
    
    int diferenciaCalculo = 0;
    String diferenciaString = "";
    String diferenciaTotal = "";
    
    //Variables del numero de billetes y monedas de cada una de las denominaciones
    String numBilletesDe100Mil = "";
    String numBilletesDe50Mil = "";
    String numBilletesDe20Mil = "";
    String numBilletesDe10Mil = "";
    String numBilletesDe5Mil = "";
    String numBilletesDe2Mil = "";
    String numBilletesOMonedasDeMil = "";
    String numMonedasDe500 = "";
    String numMonedasDe200 = "";
    String numMonedasDe100 = "";
    String numMonedasDe50 = "";
        
    int ID;
    String codigoArqueo = "";
        
    //Variables de montos en cada una de las denominaciones
    int montoEnBilletes100Mil = 0;
    int montoEnBilletes50Mil = 0;
    int montoEnBilletes20Mil = 0;
    int montoEnBilletes10Mil = 0;
    int montoEnBilletes5Mil = 0;
    int montoEnBilletes2Mil = 0;
    int montoEnBilletesOMonedasMil = 0;
    int montoEnMonedasDe500 = 0;
    int montoEnMonedasDe200 = 0;
    int montoEnMonedasDe100 = 0;
    int montoEnMonedasDe50 = 0; 
      
    Arqueo arqueoAEditar = new Arqueo(0, "", 0, "", "", "", "", "", "", "", "", "", "", "", "", "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, "", "");
    Arqueo arqueoAActualizar = new Arqueo(0, "", 0, "", "", "", "", "", "", "", "", "", "", "", "", "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, "", "");
    ArqueoControlador arqueoControla = new ArqueoControlador();
    FacturaControlador facturaControla = new FacturaControlador();
    UsuarioControlador usuarioControla = new UsuarioControlador();
            
    private final Logger log = Logger.getLogger(EditarArqueoDeCaja.class);
    private URL url = EditarArqueoDeCaja.class.getResource("Log4j.properties");
    
    /**
     * Creates new form ArqueoDeCaja
     */
    public EditarArqueoDeCaja() {
        initComponents();
        setSize(560, 385);
        setResizable(false);
        setTitle("Arqueo de caja");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        usuarioDelSistema = Login.usuario;
        codigoArqueo = GestionarArqueos.codigoArqueo_update;
        tablaArqueos = GestionarArqueos.table_listaArqueos;
        modelo = GestionarArqueos.modelo;
        
        //Avisamos que esta ventana se encuentra abierta para que no deje cerrar sesion al usuario
        MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = true;
                
        //Traemos la información del arqueo a editar
        arqueoAEditar = arqueoControla.consultarInformacionDeUnArqueo(codigoArqueo);
        
        //Capturamos la informacion recibida del objeto en variables
        ID = arqueoAEditar.getId();
        numBilletesDe100Mil = arqueoAEditar.getNumBilletesDe100Mil();
        numBilletesDe50Mil = arqueoAEditar.getNumBilletesDe50Mil();
        numBilletesDe20Mil = arqueoAEditar.getNumBilletesDe20Mil();
        numBilletesDe10Mil = arqueoAEditar.getNumBilletesDe10Mil();
        numBilletesDe5Mil = arqueoAEditar.getNumBilletesDe5Mil();
        numBilletesDe2Mil = arqueoAEditar.getNumBilletesDe2Mil();
        numBilletesOMonedasDeMil = arqueoAEditar.getNumBilletesOMonedasDeMil();
        numMonedasDe500 = arqueoAEditar.getNumMonedasDe500();
        numMonedasDe200 = arqueoAEditar.getNumMonedasDe200();       
        numMonedasDe100 = arqueoAEditar.getNumMonedasDe100();
        numMonedasDe50 = arqueoAEditar.getNumMonedasDe50();
        
        //Cargamos el valor de la base que debe tener la caja
        baseDeCajaOriginal = arqueoAEditar.getBase_caja();
        
        //Guardamos el valor int de la base de caja para calculos futuros 
        baseDeCajaInt = Integer.parseInt(baseDeCajaOriginal);
                        
        //Calculamos los montos para cada una de las denominaciones de dinero
        montoEnBilletes100Mil = 100000 * Integer.parseInt(numBilletesDe100Mil);
        montoEnBilletes50Mil = 50000 * Integer.parseInt(numBilletesDe50Mil);
        montoEnBilletes20Mil = 20000 * Integer.parseInt(numBilletesDe20Mil);
        montoEnBilletes10Mil = 10000 * Integer.parseInt(numBilletesDe10Mil);
        montoEnBilletes5Mil = 5000 * Integer.parseInt(numBilletesDe5Mil);
        montoEnBilletes2Mil = 2000 * Integer.parseInt(numBilletesDe2Mil);
        montoEnBilletesOMonedasMil = 1000 * Integer.parseInt(numBilletesOMonedasDeMil);
        montoEnMonedasDe500 = 500 * Integer.parseInt(numMonedasDe500);
        montoEnMonedasDe200 = 200 * Integer.parseInt(numMonedasDe200);
        montoEnMonedasDe100 = 100 * Integer.parseInt(numMonedasDe100);
        montoEnMonedasDe50 = 50 * Integer.parseInt(numMonedasDe50);
        
        //Calculamos el dinero existente en caja para ese momento
        dineroEnCaja = montoEnBilletes100Mil + montoEnBilletes50Mil + montoEnBilletes20Mil + montoEnBilletes10Mil + montoEnBilletes5Mil + montoEnBilletes2Mil + montoEnBilletesOMonedasMil + montoEnMonedasDe500 + montoEnMonedasDe200 + montoEnMonedasDe100 + montoEnMonedasDe50;
                
        dineroEnCajaString = Integer.toString(dineroEnCaja);
        dineroEnCajaString = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
        
        //Calculamos la diferencia respecto a la base en tiempo real
        diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
        diferenciaString = Integer.toString(diferenciaCalculo);
        diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);
        String baseCajaFormat = facturaControla.agregarFormatoMoneda(baseDeCajaOriginal);
                
        //Mostramos toda la informacion en pantalla        
        txt_numBilletes100mil.setText(numBilletesDe100Mil);
        txt_numBilletes50mil.setText(numBilletesDe50Mil);
        txt_numBilletes20mil.setText(numBilletesDe20Mil);
        txt_numBilletes5mil.setText(numBilletesDe5Mil);
        txt_numBilletes10mil.setText(numBilletesDe10Mil);
        txt_numBilletes2mil.setText(numBilletesDe2Mil);
        txt_numBilletesOMonedasDeMil.setText(numBilletesOMonedasDeMil);
        txt_numMonedas500pesos.setText(numMonedasDe500);
        txt_numMonedas200pesos.setText(numMonedasDe200);
        txt_numMonedas100pesos.setText(numMonedasDe100);
        txt_numMonedas50pesos.setText(numMonedasDe50);  
        lbl_baseDeCaja.setText(baseCajaFormat);
        lbl_totalEnCaja.setText(dineroEnCajaString);
        lbl_diferencia.setText(diferenciaTotal);
        lbl_baseDeCaja.setText(facturaControla.agregarFormatoMoneda(baseDeCajaOriginal));
        muestreoDiferencia();
        
    }
    
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/Caja.png"));
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

        jLabel4 = new javax.swing.JLabel();
        txt_nombrePropietario = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        txt_numBilletes100mil = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_numBilletes50mil = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_numBilletes20mil = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_numBilletes10mil = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_numBilletes5mil = new javax.swing.JTextField();
        txt_numBilletes2mil = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_numBilletesOMonedasDeMil = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_numMonedas500pesos = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txt_numMonedas200pesos = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txt_numMonedas100pesos = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txt_numMonedas50pesos = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lbl_baseDeCaja = new javax.swing.JLabel();
        lbl_totalEnCaja = new javax.swing.JLabel();
        lbl_diferencia = new javax.swing.JLabel();
        btn_actualizar = new javax.swing.JButton();

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Clase:");

        txt_nombrePropietario.setEnabled(false);
        txt_nombrePropietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nombrePropietarioActionPerformed(evt);
            }
        });
        txt_nombrePropietario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombrePropietarioKeyTyped(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconImage(getIconImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Conteo de dinero"));

        txt_numBilletes100mil.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_numBilletes100milFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_numBilletes100milFocusLost(evt);
            }
        });
        txt_numBilletes100mil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numBilletes100milActionPerformed(evt);
            }
        });
        txt_numBilletes100mil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_numBilletes100milKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_numBilletes100milKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("$ 100.000:");

        txt_numBilletes50mil.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_numBilletes50milFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_numBilletes50milFocusLost(evt);
            }
        });
        txt_numBilletes50mil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numBilletes50milActionPerformed(evt);
            }
        });
        txt_numBilletes50mil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_numBilletes50milKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_numBilletes50milKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("$ 50.000:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("$ 20.000:");

        txt_numBilletes20mil.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_numBilletes20milFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_numBilletes20milFocusLost(evt);
            }
        });
        txt_numBilletes20mil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numBilletes20milActionPerformed(evt);
            }
        });
        txt_numBilletes20mil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_numBilletes20milKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_numBilletes20milKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("$ 10.000:");

        txt_numBilletes10mil.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_numBilletes10milFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_numBilletes10milFocusLost(evt);
            }
        });
        txt_numBilletes10mil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numBilletes10milActionPerformed(evt);
            }
        });
        txt_numBilletes10mil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_numBilletes10milKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_numBilletes10milKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("$ 5.000:");

        txt_numBilletes5mil.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_numBilletes5milFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_numBilletes5milFocusLost(evt);
            }
        });
        txt_numBilletes5mil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numBilletes5milActionPerformed(evt);
            }
        });
        txt_numBilletes5mil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_numBilletes5milKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_numBilletes5milKeyTyped(evt);
            }
        });

        txt_numBilletes2mil.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_numBilletes2milFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_numBilletes2milFocusLost(evt);
            }
        });
        txt_numBilletes2mil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numBilletes2milActionPerformed(evt);
            }
        });
        txt_numBilletes2mil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_numBilletes2milKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_numBilletes2milKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("$ 2.000:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("$ 1.000:");

        txt_numBilletesOMonedasDeMil.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_numBilletesOMonedasDeMilFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_numBilletesOMonedasDeMilFocusLost(evt);
            }
        });
        txt_numBilletesOMonedasDeMil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numBilletesOMonedasDeMilActionPerformed(evt);
            }
        });
        txt_numBilletesOMonedasDeMil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_numBilletesOMonedasDeMilKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_numBilletesOMonedasDeMilKeyTyped(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("$ 500:");

        txt_numMonedas500pesos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_numMonedas500pesosFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_numMonedas500pesosFocusLost(evt);
            }
        });
        txt_numMonedas500pesos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numMonedas500pesosActionPerformed(evt);
            }
        });
        txt_numMonedas500pesos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_numMonedas500pesosKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_numMonedas500pesosKeyTyped(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("$ 200:");

        txt_numMonedas200pesos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_numMonedas200pesosFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_numMonedas200pesosFocusLost(evt);
            }
        });
        txt_numMonedas200pesos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numMonedas200pesosActionPerformed(evt);
            }
        });
        txt_numMonedas200pesos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_numMonedas200pesosKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_numMonedas200pesosKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("$ 100:");

        txt_numMonedas100pesos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_numMonedas100pesosFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_numMonedas100pesosFocusLost(evt);
            }
        });
        txt_numMonedas100pesos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numMonedas100pesosActionPerformed(evt);
            }
        });
        txt_numMonedas100pesos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_numMonedas100pesosKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_numMonedas100pesosKeyTyped(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("$ 50:");

        txt_numMonedas50pesos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_numMonedas50pesosFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_numMonedas50pesosFocusLost(evt);
            }
        });
        txt_numMonedas50pesos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numMonedas50pesosActionPerformed(evt);
            }
        });
        txt_numMonedas50pesos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_numMonedas50pesosKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_numMonedas50pesosKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(txt_numMonedas50pesos, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(txt_numMonedas100pesos, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(txt_numMonedas200pesos, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(txt_numMonedas500pesos, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(txt_numBilletesOMonedasDeMil, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(txt_numBilletes2mil, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(txt_numBilletes5mil, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_numBilletes100mil, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_numBilletes50mil, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_numBilletes20mil, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_numBilletes10mil, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(txt_numBilletes100mil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel8)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_numBilletes50mil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txt_numBilletes20mil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txt_numBilletes10mil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel10)
                    .addComponent(txt_numBilletes5mil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel11)
                    .addComponent(txt_numBilletes2mil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel12)
                    .addComponent(txt_numBilletesOMonedasDeMil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel13)
                    .addComponent(txt_numMonedas500pesos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel14)
                    .addComponent(txt_numMonedas200pesos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel15)
                    .addComponent(txt_numMonedas100pesos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txt_numMonedas50pesos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Totales"));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Base:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("Total:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Diferencia:");

        lbl_baseDeCaja.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_baseDeCaja.setForeground(new java.awt.Color(0, 51, 255));
        lbl_baseDeCaja.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_baseDeCaja.setText("0,00");

        lbl_totalEnCaja.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_totalEnCaja.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_totalEnCaja.setText("0,00");

        lbl_diferencia.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_diferencia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_diferencia.setText("0,00");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_diferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_totalEnCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(lbl_baseDeCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel17)
                    .addComponent(lbl_baseDeCaja))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(lbl_totalEnCaja))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(lbl_diferencia))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        btn_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh256_24854.png"))); // NOI18N
        btn_actualizar.setText("Actualizar");
        btn_actualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(btn_actualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(btn_actualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_nombrePropietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nombrePropietarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombrePropietarioActionPerformed

    private void txt_nombrePropietarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombrePropietarioKeyTyped
        
    }//GEN-LAST:event_txt_nombrePropietarioKeyTyped

    private void txt_numBilletes100milActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numBilletes100milActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_numBilletes100milActionPerformed

    private void txt_numBilletes100milKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numBilletes100milKeyTyped
        //Evalua que se digiten numeros no letras
        char validar = evt.getKeyChar();
                
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(rootPane, "Ingrese solo numeros.");
        }   
            
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 4;
        if(txt_numBilletes100mil.getText().length()== numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"No se permiten mas de 4 caracteres.");
        }    
    }//GEN-LAST:event_txt_numBilletes100milKeyTyped

    private void txt_numBilletes50milActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numBilletes50milActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_numBilletes50milActionPerformed

    private void txt_numBilletes50milKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numBilletes50milKeyTyped
        //Evalua que se digiten numeros no letras
        char validar = evt.getKeyChar();
        
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();            
            JOptionPane.showMessageDialog(rootPane, "Ingrese solo numeros.");
        }
        
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 4;
        if(txt_numBilletes50mil.getText().length()== numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"No se permiten mas de 4 caracteres.");
        } 
    }//GEN-LAST:event_txt_numBilletes50milKeyTyped

    private void txt_numBilletes20milActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numBilletes20milActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_numBilletes20milActionPerformed

    private void txt_numBilletes20milKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numBilletes20milKeyTyped
        //Evalua que se digiten numeros no letras
        char validar = evt.getKeyChar();
        
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();            
            JOptionPane.showMessageDialog(rootPane, "Ingrese solo numeros.");
        }
        
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 4;
        if(txt_numBilletes20mil.getText().length()== numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"No se permiten mas de 4 caracteres.");
        } 
    }//GEN-LAST:event_txt_numBilletes20milKeyTyped

    private void txt_numBilletes10milActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numBilletes10milActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_numBilletes10milActionPerformed

    private void txt_numBilletes10milKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numBilletes10milKeyTyped
        //Evalua que se digiten numeros no letras
        char validar = evt.getKeyChar();
        
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(rootPane, "Ingrese solo numeros.");
        }
        
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 4;
        if(txt_numBilletes10mil.getText().length()== numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"No se permiten mas de 4 caracteres.");
        } 
    }//GEN-LAST:event_txt_numBilletes10milKeyTyped

    private void txt_numBilletes5milActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numBilletes5milActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_numBilletes5milActionPerformed

    private void txt_numBilletes5milKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numBilletes5milKeyTyped
       //Evalua que se digiten numeros no letras
        char validar = evt.getKeyChar();
        
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(rootPane, "Ingrese solo numeros.");
        }
        
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 4;
        if(txt_numBilletes5mil.getText().length()== numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"No se permiten mas de 4 caracteres.");
        } 
    }//GEN-LAST:event_txt_numBilletes5milKeyTyped

    private void txt_numBilletes2milActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numBilletes2milActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_numBilletes2milActionPerformed

    private void txt_numBilletes2milKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numBilletes2milKeyTyped
        //Evalua que se digiten numeros no letras
        char validar = evt.getKeyChar();
        
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();            
            JOptionPane.showMessageDialog(rootPane, "Ingrese solo numeros.");
        }
        
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 4;
        if(txt_numBilletes2mil.getText().length()== numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"No se permiten mas de 4 caracteres.");
        } 
    }//GEN-LAST:event_txt_numBilletes2milKeyTyped

    private void txt_numBilletesOMonedasDeMilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numBilletesOMonedasDeMilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_numBilletesOMonedasDeMilActionPerformed

    private void txt_numBilletesOMonedasDeMilKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numBilletesOMonedasDeMilKeyTyped
        //Evalua que se digiten numeros no letras
        char validar = evt.getKeyChar();
        
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();            
            JOptionPane.showMessageDialog(rootPane, "Ingrese solo numeros.");
        }
        
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 4;
        if(txt_numBilletesOMonedasDeMil.getText().length()== numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"No se permiten mas de 4 caracteres.");
        } 
    }//GEN-LAST:event_txt_numBilletesOMonedasDeMilKeyTyped

    private void txt_numMonedas500pesosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numMonedas500pesosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_numMonedas500pesosActionPerformed

    private void txt_numMonedas500pesosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numMonedas500pesosKeyTyped
        //Evalua que se digiten numeros no letras
        char validar = evt.getKeyChar();
        
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(rootPane, "Ingrese solo numeros.");
        }
        
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 4;
        if(txt_numMonedas500pesos.getText().length()== numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"No se permiten mas de 4 caracteres.");
        } 
    }//GEN-LAST:event_txt_numMonedas500pesosKeyTyped

    private void txt_numMonedas200pesosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numMonedas200pesosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_numMonedas200pesosActionPerformed

    private void txt_numMonedas200pesosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numMonedas200pesosKeyTyped
        //Evalua que se digiten numeros no letras
        char validar = evt.getKeyChar();
        
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(rootPane, "Ingrese solo numeros.");
        }
        
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 4;
        if(txt_numMonedas200pesos.getText().length()== numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"No se permiten mas de 4 caracteres.");
        } 
    }//GEN-LAST:event_txt_numMonedas200pesosKeyTyped

    private void txt_numMonedas100pesosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numMonedas100pesosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_numMonedas100pesosActionPerformed

    private void txt_numMonedas100pesosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numMonedas100pesosKeyTyped
        //Evalua que se digiten numeros no letras
        char validar = evt.getKeyChar();
        
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(rootPane, "Ingrese solo numeros.");
        }
        
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 4;
        if(txt_numMonedas100pesos.getText().length()== numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"No se permiten mas de 4 caracteres.");
        } 
    }//GEN-LAST:event_txt_numMonedas100pesosKeyTyped

    private void txt_numMonedas50pesosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numMonedas50pesosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_numMonedas50pesosActionPerformed

    private void txt_numMonedas50pesosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numMonedas50pesosKeyTyped
        //Evalua que se digiten numeros no letras
        char validar = evt.getKeyChar();
        
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();            
            JOptionPane.showMessageDialog(rootPane, "Ingrese solo numeros.");
        }
        
        //Cuenta la cantidad maxima de caracteres
        int numeroCaracteres = 4;
        if(txt_numMonedas50pesos.getText().length()== numeroCaracteres){
            evt.consume();
            JOptionPane.showMessageDialog(null,"No se permiten mas de 4 caracteres.");
        } 
    }//GEN-LAST:event_txt_numMonedas50pesosKeyTyped

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        
        boolean ventanaEmergCopiaArqueo = false; 
        
        //Mientras los campos esten vacios el boton de finalizar permanecera inactivo
        if(numBilletesDe100Mil.equals("") || numBilletesDe50Mil.equals("") || numBilletesDe20Mil.equals("") || 
                numBilletesDe10Mil.equals("") || numBilletesDe5Mil.equals("") || numBilletesDe2Mil.equals("") || numBilletesOMonedasDeMil.equals("") ||
                numMonedasDe500.equals("") || numMonedasDe200.equals("") || numMonedasDe100.equals("") || numMonedasDe50.equals("")){
        
            JOptionPane.showMessageDialog(null, "Por favor diligencie todos los campos.");
        
        }else{
                      
            arqueoAActualizar.setId(ID);
            arqueoAActualizar.setFecha_arqueo(arqueoControla.fecha_Arqueo());
            arqueoAActualizar.setUsuario(usuarioControla.consultarIdDeunUsuario(usuarioDelSistema));
            arqueoAActualizar.setBase_caja(baseDeCajaOriginal);
            arqueoAActualizar.setNumBilletesDe100Mil(numBilletesDe100Mil);
            arqueoAActualizar.setNumBilletesDe50Mil(numBilletesDe50Mil);
            arqueoAActualizar.setNumBilletesDe20Mil(numBilletesDe20Mil);
            arqueoAActualizar.setNumBilletesDe10Mil(numBilletesDe10Mil);
            arqueoAActualizar.setNumBilletesDe5Mil(numBilletesDe5Mil);
            arqueoAActualizar.setNumBilletesDe2Mil(numBilletesDe2Mil);
            arqueoAActualizar.setNumBilletesOMonedasDeMil(numBilletesOMonedasDeMil);
            arqueoAActualizar.setNumMonedasDe500(numMonedasDe500);
            arqueoAActualizar.setNumMonedasDe200(numMonedasDe200);
            arqueoAActualizar.setNumMonedasDe100(numMonedasDe100);
            arqueoAActualizar.setNumMonedasDe50(numMonedasDe50);
            arqueoAActualizar.setMontoEnBilletes100Mil(montoEnBilletes100Mil);
            arqueoAActualizar.setMontoEnBilletes50Mil(montoEnBilletes50Mil);
            arqueoAActualizar.setMontoEnBilletes20Mil(montoEnBilletes20Mil);
            arqueoAActualizar.setMontoEnBilletes10Mil(montoEnBilletes10Mil);
            arqueoAActualizar.setMontoEnBilletes5Mil(montoEnBilletes5Mil);
            arqueoAActualizar.setMontoEnBilletes2Mil(montoEnBilletes2Mil);
            arqueoAActualizar.setMontoEnBilletesOMonedasMil(montoEnBilletesOMonedasMil);
            arqueoAActualizar.setMontoEnMonedasDe500(montoEnMonedasDe500);
            arqueoAActualizar.setMontoEnMonedasDe200(montoEnMonedasDe200);
            arqueoAActualizar.setMontoEnMonedasDe100(montoEnMonedasDe100);
            arqueoAActualizar.setMontoEnMonedasDe50(montoEnMonedasDe50);
            arqueoAActualizar.setMontoTotalCaja(montoTotalCaja);
            arqueoAActualizar.setDiferenciaTotal(diferenciaTotal);

            //Actualizamos el arqueo en base de datos
            arqueoControla.actualizarArqueo(arqueoAActualizar);
            
            //Aqui modificamos la fila existente y que fue seleccionada en la tabla gestionar arqueos
            Object Fila[] = new Object[4];
            Fila[0] = arqueoControla.fecha_Arqueo();
            Fila[1] = codigoArqueo;
            Fila[2] = usuarioDelSistema;
            Fila[3] = diferenciaTotal;
            
            for(int i=0; i < tablaArqueos.getColumnCount(); i++){
                modelo.setValueAt(Fila[i], GestionarArqueos.Filas, i);
            }

            //Imprimimos el ticket de arqueo de caja
            arqueoControla.generarTicketArqueoDeCaja(codigoArqueo, false);
            
            //Actualizamos los datos totales del gestor de cierres
            arqueoControla.generarEstadisticasMedianteUnCriterioDeterminado(GestionarArqueos.sentenciaSQLUtilizadaTotales);
            lbl_numeroDeArqueos.setText(arqueoControla.cantidadDeArqueosSistema);
            lbl_perdidasEnArqueos.setText(arqueoControla.totalPerdidasArqueos_str);
            arqueoControla.evaluarGravedadDePerdidas();

            ventanaEmergCopiaArqueo = true;

            while(ventanaEmergCopiaArqueo == true){
               String botones[] = {"Imprimir copia", "Cerrar"};
               //El segundo atributo numerico (el numero 1)representa el icono de tipo de mensaje, es decir puede ser informativo de advertencia de error o sin icono
               int eleccionFinalizarArqueo = JOptionPane.showOptionDialog(this, "Arqueo de caja actualizado satisfactoriamente.", "Arqueo de caja", 0, 1, null, botones, this);

               if(eleccionFinalizarArqueo == JOptionPane.YES_OPTION){
                   arqueoControla.generarTicketArqueoDeCaja(codigoArqueo, false); 
               }

               if(eleccionFinalizarArqueo == JOptionPane.NO_OPTION){
                   ventanaEmergCopiaArqueo = false;
                   GestionarArqueos.hayArqueoVisualizandose = false;
                   dispose();   
                } 
            }         
        }       
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void txt_numBilletes100milFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numBilletes100milFocusLost
        
        numBilletesDe100Mil = txt_numBilletes100mil.getText();
                          
        if(numBilletesDe100Mil.equals("")){
            numBilletesDe100Mil = "0";
            txt_numBilletes100mil.setText(numBilletesDe100Mil);   
        }
          
        //restamos lo correspondiente al monto en billetes de 100 mil previamente registrado
        dineroEnCaja = dineroEnCaja - montoEnBilletes100Mil;

        //Obtenemos el numero de billetes actual y volvemos a calcular el monto 
        int intNumBilletes100Mil = Integer.parseInt(numBilletesDe100Mil);
        montoEnBilletes100Mil = 100000 * intNumBilletes100Mil;
        dineroEnCaja = dineroEnCaja + montoEnBilletes100Mil;
        dineroEnCajaString = Integer.toString(dineroEnCaja);
        montoTotalCaja = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
        lbl_totalEnCaja.setText(montoTotalCaja);

        //Calculamos la diferencia respecto a la base en tiempo real
        diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
        diferenciaString = Integer.toString(diferenciaCalculo);
        diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);

        muestreoDiferencia();
    }//GEN-LAST:event_txt_numBilletes100milFocusLost

    private void txt_numBilletes100milFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numBilletes100milFocusGained

    }//GEN-LAST:event_txt_numBilletes100milFocusGained

    private void txt_numBilletes50milFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numBilletes50milFocusGained
        
    }//GEN-LAST:event_txt_numBilletes50milFocusGained

    private void txt_numBilletes50milFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numBilletes50milFocusLost
        
        numBilletesDe50Mil = txt_numBilletes50mil.getText();
                          
        if(numBilletesDe50Mil.equals("")){
            numBilletesDe50Mil = "0";
            txt_numBilletes50mil.setText(numBilletesDe50Mil);   
        }
          
        //restamos lo correspondiente al monto en billetes de 50 mil previamente registrado
        dineroEnCaja = dineroEnCaja - montoEnBilletes50Mil;

        //Obtenemos el numero de billetes actual y volvemos a calcular el monto 
        int intNumBilletes50Mil = Integer.parseInt(numBilletesDe50Mil);
        montoEnBilletes50Mil = 50000 * intNumBilletes50Mil;
        dineroEnCaja = dineroEnCaja + montoEnBilletes50Mil;
        dineroEnCajaString = Integer.toString(dineroEnCaja);
        montoTotalCaja = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
        lbl_totalEnCaja.setText(montoTotalCaja);

        //Calculamos la diferencia respecto a la base en tiempo real
        diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
        diferenciaString = Integer.toString(diferenciaCalculo);
        diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);

        muestreoDiferencia();
    }//GEN-LAST:event_txt_numBilletes50milFocusLost

    private void txt_numBilletes20milFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numBilletes20milFocusLost
        
        numBilletesDe20Mil = txt_numBilletes20mil.getText();
                          
        if(numBilletesDe20Mil.equals("")){
            numBilletesDe20Mil = "0";
            txt_numBilletes20mil.setText(numBilletesDe20Mil);   
        }
          
        //restamos lo correspondiente al monto en billetes de 20 mil previamente registrado
        dineroEnCaja = dineroEnCaja - montoEnBilletes20Mil;

        //Obtenemos el numero de billetes actual y volvemos a calcular el monto 
        int intNumBilletes20Mil = Integer.parseInt(numBilletesDe20Mil);
        montoEnBilletes20Mil = 20000 * intNumBilletes20Mil;
        dineroEnCaja = dineroEnCaja + montoEnBilletes20Mil;
        dineroEnCajaString = Integer.toString(dineroEnCaja);
        montoTotalCaja = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
        lbl_totalEnCaja.setText(montoTotalCaja);

        //Calculamos la diferencia respecto a la base en tiempo real
        diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
        diferenciaString = Integer.toString(diferenciaCalculo);
        diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);

        muestreoDiferencia();
    }//GEN-LAST:event_txt_numBilletes20milFocusLost

    private void txt_numBilletes20milFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numBilletes20milFocusGained
          
    }//GEN-LAST:event_txt_numBilletes20milFocusGained

    private void txt_numBilletes10milFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numBilletes10milFocusLost
        
        numBilletesDe10Mil = txt_numBilletes10mil.getText();
                          
        if(numBilletesDe10Mil.equals("")){
            numBilletesDe10Mil = "0";
            txt_numBilletes10mil.setText(numBilletesDe10Mil);   
        }
          
        //restamos lo correspondiente al monto en billetes de 10 mil previamente registrado
        dineroEnCaja = dineroEnCaja - montoEnBilletes10Mil;

        //Obtenemos el numero de billetes actual y volvemos a calcular el monto 
        int intNumBilletes10Mil = Integer.parseInt(numBilletesDe10Mil);
        montoEnBilletes10Mil = 10000 * intNumBilletes10Mil;
        dineroEnCaja = dineroEnCaja + montoEnBilletes10Mil;
        dineroEnCajaString = Integer.toString(dineroEnCaja);
        montoTotalCaja = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
        lbl_totalEnCaja.setText(montoTotalCaja);

        //Calculamos la diferencia respecto a la base en tiempo real
        diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
        diferenciaString = Integer.toString(diferenciaCalculo);
        diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);

        muestreoDiferencia();       
    }//GEN-LAST:event_txt_numBilletes10milFocusLost

    private void txt_numBilletes10milFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numBilletes10milFocusGained
       
    }//GEN-LAST:event_txt_numBilletes10milFocusGained

    private void txt_numBilletes5milFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numBilletes5milFocusLost
        
        numBilletesDe5Mil = txt_numBilletes5mil.getText();
                          
        if(numBilletesDe5Mil.equals("")){
            numBilletesDe5Mil = "0";
            txt_numBilletes5mil.setText(numBilletesDe5Mil);   
        }
          
        //restamos lo correspondiente al monto en billetes de 5 mil previamente registrado
        dineroEnCaja = dineroEnCaja - montoEnBilletes5Mil;

        //Obtenemos el numero de billetes actual y volvemos a calcular el monto 
        int intNumBilletes5Mil = Integer.parseInt(numBilletesDe5Mil);
        montoEnBilletes5Mil = 5000 * intNumBilletes5Mil;
        dineroEnCaja = dineroEnCaja + montoEnBilletes5Mil;
        dineroEnCajaString = Integer.toString(dineroEnCaja);
        montoTotalCaja = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
        lbl_totalEnCaja.setText(montoTotalCaja);

        //Calculamos la diferencia respecto a la base en tiempo real
        diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
        diferenciaString = Integer.toString(diferenciaCalculo);
        diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);

        muestreoDiferencia();
    }//GEN-LAST:event_txt_numBilletes5milFocusLost

    private void txt_numBilletes5milFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numBilletes5milFocusGained
        
    }//GEN-LAST:event_txt_numBilletes5milFocusGained

    private void txt_numBilletes2milFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numBilletes2milFocusLost
        
        numBilletesDe2Mil = txt_numBilletes2mil.getText();
                          
        if(numBilletesDe2Mil.equals("")){
            numBilletesDe2Mil = "0";
            txt_numBilletes2mil.setText(numBilletesDe2Mil);   
        }
          
        //restamos lo correspondiente al monto en billetes de 2 mil previamente registrado
        dineroEnCaja = dineroEnCaja - montoEnBilletes2Mil;

        //Obtenemos el numero de billetes actual y volvemos a calcular el monto 
        int intNumBilletes2Mil = Integer.parseInt(numBilletesDe2Mil);
        montoEnBilletes2Mil = 2000 * intNumBilletes2Mil;
        dineroEnCaja = dineroEnCaja + montoEnBilletes2Mil;
        dineroEnCajaString = Integer.toString(dineroEnCaja);
        montoTotalCaja = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
        lbl_totalEnCaja.setText(montoTotalCaja);

        //Calculamos la diferencia respecto a la base en tiempo real
        diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
        diferenciaString = Integer.toString(diferenciaCalculo);
        diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);

        muestreoDiferencia();    
    }//GEN-LAST:event_txt_numBilletes2milFocusLost

    private void txt_numBilletes2milFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numBilletes2milFocusGained
      
    }//GEN-LAST:event_txt_numBilletes2milFocusGained

    private void txt_numBilletesOMonedasDeMilFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numBilletesOMonedasDeMilFocusGained
        
    }//GEN-LAST:event_txt_numBilletesOMonedasDeMilFocusGained

    private void txt_numBilletesOMonedasDeMilFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numBilletesOMonedasDeMilFocusLost
      
        numBilletesOMonedasDeMil = txt_numBilletesOMonedasDeMil.getText();
                          
        if(numBilletesOMonedasDeMil.equals("")){
            numBilletesOMonedasDeMil = "0";
            txt_numBilletesOMonedasDeMil.setText(numBilletesOMonedasDeMil);   
        }
          
        //restamos lo correspondiente al monto en billetes o monedas de mil previamente registrado
        dineroEnCaja = dineroEnCaja - montoEnBilletesOMonedasMil;

        //Obtenemos el numero de billetes actual y volvemos a calcular el monto 
        int intNumBilletesDeMil = Integer.parseInt(numBilletesOMonedasDeMil);
        montoEnBilletesOMonedasMil = 1000 * intNumBilletesDeMil;
        dineroEnCaja = dineroEnCaja + montoEnBilletesOMonedasMil;
        dineroEnCajaString = Integer.toString(dineroEnCaja);
        montoTotalCaja = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
        lbl_totalEnCaja.setText(montoTotalCaja);

        //Calculamos la diferencia respecto a la base en tiempo real
        diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
        diferenciaString = Integer.toString(diferenciaCalculo);
        diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);

        muestreoDiferencia();
    }//GEN-LAST:event_txt_numBilletesOMonedasDeMilFocusLost

    private void txt_numMonedas500pesosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numMonedas500pesosFocusLost
        
       numMonedasDe500 = txt_numMonedas500pesos.getText();
                          
        if(numMonedasDe500.equals("")){
            numMonedasDe500 = "0";
            txt_numMonedas500pesos.setText(numMonedasDe500);   
        }
          
        //restamos lo correspondiente al monto en monedas de 500 mil previamente registrado
        dineroEnCaja = dineroEnCaja - montoEnMonedasDe500;

        //Obtenemos el numero de billetes actual y volvemos a calcular el monto 
        int intNumMonedasDe500 = Integer.parseInt(numMonedasDe500);
        montoEnMonedasDe500 = 500 * intNumMonedasDe500;
        dineroEnCaja = dineroEnCaja + montoEnMonedasDe500;
        dineroEnCajaString = Integer.toString(dineroEnCaja);
        montoTotalCaja = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
        lbl_totalEnCaja.setText(montoTotalCaja);

        //Calculamos la diferencia respecto a la base en tiempo real
        diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
        diferenciaString = Integer.toString(diferenciaCalculo);
        diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);

        muestreoDiferencia();
    }//GEN-LAST:event_txt_numMonedas500pesosFocusLost

    private void txt_numMonedas500pesosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numMonedas500pesosFocusGained
      
    }//GEN-LAST:event_txt_numMonedas500pesosFocusGained

    private void txt_numMonedas200pesosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numMonedas200pesosFocusLost
       
        numMonedasDe200 = txt_numMonedas200pesos.getText();
                          
        if(numMonedasDe200.equals("")){
            numMonedasDe200 = "0";
            txt_numMonedas200pesos.setText(numMonedasDe200);   
        }
          
        //restamos lo correspondiente al monto en monedas de 200 mil previamente registrado
        dineroEnCaja = dineroEnCaja - montoEnMonedasDe200;

        //Obtenemos el numero de billetes actual y volvemos a calcular el monto 
        int intNumMonedasDe200 = Integer.parseInt(numMonedasDe200);
        montoEnMonedasDe200 = 200 * intNumMonedasDe200;
        dineroEnCaja = dineroEnCaja + montoEnMonedasDe200;
        dineroEnCajaString = Integer.toString(dineroEnCaja);
        montoTotalCaja = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
        lbl_totalEnCaja.setText(montoTotalCaja);

        //Calculamos la diferencia respecto a la base en tiempo real
        diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
        diferenciaString = Integer.toString(diferenciaCalculo);
        diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);

        muestreoDiferencia();
    }//GEN-LAST:event_txt_numMonedas200pesosFocusLost

    private void txt_numMonedas200pesosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numMonedas200pesosFocusGained
       
    }//GEN-LAST:event_txt_numMonedas200pesosFocusGained

    private void txt_numMonedas100pesosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numMonedas100pesosFocusLost
        
        numMonedasDe100 = txt_numMonedas100pesos.getText();
                          
        if(numMonedasDe100.equals("")){
            numMonedasDe100 = "0";
            txt_numMonedas100pesos.setText(numMonedasDe100);   
        }
          
        //restamos lo correspondiente al monto en monedas de 100 mil previamente registrado
        dineroEnCaja = dineroEnCaja - montoEnMonedasDe100;

        //Obtenemos el numero de billetes actual y volvemos a calcular el monto 
        int intNumMonedasDe100 = Integer.parseInt(numMonedasDe100);
        montoEnMonedasDe100 = 100 * intNumMonedasDe100;
        dineroEnCaja = dineroEnCaja + montoEnMonedasDe100;
        dineroEnCajaString = Integer.toString(dineroEnCaja);
        montoTotalCaja = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
        lbl_totalEnCaja.setText(montoTotalCaja);

        //Calculamos la diferencia respecto a la base en tiempo real
        diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
        diferenciaString = Integer.toString(diferenciaCalculo);
        diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);

        muestreoDiferencia();
    }//GEN-LAST:event_txt_numMonedas100pesosFocusLost

    private void txt_numMonedas100pesosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numMonedas100pesosFocusGained

    }//GEN-LAST:event_txt_numMonedas100pesosFocusGained

    private void txt_numMonedas50pesosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numMonedas50pesosFocusLost
        
        numMonedasDe50 = txt_numMonedas50pesos.getText();
                          
        if(numMonedasDe50.equals("")){
            numMonedasDe50 = "0";
            txt_numMonedas50pesos.setText(numMonedasDe50);   
        }
          
        //restamos lo correspondiente al monto en monedas de 50 mil previamente registrado
        dineroEnCaja = dineroEnCaja - montoEnMonedasDe50;

        //Obtenemos el numero de billetes actual y volvemos a calcular el monto 
        int intNumMonedasDe50 = Integer.parseInt(numMonedasDe50);
        montoEnMonedasDe50 = 50 * intNumMonedasDe50;
        dineroEnCaja = dineroEnCaja + montoEnMonedasDe50;
        dineroEnCajaString = Integer.toString(dineroEnCaja);
        montoTotalCaja = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
        lbl_totalEnCaja.setText(montoTotalCaja);

        //Calculamos la diferencia respecto a la base en tiempo real
        diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
        diferenciaString = Integer.toString(diferenciaCalculo);
        diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);

        muestreoDiferencia();
    }//GEN-LAST:event_txt_numMonedas50pesosFocusLost

    private void txt_numMonedas50pesosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numMonedas50pesosFocusGained
       
    }//GEN-LAST:event_txt_numMonedas50pesosFocusGained

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        cerrarEdicionDeArqueo();
    }//GEN-LAST:event_formWindowClosing

    private void txt_numBilletes100milKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numBilletes100milKeyPressed
              
    }//GEN-LAST:event_txt_numBilletes100milKeyPressed

    private void txt_numBilletes50milKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numBilletes50milKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE){
           
            String cantidad = txt_numBilletes50mil.getText();
            if(!cantidad.equals("")){
                dineroEnCaja = dineroEnCaja - montoEnBilletes50Mil;

                dineroEnCajaString = Integer.toString(dineroEnCaja);

                montoTotalCaja = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
                lbl_totalEnCaja.setText(montoTotalCaja);

                //Calculamos la diferencia respecto a la base en tiempo real
                diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
                diferenciaString = Integer.toString(diferenciaCalculo);
                diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);
                muestreoDiferencia();

                montoEnBilletes50Mil = 0;
            }
        }    
    }//GEN-LAST:event_txt_numBilletes50milKeyPressed

    private void txt_numBilletes20milKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numBilletes20milKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE){
           
            String cantidad = txt_numBilletes20mil.getText();
            if(!cantidad.equals("")){
                
                dineroEnCaja = dineroEnCaja - montoEnBilletes20Mil;
        
                dineroEnCajaString = Integer.toString(dineroEnCaja);

                montoTotalCaja = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
                lbl_totalEnCaja.setText(montoTotalCaja);

                //Calculamos la diferencia respecto a la base en tiempo real
                diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
                diferenciaString = Integer.toString(diferenciaCalculo);
                diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);
                muestreoDiferencia();

                montoEnBilletes20Mil = 0;   
            }
        }
    }//GEN-LAST:event_txt_numBilletes20milKeyPressed

    private void txt_numBilletes10milKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numBilletes10milKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE){
           
            String cantidad = txt_numBilletes10mil.getText();
            if(!cantidad.equals("")){
        
                dineroEnCaja = dineroEnCaja - montoEnBilletes10Mil;

                dineroEnCajaString = Integer.toString(dineroEnCaja);

                montoTotalCaja = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
                lbl_totalEnCaja.setText(montoTotalCaja);

                //Calculamos la diferencia respecto a la base en tiempo real
                diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
                diferenciaString = Integer.toString(diferenciaCalculo);
                diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);
                muestreoDiferencia();

                montoEnBilletes10Mil = 0;
            }
        }
    }//GEN-LAST:event_txt_numBilletes10milKeyPressed

    private void txt_numBilletes5milKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numBilletes5milKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE){
           
            String cantidad = txt_numBilletes5mil.getText();
            if(!cantidad.equals("")){
                
                dineroEnCaja = dineroEnCaja - montoEnBilletes5Mil;
        
                dineroEnCajaString = Integer.toString(dineroEnCaja);

                montoTotalCaja = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
                lbl_totalEnCaja.setText(montoTotalCaja);

                //Calculamos la diferencia respecto a la base en tiempo real
                diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
                diferenciaString = Integer.toString(diferenciaCalculo);
                diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);
                muestreoDiferencia();
                
                montoEnBilletes5Mil = 0;
            }
        }
    }//GEN-LAST:event_txt_numBilletes5milKeyPressed

    private void txt_numBilletes2milKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numBilletes2milKeyPressed

        if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE){
           
            String cantidad = txt_numBilletes2mil.getText();
            if(!cantidad.equals("")){
                
                dineroEnCaja = dineroEnCaja - montoEnBilletes2Mil;
        
                dineroEnCajaString = Integer.toString(dineroEnCaja);

                montoTotalCaja = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
                lbl_totalEnCaja.setText(montoTotalCaja);

                //Calculamos la diferencia respecto a la base en tiempo real
                diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
                diferenciaString = Integer.toString(diferenciaCalculo);
                diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);
                muestreoDiferencia();

                montoEnBilletes2Mil = 0;    
            }
        }
    }//GEN-LAST:event_txt_numBilletes2milKeyPressed

    private void txt_numBilletesOMonedasDeMilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numBilletesOMonedasDeMilKeyPressed

        if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE){
           
            String cantidad = txt_numBilletesOMonedasDeMil.getText();
            if(!cantidad.equals("")){
                
                dineroEnCaja = dineroEnCaja - montoEnBilletesOMonedasMil;
        
                dineroEnCajaString = Integer.toString(dineroEnCaja);

                montoTotalCaja = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
                lbl_totalEnCaja.setText(montoTotalCaja);

                //Calculamos la diferencia respecto a la base en tiempo real
                diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
                diferenciaString = Integer.toString(diferenciaCalculo);
                diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);
                muestreoDiferencia();

                montoEnBilletesOMonedasMil = 0;
            }
        }
    }//GEN-LAST:event_txt_numBilletesOMonedasDeMilKeyPressed

    private void txt_numMonedas500pesosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numMonedas500pesosKeyPressed

        if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE){
           
            String cantidad = txt_numMonedas500pesos.getText();
            if(!cantidad.equals("")){
                
                dineroEnCaja = dineroEnCaja - montoEnMonedasDe500;
        
                dineroEnCajaString = Integer.toString(dineroEnCaja);

                montoTotalCaja = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
                lbl_totalEnCaja.setText(montoTotalCaja);

                //Calculamos la diferencia respecto a la base en tiempo real
                diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
                diferenciaString = Integer.toString(diferenciaCalculo);
                diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);
                muestreoDiferencia();
                
                montoEnMonedasDe500 = 0;   
            }
        }
    }//GEN-LAST:event_txt_numMonedas500pesosKeyPressed

    private void txt_numMonedas200pesosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numMonedas200pesosKeyPressed

        if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE){
           
            String cantidad = txt_numMonedas200pesos.getText();
            if(!cantidad.equals("")){
                
                dineroEnCaja = dineroEnCaja - montoEnMonedasDe200;
        
                dineroEnCajaString = Integer.toString(dineroEnCaja);

                montoTotalCaja = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
                lbl_totalEnCaja.setText(montoTotalCaja);

                //Calculamos la diferencia respecto a la base en tiempo real
                diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
                diferenciaString = Integer.toString(diferenciaCalculo);
                diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);
                muestreoDiferencia();
                
                montoEnMonedasDe200 = 0;
            }
        }
    }//GEN-LAST:event_txt_numMonedas200pesosKeyPressed

    private void txt_numMonedas100pesosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numMonedas100pesosKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE){
           
            String cantidad = txt_numMonedas100pesos.getText();
            if(!cantidad.equals("")){
                
                dineroEnCaja = dineroEnCaja - montoEnMonedasDe100;
        
                dineroEnCajaString = Integer.toString(dineroEnCaja);

                montoTotalCaja = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
                lbl_totalEnCaja.setText(montoTotalCaja);

                //Calculamos la diferencia respecto a la base en tiempo real
                diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
                diferenciaString = Integer.toString(diferenciaCalculo);
                diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);
                muestreoDiferencia();
                
                montoEnMonedasDe100 = 0;
            }
        }
    }//GEN-LAST:event_txt_numMonedas100pesosKeyPressed

    private void txt_numMonedas50pesosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numMonedas50pesosKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE){
           
            String cantidad = txt_numMonedas50pesos.getText();
            if(!cantidad.equals("")){
                
                dineroEnCaja = dineroEnCaja - montoEnMonedasDe50;
        
                dineroEnCajaString = Integer.toString(dineroEnCaja);

                montoTotalCaja = facturaControla.agregarFormatoMoneda(dineroEnCajaString);
                lbl_totalEnCaja.setText(montoTotalCaja);

                //Calculamos la diferencia respecto a la base en tiempo real
                diferenciaCalculo = baseDeCajaInt - dineroEnCaja;
                diferenciaString = Integer.toString(diferenciaCalculo);
                diferenciaTotal = facturaControla.agregarFormatoMoneda(diferenciaString);
                muestreoDiferencia();
                
                montoEnMonedasDe50 = 0;
            }
        }
    }//GEN-LAST:event_txt_numMonedas50pesosKeyPressed

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
            java.util.logging.Logger.getLogger(EditarArqueoDeCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarArqueoDeCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarArqueoDeCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarArqueoDeCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    //Esto cambia la apariencia de la app para que se acomode al Siste Operativo
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    new EditarArqueoDeCaja().setVisible(true);
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    //Logger.getLogger(GestionarFacturas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_actualizar;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbl_baseDeCaja;
    private javax.swing.JLabel lbl_diferencia;
    private javax.swing.JLabel lbl_totalEnCaja;
    public static javax.swing.JTextField txt_nombrePropietario;
    public static javax.swing.JTextField txt_numBilletes100mil;
    public static javax.swing.JTextField txt_numBilletes10mil;
    public static javax.swing.JTextField txt_numBilletes20mil;
    public static javax.swing.JTextField txt_numBilletes2mil;
    public static javax.swing.JTextField txt_numBilletes50mil;
    public static javax.swing.JTextField txt_numBilletes5mil;
    public static javax.swing.JTextField txt_numBilletesOMonedasDeMil;
    public static javax.swing.JTextField txt_numMonedas100pesos;
    public static javax.swing.JTextField txt_numMonedas200pesos;
    public static javax.swing.JTextField txt_numMonedas500pesos;
    public static javax.swing.JTextField txt_numMonedas50pesos;
    // End of variables declaration//GEN-END:variables

    //Metodo que se invoca al cerrar el jFrame
    private void cerrarEdicionDeArqueo(){
        
        String botones[] = {"Si", "No"};
        int eleccion = JOptionPane.showOptionDialog(this, "¿Está seguro que desea cerrar?", "Editar arqueo", 0, 3, null, botones, this);
        
        if(eleccion == JOptionPane.YES_OPTION){
            dispose();
            GestionarArqueos.hayArqueoVisualizandose = false;
            MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = false;
        }
    }
        
    public void muestreoDiferencia(){
        
        if(diferenciaCalculo <= 0){
            lbl_diferencia.setText(diferenciaTotal);
            lbl_diferencia.setForeground(Color.GREEN);
        }
        
        if(diferenciaCalculo > 0){
            lbl_diferencia.setText(diferenciaTotal);
            lbl_diferencia.setForeground(Color.RED);
        }        
    }    
    
    //Metodo que normaliza el formulario
    public void Normalizar(){
        txt_numBilletes100mil.setBackground(Color.WHITE);
        txt_numBilletes50mil.setBackground(Color.WHITE);
        txt_numBilletes20mil.setBackground(Color.WHITE);
        txt_numBilletes10mil.setBackground(Color.WHITE);
        txt_numBilletes5mil.setBackground(Color.WHITE);
        txt_numBilletes2mil.setBackground(Color.WHITE);
        txt_numBilletesOMonedasDeMil.setBackground(Color.WHITE);
        txt_numMonedas500pesos.setBackground(Color.WHITE);
        txt_numMonedas200pesos.setBackground(Color.WHITE);
        txt_numMonedas100pesos.setBackground(Color.WHITE);
        txt_numMonedas50pesos.setBackground(Color.WHITE);
    }
    

}
