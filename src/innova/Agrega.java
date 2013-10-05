/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package innova;
import java.sql.ResultSet;

/**
 *
 * @author gabriel
 */

public class Agrega {
    String id_producto;
    String id_paquete;
    String fecha_agrega;
    boolean vigente_agrega;

    public Agrega () {
        this.id_producto = null;
        this.id_paquete = null;
        this.fecha_agrega = null;
        this.vigente_agrega = false;
    }
    
    public Agrega(String id_producto, String id_paquete, String fecha_agrega,
                  boolean vigente_agrega) {
        
        this.id_producto = id_producto;
        this.id_paquete = id_paquete;
        this.fecha_agrega = fecha_agrega;
        this.vigente_agrega = vigente_agrega;
    }
    
   /*
    * Agrega una instancia a la tabla agrega
    */
    public void registrar(Conexion c) {
        String str = "insert into agrega ";
        str += "(id_producto, id_paquete, fecha_agrega, ";
        str += "vigente_agrega) values";
        str += "(" + "'"+ this.id_producto + "'" + ", " ;
        str += "'" + this.id_paquete + "'" + ", ";
        str += "'" + this.fecha_agrega + "' " ;
        str += "'" + this.vigente_agrega + "'" + ")";
        this.cambiarVigencia(c);
        c.execute(str);
        
    }
    
   /*
    * Cambia el atributo de Agrega, vigente_agrega a False 
    */
    private void cambiarVigencia(Conexion c) {
        String str = "UPDATE agrega SET vigente_agrega='f' ";
        str += "WHERE id_producto = " + "'" + this.id_producto + "'";
        c.execute(str);
    }
    
   /*
    * Devuelve los paquetes que se han agregado al plan de un producto
    */
    public ResultSet buscarTodos(String id_producto, Conexion c) {
        String str = "SELECT * FROM agrega ";
        str += "WHERE id_producto = " + "'" + id_producto + "'";
        ResultSet rs = c.query(str);
        return rs;
    }
    
   /*
    * Devuelve los datos de los paquetes vigentes agregados para un producto
    */
    public ResultSet buscarVigente(String id_producto, Conexion c) {
        String str = "SELECT * FROM agrega";
        str += "WHERE id_producto = " + "'" + id_producto + "' ";
        str += "AND vigente_agrega='t'";
        ResultSet rs = c.query(str);
        return rs;
    }

   /*
    * Devuelve la informacion de los paquetes agregados por un producto 
    */
    public ResultSet infoPaqueteAfiliado(String id_producto, Conexion c) {
        String str = "SELECT pq.id_paquete, pq.nomb_paquete, ";
        str += "pq.monto_paquete ";
        str += "FROM agrega ag JOIN paquete pq ON (ag.id_paquete=";
        str += "pq.id_paquete)";
        str += "WHERE ag.id_producto = " + "'" + id_producto + "' ";
        str += "AND ag.vigente_agrega='t'";
        ResultSet rs = c.query(str);
        return rs;
    }
    
   /*
    * Devuelve el costo por los servicios que consumio en exceso un producto
    */
    public double costoExcesosServicios(String id_producto, Conexion c) {
        ResultSet paquetes = this.infoPaqueteAfiliado(id_producto, c); 

        double costo = 0;
        
        try {
             if (paquetes.next()) { /*Para cada paquete*/
                String paquete = paquetes.getString(1); //Almacena solo un nombre de paquete
                String str = "select id_servicio, monto, cant_conforma, cost_adic_conforma,";
                str += " cant_consumo from conforma natural join servicio natural join ";
                str += "consumo C natural join agrega where C.id_producto = '"+ id_producto + "'";
                str += "and id_paquete = " + "'" + paquete + "'"; 
                /*Se obtienen los datos necesarios de todos aquellos paquetes (servicios con renta)
                 que ha agregado un producto y que ha consumido*/
                ResultSet rs = c.query(str);
                
                int cant_consumo = 0;
                
                if (rs.next()) {
                    cant_consumo = cant_consumo + Integer.parseInt(rs.getString(5));

                } // Para sumar todos los consumos individuales de un servicio en Consumo

                int cant_conforma = Integer.parseInt(rs.getString(3));
                double cos_adic_conforma = Double.parseDouble(rs.getString(4));

                if (cant_consumo > cant_conforma) {
                    costo = costo + (cant_consumo - cant_conforma) * cos_adic_conforma;
                }

             }
             
        } catch (Exception e) {
            
        }
        
        return costo;    
    }
    
   /*
    * Devuelve el costo por los servicios de renta que agrego un producto
    */
    public double costoServicioRenta(String id_producto, Conexion c) {
        ResultSet rs = this.infoPaqueteAfiliado(id_producto, c);
        double costo = 0;
        try {
             if (rs.next()) {
                 costo = Double.parseDouble(rs.getString(3));
             }
        } catch (Exception e) {
            
        }
        return costo;
    }
    
    public boolean esParteDelServicioRenta(String id_producto,String id_servicio,
                                           Conexion c) {
        
        String str = "SELECT c.cant_conforma FROM agrega a "
             + "NATURAL JOIN conforma c "
             + "WHERE a.id_producto='"+id_producto +"' "
             + "AND c.id_servicio='"+id_servicio+"'";
        
        boolean p = false;
        ResultSet rs = c.query(str);
        try { 
            if (rs.next()) {
                p = true;
            }
        } catch (Exception e) {
            
        }
        
        return p;   
    } 
    
}
