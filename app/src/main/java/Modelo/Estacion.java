package Modelo;

import java.util.ArrayList;

public class Estacion {
    private String nombre;
    private ArrayList<Pista> pistas;
    private ArrayList<PackReserva> packsReserva;
    private String latitud;
    private String longitud;

    public Estacion (String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }
}
