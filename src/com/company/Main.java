package com.company;

import com.company.lexical.AnalizorLexical;
import com.company.sintactic.Gramatica;
import com.company.sintactic.Productie;
import com.company.sintactic.SLR;
import com.company.utils.DecodificareFIP;
import com.company.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        AnalizorLexical analizor = new AnalizorLexical();
        analizor.getAtomi("suma.txt");
        analizor.printAtoms("fip.txt","ts.txt");

        FileUtils fileUtils = new FileUtils("gramatica.txt");

        List<Productie> productii = fileUtils.getProductii();
        List<String> terminali = fileUtils.getTerminali();
        List<String> neterminali = fileUtils.getNeterminali();

        Gramatica gramatica = new Gramatica(productii,"S'",terminali,neterminali);

        SLR slr = new SLR(gramatica);
        slr.buildStari();
        System.out.println("Conflicts:"+slr.checkIfConflicts());
        DecodificareFIP decodificareFIP = new DecodificareFIP("tabelAtomi.txt");

        try {
        Stack<Integer> sirproductii = slr.checkProgram("fip.txt",decodificareFIP);
        while(!sirproductii.empty()){
            System.out.print(sirproductii.pop()+" ");
        }}
        catch (Exception e){
            System.err.println(e.getMessage());
        }

    }
}
