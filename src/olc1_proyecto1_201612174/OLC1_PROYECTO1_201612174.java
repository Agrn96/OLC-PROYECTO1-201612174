/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package olc1_proyecto1_201612174;

import Analizador.Parser;
import Analizador.lexico;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;

/**
 *
 * @author Alberto Gabriel Reyes Ning, 201612174
 */
public class OLC1_PROYECTO1_201612174 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            System.out.println("_______iniciar analisis__________");
            String cadena = "//soy un comentario \n //comentario 2";
            lexico lex = new lexico(new BufferedReader(new StringReader(OpenArchivo())));
            Parser analisar = new Parser(lex);
            analisar.parse();
        } catch (Exception e) {
            System.err.print("Error");
        }
    }

    static String OpenArchivo() {
        String direccion="C:\\Users\\herre\\Documents\\NetBeansProjects\\prueba.txt";
        String cadena="";
        String cadena2="";
        try {
            File file=new File(direccion);
            BufferedReader lectura= new BufferedReader(new FileReader(file));
            while((cadena=lectura.readLine())!=null){
                cadena2+=cadena+"\n";
            }
            lectura.close();
        } catch (Exception e) {
            System.err.println("No se pudo abrir el archivo");
        }
        return cadena2;
    }

}
