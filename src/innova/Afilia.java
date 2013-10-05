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

public class Afilia {
   
    String id_producto;
    String id_plan;
    String fecha_afiliacion;
    String dia_cobro;
    boolean vigente_afilia;
    
    public Afilia() {
        id_producto = null;
        id_plan = null;
        fecha_afiliacion = null;
        dia_cobro = null;
        vigente_afilia = false;
    }
    
    public Afilia(String ipr, String ipl , String fa, String dc, boolean va) {
        id_producto = ipr;
        id_plan = ipl;
        fecha_afiliacion = fa;
        dia_cobro = dc;
        vigente_afilia = va;
    }
    
   /*
    * Agrega una instancia a la tabla afilia
    */
    public void registrar(Conexion c) {
        String str = "insert into afilia";
        str += " " + "(id_producto, id_plan, fecha_afilia, dia_cobro, vigente_afilia)";
        str += " " + "values(" + "'"+ id_producto + "'" + ", '" + id_plan + "' , ";
        str += "'" + fecha_afiliacion + "'" + ", '" + dia_cobro + "' , ";
        str += "'" + vigente_afilia + "'" + ")";
     
        c.execute(str);
        
    }
           
   /*
    * Elimina una instancia de la tabla afilia
    */
    public void eliminar(String id_producto, String id_plan, String fecha, Conexion c) {
        String str = "delete from afilia ";
        str += "where id_producto = " + "'" + id_producto + "'";
        str += " " + "and id_plan = " + "'" + id_plan + "'";
        str += " and fecha_afilia = " + "'" + fecha + "'";
        c.execute(str);
    }
   
   /*
    * Se usa para buscar los datos de afiliacion del producto
    */
    public ResultSet buscar(String id, String fecha, Conexion c) {
        String str = "SELECT * FROM afilia ";
        str += "WHERE id_producto = " + "'" + id + "'";
        if ((fecha != null) && (fecha != " ")) {
            str = str + "and fecha_afilia = " + "'" + fecha + "';";
        }
        
        ResultSet rs = c.query(str);
        return rs;
    }
    
   /*
    * Se usa para cambiar la vigencia de una afiliacion a FALSE
    */    
    public void suspender(String id_producto, Conexion c) {
        String str = "UPDATE afilia SET vigente_afilia = false WHERE id_producto = '";
        str += id_producto + "' and vigente_afilia = true"; 
        c.execute(str);
    }
    
   /*
    * Lista todo los productos
    */
    public ResultSet listar(Conexion c) {
        String str = "SELECT * FROM afilia;";
        ResultSet rs = c.query(str);
        return rs;
    }
    
   /*
    * Devuelve el dia de fecha de corte de un plan
    */
    public int fechaCorte(String id_producto, Conexion c) {
        String str = "SELECT af.dia_cobro FROM afilia af";
        str += " WHERE af.id_producto = " + "'" + id_producto + "'";
        str += " and af.fecha_afilia >= ALL";
        str += " (SELECT ad.fecha_afilia FROM afilia ad";
        str += " WHERE af.id_producto = ad.id_producto)";
        ResultSet rs = c.query(str);
        
        try {
            if (rs.next()) {
                return(Integer.parseInt(rs.getString(1)));
            }
        } catch (Exception e) {
            
        }
        return 0;
    }
    
   /*
    * Devuelve los datos de un plan al cual esta afiliado un producto
    */
    public ResultSet infoPlanAfiliado(String id_producto, Conexion c) {
        String str = "SELECT pl.id_plan, pl.nomb_plan, pl.monto_plan, ";
        str += "pl.tipo_plan, pl.desc_plan FROM afilia af ";
        str += "JOIN plan pl ON (af.id_plan=pl.id_plan) ";
        str += "WHERE af.id_producto = " + "'" + id_producto + "'"
                + " AND af.vigente_afilia='t'";
        ResultSet rs = c.query(str);
        return rs;
    }
    
   /*
    * Devuelve el costo basico del plan que posee un producto 
    */
    public double costoBasicoDePlan(String id_producto, Conexion c){
        ResultSet rs = this.infoPlanAfiliado(id_producto, c);
        double costo = 0;
        try {
             if (rs.next()) {                 
                 costo = Double.parseDouble(rs.getString(3));
             }
        } catch (Exception e) {
            
        }
        
        return costo;
    }
    
    public boolean esParteDelPlan(String id_producto, String id_servicio, 
                                  Conexion c) {
        
        boolean es = false;
        String str = "SELECT con.id_servicio FROM afilia af "
                + "NATURAL JOIN posee p NATURAL JOIN conforma con "
                + "WHERE af.id_producto='"+id_producto+"' AND "
                + "af.vigente_afilia='t'";
        ResultSet rs = c.query(str);
        try {
            while ((rs.next()) && (!es)) {
                es = rs.getString(1).equals(id_servicio);
            }
        } catch (Exception e) {
            
        }
        return es;
    }

}
