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
    private Vector<Integer> solution;
    private int SatClauses;
    
    public Solution(int numberOfLitterals) {
        for(int i=0;i<numberOfLitterals;i++) {
            
        }
    }   
    public void generateSearchArea() {
        
    }
    public Vector<Integer> getSolution() {
        return solution;
    }
    public void setSolution(Vector<Integer> solution) {
        this.solution = solution;
    }
    public void setSolutionElement(int index,int value){
        this.solution.add(index, new Integer(value));
    }
    public int getSatClauses() {
        return SatClauses;
    }
    public void setSatClauses(int SatClauses) {
        this.SatClauses = SatClauses;
    }
    
}
