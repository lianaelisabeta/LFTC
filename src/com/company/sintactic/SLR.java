package com.company.sintactic;

import java.util.*;
import java.util.stream.Collectors;

public class SLR {
    private Gramatica gramatica;
    private int nextStateIndex = 0;
    private List<List<StareProductie>> colectiiCanonice = new ArrayList<>(); // lista de stari ( stare = lista de stareProductie)
    private List<Map<String, List<Actiune>>> tabelaStari = new ArrayList<>(); // tabel

    public SLR(Gramatica gramatica) {
        this.gramatica = gramatica;
    }

    public SLR(Gramatica gramatica, int nextStateIndex, List<Map<String, List<Actiune>>> tabelaStari) {
        this.gramatica = gramatica;
        this.nextStateIndex = nextStateIndex;
        this.tabelaStari = tabelaStari;
    }

    public void buildStari() {
        buildColectiiCanonice();
        buildTable();
        showTabel();
    }

    private void showTabel() {
        for (int i = 0; i < tabelaStari.size(); i++) {
            System.out.println("I" + i + ":");
            for (Map.Entry<String, List<Actiune>> entry : tabelaStari.get(i).entrySet()) {

                if (entry.getValue().size() > 0) {
                    System.out.print(entry.getKey() + ": ");
                    entry.getValue().forEach(actiune -> System.out.print(actiune + "; "));
                }
            }
            System.out.println();
        }
    }

    private boolean checkIfConflicts() {
        for (Map<String, List<Actiune>> map : tabelaStari) {
            for (Map.Entry<String, List<Actiune>> entry : map.entrySet()) {
                if (entry.getValue().stream().distinct().count() > 1) {
                    return true;
                }
            }
        }
        return false;
    }


    private void buildColectiiCanonice() {
        List<StareProductie> I0 = getClosure(convertProductie(gramatica.getProductii().get(0)));
        colectiiCanonice.add(I0);
        while (colectiiCanonice.size() > nextStateIndex) {
            List<StareProductie> I = colectiiCanonice.get(nextStateIndex);
            for (StareProductie stareProductie : I) {
                if (stareProductie.shouldReduce()) {
                    continue;
                }
                String token = stareProductie.nextSymbol();
                List<StareProductie> nextState = goTo(I, token, nextStateIndex);
                if (nextState.size() > 0) { // se duce in stare noua(care nu exista deja in colectia canonica)
                    colectiiCanonice.add(nextState);
                }
            }
            nextStateIndex++;
        }
    }

    private void buildTable() {
        initTabel();
        for (int i = 0; i < colectiiCanonice.size(); i++) {
            List<StareProductie> stare = colectiiCanonice.get(i);
            for (StareProductie productie : stare) {

                if (productie.shouldReduce()) {
                    if (productie.getProductie().getLeft().equals("S'") && i > 0) {
                        tabelaStari.get(i).get("$").add(new Actiune(TipActiune.ACCEPT));
                        continue;
                    }
                    int nrRegula = gramatica.getProductii().indexOf(productie.getProductie());
                    for (String predictie : productie.getPredictii()) {
                        tabelaStari.get(i).get(predictie).add(new Actiune(TipActiune.REDUCE, nrRegula));
                    }
                    continue;
                }

                String token = productie.nextSymbol();
                StareProductie shifted = new StareProductie(productie.getProductie(), productie.getPunct() + 1, productie.getPredictii());
                List<Integer> statesToGoTo = containsStareProductie(shifted);
                tabelaStari.get(i).put(token, new ArrayList<>());
                for (Integer noStare : statesToGoTo) {
                    Actiune actiune = new Actiune(TipActiune.SHIFT, noStare);
                    if (!tabelaStari.get(i).get(token).contains(actiune)) {
                        tabelaStari.get(i).get(token).add(actiune);
                    }
                }
            }


        }
    }

    private void initTabel() {
        for (int i = 0; i < colectiiCanonice.size(); i++) { //pentru fiecare stare
            tabelaStari.add(new HashMap<>());
            for (String neterminal : gramatica.getNeterminali()) {
                if (neterminal.equals("S'")) {
                    continue;
                }
                tabelaStari.get(i).put(neterminal, new ArrayList<>());
            }
            for (String terminal : gramatica.getTerminali()) {
                tabelaStari.get(i).put(terminal, new ArrayList<>());
            }
            tabelaStari.get(i).put("$", new ArrayList<>());
        }
    }

    //shift punct peste token => starea in care se ajunge
    private List<StareProductie> goTo(List<StareProductie> state, String token, int numarStareCurenta) {

        //List<StareProductie> nextState = new ArrayList<>();
        List<StareProductie> tempState = new ArrayList<>();
        for (StareProductie stareProductie : state) {
            if (Objects.nonNull(stareProductie.nextSymbol())) {
                if (stareProductie.nextSymbol().equals(token)) {
                    StareProductie shiftProductie = new StareProductie(stareProductie.getProductie(), stareProductie.getPunct() + 1, stareProductie.getPredictii());
                    //if (!colectiiCanonice.contains(shiftProductie)) { // nu se ajunge intr o stare existenta prin shift
                    if (containsStareProductie(shiftProductie).size() == 0) {
                        tempState.addAll(getClosure(shiftProductie));
                    }
                }
            }
        }
        return tempState;
    }

    private List<Integer> containsStareProductie(StareProductie stareProductie) {
        List<Integer> stari = new ArrayList<>();
        for (int i = 0; i < colectiiCanonice.size(); i++) {
            if (colectiiCanonice.get(i).contains(stareProductie)) {
                stari.add(i);
            }
        }
        return stari;
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
            for (StareProductie stp : finalClosure) {
                tempClosure.add(new StareProductie(stp.getProductie(), stp.getPunct(), stp.getPredictii()));
            }

            for (int i = 0; i < tempClosure.size(); i++) {
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
            List<String> right = Arrays.asList(productie.getRight().split("\\s+"));
            if (right.size() == 1 && right.get(0).equals(neterminal)) {
                follows.addAll(getFollow1(productie.getLeft().trim()));
            }
        }
        if (follows.size() == 0) {
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
