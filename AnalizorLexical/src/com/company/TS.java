package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TS {
    //tabela de simboluri ca dictionar sortat dupa cheie (
    //
    private List<String> tabela = new ArrayList<>(); //simbol id/const - cod ts



    public void adaugaTS(String simbol){
        if(!tabela.contains(simbol))
        {tabela.add(simbol);
            updateTable();}

    }

    public Integer getTS(String idc){

        if(tabela.contains(idc))
        {return tabela.indexOf(idc);}
        return -1;
    }

    public int getUltimaPoz(){
        return tabela.size();
    }

    public void printTS(){
        for(String entry: tabela){
            System.out.println(entry+"|"+tabela.indexOf(entry));
        }
    }


    public void updateTable(){
        tabela.sort(Comparator.naturalOrder());
    }

    public int getSize(){
        return this.tabela.size();
    }
    public String getPoz(int p){
        return tabela.get(p);
    }

    public void writeToFileTS(String filename){

        //sortedSet.addAll(tabela.entrySet());

        try(PrintWriter pw= new PrintWriter(filename)){
            for(String entry: tabela){
                pw.println(""+entry+" "+tabela.indexOf(entry));
            }
        }
        catch (IOException e){
            System.out.println(e);
        }
    }
}



//public class TS {
//    //tabela de simboluri ca dictionar sortat dupa cheie (
//    //
//    private SortedMap<String, Integer> tabela = new TreeMap<>(); //simbol id/const - cod ts
//    public void adaugaTS(String simbol){
//        Integer codTs = tabela.size()+1;
//        tabela.put(simbol,codTs);
//    }
//
//    public Integer getTS(String idc){
//        return tabela.getOrDefault(idc, -1);
//    }
//
//    public int getUltimaPoz(){
//        return tabela.size();
//    }
//
//    public void printTS(){
//        for(Map.Entry<String,Integer> entry: tabela.entrySet()){
//            System.out.println(entry.getKey()+"|"+entry.getValue());
//        }
//    }
//
//    public void writeToFileTS(String filename){
//        try(PrintWriter pw= new PrintWriter(filename)){
//            for(Map.Entry<String,Integer> entry: tabela.entrySet()){
//                pw.println(""+entry.getKey()+" "+entry.getValue());
//            }
//        }
//        catch (IOException e){
//            System.out.println(e);
//        }
//    }
//}
