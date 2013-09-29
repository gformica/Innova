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
public class ConsumoTest {
    
    public ConsumoTest() {
    }
     @Test
    public void testTodo() {
        creaServicio();
        registrar();
        buscarTodos();
        eliminar();      
        eliminarServicio();
    } 
  
    
    public void creaServicio() {
        Conexion c = new Conexion("innova","postgres","pp");
        
        String str = "insert into servicio(id_servicio,nomb_servicio,"
                + "desc_servicio,monto) values "
                + "('serv0','serv0','servicio para testing','0.33')";
        c.execute(str);
    }
    
    public void eliminarServicio() {
       Conexion c = new Conexion("innova","postgres","pp");
        
        String str = "delete from servicio where id_servicio='serv0'";
        c.execute(str);
    }
    public void registrar() {
        System.out.println("Se creara un producto, se le asignara a Patricia"
                + " Wilthew, se crearan 10 consumos sobre el servicio 'serv0'");
        
        Conexion c = new Conexion("innova","postgres","pp");
        
        
        int i = 0;
        String id_c = "V-21081301";
        String id_p = "producto"+Integer.toString(i);
        String nombre_p = "producto"+Integer.toString(i);
        Producto instance = new Producto(id_c,id_p,nombre_p);
        
        for (i = 0; i < 10; i++) {
            String fecha = "2007-12-10 13:00:1"+Integer.toString(i);
            int total = 1 + i;
            Consumo cons;
            cons = new Consumo(id_p,"serv0",fecha,"1",Integer.toString(total));
        }
           
         
       
    }
    
    public void buscarTodos() {
        System.out.println("Se buscaran los 10 consumos agregados");
        Conexion c = new Conexion("innova","postgres","pp");
        Consumo instance = new Consumo();
        for (int i = 0; i < 10; i++) {
            try{
                String id = "producto0";
                assertEquals(instance.buscarTodos(id,c).getString(2),"serv0");
            } catch (Exception e) {
                
            }
        }
    }
    
    public void eliminar() {
        System.out.println("Se eliminaran los 10 consumos agregados");
        Conexion c = new Conexion("innova","postgres","pp");
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