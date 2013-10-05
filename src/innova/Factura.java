/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package innova;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author patricia
 */

public class Factura {
    private String nro_factura;
    private String id_producto;
    private String fecha_factura;
    private double monto_factura;
    private boolean pagada_factura;
    private String nro_tarjeta;
    private String obs_factura;

    public Factura(String nro_factura, String id_producto, String fecha_factura, double monto_factura, boolean pagada_factura, String nro_tarjeta, String obs_factura) {
        this.nro_factura = nro_factura;
        this.id_producto = id_producto;
        this.fecha_factura = fecha_factura;
        this.monto_factura = monto_factura;
        this.pagada_factura = pagada_factura;
        this.nro_tarjeta = nro_tarjeta;
        this.obs_factura = obs_factura;
    }
    
        public Factura() {
        this.nro_factura = null;
        this.id_producto = null;
        this.fecha_factura = null;
        this.monto_factura = 0.0;
        this.pagada_factura = false;
        this.nro_tarjeta = null;
        this.obs_factura = null;
    }
    
   /*
    * Agrega una instancia a la tabla factura
    */
    public void registrar(Conexion c) {
        String str = "INSERT INTO FACTURA (NRO_FACTURA, ID_PRODUCTO, "
        + "FECHA_FACTURA, MONTO_FACTURA, PAGADA_FACTURA, NRO_TARJETA, "
        + "OBS_FACTURA) VALUES (" ;
        str += "'" + nro_factura + "', '" + id_producto + "', '";
        str += fecha_factura + "', '" + monto_factura + "', '";
        str += pagada_factura + "', '" + nro_tarjeta + "', '" ;
        str += obs_factura + "');";
        c.execute(str);             
    }

   /*
    * Devuelve Las instancias de la tabla factura
    */
    public ResultSet consultar(Conexion c) {
        String str = "SELECT * FROM factura;";
        ResultSet rs = c.query(str);
        return rs;
    }
    
   /*
    * Devuelve las facturas de un producto
    */
    public ResultSet buscar(String id_producto, Conexion c) {
        String str = "SELECT * FROM factura WHERE id_producto = " ;
        str += "'" + id_producto + "'";
        ResultSet rs = c.query(str);
        return rs;
    }
    
   /*
    * Elimina una instancia de la tabla factura
    */
    public void eliminar(String nro, Conexion c) {
        String str = "DELETE FROM factura WHERE nro_factura=";
        str += "'" + nro + "'"; 
        c.execute(str);
     }
     
   /*
    * Obtiene el proximo numero disponible para una nueva factura
    */
    public int obtenerNro(Conexion c) {
        String str = "Select MAX(F.nro_factura) from factura F ";
        ResultSet rs = c.query(str);
        try {
            if (rs.next()) {
                return Integer.parseInt(rs.getString(1)) + 1;
            }
        } catch (Exception e) {
            
        }
        return 0;

    }

}
