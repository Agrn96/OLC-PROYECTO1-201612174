/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Automata;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 *
 * @author herre
 */
public class Arbol {

    Nodo root;
    int cont;
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
        //System.out.println("________nuevo__________");
//        for (Siguientes ts : this.lista_tabla_sig) {
//            //System.out.print(ts.getHoja() + ": " + ts.getLexema() + " " + "->");
//            for (String t : ts.getSiguientes()) {
//             //   System.out.print(t + ",");
//            }
//           // System.out.println("\n_________________");
//        }

        //__________________________________________________________
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

        for (int j = 0; j < this.lista_siguienes.size() - 1; j++) {

            Siguientes l_s = this.lista_tabla_sig.get(j);
            String cadena2 = "";

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

    public void tabla_Transiciones() {

        //Esta lista almacena temporalmente los siguientes que tienen el mismo lexema
        ArrayList<ArrayList<String>> sig_repetidos = new ArrayList<>();
        //Esta lista almacena los sigueintes que no estan repetidos
        ArrayList<String[]> sig_normales = new ArrayList<>();

        //________________________________________________________
        this.estados_existentes.add(new Estado(0, this.sig_estadoIncial()));
        //____________obtener el estado inicial______________________
        this.transiciones.add(new Transiciones(new Estado(0, this.sig_estadoIncial()), null, null));
        int tamanio = this.transiciones.size();
        for (int i = 0; i < tamanio; i++) {
            //_
            System.out.println("i: " + i);

            Transiciones tr = this.transiciones.get(i);
            //__obtener los diguientes repetidos
            sig_repetidos = devolverRepetido(tr.getActual().getSigueintes());
            //_obtenr los isguietnes que no estan repetidos
            sig_normales = noRepetidos(tr.getActual().getSigueintes());
            if (i == 0) {

                //_________Union de los siguientes___________
                while (!sig_repetidos.isEmpty()) {
                    System.out.println("___Repetidos_________");
                    ArrayList<String> temporal_sig;
                    temporal_sig = this.unir_Siguientes(sig_repetidos.get(0));
                    sig_repetidos.remove(0);
                    Estado est = this.comparararSi_existeEstado(this.estados_existentes, temporal_sig);

                    this.transiciones.get(i).setLexema(est.getSigueintes().get(0)[1]);
                    this.transiciones.get(i).setNext(est);
                    this.transiciones.add(new Transiciones(est, null, null));
                    tamanio = this.transiciones.size();

                    //___obtener el estado________
                }
                //________obtener estado__________
                while (!sig_normales.isEmpty()) {

                    System.out.println("sig_normales " + sig_normales.size());

                    if (this.obtenerSiguienteNormal(sig_normales.get(0)) != null) {
                        ArrayList<Estado> est2 = this.estadoExistente2(this.estados_existentes, this.obtenerSiguienteNormal(sig_normales.get(0)));
                        for (Estado est : est2) {

                            this.transiciones.get(i).setActual(this.transiciones.get(i).getActual());
                            this.transiciones.get(i).setNext(est);
                            this.transiciones.add(new Transiciones(est, null, null));
                            sig_normales.remove(0);

                        }
                        tamanio = this.transiciones.size();
                    } else {
                        sig_normales.clear();
                    }
                }
                //____________________compara que el estado actual no sea igual al anterior______________
            } else if (this.transiciones.get(i).getActual().getId() != this.transiciones.get(i - 1).getActual().getId()) {
                System.out.println("_________________Resto de los estados___________");
                //_________Union de los siguientes___________
                while (!sig_repetidos.isEmpty()) {
                    ArrayList<String> temporal_sig;
                    temporal_sig = this.unir_Siguientes(sig_repetidos.get(0));
                    sig_repetidos.remove(0);
                    Estado est = this.comparararSi_existeEstado(this.estados_existentes, temporal_sig);

                    this.transiciones.get(i).setActual(this.transiciones.get(i).getActual());
                    //this.transiciones.get(i).setLexema(est.getSigueintes().get(i)[1]);
                    this.transiciones.get(i).setNext(est);
                    this.transiciones.add(new Transiciones(est, null, null));
                    //___obtener el estado________
                    tamanio = this.transiciones.size();
                }
                //________obtener estado__________
                System.out.println("_________los no repetidos__________");

                while (!sig_normales.isEmpty()) {
                    System.out.println("sig_normales " + sig_normales.size());

                    if (this.obtenerSiguienteNormal(sig_normales.get(0)) != null) {
                      
                        ArrayList<Estado> est2 = this.estadoExistente2(this.estados_existentes, this.obtenerSiguienteNormal(sig_normales.get(0)));
                        for (Estado est : est2) {

                            this.transiciones.get(i).setActual(this.transiciones.get(i).getActual());
                            this.transiciones.get(i).setNext(est);
                            this.transiciones.add(new Transiciones(est, null, null));
                            sig_normales.remove(0);

                        }

//                        System.out.println(est.getSigueintes().get(0)[1]);
                        //this.transiciones.get(i).setLexema(est.getSigueintes().get(i)[1]);
                        //System.out.println("size sig normales " + sig_normales.size());
                        tamanio = this.transiciones.size();
                    } else {
                        sig_normales.clear();
                    }

                }

            } else {
                i += 1;
            }

            //__________________________________
        }
        System.out.println("size " + tamanio);
        mostrarTabla_transiciones();

    }

    void mostrarTabla_transiciones() {

        System.out.println("_______Tabla de transiciones________-");

        for (Transiciones transicione : this.transiciones) {
            if (transicione.getActual() != null && transicione.getNext() != null) {
                System.out.println("Acutal " + transicione.getActual().getId() + " -> " + transicione.getLexema() + " -> " + transicione.getNext().getId());

            }

        }
        System.out.println("_____________Fin tabla________________");
        System.out.println("____________Estados existentes______________");
        for (Estado estados_existente : this.estados_existentes) {
            System.out.println("Estado: " + estados_existente.getId());
        }

        System.out.println("______siguients____________");

        for (Siguientes siguientes : lista_tabla_sig) {

            System.out.print(siguientes.getHoja() + "->" + siguientes.getLexema());
            for (String l : siguientes.getSiguientes()) {
                System.out.print(l);
            }
            System.out.println("");
        }
        System.out.println("_____Fin tabla de siguients_____");
    }

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

    //________compara si el siguiente esta en un estado que ya existe______
    Estado comparararSi_existeEstado(ArrayList<Estado> estados, ArrayList<String> siguientes) {
        Estado estado = null;
        int num = 0;
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<String> temp3 = new ArrayList<>();

        for (Estado estado1 : estados) {
            ArrayList<String> temp_lex = new ArrayList<>();
            for (String[] sig : estado1.getSigueintes()) {
                temp.add(sig[0]);
                temp_lex.add(sig[1]);
            }

            if (siguientes.equals(temp_lex)) {
                num = estado1.getId();
                estado = new Estado(estado1.getId(), estado1.getSigueintes());

                return estado;

            }

        }

        ArrayList<String[]> temp2 = new ArrayList<>();

        for (int i = 0; i < temp.size(); i++) {
            String dato[] = new String[2];
            dato[0] = temp.get(i);
            dato[1] = temp3.get(i);
            temp2.add(dato);
        }
        estado = new Estado((estados.size()), temp2);
        this.estados_existentes.add(estado);
        this.estados_nuevos.add(estado);

        return estado;
    }
//_____________________________________

    ArrayList<Estado> estadoExistente2(ArrayList<Estado> estados, ArrayList<String[]> siguientes) {
        
        //System.out.println("____-auim me quedo xd");
        System.out.println("_______Estado existente 2_______________");
        ArrayList<String> temp1 = new ArrayList<>();
        ArrayList<String> temp2 = new ArrayList<>();
        ArrayList<Estado> estados2 = new ArrayList<>();
        System.out.println("estados.size "+estados.size());
       
        for (Estado estado1 : estados) {
            System.out.println("_______Estado " + estado1.getId());

            ArrayList<String> temp3 = new ArrayList<>();
            for (String[] sig1 : estado1.getSigueintes()) {
                temp3.add(sig1[0]);
                System.out.println("_>>>>temp3>>>><" + sig1[0]);

            }
            ArrayList<String> temp4 = new ArrayList<>();
            for (String[] sig2 : siguientes) {

                for (Siguientes sig : this.lista_tabla_sig) {

                    if (sig2[0].equals(sig.getHoja())) {
                        temp4 = sig.getSiguientes();
                    }
                }
                Estado estado = null;
                if (temp3.equals(temp4)) {
                    System.out.println("______son iguales______" + true);

                    estado = estado1;
                    estados2.add(estado);
                } else {
                    estado = new Estado(this.estados_existentes.size(), siguientes);
                    System.out.println(estado.getSigueintes().get(0)[1]);
                     estados2.add(estado);
                    this.estados_existentes.add(estado);

                }

                System.out.println("_>>>>>temp2>>><" + sig2[0]);
            }

        }
        
        //this.estados_nuevos.add(estado);
        System.out.println("_____________Fin estado existente 2____________");
        return estados2;
    }
//_________fin    
}
