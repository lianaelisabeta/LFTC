package com.company.lexical;

public class FIPElement {
    private Integer codAtom;
    private Integer pozTS;

    public FIPElement(Integer codAtom, Integer pozTS){
        this.codAtom = codAtom;
        this.pozTS=pozTS;
    }

    public Integer getCodAtom() {
        return codAtom;
    }

    public void setCodAtom(Integer codAtom) {
        this.codAtom = codAtom;
    }

    public Integer getPozTS() {
        return pozTS;
    }

    public void setPozTS(Integer pozTS) {
        this.pozTS = pozTS;
    }

    @Override
    public String toString() {
        return ""+codAtom+" "+pozTS;
    }
}
