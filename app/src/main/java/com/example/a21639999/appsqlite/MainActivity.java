package com.example.a21639999.appsqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.a21639999.appsqlite.model.Contacto;
import com.example.a21639999.appsqlite.rvUtils.AdaptadorContacto;
import com.example.a21639999.appsqlite.sqliteDB.ContactosDatasource;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    private ContactosDatasource cds;
    private ArrayList<Contacto> listaContactos;
    private AdaptadorContacto ac;
    private LinearLayoutManager llm;
    static final int COD_DATOS_CONTACTO = 1;
    static final int BORRADO = 10;
    static final int MODIFICADO = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rvContactos);
        cds = new ContactosDatasource(this);
        listaContactos = new ArrayList<Contacto>();
    }

    public void consultarContacto (View v){
        cargarLista();
    }

    private void cargarLista() {
        listaContactos = cds.leerContactos();
        if (listaContactos.size() == 0){
            Toast.makeText(this, "No se han encontrado contactos", Toast.LENGTH_LONG).show();
        }else{
            cargarRV();
        }
    }

    private void cargarRV(){
        rv.setHasFixedSize(true);

        llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        ac =  new AdaptadorContacto(listaContactos);

        ac.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contacto c = listaContactos.get(rv.getChildAdapterPosition(v));

                Intent i = new Intent(MainActivity.this, ContactoActivity.class);

                i.putExtra(getResources().getString(R.string.ID), c.getId());

                startActivityForResult(i, COD_DATOS_CONTACTO);
            }
        });
        rv.setAdapter(ac);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == COD_DATOS_CONTACTO){
            int res = data.getIntExtra("RESULTADO", 0);
            String nomContacto = data.getStringExtra("CONTACTO");
            String mensaje = "";
            if (resultCode == RESULT_OK){
                if (res == BORRADO){
                    mensaje = "el borrado del contacto " + nomContacto + " se ha realizado con éxito.";

                }else {
                    mensaje = "La modificación del contacto " + nomContacto + " se ha realizado con éxito.";
                }
            }else{
                if (res == BORRADO){
                    mensaje = "el borrado del contacto " + nomContacto + " no se ha realizado con éxito.";

                }else {
                    mensaje = "La modificación del contacto " + nomContacto + " no se ha realizado con éxito.";
                }
            }
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarLista();
    }

    public void insertarContacto (View v){
        Intent i = new Intent(this, InsertarActivity.class );
        startActivity(i);
    }
}
