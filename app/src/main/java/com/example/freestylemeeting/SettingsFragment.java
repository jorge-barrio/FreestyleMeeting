package com.example.freestylemeeting;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.DAO.UserDao;
import com.example.freestylemeeting.DAO.myCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.auth.User;

import Modelo.Client;
import Modelo.Estacion;
import Modelo.UserEstacion;

public class SettingsFragment extends Fragment {


    public static final String TAG = "SettingsFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static EditText nameText;
    private static EditText passwordText;
    private static TextView estacion;

    // Estacion
    TextView estaciontext;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        if(UserDao.sesionIniciada()){

            nameText = v.findViewById(R.id.editTextCambiarNombre);
            passwordText = v.findViewById(R.id.editTextCambiarPassword);

            UserDao.getUsersCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Client cliente = documentSnapshot.toObject(Client.class);

                    if(cliente != null){
                        nameText.setText(cliente.getName());
                    } else {
                        UserDao.getEnterprisesCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                UserEstacion estacion = documentSnapshot.toObject(UserEstacion.class);
                                if(estacion != null){
                                    nameText.setText(estacion.getName());
                                } else {
                                    Toast.makeText(getActivity(),"Error.", Toast.LENGTH_SHORT).show();
                                    System.out.println("ERROR. HomeFragment");
                                }
                            }
                        });
                    }
                }
            });
        } else {
            Intent intent = new Intent(getActivity(), authActivity.class);
            startActivity(intent);
        }

        Button buttonCambiarNombre = (Button) v.findViewById(R.id.buttonCambiarNombre);
        Button buttonCambiarPassword = (Button) v.findViewById(R.id.buttonCambiarPassword);
        Button buttonBorrarCuenta = (Button) v.findViewById(R.id.buttonBorraCuenta);
        Button buttonCambiarEstacion = (Button) v.findViewById(R.id.cambiar_estacion_boton_ajustes);

        buttonCambiarNombre.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                UserDao.changeNombre(nameText.getText() + "", new myCallback() {
                    @Override
                    public void onCallback(boolean status) {
                        if (status){
                            Toast.makeText(getActivity(), "Su nombre se ha cambiado con exito", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(getActivity(), "Error al cambiar nombre", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        buttonCambiarPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                UserDao.changePassword(passwordText.getText()+"", new myCallback() {
                    @Override
                    public void onCallback(boolean status) {
                        if (status){
                            Toast.makeText(getActivity(), "Su contraseña se ha cambiado con exito", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(getActivity(), "Error al cambiar contraseña", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        buttonCambiarEstacion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectEstacionActivity.class);
                startActivity(intent);
            }
        });

        buttonBorrarCuenta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder avisoBorradoCuenta = new AlertDialog.Builder(getActivity());
                avisoBorradoCuenta.setTitle("Confirmación de borrado de cuenta");
                avisoBorradoCuenta.setMessage("Si usted acepta borrar la cuenta, se eliminaran todos los datos relacionados con usted sin la posibilidad de recuperarlos.");
                avisoBorradoCuenta.setPositiveButton("Borrar Cuenta", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserDao.borrarCuenta(new myCallback() {
                            @Override
                            public void onCallback(boolean status) {
                                if (status){
                                    Intent intent = new Intent(getActivity(), NavegationDrawerActivity.class);
                                    startActivity(intent);

                                }else{
                                    Toast.makeText(getActivity(), "Error borrando la cuenta", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });
                avisoBorradoCuenta.setNegativeButton("Cancelar",null);
                AlertDialog dialog = avisoBorradoCuenta.create();
                dialog.show();
            }
        });

        return v;
    }
}