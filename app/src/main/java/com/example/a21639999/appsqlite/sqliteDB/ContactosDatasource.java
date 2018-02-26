package com.example.a21639999.appsqlite.sqliteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.a21639999.appsqlite.model.Contacto;

import java.util.ArrayList;

/**
 * Created by 21639999 on 12/02/2018.
 */

public class ContactosDatasource {

    private Context miContext;
    private ContactosSQLiteHelper miSQLiteHelper;

    public ContactosDatasource(Context context) {
        miContext = context;
        miSQLiteHelper = new ContactosSQLiteHelper(miContext);
    }

    public SQLiteDatabase openReadable() {
        return miSQLiteHelper.getReadableDatabase();
    }

    public SQLiteDatabase openWriteable() {
        return miSQLiteHelper.getWritableDatabase();
    }

    public void close(SQLiteDatabase database) {
        database.close();
    }

    public long insertarContacto(Contacto contacto) {

        SQLiteDatabase database = openWriteable();
        database.beginTransaction();

        ContentValues cv = new ContentValues();
        cv.put(ContactosDBContract.ContactoEntry.COLUMN_NAME, contacto.getNombre());
        cv.put(ContactosDBContract.ContactoEntry.COLUMN_MAIL, contacto.getEmail());
        long idContacto = database.insert(ContactosDBContract.ContactoEntry.TABLE_NAME, null, cv);

        if (idContacto != -1) {
            database.setTransactionSuccessful();
        }

        database.endTransaction();
        close(database);

        return idContacto;
    }

    public int actualizarContacto(Contacto contacto) {
        SQLiteDatabase database = openWriteable();
        database.beginTransaction();

        ContentValues cv = new ContentValues();
        cv.put(ContactosDBContract.ContactoEntry.COLUMN_NAME, contacto.getNombre());
        cv.put(ContactosDBContract.ContactoEntry.COLUMN_MAIL, contacto.getEmail());

        String Where = ContactosDBContract.ContactoEntry.COLUMN_ID + "=" + contacto.getId();
        int row = database.update(ContactosDBContract.ContactoEntry.TABLE_NAME, cv, Where, null);
//
//        String Where2 = ContactosDBContract.ContactoEntry.COLUMN_ID + "= ?";
//        String [] args = {String.valueOf(contacto.getId())};
//        database.update(ContactosDBContract.ContactoEntry.TABLE_NAME, cv, Where2, args);
//
//        String Where3 = "% = %d";
//        database.update(ContactosDBContract.ContactoEntry.TABLE_NAME, cv, String.format(Where3,
//                ContactosDBContract.ContactoEntry.COLUMN_ID, contacto.getId()), null);

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);

        return row;
    }

    public int borrarContacto(long idContacto) {

        SQLiteDatabase database = openWriteable();
        database.beginTransaction();

        String [] args = {String.valueOf(idContacto)};
        int cod = database.delete(ContactosDBContract.ContactoEntry.TABLE_NAME,
                ContactosDBContract.ContactoEntry.COLUMN_ID + "=?", args);

//        database.delete(ContactosDBContract.ContactoEntry.TABLE_NAME,
//                ContactosDBContract.ContactoEntry.COLUMN_ID + "=" + idContacto, null);

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);

        return cod;
    }

    public Contacto leerContacto(long idContacto) {
        SQLiteDatabase database = openReadable();
        Contacto contacto = null;
        String sentencia = "SELECT " + ContactosDBContract.ContactoEntry.COLUMN_ID + ", " +
                ContactosDBContract.ContactoEntry.COLUMN_NAME + ", " +
                ContactosDBContract.ContactoEntry.COLUMN_MAIL +
                " FROM contactos WHERE " +
                ContactosDBContract.ContactoEntry.COLUMN_ID + " = " +
                idContacto;

        Cursor miCursor = database.rawQuery(sentencia, null);

        String nombre = "";
        String email = "";

        if (miCursor.moveToFirst()) {
            idContacto = miCursor.getLong(miCursor.getColumnIndex(ContactosDBContract.ContactoEntry.COLUMN_ID));
            nombre = miCursor.getString(miCursor.getColumnIndex(ContactosDBContract.ContactoEntry.COLUMN_NAME));
            email = miCursor.getString(miCursor.getColumnIndex(ContactosDBContract.ContactoEntry.COLUMN_MAIL));

            contacto = new Contacto(nombre, email);
            contacto.setId(idContacto);
        }

        miCursor.close();
        database.close();
        return contacto;
    }

    public ArrayList<Contacto> leerContactos() {
        SQLiteDatabase database = openReadable();
        ArrayList<Contacto> contactos = new ArrayList<Contacto>();
        Contacto contacto = null;

        Cursor micursor = database.query(ContactosDBContract.ContactoEntry.TABLE_NAME,
        new String[]{ContactosDBContract.ContactoEntry.COLUMN_ID,
                ContactosDBContract.ContactoEntry.COLUMN_NAME,
                ContactosDBContract.ContactoEntry.COLUMN_MAIL},
                null, null, null, null, null);

        long idContacto = -1;
        String nombre = "";
        String email = "";

        if (micursor.moveToFirst()) {
            do {
                idContacto = micursor.getLong(micursor.getColumnIndex(ContactosDBContract.ContactoEntry.COLUMN_ID));
                nombre = micursor.getString(micursor.getColumnIndex(ContactosDBContract.ContactoEntry.COLUMN_NAME));
                email = micursor.getString(micursor.getColumnIndex(ContactosDBContract.ContactoEntry. COLUMN_MAIL));

                contacto = new Contacto(nombre, email);
                contacto.setId(idContacto);

                contactos.add(contacto);

            } while (micursor.moveToNext());
        }
        micursor.close();
        close(database);
        return contactos;
    }
}