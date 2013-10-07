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
    protected String id;
    
    public DecoradorProducto(Producto p) {
        this.productoBase = p;
    }
    
    @Override
    public String getId() {
        return productoBase.getId();
    }
    
    @Override
    public void setId(String id_producto) {
        this.productoBase.setId(id_producto);
    }
    
    
    public abstract void adicionarServicio(Conexion c);
    
    @Override
    public Producto removerServicio(Conexion c) {
        String id_producto =  getId();  
        String str = "delete from adiciona ";
        str += "where id_producto = " + "'" + id_producto + "' ";
        str += "and id_servicio = " + "'" + this.id +"';";                
        
        c.execute(str);
        
        return productoBase;
    }
    
    
    
    

}
