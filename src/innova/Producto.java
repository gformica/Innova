/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package innova;
import java.sql.*;

/**
 *
 * @author gabriel
 */

public class Producto {
    String nomb_producto;
    String id_producto;
    String id_cliente;
    Double saldo;
    
    public Producto(String np, String ip, String ic, Double s) {
        nomb_producto = np;
        id_producto = ip;
        id_cliente = ic;
        saldo = s;
    }
        
    public Producto() {
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
        str += "'" + id_cliente + "'" + ")" ;
        str += "'" + saldo;
     
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

}
