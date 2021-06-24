package Tablas;

import Datos.Calidad;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TabCalidad {

    //Ruta y conexion a la base de datos
    private String url = "tazas.db";
    private Connection connect;

    //Metodo para Leer la tabla correspondiente
    public ArrayList<Calidad> Leer_Calidad() {
        connect();
        ArrayList<Calidad> respuesta = new ArrayList<>();

        ResultSet result = null;
        try {
            PreparedStatement st = connect.prepareStatement("select * from Calidad");
            result = st.executeQuery();
            while (result.next()) {

                Datos.Calidad calidad = new Calidad(result.getString("Calidad"));
                respuesta.add(calidad);
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
            Logger.getLogger(TabCalidad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
