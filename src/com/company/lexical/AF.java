package com.company.lexical;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AF {
    private List<String> states;
    private List<Character> alfabet;
    private String initialState;
    private List<String> finalStates;

    private Map<Character,List<Transition>> transitions= new HashMap<>();


    public AF(String filename) {
        loadFromFile(filename);
    }

    private void loadFromFile(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String st;
            while ((st = br.readLine()) != null) {
                lines.add(st);
            }


        } catch (IOException exc) {
            System.err.println(exc.getMessage());
        }
        states = Arrays.stream(lines.get(0).split(" ")).collect(Collectors.toList());
        alfabet = Arrays.stream(lines.get(1).split(" ")).map(s -> s.charAt(0)).collect(Collectors.toList());
        initialState = lines.get(2);
        finalStates = Arrays.stream(lines.get(3).split(" ")).collect(Collectors.toList());

        for (int i = 4; i < lines.size(); i++) {
            String[] tr = lines.get(i).split(" ");
            // transitions.add(new Transition(tr[0], tr[1], tr[2].charAt(0)));
            if(tr[2].length()>1){
                String[] consts = tr[2].split("[|]");
                for(String s :consts)
                {if(!transitions.containsKey(s.charAt(0))){
                    List<Transition> trs = new ArrayList<>();
                    trs.add(new Transition(tr[0],tr[1]));
                    transitions.put(s.charAt(0),trs);}
                    else{
                        transitions.get(s.charAt(0)).add(new Transition(tr[0],tr[1]));
                }
                }
            }
           else if(!transitions.containsKey(tr[2].charAt(0))){
                List<Transition> trs = new ArrayList<>();
                trs.add(new Transition(tr[0],tr[1]));
                transitions.put(tr[2].charAt(0),trs);

            }
            else{transitions.get(tr[2].charAt(0)).add(new Transition(tr[0],tr[1]));}
        }
    }

    private String checkSequence(String sequence){
        String current_state = this.initialState;
        boolean accepted = true;
        List<Character> seq = Arrays.stream(sequence.split("")).map(s -> s.charAt(0)).collect(Collectors.toList());
        int i;

        for(i=0;i<seq.size();i++){
            final String cs = current_state;
            Optional<List<Transition>> transitionList = getListTransitions(seq.get(i));
            if(!transitionList.isPresent()){
                break;
            }
            Optional<Transition> transition = transitionList.get().stream().filter(transition1 -> transition1.getInitialState().equals(cs)).findFirst();

            if(!transition.isPresent()){
                break;
            }
            else if(!transition.get().getInitialState().equals(current_state)){
                break;
            }
            else{
                if(i== seq.size()-1){
                    if(!finalStates.contains(transition.get().getFinalState())){
                        break;
                    }

                }
                current_state= transition.get().getFinalState();

            }

        }

        return sequence.substring(0,i);

    }

    public boolean isAccepted(String sequence){
        String acceptedSeq = checkSequence(sequence);
        return acceptedSeq.equals(sequence);
}

    private Optional<List<Transition>> getListTransitions(Character c){
        return Optional.ofNullable(transitions.get(c));
    }
    public List<String> getStates() {
        return states;
    }

    public List<Character> getAlfabet() {
        return alfabet;
    }

    public String getInitialState() {
        return initialState;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public Map<Character,List<Transition>> getTransitions() {
        return transitions;
    }
}

