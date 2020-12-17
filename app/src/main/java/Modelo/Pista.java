package Modelo;

import android.os.Parcel;
import android.os.Parcelable;

public class Pista implements Parcelable {
    private String id;
    private String nombre;
    private String dificultad;
    private String notificacion;

    public Pista(){ }

    public Pista(String id, String nombre, String dificultad){
        this.id = id;
        this.nombre = nombre;
        this.dificultad = dificultad;
    }

    protected Pista(Parcel in) {
        id = in.readString();
        nombre = in.readString();
        dificultad = in.readString();
        notificacion = in.readString();
    }

    public static final Creator<Pista> CREATOR = new Creator<Pista>() {
        @Override
        public Pista createFromParcel(Parcel in) {
            return new Pista(in);
        }

        @Override
        public Pista[] newArray(int size) {
            return new Pista[size];
        }
    };

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(String notificacion) {
        this.notificacion = notificacion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeString(dificultad);
        dest.writeString(notificacion);
    }
}
