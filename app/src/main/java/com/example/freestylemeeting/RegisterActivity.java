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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import Modelo.Client;
import Modelo.User;


public class RegisterActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextNombreCompleto;
    private EditText editTextPassword;
    private Button registerButton;
    FirebaseAuth myAuth;
    FirebaseFirestore myDatabase;

    //Variables de datos a registrar
    private String name = "";
    private String email = "";
    private String nombreCompleto = "";
    private String password = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseFirestore.getInstance();
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextNombreCompleto = (EditText) findViewById(R.id.editTextNombreCompleto);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        RegisterActivity.this.setTitle("Registro");
        registerButton = (Button) findViewById(R.id.registrarse);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editTextName.getText().toString();
                email = editTextEmail.getText().toString();
                nombreCompleto = editTextNombreCompleto.getText().toString();
                password = editTextPassword.getText().toString();
                Client usuario = new Client(name,email,password,nombreCompleto);
                if(!name.isEmpty() && !email.isEmpty() && !nombreCompleto.isEmpty() && !password.isEmpty()){
                    if(password.length()>=6){
                        registerUser(usuario);
                    }else{
                        Toast.makeText(RegisterActivity.this, "El password ha de tener 6 o más caracteres",Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(RegisterActivity.this, "Se deben completar todos los campos",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void registerUser(Client user){
        if (UserDao.registerUser(user)){
            startActivity(new Intent(RegisterActivity.this,authActivity.class));
            finish();
        }else{
            Toast.makeText(RegisterActivity.this, "El formato del email no es el adecuado", Toast.LENGTH_SHORT).show();
        }
    }
}