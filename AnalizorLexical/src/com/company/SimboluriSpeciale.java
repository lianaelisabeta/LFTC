package com.company;

import java.util.ArrayList;
import java.util.List;

public class SimboluriSpeciale {
    private List<String> cuvinteRezervate;
    private List<String> operatori;
    private List<String> separatori;

    public SimboluriSpeciale(){
        loadValues();
    }

    private void loadValues(){
        cuvinteRezervate = new ArrayList<>();
        cuvinteRezervate.add("main");
        cuvinteRezervate.add("int");
        cuvinteRezervate.add("double");
        cuvinteRezervate.add("char");
        cuvinteRezervate.add("std::string");
        cuvinteRezervate.add("while");
        cuvinteRezervate.add("if");
        cuvinteRezervate.add("else");
        cuvinteRezervate.add("std::cin");
        cuvinteRezervate.add("std::cout");
        cuvinteRezervate.add("std::endl");
        cuvinteRezervate.add("#include<iostream>");
        cuvinteRezervate.add("#include<string>");

        operatori = new ArrayList<>();
        operatori.add("+");
        operatori.add("-");
        operatori.add("*");
        operatori.add("/");
        operatori.add("=");
        operatori.add("<");
        operatori.add(">");
        operatori.add("<=");
        operatori.add("==");
        operatori.add(">=");
        operatori.add("!=");
        operatori.add(">>");
        operatori.add("<<");

        separatori = new ArrayList<>();
        separatori.add("{");
        separatori.add("}");
        //separatori.add(":");
        separatori.add(";");
        separatori.add("(");
        separatori.add(")");
        separatori.add("[");
        separatori.add("]");
        separatori.add("\n");
        separatori.add("\t");
    }

    public boolean eCuvantRezervat(String secv){
        return cuvinteRezervate.contains(secv);
    }
    public  boolean eOperator(String secv){
        return operatori.contains(secv);
    }

    public  boolean eSeparator(String secv){
        return separatori.contains(secv);
    }


    public List<String> getSeparatori(){
        return separatori;
    }
}
