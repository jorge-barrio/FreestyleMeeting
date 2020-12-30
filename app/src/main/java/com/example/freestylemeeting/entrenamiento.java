package com.example.freestylemeeting;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.DAO.UserDao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;

import Modelo.Client;
import Modelo.Entrenamiento;
import Modelo.Estacion;
import Modelo.Pista;
import Modelo.UserEstacion;

public class entrenamiento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamiento);

        Entrenamiento ent = getIntent().getParcelableExtra("entrenamiento");

        ImageButton exitButton = (ImageButton) findViewById(R.id.exitPistaDescripcion);

        TextView nombreText = (TextView) findViewById(R.id.text_nombre_entrenamiento);
        //TextView idText = (TextView) findViewById(R.id.text_id_PistaDescripcion);
        Log.d("entrenamiento",ent.toString());
        TextView fechaInicio = (TextView) findViewById(R.id.fechaInicioDesc);
        TextView fechaFin = (TextView) findViewById(R.id.fechaFindescripcion);
        EstacionDao.getEstacionesCollection().document(ent.getCifEstacion()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Estacion estacion = documentSnapshot.toObject(Estacion.class);
                nombreText.setText(estacion.getNombre());
            }
        });
        Log.d("fIn",ent.getCifEstacion());
        fechaInicio.setText(ent.getCifEstacion());
        //fechaFin.setText(ent.getFechaFin().toString());


        exitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(entrenamiento.this, userTrainings.class);
                startActivity(intent);
            }
        });


    }
}