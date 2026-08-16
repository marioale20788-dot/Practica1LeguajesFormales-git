/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.practica1_lfp;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author mario
 */
public class AnalizadorPz {

    private String contenido;
    private int posicion;
    private int fila;
    private int columna;
    private Archivo archivo = new Archivo();
    private Procesador procesadorPz;

    public AnalizadorPz(File archivo) throws IOException {
        this.contenido = this.archivo.leerArchivo(archivo);
        this.posicion = 0;
        this.fila = 1;
        this.columna = 1;
        this.procesadorPz = new Procesador();
    }

    public Procesador getProcesadorPz() {
        return procesadorPz;
    }

    public void analizar() {

        while (posicion < contenido.length()) {
            char c = contenido.charAt(posicion);

            if (espacio(c) == true) {
                avanzarEspacio(c);
                continue;
            }

            if (comentarioLinea(c) == true) {
                saltarComentarioLinea();
                continue;
            }

            if (comentarioBloque(c) == true) {
                saltarComentarioBloque();
                continue;
            }

            if (c == '"') {
                procesarCadena();
                continue;
            }

            if (digito(c) == true) {
                procesarNumero();
                continue;
            }

            if (letra(c) == true || c == '_') {
                procesarIdentificador();
                continue;
            }

            if (c == '@') {
                procesarDirectiva();
                continue;
            }

            if (flecha(c) == true) {
                procesarFlecha();
                continue;
            }

            if (delimitador(c) == true) {
                procesarDelimitador(c);
                continue;
            }

            if (operador(c) == true) {
                procesarOperador(c);
                continue;
            }

            procesadorPz.agregarError("" + c, "Carácter no reconocido", fila, columna);
            posicion++;
            columna++;
        }

    }

    public boolean espacio(char c) {
        if (c == ' ' || c == '\t' || c == '\n') {
            return true;
        }
        return false;
    }

    public boolean comentarioLinea(char c) {
        if (c == '/' && posicion + 1 < contenido.length() && contenido.charAt(posicion + 1) == '/') {
            return true;
        }
        return false;
    }

    public boolean comentarioBloque(char c) {
        if (c == '/' && posicion + 1 < contenido.length() && contenido.charAt(posicion + 1) == '*') {
            return true;
        }
        return false;

    }

