/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package innova;

/**
 *
 * @author melecio
 */
public abstract class Producto {

    public abstract String getId();
    
    public abstract void setId(String id);
    
    public abstract Producto removerServicio(Conexion c);
    
}