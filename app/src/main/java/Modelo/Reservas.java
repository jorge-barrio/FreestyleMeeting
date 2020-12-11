package Modelo;

import com.google.type.DateTime;

public class Reservas {
    private String idReserva;
    private String idEstacion;
    private String idPack;
    private DateTime fechaRecogida;
    private int duracion;
    private float peso;
    private float altura;

    public Reservas(String idReserva, String idEstacion, String idPack, DateTime fechaRecogida, int duracion, float peso, float altura) {
        this.idReserva = idReserva;
        this.idEstacion = idEstacion;
        this.idPack = idPack;
        this.fechaRecogida = fechaRecogida;
        this.duracion = duracion;
        this.peso = peso;
        this.altura = altura;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public String getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(String idEstacion) {
        this.idEstacion = idEstacion;
    }

    public String getIdPack() {
        return idPack;
    }

    public void setIdPack(String idPack) {
        this.idPack = idPack;
    }

    public DateTime getFechaRecogida() {
        return fechaRecogida;
    }

    public void setFechaRecogida(DateTime fechaRecogida) {
        this.fechaRecogida = fechaRecogida;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }
}
