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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rvContactos);
        cds = new ContactosDatasource(this);
        listaContactos = new ArrayList<Contacto>();
    }

    public void consultarContacto (View v){
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
        rv.setAdapter(ac);
    }

    public void insertarContacto (View v){
        Intent i = new Intent(this, InsertarActivity.class );
        startActivity(i);
    }
}
