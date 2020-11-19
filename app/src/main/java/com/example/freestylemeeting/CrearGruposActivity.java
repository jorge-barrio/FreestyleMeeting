package com.example.freestylemeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CrearGruposActivity extends AppCompatActivity {

    private Spinner spinner1, spinner2;
    private String[] actividades = new String[] {"EsquÃ­", "Snowboard", "Otros"};
    private String[] niveles = new String[] {"Principiante", "Intermedio", "Avanzado"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupos);

        spinner1 = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CrearGruposActivity.this,
                android.R.layout.simple_spinner_item, actividades);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        spinner2= (Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(CrearGruposActivity.this,
                android.R.layout.simple_spinner_item, niveles);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
    }
}