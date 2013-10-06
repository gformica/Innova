/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package innova;
import java.sql.*;
import java.sql.ResultSet;

/**
 *
 * @author gabriel
 */

public class ProductoSimple extends Producto{
    String nomb_producto;
    String id_producto;
    String id_cliente;
    Double saldo;
    
    public ProductoSimple(String np, String ip, String ic, Double s) {
        nomb_producto = np;
        id_producto = ip;
        id_cliente = ic;
        saldo = s;
    }
    
      
    public String getId() { 
       return id_producto;
    }
        
    public ProductoSimple() {
        nomb_producto = null;
        id_producto = null;
        id_cliente = null;
        saldo = 0.0;
    }
    
   /*
    * Agrega un nuevo producto a la tabla
    */
    public void registrar(Conexion c) {
        String str = "insert into producto";
        str += " " + "(nomb_producto, id_producto, id_cliente, saldo)";
        str += " " + "values";
        str += "(" + "'"+ nomb_producto + "'" + ", " ;
        str += "'" + id_producto + "'" + ", ";
        str += "'" + id_cliente + "', " ;
        str += "'" + saldo + "')" ;
     
        c.execute(str);
        
    }
    
   /*
    * Elimina un producto especifico de la tabla
    */
    public void eliminar(String id_producto, Conexion c) {
        String str = "delete from producto";
        str += " " + "where id_producto=" + "'" + id_producto +  "'";
        c.execute(str);
    }
    
   /*
    * Devuele la informacion de un producto en especifico
    */
    public ResultSet buscar(String id, Conexion c) {
        String str = "SELECT * FROM producto ";
        str += "WHERE id_producto=" + "'" + id + "'" + ";";    
        ResultSet rs = c.query(str);
        return rs;
    }
    
   /*
    * Lista los productos registrados
    */
    public ResultSet listar(Conexion c) {
        String str = "SELECT * FROM producto;";
        ResultSet rs = c.query(str);
        return rs;
    }
    
   /*
    * Aumenta el saldo del producto
    */
    public void sumarSaldo(String id, double aumento, Conexion c) {
        String str = "select p.saldo from producto p where id_producto = '";
        str += id + "';";
        ResultSet rs = c.query(str);
        Double saldo_actual = 0.0;
        
        try { 
                rs.next();
                saldo_actual = rs.getDouble(1); 
                
        } catch (Exception e) {
        
        }
        
        saldo_actual += aumento;
        str = "update producto set saldo = " + saldo_actual ;
        str += " where id_producto = '" + id + "';";
        c.execute(str);
    }
    
   /*
    * Devuelve el saldo del producto 
    */
    public double obtenerSaldo(String id, Conexion c) {
        String str = "select p.saldo from producto p where id_producto = '";
        str += id + "';";
        ResultSet rs = c.query(str);
        Double saldo_actual = 0.0;
        
        try { 
                rs.next();
                saldo_actual = rs.getDouble(1); 
                
        } catch (Exception e) {
        
        }
        
        return saldo_actual;
    }
    
   /*
    * Devuelve el tipo de plan de producto
    */    
    public String obtenerTipoPlan(String id, Conexion c) {
        String str = "SELECT pl.tipo_plan FROM afilia a JOIN plan pl "
                + " ON (pl.id_plan=a.id_plan) "
                + "WHERE a.id_producto='"+id+"';";
        ResultSet rs = c.query(str);
        String tipo_plan = "";
        try {
            if (rs.next()) {
                tipo_plan = rs.getString(1);
            }
        } catch (Exception e) {
            
        }
        return tipo_plan;
        
    }

}
