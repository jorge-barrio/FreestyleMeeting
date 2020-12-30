package com.example.freestylemeeting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.freestylemeeting.AdaptersList.EstacionAdapter;
import com.example.freestylemeeting.AdaptersList.MisReservasAdapter;
import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.DAO.UserDao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import Modelo.Client;
import Modelo.Estacion;
import Modelo.Reserva;

public class ListMisReservas extends AppCompatActivity {

    List<Reserva> misreservas;
    private MisReservasAdapter misreservasAdapter;
    RecyclerView mMainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mis_reservas);

        /* Introducir Estaciones de la BD en la lista */
        misreservas = new ArrayList<>();
        misreservasAdapter = new MisReservasAdapter(ListMisReservas.this, misreservas);

        mMainList = (RecyclerView) findViewById(R.id.idListViewMisReservas);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(misreservasAdapter);

        UserDao.getUsersCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Client cliente = documentSnapshot.toObject(Client.class);
                misreservas.addAll(cliente.getReservasActualesAAntiguas());
                misreservasAdapter.notifyDataSetChanged();
            }
        });
    }
}