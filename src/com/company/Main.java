package com.company;

import com.company.sintactic.Gramatica;
import com.company.sintactic.Productie;
import com.company.sintactic.SLR;
import com.company.utils.FileUtils;

import java.util.List;

public class Main {

    public static void main(String[] args) {
//        AnalizorLexical analizor = new AnalizorLexical();
//        analizor.getAtomi("cmmdc.txt");
//        analizor.printAtoms("fip.txt","ts.txt");

        FileUtils fileUtils = new FileUtils("gramatica_seminar11.txt");
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

    }
}
