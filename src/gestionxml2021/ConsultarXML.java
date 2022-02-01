package gestionxml2021;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;

public class ConsultarXML {

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

            if (coleccion != null) {
                //Se crea el servicio XPathQueryService que realizará la tarea
                XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

                //Lanzamos la consulta XPATH que devuelve una coleccion ResourceSet
                ResourceSet resultado = servicio.query("for $i in /Empleados/empleado[salario>1300]\n"
                        + "let $nombre:=$i/nombre, $apellidos:=$i/apellidos, $puesto:=$i/puesto, $salario:=$i/salario\n"
                        + "return  <empleado> "
                        + "{$nombre| $apellidos | $puesto | $salario}"
                        + "</empleado>");
                ResourceIterator it = resultado.getIterator();
                while (it.hasMoreResources()) {
                    Resource r = it.nextResource();
                    System.out.println(r.getContent());
                }
                coleccion.close();

            } else {
                System.out.println("La coleccion no existe.");
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
