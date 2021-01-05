package com.example.freestylemeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.DAO.UserDao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import Modelo.Client;
import Modelo.Estacion;
import Modelo.UserEstacion;

public class StarterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_starter);
        if(UserDao.sesionIniciada()){
            UserDao.getUsersCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Client cliente = documentSnapshot.toObject(Client.class);

                    // Comprobar si se trata de un cliente o un trabajador
                    if(cliente != null){
                        UserDao.currentCliente = cliente;
                        if(cliente.getCurrentEstacion() != null){
                            EstacionDao.getEstacionesCollection().document(cliente.getCurrentEstacion()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Estacion estacion = documentSnapshot.toObject(Estacion.class);
                                    if (estacion != null) {
                                        EstacionDao.currentEstacion = estacion;
                                        if(cliente.isEntrenamientoActivo()){
                                            goToListPistas();
                                        } else {
                                            goToNavegationDrawer();
                                        }
                                    } else {
                                        goToNavegationDrawer();
                                    }
                                }
                            });
                        } else {
                            goToNavegationDrawer();
                        }
                    } else {
                        UserDao.getEnterprisesCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                UserEstacion trabajador = documentSnapshot.toObject(UserEstacion.class);
                                if(trabajador != null){
                                    if(trabajador.getCifEmpresa() != null) {
                                        UserDao.currentEmpleado = trabajador;
                                        String cifEstacion;
                                        cifEstacion = trabajador.getCifEmpresa();
                                        EstacionDao.getEstacionesCollection().document(cifEstacion).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                Estacion estacion = documentSnapshot.toObject(Estacion.class);
                                                if (estacion != null)
                                                    EstacionDao.currentEstacion = estacion;

                                                goToNavegationDrawer();
                                            }
                                        });
                                    } else {
                                        Toast.makeText(StarterActivity.this, "Error. Empleado sin cif", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    startActivity(new Intent(StarterActivity.this, NavegationDrawerActivity.class));
                                    finish();
                                }
                            }
                        });
                    }
                }
            });
        } else {
            startActivity(new Intent(StarterActivity.this, authActivity.class));
            finish();
        }
    }

    private void goToListPistas(){
        startActivity(new Intent(StarterActivity.this, ListPistasActivity.class));
        finish();
    }

    private void goToNavegationDrawer(){
        startActivity(new Intent(StarterActivity.this, NavegationDrawerActivity.class));
        finish();
    }
}