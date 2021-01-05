package com.example.freestylemeeting;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.freestylemeeting.DAO.UserDao;
import com.example.freestylemeeting.DAO.myCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import Modelo.UserEstacion;

public class enterpriseRegister extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextCif;
    private EditText editTextTelefono;
    private EditText editTextMensaje;
    private Button registerButton;
    FirebaseAuth myAuth;
    FirebaseFirestore myDatabase;
    ProgressDialog progress;

    //Variables de datos a registrar
    private String name = "";
    private String email = "";
    private String cifEmpresa = "";
    private String telefono = "";
    private String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise_register);
        myAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseFirestore.getInstance();
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextCif = (EditText) findViewById(R.id.editTextCif);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextTelefono = (EditText) findViewById(R.id.editTextTelefono);
        editTextMensaje = (EditText) findViewById(R.id.editTextMensaje);

        enterpriseRegister.this.setTitle("Solicitud de Registros para empresas");
        registerButton = (Button) findViewById(R.id.enviarSolicitud);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                name = editTextName.getText().toString();
                cifEmpresa = editTextCif.getText().toString();
                email = editTextEmail.getText().toString().trim();
                telefono = editTextTelefono.getText().toString();
                message = editTextMensaje.getText().toString();

                message = message + "\n\n" + "CIF Empresa: " + cifEmpresa + "\nEmail: " + email + "\nTelefono de Contacto:" + telefono;




                boolean internet = isOnline();
                if(internet == true){
                        sendMail(name,message);
                }else{
                        errorInternet();
                }

                //password = editTextPassword.getText().toString();
                /*UserEstacion estacion = new UserEstacion(name,email,password,cifEmpresa);
                if(!name.isEmpty() && !email.isEmpty() && !cifEmpresa.isEmpty() && !password.isEmpty()){
                    if(password.length()>=6){
                        registerEmpresa(estacion);
                    }else{
                        Toast.makeText(enterpriseRegister.this, "El password ha de tener 6 o más caracteres",Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(enterpriseRegister.this, "Se deben completar todos los campos",Toast.LENGTH_SHORT).show();
                }*/

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

    private void errorInternet(){
        progress = ProgressDialog.show(this, "Por favor, conectese a Internet", "Por favor, conectese a Internet", true);
    }

    private void sendMail(String name, String message) {
        JavaMailAPI javaMailAPI = new JavaMailAPI(this, name, message );
        javaMailAPI.execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

}