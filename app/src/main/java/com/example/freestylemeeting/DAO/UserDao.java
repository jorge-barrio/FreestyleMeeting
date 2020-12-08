package com.example.freestylemeeting.DAO;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.freestylemeeting.RegisterActivity;
import com.example.freestylemeeting.authActivity;
import com.example.freestylemeeting.enterpriseRegister;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

import Modelo.Client;
import Modelo.UserEstacion;

public class UserDao {
    static FirebaseAuth myAuth;
    static FirebaseFirestore myDatabase;
    static boolean status;

    public static FirebaseUser loginUser(String email, String password){
        myAuth = FirebaseAuth.getInstance();
        myAuth.signInWithEmailAndPassword(email,password);
        return myAuth.getCurrentUser();
    }
    public static boolean registerUser(Client user){
        status = false;
        myAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseFirestore.getInstance();
        myAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String,Object> map = new HashMap<>();
                    map.put("nombre",user.getName());
                    map.put("nombreCompleto",user.getNombreCompleto());
                    map.put("email",user.getEmail());
                    map.put("password",user.getPassword());

                    String id = myAuth.getCurrentUser().getUid();
                    myDatabase.collection("users").document(id).set(map);
                    status = true;
                }
            }
        });
        return status;

    }
    public static boolean registerEnterprise(UserEstacion estacion){
        status = false;
        myAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseFirestore.getInstance();
        myAuth.createUserWithEmailAndPassword(estacion.getEmail(),estacion.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    System.out.println("He entrado");
                    Map<String,Object> map = new HashMap<>();
                    map.put("nombre",estacion.getName());
                    map.put("CIF",estacion.getCifEmpresa());
                    map.put("email",estacion.getEmail());
                    map.put("password",estacion.getPassword());

                    String id = myAuth.getCurrentUser().getUid();
                    myDatabase.collection("enterprises").document(id).set(map);
                    status = true;

                }else{

                }
            }
        });
        return status;
    }
    public static FirebaseUser getActualUser(){
        myAuth = FirebaseAuth.getInstance();
        return myAuth.getCurrentUser();
    }
}
