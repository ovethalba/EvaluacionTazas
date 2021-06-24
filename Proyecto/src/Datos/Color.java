package Datos;

public class Color {

    //Atributos
    private Integer IDColor;
    private String Color;

    //Metodos
    public Color(String Color) {
        this.Color = Color;
    }

    public Integer getIDColor() {
        return IDColor;
    }

    public void setIDColor(Integer IDColor) {
        this.IDColor = IDColor;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }

    public Color(Integer IDColor, String Color) {
        this.IDColor = IDColor;
        this.Color = Color;
    }

    
}
