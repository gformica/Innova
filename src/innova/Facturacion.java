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
 * @author gabriel
 */

public abstract class Facturacion {
    
    public abstract Factura emitir(String id_producto, String nro_tarjeta, 
                                   String obs_factura, double monto_recarga, Conexion c);
    
      /*
    * Devuelve True si es el dia de corte del plan de un producto
    */
   
     public boolean esFechaDeCorte(String id_producto, Conexion c) {
        int hoy = this.queDiaEsHoy();
        Afilia afilia = new Afilia();
        int fecha_corte =  afilia.fechaCorte(id_producto, c);

        return (fecha_corte == hoy);
    }
    
   /*
    * Devuelve el numero correspondiente al dia de hoy (dos digitos)
    */
    private int queDiaEsHoy() {
        DateFormat dateFormat = new SimpleDateFormat("dd");
        Date date = new Date();
        return Integer.parseInt(dateFormat.format(date).toString());
    }
    
   /*
    * Devuelve True si la factura correspondiente a este mes ya fue generada
    */
    public boolean fueGeneradaFacturaPreviamente(String id_producto, Conexion c) {
       Factura factura = new Factura();
       String fecha_hoy = this.obtenerFechaHoy();
       ResultSet rs = factura.buscar(id_producto, c);
       
       try {
            while(rs.next()) {

                if (rs.getString(3).equals(fecha_hoy)){ return true; }
            }
       } catch (Exception e) {
           
       }
    
       return false;
    }
    
       /*
    * Devuele la fecha completa de hoy
    */
    public String obtenerFechaHoy() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date).toString();
    }
    
}
