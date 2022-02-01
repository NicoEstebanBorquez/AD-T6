package gestionxml2021;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;

public class CrearColeccion {

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

            //Se crea el servicio de colecciones
            if (coleccion != null) {
                CollectionManagementService cms
                        = (CollectionManagementService) coleccion.getService("CollectionManagementService", "1.0");
                cms.createCollection("empleados");
                coleccion.close();
                System.out.println("La colección se ha creado correctamente.");
            } else {
                System.out.println("Error al crear la colección");
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CrearColeccion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(CrearColeccion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CrearColeccion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLDBException ex) {
            Logger.getLogger(CrearColeccion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
