package com.example.freestylemeeting.AdaptersList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freestylemeeting.DAO.UserDao;
import com.example.freestylemeeting.NavegationDrawerActivity;
import com.example.freestylemeeting.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import Modelo.Client;
import Modelo.Grupo;

public class GrupoAdapter extends RecyclerView.Adapter<GrupoAdapter.ViewHolder>{
    public List<Grupo> gruposList;
    private String correoCliente;
    public Context context;

    public GrupoAdapter(Context context, List<Grupo> gruposList) {
        this.context = context;
        this.gruposList = gruposList;
    }

    @Override
    public GrupoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grupos_list_item, parent, false);
        return new GrupoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GrupoAdapter.ViewHolder holder, int position) {

        holder.nametext.setText(gruposList.get(position).getNombre());
        holder.modalidadtext.setText("Modalidad: "+gruposList.get(position).getModalidad());
        holder.tamtext.setText("Participantes: "+gruposList.get(position).getMiembros().size()+"/"+gruposList.get(position).getTam());
        holder.niveltext.setText("Nivel: "+gruposList.get(position).getNivel());
        holder.mView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                UserDao.getUsersCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Client cliente = documentSnapshot.toObject(Client.class);
                        correoCliente=cliente.getEmail();
                    }
                });
                gruposList.get(position).addMiembro(correoCliente);
                //----------AÑADIRMETODO PARA LA BASE DE DATOS--------------------------------------------------------------------------------------------------------------------
                context.startActivity(new Intent(context, NavegationDrawerActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return gruposList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public TextView nametext;
        public TextView modalidadtext;
        public TextView niveltext;
        public TextView tamtext;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            nametext = (TextView) mView.findViewById(R.id.nombreGrupoList);
            modalidadtext = (TextView) mView.findViewById(R.id.modalidad_list);
            niveltext = (TextView) mView.findViewById(R.id.nivel_list);
            tamtext = (TextView) mView.findViewById(R.id.tam_list);
        }
    }
}
