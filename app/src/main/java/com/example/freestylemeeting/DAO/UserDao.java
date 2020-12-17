package com.example.freestylemeeting.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Modelo.Client;
import Modelo.UserEstacion;

public class UserDao {

    static FirebaseAuth myAuth;
    static FirebaseFirestore myDatabase;
    static boolean status;

    /**
     * Logear Usuario
     *
     * @param email
     * @param password
     * @param callback
     */
    public static void loginUser(String email, String password, myCallback callback){
        myAuth = FirebaseAuth.getInstance();
        myAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    callback.onCallback(true);
                }else{
                    callback.onCallback(false);
                }
            }
        });

    }

    /**
     * Registrar usuario
     *
     * @param user
     * @param callback
     */
    public static void registerUser(Client user, myCallback callback){
        myAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseFirestore.getInstance();
        Log.d("ANTES","todavia no he entrado" );
        myAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    ArrayList <Object> entrenamientos = new ArrayList<Object>();
                    ArrayList <Object> reservas = new ArrayList<Object>();

                    Map<String,Object> map = new HashMap<>();
                    map.put("name",user.getName());
                    map.put("email",user.getEmail());
                    map.put("password",user.getPassword());
                    map.put("entrenamientos",entrenamientos);
                    map.put("reservas",reservas);
                    map.put("currentEstacion","");
                    Log.d("entrada dao","HE ENTRADO EN EL DAO CON EXITO" );
                    callback.onCallback(true);
                    String id = myAuth.getCurrentUser().getUid();
                    myDatabase.collection("users").document(id).set(map);
                }else{
                    callback.onCallback(false);
                }
            }
        });

    }

    /**
     *
     * @param estacion
     * @param callback
     */
    public static void registerEnterprise(UserEstacion estacion, myCallback callback){
        status = false;
        myAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseFirestore.getInstance();

        myAuth.createUserWithEmailAndPassword(estacion.getEmail(),estacion.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    System.out.println("He entrado");
                    Map<String,Object> map = new HashMap<>();
                    map.put("name",estacion.getName());
                    map.put("CIF",estacion.getCifEmpresa());
                    map.put("email",estacion.getEmail());
                    map.put("password",estacion.getPassword());

                    String id = myAuth.getCurrentUser().getUid();
                    myDatabase.collection("enterprises").document(id).set(map);
                    callback.onCallback(true);
                }else{
                    callback.onCallback(false);
                }
            }
        });

    }

    public static FirebaseUser getCurrentUser(){
        myAuth = FirebaseAuth.getInstance();
        return myAuth.getCurrentUser();
    }

    public static boolean sesionIniciada(){
        myAuth =FirebaseAuth.getInstance();
        FirebaseUser usuario = myAuth.getCurrentUser();
        if(usuario == null){
            return false;
        }else{
            return true;
        }
    }

    public static void editCurrentEstacion(String cifEstacion){
        Map<String,Object> map = new HashMap<>();
        map.put("currentEstacion",cifEstacion);
        getUsersCollection().document(getCurrentUser().getUid()).update(map);
    }

    public static void signOut() {
        myAuth = FirebaseAuth.getInstance();
        myAuth.signOut();
    }

    public static CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection("users");
    }
    public static CollectionReference getEnterprisesCollection(){
        return FirebaseFirestore.getInstance().collection("enterprises");
    }
    public static void getEntrenamientos(String cifEstacion){
        myAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseFirestore.getInstance();
        FirebaseUser userLogged = myAuth.getCurrentUser();
        myDatabase.collection("users").document(userLogged.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                List<Map<String,Object>> entrenamientos = (List<Map<String,Object>>) document.get("entrenamientos");//Lista de entrenamientos

            }
        });

    }
    public static void createTraining(String modalidad, String nivel,String cifEstacion) {
        myAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseFirestore.getInstance();
        FirebaseUser userLogged = myAuth.getCurrentUser();
        myDatabase.collection("users").document(userLogged.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                List<Map<String,Object>> entrenamientos = (List<Map<String,Object>>) document.get("entrenamientos");//Se ha probado a imprimirlo y se imprime bien
                Map<String,Object> nuevoEntrenamiento = new HashMap<String,Object>();
                Date date = new Date();
                String id = String.valueOf(entrenamientos.size()+1);
                List<String> idPistas = new ArrayList<String>();

                nuevoEntrenamiento.put("cifEstacion",cifEstacion);
                nuevoEntrenamiento.put("fechaInicio",date);
                nuevoEntrenamiento.put("id",id);
                nuevoEntrenamiento.put("idPistas",idPistas);
                entrenamientos.add(nuevoEntrenamiento);

                myDatabase.collection("users").document(userLogged.getUid()).update("entrenamientos", entrenamientos).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d("update","todo ha ido bien");
                        }else{
                            Log.d("update","todo ha ido mal");
                        }
                    }
                });
            }
        });

    }
}
