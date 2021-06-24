package Datos;


public class Modelo {
    
    //Atributos
    private Integer IDModelo;
    private String Modelo;

    //Metodos
    public Modelo(String Modelo) {
        this.Modelo = Modelo;
    }

    public Integer getIDModelo() {
        return IDModelo;
    }

    public void setIDModelo(Integer IDModelo) {
        this.IDModelo = IDModelo;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String Modelo) {
        this.Modelo = Modelo;
    }
    
    
}
