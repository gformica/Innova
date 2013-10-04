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
    
    
   
    public boolean registrarConsumoPrepago(Conexion c) {
        Afilia afilia = new Afilia();
        
        //Se verifica si el servicio es parte del plan
        if (afilia.esParteDelPlan(this.id_producto,this.id_servicio,c)) {
            ResultSet rs = this.cantidadDeServicioEnPlan(id_producto, id_servicio, c);    
            int cantidad_servicio = this.resultSetToInt(rs);
            rs = this.consumoTotalServicio(id_producto, id_servicio, c);
            int total_consumido = this.resultSetToInt(rs);
            int cantidad = Integer.parseInt(this.cantidad);
            
            if (total_consumido >= cantidad_servicio) {
                double monto;
                monto = this.resultSetToDouble(this.buscarMontoUnidadDeServicio(id_servicio, c));
                this.cobrarPorUnidad(monto, c);
                
            }
            else {
               if (total_consumido + cantidad <= cantidad_servicio) {
                   //se agrega consumo normal
               } 
               else {
                   int del_plan = cantidad_servicio - total_consumido;
                   //se agrega el consumo normal
                   
                   int excedente = cantidad - del_plan;
                   double y = excedente*cantidad;
                   this.cobrarPorUnidad(y,c);  
               }
            }
            
        }
        
        //Si no es parte del plan se busca cuanto consumio
        double monto_consumo = 0;
        Producto producto = new Producto();

        if (producto.obtenerSaldo(id_producto, c) >= monto_consumo) {
            String str ;
            str = "INSERT INTO CONSUMO (id_producto, id_servicio, fecha_consumo, ";
            str += "cant_consumo, cant_total_consumo) VALUES" + "(";
            str += "'" + id_producto + "', '" + id_servicio + "', '" + fecha ;
            str += "', '" + cantidad + "', '" + cantidad_total + "');" ;
            c.execute(str);
            
            return true;
        } else {
            afilia.suspender(id_producto, c);
            return false;
        }
        
    }
    
     public boolean registrarConsumoPostpago(Conexion c) {

        String str ;
        str = "INSERT INTO CONSUMO (id_producto, id_servicio, fecha_consumo, ";
        str += "cant_consumo, cant_total_consumo) VALUES" + "(";
        str += "'" + id_producto + "', '" + id_servicio + "', '" + fecha ;
        str += "', '" + cantidad + "', '" + cantidad_total + "');" ;
        c.execute(str);
        
        Afilia afilia = new Afilia();
        afilia.suspender(id_producto, c);
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
    
     
    private ResultSet cantidadDeServicioEnPlan(String id_producto, String id_servicio,
                                          Conexion c) {
        
        String str = "SELECT c.cant_conforma FROM afilia a "
                + "NATURAL JOIN plan p "
                + "NATURAL JOIN posee po "
                + "NATURAL JOIN conforma c "
                + "WHERE a.id_producto='"+id_producto+"' "
                + "AND c.id_servicio='"+id_servicio+"';";
        
        return c.query(str);
    }
    
    private int resultSetToInt(ResultSet rs) {
        try {
            if (rs.next()) {
                return Integer.parseInt(rs.getString(1));
            }
        } catch (Exception e) {
            
        }
        return 0;
    }
    
    private double resultSetToDouble(ResultSet rs) {
        try {
            if (rs.next()) 
                return Double.parseDouble(rs.getString(1));
        } catch (Exception e) {
                
        } 
        return 0.0;
    }
    
    private ResultSet buscarMontoUnidadDeServicio(String id_servicio, Conexion c) {
        String str = "SELECT monto FROM servicio WHERE id_servicio='"+
                this.id_servicio+"'";
        return c.query(str);
    }
    
    private void cobrarPorUnidad(double monto, Conexion c) {
        
        monto = this.resultSetToDouble(this.buscarMontoUnidadDeServicio(this.id_servicio, c));
        double restar = monto*Integer.parseInt(this.cantidad);
        //Restar saldo a producto
        Producto prod = new Producto();
        prod.restarSaldo(this.id_producto, restar, c);
    }
}
   
    

