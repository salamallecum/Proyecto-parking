package vista;

import controlador.UsuarioControlador;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.WindowConstants;
import org.apache.log4j.Logger;

/**
 *
 * @author ALEJO
 */
public class MenuAdministrador extends javax.swing.JFrame implements Runnable{

    String user, nombre_usuario;
    String hora, minutos, segundos, ampm;
    Calendar calendario;
    Date fechaHoraActual;
    Thread h1; 
    
    //Variables que monitorean las ventanas del sistema que estanabiertas y cerradas  
    public static boolean hayAlgunaVentanaAbiertaDelSistema = false;
    public static boolean menuAdministrador = false;
    
    /**
     * Creates new form MenuAdministrador
     */    
    public static PanelUsuarios panelUsu;
    PanelCaja panelCaja;
    PanelReportes panelReport;
    PanelVehiculos panelVehi;
    PanelParametros panelMetricas;
    
    private final Logger log = Logger.getLogger(MenuAdministrador.class); 
    private URL url = MenuAdministrador.class.getResource("Log4j.properties");
    
    UsuarioControlador usuControlador = new UsuarioControlador();
    
    public MenuAdministrador() {
        initComponents();
        user = Login.usuario;
        
        //Hilo que ejecuta el reloj en tiempo real
        h1 = new Thread(this);
        h1.start();
                
        setSize(1155,737);
        setResizable(false);
        setTitle("MORE PARKING - Sesión de " + user);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        //Inicio de color a los botones
        btn_Usuarios.setBackground(Color.WHITE); 
        btn_Caja.setBackground(Color.WHITE);
        btn_Reportes.setBackground(Color.WHITE);
        btn_vehiculos.setBackground(Color.WHITE);
        
        //Carga de paneles
        panelUsu = new PanelUsuarios();
        panelUsu.setBounds(289,76,866,600);
        add(panelUsu);
        panelUsu.setVisible(false);
        
        panelCaja = new PanelCaja();
        panelCaja.setBounds(289,76,1000,591);
        add(panelCaja);
        panelCaja.setVisible(false);
        
        panelReport = new PanelReportes();
        panelReport.setBounds(330,220,705,220);
        add(panelReport);
        panelReport.setVisible(false);
        
        panelVehi = new PanelVehiculos();
        panelVehi.setBounds(289,135,1068,595);
        add(panelVehi);
        panelVehi.setVisible(false);
        
        panelMetricas = new PanelParametros();
        panelMetricas.setBounds(330,220,705,364);
        add(panelMetricas);
        panelMetricas.setVisible(false); 
            
        nombre_usuario = usuControlador.cargarInformacionDelUsuario(user);
        lbl_nombreUsuario.setText(nombre_usuario);
    }
    
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/icoParking.png"));
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

