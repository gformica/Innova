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

public class ConsumoTest {
    
    public ConsumoTest() {
  
    }
    
    /*
     * Test de los metodos de la clase Consumo, en orden
     */
    @Test
    public void testTodo() {
        Conexion c = new Conexion("innova","postgres","pp");
        creaServicio(c);
        registrar(c);
        buscarTodos(c);
        eliminar(c);      
        eliminarServicio(c);
    } 
  
    /*
     * Crea un servicio ejemplo para la prueba de la clase Consumo
     */
    public void creaServicio(Conexion c) {
        
        String str = "insert into servicio(id_servicio,nomb_servicio,"
                + "desc_servicio,monto) values "
                + "('serv0','serv0','servicio para testing','0.33')";
        c.execute(str);
    }
    
    /*
     * Elimina el servicio ejemplo creado
     */
    public void eliminarServicio(Conexion c) {
        
        String str = "delete from servicio where id_servicio='serv0'";
        c.execute(str);
    }
    
    /*
     * Test del metodo registrar de la clase Consumo
     */
    public void registrar(Conexion c) {
        System.out.println("Se creara un producto, se le asignara a Patricia"
                + " Wilthew, se crearan 10 consumos sobre el servicio 'serv0'");        
        
        int i = 0;
        String id_c = "V-21081301";
        String id_p = "producto"+Integer.toString(i);
        String nombre_p = "producto"+Integer.toString(i);
        Producto instance = new Producto(id_c,id_p,nombre_p, 20.0);
        
        for (i = 0; i < 10; i++) {
            String fecha = "2007-12-10 13:00:1"+Integer.toString(i);
            int total = 1 + i;
            Consumo cons;
            cons = new Consumo(id_p,"serv0",fecha,"1",Integer.toString(total));
        }     
       
    }
    
    /*
     * Test del metodo buscarTodos de la clase Consumo
     */
    public void buscarTodos(Conexion c) {
        System.out.println("Se buscaran los 10 consumos agregados");
        Consumo instance = new Consumo();
        for (int i = 0; i < 10; i++) {
            try{
                String id = "producto0";
                assertEquals(instance.buscarTodos(id,c).getString(2),"serv0");
            } catch (Exception e) {
                
            }
        }
    }
    
    /*
     * Test del metodo eliminar de la clase Consumo
     */
    public void eliminar(Conexion c) {
        System.out.println("Se eliminaran los 10 consumos agregados");
        Consumo instance = new Consumo();  
        for (int i = 0; i < 10; i++) {
            String fecha = "2007-12-10 13:00:1" + Integer.toString(i);
            instance.eliminar("producto0","serv0",fecha,c);
            try {
               assertEquals(instance.buscarTodos("producto0", c).getString(1),
                            null);       
            } catch (Exception e) {
                
            }
        }
        
    }
}