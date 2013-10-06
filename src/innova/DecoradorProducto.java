/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package innova;

/**
 *
 * @author melecio
 */
public abstract class DecoradorProducto extends Producto {
    
    protected final Producto productoBase;
    
    public DecoradorProducto(Producto p) {
        this.productoBase = p;
    }
    
    @Override
    public String getId() {
        return productoBase.getId();
    }
    
    
    public abstract void adicionarServicio(Conexion c);
    
    
    
    

}
