package Modelo;

import java.util.ArrayList;

public class Grupo {
    private String nombre;
    private String modalidad;
    private int tam;
    private String nivel;
    private ArrayList<String> miembros;

    public Grupo( String nombre, String modalidad, int tam, ArrayList<String> correos, String nivel) {
        this.nombre=nombre;
        this.modalidad = modalidad;
        this.tam = tam;
        miembros= correos;
        this.nivel = nivel;
    }

    public String getNivel() { return nivel; }

    public String getNombre() {
        return nombre;
    }

    public String getModalidad() {
        return modalidad;
    }

    public int getTam() {
        return tam;
    }

    public ArrayList<String> getMiembros() {
        return miembros;
    }

    public void addMiembro(String correo){
        miembros.add(correo);
    }
}
