/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package olc1_proyecto1_201612174;

import Analizador.Parser;
import Analizador.lexico;
import Automata.Arbol;
import Principal.VentanaPrincipal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;


public class OLC1_PROYECTO1_201612174 {

    /**
     * @param args the command line arguments
     */
    public static ArrayList<Arbol> arboles= new ArrayList<>();
    public static void main(String[] args) {
        // TODO code application logic here
       
        VentanaPrincipal ven=new VentanaPrincipal();
        ven.setVisible(true);
     
        
    }

   public static String OpenArchivo(File file,String ruta) {
        String direccion=ruta;
        String cadena="";
        String cadena2="";
        try {
             file=new File(direccion);
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
