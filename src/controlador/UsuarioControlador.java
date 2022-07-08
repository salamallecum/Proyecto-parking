package controlador;

import clasesDeApoyo.Conexion;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import modelo.Usuario;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.Logger;
import vista.Login;
import static vista.Login.txt_clave;
import static vista.Login.txt_usuario;
import vista.MenuAdministrador;
import vista.MenuUsuario;
import vista.PanelCaja;
import vista.PanelUsuarios;
import static vista.PanelUsuarios.Table_listaUsuarios;
import static vista.PanelUsuarios.modelo;


/**
 *
 * @author ALEJO
 */
public class UsuarioControlador {
    
    Usuario usuarioConsultado = new Usuario(0, "", "", "", "", "", "", "", "");  
       
    private final Logger log = Logger.getLogger(UsuarioControlador.class);
    private URL url = UsuarioControlador.class.getResource("/clasesDeApoyo/Log4j.properties");

    //Constructor
    public UsuarioControlador() {} 
    
    //Metodo que valida que no exista el usuario ingresado en la BD
    public boolean validarExistenciaDeUsuarioEnBD(String usu){
        
        boolean usuarioExiste = false;
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "select Usuario from usuarios where Usuario = '" + usu + "'");
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                cn.close();
                usuarioExiste = true;
            } else {
                cn.close();
                usuarioExiste = false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al comparar usuario!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al validar la existencia de un usuario en el sistema: " + e);   
        }
        return usuarioExiste;
    }
    
    //Metodo que crea usuarios
    public void crearUsuario(Usuario usu){
        
        //Inserta el registro en la base de datos
        try {
            Connection cn2 = Conexion.conectar();
            PreparedStatement pst2 = cn2.prepareStatement(
                "insert into usuarios values (?,?,?,?,?,?,?,?,?)");

            pst2.setInt(1, usu.getId());
            pst2.setString(2, usu.getNombres());
            pst2.setString(3, usu.getApellidos());
            pst2.setString(4, usu.getCelular());
            pst2.setString(5, usu.getTelefono());
            pst2.setString(6, usu.getUsuario());
            pst2.setString(7, usu.getClave());
            pst2.setString(8, usu.getRol());
            pst2.setString(9, usu.getActivo());

            pst2.executeUpdate();
            cn2.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al crear usuario!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al crear un usuario en el sistema: " + e);
        }
    }
    
    //Metodo de edita usuarios
    public void modificarUsuario(Usuario usuarioModificado){
        
        try{
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("update usuarios set Nombres ='"+usuarioModificado.getNombres()+"',Apellidos='"+usuarioModificado.getApellidos()+"',Celular='"+usuarioModificado.getCelular()+"',Telefono='"+usuarioModificado.getTelefono()+"',Clave='"+usuarioModificado.getClave()+"',Rol='"+usuarioModificado.getRol()+"',Activo='"+usuarioModificado.getActivo()+"' where id_usuario ='"+usuarioModificado.getId()+"'");

            pst.executeUpdate();
            cn.close(); 
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al actualizar usuario!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al modificar un usuario del sistema: " + e);
        }   
    }
    
    //Metodo que consulta la información de un usuario
    public Usuario consultarUsuario(String usuario){
        
        //Hace la consulta de registros a la base de datos
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "select * from usuarios where Nombres = '" + usuario + "'");
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
                usuarioConsultado.setId(rs.getInt("Id_usuario"));
                usuarioConsultado.setNombres(rs.getString("Nombres"));
                usuarioConsultado.setApellidos(rs.getString("Apellidos"));
                usuarioConsultado.setCelular(rs.getString("Celular"));
                usuarioConsultado.setTelefono(rs.getString("Telefono"));
                usuarioConsultado.setUsuario(rs.getString("Usuario"));               
                usuarioConsultado.setClave(rs.getString("Clave"));
                usuarioConsultado.setRol(rs.getString("Rol"));
                usuarioConsultado.setActivo(rs.getString("Activo"));                 
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar información de usuario!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al consultar un usuario en el sistema: " + e);
        }
        return usuarioConsultado;
    }
    
    //Metodo que elimina un usuario
    public void eliminarUsuario(String usu){
        
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("delete from usuarios where Nombres= '"+ usu + "'");
            pst.executeUpdate(); 

            int filaSelec = PanelUsuarios.Table_listaUsuarios.getSelectedRow();
            PanelUsuarios.Table_listaUsuarios.getValueAt(filaSelec, 0).toString();

            modelo.removeRow(filaSelec);

            JOptionPane.showMessageDialog(null, "El usuario: " + usu + " ha sido eliminado.");            

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al eliminar!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar eliminar el usuario " + usu +" : " + e);
        }
    }
    
    //Metodo de login
    public boolean logueo(String usu, String pass){
    
        String rolDeIngreso = "";
        String estaActivo = "";
        boolean login = false;
        
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "select Rol, Activo from usuarios where Usuario = '" + usu
                    + "' and Clave = '" + pass + "'");

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                
                rolDeIngreso = rs.getString("Rol");
                estaActivo = rs.getString("Activo");

                if (rolDeIngreso.equalsIgnoreCase("Administra") && estaActivo.equalsIgnoreCase("Si")) {
                    login = true;
                    //Permito el ingreso al panel de administrador
                    new MenuAdministrador().setVisible(true);
                    log.info("INFO - El usuario Administrador: "+ usu +" se ha logueado satisfactoriamente.");
                } else if (rolDeIngreso.equalsIgnoreCase("Usuario") && estaActivo.equalsIgnoreCase("Si")) {
                    login = true;
                    //Permito el ingreso al panel de usuario
                    new MenuUsuario().setVisible(true);
                    log.info("INFO - El usuario Operario: "+ usu +" se ha logueado satisfactoriamente.");
                }else{
                    login = false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
                txt_usuario.setText("");
                txt_clave.setText("");
                log.warn("ADVERTENCIA - Se intentó acceder al sistema con datos erróneos");
            }          

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al iniciar aplicación!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error en el Login del sistema: " + e);
        }
        return login;
    }
    
    //Metodo que carga la info del usuario en el menu principal al iniciar sesion
    public String cargarInformacionDelUsuario(String usu){
        
        String nombre_usuario = "";
        //Carga los nombres del usuario que ingresa al sistema
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
            "select Nombres from usuarios where Usuario = '" + usu + "'");
            
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                nombre_usuario = rs.getString("Nombres");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión desde la interfaz Administrador");
            log.fatal("ERROR - Se ha producido un error al cargar los nombres del usuario en la ventana administrador: " + e);
        }
        return nombre_usuario;
    }

    //Metodo que cierra la sesión del usuario
    public boolean cerrarSesion(String usu){
        
        boolean cajaAbierta = false;
        
        if(PanelCaja.laCajaFueAbierta == true){
            cajaAbierta = true;
            JOptionPane.showMessageDialog(null, "La Caja se encuentra abierta, debe generar el cierre!!");
            log.warn("ADVERTENCIA - Se intentó cerrar sesión con la caja aún abierta con el usuario:" + usu);
        }else if(PanelCaja.laCajaFueAbierta == false){
            cajaAbierta = false;        
            new Login().setVisible(true);
            log.info("INFO - El usuario ha cerrado sesión satisfactoriamente");
        }
        return cajaAbierta;
    }

    //Metodo que genera el reporte PDF del listado de los usuarios registrados
    public void generarReportePDFdeUsuariosRegistrados(){
        
        try{
            Connection cn3 = Conexion.conectar();

            JasperReport reporte = null;
            //String path = "src\\Reportes\\ListadoUsuarios.jasper";

            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/ListadoUsuarios.jasper")); 
            
            JasperPrint jprint = JasperFillManager.fillReport(reporte, null, cn3);
            
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            view.setIconImage(getIconImagePDFUser());
            view.setTitle("Reporte de usuarios registrados");
            log.info("INFO - Reporte de vehiculos registrados generado satisfactoriamente.");            
        
        }catch(JRException ex){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al generar Reporte de Usuarios, contacte al administrador!!");
            log.fatal("ERROR - Se ha producido un error al intentar generar reporte PDF de los usuarios del sistema: " + ex);
        } 
    }
    
    //Metodo que agrega el icono a la ventana de reporte PDF de usuarios
    public Image getIconImagePDFUser() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/User.png"));
        return retValue;
    }

    //Metodo que busca un criterio de busqueda de un uauario y carga resultados mientras uno escribe
    public void busquedaUsuario(String texto){
        try{
            String [] titulos = {"Nombres", "Apellidos", "Celular", "Telefono", "Usuario", "Rol"};
            String filtro = ""+texto+"_%";
            String SQL = "select Nombres, Apellidos, Celular, Telefono, Usuario, Rol from usuarios where Nombres like "+'"'+filtro+'"';
            modelo = new DefaultTableModel(null, titulos);
            
            Connection cn6 = Conexion.conectar();
            PreparedStatement pst6 = cn6.prepareStatement(SQL);
            ResultSet rs6 = pst6.executeQuery(SQL);
            
            String[] fila = new String[6];
            
            while(rs6.next()){
                fila[0]=rs6.getString("Nombres");
                fila[1]=rs6.getString("Apellidos");
                fila[2]=rs6.getString("Celular");
                fila[3]=rs6.getString("Telefono");
                fila[4]=rs6.getString("Usuario");
                fila[5]=rs6.getString("Rol");
                modelo.addRow(fila);
            }
            Table_listaUsuarios.setModel(modelo);
            rs6.close();
            cn6.close();
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "ERROR en la busqueda del usuario, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al buscar el usuario: " + texto +" "+ ex);
        }
    }  

    //Metodo que carga la tabla de usuarios por Default al abrir el panel de usuarios
    public void cargarTablaDeUsuariosPorDefault(){
        
        try {
            modelo = new DefaultTableModel(){
                //Permite que no se puedan editar las filas de la tabla
                @Override
                public boolean isCellEditable(int row, int column) {
                    if(column >= 0){
                        return false;
                    }else{
                        return false;
                    }
                }
            };
            
            Table_listaUsuarios.setModel(modelo);
               
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                        "select Nombres, Apellidos, Celular, Telefono, Usuario, Rol from usuarios");
            
            ResultSet rs = pst.executeQuery();
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();
            
            modelo.addColumn("Nombres");
            modelo.addColumn("Apellidos");
            modelo.addColumn("Celular");
            modelo.addColumn("Telefono");
            modelo.addColumn("Usuario");
            modelo.addColumn("Rol");
            
            int[] anchosTabla = {20,20,20,20,20,20};
            
            for(int x=0; x < cantidadColumnas; x++){
                Table_listaUsuarios.getColumnModel().getColumn(x).setPreferredWidth(anchosTabla[x]);
            }
            
            while (rs.next()) {
                
                Object[] filas = new Object[cantidadColumnas];
                
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modelo.addRow(filas);
                }
                cn.close();        
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al llenar tabla de usuarios, ¡Contacte al administrador!");
            log.fatal("ERROR - Se ha producido un error al cargar los usuarios de la BD a la Tabla de usuarios: " + e);
        }
    }
}

