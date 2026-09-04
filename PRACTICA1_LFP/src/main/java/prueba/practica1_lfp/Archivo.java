/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.practica1_lfp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author mario
 */
public class Archivo {

    Scanner scanner = new Scanner(System.in);
    private String archivo = "";
    private File file;
    private String ruta;

    public String leerArchivo(File archivoPz) {

        try {
            scanner = new Scanner(archivoPz);
            while (scanner.hasNextLine()) {
                archivo = archivo + scanner.nextLine() + "\n";

            }
        } catch (FileNotFoundException ex) {
            System.getLogger(Archivo.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return archivo;
    }

    public void crearArchivoHtml(String rutaCarpeta) {
        this.ruta = rutaCarpeta;

    }

    public void pzTexto(String nombre, String texto) {
        file = new File(ruta + "/" + nombre + ".pz");

        try {
            FileWriter writer = new FileWriter(file);

            writer.write(texto);
            writer.close();

        } catch (IOException ex) {
            System.getLogger(Archivo.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public void htmlTokens(String tokenCorrectoIncorrecto) {
        try {

            file = new File(ruta + "/" + tokenCorrectoIncorrecto + ".html");

            FileWriter writer = new FileWriter(file);

            writer.write("""
            <!DOCTYPE html>
            <html>
            <head>
                <title>TOKENS  </title>

                <style>
                    table {
                        border-collapse: collapse;
                        font-family: Arial;
                        width: 90%;
                    }

                    td, th {
                        border: 1px solid black;
                        padding: 8px;
                    }
                </style>
            </head>

            <body>

            <h1>TOKENS </h1>
                         

            <table>
                <tr>
                    <th>NUMERO</th>
                    <th>LEXEMA</th>
                    <th>TIPO</th>
                     <th>FILA</th>
                    <th>COLUMNA</th>
                </tr>
            """);

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void escribirHtml(int numero, String lexema, String tipo, int fila, int columna) {

        try (FileWriter writer = new FileWriter(file, true)) {

            writer.write(
                    "<tr>"
                    + "<td>" + numero + "</td>"
                    + "<td>" + lexema + "</td>"
                    + "<td>" + tipo + "</td>"
                    + "<td>" + fila + "</td>"
                    + "<td>" + columna + "</td>"
                    + "</tr>\n"
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
