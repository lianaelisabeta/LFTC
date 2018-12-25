package com.company.sintactic;

import com.company.lexical.AF;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SLR {
    private Gramatica gramatica;
    private int nextStateIndex;
    private AF automat;
    private List<List<StareProductie>> colectiiCanonice;
    private List<Map<String, List<String>>> states; // tabel

    public SLR(Gramatica gramatica) {
        this.gramatica = gramatica;
    }

    public SLR(Gramatica gramatica, int nextStateIndex, AF automat, List<Map<String, List<String>>> states) {
        this.gramatica = gramatica;
        this.nextStateIndex = nextStateIndex;
        this.automat = automat;
        this.states = states;
    }


    private StareProductie convertProductie(Productie productie) {
        StareProductie stareProductie = new StareProductie();
        stareProductie.setProductie(productie);
        stareProductie.setPredictii(getFollow1(productie.getLeft())); // seteaza lista de predictii in fct de follow

        return stareProductie;

    }

    public List<StareProductie> getClosureTest() {
        StareProductie testSt = convertProductie(gramatica.getProductii().get(0));
        return getClosure(testSt);
    }

    // returneaza starea dupa aplicarea closure pe productie
    public List<StareProductie> getClosure(StareProductie productie) {
        List<StareProductie> finalClosure = new ArrayList<>();
        finalClosure.add(productie);
        List<StareProductie> tempClosure = new ArrayList<>();
        while (tempClosure.size() != finalClosure.size()) {

            tempClosure = new ArrayList<>();
            for(StareProductie stp : finalClosure){
                tempClosure.add(new StareProductie(stp.getProductie(),stp.getPunct(),stp.getPredictii()));
            }

            for (int i = 0 ; i < tempClosure.size(); i++) {
                if (!shouldComputeClosure(finalClosure.get(i))) {// daca nu se poate aplica closure | e punct in fata term
                    continue;
                }
                String nextToken = finalClosure.get(i).nextSymbol(); // e neterminal simbolul de dupa punct
                for (Productie productie1 : gramatica.getProductiiNeterminal(nextToken)) {
                    List<String> rightProd = Arrays.asList(productie1.getRight().split("[|]"));
                    for (String pr : rightProd) {
                        Productie newProd = new Productie(productie1.getLeft().trim(), pr.trim());

                        StareProductie newst = convertProductie(newProd);
                        if (!finalClosure.contains(newst)) {
                            finalClosure.add(newst);
                        }

                    }
                }
            }
        }

        return finalClosure;

    }

    private boolean shouldComputeClosure(StareProductie stareProductie) {
        if (stareProductie.shouldReduce()) { // punctul e la sfarsitul productiei
            return false;
        }

        String nextToken = stareProductie.nextSymbol();
        if (gramatica.isTerminal(nextToken)) { // daca e terminal dupa punct nu se mai va mai aplica closure
            return false;
        }
        return true;  //  => se mai poate aplica closure

    }

    public List<String> getFirst(String neterminal) {
        return getFirst1(neterminal);
    }

    public List<String> getFollow(String neterminal) {
        return getFollow1(neterminal);
    }

    private List<String> getFollow1(String neterminal) {
        List<String> follows = new ArrayList<>();
        for (Productie productie : gramatica.getProductii()) {
            if (productie.getRight().contains(neterminal)) {
                follows.addAll(getFollowIfTerminal(neterminal, productie));
                follows.addAll(getFollowIfNeterminal(neterminal, productie));
            }
        }

        if(follows.size() == 0){
            follows.add("$");
        }
        return follows.stream().distinct().collect(Collectors.toList());
    }


    private List<String> getFollowIfTerminal(String neterminal, Productie productie) {
        List<String> follows = new ArrayList<>();
        List<String> rprod = Arrays.asList(productie.getRight().split("[|]"));
        for (String rp : rprod) {
            List<String> rtokens = Arrays.asList(rp.trim().split("\\s+"));
            for (int i = 0; i < rtokens.size(); i++) {
                if (rtokens.get(i).equals(neterminal)) {
                    if (i + 1 == rtokens.size()) {
                        follows.add("$");
                    } else if (gramatica.isTerminal(rtokens.get(i + 1))) {
                        follows.add(rtokens.get(i + 1));
                    }
                }
            }

        }
        return follows;
    }

    private List<String> getFollowIfNeterminal(String neterminal, Productie productie) {
        List<String> follows = new ArrayList<>();
        List<String> rprod = Arrays.asList(productie.getRight().split("[|]"));
        for (String rp : rprod) {
            List<String> rtokens = Arrays.asList(rp.trim().split("\\s+"));
            for (int i = 0; i < rtokens.size(); i++) {
                if (rtokens.get(i).equals(neterminal) && i + 1 < rtokens.size()) {
                    if (gramatica.isNeterminal(rtokens.get(i + 1))) {
                        follows.addAll(getFirst1(rtokens.get(i + 1)));
                    }
                }
            }

        }
        return follows;

    }

    private List<String> getFirst1(String neterminal) {
        List<String> firsts = new ArrayList<>();
        for (Productie productie : gramatica.getProductii()) {
            if (productie.getLeft().contains(neterminal)) {
                firsts.addAll(computeFirstIfTerminal(neterminal, productie));
                firsts.addAll(computeFirstsIfNeterminal(neterminal, productie));
            }
        }
        return firsts;
    }

    private List<String> computeFirstIfTerminal(String neterminal, Productie productie) {
        List<String> firsts = new ArrayList<>();
        List<String> rprod = Arrays.asList(productie.getRight().split("[|]"));
        for (String rp : rprod) {
            List<String> rtokens = Arrays.asList(rp.trim().split("\\\\s+"));
            if (gramatica.isTerminal(rtokens.get(0))) {
                firsts.add(rtokens.get(0));
            }
        }
        return firsts;
    }

    // neterminal : A, prod: A-> Ta
    private List<String> computeFirstsIfNeterminal(String neterminal, Productie productie) {
        List<String> firsts = new ArrayList<>();
        List<String> rprod = Arrays.asList(productie.getRight().split("[|]"));
        for (String rp : rprod) {
            String rtoken = Arrays.asList(rp.trim().split(" ")).get(0);

            if (gramatica.isNeterminal(rtoken)) {
                firsts.addAll(computeFirstIfNeterminal(rtoken));
            }
        }
        return firsts.stream().distinct().collect(Collectors.toList());
    }


    private List<String> computeFirstIfNeterminal(String neterminal) {
        List<String> firsts = new ArrayList<>();

        for (Productie productie : gramatica.getProductii()) {
            if (productie.getLeft().equals(neterminal)) {
                List<String> firstRight = Arrays.asList(productie.getRight().split("[|]"));
                for (String prod : firstRight) {
                    List<String> tokens = Arrays.asList(prod.trim().split("\\s"));
                    if (gramatica.isTerminal(tokens.get(0))) {
                        firsts.add(tokens.get(0));
                    } else {
                        firsts.addAll(computeFirstIfNeterminal(tokens.get(0)));
                    }

                }
            }
        }
        return firsts;
    }
}
