package Tablas;

import Datos.Producto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TabProducto {

    //Ruta y conexion a la base de datos
    private String url = "tazas.db";
    private Connection connect;

    //Metodo para Leer la tabla correspondiente
    public ArrayList<Producto> Leer_Producto() {
        connect();
        ArrayList<Producto> respuesta = new ArrayList<>();

        ResultSet result = null;
        try {
            PreparedStatement st = connect.prepareStatement("select * from Producto");
            result = st.executeQuery();
            while (result.next()) {

                Producto producto = new Producto(result.getInt("IDCalidad"),
                        result.getInt("IDColor"), result.getInt("IDDimensiones"), result.getInt("IDMaterial"),
                        result.getInt("IDModelo"), result.getInt("IDCapacidad"));
                respuesta.add(producto);
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
            Logger.getLogger(TabProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
