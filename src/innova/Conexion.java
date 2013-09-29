/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package innova;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author gabriel
 */

public class Conexion {
    
    Connection con;
    Statement st;
    ResultSet rs;  
    
    public Conexion(String dbname, String user, String passwd) {
                  
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("No se encuentra JDBC driver");	
            e.printStackTrace(); 
        }
                          
        try {
            String url = "jdbc:postgresql://localhost/"+dbname;
            con = DriverManager.getConnection(url, user, passwd);
        }

        catch(SQLException e) {
            System.out.println("Hubo un error con la conexion");
            e.printStackTrace(); 
	}
    
    }
    
    public ResultSet query(String str) {
        
         try {
            st = con.createStatement();   
            if (str.charAt(str.length()-1) != ';') {
                str = str+';';
            }
            rs = st.executeQuery(str);
         
            } catch(SQLException e) {
                e.printStackTrace();
        }
         
        return rs;
    }
    
    public void execute(String str) {
        try {
            st = con.createStatement();
       
            st.executeUpdate(str);
        
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
    }    
}
