/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.practica1_lfp;


import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


/**
 *
 * @author mario
 */
public class GeneradorGrafico {

    public String generarArchivo() {
        String codigoDOT = """
            digraph AfdpPrompzal {
                rankdir=LR;
                node [shape = circle, fontname = "Helvetica"];

                node [shape = point]; inicio;

                node [shape = doublecircle];
                q7, q10, q17, q18, q20, q22, q23, q25, q26, q27, q30, q33;
                 node [shape = circle];

                inicio -> q0;

                q0 -> q1 [label = "@"];
                q1 -> q2 [label = "m"]; q2 -> q3 [label = "o"]; q3 -> q4 [label = "d"];
                q4 -> q5 [label = "e"]; q5 -> q6 [label = "l"]; q6 -> q7 [label = "o"];

                q1 -> q8 [label = "r"]; q8 -> q9 [label = "o"]; q9 -> q10 [label = "l"];

                q1 -> q11 [label = "f"]; q11 -> q12 [label = "o"]; q12 -> q13 [label = "r"];
                q13 -> q14 [label = "m"]; q14 -> q15 [label = "a"]; q15 -> q16 [label = "t"];
                q16 -> q17 [label = "o"];

                q0 -> q18 [label = "_[a-zA-Z]"];
                q18 -> q18 [label = "_[a-zA-Z0-9]"];

                q0 -> q19 [label = "-"];
                q19 -> q20 [label = ">"];

                q0 -> q21 [label = "\\\""];
                q21 -> q21 [label = "[^\\\"]"];
                q21 -> q22 [label = "\\\""];

                q0 -> q23 [label = "[0-9]"];
                q23 -> q23 [label = "[0-9]"];
                q23 -> q24 [label = "."];
                q24 -> q25 [label = "[0-9]"];
                q25 -> q25 [label = "[0-9]"];

                q0 -> q26 [label = "= +"];
                q0 -> q27 [label = "{ } ( )"];

                q0 -> q28 [label = "/"];
                q28 -> q29 [label = "/"];
               q29 -> q29 [label = "[^\n]"];
                q29 -> q30 [label = "[ salto de linea \n]"];
                q28 -> q31 [label = "*"];
                q31 -> q31 [label = "[^*]"];
                q31 -> q32 [label = "*"];
                q32 -> q33 [label = "/"];
     
            }
            """;

        return codigoDOT;

    }

    public void exportarImagen(String rutaSalidaPng) {
        try {
            File destino = new File(rutaSalidaPng);
            if (destino.isDirectory()) {
                destino = new File(destino, "automata.png");
            }

            String codigoDot = generarArchivo();

            String urlCodificada = "https://quickchart.io/graphviz?graph="
                    + URLEncoder.encode(codigoDot, StandardCharsets.UTF_8);

            URL url = new URL(urlCodificada);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");

            if (conexion.getResponseCode() == 200) {

                try (InputStream in = conexion.getInputStream(); OutputStream out = Files.newOutputStream(destino.toPath())) {

                    byte[] buffer = new byte[4096];
                    int bytesLeidos;
                    while ((bytesLeidos = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesLeidos);
                    }
                }

            }

        } catch (Exception e) {

        }
    }
}
