/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Automata;

import java.util.ArrayList;


public class Siguientes {

    private String hoja;
    private String lexema;
    private ArrayList<String> siguientes;

    public Siguientes(String hoja,String lexema, ArrayList<String> siguientes) {
        this.hoja = hoja;
        this.lexema =lexema;
        this.siguientes = siguientes;
    }

    public String getHoja() {
        return hoja;
    }

    public void setHoja(String hoja) {
        this.hoja = hoja;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public ArrayList<String> getSiguientes() {
        return siguientes;
    }

    public void setSiguientes(ArrayList<String> siguientes) {
        this.siguientes = siguientes;
    }
}
