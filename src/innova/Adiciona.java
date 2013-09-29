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

public class Adiciona {
    String id_producto;
    String id_servicio;
    String fecha_adicion;
    String vigente_adiciona;
    
    public Adiciona() {
        this.id_producto = null;
        this.id_servicio = null;
        this.fecha_adicion = null;
        this.vigente_adiciona = null;
    }

    public Adiciona(String id_producto, String id_servicio, String fecha_adicion, 
                    String vigente_adiciona) {
        this.id_producto = id_producto;
        this.id_servicio = id_servicio;
        this.fecha_adicion = fecha_adicion;
        this.vigente_adiciona = vigente_adiciona;
    }

   /*
    * Devuelve los servicios y los montos que se han adicionado al plan de un producto
    */
    public ResultSet buscar(String id_producto, Conexion c) {
        String str = "SELECT sr.id_servicio, sr.monto ";
        str += "FROM adiciona ad JOIN servicio sr ON ";
        str += "(ad.id_servicio=sr.id_servicio) ";
        str += "WHERE ad.id_producto = " + "'" + id_producto +"'";
        str += " " + "AND ad.vigente_adiciona='t'";
        ResultSet rs = c.query(str);
        return rs;
    }
    
   /*
    * Devuelve el costo por los servicios que adiciono un producto
    */
    public double costoServiciosAdicionados(String id_producto, Conexion c) {   
        //Se buscan todos los servicios adicionados a ese producto
        ResultSet rs = this.buscar(id_producto, c);    
        ResultSet rs2;  
        
        //Para cada uno de ellos se buscan los consumos en el ultimo mes
        double costo = 0;
        try {
             while (rs.next()) {
                 Consumo cons = new Consumo();
                 String servicio = rs.getString(1);
                 double monto = Double.parseDouble(rs.getString(2));
                 rs2 = cons.consumoTotalServicio(id_producto, servicio, c);
                 rs2.next();
                 System.out.print(rs2.getString(1));
                 int consumido = Integer.parseInt(rs2.getString(1));
                 costo = costo + (monto*consumido);
             }
        } catch (Exception e) {
            
        }
        return costo;
    }
           
}
