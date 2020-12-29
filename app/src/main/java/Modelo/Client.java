package Modelo;

import java.sql.Array;
import java.util.ArrayList;

public class Client extends User{

    private ArrayList<Entrenamiento> entrenamientos;
    private ArrayList<ReservaCliente> reservas;
    private String currentEstacion;
    private boolean entrenamientoActivo;

    public Client(){}

    public Client(String name, String email, String password){

        super(name,email,password);
        this.entrenamientoActivo = false;
        this.entrenamientos = new ArrayList<Entrenamiento>();
        this.reservas = new ArrayList<ReservaCliente>();
        this.currentEstacion = null;

    }

    public ArrayList<Entrenamiento> getEntrenamientos() {
        return entrenamientos;
    }

    public void setEntrenamientos(ArrayList<Entrenamiento> entrenamientos) {
        this.entrenamientos = entrenamientos;
    }

    public ArrayList<ReservaCliente> getReservas() {
        return reservas;
    }

    public void setReservas(ArrayList<ReservaCliente> reservas) {
        this.reservas = reservas;
    }

    public String getCurrentEstacion() {
        return currentEstacion;
    }

    public void setCurrentEstacion(String currentEstacion) {
        this.currentEstacion = currentEstacion;
    }

    public boolean isEntrenamientoActivo() {
        return entrenamientoActivo;
    }

    public void setEntrenamientoActivo(boolean entrenamientoActivo) {
        this.entrenamientoActivo = entrenamientoActivo;
    }
}
