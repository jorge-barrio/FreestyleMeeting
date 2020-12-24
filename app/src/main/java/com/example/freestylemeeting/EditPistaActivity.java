package com.example.freestylemeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.freestylemeeting.DAO.EstacionDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;

import Modelo.Pista;

public class EditPistaActivity extends AppCompatActivity {

    String CERRADA = "Cerrada";
    String ABIERTA = "Abierta";
    String VERDE = "Verde";
    String AZUL = "Azul";
    String ROJO = "Rojo";
    String NEGRO = "Negro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pista);

        Pista pista= getIntent().getParcelableExtra("pista");

        ImageButton exitButton = (ImageButton) findViewById(R.id.exitEdit);
        Button cancelButton = (Button) findViewById(R.id.boton_cancelar_edit);
        Button confirmButton = (Button) findViewById(R.id.boton_confirmar_edit);

        EditText nombreText = (EditText) findViewById(R.id.editTextNombrePistaEdit);
        EditText avisosText = (EditText) findViewById(R.id.editTextAvisosPistaEdit);
        //EditText idText = (EditText) findViewById(R.id.editTextIdPistaEdit);
        Spinner spinnerDificultad = (Spinner) findViewById(R.id.spinner_nivel_editPista);
        Spinner spinnerDisponibilidad = (Spinner) findViewById(R.id.spinner_disponibilidad_editPista);

        String[] disponibilidad = {CERRADA, ABIERTA};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditPistaActivity.this,
                android.R.layout.simple_spinner_item, disponibilidad);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDisponibilidad.setAdapter(adapter);

        spinnerDisponibilidad.setSelection(pista.getDisponible() ? 1 : 0);

        String[] niveles = {VERDE, AZUL, ROJO, NEGRO};

        HashMap<String, Integer> map = new HashMap<>();
        map.put(VERDE,0);
        map.put(AZUL, 1);
        map.put(ROJO, 2);
        map.put(NEGRO,3);

        adapter = new ArrayAdapter<String>(EditPistaActivity.this,
                android.R.layout.simple_spinner_item, niveles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDificultad.setAdapter(adapter);
        System.out.println("AQUIIII"+pista.getDificultad());
        System.out.println(map.get(pista.getDificultad()));
        spinnerDificultad.setSelection(map.get(pista.getDificultad()));

        nombreText.setText(pista.getNombre());
        //idText.setText(pista.getId());
        avisosText.setText(pista.getAvisos());

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                pista.setNombre(nombreText.getText().toString());
                pista.setDificultad(spinnerDificultad.getSelectedItem().toString());
                pista.setDisponible(spinnerDisponibilidad.getSelectedItem().toString().equals(ABIERTA) ? true : false);
                pista.setAvisos(avisosText.getText().toString());
                EstacionDao.editPista(pista);
                Intent intent = new Intent(EditPistaActivity.this, ListPistasActivity.class);
                startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(EditPistaActivity.this, ListPistasActivity.class);
                startActivity(intent);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(EditPistaActivity.this, ListPistasActivity.class);
                startActivity(intent);
            }
        });
    }
}