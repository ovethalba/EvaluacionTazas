package Tablas;

import Datos.Almacen;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TabAlmacen {

    //Ruta y conexion a la base de datos
    private String url = "tazas.db";
    private Connection connect;

    //Metodo para Leer la tabla correspondiente
    public ArrayList<Almacen> Leer_Almacen() {
        connect();
        ArrayList<Almacen> respuesta = new ArrayList<>();

        ResultSet result = null;
        try {
            PreparedStatement st = connect.prepareStatement("select * from Producto");
            result = st.executeQuery();
            while (result.next()) {
                Almacen almacen = new Almacen(result.getInt("IDProducto"), result.getInt("Cantidad"));
                respuesta.add(almacen);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return respuesta;
    }

    //Metodo para abrir conexiona la base de datos
    public void connect() {
        try {
            connect = DriverManager.getConnection("jdbc:sqlite:" + url);
        } catch (SQLException ex) {
            System.err.println("No se ha podido conectar a la base de datos\n" + ex.getMessage());
        }
    }

    //Metodo para cerrar la conexion a la base de datos
    public void close() {
        try {
            connect.close();
        } catch (SQLException ex) {
            Logger.getLogger(TabAlmacen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
