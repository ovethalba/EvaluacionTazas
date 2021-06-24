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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Oveth
 */
public class Egresos extends javax.swing.JInternalFrame {

    private String url = "tazas.db";
    private Connection connect;

    public Egresos() {
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

        //Consulta para la información de la tabla 
        try {
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

            //Recuperacion de la informacion de la consulta
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
        jTableEgresos.setModel(modelo);
        modelo.setDataVector(Datos, columnNames);

        //cierre de conexion a base de datos
        close();
    }

    private void RellenarCOmboBox() {
        //Creacion de ComoboBox Calidad e ingreso de su informacion
        TabCalidad TablaCalidad = new TabCalidad();

        //Metodo Leer recupera toda la informacion de la Tabla
        ArrayList<Calidad> ArrayCalidad = TablaCalidad.Leer_Calidad();
        String[] DatosCalidad = new String[ArrayCalidad.size()];
        for (int i = 0; i < DatosCalidad.length; i++) {
            DatosCalidad[i] = ArrayCalidad.get(i).getCalidad();
        }
        jComboBoxCalidad.setModel(new DefaultComboBoxModel<>(DatosCalidad));

        //ComoboBox Color
        TabColor TablaColor = new TabColor();
        ArrayList<Color> ArrayColor = TablaColor.Leer_Color();
        String[] DatosColor = new String[ArrayColor.size()];
        for (int i = 0; i < DatosColor.length; i++) {
            DatosColor[i] = ArrayColor.get(i).getColor();
        }
        jComboBoxColor.setModel(new DefaultComboBoxModel<>(DatosColor));

        //ComoboBox Dimensiones
        Integer LongitudMaxima = 10;
        Integer LongitudMinima = 4;

        String[] DatosDimensiones = new String[LongitudMaxima - LongitudMinima];
        for (int i = LongitudMinima; i < LongitudMaxima; i++) {
            DatosDimensiones[i - LongitudMinima] = Integer.toString(i);
        }
        jComboBoxAncho.setModel(new DefaultComboBoxModel<>(DatosDimensiones));
        jComboBoxAlto.setModel(new DefaultComboBoxModel<>(DatosDimensiones));
        jComboBoxLargo.setModel(new DefaultComboBoxModel<>(DatosDimensiones));

        //ComoboBox Calidad
        TabModelo TablaModelo = new TabModelo();
        ArrayList<Modelo> ArrayModelo = TablaModelo.Leer_Modelo();
        String[] DatosModelo = new String[ArrayModelo.size()];
        for (int i = 0; i < DatosModelo.length; i++) {
            DatosModelo[i] = ArrayModelo.get(i).getModelo();
        }
        jComboBoxModelo.setModel(new DefaultComboBoxModel<>(DatosModelo));

        //ComoboBox Calidad
        TabMaterial TablaMaterial = new TabMaterial();
        ArrayList<Material> ArrayMaterial = TablaMaterial.Leer_Material();
        String[] DatosMaterial = new String[ArrayMaterial.size()];
        for (int i = 0; i < DatosMaterial.length; i++) {
            DatosMaterial[i] = ArrayMaterial.get(i).getMaterial();
        }
        jComboBoxMaterial.setModel(new DefaultComboBoxModel<>(DatosMaterial));

        //ComoboBox Calidad
        TabCapacidad TablaCapacidad = new TabCapacidad();
        ArrayList<Capacidad> ArrayCapacidad = TablaCapacidad.Leer_Capacidad();
        String[] DatosCapacidad = new String[ArrayCapacidad.size()];
        for (int i = 0; i < DatosCapacidad.length; i++) {
            DatosCapacidad[i] = ArrayCapacidad.get(i).getCapacidad().toString();
        }
        jComboBoxCapacidad.setModel(new DefaultComboBoxModel<>(DatosCapacidad));
    }

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
            Logger.getLogger(Egresos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBoxPerdido = new javax.swing.JComboBox<>();
        jPanelEgresos = new javax.swing.JPanel();
        jComboBoxLargo = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxMaterial = new javax.swing.JComboBox<>();
        jComboBoxAlto = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxAncho = new javax.swing.JComboBox<>();
        jComboBoxModelo = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        BtnEgresar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxCapacidad = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldCantidad = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEgresos = new javax.swing.JTable();
        jComboBoxCalidad = new javax.swing.JComboBox<>();
        jComboBoxColor = new javax.swing.JComboBox<>();

        jComboBoxPerdido.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setClosable(true);
        setTitle("Egresos");

        jComboBoxLargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("Largo");

        jLabel2.setText("Color");

        jComboBoxMaterial.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxAlto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setText("Material");

        jComboBoxAncho.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxModelo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Ancho");

        jLabel8.setText("Modelo");

        jLabel4.setText("Dimensiones");

        BtnEgresar.setText("Egresar");
        BtnEgresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEgresarActionPerformed(evt);
            }
        });

        jLabel5.setText("Alto");

        jComboBoxCapacidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setText("Cantidad");

        jLabel9.setText("Capacidad");

        jLabel1.setText("Calidad");

        jTableEgresos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableEgresos);

        jComboBoxCalidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxColor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanelEgresosLayout = new javax.swing.GroupLayout(jPanelEgresos);
        jPanelEgresos.setLayout(jPanelEgresosLayout);
        jPanelEgresosLayout.setHorizontalGroup(
            jPanelEgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEgresosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jComboBoxCalidad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBoxColor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelEgresosLayout.createSequentialGroup()
                        .addGroup(jPanelEgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jComboBoxModelo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxMaterial, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanelEgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextFieldCantidad, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BtnEgresar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(jPanelEgresosLayout.createSequentialGroup()
                        .addGroup(jPanelEgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxAlto, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGroup(jPanelEgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelEgresosLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel3)
                                .addGap(33, 33, 33)
                                .addComponent(jLabel6))
                            .addGroup(jPanelEgresosLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jComboBoxAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBoxLargo, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 803, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelEgresosLayout.setVerticalGroup(
            jPanelEgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEgresosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanelEgresosLayout.createSequentialGroup()
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
                        .addGroup(jPanelEgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3))
                        .addGap(16, 16, 16)
                        .addGroup(jPanelEgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxAlto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxLargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxAncho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addGroup(jPanelEgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(24, 24, 24)
                        .addGroup(jPanelEgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelEgresosLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBoxMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelEgresosLayout.createSequentialGroup()
                                .addComponent(jTextFieldCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(BtnEgresar)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelEgresos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelEgresos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnEgresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEgresarActionPerformed
        //Abrir conexion a base de datos
        connect();

        //Informacion de los campos de la cabezara de la tabla
        String Color = jComboBoxColor.getSelectedItem().toString();
        String Calidad = jComboBoxCalidad.getSelectedItem().toString();
        String Capacidad = jComboBoxCapacidad.getSelectedItem().toString();
        String Modelo = jComboBoxModelo.getSelectedItem().toString();
        String Material = jComboBoxMaterial.getSelectedItem().toString();
        String Ancho = jComboBoxAncho.getSelectedItem().toString();
        String Alto = jComboBoxAlto.getSelectedItem().toString();
        String Largo = jComboBoxLargo.getSelectedItem().toString();

        //Consulta para obtener la cantidad que hay en cierto producto en el almacen
        String Query = "select Cantidad "
                + "from Almacen "
                + "INNER JOIN Producto on Almacen.IDProducto = Producto.IDProducto "
                + "INNER JOIN Color on Producto.IDColor = Color.IDColor "
                + "INNER JOIN Calidad on Producto.IDCalidad = Calidad.IDCalidad "
                + "INNER JOIN Capacidad on Producto.IDCapacidad = Capacidad.IDCapacidad "
                + "INNER JOIN Modelo on Producto.IDModelo = Modelo.IDModelo "
                + "INNER JOIN Material on Producto.IDMaterial = Material.IDMaterial "
                + "INNER JOIN Dimensiones on Producto.IDDimensiones = Dimensiones.IDDimensiones "
                + "where Color.Color ='" + Color + "' AND Calidad.Calidad='" + Calidad + "' AND Capacidad.Capacidad='" + Capacidad + "'"
                + " AND Modelo.Modelo='" + Modelo + "' AND Material.Material='" + Material + "' AND Alto=" + Alto
                + " AND Ancho=" + Ancho + " AND Largo=" + Largo;

        ResultSet result = null;
        Integer CantidadOriginal = -1;
        try {
            PreparedStatement st = connect.prepareStatement(Query);
            result = st.executeQuery();
            while (result.next()) {
                CantidadOriginal = Integer.valueOf(result.getString("Cantidad"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Egresos.class.getName()).log(Level.SEVERE, null, ex);
        }

        //si la cantidad no es correcta se deplega mensague de error
        if (CantidadOriginal < 0 || CantidadOriginal < Integer.valueOf(this.jTextFieldCantidad.getText())) {
            JOptionPane.showMessageDialog(null, "Cantidad Erronea");
        } else {
            //Se recupera la cantidad a egresar y se hace la resta al inventario
            Integer Egreso = Integer.valueOf(this.jTextFieldCantidad.getText());
            Egreso = CantidadOriginal - Egreso;

            //Se elabora la consulta para actualizar la cantidad del producto a egresar
            String Consulta = "update Almacen"
                    + " set Cantidad = " + Egreso.toString()
                    + " where Almacen.IDProducto = (select Producto.IDProducto"
                    + " from Almacen"
                    + " INNER JOIN Producto on Almacen.IDProducto = Producto.IDProducto"
                    + " INNER JOIN Color on Producto.IDColor = Color.IDColor "
                    + " INNER JOIN Calidad on Producto.IDCalidad = Calidad.IDCalidad "
                    + " INNER JOIN Capacidad on Producto.IDCapacidad = Capacidad.IDCapacidad "
                    + " INNER JOIN Modelo on Producto.IDModelo = Modelo.IDModelo"
                    + " INNER JOIN Material on Producto.IDMaterial = Material.IDMaterial "
                    + " INNER JOIN Dimensiones on Producto.IDDimensiones = Dimensiones.IDDimensiones"
                    + " where Color.Color ='" + Color + "' AND Calidad.Calidad='" + Calidad + "' AND Capacidad.Capacidad='" + Capacidad + "'"
                    + " AND Modelo.Modelo='" + Modelo + "' AND Material.Material='" + Material + "' AND Alto=" + Alto
                    + " AND Ancho=" + Ancho + " AND Largo=" + Largo + ")";

            try {
                PreparedStatement st = connect.prepareStatement(Consulta);
                st = connect.prepareStatement(Consulta);
                st.execute();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
            if (Integer.valueOf(this.jTextFieldCantidad.getText()) > 10) {
                if (Calidad.equals("Alta")) {
                    // se desplega un mensaje de dialogo de aviso
                    Integer Regalo = (Integer.valueOf(this.jTextFieldCantidad.getText()) / 10) * 3;
                    JOptionPane.showMessageDialog(null, "Egrese: " + Regalo + " Tazas de Baja Calidad de Regalo");
                } else {
                    if (Calidad.equals("Baja")) {
                        Integer Regalo = (Integer.valueOf(this.jTextFieldCantidad.getText()) / 10) * 2;
                        JOptionPane.showMessageDialog(null,"Egrese: " + Regalo + " Tazas de Baja Calidad de Regalo");
                    }
                }
            }
        }
        //Se actualiza la tabla con los nuevos valores
        RellenarTabla();

        // se desplega un mensaje de dialogo de aviso
        JOptionPane.showMessageDialog(null, "Operacion terminada");
        close();
    }//GEN-LAST:event_BtnEgresarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnEgresar;
    private javax.swing.JComboBox<String> jComboBoxAlto;
    private javax.swing.JComboBox<String> jComboBoxAncho;
    private javax.swing.JComboBox<String> jComboBoxCalidad;
    private javax.swing.JComboBox<String> jComboBoxCapacidad;
    private javax.swing.JComboBox<String> jComboBoxColor;
    private javax.swing.JComboBox<String> jComboBoxLargo;
    private javax.swing.JComboBox<String> jComboBoxMaterial;
    private javax.swing.JComboBox<String> jComboBoxModelo;
    private javax.swing.JComboBox<String> jComboBoxPerdido;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanelEgresos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableEgresos;
    private javax.swing.JTextField jTextFieldCantidad;
    // End of variables declaration//GEN-END:variables
}