        Panel_Administrador = new javax.swing.JPanel();
        btn_Usuarios = new javax.swing.JButton();
        btn_Caja = new javax.swing.JButton();
        btn_Reportes = new javax.swing.JButton();
        btn_vehiculos = new javax.swing.JButton();
        lbl_Imagen = new javax.swing.JLabel();
        btn_parametros = new javax.swing.JButton();
        Panel_Sesion = new javax.swing.JPanel();
        lbl_nombreUsuario = new javax.swing.JLabel();
        btn_cerrarSesion = new javax.swing.JButton();
        Pnl_fotoDelPerfil = new javax.swing.JPanel();
        lbl_fotodePerfil = new javax.swing.JLabel();
        txt_Reloj = new javax.swing.JTextField();
        lbl_bienvenida = new javax.swing.JLabel();
        lbl_buenTurno = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuLogout = new javax.swing.JMenu();
        jMenuItem_cerrarSesion = new javax.swing.JMenuItem();
        menuAbout = new javax.swing.JMenu();
        jMenuItem_AcercaDe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());

        Panel_Administrador.setBackground(new java.awt.Color(51, 51, 255));

        btn_Usuarios.setBackground(new java.awt.Color(255, 255, 255));
        btn_Usuarios.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_Usuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/User.png"))); // NOI18N
        btn_Usuarios.setText("Usuarios");
        btn_Usuarios.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_Usuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Usuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_UsuariosActionPerformed(evt);
            }
        });

        btn_Caja.setBackground(new java.awt.Color(255, 255, 255));
        btn_Caja.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_Caja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Caja.png"))); // NOI18N
        btn_Caja.setText("Caja");
        btn_Caja.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_Caja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Caja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CajaActionPerformed(evt);
            }
        });

        btn_Reportes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_Reportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Reporte.png"))); // NOI18N
        btn_Reportes.setText("Reportes");
        btn_Reportes.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_Reportes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Reportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ReportesActionPerformed(evt);
            }
        });

        btn_vehiculos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_vehiculos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Carro.png"))); // NOI18N
        btn_vehiculos.setText("Vehiculos");
        btn_vehiculos.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_vehiculos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_vehiculos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_vehiculosActionPerformed(evt);
            }
        });

        lbl_Imagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Imagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/imagenMenu.png"))); // NOI18N
        lbl_Imagen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_Imagen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_ImagenMouseClicked(evt);
            }
        });

        btn_parametros.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_parametros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Ajuste.png"))); // NOI18N
        btn_parametros.setText("Parámetros");
        btn_parametros.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_parametros.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_parametros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_parametrosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_AdministradorLayout = new javax.swing.GroupLayout(Panel_Administrador);
        Panel_Administrador.setLayout(Panel_AdministradorLayout);
        Panel_AdministradorLayout.setHorizontalGroup(
            Panel_AdministradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AdministradorLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(Panel_AdministradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_Imagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_Usuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_Caja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_Reportes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_vehiculos, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(btn_parametros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        Panel_AdministradorLayout.setVerticalGroup(
            Panel_AdministradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AdministradorLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lbl_Imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_Usuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btn_Caja, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btn_Reportes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btn_vehiculos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_parametros, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(208, Short.MAX_VALUE))
        );

        Panel_Sesion.setBackground(new java.awt.Color(51, 51, 255));

        lbl_nombreUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl_nombreUsuario.setForeground(new java.awt.Color(255, 255, 255));
        lbl_nombreUsuario.setText("Nombre de Usuario");

        btn_cerrarSesion.setText("Cerrar sesión");
        btn_cerrarSesion.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_cerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cerrarSesionActionPerformed(evt);
            }
        });

        lbl_fotodePerfil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/usuarioDefa.png"))); // NOI18N
        lbl_fotodePerfil.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout Pnl_fotoDelPerfilLayout = new javax.swing.GroupLayout(Pnl_fotoDelPerfil);
        Pnl_fotoDelPerfil.setLayout(Pnl_fotoDelPerfilLayout);
        Pnl_fotoDelPerfilLayout.setHorizontalGroup(
            Pnl_fotoDelPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Pnl_fotoDelPerfilLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbl_fotodePerfil))
        );
        Pnl_fotoDelPerfilLayout.setVerticalGroup(
            Pnl_fotoDelPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_fotodePerfil, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        txt_Reloj.setEditable(false);
        txt_Reloj.setBackground(new java.awt.Color(0, 255, 0));
        txt_Reloj.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txt_Reloj.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_Reloj.setToolTipText("");
        txt_Reloj.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txt_Reloj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_RelojActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_SesionLayout = new javax.swing.GroupLayout(Panel_Sesion);
        Panel_Sesion.setLayout(Panel_SesionLayout);
        Panel_SesionLayout.setHorizontalGroup(
            Panel_SesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_SesionLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(Panel_SesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_Reloj, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(Panel_SesionLayout.createSequentialGroup()
                        .addComponent(Pnl_fotoDelPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_nombreUsuario)
                        .addGap(18, 18, 18)
                        .addComponent(btn_cerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27))
        );
        Panel_SesionLayout.setVerticalGroup(
            Panel_SesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_SesionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_SesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(Pnl_fotoDelPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_nombreUsuario)
                    .addComponent(btn_cerrarSesion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Reloj, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
        );

        lbl_bienvenida.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        lbl_bienvenida.setForeground(new java.awt.Color(51, 51, 255));
        lbl_bienvenida.setText("Bienvenido");

        lbl_buenTurno.setFont(new java.awt.Font("Tahoma", 2, 36)); // NOI18N
        lbl_buenTurno.setText("Buen turno!!! ");

        menuLogout.setText("Sistema");
        menuLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuLogoutMouseClicked(evt);
            }
        });

        jMenuItem_cerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logout.png"))); // NOI18N
        jMenuItem_cerrarSesion.setText("Cerrar sesión");
        jMenuItem_cerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem_cerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem_cerrarSesionMouseClicked(evt);
            }
        });
        jMenuItem_cerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_cerrarSesionActionPerformed(evt);
            }
        });
        menuLogout.add(jMenuItem_cerrarSesion);

        jMenuBar1.add(menuLogout);

        menuAbout.setText("Ayuda");
        menuAbout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuAbout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuAboutMouseClicked(evt);
            }
        });
        menuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAboutActionPerformed(evt);
            }
        });

        jMenuItem_AcercaDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/about.png"))); // NOI18N
        jMenuItem_AcercaDe.setText("Acerca del sistema");
        jMenuItem_AcercaDe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem_AcercaDe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem_AcercaDeMouseClicked(evt);
            }
        });
        jMenuItem_AcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_AcercaDeActionPerformed(evt);
            }
        });
        menuAbout.add(jMenuItem_AcercaDe);

        jMenuBar1.add(menuAbout);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Panel_Administrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Panel_Sesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(203, 203, 203)
                        .addComponent(lbl_bienvenida)
                        .addGap(0, 334, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_buenTurno)
                        .addGap(174, 174, 174))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel_Administrador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Panel_Sesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(166, 166, 166)
                .addComponent(lbl_bienvenida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_buenTurno)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Metodo boton "Cerrar sesion"
    private void btn_cerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerrarSesionActionPerformed
        boolean sePuedeCerrarSesion = usuControlador.cerrarSesion(user);
        
        if(sePuedeCerrarSesion == true){
            dispose();
            new Login().setVisible(true);
            log.info("INFO - El usuario ha cerrado sesión satisfactoriamente");
        }
    }//GEN-LAST:event_btn_cerrarSesionActionPerformed

    //Metodo boton modulo "Usuarios"
    private void btn_UsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_UsuariosActionPerformed
        
        lbl_bienvenida.setVisible(false);
        lbl_buenTurno.setVisible(false);
               
        btn_Usuarios.setBackground(Color.GRAY); 
        btn_Caja.setBackground(Color.WHITE);
        btn_Reportes.setBackground(Color.WHITE);
        btn_vehiculos.setBackground(Color.WHITE);
        btn_parametros.setBackground(Color.WHITE);
        
        
        setSize(1145,720);
        panelUsu.setVisible(true);
        panelCaja.setVisible(false);
        panelReport.setVisible(false);
        panelVehi.setVisible(false);
        panelMetricas.setVisible(false);
        repaint();
        
    }//GEN-LAST:event_btn_UsuariosActionPerformed
    
    private void txt_RelojActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_RelojActionPerformed
        
    }//GEN-LAST:event_txt_RelojActionPerformed

    //Metodo boton modulo "Caja"
    private void btn_CajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CajaActionPerformed
        lbl_bienvenida.setVisible(false);
        lbl_buenTurno.setVisible(false);
        
        btn_Usuarios.setBackground(Color.WHITE); 
        btn_Caja.setBackground(Color.GRAY);
        btn_Reportes.setBackground(Color.WHITE);
        btn_vehiculos.setBackground(Color.WHITE);
        btn_parametros.setBackground(Color.WHITE);
        
        
        setSize(1300,750);
        panelUsu.setVisible(false);
        panelCaja.setVisible(true);
        panelReport.setVisible(false);
        panelVehi.setVisible(false);
        panelMetricas.setVisible(false);
        repaint();
    }//GEN-LAST:event_btn_CajaActionPerformed

    //Metodo boton modulo "Reportes"
    private void btn_ReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ReportesActionPerformed
        lbl_bienvenida.setVisible(false);
        lbl_buenTurno.setVisible(false);
        
        btn_Usuarios.setBackground(Color.WHITE); 
        btn_Caja.setBackground(Color.WHITE);
        btn_Reportes.setBackground(Color.GRAY);
        btn_vehiculos.setBackground(Color.WHITE);
        btn_parametros.setBackground(Color.WHITE);
        
        setSize(1145,700);
        panelUsu.setVisible(false);
        panelCaja.setVisible(false);
        panelReport.setVisible(true);
        panelVehi.setVisible(false);
        panelMetricas.setVisible(false);
        repaint();
    }//GEN-LAST:event_btn_ReportesActionPerformed

    //Metodo boton modulo "Vehiculos"
    private void btn_vehiculosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_vehiculosActionPerformed
        lbl_bienvenida.setVisible(false);
        lbl_buenTurno.setVisible(false);
        
        btn_Usuarios.setBackground(Color.WHITE); 
        btn_Caja.setBackground(Color.WHITE);
        btn_Reportes.setBackground(Color.WHITE);
        btn_vehiculos.setBackground(Color.GRAY);
        btn_parametros.setBackground(Color.WHITE);
        
        setSize(1355,760);
        panelUsu.setVisible(false);
        panelCaja.setVisible(false);
        panelReport.setVisible(false);
        panelVehi.setVisible(true);
        panelMetricas.setVisible(false);
        repaint();
    }//GEN-LAST:event_btn_vehiculosActionPerformed

    //Metodo boton "Parametros"
    private void btn_parametrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_parametrosActionPerformed
        lbl_bienvenida.setVisible(false);
        lbl_buenTurno.setVisible(false);
        
        btn_Usuarios.setBackground(Color.WHITE); 
        btn_Caja.setBackground(Color.WHITE);
        btn_Reportes.setBackground(Color.WHITE);
        btn_vehiculos.setBackground(Color.WHITE);
        btn_parametros.setBackground(Color.GRAY);
        
        
        setSize(1145,700);
        panelUsu.setVisible(false);
        panelCaja.setVisible(false);
        panelReport.setVisible(false);
        panelVehi.setVisible(false);
        panelMetricas.setVisible(true);
        
        repaint();
    }//GEN-LAST:event_btn_parametrosActionPerformed

    //Metodo de cuando damos click en la imagen principal
    private void lbl_ImagenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_ImagenMouseClicked
        lbl_bienvenida.setVisible(false);
        lbl_buenTurno.setVisible(false);
        
        btn_Usuarios.setBackground(Color.WHITE); 
        btn_Caja.setBackground(Color.WHITE);
        btn_Reportes.setBackground(Color.WHITE);
        btn_vehiculos.setBackground(Color.WHITE);
        btn_parametros.setBackground(Color.WHITE);
        
        panelUsu.setVisible(false);
        panelCaja.setVisible(false);
        panelReport.setVisible(false);
        panelVehi.setVisible(false);
        panelMetricas.setVisible(false);
                  
    }//GEN-LAST:event_lbl_ImagenMouseClicked

    private void jMenuItem_cerrarSesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem_cerrarSesionMouseClicked
        
    }//GEN-LAST:event_jMenuItem_cerrarSesionMouseClicked

    private void jMenuItem_cerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_cerrarSesionActionPerformed
        boolean sePuedeCerrarSesion = usuControlador.cerrarSesion(user);
        
        if(sePuedeCerrarSesion == true){
            dispose();
            new Login().setVisible(true);
            log.info("INFO - El usuario ha cerrado sesión satisfactoriamente");
        }
    }//GEN-LAST:event_jMenuItem_cerrarSesionActionPerformed

    private void menuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAboutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuAboutActionPerformed

    private void jMenuItem_AcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_AcercaDeActionPerformed
        new AcercaDe().setVisible(true);
        jMenuItem_AcercaDe.setEnabled(false);
        hayAlgunaVentanaAbiertaDelSistema = true;
        menuAdministrador = true;
        log.info("INFO - Se consulta información acerca del desarrollo del sistema");
    }//GEN-LAST:event_jMenuItem_AcercaDeActionPerformed

    private void jMenuItem_AcercaDeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem_AcercaDeMouseClicked
        
    }//GEN-LAST:event_jMenuItem_AcercaDeMouseClicked

    private void menuLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuLogoutMouseClicked

    }//GEN-LAST:event_menuLogoutMouseClicked

    private void menuAboutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuAboutMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_menuAboutMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel_Administrador;
    private javax.swing.JPanel Panel_Sesion;
    private javax.swing.JPanel Pnl_fotoDelPerfil;
    private javax.swing.JButton btn_Caja;
    private javax.swing.JButton btn_Reportes;
    private javax.swing.JButton btn_Usuarios;
    private javax.swing.JButton btn_cerrarSesion;
    private javax.swing.JButton btn_parametros;
    private javax.swing.JButton btn_vehiculos;
    private javax.swing.JMenuBar jMenuBar1;
    public static javax.swing.JMenuItem jMenuItem_AcercaDe;
    public static javax.swing.JMenuItem jMenuItem_cerrarSesion;
    private javax.swing.JLabel lbl_Imagen;
    private javax.swing.JLabel lbl_bienvenida;
    private javax.swing.JLabel lbl_buenTurno;
    private javax.swing.JLabel lbl_fotodePerfil;
    public static javax.swing.JLabel lbl_nombreUsuario;
    public static javax.swing.JMenu menuAbout;
    private javax.swing.JMenu menuLogout;
    private javax.swing.JTextField txt_Reloj;
    // End of variables declaration//GEN-END:variables
     
    
    //Metodo que calcula la hora para el reloj de tiempo real del menu principal
    public void calcularHora() {
    
        calendario = new GregorianCalendar();
        fechaHoraActual = new Date();
      
        calendario.setTime(fechaHoraActual);
        ampm = calendario.get(Calendar.AM_PM)==Calendar.AM?"AM":"PM";
        
        if(ampm.equals("PM")){
            int h = calendario.get(Calendar.HOUR_OF_DAY)-12;
            hora = h>9?""+h:"0"+h;
        }else{
            hora = calendario.get(Calendar.HOUR_OF_DAY)>9?""+calendario.get(Calendar.HOUR_OF_DAY):"0"+calendario.get(Calendar.HOUR_OF_DAY);
        }
        minutos = calendario.get(Calendar.MINUTE)>9?""+calendario.get(Calendar.MINUTE):"0"+calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND)>9?""+calendario.get(Calendar.SECOND):"0"+calendario.get(Calendar.SECOND);
        
    }
    
    @Override
    public void run() {
        Thread ct = Thread.currentThread();
        while(ct ==h1){
            calcularHora();
            txt_Reloj.setText(hora+":"+minutos+":"+segundos+" "+ampm);
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                log.fatal("ERROR - Se ha producido un error al cargar el reloj de tiempo real en modo Administrador: " + e);
            }
        }
    }
}
