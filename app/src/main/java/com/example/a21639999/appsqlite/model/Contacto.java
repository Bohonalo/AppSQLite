package com.example.a21639999.appsqlite.model;

import java.io.Serializable;

/**
 * Created by 21639999 on 12/02/2018.
 */

public class Contacto implements Serializable {

    private long id;
    private String nombre;
    private String email;

    public Contacto(String nombre, String email) {
        this.id = -1;
        this.nombre = nombre;
        this.email = email;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }
}
