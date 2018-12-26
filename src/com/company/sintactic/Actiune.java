package com.company.sintactic;

import java.util.Objects;

public class Actiune {
    private TipActiune tipActiune;
    private int numar;

    public Actiune(TipActiune tipActiune, int numar) {
        this.tipActiune = tipActiune;
        this.numar = numar;
    }

    public Actiune() {
    }

    public Actiune(TipActiune tipActiune) {
        this.tipActiune = tipActiune;
    }

    public TipActiune getTipActiune() {
        return tipActiune;
    }

    public void setTipActiune(TipActiune tipActiune) {
        this.tipActiune = tipActiune;
    }

    public int getNumar() {
        return numar;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    @Override
    public String toString() {
        return tipActiune.toString()+numar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actiune actiune = (Actiune) o;
        return numar == actiune.numar &&
                tipActiune == actiune.tipActiune;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipActiune, numar);
    }
}
