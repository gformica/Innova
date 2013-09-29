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
public class ProductoTest {
    
    public ProductoTest() {
    }

    /**
     * Test of registrar method, of class Producto.
     */
    
    @Test
    public void testTodo() {
        registrar();
        buscar();
        eliminar();
    }
    
    public void registrar() {
        System.out.println("Se agregaran 10 productos a la cliente Patricia "
                + "Wilthew");
        Conexion c = new Conexion("innova","postgres","pp");
       
        for (int i = 0; i < 10; i++) {
            String id_c = "V-21081301";
            String id_p = "producto"+Integer.toString(i);
            String nombre_p = "producto"+Integer.toString(i);
            Producto instance = new Producto(id_c,id_p,nombre_p);
            
        }    
    }

    public void buscar() {
        System.out.println("Se buscaran los productos recien agregados");
        Conexion c = new Conexion("innova","postgres","pp");
        Cliente instance = new Cliente();
        for (int i = 0; i < 10; i++) {
            try{
                String id = "producto"+Integer.toString(i);
                String nomb = "producto"+Integer.toString(i);
                assertEquals(instance.buscar(id, c).getString(2),nomb);
            } catch (Exception e) {
                
            }
        }
    }
    
    public void eliminar() {
        System.out.println("Se eliminaran los productos agregados");
        Conexion c = new Conexion("innova","postgres","pp");
        Cliente instance = new Cliente();  
        for (int i = 0; i < 10; i++) {
            String id = "producto"+Integer.toString(i);
            instance.eliminar(id,c);
            try {
               assertEquals(instance.buscar(id, c).getString(1),null);       
            } catch (Exception e) {
                
            }
        }
    }

  
  

}