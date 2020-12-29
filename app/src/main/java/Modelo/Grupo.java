package Modelo;

import java.util.ArrayList;
import java.util.UUID;

public class Grupo {
    private String id;
    private String nombre;
    private String modalidad;
    private int tam;
    private String nivel;
    private ArrayList<String> miembros = new ArrayList<>();
    private String fecha;
    private String hora;
    private String lugar;

    public Grupo(){}

    public Grupo(String nombre, String modalidad, int tam, String correo, String nivel, String fecha, String hora, String lugar) {
        this.id = UUID.randomUUID().toString();
        this.nombre=nombre;
        this.modalidad = modalidad;
        this.tam = tam;
        miembros.add(correo);
        this.nivel = nivel;
        this.fecha = fecha;
        this.hora = hora;
        this.lugar = lugar;
    }

    public String getId() { return id; }

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

    public String getFecha() { return fecha; }

    public String getHora() { return hora; }

    public String getLugar() { return lugar; }


     //Devuelve cadena vacia si tod es correcto, y una cadena con el mensaje de error a imprimir que haya ocurrido

    public String addMiembro(String correo){
        String resultado = "";
        if (miembros.contains(correo) || correo==null){
            resultado="Ya formas parte de este grupo";
        }else if(miembros.size()==tam){
            resultado="Grupo completo";
        }else{
            miembros.add(correo);
        }
        return resultado;
    }
}
