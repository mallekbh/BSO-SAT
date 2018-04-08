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
       
        bso.initialise("/media/malek/MALEK/STUDY/SII_M1_S2_2018/META/PROJET/test_files/1/uuf75-01.cnf");
        bso.search();
        
         
    }
    
}
