/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.practica1_lfp;

/**
 *
 * @author mario
 */
public class Token {

    private int numero;
    private String lexema;
    private String tipo;
    private int columna;
    private int fila;
    String color;

    public Token(int numero, String lexema, String tipo, int columna, int fila, String color) {
        this.numero = numero;
        this.lexema = lexema;
        this.tipo = tipo;
        this.columna = columna;
        this.fila = fila;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    

    public int getNumero() {
        return numero;
    }

    public String getLexema() {
        return lexema;
    }

    public String getTipo() {
        return tipo;
    }

    public int getColumna() {
        return columna;
    }

    public int getFila() {
        return fila;
    }
    
    
    
}
