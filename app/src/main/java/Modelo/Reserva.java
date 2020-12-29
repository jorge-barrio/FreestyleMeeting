package Modelo;

import com.google.type.DateTime;

import java.util.Date;
import java.util.UUID;

public class Reserva {
    private String idReserva;
    private String correoCliente;
    private String nombrePack;
    private Date fechaRecogida;
    private int duracion;
    private float peso;
    private float altura;
    private float precioEuros;
    private int talla;

    public Reserva() {
        this.idReserva = UUID.randomUUID().toString();
    }

    public String getCorreoCliente() { return correoCliente; }

    public void setCorreoCliente(String correoCliente) { this.correoCliente = correoCliente; }

    public String getIdReserva() { return idReserva; }

    public Date getFechaRecogida() {
        return fechaRecogida;
    }

    public void setFechaRecogida(Date fechaRecogida) {
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

    public float getPrecioEuros() { return precioEuros; }

    public void setPrecioEuros(float precioEuros) { this.precioEuros = precioEuros; }

    public String getNombrePack() { return nombrePack; }

    public void setNombrePack(String nombrePack) { this.nombrePack = nombrePack; }

    public int getTalla() { return talla; }

    public void setTalla(int talla) { this.talla = talla; }
}
