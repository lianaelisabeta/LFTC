package com.company.sintactic;

import java.util.List;
import java.util.Objects;

public class Stare {
    private int numarStare;
    private List<StareProductie> productii;

    public Stare() {
    }

    public Stare(int numarStare, List<StareProductie> productii) {
        this.numarStare = numarStare;
        this.productii = productii;
    }

    public int getNumarStare() {
        return numarStare;
    }

    public void setNumarStare(int numarStare) {
        this.numarStare = numarStare;
    }

    public List<StareProductie> getProductii() {
        return productii;
    }

    public void setProductii(List<StareProductie> productii) {
        this.productii = productii;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stare stare = (Stare) o;
        boolean okProductii = true;
        for(int i = 0;i<productii.size();i++){
            if(!Objects.equals(productii.get(i),stare.getProductii().get(i))){
                okProductii = false;
            }
        }
        return numarStare == stare.numarStare &&
                okProductii;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numarStare, productii);
    }
}
