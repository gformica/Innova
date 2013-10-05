/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package innova;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author patwilthew
 */

public class FacturacionTest {
    
    public FacturacionTest() {
    
    }

    /*
     * Test de los metodos de la clase Facturacion, en orden
     */
    @Test
    public void testTodo() {
        Conexion c = new Conexion("innova","postgres","pp");
        Producto producto = new Producto("Telefono nuevo", "Prod1", "V-21081301", 0.0);
        producto.registrar(c);
        testEsFechaDeCorte(c);
        testEmitir(c);
        testFueGeneradaFacturaPreviamente(c);
        producto.eliminar("Prod1", c);
    }

    /*
     * Test del metodo emitir de la clase Facturacion
     */
    public void testEmitir(Conexion c) {
        System.out.println("Se emitira una factura");
        String nro_tarjeta = "123456789011121";
        String obs_factura = "Factura ejemplo";
        double monto_recarga = 0.0;
        Facturacion instance = new FacturacionImpl();
        Factura result = instance.emitir("Prod1", nro_tarjeta, obs_factura, monto_recarga, c);
        try{
            assertEquals(this.buscar("Prod1", obs_factura, c).getString(7), obs_factura);
        } catch (Exception e) { 
            
        }
        fail("The test case is a prototype.");
    }

    /*
     * Test del metodo esFechaDeCorte, de la clase Facturacion
     */
    public void testEsFechaDeCorte(Conexion c) {
        System.out.println("esFechaDeCorte");
        this.creaPlan(c);
        String fecha = this.obtenerFechaHoy();
        Afilia afiliacion = new Afilia("Prod1", "plan0", fecha, queDiaEsHoy(), true);
        afiliacion.registrar(c);
        Facturacion instance = new FacturacionImpl();
        boolean expResult = true;
        boolean result = instance.esFechaDeCorte("Prod1", c);
        assertEquals(expResult, result);
        eliminarPlan(c);
        afiliacion.eliminar("Prod1", "plan0", "2013/10/05", c);
        fail("The test case is a prototype.");
    }

    /*
     * Test del metodo fueGeneradaFacturaPreviamente de la clase Facturacion
     */
    public void testFueGeneradaFacturaPreviamente(Conexion c) {
        System.out.println("fueGeneradaFacturaPreviamente");
        String id_producto = "Prod1";
        boolean expResult = true;
        Facturacion instance = new FacturacionImpl();
        boolean result = instance.fueGeneradaFacturaPreviamente(id_producto, c);
        assertEquals(expResult, result);
        this.eliminarFactura("Prod1", "Factura ejemplo", c);
        fail("The test case is a prototype.");
    }

    public class FacturacionImpl extends Facturacion {

        public Factura emitir(String id_producto, String nro_tarjeta, String obs_factura, double monto_recarga, Conexion c) {
            return null;
        }

}

      public ResultSet buscar(String id_producto, String obs_factura, Conexion c) {
        String str = "SELECT * FROM factura WHERE id_producto = " ;
        str += "'" + id_producto + "' AND obs_factura = '" + obs_factura + "';";
        ResultSet rs = c.query(str);
        return rs;
    }
      
      public void eliminarFactura(String id_producto, String obs_factura, Conexion c) {
        String str = "DELETE FROM factura WHERE id_producto = " ;
        str += "'" + id_producto + "' AND obs_factura = '" + obs_factura + "';";
        c.execute(str);
      }
    
    /*
     * Crea un plan ejemplo para la prueba de la clase Facturacion
     */
    public void creaPlan(Conexion c) {
        
        String str = "insert into plan(id_plan, nomb_plan, monto_plan, tipo_plan, "
                + "desc_plan) values "
                + "('plan0','Plan 0', 5, 'prepago', 'Plan ejemplo');";
        c.execute(str);
    }
    
    /*
     * Auxiliar para eliminar plan creado para la prueba
     */
    public void eliminarPlan(Conexion c) {
        
        String str = "delete from plan where id_plan = 'plan0';";
        c.execute(str);
    }
    
    /*
     * Auxiliar para tener la fecha de hoy
     */
    public String obtenerFechaHoy() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date).toString();
    }
    
   /*
    * Auxiliar. Devuelve el numero correspondiente al dia de hoy (dos digitos)
    */
    private String queDiaEsHoy() {
        DateFormat dateFormat = new SimpleDateFormat("dd");
        Date date = new Date();
        return dateFormat.format(date).toString();
    }
}