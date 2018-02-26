package com.example.a21639999.appsqlite;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a21639999.appsqlite.model.Contacto;
import com.example.a21639999.appsqlite.sqliteDB.ContactosDatasource;

public class ContactoActivity extends AppCompatActivity {
    private long idContacto;
    private ContactosDatasource cds;
    private EditText etNombre;
    private EditText etEmail;
    private Contacto c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        idContacto = getIntent().getLongExtra(getResources().getString(R.string.ID), -1);

        cds = new ContactosDatasource(this);
        c = cds.leerContacto(idContacto);
        etNombre = findViewById(R.id.etNombreC);
        etEmail = findViewById(R.id.etEmailC);

        etNombre.setText(c.getNombre());
        etEmail.setText(c.getEmail());

    }

    public void guardarDatos(View view) {
        crear_dialogo(MainActivity.MODIFICADO).show();
        c.setNombre(etNombre.getText().toString());
        c.setEmail(etEmail.getText().toString());
    }

    public void eliminarContacto(View view) {
        crear_dialogo(MainActivity.BORRADO).show();

    }

    private Dialog crear_dialogo(final int opcion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ContactoActivity.this);
        builder.setCancelable(false);
        if (opcion == MainActivity.BORRADO){
            builder.setMessage("¿Desea eliminar el contacto?");
        }else{
            builder.setMessage("¿Desea actualizar el contacto?");
        }
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (opcion == MainActivity.BORRADO){
                    int cod = cds.borrarContacto(idContacto);
                    Intent i = getIntent();
                    i.putExtra("RESULTADO", MainActivity.BORRADO);
                    i.putExtra("CONTACTO", c.getNombre());
                    if (cod == 1){
                        setResult(RESULT_OK, i);
                    }else {
                        setResult(RESULT_CANCELED);
                    }
                }else {
                    int row = cds.actualizarContacto(c);
                    Intent i = getIntent();
                    i.putExtra("RESULTADO", MainActivity.MODIFICADO);
                    i.putExtra("CONTACTO", c.getNombre());
                    if (row > 0){
                        setResult(RESULT_OK, i);
                    }else {
                        setResult(RESULT_CANCELED);
                    }
                }
                finish();
            }

        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }
}
