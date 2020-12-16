package com.example.freestylemeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.freestylemeeting.AdaptersList.PistaAdapter;
import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.DAO.UserDao;
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

        FloatingActionButton addPistaButton = findViewById(R.id.add_pista_button);
        addPistaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ListPistasActivity.this, "Funcionalidad en construccion...", Toast.LENGTH_SHORT).show();
            }
        });

        /* Mostrar Pistas en la lista */
        pistas = new ArrayList<>();
        pistaAdapter = new PistaAdapter(ListPistasActivity.this, pistas);

        mMainList = (RecyclerView) findViewById(R.id.idListViewPistas);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(pistaAdapter);

        UserDao.getUsersCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Client cliente = documentSnapshot.toObject(Client.class);
                if (cliente != null) {
                    EstacionDao.getEstacionesCollection().document(cliente.getCurrentEstacion()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Estacion estacion = documentSnapshot.toObject(Estacion.class);
                            System.out.println("PISTAS: " + estacion.getPistas().size());
                            if (estacion != null) {
                                pistas.addAll(estacion.getPistas());
                                pistaAdapter.notifyDataSetChanged();
                            }
                            System.out.println("PISTAS DEBERIA MOSTRAR: " + pistas.size());
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
}