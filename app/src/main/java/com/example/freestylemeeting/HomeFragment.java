package com.example.freestylemeeting;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.DAO.UserDao;
import com.example.freestylemeeting.DAO.myCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

import Modelo.Client;
import Modelo.Estacion;
import Modelo.PackReserva;
import Modelo.UserEstacion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String TAG = "HomeFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Estacion
    TextView estaciontext;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
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
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        //UserDao.usersCollection.document(UserDao.getActualUser().getUid()).get().addOnSuccessListener();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        estaciontext = v.findViewById(R.id.textNombreEstacion);
        if(EstacionDao.currentEstacion != null){
            estaciontext.setText(EstacionDao.currentEstacion.getNombre());
        } else if (UserDao.currentCliente != null) {
            estaciontext.setText("Seleccionar estacion");
        }  else {
            Intent intent = new Intent(getActivity(), authActivity.class);
            startActivity(intent);

        }

        estaciontext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(UserDao.currentCliente != null){
                    Intent intent = new Intent(getActivity(), SelectEstacionActivity.class);
                    startActivity(intent);
                }else if(UserDao.currentEmpleado == null){
                    Intent intent = new Intent(getActivity(), authActivity.class);
                    startActivity(intent);
                }
            }
        });

        CardView training = v.findViewById(R.id.cardEntrenamiento);
        training.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(UserDao.currentCliente != null && UserDao.currentCliente.getCurrentEstacion() != null) {
                    Intent intent = new Intent(getActivity(), TrainingActivity.class);
                    startActivity(intent);
                }else if(UserDao.currentCliente != null){
                    Toast.makeText(getActivity(), "Selecciona antes una estacion", Toast.LENGTH_SHORT).show();
                }else if(UserDao.currentEmpleado != null) {
                    Toast.makeText(getActivity(), "Funcionalidad solo para clientes", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getActivity(), authActivity.class);
                    startActivity(intent);
                }
            }
        });

        CardView ski_trails_button = v.findViewById(R.id.cardPistas);
        ski_trails_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(UserDao.currentCliente != null && UserDao.currentCliente.getCurrentEstacion() != null) {
                    Intent intent = new Intent(getActivity(), ListPistasActivity.class);
                    startActivity(intent);
                }else if(UserDao.currentCliente != null){
                    Toast.makeText(getActivity(), "Selecciona antes una estacion", Toast.LENGTH_SHORT).show();
                }else if(UserDao.currentEmpleado != null) {
                    Intent intent = new Intent(getActivity(), ListPistasActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getActivity(), authActivity.class);
                    startActivity(intent);
                }
            }
        });

        CardView group_button = v.findViewById(R.id.cardGrupos);
        group_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(UserDao.currentCliente != null && UserDao.currentCliente.getCurrentEstacion() != null) {
                    Intent intent = new Intent(getActivity(), GroupActivity.class);
                    startActivity(intent);
                }else if(UserDao.currentCliente != null){
                    Toast.makeText(getActivity(), "Selecciona antes una estacion", Toast.LENGTH_SHORT).show();
                }else if(UserDao.currentEmpleado != null) {
                    Toast.makeText(getActivity(), "Funcionalidad solo para clientes", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getActivity(), authActivity.class);
                    startActivity(intent);
                }
            }
        });

        CardView book_material_button = v.findViewById(R.id.cardReservar);
        book_material_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(UserDao.currentCliente != null && UserDao.currentCliente.getCurrentEstacion() != null) {
                    if(EstacionDao.currentEstacion.getPacksReserva().isEmpty()){
                        UserDao.getUsersCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Client cliente = documentSnapshot.toObject(Client.class);
                                String cifEstacion = cliente.getCurrentEstacion();
                                EstacionDao.getEstacionesCollection().document(cifEstacion).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        Estacion estacion = documentSnapshot.toObject(Estacion.class);
                                        if(!estacion.getPacksReserva().isEmpty()){
                                            EstacionDao.currentEstacion = estacion;
                                            Intent intent = new Intent(getActivity(), ReservarMaterialActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(getActivity(), "En este momento no hay packs para alquilar", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                    } else {
                        Intent intent = new Intent(getActivity(), ReservarMaterialActivity.class);
                        startActivity(intent);
                    }
                }else if(UserDao.currentCliente != null){

                    Toast.makeText(getActivity(), "Selecciona antes una estacion", Toast.LENGTH_SHORT).show();
                }else if(UserDao.currentEmpleado != null) {
                    Log.d("Prueba","txt");
                    Intent intent = new Intent(getActivity(), ListMisReservasEstacion.class);
                    startActivity(intent);
                    //Toast.makeText(getActivity(), "Funcionalidad solo para clientes", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getActivity(), authActivity.class);
                    startActivity(intent);
                }
            }
        });



        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    getActivity().moveTaskToBack(true);
                                    getActivity().finish();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No hacer nada
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("¿Quieres salir de Freestyle Meeting?").setPositiveButton("Si", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                    return true;
                } else {
                    return false;
                }
            }
        });

        return v;
    }
}