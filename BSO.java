/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bso;

import java.io.File;

/**
 *
 * @author mohamed amine ben hamida
 */
public class BSO {

    private int MaxIter = 0;
    private int NumBees = 0;
    private int MaxChange = 0;
    private int LocalIter = 0;

    public void search(File file) {
        
    }  
    public BSO(int MaxIter, int NumBees,int MaxChange,int LocalIter) {
        this.MaxIter = MaxIter;
        this.NumBees = NumBees;
        this.MaxChange = MaxChange;
        this.LocalIter = LocalIter;
    }
    public int getMaxIter() {
        return MaxIter;
    }
    public void setMaxIter(int MaxIter) {
        this.MaxIter = MaxIter;
    }
    public int getNumBees() {
        return NumBees;
    }
    public void setNumBees(int NumBees) {
        this.NumBees = NumBees;
    }
    public int getMaxChange() {
        return MaxChange;
    }
    public void setMaxChange(int MaxChange) {
        this.MaxChange = MaxChange;
    }
    public int getLocalIter() {
        return LocalIter;
    }
    public void setLocalIter(int LocalIter) {
        this.LocalIter = LocalIter;
    }
    
    
    
    
}
