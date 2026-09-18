/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.practica1_lfp;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mario
 */
public class MotorAnalizador {

    private AnalizadorPz analizador;

    private FramePrincipal principalFrame = new FramePrincipal();

    public void inciarAnalizadorLexico() {
        principalFrame.setVisible(true);
        principalFrame.setMotor(this);
        analizador = new AnalizadorPz();

    }

    public void cargarArchivo() {
        File archivoDirecto = principalFrame.getArchivo();
        String texto = analizador.pasarTextoAVisualizador(archivoDirecto);
        principalFrame.getTxtAreaTexto().setText(texto);

    }

    public void analizarArchivo() {

        try {
            String texto = principalFrame.getTxtAreaTexto().getText();
            analizador = new AnalizadorPz();
            analizador.analizar(texto);
            cargaTablaTokensCorrectos();
            cargarTablaTokensIncorrectos();
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(principalFrame, "no se pudo hacer esta accion");
        }

    }

    public void cargaTablaTokensCorrectos() {
        DefaultTableModel modeloTabla = (DefaultTableModel) principalFrame.getTableTokensCorrectos().getModel();
       ArrayList <Token>tokenscorrectos= analizador.getProcesadorPz().getTokens();
    
        modeloTabla.setRowCount(0);

        for (Token tokenscorrecto : tokenscorrectos) {
            modeloTabla.addRow(new Object[]{
                    tokenscorrecto.getNumero(),
                    tokenscorrecto.getLexema(),
                    tokenscorrecto.getTipo(),
                    tokenscorrecto.getFila(),
                    tokenscorrecto.getColumna()
                });
        }
    

    }

    public void cargarTablaTokensIncorrectos() {
        DefaultTableModel modeloTabla = (DefaultTableModel) principalFrame.getTableTokensIncorrectos().getModel();
         ArrayList <Error>tokenscorrectos= analizador.getProcesadorPz().getErrores();

        modeloTabla.setRowCount(0);
        for (Error tokenError : tokenscorrectos) {
               modeloTabla.addRow(new Object[]{
                    tokenError.getNumero(),
                    tokenError.getLexema(),
                    tokenError.getTipoError(),
                    tokenError.getFila(),
                    tokenError.getColumna()
                });
        }
        
 

    }
    public void generarImagen(){
         String ruta = principalFrame.getDireccionHtml();
         GeneradorGrafico generador = new GeneradorGrafico();
         generador.exportarImagen(ruta);
    }

    public void crearHtmlTokensCorrectos(String nombre) {
        String ruta = principalFrame.getDireccionHtml();
        analizador.creraHtmlTokensCorrectos(ruta, nombre);
    }

    public void crearHtmlTokensIncorrectos(String nombre) {
        String ruta = principalFrame.getDireccionHtml();

        analizador.crearHtmlTokensIncorrectos(ruta, nombre);

    }
    public void crearEstadisitca(String nombre){
          String ruta = principalFrame.getDireccionHtml();
          analizador.crearRerporteEstadisticas(ruta, nombre);
    }

    public void crearPz(String nombre) {
        String ruta = principalFrame.getDireccionHtml();
        String texto = principalFrame.getTxtAreaTexto().getText();
        analizador.crearPz(ruta, nombre, texto);

    }

}
