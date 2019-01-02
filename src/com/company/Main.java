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
        analizor.getAtomi("cerc.txt");
        analizor.printAtoms("fip.txt","ts.txt");

        FileUtils fileUtils = new FileUtils("gramatica.txt");
//        System.out.println("Productii:");
        List<Productie> productii = fileUtils.getProductii();
//        for (Productie productie : productii){
//            System.out.println(productie.toString());
//        }
//        System.out.println();
//        System.out.println("Terminali:");
        List<String> terminali = fileUtils.getTerminali();
//        for (String terminal : terminali){
//            System.out.println(terminal);
//        }
//
//        System.out.println();
//        System.out.println("Neterminali:");
        List<String> neterminali = fileUtils.getNeterminali();
//        for (String neterminal : neterminali){
//            System.out.println(neterminal);
//        }
//

        Gramatica gramatica = new Gramatica(productii,"S'",terminali,neterminali);

        SLR slr = new SLR(gramatica);
        /*List<String> firsts = slr.getFirst("EXPRESIE");

        for(String first : firsts){
            System.out.println(first);
        }*/

//        List<String> follows = slr.getFollow("S");
//
//        for(String follow : follows){
//            System.out.println(follow);
//        }
//        slr.getClosureTest();
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


//        List<String> secv = new ArrayList<>();
//        secv.add("b");
//        secv.add("i");
//        secv.add("c");
//       // secv.add("i");
//        secv.add("e");
//        try{
//        Stack<Integer> sirproductii = slr.parseSecv(secv);
//        while(!sirproductii.empty()){
//            System.out.print(sirproductii.pop()+" ");
//        }}
//        catch (Exception e){
//            System.err.println(e.getMessage());
//        }

    }
}
