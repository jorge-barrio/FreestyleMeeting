package com.example.freestylemeeting;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.DAO.UserDao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import Modelo.Client;
import Modelo.Grupo;

public class CrearGruposActivity extends AppCompatActivity {


    private String[] actividades = new String[] {"Esquí", "Snowboard", "Otros"};
    private String[] niveles = new String[] {"Principiante", "Intermedio", "Avanzado"};
    private Client cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupos);

        UserDao.getUsersCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                cliente = documentSnapshot.toObject(Client.class);
            }
        });

        Spinner spinnerActividad = (Spinner)findViewById(R.id.spinnerActividad);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CrearGruposActivity.this,
                android.R.layout.simple_spinner_item, actividades);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActividad.setAdapter(adapter);

        Spinner spinnerNivel= (Spinner)findViewById(R.id.spinnerNivel);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(CrearGruposActivity.this,
                android.R.layout.simple_spinner_item, niveles);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNivel.setAdapter(adapter2);

        Button botonAtras = (Button) findViewById(R.id.botonAtrasCrearGrupos);
        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CrearGruposActivity.this,GroupActivity.class));
            }
        });

        EditText editTextFecha = (EditText) findViewById(R.id.editTextFecha);
        editTextFecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                muestraVentanaCalendario();
            }
        });

        EditText editTextHora = (EditText) findViewById(R.id.editTextHora);
        editTextHora.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                muestraVentanaHora();
            }
        });

        Button botonCrear = (Button) findViewById(R.id.botonCrearGrupo);
        botonCrear.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                EditText nombreEditText = (EditText) findViewById(R.id.editTextNombreGrupo);
                Spinner spinnerActividad = (Spinner)findViewById(R.id.spinnerActividad);
                Spinner spinnerNivel = (Spinner)findViewById(R.id.spinnerNivel);
                EditText participantesEditText = (EditText) findViewById(R.id.editTextParticipantes);
                EditText lugarEditText = (EditText) findViewById(R.id.editTextLugar);
                EditText fechaEditText = (EditText) findViewById(R.id.editTextFecha);
                EditText horaEditText = (EditText) findViewById(R.id.editTextHora);

                if (nombreEditText.getText().toString().matches("") || participantesEditText.getText().toString().matches("") || lugarEditText.getText().toString().matches("") || fechaEditText.getText().toString().matches("") || horaEditText.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Faltan campos por rellenar", Toast.LENGTH_SHORT ).show();
                }else{

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    LocalDateTime fechaSeleccionada=null;

                    fechaSeleccionada = LocalDateTime.parse(fechaEditText.getText().toString()+" "+horaEditText.getText().toString(), formatter);


                    LocalDateTime now = LocalDateTime.now();

                    if(now.isAfter(fechaSeleccionada)){
                        Toast.makeText(getApplicationContext(), "Fecha y hora introducidas son inválidas", Toast.LENGTH_SHORT ).show();

                    }else {

                        int participantes = Integer.parseInt(participantesEditText.getText().toString());

                        Grupo grupo = new Grupo(nombreEditText.getText().toString(), spinnerActividad.getSelectedItem().toString(), participantes, cliente.getEmail(),
                                spinnerNivel.getSelectedItem().toString(), fechaEditText.getText().toString(), horaEditText.getText().toString(), lugarEditText.getText().toString());

                        EstacionDao.addGrupo(grupo);

                        Toast.makeText(getApplicationContext(), "Grupo Creado", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CrearGruposActivity.this, NavegationDrawerActivity.class));
                    }
                }
            }
        });
    }



    public void muestraVentanaCalendario(){
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = new DecimalFormat("00").format(day) + "/" + new DecimalFormat("00").format((month+1)) + "/" + year;
                EditText editTextFecha = (EditText) findViewById(R.id.editTextFecha);;
                editTextFecha.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void muestraVentanaHora(){
        TimePickerFragment newFragment = TimePickerFragment.newInstance(new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                // +1 because january is zero
                final String selectedTime = new DecimalFormat("00").format(hour)+ ":" + new DecimalFormat("00").format(minute);
                EditText editTextHora = (EditText) findViewById(R.id.editTextHora);;
                editTextHora.setText(selectedTime);
            }
        });
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

}