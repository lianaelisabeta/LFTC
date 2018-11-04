package com.company;

import com.company.exceptions.MyExc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AnalizorLexical {
    private List<Atom> atomi;
    private SimboluriSpeciale simboluriSpeciale;
    private FIP fip;
    private TS ts;
    private CodificareAtomi codificareAtomi;
   // private List<Integer> pozitii = new ArrayList<>();


    public AnalizorLexical(){
        atomi= new ArrayList<>();
        fip = new FIP();
        ts = new TS();
        codificareAtomi = new CodificareAtomi();
        simboluriSpeciale = new SimboluriSpeciale();
    }


    public void getAtomi(String filename){
        int l=1;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String st;
            while ((st = br.readLine()) != null)
            {String patternSeparatori= "((?<=;)|(?=;)|(?<=\\{)|(?=\\{)|(?<=\\})|(?=\\})|(?<=[)])|(?=[)]))|(?<=[(])|(?=[(])";
                String patternOperatori= "((?<=\\+)|(?=\\+)|(?<=\\-)|(?=\\-)|(?<=\\*)|(?=\\*)|(?<=\\/)|(?=\\/)|(?<=\\=)|(?=\\=)|(?<=\\<)|(?=\\<)|(?<=\\>)|(?=\\>)|(?<=\\<=)|(?=\\<=)|(?<=\\>=)|(?=\\>=)|(?<=\\==)|(?=\\==)|(?<=\\!=)|(?=\\!=)|(?<=\\>>)|(?=\\>>)|(?<=\\<<)|(?=\\<<))";

                String[] tokens= st.split("\\s+|"+patternSeparatori+"|"+patternOperatori );
                for(int i=0;i<tokens.length;i++){

                    if(!tokens[i].contains(" ") && tokens[i].length()>0) {

                        if(i<tokens.length-1 &&simboluriSpeciale.eOperator(tokens[i]+tokens[i+1]))
                        {
                            tokens[i]+=tokens[i+1];

                            int cod = codificareAtomi.getCod(tokens[i]+tokens[i+1]);
                            atomi.add(new Atom(tokens[i], cod, l));

                            i=i+2;
                        }

                        if (simboluriSpeciale.eSeparator(tokens[i]) || simboluriSpeciale.eCuvantRezervat(tokens[i]) || simboluriSpeciale.eOperator(tokens[i])) {

                            boolean ok= false;

                            int cod = codificareAtomi.getCod(tokens[i]);
                            atomi.add(new Atom(tokens[i], cod, l));


                        }
                        else if(eIdentificator(tokens[i])){
                            if(tokens[i].length()>8){
                                throw new MyExc("Identificator too long at line :"+l+". Lenght should be < than 8 characters.");
                            }


                            atomi.add(new Atom(tokens[i],1,l));
                            ts.adaugaTS(tokens[i]);
                        }
                         else if(eConstanta(tokens[i])){

                            atomi.add(new Atom(tokens[i],0,l));
                            ts.adaugaTS(tokens[i]);
                        }

                        else{
                            boolean ok=false;
                            if(tokens[i].contains("#include")){
                                String s = tokens[i] + tokens[i+1]+tokens[i+2]+tokens[i+3];
                                if(simboluriSpeciale.eCuvantRezervat(s))
                                {int cod = codificareAtomi.getCod(s);
                                atomi.add(new Atom(s, cod, l));

                                i=i+3;
                                ok=true;
                                }
                            }
                           else if(tokens[i].contains("std")){
                                String s = tokens[i] + tokens[i+1]+tokens[i+2]+tokens[i+3];
                                if(simboluriSpeciale.eCuvantRezervat(s))
                                {int cod = codificareAtomi.getCod(s);
                                    atomi.add(new Atom(s, cod, l));

                                    i=i+3;
                                    ok=true;
                                }
                            }
                           if(!ok)
                           {throw new MyExc("Unrecognized Token "+ tokens[i] +"at line: "+l);}
                        }
                    }
        }
               l++;

            }

    }
        catch (IOException e){
            throw new MyExc("Fisier invalid.");
        }
        for(Atom atom : atomi){
            int poz = ts.getTS(atom.getDenumire());
            fip.addToFIP(atom.getCodAtom(),poz);
        }

    }



    public void printAtoms(String fileFIP, String fileTS){
        for(Atom a: atomi){
            System.out.println("|"+a.getDenumire()+"|"+a.getLinie()+"|"+a.getCodAtom());
        }
        System.out.println("FIP--------------------------------------------------");
        fip.printFIP();
        System.out.println("TS--------------------------------------------");
        ts.printTS();

        fip.writeToFileFIP(fileFIP);
        ts.writeToFileTS(fileTS);
    }



    private boolean eIdentificator(String secv){
        String pattern ="[a-zA-Z]+[0-9a-zA-Z]*";
        return Pattern.matches(pattern,secv);
    }

    private boolean eConstanta(String secv){
        String pattern_int = "[-]?[1-9]?[0-9]*|[0]";
        String pattern_char="\'[a-zA-Z0-9.!?:,-]\'";
        String pattern_string="\"[a-zA-Z0-9.!?:,-]+\"";
        String pattern_double = "("+pattern_int+")[\\.][0-9]+";
        if(Pattern.matches(pattern_int,secv) || Pattern.matches(pattern_char,secv) || Pattern.matches(pattern_string,secv) || Pattern.matches(pattern_double,secv)){
            return true;
        }
        return false;

    }

}
