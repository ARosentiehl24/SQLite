package com.unimagdalena.edu.co.sqlite;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder>{

    private Activity context;
    private ArrayList<Plato> platos;

    public Adaptador(Activity context, ArrayList<Plato> platos) {
        this.context = context;
        this.platos = platos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View platos = layoutInflater.inflate(R.layout.elemento_plato, parent, false);

        return new ViewHolder(platos);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Plato plato = platos.get(position);

        holder.nombre.setText(plato.getNombre());
        holder.descrtipcion.setText(plato.getDescripcion());
        holder.tipoDePlato.setText(plato.getTipoDePlato());
        holder.tipoDeComida.setText(plato.getTipoDeComida());
        holder.precio.setText(plato.getPrecio());
        holder.enPromocion.setVisibility(plato.isEnPromocion() ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return platos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.nombre)
        TextView nombre;

        @Bind(R.id.descrtipcion)
        TextView descrtipcion;

        @Bind(R.id.tipoDePlato)
        TextView tipoDePlato;

        @Bind(R.id.tipoDeComida)
        TextView tipoDeComida;

        @Bind(R.id.precio)
        TextView precio;

        @Bind(R.id.enPromocion)
        TextView enPromocion;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
