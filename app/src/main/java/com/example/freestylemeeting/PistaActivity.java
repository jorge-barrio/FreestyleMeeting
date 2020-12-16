package com.example.freestylemeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.freestylemeeting.DAO.UserDao;

import Modelo.Pista;

public class PistaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pista);

        Pista pista= getIntent().getParcelableExtra("pista");

        ImageButton exitButton = (ImageButton) findViewById(R.id.exitPistaDescripcion);

        TextView nombreText = (TextView) findViewById(R.id.text_nombre_PistaDescripcion);
        TextView idText = (TextView) findViewById(R.id.text_id_PistaDescripcion);
        TextView dificultadText = (TextView) findViewById(R.id.text_dificultad_PistaDescripcion);
        TextView usuariosText = (TextView) findViewById(R.id.text_usuarios_PistaDescripcion);

        nombreText.setText(pista.getNombre());
        idText.setText(pista.getId());
        dificultadText.setText(pista.getDificultad());
        usuariosText.setText("To-Do");

        exitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(PistaActivity.this, ListPistasActivity.class);
                startActivity(intent);
            }
        });

    }
}