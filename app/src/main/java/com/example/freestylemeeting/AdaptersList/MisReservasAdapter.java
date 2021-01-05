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

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Modelo.Estacion;
import Modelo.Reserva;

public class MisReservasAdapter extends RecyclerView.Adapter<MisReservasAdapter.ViewHolder>  {

    public List<Reserva> reservasList;

    public Context context;

    public MisReservasAdapter(Context context, List<Reserva> reservasList) {
        this.context = context;
        this.reservasList = reservasList;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mi_reserva_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String dateRecogida = new SimpleDateFormat("dd / MM / yyyy").format(reservasList.get(position).getFechaRecogida());
        String actual = reservasList.get(position).getFechaRecogida().before(new Date()) ? "" : " (Pendiente)" ;
        holder.fechatext.setText("Reserva nº"+(position+1)+"\nFecha: "+dateRecogida+actual);
        holder.estaciontext.setText(reservasList.get(position).getEstacion());
        holder.packtext.setText(reservasList.get(position).getNombrePack());
        holder.preciotext.setText(""+reservasList.get(position).getPrecioEuros()+" €");
        holder.alturatext.setText(""+reservasList.get(position).getAltura()+" m");
        holder.pesotext.setText(""+reservasList.get(position).getPeso()+" kg");
        holder.pietext.setText(""+reservasList.get(position).getTalla());
        holder.tiendatext.setText(reservasList.get(position).getCorreoTienda());
    }

    @Override
    public int getItemCount() {
        return reservasList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public TextView fechatext;
        public TextView estaciontext;
        public TextView packtext;
        public TextView preciotext;
        public TextView alturatext;
        public TextView pesotext;
        public TextView pietext;
        public TextView tiendatext;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            fechatext = (TextView) mView.findViewById(R.id.mi_reserva_fecha_MisReservasList);
            estaciontext = (TextView) mView.findViewById(R.id.estacion_MisReserasList);
            packtext = (TextView) mView.findViewById(R.id.pack_MisReserasList);
            preciotext = (TextView) mView.findViewById(R.id.precio_MisReserasList);
            alturatext = (TextView) mView.findViewById(R.id.altura_MisReserasList);
            pesotext = (TextView) mView.findViewById(R.id.peso_MisReserasList);
            pietext = (TextView) mView.findViewById(R.id.talla_pie_MisReserasList);
            tiendatext = (TextView) mView.findViewById(R.id.correo_tienda_MisReserasList);
        }
    }
}
