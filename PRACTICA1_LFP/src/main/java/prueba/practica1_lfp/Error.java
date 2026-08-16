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
    private String mensaje;
    private int fila;
    private int columna;
    
    
        public Error(String lexema, String mensaje, int fila, int columna) {
        this.lexema = lexema;
        this.mensaje = mensaje;
        this.fila = fila;
        this.columna = columna;
    }

    public String getLexema() {
        return lexema;
    }

    public String getMensaje() {
        return mensaje;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
    
}
