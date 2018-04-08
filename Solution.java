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
    
    public Solution(int numberOfLitterals) {
        // initialise all values with 0
        for(int i=0;i<numberOfLitterals;i++) {
           this.solution[i] = 0; 
        }
    }   
    public Solution(int[] solution){
       this.solution = Arrays.copyOf(solution, solution.length);
    }
    public Solution(){
    }
    public int[] getSolution() {
        return solution;
    }
    public int getSolutionElement(int index) {
        return this.solution[index];
    }
    public void setSolution(int[] solution) {
        this.solution = solution;
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
    
}
