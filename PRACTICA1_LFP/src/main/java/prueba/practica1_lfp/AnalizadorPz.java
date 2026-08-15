/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.practica1_lfp;

/**
 *
 * @author mario
 */
public class AnalizadorPz {

    private String contenido;
    private int posicion;
    private int fila;
    private int columna;
    private Token[] tokens;
    private Error[] errores;
    private int contadorTokens;
    private int contadorErrores;
    private int numeroToken;
    private Archivo archivo;

    public AnalizadorPz(File archivo) throws IOException {
        this.contenido = this.archivo.leerArchivo(archivo);
        this.posicion = 0;
        this.fila = 1;
        this.columna = 1;
        this.tokens = new Token[500];
        this.errores = new Error[500];
        this.contadorTokens = 0;
        this.contadorErrores = 0;
        this.numeroToken = 0;
    }

    public void analizar() {
        while (posicion < contenido.length()) {
            char c = contenido.charAt(posicion);

            if (espacio(c)) {

                continue;
            }

            if (comentarioLinea(c)) {

                continue;
            }

            if (comentarioBloque(c)) {

                continue;
            }

            if (c == '"') {

                continue;
            }

            if (digito(c)) {

                continue;
            }

            if (letra(c) || c == '_') {

                continue;
            }

            if (c == '@') {

                continue;
            }

            if (flecha(c)) {

                continue;
            }

            if (delimitador(c)) {

                continue;
            }

            if (operador(c)) {

                continue;
            }

        }
    }

    private boolean espacio(char c) {
        return c == ' ' || c == '\t' || c == '\r' || c == '\n';
    }

    private boolean comentarioLinea(char c) {
        return c == '/' && posicion + 1 < contenido.length()
                && contenido.charAt(posicion + 1) == '/';
    }

    private boolean comentarioBloque(char c) {
        return c == '/' && posicion + 1 < contenido.length()
                && contenido.charAt(posicion + 1) == '*';
    }

    private boolean digito(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean letra(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private boolean flecha(char c) {
        return c == '-' && posicion + 1 < contenido.length()
                && contenido.charAt(posicion + 1) == '>';
    }

    private boolean delimitador(char c) {
        return c == '{' || c == '}' || c == '(' || c == ')';
    }

    private boolean operador(char c) {
        return c == '=' || c == '+';
    }

}
