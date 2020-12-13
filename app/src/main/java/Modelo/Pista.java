package Modelo;

public class Pista {
    private String id;
    private String nombre;
    private String dificultad;
    private String notificacion;

    public Pista(){ }

    public Pista(String nombre, String dificultad){
        this.nombre = nombre;
        this.dificultad = dificultad;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(String notificacion) {
        this.notificacion = notificacion;
    }

}
