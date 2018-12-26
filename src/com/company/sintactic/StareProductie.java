package com.company.sintactic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

//o productie dintr-o stare
public class StareProductie {
    private Productie productie;
    private int punct = 0;
    private List<String> predictii = new ArrayList<>();

    public StareProductie(Productie productie, int punct, List<String> predictii) {
        this.productie = productie;
        this.punct = punct;
        this.predictii = predictii;
    }

    public StareProductie() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StareProductie that = (StareProductie) o;

        boolean okPredictii = true;
        if(predictii.size()!= that.predictii.size()){
            okPredictii = false;
        }
        else{for(int i = 0; i<predictii.size();i++){
            if(!predictii.contains(that.predictii.get(i))){
                okPredictii = false;
                break;
            }
        }
        }
        return punct == that.punct &&
                Objects.equals(productie, that.productie) &&
                okPredictii;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productie, punct, predictii);
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

    public boolean shouldShift() {
        return this.punct < this.productie.getRight().length();
    }

    public boolean shouldReduce() {
        List<String> rightTokens = Arrays.asList(this.productie.getRight().split("\\s+"));
        return this.punct == rightTokens.size();
    }

    public String nextSymbol() {
        List<String> rightTokens = Arrays.asList(this.productie.getRight().split("\\s+"));

        //return String.valueOf(this.productie.getRight().charAt(punct));
        if (punct < rightTokens.size()) {
            return rightTokens.get(punct);
        }
        return null;
    }
}
