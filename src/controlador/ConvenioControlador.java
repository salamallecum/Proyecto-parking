package controlador;

import clasesDeApoyo.Conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Convenio;
import org.apache.log4j.Logger;
import static vista.GestionarConvenios.modeloTablaConvenios;
import static vista.GestionarConvenios.table_listaConvenios;

/**
 *
 * @author ALEJO
 */
public class ConvenioControlador {
    
   Convenio convenioConsultado = new Convenio(); 
    
   private final Logger log = Logger.getLogger(ConvenioControlador.class);
   private URL url = ConvenioControlador.class.getResource("Log4j.properties");
   
   //Constructor
    public ConvenioControlador() {}
   
    
    //Metodo que carga la tabla de convenios al iniciar en clase gestionarConvenios
    public void cargarTablaConvenios(){
        
        //Cargamos los datos de la tabla
        try {
            modeloTablaConvenios = new DefaultTableModel(){
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
                        
            table_listaConvenios.setModel(modeloTablaConvenios);
               
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                        "select Nombre_convenio, Monto, Frecuencia from convenios");
            
            ResultSet rs = pst.executeQuery();
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();
           
            modeloTablaConvenios.addColumn("Nombre");
            modeloTablaConvenios.addColumn("Monto ($)");
            modeloTablaConvenios.addColumn("Frecuencia");
                        
            int[] anchosTabla = {10,5,10};
            
            for(int x=0; x < cantidadColumnas; x++){
                table_listaConvenios.getColumnModel().getColumn(x).setPreferredWidth(anchosTabla[x]);
            }
            
            while (rs.next()) {
                
                Object[] filas = new Object[cantidadColumnas];
                
                for (int i = 0; i < cantidadColumnas; i++) {
                    
                        filas[i] = rs.getObject(i + 1); 
                }
                modeloTablaConvenios.addRow(filas);
            }
            cn.close();                    
            } catch (SQLException e) {
               JOptionPane.showMessageDialog(null, "Error al llenar tabla de convenios, ¡Contacte al administrador!");
               log.fatal("ERROR - Se ha producido un error al cargar los convenios de la BD a la Tabla de convenios del sistema: " + e);
            }
    }
    
    //Metodo que crea un convenio
    public void crearConvenio(Convenio conv){
        
        //Inserta el convenio en la base de datos de  convenios      
        try {
            Connection cn3 = Conexion.conectar();
            PreparedStatement pst3 = cn3.prepareStatement(
                "insert into convenios values (?,?,?,?)");

            pst3.setInt(1, conv.getId());
            pst3.setString(2, conv.getNombre());
            pst3.setString(3, conv.getMonto());
            pst3.setString(4, conv.getFrecuencia());

            pst3.executeUpdate();
            cn3.close();
        
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al registrar convenio!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al crear un convenio en el sistema: " + e);
        } 
    }
 
    //Metodo que evalua si el convenio ya existe en el sistema
    public boolean validarExistenciaDeConvenio(String nombreConv){
        
        boolean elConvenioExiste = false;
        //Valida que el parqueadero ingresado no exista previamente en la BD  
        try {
            Connection cn4 = Conexion.conectar();
            PreparedStatement pst4;
            pst4 = cn4.prepareStatement(
                        "select Nombre_convenio from convenios where Nombre_convenio = '" + nombreConv + "'");
            
            ResultSet rs4 = pst4.executeQuery();
            
            if (rs4.next()) {
                cn4.close();
                elConvenioExiste = true;
            } else { 
                elConvenioExiste = false;
            }
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al comparar nomParquadero!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al validar la existencia de un convenio en el sistema: " + ex);
        }
        return elConvenioExiste;
    }

    //Metodo que permite actualizar un convenio
    public void actualizarConvenio(String nombreConv, Convenio convenioAEditar){
        
        try{
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("update convenios set Nombre_convenio ='"+convenioAEditar.getNombre()+"',Monto='"+convenioAEditar.getMonto()+"',Frecuencia='"+convenioAEditar.getFrecuencia()+"'where Nombre_convenio ='"+nombreConv+"'");

            pst.executeUpdate();
            cn.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al actualizar convenio!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar actualizar un convenio en el sistema: " + e);
        }  
    }
    
