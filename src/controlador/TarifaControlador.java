package controlador;

import clasesDeApoyo.Conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Tarifa;
import org.apache.log4j.Logger;
import static vista.GestionarTarifas.modeloTablaTarifas;
import static vista.GestionarTarifas.table_listaTarifas;

/**
 *
 * @author ALEJO
 */
public class TarifaControlador {
    
    private final Logger log = Logger.getLogger(TarifaControlador.class);
    private URL url = TarifaControlador.class.getResource("Log4j.properties");
    
    //Metodo que carga el contenido dela tabla tarifas de la BD
    public void cargarTarifas(){
        
        //Cargamos los datos de la tabla tarifas
        try {
            modeloTablaTarifas = new DefaultTableModel(){
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
            
            table_listaTarifas.setModel(modeloTablaTarifas);
               
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                        "select Nombre_tarifa, Monto_tarifa, Frecuencia_tarifa, Tarifa_anulada from tarifas");
            
            ResultSet rs = pst.executeQuery();
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();
           
            modeloTablaTarifas.addColumn("Nombre");
            modeloTablaTarifas.addColumn("Monto ($)");
            modeloTablaTarifas.addColumn("Frecuencia");
            modeloTablaTarifas.addColumn("Anulada?");
                                   
            int[] anchosTabla = {10,5,5,5};
            
            for(int x=0; x < cantidadColumnas; x++){
                table_listaTarifas.getColumnModel().getColumn(x).setPreferredWidth(anchosTabla[x]);
            }
            
            while (rs.next()) {
                
                Object[] filas = new Object[cantidadColumnas];
                
                for (int i = 0; i < cantidadColumnas; i++) {
                    
                        filas[i] = rs.getObject(i + 1); 
                }
                modeloTablaTarifas.addRow(filas);
            }
            cn.close();                    
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al llenar tabla de tarifas, ¡Contacte al administrador!");
                log.fatal("ERROR - Se ha producido un error al cargar las tarifas de la BD a la Tabla de tarifas: " + e);
            }
    }
    
    //Metodo que evalua si la tarifa ya existe en el sistema
    public boolean evaluarExistenciaDeTarifa(String tarif){
        
        boolean laTarifaExiste = false;
        //Valida que la tarifa ingresada no exista previamente en la BD  
        try {
            Connection cn4 = Conexion.conectar();
            PreparedStatement pst4;
            pst4 = cn4.prepareStatement(
                        "select Nombre_tarifa from tarifas where Nombre_tarifa = '" + tarif + "'");
            
            ResultSet rs4 = pst4.executeQuery();
            
            if (rs4.next()) {
                laTarifaExiste = true;
                cn4.close();
            } else {
                laTarifaExiste = false;
            }
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al comparar nomParquadero!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al validar la existencia de una tarifa en el sistema: " + ex);
        }
        return laTarifaExiste;
    }
    
    //Metodo que permite crear una tarifa
    public void crearTarifa(Tarifa tarif){
        
        //Inserta la tarifa en la base de datos de tarifas
        try {
            Connection cn3 = Conexion.conectar();
            PreparedStatement pst3 = cn3.prepareStatement(
                "insert into tarifas values (?,?,?,?,?,?,?,?,?,?,?)");

            pst3.setInt(1, tarif.getId());
            pst3.setString(2, tarif.getNombreTarifa());
            pst3.setString(3, tarif.getMontoTarifa());
            pst3.setString(4, tarif.getFrecuenciaTarifa());
            pst3.setString(5, tarif.getTarifaAnulada());
            pst3.setString(6, tarif.getTarifaTieneDescuento());
            pst3.setString(7, tarif.getTiempoDelDescuento());
            pst3.setString(8, tarif.getUnidadDelDescuento());
            pst3.setString(9, tarif.getTarifaCobraTiempoAdicional());
            pst3.setString(10,tarif.getMontoTiempoAdicional());
            pst3.setString(11,tarif.getUnidadDelTiempoAdicional());

            pst3.executeUpdate();
            cn3.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al registrar tarifa!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al crear una tarifa en el sistema: " + e);
        } 
    }
    
