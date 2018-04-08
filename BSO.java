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

    private int MaxIter = 0,NumBees = 10,MaxChange = 0,LocalIter = 0,NumberOfLiterals = 20,NumberOfClauses = 0, Flip = 12;
    private Vector<Solution> SearchArea;
    private Vector<Solution> TabooList;
    private int[][] BC;
    private int[] Dance;
   
    public BSO() {
        this.SearchArea = new Vector<Solution>();
        this.TabooList = new Vector<Solution>();
        this.Dance = new int[this.getNumberOfLiterals()];
    }
    
    public void search() {
        int[] tab = new int[this.getNumberOfLiterals()];
        for(int i=0;i<tab.length;i++) {
            tab[i] = 0;
        }
        //Solution Sref = new Solution(tab);
        Solution Sref = this.generateRandomSolution();
        this.TabooList.add(Sref);
        this.generateSearchArea(Sref);
        for(Solution s : this.getSearchArea()) {
            for(int e:s.getSolution()) {
                System.out.print(" "+e);
            }
            System.out.println("");
        }
        for(Solution Sol:this.getSearchArea()) {
            System.out.println(this.evaluate(Sol));  
        }
    }  
    
    public Vector<Solution> generateSearchArea(Solution Sref) {
        int h = 0 , p = 0, index = 0;
        Solution Sol;
        if(this.SearchArea != null)this.SearchArea.removeAllElements();
        while(this.SearchArea.size() < NumBees && h<Flip) {
            Sol = new Solution(Sref.getSolution());
            p = 0; index = 0;
            index = p*Flip+h;
            if(Sol.getSolutionElement(index) == 0) {
                Sol.setSolutionElement(index,1);
            }else{
                Sol.setSolutionElement(index,0);
            }
            while(index < NumberOfLiterals){
                if(Sol.getSolutionElement(index) == 0) {
                    Sol.setSolutionElement(index,1);
                }else{
                    Sol.setSolutionElement(index,0);
                }
                index = p*Flip+h;
                p++;
            }
            this.SearchArea.add(Sol);
            h++;
        }
        return SearchArea;
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
            clauses++;
        }
        return satClauses;
    }
  
    public void initialise(String file_path){
        String headerReg= "p[ \\t\\n\\x0B\\f\\r]+cnf[ \\t\\n\\x0B\\f\\r]+([0-9]+)[ \\t\\n\\x0B\\f\\r]+([0-9]+)";
        String clauseReg= "(.*)[ \\t\\n\\x0B\\f\\r]+0";//"[ \\t\\n\\x0B\\f\\r]*(.+)[ \\t\\n\\x0B\\f\\r]+0[ \\t\\n\\x0B\\f\\r]+";
        Boolean InfosLineFound = false;
        String inString;
        int clauseLine = 0 ,ltValue=0;
        String[] literals;
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
    public Vector<Solution> getSearchArea() {
        return SearchArea;
    }
    public void setSearchArea(Vector<Solution> SearchArea) {
        this.SearchArea = SearchArea;
    }
    public Vector<Solution> getTabooList() {
        return TabooList;
    }
    public void setTabooList(Vector<Solution> TabooList) {
        this.TabooList = TabooList;
    }    
    
    public int distance(Solution sol1,Solution sol2) {
        int distance =0;
        for(int i=0;i<this.getNumberOfLiterals();i++) {
            if(sol1.getSolutionElement(i) != sol2.getSolutionElement(i)) {
                distance++;
            }
        }
        return distance;
    }
    
    public int diversityDegree(Solution sol) {
        int degree = 0;
        
        for(Solution e:this.getTabooList()) {
            
        }
        return degree;
        
    }
    
    public Solution generateRandomSolution() {
        Solution randomSol = new Solution();
        return randomSol;
    }
}
