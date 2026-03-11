package modelo;

public class Ingrediente {

    private int idIngrediente;
    private String nombre;

    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
    private String rutaImagen;

    public Ingrediente(int idIngrediente, String nombre, String rutaImagen) {
        this.idIngrediente = idIngrediente;
        this.nombre = nombre;
        this.rutaImagen = rutaImagen;
    }

    public int getIdIngrediente() {
        return idIngrediente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }
}
