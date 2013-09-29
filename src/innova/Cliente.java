/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package innova;
import java.sql.*;

/**
 *
 * @author patricia
 */

public class Cliente {
    private String id;
    private String nombre;
    private String nacimiento;
    private String direccion;

    public Cliente(String id, String nombre, String nacimiento, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.direccion = direccion;
    }
    public Cliente() {
        this.id = null;
        this.nombre = null;
        this.nacimiento = null;
        this.direccion = null;
    }
    
   /*
    * Agrega un cliente a la tabla cliente
    */
    public void registrar(Conexion c) {
        String str = "INSERT INTO CLIENTE (id_cliente, nomb_cliente, nac_cliente,";
        str += " dir_cliente)  VALUES (" ;
        str += "'" + id + "', '" + nombre + "', '" + nacimiento + "', '" + direccion + "');" ;
        c.execute(str);
    }

   /*
    * Devuelve la informacion de un cliente en especifico
    */
    public ResultSet buscar(String id, Conexion c) {
        String str = "SELECT * FROM cliente ";
        str += "WHERE id_cliente =" + "'" + id + "'" + ";";
        
        ResultSet rs = c.query(str);
        return rs;
    }

   /*
    * Lista los clientes registrados
    */
    public ResultSet consultar(Conexion c) {
        String str = "SELECT * FROM cliente;";
        ResultSet rs = c.query(str);
        return rs;
    }
    
   /*
    * Elimina una instancia especifica de la tabla cliente
    */
    public void eliminar(String id, Conexion c) {
        String str = "DELETE FROM cliente WHERE id_cliente=";
        str += "'" + id + "'";
        c.execute(str);
    }
        
    
}