package Modelo;

import java.sql.Array;
import java.util.ArrayList;

public class Client extends User{

    private ArrayList<Entrenamiento> entrenamientos;
    private ArrayList<Reserva> reservas;
    private String currentEstacion;

    public Client(){}

    public Client(String name, String email, String password){
        super(name,email,password);
    }

    public ArrayList<Entrenamiento> getEntrenamientos() {
        return entrenamientos;
    }

    public void setEntrenamientos(ArrayList<Entrenamiento> entrenamientos) {
        this.entrenamientos = entrenamientos;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }

    public String getCurrentEstacion() {
        return currentEstacion;
    }

    public void setCurrentEstacion(String currentEstacion) {
        this.currentEstacion = currentEstacion;
    }
}
