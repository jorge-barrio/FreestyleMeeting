package com.example.freestylemeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class ReservarMaterialActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_material);

        EditText fechaRecogidaEditText = (EditText) findViewById(R.id.fechaRecogidaEditText);
        fechaRecogidaEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                        muestraVentanaCalendario();
                }
            });

        Button atrasReservarMaterialButton = (Button) findViewById(R.id.atrasReservarMaterialButton);
        atrasReservarMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReservarMaterialActivity.this,HomeFragment.class));
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
    }
