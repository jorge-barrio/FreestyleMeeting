package Modelo;

public class Estacion {
    private String nombre;
    private Pista[] pistas;
    private String latitud;
    private String longitud;

    public Estacion (String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }
}
