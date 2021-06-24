package Tablas;

import Datos.Modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TabModelo {

    //Ruta y conexion a la base de datos
    private String url = "tazas.db";
    private Connection connect;

    //Metodo para Leer la tabla correspondiente
    public ArrayList<Modelo> Leer_Modelo() {
        connect();
        ArrayList<Modelo> respuesta = new ArrayList<>();

        ResultSet result = null;
        try {
            PreparedStatement st = connect.prepareStatement("select * from Modelo");
            result = st.executeQuery();
            while (result.next()) {

                Modelo modelo = new Modelo(result.getString("Modelo"));
                respuesta.add(modelo);
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
            Logger.getLogger(TabModelo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
