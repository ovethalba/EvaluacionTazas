package Datos;

public class Dimensiones {

    //Atributos
    private Integer IDDimensiones;
    private Integer Alto;
    private Integer Ancho;
    private Integer Largo;

    //Metodos
    public Dimensiones(Integer Alto, Integer Ancho, Integer Largo) {
        this.Alto = Alto;
        this.Ancho = Ancho;
        this.Largo = Largo;
    }

    public Integer getIDDimensiones() {
        return IDDimensiones;
    }

    public void setIDDimensiones(Integer IDDimensiones) {
        this.IDDimensiones = IDDimensiones;
    }

    public Integer getAlto() {
        return Alto;
    }

    public void setAlto(Integer Alto) {
        this.Alto = Alto;
    }

    public Integer getAncho() {
        return Ancho;
    }

    public void setAncho(Integer Ancho) {
        this.Ancho = Ancho;
    }

    public Integer getLargo() {
        return Largo;
    }

    public void setLargo(Integer Largo) {
        this.Largo = Largo;
    }

}
