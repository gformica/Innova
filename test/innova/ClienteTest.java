/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package innova;


import java.sql.ResultSet;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gabriel
 */


public class ClienteTest {
    
    public ClienteTest() {
    }
    


    /**
     * Test of registrar method, of class Cliente.
     */
    
    @Test
    public void testTodo() {
        registrar();
        buscar();
        eliminar();
    }
    
    public void registrar() {
        System.out.println("Se agregaran 10 clientes");
        Conexion c = new Conexion("innova","postgres","pp");
        for (int i = 0; i < 10; i++) {
            String ci = "V-9900000"+Integer.toString(i);
            String id = "cliente"+Integer.toString(i);
            String nac = "1993-04-1"+Integer.toString(i);
            String dir = "dir"+Integer.toString(i);
            Cliente instance = new Cliente(ci,id,nac,dir);  
            instance.registrar(c);
        }
    }

    /**
     * Test of buscar method, of class Cliente.
     */
    
  
    public void buscar() {
        System.out.println("Se buscaran los clientes agregados");
        Conexion c = new Conexion("innova","postgres","pp");
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

    /**
     * Test of consultar method, of class Cliente.
     */
  
    
    public void eliminar() {
        System.out.println("se eliminaran los clientes agregados");
        Conexion c = new Conexion("innova","postgres","pp");
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