/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.practica1_lfp;

import java.io.File;
import java.io.IOException;
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
    }

    public void cargarArchivo() {
        File archivoDirecto = principalFrame.getArchivo();

        try {
            analizador = new AnalizadorPz(archivoDirecto);
            analizador.analizar();
        } catch (IOException ex) {
            System.getLogger(MotorAnalizador.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        cargaTablaTokensCorrectos();
        cargarTablaTokensIncorrectos();

    }

    public void cargaTablaTokensCorrectos() {
        DefaultTableModel modeloTabla = (DefaultTableModel) principalFrame.getTableTokensCorrectos().getModel();
        Token[] tokensCorrectos = analizador.getProcesadorPz().getTokens();
        modeloTabla.setRowCount(0);
        for (int i = 0; i < tokensCorrectos.length; i++) {
            Token tokensCorrecto = tokensCorrectos[i];
            if (tokensCorrecto != null) {
                modeloTabla.addRow(new Object[]{
                    tokensCorrecto.getNumero(),
                    tokensCorrecto.getLexema(),
                    tokensCorrecto.getTipo(),
                    tokensCorrecto.getFila(),
                    tokensCorrecto.getColumna()
                });
            }

        }

    }

    public void cargarTablaTokensIncorrectos() {
        DefaultTableModel modeloTabla = (DefaultTableModel) principalFrame.getTableTokensIncorrectos().getModel();
        Error[] erroresTokens = analizador.getProcesadorPz().getErrores();
        modeloTabla.setRowCount(0);
        for (int i = 0; i < erroresTokens.length; i++) {
            Error tokenError = erroresTokens[i];
            if (tokenError != null) {
                modeloTabla.addRow(new Object[]{
                    tokenError.getNumero(),
                    tokenError.getLexema(),
                    tokenError.getTipoError(),
                    tokenError.getFila(),
                    tokenError.getColumna()
                });
            }

        }

    }

}
