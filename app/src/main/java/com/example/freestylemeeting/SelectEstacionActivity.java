package com.example.freestylemeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.freestylemeeting.DAO.EstacionDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Modelo.Estacion;

public class SelectEstacionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_estacion);

        Estacion[] estaciones = EstacionDao.getEstaciones();

        ListView listView = findViewById(R.id.idListViewEstaciones);

        ArrayList<String> estacionesNames = new ArrayList<String>();

        for (Estacion estacion : estaciones) {
            estacionesNames.add(estacion.getNombre());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, estacionesNames);

        listView.setAdapter(adapter);

    }
}