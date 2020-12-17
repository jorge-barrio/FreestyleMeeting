package com.example.freestylemeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.DAO.UserDao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Modelo.Client;
import Modelo.Estacion;
import Modelo.PackReserva;
import Modelo.Reserva;

public class ReservarMaterialActivity extends AppCompatActivity {
    ArrayList<PackReserva> packsReserva;
    Estacion estacion;
    Client cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_material);

        getPacksEstacion();

        EditText fechaRecogidaEditText = (EditText) findViewById(R.id.fechaRecogidaEditText);
        fechaRecogidaEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                        muestraVentanaCalendario();
                }
            });

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                EditText duracionEditText = (EditText) findViewById(R.id.duracionEditText);
                TextView precioTotal = (TextView) findViewById(R.id.totalPrecioTextView);
                if(!duracionEditText.getText().toString().matches("")){
                    precioTotal.setText("Total: "+packsReserva.get(spinner.getSelectedItemPosition()).getPrecio() * Integer.parseInt(duracionEditText.getText().toString()));
                }else{
                    precioTotal.setText("Total: -");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        EditText duracionEditText = (EditText) findViewById(R.id.duracionEditText);
        duracionEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView precioTotal = (TextView) findViewById(R.id.totalPrecioTextView);
                if(!duracionEditText.getText().toString().matches("")){
                    Spinner spinner = (Spinner)findViewById(R.id.spinner);
                    precioTotal.setText("Total: "+packsReserva.get(spinner.getSelectedItemPosition()).getPrecio() * Integer.parseInt(duracionEditText.getText().toString()));
                }else{
                    precioTotal.setText("Total: -");
                }
            }
        });

        Button atrasReservarMaterialButton = (Button) findViewById(R.id.atrasReservarMaterialButton);
        atrasReservarMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReservarMaterialActivity.this,NavegationDrawerActivity.class));
            }
        });

        Button reservarButton = (Button) findViewById(R.id.reservarButton);
        reservarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("--------------------entrabien: ");
                Spinner spinner = (Spinner)findViewById(R.id.spinner);
                EditText pesoEditText = (EditText) findViewById(R.id.pesoEditText);
                EditText alturaEditText = (EditText) findViewById(R.id.alturaEditText);
                EditText fechaEditText = (EditText) findViewById(R.id.fechaRecogidaEditText);
                EditText duracionEditText = (EditText) findViewById(R.id.duracionEditText);

                if (pesoEditText.getText().toString().matches("") || alturaEditText.getText().toString().matches("") || duracionEditText.getText().toString().matches("") || fechaEditText.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Faltan campos por rellenar", Toast.LENGTH_SHORT ).show();
                }else{

                    int indicePack = spinner.getSelectedItemPosition();
                    int peso = Integer.parseInt(pesoEditText.getText().toString());
                    int altura = Integer.parseInt(alturaEditText.getText().toString());
                    int duracion = Integer.parseInt(duracionEditText.getText().toString());

                    Reserva reserva = new Reserva();
                    reserva.setAltura(altura);
                    reserva.setDuracion(duracion);
                    reserva.setIdEstacion(estacion.getCif());
                    reserva.setIdPack(packsReserva.get(indicePack).getId());
                    reserva.setPeso(peso);
                    reserva.setPrecioEuros(packsReserva.get(indicePack).getPrecio() * duracion);
                    Date dateRecogida= null;
                    try {
                        dateRecogida = new SimpleDateFormat("dd / MM / yyyy").parse(fechaEditText.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    reserva.setFechaRecogida(dateRecogida);
                    reserva.setIdReserva(cliente.getEmail()+"-"+reserva.getFechaRecogida());

                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{ estacion.getEmailTienda() });
                    email.putExtra(Intent.EXTRA_SUBJECT, "Nueva Reserva");
                    email.putExtra(Intent.EXTRA_TEXT, "Registrada nueva reserva con id: "+reserva.getIdReserva()+"\n Pack: "
                            +reserva.getIdPack()+"\n Peso(kg): "+reserva.getPeso()+"\n Altura(m): "+reserva.getAltura()+"\n Duracion: "
                            +reserva.getDuracion()+"\n Fecha de recogida: "+reserva.getFechaRecogida()+"\n Precio: "+reserva.getPrecioEuros());
//need this to prompts email client only
                    email.setType("message/rfc822");
                    startActivity(Intent.createChooser(email, "Choose an Email client :"));

                    Toast.makeText(getApplicationContext(), "ReservaRealizada", Toast.LENGTH_SHORT ).show();
                    startActivity(new Intent(ReservarMaterialActivity.this,NavegationDrawerActivity.class));
                }
            }
        });
        }

    private void muestraVentanaCalendario() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                EditText fechaRecogidaEditText = (EditText) findViewById(R.id.fechaRecogidaEditText);;
                fechaRecogidaEditText.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void getPacksEstacion(){
        UserDao.getUsersCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Client cliente = documentSnapshot.toObject(Client.class);
                String cifEstacion = cliente.getCurrentEstacion();
                EstacionDao.getEstacionesCollection().document(cifEstacion).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Estacion estacion = documentSnapshot.toObject(Estacion.class);
                        ArrayList<PackReserva> packs = estacion.getPacksReserva();
                        muestraPacks(packs, estacion, cliente);

                    }
                });
            }
        });

    }

    private void muestraPacks(ArrayList<PackReserva> packs, Estacion objEstacion, Client objClient){
        packsReserva=packs;
        estacion = objEstacion;
        cliente = objClient;
        String[] nombres = new String[packs.size()];
        for(int i =0; i<packs.size(); i++){
            nombres[i] = packs.get(i).getDescripcion();
        }
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReservarMaterialActivity.this,
                android.R.layout.simple_spinner_item, nombres);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
}

