/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.practica1_lfp;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author mario
 */
public class AnalizadorPz {

    private String contenido = "";
    private int posicion;
    private int fila;
    private int columna;
    private Archivo archivo = new Archivo();
    private Procesador procesadorPz;
    private HashMap consultor = new HashMap();

    public AnalizadorPz() {

        this.posicion = 0;
        this.fila = 1;
        this.columna = 1;
        this.procesadorPz = new Procesador();

    }

    public String pasarTextoAVisualizador(File archivo) {
        this.contenido = this.archivo.leerArchivo(archivo);
        return contenido;
    }

    public Procesador getProcesadorPz() {
        return procesadorPz;
    }

    public String getContenido() {
        return contenido;
    }

    public void analizar(String texto) {
        this.contenido = texto;
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
        if (c == '{' || c == '}' || c == '(' || c == ')' || c == ',' || c == ';') {
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
        posicion++;
        columna++;

        boolean cadenaValida = false;
        boolean error = false;

        while (posicion < contenido.length() && cadenaValida == false && error == false) {
            char c = contenido.charAt(posicion);
            if (c == '"') {
                cadenaValida = true;
                posicion++;
                columna++;
            } else if (c == '\n') {
                error = true;
            } else {
                lexema = lexema + c;
                posicion++;
                columna++;
            }
        }
        if (cadenaValida == false && error == false) {
            error = true;
        }

        if (cadenaValida == true) {
            procesadorPz.agregarToken(lexema, "LITERAL_CADENA", inicioFila, inicioColumna + 1);
        } else {
            procesadorPz.agregarError(lexema, "Cadena sin cerrar", inicioFila, inicioColumna);
        }
    }

    public void procesarNumero() {
        int inicioFila = fila;
        int inicioColumna = columna;
        String lexema = "";

        while (posicion < contenido.length() && digito(contenido.charAt(posicion))) {
            lexema = lexema + contenido.charAt(posicion);
            posicion++;
            columna++;
        }

        if (posicion < contenido.length() && contenido.charAt(posicion) == '.') {
            lexema = lexema + contenido.charAt(posicion);
            posicion++;
            columna++;

            if (posicion < contenido.length() && digito(contenido.charAt(posicion))) {

                while (posicion < contenido.length() && digito(contenido.charAt(posicion))) {
                    lexema = lexema + contenido.charAt(posicion);
                    posicion++;
                    columna++;
                }

                procesadorPz.agregarToken(lexema, "LITERAL_DECIMAL", inicioFila, inicioColumna);
                return;
            } else {

                procesadorPz.agregarError(lexema, "Número decimal inválido (falta parte decimal)", inicioFila, inicioColumna);
                return;
            }

        }

        procesadorPz.agregarToken(lexema, "LITERAL_ENTERO", inicioFila, inicioColumna);
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
                || lexema.equals("EXPORTAR") || lexema.equals("CODIFICAR")) {
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
        char c = contenido.charAt(posicion);
        String lexema = "";
        if (c == '-') {
            lexema = lexema + c;
            posicion = posicion + 1;
            columna = columna + 1;
             c = contenido.charAt(posicion);
        }
        if (c == '>') {
            lexema = lexema + c;
            posicion = posicion + 1;
            columna = columna + 1;
        }
        procesadorPz.agregarToken(lexema, "CONECTOR", inicioFila, inicioColumna);

    }

    public void procesarDelimitador(char c) {
       
        procesadorPz.agregarToken("" + c, "DELIMITADOR", fila, columna);
        posicion++;
        columna++;
    }

    public void procesarOperador(char c) {
        String tipo;
        if (c == '=') {
            tipo = "OPERADOR_ASIGNACION";
        } else {
            tipo = "OPERADOR_CONCATENACION";
        }
        procesadorPz.agregarToken("" + c, tipo, fila, columna);
        posicion++;
        columna++;
    }

    public void creraHtmlTokensCorrectos(String ruta, String nombre) {
        archivo.crearArchivoHtml(ruta);
        archivo.htmlTokens(nombre);
        Token[] correcto = procesadorPz.getTokens();
        for (int i = 0; i < correcto.length; i++) {
            Token token = correcto[i];
            if (token != null) {
                archivo.escribirHtml(token.getNumero(), token.getLexema(), token.getTipo(), token.getFila(), token.getColumna());
            }

        }
    }

    public void crearHtmlTokensIncorrectos(String ruta, String nombre) {
        archivo.crearArchivoHtml(ruta);
        archivo.htmlTokens(nombre);
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

    public void crearPz(String ruta, String nombre, String texto) {
        archivo.crearArchivoHtml(ruta);

        archivo.pzTexto(nombre, texto);

    }

}
