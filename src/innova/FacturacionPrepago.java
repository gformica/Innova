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
       Producto producto = new Producto();
       
       // Se obtiene la fecha de hoy
       String fecha_hoy = this.obtenerFechaHoy();
       
       // Se obtiene el numero de la nueva factura
       int nro_fact = factura.obtenerNro(c);
         
       // Se calcula el costo basico del plan afiliado a dicho producto
       Afilia afilia = new Afilia();
       double costo_plan = afilia.costoBasicoDePlan(id_producto, c);
     
       // Se calcula el costo por servicios de renta (paquete) agregados  
       Agrega agrega = new Agrega();
       double costo_servicio_renta = agrega.costoServicioRenta(id_producto, c);
       
       // Se aumenta el saldo del producto
       producto.sumarSaldo(id_producto, monto_recarga, c);
      
       Double saldo_actual = producto.obtenerSaldo(id_producto, c);
       double saldo_a_restar = 0.0;
       
       if (saldo_actual < costo_plan) {
           obs_factura += "\n No se pudo renovar el plan";
           // Se agrega a plan NO TIENE PLAN
       }
       else {
           saldo_a_restar = costo_plan;
       }
     
       if (saldo_actual < (costo_plan + costo_servicio_renta)) {
           obs_factura += "\n No se pueden renovar los servicio de renta";
           // Se desafilian los servicios de renta
       }
       else {
           saldo_a_restar += costo_servicio_renta;
       }

       saldo_a_restar = costo_plan + costo_servicio_renta;
       producto.sumarSaldo(id_producto, -1*saldo_a_restar, c);
       
       double nuevo_saldo = saldo_actual - saldo_a_restar;
       // Se crea la factura
       
       factura = new Factura(Integer.toString(nro_fact), id_producto, fecha_hoy,
                          monto_recarga, true, nro_tarjeta, obs_factura);
       
       // Se registra la factura
       
       factura.registrar(c);

       System.out.println("\n ---FACTURA---");
       System.out.println("Nro: " + nro_fact);
       System.out.println("ID del producto: " + id_producto);
       System.out.println("Fecha: " + fecha_hoy);
       System.out.println("Monto recarga: " + monto_recarga);
       System.out.println("Saldo actual: " + nuevo_saldo);
       System.out.println("Nro Tarjeta: " + nro_tarjeta);
       System.out.println("Costo del plan: " + costo_plan + "\n");
       System.out.println("Costo de los servicios de renta: " + 
                            costo_servicio_renta + "\n");
       System.out.println("Observaciones: " + obs_factura + "\n");

       return factura;
       
     }
}

