package gestionxml2021;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;

public class InsertarXML {

    public static void main(String[] args) {
        try {

            //Se carga el Driver
            Class driverClass = Class.forName("org.exist.xmldb.DatabaseImpl");
            Database db = (Database) driverClass.newInstance();
            DatabaseManager.registerDatabase(db);

            //Se obtiene la colección con la que se va a trabajar
            String URL = "xmldb:exist://localhost:8899/exist/xmlrpc/db";
            String USER = "admin";
            String PASSWORD = "";
            Collection coleccion = DatabaseManager.getCollection(URL, USER, PASSWORD);

            //Datos que se insertarán
            String codEmpleado = "1";
            String nombre = "Nicolas";
            String apellidos = "Esteban Borquez";
            String puesto = "Desarrollador";
            String salario = "1350";

            if (coleccion != null) {
                //Se crea el servicio XPathQueryService que realizará la tarea
                XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

                //Sentencia XQUERY con el nuevo nodo
                String sentencia
                        = "update insert "
                        + "<empleado>"
                        + "<cod_empleado>" + codEmpleado + "</cod_empleado>"
                        + "<nombre>" + nombre + "</nombre>"
                        + "<apellidos>" + apellidos + "</apellidos>"
                        + "<puesto>" + puesto + "</puesto>"
                        + "<salario>" + salario + "</salario>"
                        + "</empleado> into /Empleados";

                //Se lanza la sentencia
                servicio.query(sentencia);
                System.out.println("Se ha insertado: \n" + sentencia);
                coleccion.close();

            } else {
                System.out.println("No se encuentra la colección.");
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CrearColeccion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(InsertarXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(InsertarXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLDBException ex) {
            Logger.getLogger(InsertarXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
