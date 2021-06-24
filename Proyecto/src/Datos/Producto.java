package Datos;

public class Producto {

    //Atributos
    private Integer IDProducto;
    private Integer IDCalidad;
    private Integer IDCapacidad;
    private Integer IDColor;
    private Integer IDDimensiones;
    private Integer IDMaterial;
    private Integer IDModelo;

    //Metodos
    public Producto(Integer IDCalidad, Integer IDCapacidad, Integer IDColor, Integer IDDimensiones, Integer IDMaterial, Integer IDModelo) {
        this.IDCalidad = IDCalidad;
        this.IDCapacidad = IDCapacidad;
        this.IDColor = IDColor;
        this.IDDimensiones = IDDimensiones;
        this.IDMaterial = IDMaterial;
        this.IDModelo = IDModelo;
    }

    public Integer getIDProducto() {
        return IDProducto;
    }

    public void setIDProducto(Integer IDProducto) {
        this.IDProducto = IDProducto;
    }

    public Integer getIDCalidad() {
        return IDCalidad;
    }

    public void setIDCalidad(Integer IDCalidad) {
        this.IDCalidad = IDCalidad;
    }

    public Integer getIDCapacidad() {
        return IDCapacidad;
    }

    public void setIDCapacidad(Integer IDCapacidad) {
        this.IDCapacidad = IDCapacidad;
    }

    public Integer getIDColor() {
        return IDColor;
    }

    public void setIDColor(Integer IDColor) {
        this.IDColor = IDColor;
    }

    public Integer getIDDimensiones() {
        return IDDimensiones;
    }

    public void setIDDimensiones(Integer IDDimensiones) {
        this.IDDimensiones = IDDimensiones;
    }

    public Integer getIDMaterial() {
        return IDMaterial;
    }

    public void setIDMaterial(Integer IDMaterial) {
        this.IDMaterial = IDMaterial;
    }

    public Integer getIDModelo() {
        return IDModelo;
    }

    public void setIDModelo(Integer IDModelo) {
        this.IDModelo = IDModelo;
    }

}
