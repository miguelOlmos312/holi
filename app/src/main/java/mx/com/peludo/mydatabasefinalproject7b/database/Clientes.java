package mx.com.peludo.mydatabasefinalproject7b.database;

public class Clientes {
    // Atributos
    private int clave;
    private String nombre;
    private String direccion;

    public Clientes() {
    }

    public Clientes(int clave, String nombre, String direccion) {
        this.clave = clave;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
