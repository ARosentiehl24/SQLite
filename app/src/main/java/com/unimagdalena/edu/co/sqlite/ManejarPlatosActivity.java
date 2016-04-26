package com.unimagdalena.edu.co.sqlite;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.inquiry.Inquiry;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManejarPlatosActivity extends AppCompatActivity {

    private boolean modoEdicion = false;

    @Bind(R.id.etNombre)
    TextInputEditText etNombre;

    @Bind(R.id.etDescripcion)
    TextInputEditText etDescripcion;

    @Bind(R.id.etPrecio)
    TextInputEditText etPrecio;

    @Bind(R.id.sTiposDePlato)
    AppCompatSpinner sTiposDePlato;

    @Bind(R.id.sTiposDeComida)
    AppCompatSpinner sTiposDeComida;

    @Bind(R.id.cbEnPromocion)
    AppCompatCheckBox cbEnPromocion;

    @OnClick({R.id.fab})
    public void OnClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.fab:
                Plato plato = new Plato();
                plato.setNombre(etNombre.getText().toString());
                plato.setDescripcion(etDescripcion.getText().toString());
                plato.setPrecio(etPrecio.getText().toString());
                plato.setTipoDePlato(sTiposDePlato.getSelectedItem().toString());
                plato.setTipoDeComida(sTiposDeComida.getSelectedItem().toString());
                plato.setEnPromocion(cbEnPromocion.isChecked());

                Inquiry.init(this, Constantes.NOMBRE_BASE_DE_DATOS, 1);

                if (modoEdicion) {
                    Inquiry.get().update(Constantes.TABLA_PLATO, Plato.class).values(plato).where("nombre = ?", plato.getNombre()).run();
                } else {
                    Inquiry.get().insertInto(Constantes.TABLA_PLATO, Plato.class).values(plato).run();
                }

                onBackPressed();

                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manejar_platos);
        ButterKnife.bind(this);
        Inquiry.init(this, Constantes.NOMBRE_BASE_DE_DATOS, 1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        modoEdicion = getIntent().getBooleanExtra("editar", false);

        if (modoEdicion) {
            Plato plato = (Plato) getIntent().getSerializableExtra("plato");

            etNombre.setText(plato.getNombre());
            etDescripcion.setText(plato.getDescripcion());
            etPrecio.setText(plato.getPrecio());
            sTiposDeComida.setSelection(getIndexFor(plato.getTipoDeComida(), R.array.tiposDeComida));
            sTiposDePlato.setSelection(getIndexFor(plato.getTipoDePlato(), R.array.tiposDePlato));
            cbEnPromocion.setChecked(plato.isEnPromocion());

            ((FloatingActionButton) findViewById(R.id.fab)).setImageResource(R.drawable.ic_autorenew);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Inquiry.deinit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int getIndexFor(String item, int resId) {
        int i = 0;

        String[] androidStrings = getResources().getStringArray(resId);

        for (String string : androidStrings) {
            if (item.equals(string)) {
                break;
            }

            i++;
        }

        return i;
    }
}
