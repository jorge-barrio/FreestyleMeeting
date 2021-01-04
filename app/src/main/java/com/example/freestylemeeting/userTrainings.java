package com.example.freestylemeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.freestylemeeting.AdaptersList.EntrenamientoAdapter;
import com.example.freestylemeeting.AdaptersList.PistaAdapter;
import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.DAO.UserDao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import Modelo.Client;
import Modelo.Entrenamiento;
import Modelo.Estacion;
import Modelo.Pista;
import Modelo.UserEstacion;

public class userTrainings extends AppCompatActivity {
    List<Entrenamiento> entrenamientos;
    private EntrenamientoAdapter entAdapter;
    RecyclerView mMainList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_trainings);

        entrenamientos = new ArrayList<>();
        entAdapter = new EntrenamientoAdapter(userTrainings.this, entrenamientos);

        mMainList = (RecyclerView) findViewById(R.id.idListViewEntrenamientos);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(entAdapter);

        Estacion estacion;

        UserDao.getUsersCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Client cliente = documentSnapshot.toObject(Client.class);
                if (cliente != null) {
                    entrenamientos.addAll(cliente.getEntrenamientos());
                    entAdapter.notifyDataSetChanged();
                }
            }
        });



    }
}