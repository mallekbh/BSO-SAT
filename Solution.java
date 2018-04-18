/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bso;

import java.util.Arrays;
import java.util.Vector;

/**
 *
 * @author mohamed amine ben hamida
 */
public class Solution {
    private int[] solution;
    private int SatClauses;
    private int diversity;
    
    public Solution(int numberOfLiterals) {
        solution = new int[numberOfLiterals];
        // initialise all values with 0
        for(int i=0;i<numberOfLiterals;i++) {
           this.solution[i] = 0; 
        }
    }   
    public Solution(int[] solution){
       this.solution = Arrays.copyOf(solution, solution.length);
    }
    public Solution(int[] solution , int satClauses){
        this.solution = Arrays.copyOf(solution, solution.length);
        this.SatClauses = satClauses;
    }
    public Solution(){
    }
    public int[] getSolution() {
        return solution;
    }
    public boolean isEqual(Solution sol) {
        boolean state = false;
        int cpt = 0;
        if(sol == null) return false;
        else {
            for(int i=0;i<sol.getSolution().length;i++) {
                if(this.getSolution()[i] == sol.getSolution()[i]) {
                    cpt++;
                }
            }
        }
        if(cpt == this.getSolution().length) state = true;
        return state;
    }
    public int getSolutionElement(int index) {
        return this.solution[index];
    }
    public void setSolution(int[] solution) {
        this.solution = Arrays.copyOf(solution, solution.length);
    }
    public void setSolutionElement(int index,int value){
        this.solution[index] = value;
    }
    public int getSatClauses() {
        return SatClauses;
    }
    public void setSatClauses(int SatClauses) {
        this.SatClauses = SatClauses;
    }
    public int getDiversity() {
        return diversity;
    }
    public void setDiversity(int diversity) {
        this.diversity = diversity;
    }
    public void showSolution(String msg) {
        System.out.print(msg+": "+this.getSatClauses()+" diversity : "+this.getDiversity()+" ");
        for(int e:this.getSolution()) {
            System.out.print(e);
        }
        System.out.println("");
    }
    
    public int distance(Solution sol2) {
        int distance =0;
        for(int i=0;i<this.getSolution().length;i++) {
            if(this.getSolutionElement(i) != sol2.getSolutionElement(i)) {
                distance++;
            }
        }
        return distance;
    }  
}
