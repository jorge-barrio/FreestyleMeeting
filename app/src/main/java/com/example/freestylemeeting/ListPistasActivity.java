package com.example.freestylemeeting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.example.freestylemeeting.AdaptersList.PistaAdapter;
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
import Modelo.Pista;

public class ListPistasActivity extends AppCompatActivity {

    List<Pista> pistas;
    private PistaAdapter pistaAdapter;
    RecyclerView mMainList;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pistas);

        /* Introducir Estaciones de la BD en la lista */
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
                EstacionDao.getEstacionesCollection().document(cliente.getCurrentEstacion()).collection("pistas").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            //Error
                        } else {
                            for (DocumentChange doc : value.getDocumentChanges()) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    Pista pista = doc.getDocument().toObject(Pista.class);
                                    pistas.add(pista);
                                    pistaAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                });
            }
        });
    }
}