package Modelo;

import java.util.ArrayList;
import java.util.HashMap;

public class Estacion {

    private String cif;
    private String nombre;
    private ArrayList<Pista> pistas;
    private ArrayList<PackReserva> packsReserva;
    private String latitud;
    private String longitud;
    private String localidad;
    private String emailTienda;
    private ArrayList<Grupo> grupos;
    private ArrayList<Reserva> reservas;

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
        this.grupos = new ArrayList<>();
        this.emailTienda = "";
        this.reservas = new ArrayList<>();
    }

    public ArrayList<Reserva> getReservas() { return reservas; }

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

    public ArrayList<Grupo> getGrupos() { return grupos; }

    public void addGrupo(Grupo grupo){
        grupos.add(grupo);
    }

    public HashMap<String, Object> toHasMap () {
        HashMap<String, Object> map = new HashMap<>();
        map.put("cif",cif);
        map.put("nombre",nombre);
        map.put("pistas",pistas);
        map.put("packsReserva",packsReserva);
        map.put("latitud",latitud);
        map.put("longitud",longitud);
        map.put("localidad",localidad);
        map.put("emailTienda",emailTienda);
        return map;
    }
}
