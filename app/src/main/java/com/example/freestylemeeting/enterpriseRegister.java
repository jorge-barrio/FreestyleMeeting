package com.example.freestylemeeting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.freestylemeeting.DAO.UserDao;
import com.example.freestylemeeting.DAO.myCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import Modelo.User;
import Modelo.UserEstacion;

public class enterpriseRegister extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextCif;
    private EditText editTextPassword;
    private Button registerButton;
    FirebaseAuth myAuth;
    FirebaseFirestore myDatabase;

    //Variables de datos a registrar
    private String name = "";
    private String email = "";
    private String cifEmpresa = "";
    private String password = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise_register);
        myAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseFirestore.getInstance();
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextCif = (EditText) findViewById(R.id.editTextNombreCompleto);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        enterpriseRegister.this.setTitle("Registro para empresas");
        registerButton = (Button) findViewById(R.id.registrarse);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editTextName.getText().toString();
                email = editTextEmail.getText().toString();
                cifEmpresa = editTextCif.getText().toString();
                password = editTextPassword.getText().toString();
                UserEstacion estacion = new UserEstacion(name,email,password,cifEmpresa);
                if(!name.isEmpty() && !email.isEmpty() && !cifEmpresa.isEmpty() && !password.isEmpty()){
                    if(password.length()>=6){
                        registerEmpresa(estacion);
                    }else{
                        Toast.makeText(enterpriseRegister.this, "El password ha de tener 6 o más caracteres",Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(enterpriseRegister.this, "Se deben completar todos los campos",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void registerEmpresa(UserEstacion estacion){
        UserDao.registerEnterprise(estacion,new myCallback(){
            @Override
            public void onCallback(boolean status) {
                if (status){
                    startActivity(new Intent(enterpriseRegister.this,authActivity.class));
                    finish();
                }else{
                    Toast.makeText(enterpriseRegister.this, "El formato del email no es el adecuado", Toast.LENGTH_SHORT).show();
                }

            }

        });







    }
}