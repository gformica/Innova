/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package innova;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author melecio
 */
class AlquilerDecodificador extends DecoradorProducto {
    public AlquilerDecodificador(Producto p) {
        super(p);
        this.id = "tvCS0001";
    }
    
    @Override
    public void adicionarServicio(Conexion c){
        String id_producto =  super.getId();
        
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date();
        String fecha_adicion = dateFormat.format(date1).toString();

        
        String str = "insert into adiciona ";
        str += "(id_producto, id_servicio, fecha_adicion, vigente_adiciona)";
        str += " " + "values";
        str += "(" + "'"+ id_producto + "'" + ", " ;
        str += "'" + this.id + "'" + ", ";
        str += "'" + fecha_adicion + "'" + ",";
        str += "'" + "t" + "'" + ")";
        c.execute(str);
        
    }
    
}
