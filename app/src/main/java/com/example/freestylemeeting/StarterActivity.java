package com.example.freestylemeeting;

import androidx.appcompat.app.AppCompatActivity;

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
        setContentView(R.layout.activity_starter);
        if(UserDao.sesionIniciada()){
            UserDao.getEnterprisesCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    UserEstacion userEstacion = documentSnapshot.toObject(UserEstacion.class);
                    if(userEstacion != null){
                        startActivity(new Intent(StarterActivity.this, pistaActivityEnterprise.class));
                        finish();
                    }else{
                        startActivity(new Intent(StarterActivity.this, NavegationDrawerActivity.class));
                        finish();
                    }
                }
            });
        } else {
            startActivity(new Intent(StarterActivity.this, authActivity.class));
            finish();
        }
    }
}