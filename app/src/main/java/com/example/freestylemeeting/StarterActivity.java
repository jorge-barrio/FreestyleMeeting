package com.example.freestylemeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;

import com.example.freestylemeeting.DAO.UserDao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import Modelo.UserEstacion;

public class StarterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_starter);
        if(UserDao.sesionIniciada()){
            startActivity(new Intent(StarterActivity.this, NavegationDrawerActivity.class));
            finish();
        } else {
            startActivity(new Intent(StarterActivity.this, authActivity.class));
            finish();
        }
    }
}