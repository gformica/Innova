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

public class ClienteTest {
    
    public ClienteTest() {
    
    }
    
    /*
     * Test de los metodos de la clase Cliente, en orden.
     */
    @Test
    public void testTodo() {
        Conexion c = new Conexion("innova","postgres","pp");
        registrar(c);
        buscar(c);
        eliminar(c);
    }
    
    /*
     * Test del metodo registrar, de la Cliente.
     */
    public void registrar(Conexion c) {
        System.out.println("Se agregaran 10 clientes");
        for (int i = 0; i < 10; i++) {
            String ci = "V-9900000"+Integer.toString(i);
            String id = "cliente"+Integer.toString(i);
            String nac = "1993-04-1"+Integer.toString(i);
            String dir = "dir"+Integer.toString(i);
            Cliente instance = new Cliente(ci,id,nac,dir);  
            instance.registrar(c);
        }
    }

    /*
     * Test del metodo buscar, de la Cliente.
     */
    public void buscar(Conexion c) {
        System.out.println("Se buscaran los clientes agregados");
        Cliente instance = new Cliente();
        for (int i = 0; i < 10; i++) {
            try{
                String id = "V-9900000"+Integer.toString(i);
                String nomb = "cliente"+Integer.toString(i);
                assertEquals(instance.buscar(id, c).getString(2),nomb);
            } catch (Exception e) {
                
            }
        }
    }

    /*
     * Test del metodo eliminar, de la clase Cliente.
     */
    public void eliminar(Conexion c) {
        System.out.println("se eliminaran los clientes agregados");
        Cliente instance = new Cliente();  
        for (int i = 0; i < 10; i++) {
            String id = "V-9900000"+Integer.toString(i);
            instance.eliminar(id,c);
           
            try {
               assertEquals(instance.buscar(id, c).getString(1),null);
                
            } catch (Exception e) {
                
            }
        }
    }
}