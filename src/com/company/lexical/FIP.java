package com.company.lexical;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

//forma interna a programului
public class FIP {
    private List<FIPElement> fip ;
    public FIP(){
        fip= new ArrayList<>();
    }

    public void addToFIP(Integer codAtom, Integer pozTS){
        fip.add(new FIPElement(codAtom,pozTS));
    }

    public int getSize(){
        return fip.size();
    }

    public void setToFIP(Integer codAtom, Integer pozTS, int p){
        fip.set(p,new FIPElement(codAtom,pozTS));
    }

    public void writeToFileFIP(String filename ){
        try(PrintWriter pw= new PrintWriter(filename)){
            for(FIPElement s : fip){
                    pw.println(s);

            }
        }
        catch (IOException e){
            System.out.println(e);
        }
    }
    public void printFIP(){
        for(FIPElement s : fip){
            System.out.println(s);

        }
    }

}
