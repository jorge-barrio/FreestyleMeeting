package com.example.freestylemeeting;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

        Pista pista = getIntent().getParcelableExtra("pista");

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

        switch (pista.getDificultad()){
            case "Verde":
                dificultadText.setTextColor(Color.rgb(76,175,80));
                break;
            case "Azul":
                dificultadText.setTextColor(Color.rgb(23,137,255));
                break;
            case "Rojo":
                dificultadText.setTextColor(Color.rgb(223,0,0));
                break;
            case "Negro":
                dificultadText.setTextColor(Color.rgb(0,0,0));
                break;
        }
        dificultadText.setText(pista.getDificultad());

        System.out.println("ARRAY:" + pista.getUsuariosActivos().size());
        usuariosText.setText("" + pista.getUsuariosActivos().size());
        if (pista.getDisponible()) {
            disponibleText.setText("Abierta");
            disponibleText.setTextColor(Color.rgb(76, 175, 80));
        } else {
            disponibleText.setText("Cerrada");
            disponibleText.setTextColor(Color.RED);
        }
        avisosText.setText(pista.getAvisos());

        ImageButton delete = (ImageButton) findViewById(R.id.deletePistaDescripcion);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                EstacionDao.deletePista(pista);
                                Intent intent = new Intent(PistaActivity.this, ListPistasActivity.class);
                                startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No hacer nada
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(PistaActivity.this);
                builder.setMessage("¿Estas seguro de que quieres borrar la pista?").setPositiveButton("Si", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

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
                    delete.setVisibility(View.VISIBLE);
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
                Toast.makeText(PistaActivity.this, "Pista añadida con éxito!", Toast.LENGTH_SHORT).show();
                back();
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
                back();
            }
        });

    }

    @Override
    public void onBackPressed() {
        back();
    }

    public void back(){
        String cif = null;
        if(UserDao.currentCliente!=null) {
            cif = UserDao.currentCliente.getCurrentEstacion();
        }else if(UserDao.currentEmpleado!=null) {
            cif = UserDao.currentEmpleado.getCifEmpresa();
        }
        if(cif != null){
            EstacionDao.getEstacionesCollection().document(cif).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Estacion estacion = documentSnapshot.toObject(Estacion.class);
                    if (estacion != null) {
                        EstacionDao.currentEstacion = estacion;
                    }
                }
            });
        }
        Intent intent = new Intent(PistaActivity.this, ListPistasActivity.class);
        startActivity(intent);
    }
}