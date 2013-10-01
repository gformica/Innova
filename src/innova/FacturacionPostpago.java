/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package innova;


/**
 *
 * @author gabriel
 */
public class FacturacionPostpago extends Facturacion {
    
     @Override
     public Factura emitir(String id_producto, String nro_tarjeta, 
            String obs_factura, double monto_recarga, Conexion c) {
 
        
       Factura factura = new Factura();
       String fecha_hoy = this.obtenerFechaHoy();
       int nro_fact = factura.obtenerNro(c);
       
       //Se verifica si ya es el corte del plan del producto  
       
       if (!this.esFechaDeCorte(id_producto, c)) {
           System.out.println("ERROR : No se puede generar factura ya que no es fecha de corte\n");
           return null;
       }
       

       //Se verifica si la factura para el dia de hoy no se ha generado
       
       if (this.fueGeneradaFacturaPreviamente(id_producto, c)) {
           System.out.println("ERROR: Ya fue generada la factura correspondiente a este mes");
           return null;
       }
       
       
       //Se calcula el costo basico del plan afiliado a dicho producto
       Afilia afilia = new Afilia();
       double costo_plan = afilia.costoBasicoDePlan(id_producto, c);
       
       
       //Se calcula el costo por servicios de renta (paquete) agregados
       Agrega agrega = new Agrega();
       double costo_servicio_renta = agrega.costoServicioRenta(id_producto, c);
       
       
       //Se calcula el costo por consumo de los servicios adicionados
       Adiciona adiciona = new Adiciona();
       double costo_adicional = adiciona.costoServiciosAdicionados(id_producto, c);
       
       
       //Se calcula el costo por los servicios consumidos en exceso
       
       double costo_excesos = agrega.costoExcesosServicios(id_producto, c);

       
       //Se suman los costos
       
       double monto_fact = costo_plan + costo_servicio_renta + costo_adicional;
       monto_fact = monto_fact + costo_excesos;
               
       
       //Ya esta todo listo para generar, registrar e imprimir la factura
       
       factura = new Factura(Integer.toString(nro_fact), id_producto, fecha_hoy,
                          monto_fact, false, nro_tarjeta, obs_factura);
       
       factura.registrar(c);

       System.out.println("\n ---FACTURA---");
       System.out.println("Nro: " + nro_fact);
       System.out.println("ID del producto: " + id_producto);
       System.out.println("Fecha: " + fecha_hoy);
       System.out.println("Monto del plan: " + costo_plan);
       System.out.println("Monto por paquetes agregados: " + costo_servicio_renta);
       System.out.println("Monto por servicios adicionados: " + costo_adicional);
       System.out.println("Monto por servicios consumidos en exceso: " + costo_excesos);
       System.out.println("Monto total: " + monto_fact);
       System.out.println("Nro Tarjeta: " + nro_tarjeta);
       System.out.println("Observaciones: " + obs_factura + "\n");
             
       return factura;

    }
    
}