    public boolean digito(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        }
        return false;
    }

    public boolean letra(char c) {
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
            return true;
        }
        return false;
    }

    public boolean flecha(char c) {
        if (c == '-' && posicion + 1 < contenido.length() && contenido.charAt(posicion + 1) == '>') {
            return true;
        }
        return false;
    }

    public boolean delimitador(char c) {
        if (c == '{' || c == '}' || c == '(' || c == ')' || c == ',') {
            return true;
        }
        return false;
    }

    public boolean operador(char c) {
        if (c == '=' || c == '+') {
            return true;
        }
        return false;
    }

    public void avanzarEspacio(char c) {
        if (c == '\n') {
            fila++;
            columna = 1;
        } else {
            columna++;
        }
        posicion++;
    }

    public void saltarComentarioLinea() {
        posicion = posicion + 2;
        columna = columna + 2;
        while (posicion < contenido.length() && contenido.charAt(posicion) != '\n') {
            posicion++;
            columna++;
        }
    }

    public void saltarComentarioBloque() {
        posicion = posicion + 2;
        columna = columna + 2;
        while (posicion < contenido.length() - 1) {
            if (contenido.charAt(posicion) == '*' && contenido.charAt(posicion + 1) == '/') {
                posicion = posicion + 2;
                columna = columna + 2;
                return;
            }
            if (contenido.charAt(posicion) == '\n') {
                fila++;
                columna = 1;
            } else {
                columna++;
            }
            posicion++;
        }
        procesadorPz.agregarError("/*", "Comentario de bloque sin cerrar", fila, columna);
    }

    public void procesarCadena() {
        int inicioFila = fila;
        int inicioColumna = columna;
        String lexema = "";
        procesadorPz.agregarToken("\"", "DELIMITADOR", inicioFila, inicioColumna);
        posicion++;
        columna++;

        while (posicion < contenido.length()) {
            char c = contenido.charAt(posicion);
            if (c == '"') {
                procesadorPz.agregarToken(lexema, "LITERAL_CADENA", inicioFila, inicioColumna + 1);
                procesadorPz.agregarToken("\"", "DELIMITADOR", inicioFila, columna);
                posicion++;
                columna++;

                return;
            }
            if (c == '\n') {
                break;
            }
            lexema = lexema + c;
            posicion++;
            columna++;
        }

        procesadorPz.agregarError(lexema, "Cadena sin cerrar", inicioFila, inicioColumna);
    }

    public void procesarNumero() {
        int inicioFila = fila;
        int inicioColumna = columna;
        String lexema = "";
        boolean esDecimal = false;

        while (posicion < contenido.length()) {
            char c = contenido.charAt(posicion);
            if (digito(c)) {
                lexema = lexema + c;
                posicion++;
                columna++;
            } else if (c == '.' && !esDecimal) {
                lexema = lexema + c;
                esDecimal = true;
                posicion++;
                columna++;
            } else {
                break;
            }
        }

        String tipo;
        if (esDecimal == true) {
            tipo = "LITERAL_DECIMAL";
        } else {
            tipo = "LITERAL_ENTERO";
        }
        procesadorPz.agregarToken(lexema, tipo, inicioFila, inicioColumna);
    }

    public void procesarIdentificador() {
        int inicioFila = fila;
        int inicioColumna = columna;
        String lexema = "";

        while (posicion < contenido.length()) {
            char c = contenido.charAt(posicion);
            if (letra(c) || digito(c) || c == '_') {
                lexema = lexema + c;
                posicion++;
                columna++;
            } else {
                break;
            }
        }

        String tipo = clasificarIdentificador(lexema);
        procesadorPz.agregarToken(lexema, tipo, inicioFila, inicioColumna);
    }

    public String clasificarIdentificador(String lexema) {
        if (lexema.equals("AGENTE") || lexema.equals("contexto")
                || lexema.equals("variable") || lexema.equals("EJECUTAR")
                || lexema.equals("EXPORTAR")) {
            return "PALABRA_RESERVADA";
        }
        if (lexema.equals("PREGUNTAR") || lexema.equals("GENERAR")
                || lexema.equals("RESUMIR") || lexema.equals("ANALIZAR")
                || lexema.equals("TRADUCIR") || lexema.equals("CLASIFICAR")
                || lexema.equals("EXTRAER")) {
            return "COMANDO_IA";
        }
        if (lexema.equals("SOBRE") || lexema.equals("DESDE")
                || lexema.equals("EN") || lexema.equals("COMO")) {
            return "CONECTOR";
        }
        if (lexema.equals("CARGAR")) {
            return "FUNCION";
        }
        return "IDENTIFICADOR";
    }

    public void procesarDirectiva() {
        int inicioFila = fila;
        int inicioColumna = columna;
        String lexema = "@";

        posicion++;
        columna++;

        while (posicion < contenido.length()) {
            char c = contenido.charAt(posicion);
            if (letra(c) || c == '-') {
                lexema = lexema + c;
                posicion++;
                columna++;
            } else {
                break;
            }
        }

        if (directivaValida(lexema)) {
            procesadorPz.agregarToken(lexema, "DIRECTIVA", inicioFila, inicioColumna);
        } else {
            procesadorPz.agregarError(lexema, "Directiva no es válida", inicioFila, inicioColumna);
        }
    }

    public boolean directivaValida(String lexema) {
        return lexema.equals("@modelo") || lexema.equals("@rol") || lexema.equals("@formato");
    }

    public void procesarFlecha() {
        int inicioFila = fila;
        int inicioColumna = columna;
        procesadorPz.agregarToken("->", "CONECTOR", inicioFila, inicioColumna);
        posicion = posicion + 2;
        columna = columna + 2;
    }

    public void procesarDelimitador(char c) {
        procesadorPz.agregarToken("" + c, "DELIMITADOR", fila, columna);
        posicion++;
        columna++;
    }

    public void procesarOperador(char c) {
        String tipo;
        if (c == '=') {
            tipo = "ASIGNACION";
        } else {
            tipo = "CONCATENACION";
        }
        procesadorPz.agregarToken("" + c, tipo, fila, columna);
        posicion++;
        columna++;
    }

    public void creraHtmlTokensCorrectos(String ruta) {
        archivo.crearArchivoHtml(ruta);
        archivo.htmlTokens("Tokens_correctos");
        Token[] correcto = procesadorPz.getTokens();
        for (int i = 0; i < correcto.length; i++) {
            Token token = correcto[i];
            if (token != null) {
                archivo.escribirHtml(token.getNumero(), token.getLexema(), token.getTipo(), token.getFila(), token.getColumna());
            }

        }
    }

    public void crearHtmlTokensIncorrectos(String ruta) {
        archivo.crearArchivoHtml(ruta);
        archivo.htmlTokens("Tokens_incorrectos");
        int contador = 0;
        Error[] errores = procesadorPz.getErrores();
        for (int i = 0; i < errores.length; i++) {
            Error error = errores[i];
            if (error != null) {
                archivo.escribirHtml(error.getNumero(), error.getLexema(), error.getTipoError(), error.getFila(), error.getColumna());
                contador++;
            }

        }
        if (contador == 0) {
            archivo.escribirHtml(0, "SIN ERRORES", "SIN ERRORES", 0, 0);
        }
    }

}
