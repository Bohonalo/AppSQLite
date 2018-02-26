package com.example.a21639999.appsqlite.rvUtils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a21639999.appsqlite.R;
import com.example.a21639999.appsqlite.model.Contacto;

import java.util.ArrayList;

/**
 * Created by 21639999 on 12/02/2018.
 */

public class AdaptadorContacto extends RecyclerView.Adapter <AdaptadorContacto.VHContacto> implements View.OnClickListener{
    private ArrayList<Contacto> datos;
    private View.OnClickListener listener;

    public AdaptadorContacto(ArrayList<Contacto> datos){
        this.datos = datos;
    }

    @Override
    public VHContacto onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contacto, parent, false);
        VHContacto vhc = new VHContacto(v);
        v.setOnClickListener(this);
        return vhc;
    }

    @Override
    public void onBindViewHolder(VHContacto holder, int position) {
        holder.tvNombre.setText(datos.get(position).getNombre());
        holder.tvEmail.setText(datos.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public void setOnclickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    public static class VHContacto extends  RecyclerView.ViewHolder{
        private TextView tvNombre;
        private TextView tvEmail;

        public VHContacto(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvEmail = itemView.findViewById(R.id.tvEmail);
        }

        public TextView getTvNombre() {
            return tvNombre;
        }

        public TextView getTvEmail() {
            return tvEmail;
        }
    }

}
