/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Automata;

import java.util.ArrayList;

/**
 *
 * @author Alberto Gabriel Reyes Ning, 201612174
 */
public class Estado {

    private int id;
    private ArrayList<String[]> sigueintes;

    public Estado(int id, ArrayList<String[]> sigueintes) {
        this.id = id;
        this.sigueintes = sigueintes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String[]> getSigueintes() {
        return sigueintes;
    }

    public void setSigueintes(ArrayList<String[]> sigueintes) {
        this.sigueintes = sigueintes;
    }

}
