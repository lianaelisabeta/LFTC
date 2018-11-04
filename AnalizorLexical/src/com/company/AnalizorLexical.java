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
    private List<Integer> pozitii = new ArrayList<>();


    public AnalizorLexical(){
        atomi= new ArrayList<>();
        fip = new FIP();
        ts = new TS();
        codificareAtomi = new CodificareAtomi();
        simboluriSpeciale = new SimboluriSpeciale();
    }


    public void getAtomi(String filename){
        int l=1;
        List<Atom> atoms= new ArrayList<>();

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
                            atoms.add(new Atom(tokens[i], cod, l));
                            fip.addToFIP(cod,-1);
                            i++;
                        }

                        if (simboluriSpeciale.eSeparator(tokens[i]) || simboluriSpeciale.eCuvantRezervat(tokens[i]) || simboluriSpeciale.eOperator(tokens[i])) {

                            boolean ok= false;

                            int cod = codificareAtomi.getCod(tokens[i]);
                            atoms.add(new Atom(tokens[i], cod, l));
                            fip.addToFIP(cod,-1);

                        }
                        else if(eIdentificator(tokens[i])){
                            if(tokens[i].length()>8){
                                throw new MyExc("Identificator too long at line :"+l+". Lenght should be < than 8 characters.");
                            }
                            if(ts.getTS(tokens[i])==-1){
                                ts.adaugaTS(tokens[i]);
                            }
                            pozitii.add(fip.getSize());
                            fip.addToFIP(1,ts.getTS(tokens[i]));

                            atoms.add(new Atom(tokens[i],l));}
                         else if(eConstanta(tokens[i])){
                            if(ts.getTS(tokens[i])==-1){
                                ts.adaugaTS(tokens[i]);

                            }
                            pozitii.add(fip.getSize());
                            fip.addToFIP(0,ts.getTS(tokens[i]));

                            atoms.add(new Atom(tokens[i],l));
                        }

                        else{
                            boolean ok=false;
                            if(tokens[i].contains("#include")){
                                String s = tokens[i] + tokens[i+1]+tokens[i+2]+tokens[i+3];
                                if(simboluriSpeciale.eCuvantRezervat(s))
                                {int cod = codificareAtomi.getCod(s);
                                atoms.add(new Atom(s, cod, l));
                                fip.addToFIP(cod,-1);
                                i=i+3;
                                ok=true;
                                }
                            }
                           else if(tokens[i].contains("std")){
                                String s = tokens[i] + tokens[i+1]+tokens[i+2]+tokens[i+3];
                                if(simboluriSpeciale.eCuvantRezervat(s))
                                {int cod = codificareAtomi.getCod(s);
                                    atoms.add(new Atom(s, cod, l));
                                    fip.addToFIP(cod,-1);
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
        atomi=atoms;

    }
        catch (IOException e){
            throw new MyExc("Fisier invalid.");
        }


        for(int i =0;i<pozitii.size();i++){
            int cod =-1;

            if(eConstanta(ts.getPoz(i))){
                cod =0;

            }
            else if (eIdentificator(ts.getPoz(i))){
                cod = 1;
            }
            fip.setToFIP(pozitii.get(i),cod,ts.getTS(ts.getPoz(i)));
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
