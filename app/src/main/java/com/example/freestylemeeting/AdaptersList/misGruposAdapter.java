package com.example.freestylemeeting.AdaptersList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freestylemeeting.DAO.EstacionDao;
import com.example.freestylemeeting.DAO.UserDao;
import com.example.freestylemeeting.NavegationDrawerActivity;
import com.example.freestylemeeting.R;
import com.example.freestylemeeting.ListaParticipantes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

import Modelo.Client;
import Modelo.Grupo;

public class misGruposAdapter extends RecyclerView.Adapter<misGruposAdapter.ViewHolder>{
    public ArrayList<Grupo> gruposList = new ArrayList<>();
    private Client cliente;
    public Context context;

    public misGruposAdapter(Context context, ArrayList<Grupo> gruposList) {
        this.context = context;
        this.gruposList = gruposList;
    }

    @Override
    public misGruposAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        UserDao.getUsersCollection().document(UserDao.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                cliente = documentSnapshot.toObject(Client.class);
            }
        });

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mis_grupos_list_item, parent, false);
        return new misGruposAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull misGruposAdapter.ViewHolder holder, int position) {
        holder.nametext.setText(gruposList.get(position).getNombre());
        holder.modalidadtext.setText("Modalidad: "+gruposList.get(position).getModalidad());
        holder.tamtext.setText("Participantes: "+gruposList.get(position).getMiembros().size()+"/"+gruposList.get(position).getTam());
        holder.niveltext.setText("Nivel: "+gruposList.get(position).getNivel());
        holder.fechahoratext.setText("Fecha y hora: "+gruposList.get(position).getFecha()+" "+gruposList.get(position).getHora());
        holder.lugartext.setText("Lugar: "+gruposList.get(position).getLugar());
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
        public TextView fechahoratext;
        public TextView lugartext;
        public Button botonParticipantes;
        public ImageButton botonEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            nametext = (TextView) mView.findViewById(R.id.nombreGrupoList);
            modalidadtext = (TextView) mView.findViewById(R.id.modalidad_list);
            niveltext = (TextView) mView.findViewById(R.id.nivel_list);
            tamtext = (TextView) mView.findViewById(R.id.tam_list);
            fechahoratext = (TextView) mView.findViewById(R.id.fechaHoraList);
            lugartext = (TextView) mView.findViewById(R.id.lugarList);
            botonParticipantes = (Button) mView.findViewById(R.id.botonVerParticipates);
            botonEliminar = (ImageButton) mView.findViewById(R.id.botonElimnarGrupo);
            botonParticipantes.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    ArrayList<String> participantes = gruposList.get(getAdapterPosition()).getMiembros();
                    Intent intent = new Intent(context, ListaParticipantes.class);
                    intent.putExtra("participantes", participantes);
                    context.startActivity(intent);

                }
            });
            botonEliminar.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Grupo "+gruposList.get(getAdapterPosition()).getNombre()+" eliminado", Toast.LENGTH_SHORT ).show();
                    EstacionDao.deleteGrupo(gruposList.get(getAdapterPosition()).getId());
                    context.startActivity(new Intent(context, NavegationDrawerActivity.class));
                }
            });
        }
    }
}
