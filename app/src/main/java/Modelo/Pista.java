package Modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Array;
import java.util.ArrayList;
import java.util.UUID;

public class Pista implements Parcelable {
    private String id;
    private String nombre;
    private String dificultad;
    private boolean disponible = false;
    private String avisos;
    private ArrayList<String> usuariosActivos;

    public Pista(){ }

    public Pista(String nombre, String dificultad){
        this.id = UUID.randomUUID().toString();
        System.out.println("ID:"+id);
        this.nombre = nombre;
        this.dificultad = dificultad;
        this.disponible = false;
        this.avisos = "";
        this.usuariosActivos = new ArrayList<>();
    }

    protected Pista(Parcel in) {
        id = in.readString();
        nombre = in.readString();
        dificultad = in.readString();
        disponible = in.readInt() == 0 ? false : true;
        avisos = in.readString();
        usuariosActivos = new ArrayList<>();
        in.readStringList(usuariosActivos);
        System.out.println("EEEEOOOOOOO"+usuariosActivos.size());
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

    public boolean getDisponible(){ return disponible; }

    public void setDisponible(boolean disponible){ this.disponible = disponible; }

    public String getAvisos() {
        return avisos;
    }

    public void setAvisos(String avisos) {
        this.avisos = avisos;
    }

    public ArrayList<String> getUsuariosActivos() { return usuariosActivos; }

    public void setUsuariosActivos(ArrayList<String> usuariosActivos){ this.usuariosActivos = usuariosActivos; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeString(dificultad);
        dest.writeInt(disponible ? 1 : 0);
        dest.writeString(avisos);
        dest.writeStringList(usuariosActivos);
    }
}
