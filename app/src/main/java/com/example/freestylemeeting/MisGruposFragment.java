package com.example.freestylemeeting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freestylemeeting.AdaptersList.misGruposAdapter;
import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.DAO.UserDao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

import Modelo.Client;
import Modelo.Estacion;
import Modelo.Grupo;

public class MisGruposFragment extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList<Grupo> grupos;
    private misGruposAdapter grupoAdapter;
    RecyclerView mMainList;

    private String mParam1;
    private String mParam2;

    public MisGruposFragment(){}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */

    public static MisGruposFragment newInstance(String param1, String param2) {
        MisGruposFragment fragment = new MisGruposFragment();
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
        View v = inflater.inflate(R.layout.fragment_mis_grupos, container, false);
        grupos = new ArrayList<>();
        grupoAdapter = new misGruposAdapter(getActivity(), grupos);

        mMainList = (RecyclerView) v.findViewById(R.id.idListViewMisGrupos);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMainList.setAdapter(grupoAdapter);

        // Mostramos los datos cacheados y despues ya hacemos la consulta
        //grupos.addAll(EstacionDao.currentEstacion.getGruposActualesOrdenados());

        UserDao.getUsersCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Client cliente = documentSnapshot.toObject(Client.class);
                EstacionDao.getEstacionesCollection().document(cliente.getCurrentEstacion()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Estacion estacion = documentSnapshot.toObject(Estacion.class);
                        if (estacion != null) {
                            grupos.removeAll(grupos);
                            grupos.addAll(estacion.getGruposPropietario(cliente.getEmail()));
                            grupoAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
        return v;
    }
}
