package com.example.a21639999.appsqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a21639999.appsqlite.model.Contacto;
import com.example.a21639999.appsqlite.sqliteDB.ContactosDatasource;


public class InsertarActivity extends AppCompatActivity {

    private EditText etNombre;
    private EditText etEmail;
    private ContactosDatasource cds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);
        etNombre = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        cds = new ContactosDatasource(this);
    }

    public void insertar (View v){
        Contacto contacto = new Contacto(etNombre.getText().toString(), etEmail.getText().toString());

        long idC = cds.insertarContacto(contacto);

        if(idC != -1){
            Toast.makeText(this, "La inserción se ha realizado correctamente", Toast.LENGTH_LONG).show();
            etNombre.setText("");
            etEmail.setText("");
        }else{
            Toast.makeText(this, "No se a realizado la inserción", Toast.LENGTH_LONG).show();
        }
    }
}
