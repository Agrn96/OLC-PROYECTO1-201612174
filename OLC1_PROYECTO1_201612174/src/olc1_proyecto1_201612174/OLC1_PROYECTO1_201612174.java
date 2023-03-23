/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package olc1_proyecto1_201612174;

import Analizador.Parser;
import Analizador.lexico;
import Automata.Arbol;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 *
 * @author Alberto Gabriel Reyes Ning, 201612174
 */
public class OLC1_PROYECTO1_201612174 {

    /**
     * @param args the command line arguments
     */
    public static ArrayList<Arbol> arboles= new ArrayList<>();
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
        
        //______________
        int cont=0;
        for (Arbol arbol : arboles) {
            cont++;
            arbol.asignarAnulabilidad(arbol.rootArbol());
           String cadena2= arbol.getContenido(arbol.rootArbol());
            System.out.println("NOMBRE:_"+arbol.getName());
            
          // arbol.ObtenerSiguientes(arbol.rootArbol());
           arbol.crearTabla_siguientes();
           arbol.tabla_Transiciones();
           arbol.graficar("arbol"+String.valueOf(cont)+".jpg", "arbol"+String.valueOf(cont), cadena2);
           arbol.graficar("siguiente"+String.valueOf(cont)+".jpg","siguientes"+String.valueOf(cont), arbol.GraficarTabla());
           
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
