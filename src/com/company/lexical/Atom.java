package com.company.lexical;

//clasa reprezentand un atom lexical
public class Atom {
    private String denumire; //valoarea/textul atomului
    private int codAtom;     //codul corespunzator tipului de atom lexical
    private int linie;       //linia la care se gaseste primul caracter al atomului in fisierul cu cod sursa

    public Atom(String denumire, int codAtom, int linie) {
        this.denumire = denumire;
        this.codAtom = codAtom;
        this.linie = linie;
    }
    public Atom(){

    }

    public Atom(String denumire, int linie) {
        this.denumire = denumire;
        this.codAtom = -1;
        this.linie = linie;
    }

    public String getDenumire() {
        return denumire;
    }

    public int getCodAtom() {
        return codAtom;
    }

    public int getLinie() {
        return linie;
    }

      public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public void setCodAtom(int codAtom) {
        this.codAtom = codAtom;
    }

    public void setLinie(int linie) {
        this.linie = linie;
    }

}
