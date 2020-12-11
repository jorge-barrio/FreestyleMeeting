package Modelo;

import java.util.ArrayList;

public class Client extends User{
    private String nombreCompleto;
    private ArrayList<Entrenamiento> entrenamientos;
    private ArrayList<Reservas> reservas;
    private String currentEstacion;

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public ArrayList<Entrenamiento> getEntrenamientos() {
        return entrenamientos;
    }

    public void setEntrenamientos(ArrayList<Entrenamiento> entrenamientos) {
        this.entrenamientos = entrenamientos;
    }

    public ArrayList<Reservas> getReservas() {
        return reservas;
    }

    public void setReservas(ArrayList<Reservas> reservas) {
        this.reservas = reservas;
    }

    public String getCurrentEstacion() {
        return currentEstacion;
    }

    public void setCurrentEstacion(String currentEstacion) {
        this.currentEstacion = currentEstacion;
    }

    public Client(){}
    public Client(String name, String email, String password, String nombreCompleto){
        super(name,email,password);
        this.nombreCompleto=nombreCompleto;
        this.entrenamientos = new ArrayList<Entrenamiento>();
        this.reservas = new ArrayList<Reservas>();
    }
}
