package com.example.freestylemeeting;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class PistaHolder extends RecyclerView.ViewHolder {

    TextView nombre,dificultad;

    public PistaHolder(View itemView) {
        super(itemView);
        nombre = itemView.findViewById(R.id.text_view_title);
        dificultad = itemView.findViewById(R.id.text_view_description);
    }
}
