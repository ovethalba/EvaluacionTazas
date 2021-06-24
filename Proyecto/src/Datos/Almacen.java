package Datos;

public class Almacen {
    
    //Atributos
    private Integer IDAlmacen;
    private Integer IDProducto;
    private Integer Cantidad;

    //Metodos
    public Almacen(Integer IDProducto, Integer Cantidad) {
        this.IDProducto = IDProducto;
        this.Cantidad = Cantidad;
    }

    public Integer getIDAlmacen() {
        return IDAlmacen;
    }

    public void setIDAlmacen(Integer IDAlmacen) {
        this.IDAlmacen = IDAlmacen;
    }

    public Integer getIDProducto() {
        return IDProducto;
    }

    public void setIDProducto(Integer IDProducto) {
        this.IDProducto = IDProducto;
    }

    public Integer getCantidad() {
        return Cantidad;
    }

    public void setCantidad(Integer Cantidad) {
        this.Cantidad = Cantidad;
    }

}
