package com.company.lexical;

public class Transition {
    private String initialState;
    private Character simbol;
    private String finalState;

    public Transition(String initialState, String stare_finala, Character simbol) {
        this.initialState = initialState;
        this.simbol = simbol;
        this.finalState = stare_finala;
    }

    public Transition(String initial_state, String final_state){
        this.initialState = initial_state;
        this.finalState = final_state;
    }

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public Character getSimbol() {
        return simbol;
    }

    public void setSimbol(Character simbol) {
        this.simbol = simbol;
    }

    public String getFinalState() {
        return finalState;
    }

    public void setFinalState(String finalState) {
        this.finalState = finalState;
    }
}

