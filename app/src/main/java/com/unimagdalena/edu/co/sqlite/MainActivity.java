package com.unimagdalena.edu.co.sqlite;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.inquiry.Inquiry;
import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private Activity activity;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @OnClick({R.id.fab})
    public void OnClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.fab:
                startActivity(new Intent(this, ManejarPlatosActivity.class));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        activity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Inquiry.init(this, Constantes.NOMBRE_BASE_DE_DATOS, 1);

        Adaptador adaptador = new Adaptador(this, obtenerPlatos());

        recyclerView.setAdapter(adaptador);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Inquiry.deinit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem actionSearch = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(actionSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();

                ArrayList<Plato> filteredFoods = new ArrayList<>();

                for (Plato food : obtenerPlatos()) {
                    if (food.getNombre().toLowerCase().contains(newText)) {
                        filteredFoods.add(food);
                    }
                }

                Adaptador adaptador = new Adaptador(MainActivity.this, filteredFoods);
                recyclerView.setAdapter(adaptador);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setHasFixedSize(true);

                adaptador.notifyDataSetChanged();

                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_show_all: {
                Adaptador adaptador = new Adaptador(this, obtenerPlatos());

                recyclerView.setAdapter(adaptador);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                adaptador.notifyDataSetChanged();
                break;
            }
            case R.id.action_kind_of_food:
                new MaterialDialog.Builder(this)
                        .items(R.array.tiposDeComida)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                ArrayList<Plato> platos = new ArrayList<>();

                                Plato[] platosDB = Inquiry.get().selectFrom(Constantes.TABLA_PLATO, Plato.class).where("tipoDeComida = ?", text.toString()).sort("nombre ASC").all();

                                if (platosDB != null) {
                                    Collections.addAll(platos, platosDB);
                                }

                                Adaptador adaptador = new Adaptador(activity, platos);

                                recyclerView.setAdapter(adaptador);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                                adaptador.notifyDataSetChanged();
                            }
                        })
                        .show();
                return true;
            case R.id.action_on_sale: {
                ArrayList<Plato> platos = new ArrayList<>();

                Plato[] platosDB = Inquiry.get().selectFrom(Constantes.TABLA_PLATO, Plato.class).where("enPromocion = ?", 1).sort("nombre ASC").all();

                if (platosDB != null) {
                    Collections.addAll(platos, platosDB);
                }

                Adaptador adaptador = new Adaptador(this, platos);

                recyclerView.setAdapter(adaptador);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                adaptador.notifyDataSetChanged();

                return true;
            }
            case R.id.action_about:
                new AlertDialogWrapper.Builder(this)
                        .setTitle("Acerca de")
                        .setMessage("Estudiantes\n\n - Jose Arturo\n - Kelly Agudelo")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Plato> obtenerPlatos() {
        ArrayList<Plato> platos = new ArrayList<>();

        Plato[] platosDB = Inquiry.get().selectFrom(Constantes.TABLA_PLATO, Plato.class).sort("nombre ASC").all();

        if (platosDB != null) {
            Collections.addAll(platos, platosDB);
        }

        return platos;
    }
}