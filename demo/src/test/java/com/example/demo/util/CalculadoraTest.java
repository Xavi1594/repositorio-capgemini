package com.example.demo.util;

import org.junit.jupiter.api.Test;

import com.example.util.Calculadora;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculadoraTest {
    Calculadora calculadora = new Calculadora();
    @Test
    void testSumar() {
        assertEquals(5.0, calculadora.sumar(2.0, 3.0));
        assertEquals(-1.0, calculadora.sumar(2.0, -3.0));
        assertEquals(0.0, calculadora.sumar(0.0, 0.0));
    }
    @Test 
    void sumarReales(){
        assertEquals(5.0, calculadora.sumar(2.5, 2.5));
        assertEquals(0.0, calculadora.sumar(2.5, -2.5));
        // assertEquals(0.3, calculadora.sumar(0.1, 0.2));
        assertEquals(-5.0, calculadora.sumar(-2.5, -2.5));
    }
    @Test
    void restarTest(){
        assertEquals(
            3.0, calculadora.restar(5.0, 2.0)
        );
    }
    @Test
    void dividirTest(){
        assertEquals(
            2.0, calculadora.dividir(4.0, 2.0)
        );
    }
    @Test  
    void multiplicarTest(){
        assertEquals(
            8.0, calculadora.multiplicar(4.0, 2.0)
        );
    }
  
}

