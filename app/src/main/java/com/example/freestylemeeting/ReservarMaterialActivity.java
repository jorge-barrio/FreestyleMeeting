package com.example.freestylemeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.DAO.UserDao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

import Modelo.Client;
import Modelo.Estacion;
import Modelo.PackReserva;

public class ReservarMaterialActivity extends AppCompatActivity {
    ArrayList<PackReserva> packsReserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_material);

        getPacksEstacion();

        EditText fechaRecogidaEditText = (EditText) findViewById(R.id.fechaRecogidaEditText);
        fechaRecogidaEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                        muestraVentanaCalendario();
                }
            });

        Button atrasReservarMaterialButton = (Button) findViewById(R.id.atrasReservarMaterialButton);
        atrasReservarMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReservarMaterialActivity.this,HomeFragment.class));
            }
        });
        }

    private void muestraVentanaCalendario() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                EditText fechaRecogidaEditText = (EditText) findViewById(R.id.fechaRecogidaEditText);;
                fechaRecogidaEditText.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void getPacksEstacion(){
        UserDao.getUsersCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Client cliente = documentSnapshot.toObject(Client.class);
                String cifEstacion = cliente.getCurrentEstacion();
                EstacionDao.getEstacionesCollection().document(cifEstacion).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Estacion estacion = documentSnapshot.toObject(Estacion.class);
                        ArrayList<PackReserva> packs = estacion.getPacksReserva();
                        muestraPacks(packs);
                    }
                });
            }
        });

    }

    private void muestraPacks(ArrayList<PackReserva> packs){
        packsReserva=packs;
        String[] nombres = new String[packs.size()];
        for(int i =0; i<packs.size(); i++){
            nombres[i] = packs.get(i).getDescripcion();
        }
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReservarMaterialActivity.this,
                android.R.layout.simple_spinner_item, nombres);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
}

