/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Automata;


public class Transiciones {
    private Estado actual;
    private String lexema;
    private Estado next;

    public Transiciones(Estado actual, String lexema, Estado next) {
        this.actual = actual;
        this.lexema = lexema;
        this.next = next;
    }

    public Estado getActual() {
        return actual;
    }

    public void setActual(Estado actual) {
        this.actual = actual;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public Estado getNext() {
        return next;
    }

    public void setNext(Estado next) {
        this.next = next;
    }
}
