/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package innova;

/**
 *
 * @author gabriel
 */
public abstract class TipoRegistro {
    public abstract boolean registrar(String id_producto, String id_servicio,
            String fecha, String cantidad, String cantidad_total,
            Conexion c);
}
