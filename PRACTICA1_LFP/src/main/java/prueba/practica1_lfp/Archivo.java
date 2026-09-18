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

  public void crearReporteEstadisticas(String nombreArchivo, int totalLineas, int totalTokens, int totalErrores) {
    try {
        file = new File(ruta + "/" + nombreArchivo + ".html");
        FileWriter writer = new FileWriter(file);

        writer.write("""
        <!DOCTYPE html>
        <html lang="es">
        <head>
            <meta charset="UTF-8">
            <title>REPORTE DE ESTADÍSTICAS</title>
            <style>
                body {
                    background-color: #ffffff;
                    color: #1e293b;
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    padding: 30px;
                    margin: 0;
                }
                .card {
                    background: #ffffff;
                    padding: 25px;
                    border-radius: 6px;
                    width: 500px;
                    margin: 0 auto;
                    border: 1px solid #cbd5e1;
                    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
                }
                h2 {
                    color: #0f172a;
                    text-align: center;
                    font-size: 20px;
                    margin-top: 0;
                    margin-bottom: 20px;
                    text-transform: uppercase;
                    letter-spacing: 1px;
                }
                h3 {
                    color: #0f172a;
                    font-size: 15px;
                    margin-top: 20px;
                    margin-bottom: 12px;
                    text-transform: uppercase;
                }
                .resumen {
                    background: #f8fafc;
                    padding: 12px 16px;
                    border-radius: 6px;
                    border: 1px solid #e2e8f0;
                    margin-bottom: 20px;
                }
                .resumen p {
                    margin: 6px 0;
                    font-weight: 600;
                    font-size: 14px;
                }
                .error {
                    color: #ef4444;
                }
                table {
                    width: 100%;
                    border-collapse: collapse;
                    background-color: #ffffff;
                    border: 1px solid #cbd5e1;
                    border-radius: 6px;
                    overflow: hidden;
                }
                th {
                    background-color: #f1f5f9;
                    color: #0f172a;
                    padding: 12px;
                    text-align: left;
                    font-size: 13px;
                    border-bottom: 2px solid #cbd5e1;
                    text-transform: uppercase;
                }
                td {
                    border-bottom: 1px solid #e2e8f0;
                    padding: 10px 12px;
                    font-size: 14px;
                }
                tr:hover {
                    background-color: #f8fafc;
                }
            </style>
        </head>
        <body>

        <div class="card">
            <h2>Resumen Léxico</h2>
            
            <div class="resumen">
                <p>Total de Líneas: """ + totalLineas + """
                </p>
                <p>Total de Tokens: """ + totalTokens + """
                </p>
                <p class="error">Total de Errores: """ + totalErrores + """
                </p>
            </div>

            <h3>Frecuencia por Tipo de Token</h3>
            <table>
                <tr>
                    <th>TIPO DE TOKEN</th>
                    <th>CANTIDAD</th>
                </tr>
        """);

        writer.close();

    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public void escribirFrecuencia(String tipoToken, int cantidad) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(
                    "<tr>"
                    + "<td>" + tipoToken + "</td>"
                    + "<td>" + cantidad + "</td>"
                    + "</tr>\n"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

public void htmlTokens(String tokenCorrectoIncorrecto) {
    try {
        file = new File(ruta + "/" + tokenCorrectoIncorrecto + ".html");
        FileWriter writer = new FileWriter(file);

        writer.write("""
        <!DOCTYPE html>
        <html lang="es">
        <head>
            <meta charset="UTF-8">
            <title>TOKENS</title>
            <style>
                body {
                    background-color: #ffffff;
                    color: #1e293b;
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    padding: 30px;
                    margin: 0;
                }
                h1 {
                    color: #0f172a;
                    text-align: center;
                    font-size: 22px;
                    margin-bottom: 25px;
                    text-transform: uppercase;
                    letter-spacing: 1px;
                }
                table {
                    border-collapse: collapse;
                    width: 90%;
                    margin: 0 auto;
                    background-color: #ffffff;
                    border: 1px solid #cbd5e1;
                    border-radius: 6px;
                    overflow: hidden;
                    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
                }
                th {
                    background-color: #f1f5f9;
                    color: #0f172a;
                    padding: 12px;
                    text-align: left;
                    font-size: 13px;
                    border-bottom: 2px solid #cbd5e1;
                    text-transform: uppercase;
                }
                td {
                    border-bottom: 1px solid #e2e8f0;
                    padding: 10px 12px;
                    font-size: 14px;
                }
                td.lexema {
                    font-family: 'Consolas', 'Courier New', monospace;
                    font-weight: bold;
                }
                .badge-tipo {
                    padding: 4px 8px;
                    border-radius: 4px;
                    font-weight: bold;
                    font-size: 12px;
                    color: #ffffff;
                    display: inline-block;
                }
                tr:hover {
                    background-color: #f8fafc;
                }
            </style>
        </head>
        <body>

        <h1>REPORTE DE TOKENS</h1>

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
 public void escribirHtml(int numero, String lexema, String tipo, int fila, int columna, String color) {

    try (FileWriter writer = new FileWriter(file, true)) {

        writer.write(
            "<tr>"
            + "<td>" + numero + "</td>"
            + "<td class=\"lexema\"style=\"color: " + color + ";\">" + lexema + "</td>"
            + "<td><span class=\"badge-tipo\" style=\"background-color: " + color + ";\">" + tipo + "</span></td>"
            + "<td>" + fila + "</td>"
            + "<td>" + columna + "</td>"
            + "</tr>\n"
        );

    } catch (IOException e) {
        e.printStackTrace();
    }
    
 }

}
