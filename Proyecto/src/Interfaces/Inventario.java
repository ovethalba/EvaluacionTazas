package Interfaces;

import Datos.Calidad;
import Datos.Capacidad;
import Datos.Color;
import Datos.Material;
import Datos.Modelo;
import Tablas.TabCalidad;
import Tablas.TabCapacidad;
import Tablas.TabColor;
import Tablas.TabMaterial;
import Tablas.TabModelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Oveth
 */
public class Inventario extends javax.swing.JInternalFrame {

    private String url = "tazas.db";
    private Connection connect;

    public Inventario() {
        initComponents();

        //Inicializar JInternalFrame con los comboBox y la tabla
        RellenarCOmboBox();
        RellenarTabla();
    }

    private void RellenarTabla() {
        //Abrir conexion a base de datos
        connect();

        //Informacion de los campos de la cabezara de la tabla
        Integer NoColumnas = 10;
        String[] columnNames = new String[NoColumnas];
        columnNames[0] = "ID Producto";
        columnNames[1] = "Cantidad";
        columnNames[2] = "Color";
        columnNames[3] = "Calidad";
        columnNames[4] = "Capacidad";
        columnNames[5] = "Modelo";
        columnNames[6] = "Material";
        columnNames[7] = "Alto";
        columnNames[8] = "Ancho";
        columnNames[9] = "Largo";

        //Rellenar la tabla con lo ingresado en la base de datos 
        LinkedList<String[]> Filas = new LinkedList<>();
        ResultSet result = null;
        try {
            //Consulta para la información de la tabla 
            PreparedStatement st = connect.prepareStatement("select Producto.IDProducto as \"ID Producto\", "
                    + "Almacen.Cantidad, Color.Color, Calidad.Calidad, Capacidad.Capacidad, Modelo.Modelo, "
                    + "Material.Material, tamaño.Ancho, tamaño.Alto, tamaño.Largo from Almacen "
                    + "INNER JOIN Producto on Almacen.IDProducto = Producto.IDProducto "
                    + "INNER JOIN Color on Producto.IDColor = Color.IDColor "
                    + "INNER JOIN Calidad on Producto.IDCalidad = Calidad.IDCalidad "
                    + "INNER JOIN Capacidad on Producto.IDCapacidad = Capacidad.IDCapacidad "
                    + "INNER JOIN Modelo on Producto.IDModelo = Modelo.IDModelo "
                    + "INNER JOIN Material on Producto.IDMaterial = Material.IDMaterial "
                    + "INNER JOIN Dimensiones as \"tamaño\" on Producto.IDDimensiones = tamaño.IDDimensiones");
            result = st.executeQuery();
            while (result.next()) {
                //Recuperacion de la informacion de la consulta
                String[] Fila = new String[NoColumnas];
                Fila[0] = result.getString("ID Producto");
                Fila[1] = result.getString("Cantidad");
                Fila[2] = result.getString("Color");
                Fila[3] = result.getString("Calidad");
                Fila[4] = result.getString("Capacidad");
                Fila[5] = result.getString("Modelo");
                Fila[6] = result.getString("Material");
                Fila[7] = result.getString("Alto");
                Fila[8] = result.getString("Ancho");
                Fila[9] = result.getString("Largo");
                Filas.add(Fila);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        //Matriz con la información de cabezara y filas de la tabla
        Object Datos[][] = new Object[Filas.size()][NoColumnas];
        for (int i = 0; i < Filas.size(); i++) {
            Datos[i] = Filas.get(i);
        }

        //instancia de la tabla con su desplegue
        DefaultTableModel modelo = new DefaultTableModel();
        jTableAlmacen.setModel(modelo);
        modelo.setDataVector(Datos, columnNames);

        //cierre de conexion a base de datos
        close();
    }

    private void Buscar() {
        //Abrir conexion a base de datos
        connect();

        //Informacion de los campos de la cabezara de la tabla
        Integer NoColumnas = 10;
        String[] columnNames = new String[NoColumnas];
        columnNames[0] = "ID Producto";
        columnNames[1] = "Cantidad";
        columnNames[2] = "Color";
        columnNames[3] = "Calidad";
        columnNames[4] = "Capacidad";
        columnNames[5] = "Modelo";
        columnNames[6] = "Material";
        columnNames[7] = "Alto";
        columnNames[8] = "Ancho";
        columnNames[9] = "Largo";

        LinkedList<String[]> Filas = new LinkedList<>();
        ResultSet result = null;
        boolean condicionante = false;

        //Recuperación de elementos seleccionados en comboBox
        String Color = jComboBoxColor.getSelectedItem().toString();
        String Calidad = jComboBoxCalidad.getSelectedItem().toString();
        String Capacidad = jComboBoxCapacidad.getSelectedItem().toString();
        String Modelo = jComboBoxModelo.getSelectedItem().toString();
        String Material = jComboBoxMaterial.getSelectedItem().toString();
        String Ancho = jComboBoxAncho.getSelectedItem().toString();
        String Alto = jComboBoxAlto.getSelectedItem().toString();
        String Largo = jComboBoxLargo.getSelectedItem().toString();

        //Consulta para recuperar informacion con condiciones de comboBox
        String Script = "select Producto.IDProducto as \"ID Producto\", "
                + "Almacen.Cantidad, Color.Color, Calidad.Calidad, Capacidad.Capacidad, Modelo.Modelo, "
                + "Material.Material, tamaño.Ancho, tamaño.Alto, tamaño.Largo from Almacen "
                + "INNER JOIN Producto on Almacen.IDProducto = Producto.IDProducto "
                + "INNER JOIN Color on Producto.IDColor = Color.IDColor "
                + "INNER JOIN Calidad on Producto.IDCalidad = Calidad.IDCalidad "
                + "INNER JOIN Capacidad on Producto.IDCapacidad = Capacidad.IDCapacidad "
                + "INNER JOIN Modelo on Producto.IDModelo = Modelo.IDModelo "
                + "INNER JOIN Material on Producto.IDMaterial = Material.IDMaterial "
                + "INNER JOIN Dimensiones as \"tamaño\" on Producto.IDDimensiones = tamaño.IDDimensiones";

        //Ingresar condiciones de acuerdo a cada comboBox
        if (!Color.equals("Cualquiera")) {
            Script += " Where Color.Color = \"" + Color + "\" ";
            condicionante = true;
        }

        if (!Calidad.equals("Cualquiera")) {
            if (condicionante) {
                Script += " AND Calidad.Calidad = \"" + Calidad + "\" ";
            } else {
                Script += " Where Calidad.Calidad = \"" + Calidad + "\" ";
            }
            condicionante = true;
        }

        if (!Capacidad.equals("Cualquiera")) {
            if (condicionante) {
                Script += " AND Capacidad.Capacidad = \"" + Capacidad + "\" ";
            } else {
                Script += " Where Capacidad.Capacidad = \"" + Capacidad + "\" ";
            }
            condicionante = true;
        }
        if (!Modelo.equals("Cualquiera")) {
            if (condicionante) {
                Script += " AND Modelo.Modelo = \"" + Modelo + "\" ";
            } else {
                Script += " Where Modelo.Modelo = \"" + Modelo + "\" ";
            }
            condicionante = true;
        }
        if (!Material.equals("Cualquiera")) {
            if (condicionante) {
                Script += " AND Material.Material = \"" + Material + "\" ";
            } else {
                Script += " Where Material.Material = \"" + Material + "\" ";
            }
            condicionante = true;
        }
        if (!Ancho.equals("Cualquiera")) {
            if (condicionante) {
                Script += " AND Ancho = \"" + Ancho + "\" ";
            } else {
                Script += " Where Ancho = \"" + Ancho + "\" ";
            }
            condicionante = true;
        }
        if (!Alto.equals("Cualquiera")) {
            if (condicionante) {
                Script += " AND Alto = \"" + Alto + "\" ";
            } else {
                Script += " Where Alto = \"" + Alto + "\" ";
            }
            condicionante = true;
        }
        if (!Largo.equals("Cualquiera")) {
            if (condicionante) {
                Script += " AND Largo = \"" + Largo + "\" ";
            } else {
                Script += " Where Largo = \"" + Largo + "\" ";
            }
            condicionante = true;
        }

        //Recuperacion de la informacion de la consulta
        try {
            PreparedStatement st = connect.prepareStatement(Script);
            result = st.executeQuery();
            while (result.next()) {
                String[] Fila = new String[NoColumnas];
                Fila[0] = result.getString("ID Producto");
                Fila[1] = result.getString("Cantidad");
                Fila[2] = result.getString("Color");
                Fila[3] = result.getString("Calidad");
                Fila[4] = result.getString("Capacidad");
                Fila[5] = result.getString("Modelo");
                Fila[6] = result.getString("Material");
                Fila[7] = result.getString("Alto");
                Fila[8] = result.getString("Ancho");
                Fila[9] = result.getString("Largo");
                Filas.add(Fila);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        //Matriz con la información de cabezara y filas de la tabla
        Object Datos[][] = new Object[Filas.size()][NoColumnas];
        for (int i = 0; i < Filas.size(); i++) {
            Datos[i] = Filas.get(i);
        }

        //instancia de la tabla con su desplegue
        DefaultTableModel modelo = new DefaultTableModel();
        jTableAlmacen.setModel(modelo);
        modelo.setDataVector(Datos, columnNames);

        //cierre de conexion a base de datos
        close();
    }

    private void RellenarCOmboBox() {
        //Campo extra para buscar cualquier coincidencia
        String CampoExtra = "Cualquiera";

        //Creacion de ComoboBox Calidad e ingreso de su informacion
        TabCalidad TablaCalidad = new TabCalidad();

        //Metodo Leer recupera toda la informacion de la Tabla
        ArrayList<Calidad> ArrayCalidad = TablaCalidad.Leer_Calidad();
        String[] DatosCalidad = new String[ArrayCalidad.size() + 1];
        for (int i = 0; i < DatosCalidad.length - 1; i++) {
            DatosCalidad[i] = ArrayCalidad.get(i).getCalidad();
        }

        //Se incluye la opcion al comboBox "Cualquiera"
        DatosCalidad[ArrayCalidad.size()] = CampoExtra;
        jComboBoxCalidad.setModel(new DefaultComboBoxModel<>(DatosCalidad));

        //Creacion de ComoboBox Calidad e ingreso de su informacion
        TabColor TablaColor = new TabColor();

        //Metodo Leer recupera toda la informacion de la Tabla
        ArrayList<Color> ArrayColor = TablaColor.Leer_Color();
        String[] DatosColor = new String[ArrayColor.size() + 1];
        for (int i = 0; i < DatosColor.length - 1; i++) {
            DatosColor[i] = ArrayColor.get(i).getColor();
        }

        //Se incluye la opcion al comboBox "Cualquiera"
        DatosColor[ArrayColor.size()] = CampoExtra;
        jComboBoxColor.setModel(new DefaultComboBoxModel<>(DatosColor));

        //Creacion de ComoboBox Dimensiones del rango 4 a 10
        Integer LongitudMaxima = 10;
        Integer LongitudMinima = 4;

        String[] DatosDimensiones = new String[LongitudMaxima - LongitudMinima + 1];
        for (int i = LongitudMinima; i < LongitudMaxima; i++) {
            DatosDimensiones[i - LongitudMinima] = Integer.toString(i);
        }

        //Se incluye la opcion a los comboBox "Cualquiera"
        DatosDimensiones[DatosDimensiones.length - 1] = CampoExtra;
        jComboBoxAncho.setModel(new DefaultComboBoxModel<>(DatosDimensiones));
        jComboBoxAlto.setModel(new DefaultComboBoxModel<>(DatosDimensiones));
        jComboBoxLargo.setModel(new DefaultComboBoxModel<>(DatosDimensiones));

        //Creacion de ComoboBox Calidad e ingreso de su informacion
        TabModelo TablaModelo = new TabModelo();

        //Metodo Leer recupera toda la informacion de la Tabla
        ArrayList<Modelo> ArrayModelo = TablaModelo.Leer_Modelo();
        String[] DatosModelo = new String[ArrayModelo.size() + 1];
        for (int i = 0; i < DatosModelo.length - 1; i++) {
            DatosModelo[i] = ArrayModelo.get(i).getModelo();
        }

        //Se incluye la opcion al comboBox "Cualquiera"
        DatosModelo[ArrayModelo.size()] = CampoExtra;
        jComboBoxModelo.setModel(new DefaultComboBoxModel<>(DatosModelo));

        //Creacion de ComoboBox Calidad e ingreso de su informacion
        TabMaterial TablaMaterial = new TabMaterial();

        //Metodo Leer recupera toda la informacion de la Tabla
        ArrayList<Material> ArrayMaterial = TablaMaterial.Leer_Material();
        String[] DatosMaterial = new String[ArrayMaterial.size() + 1];
        for (int i = 0; i < DatosMaterial.length - 1; i++) {
            DatosMaterial[i] = ArrayMaterial.get(i).getMaterial();
        }

        //Se incluye la opcion al comboBox "Cualquiera"
        DatosMaterial[ArrayMaterial.size()] = CampoExtra;
        jComboBoxMaterial.setModel(new DefaultComboBoxModel<>(DatosMaterial));

        //Creacion de ComoboBox Calidad e ingreso de su informacion
        TabCapacidad TablaCapacidad = new TabCapacidad();

        //Metodo Leer recupera toda la informacion de la Tabla
        ArrayList<Capacidad> ArrayCapacidad = TablaCapacidad.Leer_Capacidad();
        String[] DatosCapacidad = new String[ArrayCapacidad.size() + 1];
        for (int i = 0; i < DatosCapacidad.length - 1; i++) {
            DatosCapacidad[i] = ArrayCapacidad.get(i).getCapacidad().toString();
        }

        //Se incluye la opcion al comboBox "Cualquiera"
        DatosCapacidad[ArrayCapacidad.size()] = CampoExtra;
        jComboBoxCapacidad.setModel(new DefaultComboBoxModel<>(DatosCapacidad));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jComboBoxCalidad = new javax.swing.JComboBox<>();
        jComboBoxColor = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxAlto = new javax.swing.JComboBox<>();
        jComboBoxAncho = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxLargo = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jComboBoxMaterial = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxModelo = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        BtnBuscar = new javax.swing.JButton();
        jComboBoxCapacidad = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableAlmacen = new javax.swing.JTable();

        setClosable(true);
        setTitle("Almacen");

        jLabel1.setText("Calidad");

        jComboBoxCalidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxColor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Color");

        jComboBoxAlto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxAncho.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Ancho");

        jLabel4.setText("Dimensiones");

        jLabel5.setText("Alto");

        jComboBoxLargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("Largo");

        jComboBoxMaterial.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setText("Material");

        jComboBoxModelo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setText("Modelo");

        BtnBuscar.setText("Buscar");
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });

        jComboBoxCapacidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("Capacidad");

        jTableAlmacen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTableAlmacen);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(jLabel4)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(48, 48, 48)
                            .addComponent(jLabel3)
                            .addGap(31, 31, 31)
                            .addComponent(jLabel6))
                        .addComponent(jComboBoxCalidad, 0, 194, Short.MAX_VALUE)
                        .addComponent(jComboBoxColor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jComboBoxModelo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxMaterial, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxCapacidad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(47, 47, 47)
                        .addComponent(BtnBuscar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBoxAlto, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jComboBoxAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxLargo, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 816, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxCalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3))
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxAlto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxLargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxAncho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(BtnBuscar))
                        .addGap(24, 24, 24)
                        .addComponent(jComboBoxMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed
        // TODO add your handling code here:
        Buscar();
    }//GEN-LAST:event_BtnBuscarActionPerformed

    //Funcion para conectara la Base de Datos
    public void connect() {
        try {
            connect = DriverManager.getConnection("jdbc:sqlite:" + url);
            if (connect != null) {
            }
        } catch (SQLException ex) {
            System.err.println("No se ha podido conectar a la base de datos\n" + ex.getMessage());
        }
    }

    //Funcion para cerrar la conexion a la BAse de Datos
    public void close() {
        try {
            connect.close();
        } catch (SQLException ex) {
            Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JComboBox<String> jComboBoxAlto;
    private javax.swing.JComboBox<String> jComboBoxAncho;
    private javax.swing.JComboBox<String> jComboBoxCalidad;
    private javax.swing.JComboBox<String> jComboBoxCapacidad;
    private javax.swing.JComboBox<String> jComboBoxColor;
    private javax.swing.JComboBox<String> jComboBoxLargo;
    private javax.swing.JComboBox<String> jComboBoxMaterial;
    private javax.swing.JComboBox<String> jComboBoxModelo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableAlmacen;
    // End of variables declaration//GEN-END:variables
}
