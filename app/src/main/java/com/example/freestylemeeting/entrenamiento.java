package com.example.freestylemeeting;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.DAO.UserDao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

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

        Bundle extras = getIntent().getExtras();


        ArrayList<String> pist2 =  extras.getStringArrayList("pistas");
        Log.d("size",String.valueOf(pist2.size()));
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pist2);
        ListView listView = (ListView) findViewById(R.id.listaPistasTraining);
        listView.setAdapter(itemsAdapter);
        ImageButton exitButton = (ImageButton) findViewById(R.id.exitPistasEntrenamiento);

        exitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(entrenamiento.this, userTrainings.class);
                startActivity(intent);
            }
        });


    }
}