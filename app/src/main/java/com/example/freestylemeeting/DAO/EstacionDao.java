package com.example.freestylemeeting.DAO;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import Modelo.Estacion;
import Modelo.Pista;


public class EstacionDao {

    static FirebaseFirestore myDatabase = FirebaseFirestore.getInstance();
    private final static String callectionEstacionesName = "Estaciones";
    private final static String callectionPistasName = "Pistas";

    public static Estacion[] getEstaciones(){
        DocumentReference estaciones = myDatabase.collection(callectionEstacionesName).document();
        System.out.println("Estaciones: "+estaciones);
        //To-Do
        Estacion[] result = {new Estacion("Alto campoo"), new Estacion("Baqueira-Beret"), new Estacion("Formigal")};
        return result;
    }

    public static Pista[] getPistasByEstacionId(String id){
        CollectionReference pistas = myDatabase.collection(callectionEstacionesName).document(id).collection(callectionPistasName);
        return new Pista[0];
    }
}

