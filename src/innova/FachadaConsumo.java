/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package innova;
import java.sql.ResultSet;
import java.sql.Timestamp;

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

    /*
     * Registra un consumo de un producto prepago en la base de datos
     */
    public boolean registrarConsumoPrepago(Conexion c) {
      
        if (!this.tieneSaldo(c)) {
            return false;
        }
        
        Afilia afilia = new Afilia();
        String fechahora = this.obtenerFechaHora();
      
        //Se verifica si el servicio es parte del plan
        if (afilia.esParteDelPlan(this.id_producto,this.id_servicio,c)) {
            int cantidadServicio = this.cantidadDeServicioEnPlan(this.id_producto, this.id_servicio, c);    
            Consumo auxCons = new Consumo();
            ResultSet rs = auxCons.consumoTotalServicio(this.id_producto, this.id_servicio, c);
            int totalConsumido = this.resultSetToInt(rs);
            
            
            if (totalConsumido >= cantidadServicio) {
                double monto;
                monto = this.buscarMontoUnidadDeServicio(this.id_servicio, c);
                //verificar saldo del cliente
                if (!this.consumirMaximoQuePueda(c)) 
                    return false;
                int x = Integer.parseInt(this.cantidad);     
                String nuevoTotalConsumido;
                nuevoTotalConsumido = String.valueOf(x + totalConsumido);
                this.consumo = new Consumo(this.id_producto,this.id_servicio,fechahora,
                                           this.cantidad,nuevoTotalConsumido);
                     
            } else {
               if (totalConsumido + Integer.parseInt(this.cantidad) > cantidadServicio) {
                   int cantidad = Integer.parseInt(this.cantidad);
           
                   int delPlan = cantidadServicio - totalConsumido;
                   int excedente = cantidad - delPlan;
                   this.cantidad = String.valueOf(excedente);
                   
                   this.consumirMaximoQuePueda(c);
                   String nuevaCantidad = String.valueOf(delPlan + Integer.parseInt(this.cantidad));
                   String nuevoTotalConsumido = String.valueOf(nuevaCantidad + totalConsumido);

                   this.consumo = new Consumo(this.id_producto,this.id_servicio, 
                                      fechahora, nuevaCantidad,nuevoTotalConsumido);
                   
               }
            }
        } else {
            Consumo auxCons = new Consumo();
            ResultSet rs = auxCons.consumoTotalServicio(this.id_producto, this.id_servicio, c);
            int totalConsumido = this.resultSetToInt(rs);
            double monto;
            monto = this.buscarMontoUnidadDeServicio(this.id_servicio, c);
            //verificar saldo del cliente
            if (!this.consumirMaximoQuePueda(c)) 
                return false;
            int x = Integer.parseInt(this.cantidad); 
            
            String nuevoTotalConsumido;
            nuevoTotalConsumido = String.valueOf(x + totalConsumido);
            this.consumo = new Consumo(this.id_producto,this.id_servicio,fechahora,
                                       this.cantidad,nuevoTotalConsumido);
        }
        
        this.consumo.registrar(c);
        return true;
    }
    
    /*
     * Registra un consumo exitoso en la base de datos
     */
    public boolean registrar(Conexion c) {
        return this.consumo.registrar(c);
    }
    
    /*
     * Devuelve en un ResultSet la cantidad de servicio que tiene un producto
     * en su plan afiliado
     */
    private int cantidadDeServicioEnPlan(String id_producto, String id_servicio,Conexion c) {
        
        String str = "SELECT c.cant_conforma FROM afilia a "
                + "NATURAL JOIN plan p "
                + "NATURAL JOIN posee po "
                + "NATURAL JOIN conforma c "
                + "WHERE a.id_producto='"+id_producto+"' "
                + "AND c.id_servicio='"+id_servicio+"';";
        
        int cantidad = 0;
        ResultSet rs = c.query(str);
        try {
            cantidad = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            
        }
        return cantidad;
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
    private double buscarMontoUnidadDeServicio(String id_servicio, Conexion c) {
        String str = "SELECT monto FROM servicio WHERE id_servicio='"+
                this.id_servicio+"'";
        double monto = 0.0;
        ResultSet rs = c.query(str);
        try {
            if (rs.next())
                monto = Double.parseDouble(rs.getString(1));
        } catch (Exception e) {
            
        }
        
        return monto;
    }
    
    /*
     * Metodo auxiliar que resta el saldo del producto
     */
    private boolean consumirMaximoQuePueda(Conexion c) {
        
        double monto = this.buscarMontoUnidadDeServicio(this.id_servicio, c);
        int cantidad = Integer.parseInt(this.cantidad);
      
        Producto producto = new Producto();
        double saldo = producto.obtenerSaldo(this.id_producto, c);
       
        int i;
        for (i = 1; i <= cantidad; i++) { 
            if (saldo - monto*i < 0)
                break;
        }
        i--;
        this.cantidad = String.valueOf(i);
        cantidad = Integer.parseInt(this.cantidad);

        producto.sumarSaldo(this.id_producto, (-1)*(monto*cantidad), c);
        return i > 0;
    }
    
    /*
     * Devuelve true si un cliente posee saldo
     */
    private boolean tieneSaldo(Conexion c) {
        return ((new Producto()).obtenerSaldo(this.id_producto, c) > 0);
    }

    /*
     * Devuelve la fecha y hora actual
     */
    private String obtenerFechaHora() {
          java.util.Date date = new java.util.Date();
          String h = (new Timestamp(date.getTime())).toString();
          return (h.split("\\.")[0]);
    }
}
