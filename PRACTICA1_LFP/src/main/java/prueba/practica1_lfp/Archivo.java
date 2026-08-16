/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.practica1_lfp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author mario
 */
public class Archivo {

    Scanner scanner = new Scanner(System.in);
    private String archivo = "";

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

}
