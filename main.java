/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bso;

import java.util.Vector;

/**
 *
 * @author mohamed amine benhamida
 */
public class main {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BSO bso = new BSO();
        int[] tab = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        Solution Sref = new Solution(tab);
        //bso.initialise("/media/malek/8A92B00A92AFF939/MALEK/STUDY/SII_M1_S2_2018/META/PROJET/test_files/1/uuf75-01.cnf");
        //bso.search();
        Vector<Solution> SearchArea;
        bso.generateSearchArea(Sref);
        for(Solution sol:bso.getSearchArea()) {
            for(int e:sol.getSolution()) {
                System.out.print(" "+e);
            }
            System.out.println(" ");
        }      
    }
    
}
