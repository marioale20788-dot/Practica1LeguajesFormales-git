/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.practica1_lfp;

import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class Procesador {

    private ArrayList<Token> tokens = new ArrayList<>();
private ArrayList<Error> errores = new ArrayList<>();
    private int contadorTokens;
    private int contadorErrores;
    private int numeroToken;
    private int numeroError;

    public Procesador() {
 
        this.contadorTokens = 0;
        this.contadorErrores = 0;
        this.numeroToken = 0;
    }
  
    public void agregarToken(String lexema, String tipo, int fila, int columna) {
        numeroToken++;     
       String color = color(tipo);
          Token token = new Token(numeroToken, lexema, tipo, columna, fila,color);
       
        tokens.add(token);
        
     
    }

    public void agregarError(String lexema, String mensaje, int fila, int columna) {
        numeroError++;
        Error error= new Error(lexema, mensaje, fila, columna, numeroError);
        errores.add(error);
    
    }
    
    public  String color(String tipo){
          if (tipo.equalsIgnoreCase("PALABRA RESERVADA")) {
                return "#38BDF8";
            }
            if (tipo.equalsIgnoreCase("COMANDO IA")) {
               return "#A855F7";
            }
            if (tipo.equalsIgnoreCase("CONECTOR")) {
               return  "#3B82F6";
            }
            if (tipo.equalsIgnoreCase("FUNCION")) {
                return "#22C55E";
            }
            if (tipo.equalsIgnoreCase("DIRECTIVA")) {
                return "#14B8A6";
            }
            if (tipo.equalsIgnoreCase("DELIMITADOR")) {
                return "#94A3B8";
            }
            if (tipo.equalsIgnoreCase("LITERAL_CADENA")) {
               return "#F59E0B";
            }
            if (tipo.equalsIgnoreCase("LITERAL_DECIMAL")) {
                return "#10B981";
            }
            if (tipo.equalsIgnoreCase("LITERAL_ENTERO")) {
                 return "#10B981";
            }
  
            if (tipo.equalsIgnoreCase("OPERADOR_ASIGNACION")) {
                return "#EF4444";
            }
            if (tipo.equalsIgnoreCase("OPERADOR_CONCATENACION")) {
                 return "#EC4899";
            }
return "#EC4899";
    }
            

    public ArrayList<Token> getTokens() {

        return this.tokens;
    }

    public ArrayList<Error> getErrores() {

        return this.errores;
    }

    public int getNumTokens() {
        return numeroToken;
    }

    public int getNumErrores() {
        return numeroError;
    }
}
