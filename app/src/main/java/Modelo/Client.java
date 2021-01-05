package Modelo;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Client extends User{

    private ArrayList<Entrenamiento> entrenamientos;
    private ArrayList<Reserva> reservas;
    private String currentEstacion;
    private boolean entrenamientoActivo;

    public Client(){}

    public Client(String name, String email, String password){

        super(name,email,password);
        this.entrenamientoActivo = false;
        this.entrenamientos = new ArrayList<Entrenamiento>();
        this.reservas = new ArrayList<Reserva>();
        this.currentEstacion = null;

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

    public ArrayList<Reserva> getReservasActualesAAntiguas() {
        ArrayList<Reserva> reservasOrdenada = (ArrayList<Reserva>) reservas.clone();
        Collections.sort(reservasOrdenada, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva r1, Reserva r2) {
                return r2.getFechaRecogida().compareTo(r1.getFechaRecogida());
            }
        });

        return reservasOrdenada;
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

    public boolean isEntrenamientoActivo() {
        return entrenamientoActivo;
    }

    public void setEntrenamientoActivo(boolean entrenamientoActivo) {
        this.entrenamientoActivo = entrenamientoActivo;
    }
}
