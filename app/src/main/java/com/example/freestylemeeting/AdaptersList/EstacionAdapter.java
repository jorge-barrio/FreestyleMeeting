package com.example.freestylemeeting.AdaptersList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.DAO.UserDao;
import com.example.freestylemeeting.NavegationDrawerActivity;
import com.example.freestylemeeting.R;

import java.util.List;

import Modelo.Estacion;

public class EstacionAdapter extends RecyclerView.Adapter<EstacionAdapter.ViewHolder>  {

    public List<Estacion> estacionList;
    public Context context;

    public EstacionAdapter(Context context, List<Estacion> estacionList) {
        this.context = context;
        this.estacionList = estacionList;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.estaciones_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nametext.setText(estacionList.get(position).getNombre());
        holder.localidadtext.setText(estacionList.get(position).getLocalidad());

        Estacion estacion = estacionList.get(position);
        String cifEstacion = estacion.getCif();
        holder.mView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                UserDao.editCurrentEstacion(cifEstacion);
                EstacionDao.currentEstacion = estacion;
                context.startActivity(new Intent(context, NavegationDrawerActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return estacionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public TextView nametext;
        public TextView localidadtext;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            nametext = (TextView) mView.findViewById(R.id.nombreEstacionList);
            localidadtext = (TextView) mView.findViewById(R.id.localidad_estacion_list);
        }
    }
}
