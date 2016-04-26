package com.unimagdalena.edu.co.sqlite;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.inquiry.Inquiry;
import com.afollestad.materialdialogs.AlertDialogWrapper;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {

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

    public void borrarPlatos(int i) {
        platos.remove(i);
        notifyItemRemoved(i);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

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

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            new AlertDialogWrapper.Builder(context)
                    .setTitle("Borrar")
                    .setMessage("¿Estas seguro que deseas borrar el plato?")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Plato plato = platos.get(getLayoutPosition());

                            Inquiry.get().deleteFrom(Constantes.TABLA_PLATO, Plato.class).where("nombre = ?", plato.getNombre()).run();

                            borrarPlatos(getLayoutPosition());

                            dialog.dismiss();
                        }
                    })
                    .show();
            return false;
        }

        @Override
        public void onClick(View v) {
            Plato plato = platos.get(getLayoutPosition());

            Intent intent = new Intent(context, ManejarPlatosActivity.class);

            intent.putExtra("editar", true);
            intent.putExtra("plato", plato);

            context.startActivity(intent);
        }
    }
}
