/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.practica1_lfp;

/**
 *
 * @author mario
 */
public class Procesador {

    private Token[] tokens;
    private Error[] errores;
    private int contadorTokens;
    private int contadorErrores;
    private int numeroToken;
    private int numeroError;

    public Procesador() {
        this.tokens = new Token[500];
        this.errores = new Error[500];
        this.contadorTokens = 0;
        this.contadorErrores = 0;
        this.numeroToken = 0;
    }

    public void agregarToken(String lexema, String tipo, int fila, int columna) {
        numeroToken++;
        tokens[contadorTokens] = new Token(numeroToken, lexema, tipo, fila, columna);
        contadorTokens++;
    }

    public void agregarError(String lexema, String mensaje, int fila, int columna) {
        numeroError++;
        errores[contadorErrores] = new Error(lexema, mensaje, fila, columna, numeroError);
        contadorErrores++;
    }

    public Token[] getTokens() {

        return tokens;
    }

    public Error[] getErrores() {

        return errores;
    }

    public int getNumTokens() {
        return contadorTokens;
    }

    public int getNumErrores() {
        return contadorErrores;
    }
}
