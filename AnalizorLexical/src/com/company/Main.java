package com.company;

public class Main {

    public static void main(String[] args) {
        AnalizorLexical analizor = new AnalizorLexical();
        analizor.getAtomi("cerc.txt");
        analizor.printAtoms("fip.txt","ts.txt");

    }
}
