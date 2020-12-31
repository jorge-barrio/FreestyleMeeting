package Modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class Entrenamiento implements Parcelable {
    private String id;
    private ArrayList<String> idPistas;
    private Date fechaInicio;
    private Date fechaFin;
    private String cifEstacion;

    public Entrenamiento() {}

    public Entrenamiento(String id, Date fechaInicio, Date fechaFin, String cifEstacion) {
        this.id = id;
        this.idPistas = new ArrayList<>();
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cifEstacion = cifEstacion;
    }

    protected Entrenamiento(Parcel in) {
        id = in.readString();
        cifEstacion = in.readString();
        idPistas = new ArrayList<>();
        in.readStringList(idPistas);
        fechaInicio = (java.util.Date) in.readSerializable();
        fechaFin = (java.util.Date) in.readSerializable();

    }

    public static final Creator<Entrenamiento> CREATOR = new Creator<Entrenamiento>() {
        @Override
        public Entrenamiento createFromParcel(Parcel in) {
            return new Entrenamiento(in);
        }

        @Override
        public Entrenamiento[] newArray(int size) {
            return new Entrenamiento[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeStringList(idPistas);
        dest.writeString(cifEstacion);
        dest.writeSerializable(fechaInicio);
        dest.writeSerializable(fechaFin);
    }
}
