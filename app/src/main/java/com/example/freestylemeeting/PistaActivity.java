package com.example.freestylemeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.DAO.UserDao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;

import Modelo.Estacion;
import Modelo.Pista;
import Modelo.UserEstacion;

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
        FloatingActionButton editPistaButton = findViewById(R.id.edit_pista_button);

        nombreText.setText(pista.getNombre());
        idText.setText(pista.getId());
        dificultadText.setText(pista.getDificultad());
        usuariosText.setText("To-Do");

        UserDao.getEnterprisesCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserEstacion trabajador = documentSnapshot.toObject(UserEstacion.class);
                if(trabajador != null) {
                    //Activamos la visibilidad del floating button
                    editPistaButton.setVisibility(View.VISIBLE);
                }
            }
        });

        editPistaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PistaActivity.this, "Funcionalidad en construccion...", Toast.LENGTH_SHORT).show();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(PistaActivity.this, ListPistasActivity.class);
                startActivity(intent);
            }
        });

    }
}