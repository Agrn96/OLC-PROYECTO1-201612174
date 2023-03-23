/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Automata;

import java.util.ArrayList;
import java.util.Collections;


public class Estado {

   private String name;
   private ArrayList<Integer> datos=new ArrayList<>();

    public Estado(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public void setNumero(int number){
        if (!this.datos.isEmpty()) {
            if (!existente(number)) {
                this.datos.add(number);
                Collections.sort(this.datos);
            }
        }else{
            this.datos.add(number);
        }
    }
    
   
   
    public void setNumbers(ArrayList<Integer> numbers){
        this.datos=numbers;
    }
    
   public ArrayList<Integer> getNumbers(){
       return this.datos;
   }
   
   public boolean existente(int number){
       
       for (Integer dato : datos) {
           if (dato==number) {
               return true;
           }
       }
       return false;
   }
}
