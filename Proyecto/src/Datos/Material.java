package Datos;

public class Material {

    //Atributos
    private Integer IDMaterial;
    private String Material;

    //Metodos
    public Material(String Material) {
        this.Material = Material;
    }

    public Integer getIDMaterial() {
        return IDMaterial;
    }

    public void setIDMaterial(Integer IDMaterial) {
        this.IDMaterial = IDMaterial;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String Material) {
        this.Material = Material;
    }

}
