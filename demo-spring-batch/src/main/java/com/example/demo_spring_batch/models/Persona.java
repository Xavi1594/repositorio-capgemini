package com.example.demo_spring_batch.models;

import lombok.Data;

@Data

public class Persona {
    private long id;
    private String nombre, correo, ip;

    public Persona() {
    }

    public Persona(long id, String nombre, String correo, String ip) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.ip = ip;
    }
}
