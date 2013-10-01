/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package innova;

/**
 *
 * @author patricia
 */

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FachadaFactura {
    
    private String id_producto;
    private String nro_tarjeta;
    private String obs_factura;
    private Facturacion facturacion;
    
    

    public FachadaFactura(String id_producto, String nro_tarjeta, 
                          String obs_factura, Facturacion facturacion){

       this.id_producto = id_producto;
       this.nro_tarjeta = nro_tarjeta;
       this.obs_factura = obs_factura;
       this.facturacion = facturacion;
    }
    
   /*
    * Devuelve e imprime una factura, luego de registrarla
    */
    public Factura emitir(Conexion c){
        return this.facturacion.emitir(id_producto, nro_tarjeta, obs_factura, 0, c);
    }
}
