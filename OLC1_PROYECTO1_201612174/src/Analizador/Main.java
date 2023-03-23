/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Analizador;

/**
 *
 * @author herre
 */
public class Main {

    public static void main(String[] args) {
        try {
            // Analisis lexico y poder utilizar Jflex
            String ruta = "src/Analizador/";
            String openJflex[] = {ruta + "lexico.jflex", "-d", ruta};
            jflex.Main.generate(openJflex);
            //Para utilizar el analizado sintatico Jcup
            String openCup[] = {"-destdir", ruta, "-parser", "Parser", ruta + "Parser.cup"};
            java_cup.Main.main(openCup);

        } catch (Exception e) {
            System.err.println("Error al acceder a lexico.jflex y parser.cup");
        }
        //System.out.println("");
    }
}
