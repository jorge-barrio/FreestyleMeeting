package com.example.freestylemeeting;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freestylemeeting.AdaptersList.MisReservasAdapter;
import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.DAO.UserDao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import Modelo.Client;
import Modelo.Estacion;
import Modelo.Reserva;
import Modelo.UserEstacion;

public class ListMisReservasEstacion extends AppCompatActivity {

    List<Reserva> misreservas;
    private MisReservasAdapter misreservasAdapter;
    RecyclerView mMainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_estacion_reservas);

        UserDao.getEnterprisesCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserEstacion trabajador = documentSnapshot.toObject(UserEstacion.class);
                if(trabajador != null){
                    EstacionDao.getEstacionesCollection().document(trabajador.getCifEmpresa()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Estacion estacion = documentSnapshot.toObject(Estacion.class);
                            ArrayList<Reserva> reservas = estacion.getReservasActualesAAntiguas();
                            MisReservasAdapter misreservasAdapter;
                            RecyclerView mMainList;


                            if(estacion != null){
                                if(reservas != null) {
                                    misreservasAdapter = new MisReservasAdapter(ListMisReservasEstacion.this, reservas);
                                    mMainList = (RecyclerView) findViewById(R.id.idListViewEstacionReservas);
                                    mMainList.setHasFixedSize(true);
                                    mMainList.setLayoutManager(new LinearLayoutManager(ListMisReservasEstacion.this));
                                    mMainList.setAdapter(misreservasAdapter);

                                    misreservasAdapter.notifyDataSetChanged();
                                }else{
                                    Toast.makeText(ListMisReservasEstacion.this,"No tiene ninguna reserva.", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });
                }
            }
        });
    }
}