package com.example.freestylemeeting;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import com.squareup.picasso.Picasso;

import Modelo.Pista;

class PistaAdapter extends FirebaseRecyclerAdapter<Pista, PistaHolder> {

    Context context;

    public PistaAdapter(Class<Pista> modelClass, int modelLayout, Class<PistaHolder> viewHolderClass, DatabaseReference ref, Context c)
    {
        super(modelClass, modelLayout, viewHolderClass, ref);
        context = c;
    }

    @Override
    protected void populateViewHolder(PistaHolder viewHolder, final Pista model, int position) {
        viewHolder.nombre.setText(model.getNombre());
        viewHolder.dificultad.setText(model.getDificultad());
        // Para imagenes Picasso.with(context).load(model.getLogo()).into(viewHolder.imagen);
        viewHolder.nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"La version es: "+ String.valueOf(model.getNombre()),Toast.LENGTH_SHORT).show();
            }
        });
    }
}