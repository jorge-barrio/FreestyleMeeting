package com.example.freestylemeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freestylemeeting.DAO.UserDao;
import com.example.freestylemeeting.DAO.myCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import Modelo.UserEstacion;

public class authActivity extends AppCompatActivity {
    private EditText editTextPassword;
    private EditText editTextEmail;
    private String password="";
    private String email="";
    FirebaseAuth myAuth;
    FirebaseAuth.AuthStateListener myAuthListener;

    public static final int REQUEST_CODE = 54654;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextEmail = (EditText) findViewById(R.id.email);


        myAuth = FirebaseAuth.getInstance();
        Button registerButton = (Button) findViewById(R.id.registro);
        Button loginButton = (Button) findViewById(R.id.logOut);
        TextView enterpriseRegister = (TextView) findViewById(R.id.enterpriseRegister);
        authActivity.this.setTitle("Inicio de Sesión");
        enterpriseRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(authActivity.this, enterpriseRegister.class));
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(authActivity.this, RegisterActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                if(!email.isEmpty() && !password.isEmpty()){
                    loginUser(email,password);
                }else{
                    Toast.makeText(authActivity.this,"Complete los campos", Toast.LENGTH_SHORT).show();
                }


            }
        });
        /*myAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    Toast.makeText(authActivity.this,"Sesión iniciada!",Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        };*/

    }

    /*private void setup(){
        String title = "Autenticación";
        final Button boton = (Button) findViewById(R.id.signUp);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.get)
            }
        });
    }*/
    private void loginUser(String email, String password){
        UserDao.loginUser(email,password,new myCallback(){
             @Override
             public void onCallback(boolean status) {
                 if (status){
                     UserDao.getEnterprisesCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                         @Override
                         public void onSuccess(DocumentSnapshot documentSnapshot) {
                             UserEstacion userEstacion = documentSnapshot.toObject(UserEstacion.class);
                             if(userEstacion != null){
                                 startActivity(new Intent(authActivity.this, pistaActivityEnterprise.class));
                                 finish();
                             }else{
                                 startActivity(new Intent(authActivity.this, NavegationDrawerActivity.class));
                                 finish();
                             }
                         }
                     });
                 }else{
                     Toast.makeText(authActivity.this, "El formato del email no es el adecuado", Toast.LENGTH_SHORT).show();
                 }

             }

         });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(UserDao.sesionIniciada()){
            UserDao.getEnterprisesCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    UserEstacion userEstacion = documentSnapshot.toObject(UserEstacion.class);
                    if(userEstacion != null){
                        startActivity(new Intent(authActivity.this, pistaActivityEnterprise.class));
                        finish();
                    }else{
                        startActivity(new Intent(authActivity.this, NavegationDrawerActivity.class));
                        finish();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}