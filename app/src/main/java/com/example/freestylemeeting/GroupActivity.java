package com.example.freestylemeeting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.freestylemeeting.AdaptersList.EstacionAdapter;
import com.example.freestylemeeting.AdaptersList.GrupoAdapter;
import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.DAO.UserDao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

import Modelo.Client;
import Modelo.Estacion;
import Modelo.Grupo;

public class GroupActivity extends AppCompatActivity {
    ArrayList<Grupo> grupos;
    private GrupoAdapter grupoAdapter;
    RecyclerView mMainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        Button botonCrearGrupo = (Button) findViewById(R.id.botonCrearGrupo);
        botonCrearGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupActivity.this,CrearGruposActivity.class));
            }
        });

        Button botonAtras = (Button) findViewById(R.id.botonAtrasGrupos);
        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupActivity.this,NavegationDrawerActivity.class));
            }
        });

        /* Introducir Estaciones de la BD en la lista */
        grupos = new ArrayList<>();
        grupoAdapter = new GrupoAdapter(GroupActivity.this, grupos);

        mMainList = (RecyclerView) findViewById(R.id.idListViewGrupos);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(grupoAdapter);

        UserDao.getUsersCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Client cliente = documentSnapshot.toObject(Client.class);
                EstacionDao.getEstacionesCollection().document(cliente.getCurrentEstacion()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Estacion estacion = documentSnapshot.toObject(Estacion.class);
                        if (estacion != null) {
                            grupos.addAll(estacion.getGrupos());
                            grupoAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
    }
}