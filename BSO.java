/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bso;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.exit;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author mohamed amine ben hamida
 */
public class BSO {

    private int MaxIter = 10,NumBees = 5,MaxChance =1,NbrChance=0,NumberOfLiterals = 37,NumberOfClauses = 0, Flip = 1 , LocalIter = 100;
    private boolean OptimumFound = false;
    private Vector<Solution> SearchArea,TabooList,Dance;
    private int[][] BC;
    
    public void search() {
        int iter = 0,best_result = 0;
        Solution Sref = this.generateRandomSolution();
        if(this.evaluate(Sref) == this.getNumberOfClauses()) {
                this.setOptimumFound(true);
        }
        while(!this.isOptimumFound()&& iter < this.getMaxIter()) {
            this.TabooList.add(Sref);
            Sref.showSolution("Sref");
            this.emptySearchArea();
            this.generateSearchArea(Sref);
            this.emptyDance();
            for(Solution sol:this.getSearchArea()) {
                this.evaluate(sol);
                sol = this.localSearch(sol); 
                if(sol.getSatClauses() == this.getNumberOfClauses()) {
                    this.setOptimumFound(true);
                }
                if(this.isInTabooList(sol) == false) this.getDance().add(sol);
                if(best_result < sol.getSatClauses()) best_result = sol.getSatClauses();
            }  
            ;
            Sref = this.findSref(Sref);
            iter++;
        }
        System.out.println("best => "+best_result);
    }  
    public void generateSearchArea(Solution Sref) {
        int h = 0 , p = 0, index = 0;
        Solution Sol = null;
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
        solution.setSatClauses(satClauses);
        return satClauses;
    } 
    public void emptyDance() {
        this.Dance.removeAllElements();
    }
    public void emptySearchArea() {
        this.SearchArea.removeAllElements();
    }
    
