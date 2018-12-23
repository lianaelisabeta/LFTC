package com.company.sintactic;

import java.util.ArrayList;
import java.util.List;

public class StareProductie {
    private Productie productie;
    private int punct = 0;
    private List<String> predictii = new ArrayList<>();

    public StareProductie(Productie productie, int punct, List<String> predictii) {
        this.productie = productie;
        this.punct = punct;
        this.predictii = predictii;
    }

    public Productie getProductie() {
        return productie;
    }

    public void setProductie(Productie productie) {
        this.productie = productie;
    }

    public int getPunct() {
        return punct;
    }

    public void setPunct(int punct) {
        this.punct = punct;
    }

    public List<String> getPredictii() {
        return predictii;
    }

    public void setPredictii(List<String> predictii) {
        this.predictii = predictii;
    }

    public boolean shouldShift(){
        return this.punct < this.productie.getRight().length();
    }

    public boolean shouldReduce(){
        return this.punct == this.productie.getRight().length();
    }

    public String nextSymbol(){
        return String.valueOf(this.productie.getRight().charAt(punct));
    }
}
