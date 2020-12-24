package com.example.freestylemeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.DAO.UserDao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import Modelo.Client;
import Modelo.Estacion;

public class TrainingActivity extends AppCompatActivity {

    FirebaseAuth myAuth;
    FirebaseFirestore myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        myAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseFirestore.getInstance();
        Spinner spinnerModalidad = (Spinner) findViewById(R.id.modalidad_spinner);
        Spinner spinnerNivel = (Spinner) findViewById(R.id.nivel_spinner);
        TrainingActivity.this.setTitle("Iniciar Entrenamiento");

        ImageButton createTraining = (ImageButton) findViewById(R.id.finalizarInicioEntrButton);
        String[] opcionesModalidad = {"Esqui","Snowboard","Travesia"};
        ArrayAdapter<String> adaptadorModalidad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opcionesModalidad);
        spinnerModalidad.setAdapter(adaptadorModalidad);
        String[] opcionesNivel = {"Principiante","Intermedio","Avanzado","Experto"};
        ArrayAdapter<String> adaptadorNivel = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opcionesNivel);
        spinnerNivel.setAdapter(adaptadorNivel);
        createTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserDao.getUsersCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        Client cliente = documentSnapshot.toObject(Client.class);
                        String modalidad = spinnerModalidad.getSelectedItem().toString();
                        String nivel = spinnerNivel.getSelectedItem().toString();
                        Log.d("cliente", String.valueOf(cliente));
                        String cifEstacion = cliente.getCurrentEstacion();
                        UserDao.createTraining(modalidad,nivel,cifEstacion);
                        Toast.makeText(TrainingActivity.this,"Su entrenamiento ha sido creado con exito", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(TrainingActivity.this, ListPistasActivity.class));
                        }

                });
            }
        });
    }
}