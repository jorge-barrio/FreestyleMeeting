package com.example.freestylemeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class TrainingActivity extends AppCompatActivity {

    FirebaseAuth myAuth;
    FirebaseFirestore myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseFirestore.getInstance();
        Spinner spinnerModalidad = (Spinner) findViewById(R.id.modalidad_spinner);
        Spinner spinnerNivel = (Spinner) findViewById(R.id.nivel_spinner);
        setContentView(R.layout.activity_training);
        ImageButton registerButton = (ImageButton) findViewById(R.id.finalizarInicioEntrButton);
        String[] opcionesModalidad = {"esqui","snowboard","travesia"};
        ArrayAdapter<String> adaptadorModalidad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opcionesModalidad);
        spinnerModalidad.setAdapter(adaptadorModalidad);
        String[] opcionesNivel = {"principiante","intermedio","avanzado","experto"};
        ArrayAdapter<String> adaptadorNivel = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opcionesNivel);
        spinnerNivel.setAdapter(adaptadorNivel);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String modalidad = spinnerModalidad.getSelectedItem().toString();
                String nivel = spinnerNivel.getSelectedItem().toString();
                Log.d("mod", modalidad);
                Log.d("niv", nivel);

            }
        });
    }
}