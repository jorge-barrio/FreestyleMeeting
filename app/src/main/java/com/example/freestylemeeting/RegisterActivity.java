package com.example.freestylemeeting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
                User usuario = new User(name,nombreCompleto,email,password);
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
    private void registerUser(User user){
        myAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    System.out.println("He entrado");
                    Map<String,Object> map = new HashMap<>();
                    map.put("nombre",user.getName());
                    map.put("nombreCompleto",user.getNombreCompleto());
                    map.put("email",user.getEmail());
                    map.put("password",user.getPassword());

                    String id = myAuth.getCurrentUser().getUid();
                    myDatabase.collection("users").document(id).set(map);
                    startActivity(new Intent(RegisterActivity.this,authActivity.class));
                    finish();

                /*add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });*/
                }else{
                    Toast.makeText(RegisterActivity.this, "El formato del email no es el adecuado", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}