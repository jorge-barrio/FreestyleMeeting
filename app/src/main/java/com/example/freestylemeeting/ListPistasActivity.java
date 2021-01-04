package com.example.freestylemeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freestylemeeting.AdaptersList.PistaAdapter;
import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.DAO.UserDao;
import com.example.freestylemeeting.DAO.myCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import Modelo.Client;
import Modelo.Estacion;
import Modelo.Pista;
import Modelo.UserEstacion;

public class ListPistasActivity extends AppCompatActivity {

    List<Pista> pistas;
    private PistaAdapter pistaAdapter;
    RecyclerView mMainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pistas);
        TextView titulo = findViewById(R.id.textListaDePistas);
        FloatingActionButton addPistaButton = findViewById(R.id.add_pista_button);
        FloatingActionButton closeTraining = findViewById(R.id.close_training);
        addPistaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListPistasActivity.this, CreatePistaActivity.class);
                startActivity(intent);
            }
        });
        closeTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDao.closeTraining(new myCallback(){
                    @Override
                    public void onCallback(boolean status) {
                        if (status){
                            Toast.makeText(ListPistasActivity.this, "Entrenamiento finalizado con exito", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ListPistasActivity.this, NavegationDrawerActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(ListPistasActivity.this, "No se puede guardar un entrenamiento vacío", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ListPistasActivity.this, NavegationDrawerActivity.class);
                            startActivity(intent);
                        }

                    }

                });

            }
        });

        /* Mostrar Pistas en la lista */
        pistas = new ArrayList<>();
        pistaAdapter = new PistaAdapter(ListPistasActivity.this, pistas);

        mMainList = (RecyclerView) findViewById(R.id.idListViewPistas);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(pistaAdapter);

        // Mostramos primero las pistas cacheadas mientras se actualizan
        pistas.addAll(EstacionDao.currentEstacion.getPistas());

        Estacion estacion;

        UserDao.getUsersCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Client cliente = documentSnapshot.toObject(Client.class);
                if (cliente != null) {
                    if(cliente.isEntrenamientoActivo()){
                        closeTraining.setVisibility(View.VISIBLE);
                        titulo.setText("Selecciona tus pistas:\n-------------------------");
                    }
                    EstacionDao.getEstacionesCollection().document(cliente.getCurrentEstacion()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Estacion estacion = documentSnapshot.toObject(Estacion.class);
                            if (estacion != null) {
                                pistas.removeAll(pistas);
                                pistas.addAll(estacion.getPistas());
                                pistaAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                } else {
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
                                            pistas.removeAll(pistas);
                                            pistas.addAll(estacion.getPistas());
                                            pistaAdapter.notifyDataSetChanged();
                                        }
                                    }
                                });

                                //Activamos la visibilidad del floating button
                                addPistaButton.setVisibility(View.VISIBLE);

                            } else {
                                Intent intent = new Intent(ListPistasActivity.this, authActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListPistasActivity.this, NavegationDrawerActivity.class);
        startActivity(intent);
    }
}