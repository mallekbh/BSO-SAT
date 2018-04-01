/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bso;

import java.util.Vector;

/**
 *
 * @author mohamed amine ben hamida
 */
public class Solution {
    private int[] solution;
    private int SatClauses;
    
    public Solution(int numberOfLitterals) {
        // initialise all values with 0
        for(int i=0;i<numberOfLitterals;i++) {
           this.solution[i] = 0; 
        }
    }   
    public Solution(int[] solution){
        this.solution = solution;
    }
    public Vector<Solution> generateSearchArea(int Flip) {
        Vector<Solution> SearchArea = new Vector<Solution>();
        
        
        
        return SearchArea;
    }
    public int[] getSolution() {
        return solution;
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

}
