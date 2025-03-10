package com.example.demo.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculadoraTest {
    @Test
    void testSumar() {
        Calculadora calculadora = new Calculadora();
        assertEquals(5.0, calculadora.sumar(2.0, 3.0));
        assertEquals(-1.0, calculadora.sumar(2.0, -3.0));
        assertEquals(0.0, calculadora.sumar(0.0, 0.0));
    }
  
}

