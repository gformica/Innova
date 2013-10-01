/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package innova;

/**
 *
 * @author gabriel
 */

public class FacturacionPrepago extends Facturacion {
    
     @Override
     public Factura emitir(String id_producto, String nro_tarjeta, 
            String obs_factura, double monto_recarga, Conexion c) {
        
       Factura factura = new Factura();
       String fecha_hoy = this.obtenerFechaHoy();
       int nro_fact = factura.obtenerNro(c);
       double saldo_actual = 0;
       
       factura = new Factura(Integer.toString(nro_fact), id_producto, fecha_hoy,
                          monto_recarga, true, nro_tarjeta, obs_factura);
       
       factura.registrar(c);

       System.out.println("\n ---FACTURA---");
       System.out.println("Nro: " + nro_fact);
       System.out.println("ID del producto: " + id_producto);
       System.out.println("Fecha: " + fecha_hoy);
       System.out.println("Monto recarga: " + monto_recarga);
       System.out.println("Saldo actual: " + saldo_actual);
       System.out.println("Nro Tarjeta: " + nro_tarjeta);
       System.out.println("Observaciones: " + obs_factura + "\n");
       
       
       return factura;
       
     }
}

