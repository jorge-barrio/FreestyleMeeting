package com.example.freestylemeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freestylemeeting.DAO.EstacionDao;

import Modelo.Pista;

public class CreatePistaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pista);

        EditText nombrePista = (EditText) findViewById(R.id.editTextNombrePista);
        Spinner spinner = (Spinner)findViewById(R.id.spinner_crearPista);
        Button cancelButton = (Button) findViewById(R.id.boton_cancelar_create);
        ImageButton exitButton = (ImageButton) findViewById(R.id.exitPistaCreate);
        Button confirmButton = (Button) findViewById(R.id.boton_confirmar_create);

        String[] niveles = {"Verde", "Azul", "Rojo", "Negro"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreatePistaActivity.this,
                android.R.layout.simple_spinner_item, niveles);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(nombrePista.equals("")){
                    Toast.makeText(CreatePistaActivity.this, "Rellene el campo nombre", Toast.LENGTH_SHORT).show();
                } else {
                    Pista pista = new Pista(nombrePista.getText().toString(), spinner.getSelectedItem().toString());
                    EstacionDao.addPista(pista);
                    Intent intent = new Intent(CreatePistaActivity.this, ListPistasActivity.class);
                    startActivity(intent);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(CreatePistaActivity.this, ListPistasActivity.class);
                startActivity(intent);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(CreatePistaActivity.this, ListPistasActivity.class);
                startActivity(intent);
            }
        });
    }
}