package com.company.sintactic;


import java.util.List;
import java.util.stream.Collectors;

public class Gramatica {
    private List<Productie> productii;
    private String start; // S'
    private List<String> terminali;
    private List<String> neterminali;

    public Gramatica(List<Productie> productii, String start, List<String> terminali, List<String> neterminali) {
        this.productii = productii;
        this.start = start;
        this.terminali = terminali;
        this.neterminali = neterminali;
    }

    public List<Productie> getProductiiNeterminal(String neterminal) {
        return productii.stream()
                .filter(productie -> productie.getLeft().equals(neterminal))
                .collect(Collectors.toList());
    }

    public List<Productie> getProductii() {
        return productii;
    }

    public void setProductii(List<Productie> productii) {
        this.productii = productii;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public List<String> getTerminali() {
        return terminali;
    }

    public void setTerminali(List<String> terminali) {
        this.terminali = terminali;
    }

    public List<String> getNeterminali() {
        return neterminali;
    }

    public void setNeterminali(List<String> neterminali) {
        this.neterminali = neterminali;
    }

    public boolean isNeterminal(String token){
        return this.neterminali.contains(token);
    }

    public boolean isTerminal(String token){
        return this.terminali.contains(token);
    }


}