     //Metodo que trae un objeto de tipo tarifa con los atributos para su edición o eliminacion
    public Tarifa traerUnaTarifaAlFormulario(String nomTarifa){
        
        Tarifa tarifaRescatada = new Tarifa();
        PreparedStatement ps1 = null;
        try{
            Connection cn1 = Conexion.conectar();          

            ps1 = cn1.prepareStatement("select Nombre_tarifa, Monto_tarifa, Frecuencia_tarifa, Tarifa_anulada, Tiene_descuento, Tiempo_descuento, Unidad_descuento, Cobrar_Tiempo_Ad, Monto_Tiempo_Ad, Unidad_Tiempo_Ad from tarifas where Nombre_tarifa=?");
            ps1.setString(1, nomTarifa);
            ResultSet rs1 = ps1.executeQuery();

            while(rs1.next()){
                tarifaRescatada.setNombreTarifa(rs1.getString("Nombre_tarifa"));
                tarifaRescatada.setMontoTarifa(rs1.getString("Monto_tarifa"));
                tarifaRescatada.setFrecuenciaTarifa(rs1.getString("Frecuencia_tarifa"));
                tarifaRescatada.setTarifaAnulada(rs1.getString("Tarifa_anulada"));
                tarifaRescatada.setTarifaTieneDescuento(rs1.getString("Tiene_descuento"));
                tarifaRescatada.setTiempoDelDescuento(rs1.getString("Tiempo_descuento"));
                tarifaRescatada.setUnidadDelDescuento(rs1.getString("Unidad_descuento"));
                tarifaRescatada.setTarifaCobraTiempoAdicional(rs1.getString("Cobrar_Tiempo_Ad"));
                tarifaRescatada.setMontoTiempoAdicional(rs1.getString("Monto_Tiempo_Ad"));
                tarifaRescatada.setUnidadDelTiempoAdicional(rs1.getString("Unidad_Tiempo_Ad"));
            }
            cn1.close();

        }catch(SQLException e){
           JOptionPane.showMessageDialog(null, "¡¡ERROR al seleccionar tarifa!!, contacte al administrador.");
           log.fatal("ERROR - Se ha producido un error al seleccionar una tarifa de la tabla de tarifas del sistema: " + e); 
        }
        return tarifaRescatada;
    }
    
    //Metodo que permite actualizar una tarifa
    public void actualizarTarifa(String nombreTarifa, Tarifa tarifaAEditar){
        
        try{
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("update tarifas set Nombre_tarifa='"+tarifaAEditar.getNombreTarifa()+"',Monto_tarifa='"+tarifaAEditar.getMontoTarifa()+"',Frecuencia_tarifa='"+tarifaAEditar.getFrecuenciaTarifa()+
                                        "',Tarifa_anulada='"+tarifaAEditar.getTarifaAnulada()+"',Tiene_descuento='"+tarifaAEditar.getTarifaTieneDescuento()+"',Tiempo_descuento='"+tarifaAEditar.getTiempoDelDescuento()+"',Unidad_descuento='"+tarifaAEditar.getUnidadDelDescuento()+"',Cobrar_Tiempo_Ad='"+tarifaAEditar.getTarifaCobraTiempoAdicional()+"',Monto_Tiempo_Ad='"+tarifaAEditar.getMontoTiempoAdicional()+"',Unidad_Tiempo_Ad='"+tarifaAEditar.getUnidadDelTiempoAdicional()+"'where Nombre_tarifa ='"+nombreTarifa+"'");
            pst.executeUpdate();
            cn.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al actualizar tarifa!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar actualizar una tarifa en el sistema: " + e);
        }  
    }
    
    //Metodo que permite consultar el id de una tarifa
    public int consultarIdDeunaTarifa(String nombreTar){
       
        int idTarifa = 0;
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst;
            pst = cn.prepareStatement(
                        "select Id_tarifa from tarifas where Nombre_tarifa = '" + nombreTar + "'");
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                idTarifa = rs.getInt("Id_tarifa");
                cn.close();
           
            } else {
                log.fatal("ERROR - No se ha encontrado el ID de la tarifa requerida");
            }
        }catch (SQLException ex){ 
            log.fatal("ERROR - Se ha producido un error al consultar el ID de una tarifa en el sistema: " + ex); 
        } 
        return idTarifa;
    }
    
    //Metodo que verifica que la tarifa no se este utilizando en el sistema
    public boolean validarSiLaTarifaSeEstaImplementando(int idTarifa){
        
        boolean laTarifaSeEstaImplementando = false;
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst;
            pst = cn.prepareStatement(
                "select Placa, Id_tarifa from vehiculos where Id_tarifa = '"+idTarifa+"'");

            ResultSet rs = pst.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();

            if (rs.next()) {
                laTarifaSeEstaImplementando = true;
                cn.close();
            }else{
                laTarifaSeEstaImplementando = false;
                cn.close();
            }
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al verificar uso de tarifa!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al validar el uso de una tarifa en el sistema: " + ex);
        }
        return laTarifaSeEstaImplementando;
    }
    
    //Metodo que elimina una tarifa de la BD
    public void eliminarTarifa(String tarifaAEliminar){
        
        PreparedStatement ps1 = null;
        try{
            Connection cn1 = Conexion.conectar();          

            ps1 = cn1.prepareStatement("delete from tarifas where Nombre_tarifa=?");
            ps1.setString(1, tarifaAEliminar);
            ps1.execute();

            JOptionPane.showMessageDialog(null, "La tarifa: " + tarifaAEliminar + " ha sido eliminada");
            cn1.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al eliminar tarifa!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar eliminar una tarifa: " + e);
        }
    }
    
    //Metodo que limpia un objeto tarifa
    public void limpiarTarifa(Tarifa tarif){
        tarif.setId(0);
        tarif.setNombreTarifa("");
        tarif.setMontoTarifa("");
        tarif.setFrecuenciaTarifa("");
        tarif.setTarifaAnulada("");
        tarif.setTarifaTieneDescuento("");
        tarif.setTiempoDelDescuento("");
        tarif.setUnidadDelDescuento("");
        tarif.setTarifaCobraTiempoAdicional("");
        tarif.setMontoTiempoAdicional("");
        tarif.setUnidadDelTiempoAdicional("");
    }
}
