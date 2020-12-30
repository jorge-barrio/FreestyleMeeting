package com.example.freestylemeeting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.example.freestylemeeting.AdaptersList.EstacionAdapter;
import com.example.freestylemeeting.DAO.EstacionDao;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import Modelo.Estacion;

public class SelectEstacionActivity extends AppCompatActivity {

    List<Estacion> estaciones;
    private EstacionAdapter estacionAdapter;
    RecyclerView mMainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_estacion);

        /* Introducir Estaciones de la BD en la lista */
        estaciones = new ArrayList<>();
        estacionAdapter = new EstacionAdapter(SelectEstacionActivity.this, estaciones);

        mMainList = (RecyclerView) findViewById(R.id.idListViewEstaciones);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(estacionAdapter);

        EstacionDao.getEstacionesCollection().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    //Error
                } else {
                    for(DocumentChange doc : value.getDocumentChanges()){
                        if(doc.getType() == DocumentChange.Type.ADDED){
                            Estacion estacion = doc.getDocument().toObject(Estacion.class);
                            estaciones.add(estacion);
                            estacionAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }
}