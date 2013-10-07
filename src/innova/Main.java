/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package innova;
import java.util.Scanner;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author gabriel
 */

public class Main {
    
    public static void main(String[] args) {
        
        
       
        String dbname = "innova" ;
        String user = "postgres" ;
        String passwd = "pp";
        Conexion inn = new Conexion(dbname,user,passwd);
        String msj;
        int opcion;
      
        msj = "-----GESTION DE COBROS-----";
        System.out.println(msj);
        
        msj = "\n Marque el numero correspondiente a la accion que desea realizar: " ;
        msj = msj + "\n 1) Gestion de Clientes.";
        msj = msj + "\n 2) Gestion de Productos.";
        msj = msj + "\n 3) Gestion de Afiliaciones.";
        msj = msj + "\n 4) Gestion de Servicios Adicionales.";
        msj = msj + "\n 5) Gestion de Consumos.";
        msj = msj + "\n 6) Gestion de Facturas. \n";
        msj = msj + "\n Opcion nro: ";
        System.out.println(msj);
        
        Scanner in = new Scanner(System.in);
        opcion = in.nextInt();
        
        switch(opcion){
            case 1: msj = "---GESTION CLIENTES--- \n Acciones: \n 1) Registrar \n " ; 
                    msj = msj + "2) Buscar \n 3) Consultar \n Opcion nro: " ; 
                    
                    System.out.println(msj);
                    in = new Scanner(System.in);
                    opcion = in.nextInt();
                
                    switch(opcion){
                        case 1: System.out.println("---REGISTRAR--- \n Cedula del cliente a registrar (V-xxxxxxxx): ");
                                Scanner sc1 = new Scanner(System.in);
                                String cedula = sc1.nextLine();

                                System.out.println("Nombre del cliente: ");
                                Scanner sc2 = new Scanner(System.in);
                                String nombre = sc2.nextLine();

                                System.out.println("Fecha de nacimiento (aaaa/mm/dd): ");
                                Scanner sc3 = new Scanner(System.in);
                                String nacimiento = sc3.nextLine();

                                System.out.println("Direccion: ");
                                Scanner sc4 = new Scanner(System.in);
                                String direccion = sc4.nextLine();

                                Cliente cliente = new Cliente(cedula, nombre, nacimiento, direccion);
                                cliente.registrar(inn);
                        break;

                        case 2: System.out.println("---BUSCAR--- \n Cedula del cliente a buscar: ");
                                Scanner sc5 = new Scanner(System.in);
                                String cedula_a_buscar = sc5.nextLine();
                                Cliente cliente2 = new Cliente();
                                ResultSet Datos = cliente2.buscar(cedula_a_buscar, inn);
                                try {
                                        Datos.next();
                                        System.out.println("Nombre: ");
                                        System.out.println(Datos.getString(2));
                                        System.out.println("Fecha de nacimiento: ");
                                        System.out.println(Datos.getString(3));
                                        System.out.println("Direccion: ");
                                        System.out.println(Datos.getString(4));
                                } catch (Exception e){

                                }                            
                        break;

                        case 3: System.out.println("---LISTA DE CLIENTES--- ");
                                Cliente cliente3 = new Cliente();
                                ResultSet ConsultaClientes = cliente3.consultar(inn);
                                try {
                                        while (ConsultaClientes.next()) {
                                        System.out.println("Cedula: ");
                                        System.out.println(ConsultaClientes.getString(1));
                                        System.out.println("Nombre: ");
                                        System.out.println(ConsultaClientes.getString(2));
                                        System.out.println("Fecha de nacimiento: ");
                                        System.out.println(ConsultaClientes.getString(3));
                                        System.out.println("Direccion: ");
                                        System.out.println(ConsultaClientes.getString(4));
                                        System.out.println("\n");
                                        }
                                } catch (Exception e){

                                }         
                    break;    
                }
            break;
                
            case 2: msj = "---GESTION PRODUCTOS--- \n Acciones: \n 1) Registrar \n " ; 
                    msj = msj + "2) Buscar \n 3) Eliminar \n 4) Listar \n"
                            + " 5) Recargar saldo \n Opcion nro: " ; 
                    
                    System.out.println(msj);
                    in = new Scanner(System.in);
                    opcion = in.nextInt();
                
                    switch(opcion){
                        case 1: System.out.println("---REGISTRAR--- \n Nombre del producto a registrar: ");
                                Scanner sc1 = new Scanner(System.in);
                                String nombre = sc1.nextLine();

                                System.out.println("ID del producto: ");
                                Scanner sc2 = new Scanner(System.in);
                                String id = sc2.nextLine();

                                System.out.println("Cedula del cliente (V-xxxxxxxx): ");
                                Scanner sc3 = new Scanner(System.in);
                                String cedula = sc3.nextLine();

                                Producto producto = new Producto(nombre, id, cedula, 0.0);
                                producto.registrar(inn);
                        break;

                        case 2: System.out.println("---BUSCAR--- \n ID del producto a buscar: ");
                                Scanner sc4 = new Scanner(System.in);
                                String id_a_buscar = sc4.nextLine();
                                Producto producto2 = new Producto();
                                ResultSet DatosProducto = producto2.buscar(id_a_buscar, inn);
                                try {
                                        DatosProducto.next();
                                        System.out.println("Nombre: ");
                                        System.out.println(DatosProducto.getString(1));
                                        System.out.println("Cedula cliente: ");
                                        System.out.println(DatosProducto.getString(3));
                                } catch (Exception e){

                                }
                        break;

                        case 3: System.out.println("---ELIMINAR--- \n ID del producto a eliminar: ");
                                Scanner sc5 = new Scanner(System.in);
                                String id_a_eliminar = sc5.nextLine();
                                Producto producto3 = new Producto();
                                producto3.eliminar(id_a_eliminar, inn);
                        break;

                        case 4: System.out.println("---LISTA DE PRODUCTOS--- ");
                                Producto producto4 = new Producto();
                                ResultSet ConsultaProductos = producto4.listar(inn);
                                try {
                                    while (ConsultaProductos.next()) {
                                        System.out.println("Nombre: ");
                                        System.out.println(ConsultaProductos.getString(1));
                                        System.out.println("ID: ");
                                        System.out.println(ConsultaProductos.getString(2));
                                        System.out.println("Cedula cliente");
                                        System.out.println(ConsultaProductos.getString(3));
                                        System.out.println("\n");
                                     }
                                } catch (Exception e){

                                }
                        break;
                            
                        case 5: System.out.println("---RECARGAR SALDO---\n ID del producto a recargar: ");
                                Scanner sc6 = new Scanner(System.in);
                                String id_a_recargar = sc6.nextLine();
                                Producto producto5 = new Producto();
                                System.out.println("Monto a recargar: ");
                                Scanner sc7 = new Scanner(System.in);
                                int monto = Integer.parseInt(sc7.nextLine());
                                producto5.sumarSaldo(id_a_recargar, monto, inn);
                                System.out.println("Recarga exitosa. \n");
                        break;
                }
            break;
                
            case 3: msj = "---GESTION AFILIACIONES--- \n Acciones: \n 1) Registrar \n " ; 
                    msj = msj + "2) Buscar \n 3) Eliminar \n 4) Listar  \n 5) Desafiliar \n Opcion nro: " ; 
                    
                    System.out.println(msj);
                    in = new Scanner(System.in);
                    opcion = in.nextInt();
                    
                    switch(opcion){
                    case 1: System.out.println("---AFILIAR--- \n ID del producto: ");
                            Scanner sc1 = new Scanner(System.in);
                            String id_producto = sc1.nextLine();

                            System.out.println("ID del plan: ");
                            Scanner sc2 = new Scanner(System.in);
                            String id_plan = sc2.nextLine();

                            DateFormat dateFormat1;
                            dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                            Date date1 = new Date();
                            String fecha_afiliacion = dateFormat1.format(date1).toString();
                                    
                            DateFormat dateFormat2 = new SimpleDateFormat("dd");
                            Date date2 = new Date();
                            String dia_cobro;
                            dia_cobro = dateFormat2.format(date2).toString();
                            
                            boolean vigente_afilia = true;

                            Afilia afiliacion = new Afilia(id_producto, id_plan, fecha_afiliacion, dia_cobro, vigente_afilia);
                            afiliacion.registrar(inn);
                    break;

                    case 2: System.out.println("---BUSCAR AFILIACIONES--- \n ID del producto: ");
                            Scanner sc3 = new Scanner(System.in);
                            String id_a_buscar = sc3.nextLine();
                            Afilia afiliacion2 = new Afilia();
                            ResultSet DatosProducto = afiliacion2.buscar(id_a_buscar, null, inn);
                            try {
                                    while (DatosProducto.next()) {
                                    System.out.println("\n ID del plan: ");
                                    System.out.println(DatosProducto.getString(2));
                                    System.out.println("Fecha Afiliacion: ");
                                    System.out.println(DatosProducto.getString(3));
                                    System.out.println("Dia de cobro: ");
                                    System.out.println(DatosProducto.getString(4));
                                    System.out.println("Vigencia");
                                    System.out.println(DatosProducto.getString(5));
                                    }
                                    
                            } catch (Exception e){

                            }
                    break;

                    case 3: System.out.println("---ELIMINAR--- \n ID del producto: ");
                            Scanner sc4 = new Scanner(System.in);
                            String id_a_eliminar = sc4.nextLine();
                            System.out.println("ID del plan: ");
                            Scanner sc5 = new Scanner(System.in);
                            String id_plan2 = sc5.nextLine();
                            System.out.println("Fecha de afiliacion: ");
                            Scanner sc6 = new Scanner(System.in);
                            String fecha_afiliacion2 = sc6.nextLine();
                            Afilia afiliacion3 = new Afilia();
                            afiliacion3.eliminar(id_a_eliminar, id_plan2, fecha_afiliacion2, inn); 
                    break;

                    case 4: System.out.println("---LISTA DE AFILIACIONES--- ");
                            Afilia afiliacion4 = new Afilia();
                            ResultSet ConsultaAfiliaciones = afiliacion4.listar(inn);
                            try {
                                while (ConsultaAfiliaciones.next()) {
                                    System.out.println("ID del producto: ");
                                    System.out.println(ConsultaAfiliaciones.getString(1));
                                    System.out.println("ID del plan: ");
                                    System.out.println(ConsultaAfiliaciones.getString(2));
                                    System.out.println("Fecha afiliacion: ");
                                    System.out.println(ConsultaAfiliaciones.getString(3));
                                    System.out.println("Dia de cobro ");
                                    System.out.println(ConsultaAfiliaciones.getString(4));
                                    System.out.println("Vigencia");
                                    System.out.println(ConsultaAfiliaciones.getString(5));
                                    System.out.println("\n");
                                 }
                            } catch (Exception e){

                            }
                    break;
                        
                    case 5: System.out.println("---DESAFILIAR--- \n ID del producto: ");
                            Scanner sc7 = new Scanner(System.in);
                            String id_producto1 = sc7.nextLine();
                            Afilia afiliacion5 = new Afilia();
                            afiliacion5.suspender(id_producto1, inn);
                    }
                    
            break;
            
            case 4: msj = "---GESTION DE SERVICIOS Y PAQUETES ADICIONALES--- \n Acciones: \n "
                    + "1) Agregar Servicio \n 2) Agregar Paquete \n Opcion nro:" ; 
                                        
                    System.out.println(msj);
                    in = new Scanner(System.in);
                    opcion = in.nextInt();
                    
                    switch(opcion){ 
                        case 1: System.out.println("ID del producto: ");
                                Scanner scan = new Scanner(System.in);
                                String producto0 = scan.nextLine();

                                System.out.println("ID del servicio: ");
                                Scanner scan0 = new Scanner(System.in);
                                String servicio = scan0.nextLine();
                        break;
                        
                        case 2: System.out.println("ID del producto: ");
                                Scanner scan1 = new Scanner(System.in);
                                String producto1 = scan1.nextLine();

                                System.out.println("ID del paquete: ");
                                Scanner scan2 = new Scanner(System.in);
                                String paquete = scan2.nextLine();
                        break;
                
            break;
                
            case 5: msj = "---GESTION CONSUMOS--- \n Acciones: \n 1) Registrar \n " ; 
                    msj = msj + "2) Buscar \n 3) Consultar \n Opcion nro: " ; 
                    
                    System.out.println(msj);
                    in = new Scanner(System.in);
                    opcion = in.nextInt();
                
                    switch(opcion){ 
                        case 1: System.out.println("---REGISTRAR--- \n ID del producto: ");
                                Scanner sc1 = new Scanner(System.in);
                                String producto = sc1.nextLine();

                                System.out.println("ID del servicio: ");
                                Scanner sc2 = new Scanner(System.in);
                                String servicio = sc2.nextLine();

                                System.out.println("Fecha: ");
                                Scanner sc3 = new Scanner(System.in);
                                String fecha = sc3.nextLine();

                                System.out.println("Cantidad: ");
                                Scanner sc4 = new Scanner(System.in);
                                String cantidad = sc4.nextLine();
                                
                                System.out.println("Cantidad total: ");
                                Scanner sc5 = new Scanner(System.in);
                                String cantidad_total = sc5.nextLine();

                                Consumo consumo = new Consumo(producto, servicio, fecha, cantidad, cantidad_total);
                                boolean registrado = consumo.registrar(inn);
                                if (registrado) {
                                    System.out.println("Registro exitoso. \n");
                                } else {
                                    System.out.println("No dispone de saldo positivo,"
                                            + " su plan fue suspendido. \n");
                                }
                                 
                        break;

                        case 2: System.out.println("---BUSCAR CONSUMOS DE UN PRODUCTO--- \n ID del producto: ");
                                Scanner sc6 = new Scanner(System.in);
                                String id_producto = sc6.nextLine();
                                Consumo consumo2 = new Consumo();
                                ResultSet Datos = consumo2.buscarTodos(id_producto, inn);
                                try {
                                        while(Datos.next()) {
                                        System.out.println("\n");
                                        System.out.println("Servicio: ");
                                        System.out.println(Datos.getString(2));
                                        System.out.println("Fecha: ");
                                        System.out.println(Datos.getString(3));
                                        System.out.println("Cantidad: ");
                                        System.out.println(Datos.getString(4));
                                        System.out.println("Cantidad total: ");
                                        System.out.println(Datos.getString(5));
                                        }
                                } catch (Exception e){

                                }                            
                        break;

                        case 3: System.out.println("---LISTA DE CONSUMOS--- ");
                                Consumo consumo1 = new Consumo();
                                ResultSet ListaConsumos = consumo1.consultar(inn);
                                try {
                                        while (ListaConsumos.next()) {
                                        System.out.println("ID del producto: ");
                                        System.out.println(ListaConsumos.getString(1));
                                        System.out.println("ID del servicio: ");
                                        System.out.println(ListaConsumos.getString(2));
                                        System.out.println("Fecha: ");
                                        System.out.println(ListaConsumos.getString(3));
                                        System.out.println("Cantidad: ");
                                        System.out.println(ListaConsumos.getString(4));
                                        System.out.println("Cantidad total: ");
                                        System.out.println(ListaConsumos.getString(5));
                                        System.out.println("\n");
                                        }
                                } catch (Exception e){

                                }         
                    break;    
                }
            break;
                
            case 6: msj = "---GESTION FACTURAS--- \n Acciones: \n 1) Emitir \n " ; 
                    msj = msj + "2) Consultar facturas de un producto \n Opcion nro: " ; 
                    
                    System.out.println(msj);
                    in = new Scanner(System.in);
                    opcion = in.nextInt();
                    
                    switch(opcion){ 
                        case 1: System.out.println("---EMITIR--- \n ID del producto: ");
                                Scanner sc1 = new Scanner(System.in);
                                String id_producto = sc1.nextLine(); 

                                System.out.println("Nro de Tarjeta: ");
                                Scanner sc2 = new Scanner(System.in);
                                String tarjeta = sc2.nextLine();

                                System.out.println("Observaciones: ");
                                Scanner sc3 = new Scanner(System.in);
                                String observaciones = sc3.nextLine();
                                
                                Producto producto1 = new Producto();
                                producto1.id_cliente = id_producto;
                                String tipo_plan = producto1.obtenerTipoPlan(id_producto, inn);
                                
                                if (tipo_plan.equals("postpago")) {
                                    FacturacionPostpago tipo_facturacion = new FacturacionPostpago();
                                    FachadaFactura FF = new FachadaFactura(id_producto, tarjeta, observaciones, tipo_facturacion);
                                    Factura Fact = FF.emitir(inn);
                                } else {
                                    FacturacionPrepago tipo_facturacion = new FacturacionPrepago();
                                    FachadaFactura FF = new FachadaFactura(id_producto, tarjeta, observaciones, tipo_facturacion);
                                    Factura Fact = FF.emitir(inn);
                                };

                        break;
                        
                        case 2: System.out.println("---BUSCAR FACTURAS DE UN PRODUCTO--- \n ID del producto: ");
                                Scanner sc4 = new Scanner(System.in);
                                String id_producto1 = sc4.nextLine(); 
                                Factura Fact2 = new Factura();
                                ResultSet Facturas = Fact2.buscar(id_producto1, inn);
                                try {
                                        while(Facturas.next()) {
                                        System.out.println("\n ---FACTURA---");
                                        System.out.println("Nro: ");
                                        System.out.println(Facturas.getString(1));
                                        System.out.println("Monto: ");
                                        System.out.println(Facturas.getString(4));
                                        System.out.println("Pagada: ");
                                        System.out.println(Facturas.getString(5));
                                        System.out.println("Nro Tarjeta: ");
                                        System.out.println(Facturas.getString(6));
                                        System.out.println("Observacion: ");
                                        System.out.println(Facturas.getString(7));
                                        }
                                } catch (Exception e){

                                }
                        break;
                    }
                    break;
        };

    }
}

