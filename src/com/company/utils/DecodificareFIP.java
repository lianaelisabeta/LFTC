package com.company.utils;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DecodificareFIP {
    // dictionar in care pastram codurile corespunzatoare fiecarui atom lexical
    private Map<Integer, String> coduriAtomi = new HashMap<>();
    private String filename;
    public DecodificareFIP(String filename){
        this.filename = filename;
        loadAtoms();
    }

    public List<String> getFipTokens(String filenameFip){
        List<String> tokens = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filenameFip))) {
            br.lines().forEach(line ->{
                String[] line1 = line.split(" ");
                String atom = coduriAtomi.get(Integer.valueOf(line1[0]));
                if("ID".equals(atom) || "CONST".equals(atom)){
                    atom = atom.toLowerCase();
                }

                tokens.add(atom);

            });
        }
        catch (IOException e){
            System.err.println(e);
        }
        return tokens;
    }

    //incarca din fisier codurile pentru fiecare tip de atom lexical
    private void loadAtoms() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.lines().forEach(line ->{

                String[] line1 = line.split(" ");
                coduriAtomi.put(Integer.valueOf(line1[1]),line1[0]);

            });
        }
        catch (IOException e){
            System.err.println(e);
        }
    }



}

