/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package innova;

import java.sql.ResultSet;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gabriel
 */
public class FacturaTest {
    
    public FacturaTest() {
    }

    @Test
    public void testTodo() {
        registrar();
        buscar();
        eliminar();        
    } 
  
    
    public void registrar() {
        System.out.println("Se creara un producto, se le asignara a Patricia"
                + " Wilthew, y se agregaran 10 facturas pagadas a ese producto");
        Conexion c = new Conexion("innova","postgres","pp");
       
        int i = 0;
        String id_c = "V-21081301";
        String id_p = "producto"+Integer.toString(i);
        String nombre_p = "producto"+Integer.toString(i);
        Producto instance = new Producto(id_c,id_p,nombre_p);
        
        for (i = 0; i < 10; i++) {
            String nro = "9999"+Integer.toString(i);
            String fecha_fact = "2007-12-1"+Integer.toString(i);
        
            String pagada = "t";
            String tarjeta = "1234567";
            Factura fact = new Factura(nro,id_p,fecha_fact,30.0,true,tarjeta,"");
        }
            
         
       
    }
    
    public void buscar() {
        System.out.println("Se buscaran las 10 facturas agregadas");
        Conexion c = new Conexion("innova","postgres","pp");
        Factura instance = new Factura();
        for (int i = 0; i < 10; i++) {
            try{
                String id = "producto0";
                String nro_fact = "9999"+Integer.toString(i);
                assertEquals(instance.buscar(id, c).getString(1),nro_fact);
            } catch (Exception e) {
                
            }
        }
    }
    
    public void eliminar() {
        System.out.println("Se eliminaran las 10 facturas agregadas");
        Conexion c = new Conexion("innova","postgres","pp");
        Cliente instance = new Cliente();  
        for (int i = 0; i < 10; i++) {
            String nro = "9999"+Integer.toString(i);
            instance.eliminar(nro,c);
            try {
               assertEquals(instance.buscar(nro, c).getString(1),null);       
            } catch (Exception e) {
                
            }
        }
        
    }
}