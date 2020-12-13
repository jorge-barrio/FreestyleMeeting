package com.example.freestylemeeting.DAO;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import Modelo.Estacion;
import Modelo.Pista;



public class EstacionDao {

    private static FirebaseFirestore db;
    public static Estacion estaciontmp = null;

    /**
     * Crea una nueva estacion
     * @param estacion
     */
    public static void postEstacion (Estacion estacion){
        getEstacionesCollection().document(estacion.getCif()).set(estacion);
    }

    /**
     *
     * @param cif
     */
    public void getEstacion (String cif, EstacionCallback estacionCallback) {
        getEstacionesCollection().document(cif).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Estacion estacion = documentSnapshot.toObject(Estacion.class);
                estacionCallback.onCallback(estacion);
            }
        });
    }

    private static void saveEstacion(Estacion estacion) {
        System.out.println("Aqui llego");
        estaciontmp = estacion;
    }

    public static CollectionReference getEstacionesCollection(){
         db = FirebaseFirestore.getInstance();
         return db.collection("Estaciones");
    }
}