    //Metodo que elimina un convenio de la BD
    public void eliminarConvenio(String convenioAEliminar){
        
        PreparedStatement ps1 = null;
        try{
            Connection cn1 = Conexion.conectar();          

            ps1 = cn1.prepareStatement("delete from convenios where Nombre_convenio=?");
            ps1.setString(1, convenioAEliminar);
            ps1.execute();

            JOptionPane.showMessageDialog(null, "El convenio: " + convenioAEliminar + " ha sido eliminado");
            cn1.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al eliminar convenio!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar eliminar un convenio: " + e);
        }
    }

    //Metodo que evalua si el convnio consultado esta en uso o no
    public boolean evaluarSiElConvenioEstaEnUso(int idConvenio){
        
        boolean convenioEnUso = false;
        try {
            Connection cn4 = Conexion.conectar();
            PreparedStatement pst4;
            pst4 = cn4.prepareStatement(
                        "select * from vehiculos where Id_convenio = '" + idConvenio + "'");
            
            ResultSet rs4 = pst4.executeQuery();
            
            if (rs4.next()) {
                cn4.close();
                convenioEnUso = true;
            } else { 
                convenioEnUso = false;
                cn4.close();
            }
        }catch (SQLException ex) {
            log.fatal("ERROR - Se ha producido un error al validar la existencia de un convenio en el sistema: " + ex);
        }
        return convenioEnUso;
    }
    
    //Metodo que permite consultar el id de un convenio
    public int consultarIdDeunConvenio(String nombreConv){
       
        int idConvenio = 0;
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst;
            pst = cn.prepareStatement(
                        "select Id_convenio from convenios where Nombre_convenio = '" + nombreConv + "'");
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                idConvenio = rs.getInt("Id_convenio");
                cn.close();
           
            } else {
                log.fatal("ERROR - No se ha encontrado el ID del convenio requerido");
            }
        }catch (SQLException ex){ 
            log.fatal("ERROR - Se ha producido un error al consultar el ID de un convenio en el sistema: " + ex); 
        } 
        return idConvenio;
    }

    //Metodo que trae un objeto de tipo convenio con los atributos para su edición o eliminacion
    public Convenio traerUnConvenioAlFormulario(String nomConv){
              
        Convenio convenioRescatado = new Convenio();
        PreparedStatement ps1 = null;
        try{
            Connection cn1 = Conexion.conectar();          

            ps1 = cn1.prepareStatement("select Nombre_convenio, Monto, Frecuencia from convenios where Nombre_convenio=?");
            ps1.setString(1, nomConv);
            ResultSet rs1 = ps1.executeQuery();

            while(rs1.next()){
                convenioRescatado.setNombre(rs1.getString("Nombre_convenio"));
                convenioRescatado.setMonto(rs1.getString("Monto"));
                convenioRescatado.setFrecuencia(rs1.getString("Frecuencia"));
            }
            cn1.close();

        }catch(SQLException e){
           JOptionPane.showMessageDialog(null, "¡¡ERROR al seleccionar convenio!!, contacte al administrador.");
           log.fatal("ERROR - Se ha producido un error al seleccionar un convenio de la tabla de convenios del sistema: " + e); 
        }
        return convenioRescatado;
    }

    //Metodo que permite consultar el nombre de un convenio para su muestreo usando su id
    public String consultarNombreDeConvenioMedianteID(int idConv){
              
        String nombreConvenio = "";
                
        //Consulta el nombre del convenio utilizando su id
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "select Nombre_convenio from convenios where Id_convenio = " + idConv);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                nombreConvenio = rs.getString("Nombre_convenio");   
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al comparar convenio!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar validar el nombre de un convenio utilizando su ID: " + e);
        } 
        return nombreConvenio;
    }
    
    //Metodo que permite consultar la informacion de un convenio en general
    public Convenio consultarUnConvenioMedianteID(int idConv){
                              
        //Consulta el nombre de la tarifa utilizando su id
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "select * from convenios where Id_convenio = " + idConv);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                convenioConsultado.setId(rs.getInt("Id_convenio"));
                convenioConsultado.setNombre(rs.getString("Nombre_convenio"));
                convenioConsultado.setMonto(rs.getString("Monto"));
                convenioConsultado.setFrecuencia(rs.getString("Frecuencia"));              
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al comparar tarifa!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar validar la informacion total de un convenio utilizando su ID: " + e);
        } 
        return convenioConsultado;
    }
}
