package Modelo;

import java.util.ArrayList;

public class Estacion {

    private String cif;
    private String nombre;
    private ArrayList<Pista> pistas;
    private ArrayList<PackReserva> packsReserva;
    private String latitud;
    private String longitud;
    private String localidad;

    public static Estacion lastEstacion;

    public Estacion () {
        // Default constructor required for calls to DataSnapshot.getValue(Estacion.class)
        lastEstacion = this;
    }
    public Estacion (String cif, String nombre){
        this.cif = cif;
        this.nombre = nombre;
        this.latitud = "";
        this.longitud = "";
        this.pistas = new ArrayList<>();
        this.packsReserva = new ArrayList<>();
    }

    public String getCif() {
        return cif;
    }

    public String getNombre(){
        return nombre;
    }

    public String getLocalidad() { return localidad; }
}
