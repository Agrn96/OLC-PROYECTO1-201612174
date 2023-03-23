/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Automata;

public class Nodo {

    private String object;
    private Nodo node_Left;
    private Nodo node_Right;
    String numLeft;
    String numRight;
    private boolean anulable;
    static int cor = 1;
    int id;

    public Nodo(Nodo node_Left, String object, Nodo node_Right) {
        this.object = object;
        this.node_Left = node_Left;
        this.node_Right = node_Right;
        this.numLeft = "";
        this.numRight = "";
        this.anulable = false;
        this.id = cor++;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Nodo getNode_Left() {
        return node_Left;
    }

    public void setNode_Left(Nodo node_Left) {
        this.node_Left = node_Left;
    }

    public Nodo getNode_Right() {
        return node_Right;
    }

    public void setNode_Right(Nodo node_Right) {
        this.node_Right = node_Right;
    }

    public String getNumLeft() {
        return numLeft;
    }

    public void setNumLeft(String numLeft) {
        this.numLeft = numLeft;
    }

    public String getNumRight() {
        return numRight;
    }

    public void setNumRight(String numRight) {
        this.numRight = numRight;
    }

    public boolean getAnulable() {
        return anulable;
    }

    public void setAnulable(boolean anulable) {
        this.anulable = anulable;
    }

    public String grafica() {

        String temp = "";
        temp = this.getObject().replaceFirst("\"", "");
        char comillas = temp.charAt(temp.length() - 1);
        if ("\"".equals(String.valueOf(comillas))) {
            temp = temp.substring(0, temp.length() - 1);
        }

        if (temp.equals("[")) {
            temp = "\\[";
        } else if (temp.equals("]")) {
            temp = "\\]";
        } else if (temp.equals("<")) {
            temp = "\\<";
        } else if (temp.equals(">")) {
            temp = "\\>";
        }
        if (temp.equals("{")) {
            temp = "\\{";
        }
        if (temp.equals("}")) {
            temp = "\\}";
        }

        temp = temp.replace("{", "");
        temp = temp.replace("}", "");

        String grafo;

        grafo = "nodo" + this.id + "[ label=\"<C0>" + this.numLeft + "|" + this.anulable + "\\n" + temp + "|<C1>" + this.numRight + "\"];\n";

        if (this.node_Left != null) {
            grafo = grafo + this.node_Left.grafica() + "nodo" + this.id + ":C0->nodo" + this.node_Left.id + "\n";
        }

        if (this.node_Right != null) {

            grafo = grafo + this.node_Right.grafica() + "nodo" + this.id + ":C0->nodo" + this.node_Right.id + "\n";

        }
        return grafo;

    }

}
