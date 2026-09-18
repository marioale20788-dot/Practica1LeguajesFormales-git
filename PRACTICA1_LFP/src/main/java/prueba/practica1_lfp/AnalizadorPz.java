/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.practica1_lfp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    private Archivo archivo;
    private Procesador procesadorPz;
    private HashMap consultor = new HashMap();
    private final HashMap<String, String> tablaReservadas = new HashMap<>();

    public AnalizadorPz() {

        this.posicion = 0;
        this.fila = 1;
        this.columna = 1;
        this.procesadorPz = new Procesador();
        archivo = new Archivo();

        tablaReservadas.put("AGENTE", "PALABRA RESERVADA");
        tablaReservadas.put("contexto", "PALABRA RESERVADA");
        tablaReservadas.put("variable", "PALABRA RESERVADA");
        tablaReservadas.put("EJECUTAR", "PALABRA RESERVADA");
        tablaReservadas.put("EXPORTAR", "PALABRA RESERVADA");
        tablaReservadas.put("CODIFICAR", "PALABRA RESERVADA");
        tablaReservadas.put("PREGUNTAR", "COMANDO IA");
        tablaReservadas.put("GENERAR", "COMANDO IA");
        tablaReservadas.put("RESUMIR", "COMANDO IA");
        tablaReservadas.put("ANALIZAR", "COMANDO IA");
        tablaReservadas.put("TRADUCIR", "COMANDO IA");
        tablaReservadas.put("CLASIFICAR", "COMANDO IA");
        tablaReservadas.put("EXTRAER", "COMANDO IA");
        tablaReservadas.put("SOBRE", "CONECTOR");
        tablaReservadas.put("DESDE", "CONECTOR");
        tablaReservadas.put("EN", "CONECTOR");
        tablaReservadas.put("COMO", "CONECTOR");
        tablaReservadas.put("CARGAR", "FUNCION");
        tablaReservadas.put("@modelo", "DIRECTIVA");
        tablaReservadas.put("@rol", "DIRECTIVA");
        tablaReservadas.put("@formato", "DIRECTIVA");

    }

    public String pasarTextoAVisualizador(File archivo) {
        this.contenido = this.archivo.leerArchivo(archivo);
        return contenido;
    }

    public String clasificarIdentificador(String lexema) {
        return tablaReservadas.getOrDefault(lexema, "IDENTIFICADOR");
    }

    public boolean directivaValida(String lexema) {
        return "DIRECTIVA".equals(tablaReservadas.get(lexema));
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

            if (c == '/') {
                procesarComentario();
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
                procesarCadenaCarcteres();
                continue;
            }

            if (c == '@') {
                procesarDirectiva();
                continue;
            }

            if (c == '-') {
                procesarFlecha();
                continue;
            }

            if (delimitador(c) == true) {
                procesadorPz.agregarToken("" + c, "DELIMITADOR", fila, columna);
                posicion++;
                columna++;
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

    public boolean delimitador(char c) {
        if (c == '{' || c == '}' || c == '(' || c == ')'|| c==',') {
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

    public void procesarCadenaCarcteres() {
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

    public void procesarDirectiva() {
        int inicioFila = fila;
        int inicioColumna = columna;
        StringBuilder lexema = new StringBuilder("@");

        posicion++;
        columna++;

        while (posicion < contenido.length()) {
            char c = contenido.charAt(posicion);
            if (letra(c)) {
                lexema.append(c);
                posicion++;
                columna++;
            } else {
                break;
            }
        }

        String lexemaResultante = lexema.toString();

        if (directivaValida(lexemaResultante)) {
            procesadorPz.agregarToken(lexemaResultante, "DIRECTIVA", inicioFila, inicioColumna);
        } else {
            procesadorPz.agregarError(lexemaResultante, "Directiva no es válida", inicioFila, inicioColumna);
        }
    }

    public void procesarFlecha() {
        int inicioFila = fila;
        int inicioColumna = columna;

        posicion++;
        columna++;

        if (posicion < contenido.length() && contenido.charAt(posicion) == '>') {
            posicion++;
            columna++;
            procesadorPz.agregarToken("->", "CONECTOR", inicioFila, inicioColumna);
        } else {

            procesadorPz.agregarError("-", "Simbolo no reconocido", inicioFila, inicioColumna);
        }
    }

    public void procesarComentario() {
        int inicioFila = fila;
        int inicioColumna = columna;

        posicion++;
        columna++;

        if (posicion < contenido.length()) {
            char siguiente = contenido.charAt(posicion);

            if (siguiente == '/') {
                posicion++;
                columna++;
                while (posicion < contenido.length() && contenido.charAt(posicion) != '\n') {
                    posicion++;
                    columna++;
                }
                return;
            }

            if (siguiente == '*') {
                posicion++;
                columna++;
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
                procesadorPz.agregarError("/*", "Comentario de bloque sin cerrar", inicioFila, inicioColumna);
                return;
            }
        }

        procesadorPz.agregarError("/", "Carácter no reconocido", inicioFila, inicioColumna);
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

        ArrayList<Token> correcto = procesadorPz.getTokens();
        for (Token token : correcto) {
            archivo.escribirHtml(token.getNumero(), token.getLexema(), token.getTipo(), token.getFila(), token.getColumna(),token.getColor());
        }

    }

    public void crearHtmlTokensIncorrectos(String ruta, String nombre) {
        archivo.crearArchivoHtml(ruta);
        archivo.htmlTokens(nombre);
        int contador = 0;
        ArrayList<Error> errores = procesadorPz.getErrores();
        for (Error error : errores) {
            archivo.escribirHtml(error.getNumero(), error.getLexema(), error.getTipoError(), error.getFila(), error.getColumna(),"#EF4444");
            contador++;
        }

        if (contador == 0) {
            archivo.escribirHtml(0, "SIN ERRORES", "SIN ERRORES", 0, 0,"#EF4444");
        }
    }

    public void crearPz(String ruta, String nombre, String texto) {
        archivo.crearArchivoHtml(ruta);

        archivo.pzTexto(nombre, texto);

    }

    public void crearRerporteEstadisticas(String ruta, String nombre) {
        int totalTokens = procesadorPz.getNumTokens();
        int TotalErrores = procesadorPz.getNumErrores();
        ArrayList<Token> correcto = procesadorPz.getTokens();
        int palabraReservada = 0;
        int comandoIa = 0;
        int conector = 0;
        int funcion = 0;
        int directiva = 0;
        int delimitador = 0;
        int literalCadena = 0;
        int literalDecimal = 0;
        int literalEntero = 0;
        int operadorAsignacion = 0;
        int operadorConcatenacion = 0;

        int filaCantidad = this.fila;

        archivo.crearArchivoHtml(ruta);
        archivo.crearReporteEstadisticas(nombre, totalTokens, totalTokens, TotalErrores);
        for (Token token : correcto) {
            if (token.getTipo().equalsIgnoreCase("PALABRA RESERVADA")) {
                palabraReservada++;
            }
            if (token.getTipo().equalsIgnoreCase("COMANDO IA")) {
                comandoIa++;
            }
            if (token.getTipo().equalsIgnoreCase("CONECTOR")) {
                conector++;
            }
            if (token.getTipo().equalsIgnoreCase("FUNCION")) {
                funcion++;
            }
            if (token.getTipo().equalsIgnoreCase("DIRECTIVA")) {
                directiva++;
            }
            if (token.getTipo().equalsIgnoreCase("DELIMITADOR")) {
                delimitador++;
            }
            if (token.getTipo().equalsIgnoreCase("LITERAL_CADENA")) {
                literalCadena++;
            }
            if (token.getTipo().equalsIgnoreCase("LITERAL_DECIMAL")) {
                literalDecimal++;
            }
            if (token.getTipo().equalsIgnoreCase("LITERAL_ENTERO")) {
                literalEntero++;
            }

            if (token.getTipo().equalsIgnoreCase("OPERADOR_ASIGNACION")) {
                operadorAsignacion++;
            }
            if (token.getTipo().equalsIgnoreCase("OPERADOR_CONCATENACION")) {
                operadorConcatenacion++;
            }

        }

        archivo.escribirFrecuencia("PALABRA RESERVADA", palabraReservada);
        archivo.escribirFrecuencia("COMANDO IA", comandoIa);
        archivo.escribirFrecuencia("CONECTOR", conector);
        archivo.escribirFrecuencia("FUNCION", funcion);
        archivo.escribirFrecuencia("DIRECTIVA", directiva);
        archivo.escribirFrecuencia("DELIMITADOR", delimitador);
        archivo.escribirFrecuencia("LITERAL_CADENA", literalCadena);
        archivo.escribirFrecuencia("LITERAL_DECIMAL", literalDecimal);
        archivo.escribirFrecuencia("LITERAL_ENTERO", literalEntero);
        archivo.escribirFrecuencia("OPERADOR_ASIGNACION", operadorAsignacion);
        archivo.escribirFrecuencia("OPERADOR_CONCATENACION", operadorConcatenacion);

    }

}
