/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.practica1_lfp;

/**
 *
 * @author mario
 */
public class Error {
    
    private String lexema;
    private String tipoError;
    private int fila;
    private int columna;
    private int numero;
    
    
        public Error(String lexema, String error, int fila, int columna, int numero) {
        this.lexema = lexema;
        this.tipoError = error;
        this.fila = fila;
        this.columna = columna;
        this.numero=numero;
    }

    public String getLexema() {
        return lexema;
    }

    public String getTipoError() {
        return tipoError;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public int getNumero() {
        return numero;
    }
    
}
