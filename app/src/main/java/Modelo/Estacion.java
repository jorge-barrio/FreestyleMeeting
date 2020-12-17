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
    private String emailTienda;

    public Estacion () {
        // Default constructor required for calls to DataSnapshot.getValue(Estacion.class)
    }

    public Estacion (String cif, String nombre){
        this.cif = cif;
        this.nombre = nombre;
        this.latitud = "";
        this.longitud = "";
        this.pistas = new ArrayList<>();
        this.packsReserva = new ArrayList<>();
        this.emailTienda = "";
    }

    public String getCif() {
        return cif;
    }

    public String getNombre(){
        return nombre;
    }

    public String getLocalidad() { return localidad; }

    public ArrayList<Pista> getPistas() { return pistas; }

    public ArrayList<PackReserva> getPacksReserva() { return packsReserva; }

    public String getEmailTienda () { return emailTienda; }
}
