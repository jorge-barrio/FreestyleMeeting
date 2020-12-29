package Modelo;

import java.util.Date;

public class ReservaCliente {
    private String idReserva;
    private String cifEstacion;
    private Date fecha;

    public ReservaCliente(){}

    public ReservaCliente(String idReserva, Date fecha, String cifEstacion) {
        this.idReserva = idReserva;
        this.fecha = fecha;
        this.cifEstacion = cifEstacion;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public String getCifEstacion() {
        return cifEstacion;
    }

    public void setCifEstacion(String cifEstacion) {
        this.cifEstacion = cifEstacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
