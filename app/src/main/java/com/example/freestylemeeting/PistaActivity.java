package com.example.freestylemeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.DAO.UserDao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;

import Modelo.Client;
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
        //TextView idText = (TextView) findViewById(R.id.text_id_PistaDescripcion);
        TextView dificultadText = (TextView) findViewById(R.id.text_dificultad_PistaDescripcion);
        TextView usuariosText = (TextView) findViewById(R.id.text_usuarios_activos_PistaDescripcion);
        TextView disponibleText = (TextView) findViewById(R.id.text_disponible_PistaDescripcion);
        TextView avisosText = (TextView) findViewById(R.id.text_avisos_PistaDescripcion);
        FloatingActionButton editPistaButton = findViewById(R.id.edit_pista_button);
        FloatingActionButton addPistaToTraining = findViewById(R.id.add_pista_training_button);
        nombreText.setText(pista.getNombre());
        //idText.setText(pista.getId());
        dificultadText.setText(pista.getDificultad());
        System.out.println("ARRAY:"+pista.getUsuariosActivos().size());
        usuariosText.setText(""+pista.getUsuariosActivos().size());
        if(pista.getDisponible()){
            disponibleText.setText("Abierta");
            disponibleText.setTextColor(Color.rgb(76,175,80));
        } else {
            disponibleText.setText("Cerrada");
            disponibleText.setTextColor(Color.RED);
        }
        System.out.println("AVISOS: "+pista.getAvisos());
        avisosText.setText(pista.getAvisos());

        UserDao.getEnterprisesCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserEstacion trabajador = documentSnapshot.toObject(UserEstacion.class);
                Client cliente = documentSnapshot.toObject(Client.class);
                Log.d("client", String.valueOf(cliente));
                if(trabajador != null) {
                    //Activamos la visibilidad del floating button
                    addPistaToTraining.setClickable(false);
                    editPistaButton.setVisibility(View.VISIBLE);
                } else if(cliente != null){
                    Log.d("entrenamiento activo", String.valueOf(cliente.isEntrenamientoActivo()));
                    if(cliente.isEntrenamientoActivo()){
                        editPistaButton.setClickable(false);
                        addPistaToTraining.setVisibility(View.VISIBLE);

                    }
                }
            }
        });
        UserDao.getUsersCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Client cliente = documentSnapshot.toObject(Client.class);
                Log.d("client", String.valueOf(cliente));
                if(cliente != null){
                    Log.d("entrenamiento activo", String.valueOf(cliente.isEntrenamientoActivo()));
                    if(cliente.isEntrenamientoActivo()){
                        editPistaButton.setClickable(false);
                        addPistaToTraining.setVisibility(View.VISIBLE);

                    }
                }
            }
        });
        addPistaToTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDao.addPistaToTraining(pista.getNombre());
                Intent intent = new Intent(PistaActivity.this, ListPistasActivity.class);
                startActivity(intent);

            }
        });
        editPistaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PistaActivity.this, EditPistaActivity.class);
                intent.putExtra("pista", pista);
                startActivity(intent);
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