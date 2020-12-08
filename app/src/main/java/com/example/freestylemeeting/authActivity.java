package com.example.freestylemeeting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freestylemeeting.DAO.UserDao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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


        if(UserDao.loginUser(email,password)!=null){
            startActivity(new Intent(authActivity.this, MyHome.class));
            finish();
        }else{
            Toast.makeText(authActivity.this, "No se pudo iniciar sesión", Toast.LENGTH_SHORT).show();
        }

       /* myAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    /*HomeFragment myFragment = HomeFragment.newInstance("10","10");
                    // R.id.container - the id of a view that will hold your fragment; usually a FrameLayout
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, myFragment, HomeFragment.TAG)
                            .commit();*/

                    /*startActivity(new Intent(authActivity.this, MyHome.class));
                    finish();
                }else{
                    Toast.makeText(authActivity.this, "No se pudo iniciar sesión", Toast.LENGTH_SHORT).show();

                }
            }
        });

*/
    }
    @Override
    protected void onStart() {

        super.onStart();
        if(myAuth.getCurrentUser() !=null){
            startActivity(new Intent(authActivity.this, HomeActivity.class));
            finish();
        }
    }
}