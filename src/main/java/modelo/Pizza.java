package modelo;


public class Pizza {

    private String nombre;
    private String imagen;
    private String[] ingredientes;

  
    public Pizza(String nombre, String imagen, String[] ingredientes) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.ingredientes = ingredientes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String[] getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String[] ingredientes) {
        this.ingredientes = ingredientes;
    }
}
