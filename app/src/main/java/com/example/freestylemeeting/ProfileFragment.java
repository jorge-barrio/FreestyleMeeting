package com.example.freestylemeeting;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.DAO.UserDao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import Modelo.Client;
import Modelo.Estacion;
import Modelo.UserEstacion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        if(UserDao.sesionIniciada()){
            /* Mostrar datos del usuarios */
            TextView entrenamientos = v.findViewById(R.id.entrenamientos);
            TextView nametext = v.findViewById(R.id.namePerfil);
            TextView emailtext = v.findViewById(R.id.emailPerfil);
            TextView numEntrenamientos = v.findViewById(R.id.numEstrenamientosPerfil);
            TextView numReservas = v.findViewById(R.id.numReservasPerfil);
            UserDao.getUsersCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Client cliente = documentSnapshot.toObject(Client.class);

                    if(cliente != null){
                        nametext.setText(cliente.getName());
                        emailtext.setText(cliente.getEmail());
                        numEntrenamientos.setText(""+cliente.getEntrenamientos().size());
                        numReservas.setText(""+cliente.getReservas().size());
                        entrenamientos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), userTrainings.class);
                                startActivity(intent);
                            }
                        });
                    } else {
                        UserDao.getEnterprisesCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                UserEstacion trabajador = documentSnapshot.toObject(UserEstacion.class);
                                if(trabajador != null){
                                    nametext.setText(trabajador.getName());
                                    emailtext.setText(trabajador.getEmail());
                                } else {
                                    Toast.makeText(getActivity(), "Error.", Toast.LENGTH_SHORT).show();
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

        Button logOutButton = (Button) v.findViewById(R.id.logout_button);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                UserDao.signOut();
                startActivity(new Intent(getActivity(), authActivity.class));
            }
        });

        // Inflate the layout for this fragment
        return v;
    }
}