package com.company.lexical;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CodificareAtomi {
    // dictionar in care pastram codurile corespunzatoare fiecarui atom lexical
    private Map<String, Integer> atomi;

    public CodificareAtomi(){
        atomi= new HashMap<>();
    loadAtoms();
    }

    //returneaza multimea tipurilor de atomi lexicali
    public Set<String> getTipuriAtomi(){
      return atomi.keySet();
    }

    //returneaza codul specific unui atom lexical
    public Integer getCod(String tipAtom){

        return atomi.getOrDefault(tipAtom,-1);
    }

    //incarca din fisier codurile pentru fiecare tip de atom lexical
    private void loadAtoms(){

        try (BufferedReader br = new BufferedReader(new FileReader("tabelAtomi.txt"))) {
            br.lines().forEach(line ->{

                String[] line1 = line.split(" ");

                atomi.put(line1[0],Integer.valueOf(line1[1]));

            });
    }
        catch (IOException e){
            System.err.println(e);
        }}

    }

