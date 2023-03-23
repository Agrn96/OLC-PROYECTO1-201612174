/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Automata;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Arbol {

    Nodo root;
    int cont;
    int contEst = 1;
    String name;
    //________listas__________
    ArrayList<Temp_siguietes> lista_siguienes = new ArrayList<>();
    ArrayList<Siguientes> lista_tabla_sig = new ArrayList<>();
    ArrayList<String[]> lista_numeros = new ArrayList<>();
    ArrayList<Transiciones> transiciones = new ArrayList<>();

    //Esta lista va a almacenar los estados nuevos que se vayan generando
    ArrayList<Estado> estados_nuevos = new ArrayList<>();
    // Esta lista almacenara lo estados que ya existen
    ArrayList<Estado> estados_existentes = new ArrayList<>();
    //
    ArrayList<String> listaSiguientes = new ArrayList<>();
    ArrayList<String> terminales = new ArrayList<>();
    ArrayList<String> terminalesNoRep = new ArrayList<>();
    ArrayList<Estado> lista_estados = new ArrayList<>();
    ArrayList<String[]> temporal = new ArrayList<>();
    // ArrayList
    ArrayList<String[]> transiciones2 = new ArrayList<>();
    ArrayList<String> estadoAceptacion = new ArrayList<>();
    private String nombreEstado = "";

    public Arbol() {
        root = null;
    }

    public Arbol(Nodo root) {
        this.root = root;
        this.name = "";
        //contador para enumerar las hojas
        preorder(root);
        this.cont = 0;

    }

    public Nodo rootArbol() {
        return root;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public boolean arbolVacio() {
        return this.root == null;
    }

    public Nodo crearArbol(Nodo node_Left, String object, Nodo node_Right) {

        return new Nodo(node_Left, object, node_Right);
    }

    public void preorder(Nodo node) {

        if (node != null) {

            if (!node.getObject().equals(".") && !node.getObject().equals("\\|") && !node.getObject().equals("*") && !node.getObject().equals("+") && !node.getObject().equals("?")) {
                //aumentar el contador
                this.cont++;
                //asignar el numero del lado izquierdo
                node.setNumLeft(String.valueOf(this.cont));
                //asignar el numero del lado drecho
                node.setNumRight(String.valueOf(this.cont));
                String numeros[] = new String[2];
                numeros[0] = String.valueOf(this.cont);
                numeros[1] = node.getObject();
                lista_numeros.add(numeros);
            }
            preorder(node.getNode_Left());
            preorder(node.getNode_Right());
        }
    }
    //________enumerar y colocar la anulabilidad____________

    public void asignarAnulabilidad(Nodo nodo) {

        if (nodo != null) {
            asignarAnulabilidad(nodo.getNode_Left());
            asignarAnulabilidad(nodo.getNode_Right());

            //_______concatenacion__________
            if (nodo.getObject().equals(".")) {

                //__________si ambos hijos son anulable(true) entonces se tomana ambos hijos____
                if (nodo.getNode_Left().getAnulable() == true && nodo.getNode_Right().getAnulable() == true) {
                    nodo.setAnulable(true);
                    nodo.setNumLeft(nodo.getNode_Left().getNumLeft() + "," + nodo.getNode_Right().getNumLeft());
                    nodo.setNumRight(nodo.getNode_Left().getNumRight() + "," + nodo.getNode_Right().getNumRight());

                    //_________si uno es anulable y el otro es no anulable.
                } else if (nodo.getNode_Left().getAnulable() == true && nodo.getNode_Right().getAnulable() == false) {
                    nodo.setAnulable(false);
                    nodo.setNumLeft(nodo.getNode_Left().getNumLeft() + "," + nodo.getNode_Right().getNumLeft());
                    nodo.setNumRight(nodo.getNode_Right().getNumRight());

                    //_____________si uno es no anulable y el otro es anulable    
                } else if (nodo.getNode_Left().getAnulable() == false && nodo.getNode_Right().getAnulable() == true) {
                    nodo.setAnulable(false);
                    nodo.setNumLeft(nodo.getNode_Left().getNumLeft());
                    nodo.setNumRight(nodo.getNode_Left().getNumRight() + "," + nodo.getNode_Right().getNumRight());
                } else if (nodo.getNode_Left().getAnulable() == false && nodo.getNode_Right().getAnulable() == false) {
                    nodo.setAnulable(false);
                    nodo.setNumLeft(nodo.getNode_Left().getNumLeft());
                    nodo.setNumRight(nodo.getNode_Right().getNumRight());
                }
                //____________para el or____________________
            } else if ("\\|".equals(nodo.getObject())) {
                if (nodo.getNode_Left().getAnulable() == true || nodo.getNode_Right().getAnulable() == true) {
                    nodo.setAnulable(true);
                } else {
                    nodo.setAnulable(false);
                }
                nodo.setNumLeft(nodo.getNode_Left().getNumLeft() + "," + nodo.getNode_Right().getNumLeft());
                nodo.setNumRight(nodo.getNode_Left().getNumRight() + "," + nodo.getNode_Right().getNumRight());

                //____-para la cerradura K__________
            } else if (nodo.getObject().equals("*")) {
                nodo.setAnulable(true);
                nodo.setNumLeft(nodo.getNode_Left().getNumLeft());
                nodo.setNumRight(nodo.getNode_Left().getNumRight());
                //_______para el ?_______________
            } else if (nodo.getObject().equals("?")) {
                nodo.setAnulable(true);
                nodo.setNumLeft(nodo.getNode_Left().getNumLeft());
                nodo.setNumRight(nodo.getNode_Left().getNumRight());

                //_______para la cerradura positiva________   
            } else if (nodo.getObject().equals("+")) {
                nodo.setAnulable(nodo.getNode_Left().getAnulable());
                nodo.setNumLeft(nodo.getNode_Left().getNumLeft());
                nodo.setNumRight(nodo.getNode_Left().getNumRight());

            }
        }
    }
//___________________________________________________________________________________

    public void graficar(String path, String name, String cadena) {

        FileWriter fw = null;
        PrintWriter writer;

        try {
            fw = new FileWriter(name + ".dot");
            writer = new PrintWriter(fw);
            writer.print(cadena);
        } catch (Exception e) {
            System.err.println("Error al intentar escribir el archivo (.dot)");
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (Exception e2) {
                System.err.println("Error al cerrar el archivo");
            }
        }
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec("dot -Tjpg -o " + path + " " + name + ".dot");
        } catch (Exception e) {
            System.err.println("Error al crear la imagen");
        }
    }

    public String getContenido(Nodo raiz) {
        return "digraph g{\n label=\"" + this.name + "\";"
                + "rankdir=TB;\n"
                + "node [shape=record, width=0.5, fontsize=12, fillcolor=seashell2, style=filled];\n"
                + raiz.grafica()
                + "}\n";

    }
//___________________________Tabla de siguientes_____________________

    void ObtenerSiguientes(Nodo node) {
        if (node != null) {
            ObtenerSiguientes(node.getNode_Left());
            ObtenerSiguientes(node.getNode_Right());

            if (node.getObject().equals(".")) {
                String i[] = node.getNode_Left().getNumRight().split(","); // a cada numero le corrresponde una lista de siguienes
                String siguientes[] = node.getNode_Right().getNumLeft().split(",");
                Temp_siguietes temp = new Temp_siguietes(i, siguientes);
                this.lista_siguienes.add(temp);
                // System.out.println("________siguientes_________");
                // System.out.println(node.getNode_Left().getNumRight() + "->" + node.getNode_Right().getNumLeft());

            } else if (node.getObject().equals("*")) {
                String i[] = node.getNode_Left().getNumRight().split(","); // a cada numero le corrresponde una lista de siguienes
                String siguientes[] = node.getNode_Left().getNumLeft().split(",");
                Temp_siguietes temp = new Temp_siguietes(i, siguientes);
                this.lista_siguienes.add(temp);

                //System.out.println("________siguientes_________");
                //System.out.println(node.getNode_Left().getNumRight() + "->" + node.getNode_Left().getNumLeft());
            } else if (node.getObject().equals("+")) {
                String i[] = node.getNode_Left().getNumRight().split(","); // a cada numero le corrresponde una lista de siguienes
                String siguientes[] = node.getNode_Left().getNumLeft().split(",");
                Temp_siguietes temp = new Temp_siguietes(i, siguientes);
                this.lista_siguienes.add(temp);
                // System.out.println("________siguientes_________");
                //System.out.println(node.getNode_Left().getNumRight() + "->" + node.getNode_Left().getNumLeft());

            }

        }
    }

    public void crearTabla_siguientes() {
        this.ObtenerSiguientes(root);
        ArrayList<String> temporal = quitarReptinos();

        temporal = (ArrayList<String>) temporal.stream().distinct().collect(Collectors.toList());
        temporal.sort(null);
        ArrayList<Siguientes> temporalS = new ArrayList<>();

        for (String[] tp1 : lista_numeros) {
            ArrayList<String> auxiliar = new ArrayList<>();
            for (Temp_siguietes t_s : this.lista_siguienes) {

                for (int i = 0; i < t_s.getHoja().length; i++) {
                    if (tp1[0].equals(t_s.getHoja()[i])) {
                        for (int j = 0; j < t_s.getSiguiente().length; j++) {
                            auxiliar.add(t_s.getSiguiente()[j]);
                        }
                    }

                }

            }
            Siguientes sig = new Siguientes(tp1[0], tp1[1], auxiliar);
            this.lista_tabla_sig.add(sig);
        }
        System.out.println("________nuevo__________");
        for (Siguientes ts : this.lista_tabla_sig) {
            if (ts.getLexema().equals("#")) {
                System.out.println("hola");
                ArrayList<String> t2 = new ArrayList<>();
                t2.add("");
                ts.setSiguientes(t2);
            }
            System.out.print(ts.getHoja() + ": " + ts.getLexema() + " " + "->");
            for (String t : ts.getSiguientes()) {
                System.out.print(t + ",");
            }
            System.out.println("\n_________________");
        }

    }

    ArrayList<String> quitarReptinos() {

        ArrayList<String> temporal = new ArrayList<>();
        for (Temp_siguietes sig : lista_siguienes) {

            for (int i = 0; i < sig.getHoja().length; i++) {

                temporal.add(sig.getHoja()[i]);
            }
        }
        temporal = (ArrayList<String>) temporal.stream().distinct().collect(Collectors.toList());
        temporal.sort(null);
        //______________oRIGINALES____________________________________________________________
//        System.out.println("_____Originales______________");
//        for (String ls : temporal) {
//            System.out.print(ls + "->");
//
//        }
//        System.out.println("\n_______________________");
        return temporal;
    }

    public String GraficarTabla() {

        String cadena = "";
        cadena += "digraph G{\nlabel=\"" + this.name + "\"; graph [pad=\"0.5\", nodesep=\"0.5\", ranksep=\"2\"];\n";
        cadena += "node[shape=plain]\n";
        cadena += "rankdir=LR;\n";
        cadena += "Foo [label=<\n";
        cadena += "<table border=\"0\" cellborder=\"1\" cellspacing=\"0\">\n";
        cadena += "<tr><td><i>Hoja</i></td> <td><i>Siguientes</i></td></tr>\n";

        for (int j = 0; j < this.lista_tabla_sig.size() - 1; j++) {

            Siguientes l_s = this.lista_tabla_sig.get(j);
            String cadena2 = "";
           
            String lexema = l_s.getLexema() ;
            lexema = lexema.replaceFirst("\"", "");
            char quitarComilla = lexema.charAt(lexema.length() - 1);
            if ("\"".equals(String.valueOf(quitarComilla))) {
                lexema = lexema.substring(0, lexema.length() - 1);
            }
            cadena += "<tr><td><i>" + l_s.getHoja() + " " + l_s.getLexema() + "</i></td>\n";

            for (int i = 0; i < l_s.getSiguientes().size(); i++) {

                if (i < (l_s.getSiguientes().size() - 1)) {
                    cadena2 += l_s.getSiguientes().get(i) + ",";
                } else {

                    cadena2 += l_s.getSiguientes().get(i);
                }
            }
            cadena += "<td><i>" + cadena2 + "</i></td>\n";
            cadena += "</tr>";
        }
        cadena += "<tr><td><i>" + this.lista_tabla_sig.get(this.lista_tabla_sig.size() - 1).getHoja() + " \"#\"</i></td><td><i> --- </i></td></tr>";

        cadena += "</table>>];}";
        //System.out.println(cadena);
        return cadena;
    }
//_________________________Transiciones________________________________--

//____________obtener el estado inicial____________________________
    ArrayList<String[]> sig_estadoIncial() {
        ArrayList<String[]> temporal = new ArrayList<>();

        String temp[] = this.root.getNumLeft().split(",");
        for (int i = 0; i < temp.length; i++) {

            String datos[] = new String[2];
            for (Siguientes s : this.lista_tabla_sig) {
                if (temp[i].equals(s.getHoja())) {

                    datos[0] = s.getHoja();
                    datos[1] = s.getLexema();
                    temporal.add(datos);
                }
            }
        }
        return temporal;
    }
//_____________________--metodo para devolver los siguientes que no estan repetidos________________________

    ArrayList<ArrayList<String>> devolverRepetido(ArrayList<String[]> original) {
        ArrayList<ArrayList<String>> temp = new ArrayList<>();
        ArrayList<String[]> temp2 = new ArrayList<>();
        for (int i = 0; i < original.size(); i++) {

            boolean ban = false;
            if (!original.get(i)[1].equals("")) {
                ArrayList<String> aux = new ArrayList<>();
                aux.add(original.get(i)[0]);
                for (int j = i + 1; j < original.size(); j++) {

                    if (original.get(i)[1].equals(original.get(j)[1])) {
                        original.get(j)[1] = "";
                        aux.add(original.get(j)[0]);
                        System.out.println("son iguales" + original.get(j)[0]);
                        ban = true;
                    }
                }

                if (aux.size() > 1) {

                    temp.add(aux);
                } else {
                    String array[] = new String[2];
                    array[0] = original.get(i)[0];
                    array[1] = original.get(i)[1];

                    temp2.add(array);
                }

            }
        }
        System.out.println("-temp esta vacia--" + temp.isEmpty());
        return temp;
    }
//___________________Metodo para devolver los siguientes que no estan repetidos______________-

    ArrayList<String[]> noRepetidos(ArrayList<String[]> original) {
        ArrayList<ArrayList<String>> temp = new ArrayList<>();
        ArrayList<String[]> temp2 = new ArrayList<>();
        for (int i = 0; i < original.size(); i++) {

            boolean ban = false;
            if (!original.get(i)[1].equals("")) {
                ArrayList<String> aux = new ArrayList<>();
                aux.add(original.get(i)[0]);
                for (int j = i + 1; j < original.size(); j++) {

                    if (original.get(i)[1].equals(original.get(j)[1])) {
                        original.get(j)[1] = "";
                        aux.add(original.get(j)[0]);
                        System.out.println("son iguales" + original.get(j)[0]);
                        ban = true;
                    }
                }

                if (aux.size() > 1) {

                    temp.add(aux);
                } else {
                    String array[] = new String[2];
                    array[0] = original.get(i)[0];
                    array[1] = original.get(i)[1];

                    temp2.add(array);
                }

            }
        }
        System.out.println("normales size " + temp2.size());
        System.out.println("temp2 Normales" + temp2.isEmpty());
        System.out.println("temp2 " + temp2.get(0)[0]);
        return temp2;
    }

    //____________-Unir los siguientes de los estados repetidos______________
    ArrayList<String> unir_Siguientes(ArrayList<String> original) {

        System.out.println("______Unir siguientws__________");
        ArrayList<String> temp = new ArrayList<>();
        int i = -1;
        int j = -1;
        int k = -1;
        int z = -1;
        for (String or : original) {

            for (Siguientes sig : this.lista_tabla_sig) {
                if (or.equals(sig.getHoja())) {
                    for (String sig2 : sig.getSiguientes()) {
                        temp.add(sig2);

//pendiente de revisar
                    }

                }

            }

        }
        temp = (ArrayList<String>) temp.stream().distinct().collect(Collectors.toList());
        return temp;
    }
//________obtener siguientes del estado__________________________

    ArrayList<String[]> obtenerSiguienteNormal(String[] normales) {
        System.out.println("______obtenerSiguienteNormal__________");
        ArrayList<String[]> temporal = new ArrayList<>();

        ArrayList<String> temporal2 = new ArrayList<>();

        for (Siguientes siguientes : lista_tabla_sig) {

            // System.out.println(n[0] + " :::" + siguientes.getHoja());
            // System.out.print(siguientes.getHoja() + "->" + siguientes.getLexema());
            for (String l : siguientes.getSiguientes()) {

                if (normales[0].equals(siguientes.getHoja()) && !normales[1].equals("#")) {
                    System.out.print(l);
                    temporal2.add(l);
                }

                if (normales[1].equals("#")) {
                    System.out.println("_____________salir__________");

                    return null;
                } else {
                }
                //System.out.println("");
            }
        }

        System.out.println("temp2_______" + temporal2.size());
        for (String t2 : temporal2) {

            for (Siguientes sig : this.lista_tabla_sig) {

                if (t2.equals(sig.getHoja())) {
                    String dato[] = new String[2];
                    dato[0] = sig.getHoja();
                    dato[1] = sig.getLexema();
                    System.out.println("getHoja " + sig.getHoja());
                    temporal.add(dato);
                }
            }
        }

        return temporal;
    }

//_________fin    
    public void crearPrimerEstado() {

        Estado inicial = new Estado("S0");
        String primeros[] = this.root.getNumLeft().split(",");
        for (String p : primeros) {

            inicial.setNumero(Integer.parseInt(p));
        }
        System.out.println("inicial size" + String.valueOf(inicial.getNumbers().size()));
        this.lista_estados.add(inicial);
    }

    private Siguientes verificarSiguiente(String sig) {

        for (Siguientes siguientes : lista_tabla_sig) {

            return siguientes;
        }

        return null;
    }

    public void crearEstado() {
        int tam = this.lista_estados.size();
        // System.out.println("lista_estado : " + String.valueOf(tam));
        int contDato = 0;
        // System.out.println(contDato);
        //System.out.println("-->");
        //System.out.println(this.lista_estados.get(contDato).getName());
        while (contDato < tam) {
            // System.out.println(this.lista_estados.get(0).getName());
            String nameEstado = this.lista_estados.get(contDato).getName();//Name estado actual
            //System.out.println("__tam_" + String.valueOf(this.lista_estados.get(contDato).getName()));
            ArrayList<Integer> numbers = this.lista_estados.get(contDato).getNumbers();

            ArrayList<String[]> terminales = new ArrayList<>();
            //_________________________________________
            for (int i = 0; i < numbers.size(); i++) {
                String t[] = obtenerSiguiente(numbers.get(i));

                if (t != null) {
                    terminales.add(t);
                }
            }
            //_________________________________

            for (int i = 0; i < terminales.size(); i++) {
                if (temporal.isEmpty()) {
                    temporal.add(terminales.get(i));
                } else {
                    //System.out.println("________verfi_______");
                    verificacionTemp(terminales.get(i));
                }
            }
            //___________________________

            for (String[] temp1 : temporal) {

                //System.out.println("temp1___-s" + String.valueOf(temp1.length));
                if (!"".equals(temp1[2])) {
                    ArrayList<Integer> datos = new ArrayList<>();
                    String[] dato = temp1[2].split(",");
                    // System.out.println(temp1[2]);
                    // System.out.println("dato--"+dato.length);
                    for (int i = 0; i < dato.length; i++) {
                        //  System.out.println("dato: "+dato[i]);
                        datos.add(Integer.parseInt(dato[i]));
                    }
                    if (!existeEstado(datos)) {
                        Estado nuevo = new Estado("S" + String.valueOf(contEst));
                        nuevo.setNumbers(datos);
                        this.lista_estados.add(nuevo);
                        this.contEst++;
                        tam++;
                        String[] trasicion = {nameEstado, temp1[1], nuevo.getName()};
                        this.transiciones2.add(trasicion);
                    } else {
                        String[] transicion = {nameEstado, temp1[1], nombreEstado};
                        transiciones2.add(transicion);
                        nombreEstado = "";
                    }
                }
            }
            this.temporal.clear();
            contDato++;
        }
        //___________________________________________

    }

    private void verificacionTemp(String[] simb) {
        //System.out.println("Simb_______" + String.valueOf(simb[0]));
        boolean flag = false;
        for (String[] temp : this.temporal) {
            if (simb[1].equals(temp[1])) {
                // System.out.println("------esta aqui-------");
                String[] numSimbolos = simb[2].split(",");
                String[] numTemp = temp[2].split(",");
                ArrayList<Integer> numberTotales = new ArrayList<>();

                for (int i = 0; i < numSimbolos.length; i++) {
                    numberTotales.add(Integer.parseInt(numSimbolos[i]));
                }
                for (int i = 0; i < numSimbolos.length; i++) {
                    numberTotales.add(Integer.parseInt(numTemp[i]));
                }

                Set<Integer> hashset = new HashSet(numberTotales);
                numberTotales.clear();
                numberTotales.addAll(hashset);
                Collections.sort(numberTotales);

                String noRepetidos = "";
                int cont = 0;
                for (Integer num : numberTotales) {
                    if (cont < (numberTotales.size() - 1)) {
                        noRepetidos += num + ",";
                    } else {
                        noRepetidos += num;
                    }
                    cont++;
                }
                temp[2] = noRepetidos;
                flag = true;
            }

        }
        // System.out.println("Simb_______" + String.valueOf(simb[0]));
        if (!flag) {
            temporal.add(simb);
        }
    }

    private boolean existeEstado(ArrayList<Integer> numbers) {
        boolean flag = false;
        for (Estado estado : this.lista_estados) {
            ArrayList<Integer> numerosEstao = estado.getNumbers();
            int sizeAnterior = numerosEstao.size();
            int sizeActual = numbers.size();
            int comparar = 0;
            if (sizeAnterior == sizeActual) {

                for (Integer anterior : numerosEstao) {
                    for (Integer number : numbers) {
                        if (anterior.equals(number)) {
                            comparar++;
                        }
                    }
                }
            }
            if (comparar == sizeActual) {
                flag = true;
                this.nombreEstado = estado.getName();
            }

        }
        return flag;
    }

    private String[] obtenerSiguiente(int simb) {

        for (Siguientes siguiente : lista_tabla_sig) {
//            System.out.println("get hoja "+siguiente.getLexema());
            if (siguiente.getHoja().equals(String.valueOf(simb))) {
//                System.out.println("-Simbolo-" + String.valueOf(simb));
//                System.out.println("-hoja-" + siguiente.getHoja());

                String temp = "";
                for (int i = 0; i < siguiente.getSiguientes().size(); i++) {
                    if (i < (siguiente.getSiguientes().size() - 1)) {
                        temp += siguiente.getSiguientes().get(i) + ",";
                    } else {
                        temp += siguiente.getSiguientes().get(i);
                    }
                }
//                if (siguiente.getLexema().equals("#")) {
//                    System.out.println("###########");
//                }
//                System.out.println("_____fin obtener siguiente________");
                String temp3[] = {siguiente.getHoja(), siguiente.getLexema(), temp};
                return temp3;
            }
        }
        System.out.println("_________aqui");
        return null;
    }

    public void mostrarTransiicones() {

        for (String[] tr : transiciones2) {

            System.out.print(tr[0] + "-->");
            System.out.print(tr[1] + "-->");
            System.out.print(tr[2] + "\n");
        }
    }

    public void asignarEstado_aceptacion() {

        for (Estado estado : this.lista_estados) {

            ArrayList<Integer> num = estado.getNumbers();
            for (Integer i : num) {
                if (String.valueOf(i).equals(this.root.getNode_Right().numLeft)) {
                    System.out.println("estado aceptacion " + estado.getName());
                    estadoAceptacion.add(estado.getName());
                    break;
                }
            }
        }
    }

    //_____________Graficar tabla de transicione
    public String GraficarTranciones() {

        String cadena = "";
        cadena += "digraph G{\nlabel=\"" + this.name + "\"; graph [pad=\"0.5\", nodesep=\"0.5\", ranksep=\"2\"];\n";
        cadena += "node[shape=plain]\n";
        cadena += "rankdir=LR;\n";
        cadena += "Foo [label=<\n";
        cadena += "<table border=\"0\" cellborder=\"1\" cellspacing=\"0\">\n";
        cadena += "<tr><td><i>Estado</i></td><td><i>Lexema</i></td> <td><i>Transicion</i></td></tr>\n";

        for (String[] transicion : transiciones2) {
            String lexema = transicion[1];
            lexema = lexema.replaceFirst("\"", "");
            char quitarComilla = lexema.charAt(lexema.length() - 1);
            if ("\"".equals(String.valueOf(quitarComilla))) {
                lexema = lexema.substring(0, lexema.length() - 1);
            }
            cadena += "<tr><td>" + transicion[0] + "</td><td>" + lexema + "</td><td>" + transicion[2] + "</td></tr>";
        }
        // cadena += "<tr><td><i>" + this.lista_tabla_sig.get(this.lista_tabla_sig.size() - 1).getHoja() + " \"#\"</i></td><td><i> --- </i></td></tr>";

        cadena += "</table>>];}";
        //System.out.println(cadena);
        return cadena;
    }

    public String GenerarAFD() {
        String etiqueta = "digraph g{\n label=\"" + this.name + "\";\n";
        etiqueta += "rankdir=LR;\n";
        etiqueta += "node[shape=oval,width=0.5,fontsize=12,fillcolor=seashell2,style=filled];\n";
        String nodos = "";

        for (Estado estado : lista_estados) {
            nodos += estado.getName() + "[label=\"" + estado.getName() + "\"];\n";
            for (String estadoA : estadoAceptacion) {
                if (estadoA.equals(estado.getName())) {

                    nodos += estado.getName() + "[shape=doublecircle, label=\"" + estado.getName() + "\"];\n";
                }
            }
        }

        for (String[] transicion : this.transiciones2) {

            String lexema = transicion[1];
            lexema = lexema.replaceFirst("\"", "");
            char quitarComilla = lexema.charAt(lexema.length() - 1);
            if ("\"".equals(String.valueOf(quitarComilla))) {
                lexema = lexema.substring(0, lexema.length() - 1);
            }
            nodos += transicion[0] + "->" + transicion[2] + "[label=\"" + lexema + "\"];\n";
        }
        etiqueta += nodos;
        etiqueta += "\n}";
        return etiqueta;
    }

}
