package Modelo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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

    public ArrayList<Grupo> getGrupos() { return grupos; }

    public ArrayList<Grupo> getGruposActualesOrdenados() {
        ArrayList<Grupo> listaOrdenada = (ArrayList<Grupo>) grupos.clone();

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        //DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Collections.sort(listaOrdenada, new Comparator<Grupo>() {
            @Override
            public int compare(Grupo g1, Grupo g2) {
                int barra1;
                int barra2;
                int barra3;

                String f1 = g1.getFecha();
                String f2 = g2.getFecha();

                barra1 = f1.indexOf('/');
                barra2 = f1.indexOf('/', barra1+1);

                String f1Invert = f1.substring(barra2+1, f1.length())+f1.substring(barra1,barra2+1)+f1.substring(0,barra1)+" "+g1.getHora();

                System.out.println("FECHA1: "+f1Invert);

                barra1 = f2.indexOf('/');
                barra2 = f2.indexOf('/', barra1+1);

                String f2Invert = f2.substring(barra2+1,f2.length())+f2.substring(barra1,barra2+1)+f2.substring(0,barra1)+" "+g2.getHora();

                System.out.println("FECHA2: "+f2Invert);

                return f1Invert.compareTo(f2Invert);
            }
        });

        return listaOrdenada;
    }

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
