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
public class FachadaConsumo {
    
    String id_producto;
    String id_servicio;
    String cantidad;
    Consumo consumo;
    
    public FachadaConsumo(String id_producto, String id_servicio, String cantidad) {
        this.id_producto = id_producto;
        this.id_servicio = id_servicio;
        this.cantidad = cantidad;
        this.consumo = null;
    }

    public boolean registrarConsumoPrepago(Conexion c) {
        Afilia afilia = new Afilia();
        
        //Se verifica si el servicio es parte del plan
        if (afilia.esParteDelPlan(this.id_producto,this.id_servicio,c)) {
            ResultSet rs = this.cantidadDeServicioEnPlan(this.id_producto, this.id_servicio, c);    
            int cantidad_servicio = this.resultSetToInt(rs);
            rs = this.consumoTotalServicio(this.id_producto, this.id_servicio, c);
            int total_consumido = this.resultSetToInt(rs);
            int cantidad = Integer.parseInt(this.cantidad);
            
            if (total_consumido >= cantidad_servicio) {
                double monto;
                monto = this.resultSetToDouble(this.buscarMontoUnidadDeServicio(this.id_servicio, c));
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
    }
    
    public boolean registrar(Conexion c) {
        return this.consumo.registrar(c);
    }
    
    
    /*
     * Devuelve en un ResultSet la cantidad de servicio que tiene un producto
     * en su plan afiliado
     */
   
    private ResultSet cantidadDeServicioEnPlan(String id_producto, String id_servicio,Conexion c) {
        
        String str = "SELECT c.cant_conforma FROM afilia a "
                + "NATURAL JOIN plan p "
                + "NATURAL JOIN posee po "
                + "NATURAL JOIN conforma c "
                + "WHERE a.id_producto='"+id_producto+"' "
                + "AND c.id_servicio='"+id_servicio+"';";
        
        return c.query(str);
    }
    
    /*
     * Convierte el primer string de un result set a un entero. Retorna 0 si
     * si el ResultSet es nulo o no puede convertir el entero
     */
    
    private int resultSetToInt(ResultSet rs) {
        try {
            if (rs.next()) {
                return Integer.parseInt(rs.getString(1));
            }
        } catch (Exception e) {
            
        }
        return 0;
    }
    
    
    /*
     * Convierte el primer string de un result set a un double. Retorna 0 si
     * si el ResultSet es nulo o no puede convertir a double
     */
    
    private double resultSetToDouble(ResultSet rs) {
        try {
            if (rs.next()) 
                return Double.parseDouble(rs.getString(1));
        } catch (Exception e) {
                
        } 
        return 0.0;
    }
    
    /*
     * Devuelve el monto de la unidad de un servicio
     */
    
    private ResultSet buscarMontoUnidadDeServicio(String id_servicio, Conexion c) {
        String str = "SELECT monto FROM servicio WHERE id_servicio='"+
                this.id_servicio+"'";
        return c.query(str);
    }
    
    /*
     * Metodo auxiliar que cobra al producto 
     */
    
    private void cobrarPorUnidad(double monto, Conexion c) {
        
        monto = this.resultSetToDouble(this.buscarMontoUnidadDeServicio(this.id_servicio, c));
        double restar = monto*Integer.parseInt(this.cantidad);
        //Restar saldo a producto
        Producto prod = new Producto();
        prod.restarSaldo(this.id_producto, restar, c);
    }
    
}
