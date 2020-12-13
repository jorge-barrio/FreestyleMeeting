package Modelo;

import java.util.ArrayList;
import java.util.Date;

public class Entrenamiento {
    private String id;
    private ArrayList<String> idPistas;
    private Date fechaInicio;
    private Date fechaFin;
    private String cifEstacion;

    public Entrenamiento() {}

    public Entrenamiento(String id, Date fechaInicio, Date fechaFin, String cifEstacion) {
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

    public Date getFechaInicio() { return fechaInicio; }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getCifEstacion() {
        return cifEstacion;
    }

    public void setCifEstacion(String cifEstacion) {
        this.cifEstacion = cifEstacion;
    }
}
