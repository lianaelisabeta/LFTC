package com.company;

import java.util.Scanner;

public class Main {



    static void printMenu(){
        System.out.println("Optiuni:");
        System.out.println("1 Afiseaza multimea starilor");
        System.out.println("2 Afiseaza alfabetul");
        System.out.println("3 Afiseaza starea initiala");
        System.out.println("4 Afiseaza multimea tranzitiilor");
        System.out.println("5 Afiseaza multimea starilor finale");
        System.out.println("6 Verifica secventa");
        System.out.println("0 Exit");

    }


    static void runMenu(String filename){
        Scanner scanner = new Scanner(System.in);

        AF af = new AF(filename);
        boolean ok =true;
        while(ok){
            printMenu();
            try{
                System.out.println("Alegeti optiunea: ");
                int cmd = Integer.valueOf(scanner.nextLine());
                switch (cmd){
                    case 0:
                        ok= false;
                        break;
                    case 1:
                        af.getStates().forEach(s -> System.out.print(s+" "));
                        System.out.println();
                        break;
                    case 2:
                        af.getAlfabet().forEach(s -> System.out.print(s+" "));
                        System.out.println();
                        break;
                    case 3:
                        System.out.println(af.getInitialState());
                        break;
                    case 4:
                        af.getTransitions().forEach((c,tr) -> tr.forEach(t -> System.out.println(t.getInitialState()+" "+ t.getFinalState()+" "+c)));
                        break;
                    case 5:
                        af.getFinalStates().forEach(s -> System.out.print(s+" "));
                        System.out.println();
                        break;
                    case 6:
                        System.out.println("Introduceti secventa: ");
                        String seq = scanner.nextLine();
                        //scanner.nextLine();
                        af.isAccepted(seq);
                        System.out.println();
                        break;
                     default:
                         System.out.println("Optiune invalida");
                         break;
                }
            }
            catch (Exception exc){
                System.err.println("Comanda invalida");
            }

        }
    }
    public static void main(String[] args) {
        String filename="af_c_c_plus_plus.txt";//"af_sem_1.txt";
	    runMenu(filename);
    }
}
