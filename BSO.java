/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bso;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mohamed amine ben hamida
 */
public class BSO {

    private int MaxIter = 0,NumBees = 0,MaxChange = 0,LocalIter = 0,NumberOfLiterals = 0,NumberOfClauses = 0, Flip = 0;
    private Vector<Solution> SearchArea;
    private Vector<Solution> TabooList;
    private int[][] BC;

    public BSO(Vector<Solution> SearchArea, Vector<Solution> TabooList, Vector<Solution> DanceTable, int[][] BC) {
        this.SearchArea = SearchArea;
        this.TabooList = TabooList;
        this.DanceTable = DanceTable;
        this.BC = BC;
    }
    public BSO() {
    }
   
    public int evaluate(Solution solution){
        int value = 0 , counter = 0 , sat = 0 , clauses = 0 , satClauses=0;
        while(clauses < this.getNumberOfClauses()) {
            counter =0; value = 0; sat=0;
            for(int e : solution.getSolution()) {
                if(sat != 1) {
                    value = e * this.getBC(clauses, counter);
                    if(value == 1){
                        sat =1;
                    }
                }
                counter++;
            }
            if(sat == 1) {
                satClauses++;
            }
            //System.out.print(clauses);
            clauses++;
        }
        return satClauses;
    }
    public void search() {
        
    }  
    public void initialise(String file_path){
        String headerReg= "p[ \\t\\n\\x0B\\f\\r]+cnf[ \\t\\n\\x0B\\f\\r]+([0-9]+)[ \\t\\n\\x0B\\f\\r]+([0-9]+)";
        String clauseReg= "(.*)[ \\t\\n\\x0B\\f\\r]+0";//"[ \\t\\n\\x0B\\f\\r]*(.+)[ \\t\\n\\x0B\\f\\r]+0[ \\t\\n\\x0B\\f\\r]+";
        Boolean InfosLineFound = false;
        String inString;
        int NumberOfClauses , NumberOfLiterals , counter = 0 , clauseLine = 0 , found = 0 ,ltValue=0;
        String[] literals , FileInfos;
        
         try {
            FileReader inFile = new  FileReader(file_path);
            BufferedReader inStream = new BufferedReader(inFile);

            while ((inString = inStream.readLine()) != null)
            {
                //find the infos header
                Pattern p = Pattern.compile(headerReg);
                Matcher m = p.matcher(inString);
                if(m.find() && m.groupCount()>0){
                    InfosLineFound=true;
                    this.setNumberOfLiterals(Integer.parseInt(m.group(1)));
                    this.setNumberOfClauses(Integer.parseInt(m.group(2)));
                    //creation et initialisation de la base de connaissances
                    this.BC = new int[this.getNumberOfClauses()+1][this.getNumberOfLiterals()+1];
                    this.initializeBC();
                    System.out.println("Literals => "+this.getNumberOfLiterals()+" clauses => "+this.getNumberOfClauses());
                }
                // si la ligne d'infos est trouvé on continue le remplissage de la BC

                if(InfosLineFound)
                {
                    //lecture des clauses est mise a jour de la base de connaissances
                    p = Pattern.compile(clauseReg);
                    m = p.matcher(inString);
                    if(m.find() && m.groupCount()>0){
                        String clause= m.group(1);
                        //get literals:
                        clause =clause.trim();
                        literals = clause.split("[ \\t\\n\\x0B\\f\\r]+");

                        for(String lt : literals){
                            ltValue = Integer.parseInt(lt);
                            this.setBC(ltValue/Math.abs(ltValue),clauseLine,Math.abs(ltValue)-1);
                        }
                        for(int i =0;i<this.getNumberOfLiterals();i++){
                            System.out.print(this.getBC(clauseLine,i));
                        }
                        System.out.print("\n");
                        clauseLine++;
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
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
    public int getNumberOfLiterals() {
        return NumberOfLiterals;
    }
    public void setNumberOfLiterals(int NumberOfLiterals) {
        this.NumberOfLiterals = NumberOfLiterals;
    }
    public int getNumberOfClauses() {
        return NumberOfClauses;
    }
    public void setNumberOfClauses(int NumberOfClauses) {
        this.NumberOfClauses = NumberOfClauses;
    }
    public void setBC(int value,int i , int j) { this.BC[i][j] = value;}
    public int getBC(int i , int j) { return this.BC[i][j]; }
    public void initializeBC() {
        for(int i=0;i<this.getNumberOfClauses();i++){
            for(int j=0;j<this.getNumberOfLiterals();j++){
                this.setBC(0,i,j);
            }
        }
    }
}
