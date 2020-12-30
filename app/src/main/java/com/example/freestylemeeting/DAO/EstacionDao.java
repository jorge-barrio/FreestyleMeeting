package com.example.freestylemeeting.DAO;

import android.content.Intent;
import android.view.View;

import com.example.freestylemeeting.ListPistasActivity;
import com.example.freestylemeeting.authActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Modelo.Client;
import Modelo.Estacion;
import Modelo.Grupo;
import Modelo.Pista;
import Modelo.Reserva;
import Modelo.User;
import Modelo.UserEstacion;


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
     * @param pista
     */
    public static void addPista (Pista pista){
        UserDao.getEnterprisesCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserEstacion trabajador = documentSnapshot.toObject(UserEstacion.class);
                if(trabajador != null) {
                    EstacionDao.getEstacionesCollection().document(trabajador.getCifEmpresa()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Estacion estacion = documentSnapshot.toObject(Estacion.class);
                            if (estacion != null) {
                                for (Pista p : estacion.getPistas()){
                                    if(p.getId().equals(pista.getId()))
                                        return;
                                }
                                estacion.getPistas().add(pista);
                                Map<String,Object> map = new HashMap<>();
                                map.put("pistas",estacion.getPistas());
                                getEstacionesCollection().document(estacion.getCif()).update(map);
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     *
     * @param pista
     */
    public static void editPista (Pista pista){
        UserDao.getEnterprisesCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserEstacion trabajador = documentSnapshot.toObject(UserEstacion.class);
                if(trabajador != null) {
                    EstacionDao.getEstacionesCollection().document(trabajador.getCifEmpresa()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Estacion estacion = documentSnapshot.toObject(Estacion.class);
                            ArrayList<Pista> pistas = estacion.getPistas();
                            Pista p;

                            if (estacion != null) {
                                for (int i = 0; i < pistas.size(); i++){
                                    p = pistas.get(i);
                                    if(p.getId().equals(pista.getId())){
                                        pistas.set(i, pista);
                                        //estacion.getPistas().add(pista);
                                        Map<String,Object> map = new HashMap<>();
                                        map.put("pistas",pistas);
                                        getEstacionesCollection().document(estacion.getCif()).update(map);
                                        return;
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     *
     * @param pista
     */
    public static void deletePista (Pista pista){
        UserDao.getEnterprisesCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserEstacion trabajador = documentSnapshot.toObject(UserEstacion.class);
                if(trabajador != null) {
                    EstacionDao.getEstacionesCollection().document(trabajador.getCifEmpresa()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Estacion estacion = documentSnapshot.toObject(Estacion.class);
                            ArrayList<Pista> pistas = estacion.getPistas();
                            Pista p;

                            if (estacion != null) {
                                for (int i = 0; i < pistas.size(); i++){
                                    p = pistas.get(i);
                                    if(p.getId().equals(pista.getId())){
                                        pistas.remove(i);
                                        //estacion.getPistas().add(pista);
                                        Map<String,Object> map = new HashMap<>();
                                        map.put("pistas",pistas);
                                        getEstacionesCollection().document(estacion.getCif()).update(map);
                                        return;
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
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
        estaciontmp = estacion;
    }

    public static CollectionReference getEstacionesCollection(){
         db = FirebaseFirestore.getInstance();
         return db.collection("Estaciones");
    }

    public static void editGrupo(Grupo grupo){
        UserDao.getUsersCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Client cliente = documentSnapshot.toObject(Client.class);
                EstacionDao.getEstacionesCollection().document(cliente.getCurrentEstacion()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Estacion estacion = documentSnapshot.toObject(Estacion.class);
                        ArrayList<Grupo> grupos = estacion.getGrupos();
                        Grupo g;
                        if (estacion != null) {
                            for (int i = 0; i < grupos.size(); i++) {
                                g = grupos.get(i);
                                if (g.getId().equals(grupo.getId())) {
                                    grupos.set(i, grupo);
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("grupos", grupos);
                                    getEstacionesCollection().document(estacion.getCif()).update(map);
                                    return;
                                }
                            }
                        }
                    }
                });
            }
        });

    }

    public static void addGrupo(Grupo grupo){
        UserDao.getUsersCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Client cliente = documentSnapshot.toObject(Client.class);
                EstacionDao.getEstacionesCollection().document(cliente.getCurrentEstacion()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Estacion estacion = documentSnapshot.toObject(Estacion.class);
                        ArrayList<Grupo> grupos = estacion.getGrupos();
                        if (estacion != null) {
                            grupos.add(grupo);
                            Map<String, Object> map = new HashMap<>();
                            map.put("grupos", grupos);
                            getEstacionesCollection().document(estacion.getCif()).update(map);
                            return;
                        }
                    }
                });
            }
        });

    }

    public static void addReserva(Reserva reserva){
        UserDao.getUsersCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Client cliente = documentSnapshot.toObject(Client.class);
                EstacionDao.getEstacionesCollection().document(cliente.getCurrentEstacion()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Estacion estacion = documentSnapshot.toObject(Estacion.class);
                        ArrayList<Reserva> reservas = estacion.getReservas();
                        if (estacion != null) {
                            reservas.add(reserva);
                            Map<String, Object> map = new HashMap<>();
                            map.put("reservas", reservas);
                            getEstacionesCollection().document(estacion.getCif()).update(map);
                            UserDao.addReserva(reserva);
                            return;
                        }
                    }
                });
            }
        });

    }
}

