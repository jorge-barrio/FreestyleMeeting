package com.example.freestylemeeting.AdaptersList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.PistaActivity;
import com.example.freestylemeeting.R;
import com.example.freestylemeeting.entrenamiento;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import Modelo.Entrenamiento;
import Modelo.Estacion;
import Modelo.Pista;

public class EntrenamientoAdapter extends RecyclerView.Adapter<EntrenamientoAdapter.ViewHolder> {
    public List<Entrenamiento> entrenamientoList;

    public Context context;

    public EntrenamientoAdapter(Context context, List<Entrenamiento> pistasList) {
        this.context = context;
        this.entrenamientoList = pistasList;
    }

    @Override
    public EntrenamientoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entrenamientos_list_item, parent, false);
        return new EntrenamientoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntrenamientoAdapter.ViewHolder holder, int position) {
        Log.d("pruebaa", String.valueOf(entrenamientoList));
        Entrenamiento ent = entrenamientoList.get(position);

        EstacionDao.getEstacionesCollection().document(ent.getCifEstacion()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Estacion estacion = documentSnapshot.toObject(Estacion.class);
                holder.idEnt.setText(estacion.getNombre());
            }
        });

        holder.fechaInicio.setText(ent.getFechaInicio().toString());
        holder.fechaFin.setText(ent.getFechaFin().toString());

        holder.mView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, entrenamiento.class);
                intent.putExtra("pistas", ent.getIdPistas());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return entrenamientoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public TextView idEnt;
        public TextView fechaInicio;
        public TextView fechaFin;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            idEnt = (TextView) mView.findViewById(R.id.nombreEntrenamientoList);
            fechaInicio = (TextView) mView.findViewById(R.id.fechaInicioDescription);
            fechaFin = (TextView) mView.findViewById(R.id.fechaFinDescription);

        }
    }
}