    public int diversityDegree(Solution sol) {
        int degree = this.getDance().firstElement().getDiversity() , distance = 0;
        for(Solution e:this.getDance()) {
            if(sol != e) {
              distance = sol.distance(e);
            if (degree > distance) {
                degree = distance;
            }  
            } 
        }
        sol.setDiversity(degree);
        return degree;
    }   
    public int diversityDegreeTaboo(Solution sol) {
        int degree = 0 , distance = 0;
        degree = sol.distance(this.getTabooList().firstElement());
        for(Solution e:this.getTabooList()) {
            distance = sol.distance(e);
            if (degree > distance) {
                degree = distance;
            }
        }
        sol.setDiversity(degree);
        return degree;
    }   
    public Solution generateRandomSolution() {
        Solution randomSol = new Solution(this.getNumberOfLiterals());
        for(int i=0;i<this.getNumberOfLiterals();i++) {
           randomSol.setSolutionElement(i,(int)(Math.random()*2));
        }
        return randomSol;
    }
    public Solution bestInQuality() {
        int i =0;
        Solution Optimum = null;
        if(this.getDance().size()>0) {
            do {
                Optimum = this.getDance().elementAt(i);
                i++; 
            }while(this.isInTabooList(Optimum) == true && i<this.getDance().size());
            for(Solution sol:this.getDance()) {
                if(this.isInTabooList(sol)==false) {
                    if(sol.getSatClauses()>Optimum.getSatClauses()) {
                        Optimum = sol;
                    }
                }
            }
        }else{
            System.out.println("dance null");
            return null;
        }
        return Optimum; 
    }
    public Solution bestInDiversity() {
        //returns the best solution in diversity from dance table
        Solution Optimum = null;
        int i = 0;
        if(this.getDance() != null) {
            do {
                Optimum = this.getDance().elementAt(i);
                i++;
            }while(this.isInTabooList(Optimum) && i<this.getDance().size());
            for(Solution e:this.getDance()){
                if(!this.isInTabooList(e)) {
                    if( Optimum != e) {
                        if(Optimum.getDiversity()<e.getDiversity()) {
                            Optimum = e;
                        }else{
                            if(Optimum.getDiversity() == e.getDiversity() && Optimum.getSatClauses() < e.getSatClauses()) {
                                    Optimum = e;
                            }
                        }
                    } 
                }  
            }
        }
        return Optimum;
    
    }
    public void updateDiversity() {
        for(Solution sol:this.getDance()) {
            this.diversityDegree(sol);
        }
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
    public void initializeBC() {
        for(int i=0;i<this.getNumberOfClauses();i++){
            for(int j=0;j<this.getNumberOfLiterals();j++){
                this.setBC(0,i,j);
            }
        }
    }
    public boolean isInTabooList(Solution sol) {
        if(sol == null) {
            return true;
        }
        for(Solution s:this.getTabooList()) {
            if(sol.isEqual(s) == true) return true;
        }
        return false;
    }  
    public Solution localSearch(Solution sol) {
        Solution inter1 = new Solution(sol.getSolution(),sol.getSatClauses()),inter2 = new Solution(sol.getSolution(),sol.getSatClauses());
        
        Solution Best = new Solution(sol.getSolution(),sol.getSatClauses());
        for(int i=0;i<this.getLocalIter();i++) {
            for(int j=0;j<this.getNumberOfLiterals();j++) {
                inter1.setSolution(Best.getSolution());
                if(inter1.getSolutionElement(j) == 1){
                    inter1.setSolutionElement(j, 0);
                }else{
                    inter1.setSolutionElement(j, 1);
                }
                this.evaluate(inter1);
                /*System.out.print("\t\t");
                inter1.showSolution("clause ");*/
                if(inter1.getSatClauses() > inter2.getSatClauses()) {
                    inter2.setSolution(inter1.getSolution());
                    inter2.setSatClauses(inter1.getSatClauses());
                }
            }
            Best.setSatClauses(inter2.getSatClauses());
            Best.setSolution(inter2.getSolution());
            
        } 
       
        this.evaluate(inter1);
        return Best;
       
    } 
    public Solution findSref(Solution Sref) {
        int Df = 0;
        Solution Best = null;          
            Best = this.bestInQuality();
            if(Sref.getSatClauses() == 283) {
                Sref = this.generateRandomSolution();
            }else{
                if(Best == null) {
                Sref = this.generateRandomSolution();
                this.evaluate(Sref);
            }else{
                Df = Best.getSatClauses() - Sref.getSatClauses();
                if(Df>0) {
                    System.out.println("Df>0 : "+Df);
                    Sref = Best;
                    if(this.getNbrChance() < this.getMaxChance()) this.setNbrChance(this.getMaxChance());
                }else{
                    this.setNbrChance(this.getNbrChance()-1);
                    if(this.getNbrChance()>0) {
                         System.out.println("Df<0 : "+Df+" chances>0 "+this.getNbrChance()+" maxchances : "+this.getMaxChance());
                        Sref = Best;   
                    }else{
                         System.out.println("Df<0 : "+Df+" chances<0 "+this.getNbrChance()+" maxchances : "+this.getMaxChance());
                        this.updateDiversity();
                        
                        Sref = this.bestInDiversity();
                        this.setNbrChance(this.getMaxChance());
                    }
                }
            }
            }
            
        return Sref;
    }
    /*****************************************************/
    /*                   constructors                    */
    /*****************************************************/
    public BSO() {
        this.SearchArea = new Vector<Solution>();
        this.TabooList = new Vector<Solution>();
        this.Dance = new Vector<Solution>();
    }
    public BSO(int MaxIter, int NumBees,int MaxChange) {
        this.MaxIter = MaxIter;
        this.NumBees = NumBees;
        this.MaxChance = MaxChance;
    }
    /*****************************************************/
    /*                getters & setters                  */
    /*****************************************************/
    public boolean isOptimumFound() {
        return OptimumFound;
    }
    public void setOptimumFound(boolean OptimumFound) {
        this.OptimumFound = OptimumFound;
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
    public int getMaxChance() {
        return MaxChance;
    }
    public void setMaxChance(int MaxChance) {
        this.MaxChance = MaxChance;
    }

    public int getNbrChance() {
        return NbrChance;
    }

    public void setNbrChance(int NbrChance) {
        this.NbrChance = NbrChance;
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
    public Vector<Solution> getDance() {
        return Dance;
    }
    public void setDance(Vector<Solution> Dance) {
        this.Dance = Dance;
    }

    public int getLocalIter() {
        return LocalIter;
    }

    public void setLocalIter(int LocalIter) {
        this.LocalIter = LocalIter;
    }
    
    
}
