package com.example.demo_spring_batch.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Persona {
    private long id;
    private String nombre, correo, ip;
}
