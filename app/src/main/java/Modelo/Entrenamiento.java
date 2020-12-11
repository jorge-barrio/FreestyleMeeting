package Modelo;

import com.google.type.DateTime;

import java.time.LocalDate;
import java.util.ArrayList;

public class Entrenamiento {
    private String id;
    private ArrayList<String> idPistas;
    private DateTime fechaInicio;
    private DateTime fechaFin;
    private String cifEstacion;

    public Entrenamiento(String id, DateTime fechaInicio, DateTime fechaFin, String cifEstacion) {
        this.id = id;
        this.idPistas = new ArrayList<String>();
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cifEstacion = cifEstacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getIdPistas() {
        return idPistas;
    }

    public void setIdPistas(ArrayList<String> idPistas) {
        this.idPistas = idPistas;
    }

    public DateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(DateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public DateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(DateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getCifEstacion() {
        return cifEstacion;
    }

    public void setCifEstacion(String cifEstacion) {
        this.cifEstacion = cifEstacion;
    }
}
