/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package innova;
import java.sql.ResultSet;

/**
 *
 * @author Gabriel
 */

public class Consumo {
    String id_producto;
    String id_servicio;
    String fecha;
    String cantidad;
    String cantidad_total;

    public Consumo() {
        this.id_producto = null;
        this.id_servicio = null;
        this.fecha = null;
        this.cantidad = null;
        this.cantidad_total = null;
    }
    
    public Consumo(String id_producto, String id_servicio, String fecha, 
                   String cantidad, String cantidad_total) {
        
        this.id_producto = id_producto;
        this.id_servicio = id_servicio;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.cantidad_total = cantidad_total;
    }
    
   
  
    
     public boolean registrar(Conexion c) {

        String str ;
        str = "INSERT INTO CONSUMO (id_producto, id_servicio, fecha_consumo, ";
        str += "cant_consumo, cant_total_consumo) VALUES" + "(";
        str += "'" + id_producto + "', '" + id_servicio + "', '" + fecha ;
        str += "', '" + cantidad + "', '" + cantidad_total + "');" ;
        c.execute(str);
        /*
        Afilia afilia = new Afilia();
        afilia.suspender(id_producto, c);
        * */
        return true;
    }
    
   /*
    * Lista la informacion de todos los consumos realizados
    */
    public ResultSet consultar(Conexion c) {
        String str = "SELECT * FROM CONSUMO;" ;
        ResultSet rs = c.query(str);
        return rs;
    }
    
   /*
    * Busca todos los consumos realizados por un producto especifico
    */
    public ResultSet buscarTodos(String producto, Conexion c) {
        String str = "SELECT * FROM CONSUMO WHERE ID_PRODUCTO = '" + producto + "';" ;
        ResultSet rs = c.query(str);
        return rs;
    }   
            
   /*
    * Devuelve el atributo consumo_total_servicio de la tabla consumo para un
    * producto y un servicio especificos
    */
    public ResultSet consumoTotalServicio(String id_producto, String id_servicio, 
                                    Conexion c) {
        String str ;
        str = "SELECT c.cant_total_consumo FROM consumo c WHERE c.id_producto = "
                + "'" + id_producto + "'" + "AND c.fecha_consumo >= "
                + "ALL (SELECT d.fecha_consumo FROM consumo d WHERE "
                + "c.id_producto = d.id_producto) "
                + "AND c.id_servicio = " + "'" + id_servicio + "'" + ";" ;
        
        ResultSet rs = c.query(str);
        return rs;
    }
    
   /*
    * Elimina las instancias de consumo de un producto, servicio y fecha especificos
    */
    public void eliminar(String id_producto, String id_servicio, String fecha,
                        Conexion c) {
        String str = "DELETE FROM consumo WHERE id_producto="
                + "'"+ id_producto+"' AND id_servicio='" + id_servicio +
                "' AND fecha_consumo='"+fecha+"'";
        c.execute(str);
    }
    

    /*
     * Devuelve la cantidad de plan que ofrece un servicio
     */
    private ResultSet obtenerCantidadDeServicioEnPlan(String id_producto, String id_servicio,
                                          Conexion c) {
        
        String str = "SELECT c.cant_conforma FROM afilia a "
                + "NATURAL JOIN plan p "
                + "NATURAL JOIN posee po "
                + "NATURAL JOIN conforma c "
                + "WHERE a.id_producto='"+id_producto+"' "
                + "AND c.id_servicio='"+id_servicio+"';";
        
        return c.query(str);
    }
    
    /*
     * Convierte un resultset en entero
     */
    private int convertirResultSetAInt(ResultSet rs) {
        try {
            if (rs.next()) {
                return Integer.parseInt(rs.getString(1));
            }
        } catch (Exception e) {
            
        }
        return 0;
    }
    
    /*
     * Convierte un resultset en un double
     */
    private double convertirResultSetADouble(ResultSet rs) {
        try {
            if (rs.next()) 
                return Double.parseDouble(rs.getString(1));
        } catch (Exception e) {
                
        } 
        return 0.0;
    }
    
    /*
     * Devuelve un result ser con el monto por la unidad de un servicio
     */
    private ResultSet buscarMontoUnidadDeServicio(String id_servicio, Conexion c) {
        String str = "SELECT monto FROM servicio WHERE id_servicio='"+
                this.id_servicio+"'";
        return c.query(str);
    }
    
  

}
   
    

