/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package innova;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gabriel
 */

public class ProductoTest {
    
    public ProductoTest() {
    }

    /*
     * Test de los metodos de Producto, en orden
     */
    @Test
    public void testTodo() {
        Conexion c = new Conexion("innova","postgres","pp");
        registrar(c);
        buscar(c);
        eliminar(c);
    }
    
    /*
     * Test del metodo registrar de la clase Producto
     */
    public void registrar(Conexion c) {
        System.out.println("Se agregaran 10 productos a la cliente Patricia "
                + "Wilthew");
       
        for (int i = 0; i < 10; i++) {
            String id_c = "V-21081301";
            String id_p = "producto"+Integer.toString(i);
            String nombre_p = "producto"+Integer.toString(i);
            Producto instance = new Producto(id_c,id_p,nombre_p, 20.0);
            
        }    
    }

    /*
     * Test del metodo buscar de la clase Buscar
     */
    public void buscar(Conexion c) {
        System.out.println("Se buscaran los productos recien agregados");
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
    
    /*
     * Test del metodo eliminar de la clase producto
     */
    public void eliminar(Conexion c) {
        System.out.println("Se eliminaran los productos agregados");
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