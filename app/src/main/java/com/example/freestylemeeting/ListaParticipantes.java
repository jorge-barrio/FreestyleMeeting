package com.example.freestylemeeting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListaParticipantes extends AppCompatActivity {

    ArrayList<String> miembros;
    TextView cadenaCorreos;

    public ListaParticipantes() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_participantes);
        miembros = getIntent().getStringArrayListExtra("participantes");

        cadenaCorreos = (TextView) findViewById(R.id.textViewCorreos);
        String cadenaNueva = "";
        for (int i = 0; i < miembros.size(); i++) {
            cadenaNueva = cadenaNueva.concat(i + ":   " + miembros.get(i) + "\n");
        }
        cadenaCorreos.append(cadenaNueva);

            Button botonAtras = (Button) findViewById(R.id.botonAtrasParticipantes);
            botonAtras.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ListaParticipantes.this, MisGruposFragment.class));
                }
            });

    }
}


