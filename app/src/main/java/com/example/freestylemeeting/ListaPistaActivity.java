package com.example.freestylemeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListaPistaActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pista);



        Button nombrePista1button = (Button)findViewById(R.id.nombrePista1Button);

        nombrePista1button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                startActivity(new Intent(ListaPistaActivity.this, PistaActivity.class));
            }
        });
    }
}