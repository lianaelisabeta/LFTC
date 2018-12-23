package com.company.utils;

import com.company.sintactic.Productie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {
    private List<String> terminali = new ArrayList<>();
    private List<String> neterminali = new ArrayList<>();
    private List<Productie> productii = new ArrayList<>();

    public FileUtils(String filename){
        loadProductii(filename);
    }
    public void loadProductii(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while (null != (line = br.readLine())) {
                String[] str = line.split("->");
                productii.add(new Productie(str[0], str[1]));
                neterminali.add(str[0].replace(" ",""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> rightParts = productii.stream()
                .map(productie -> productie.getRight())
                .map(right -> right.replace("|","")).collect(Collectors.toList());

        for(String right : rightParts){
            List<String> parts = Arrays.asList(right.split(" "));
            parts = parts.stream().filter(part -> !part.matches("[A-Z_\t]+")).distinct().collect(Collectors.toList());
            terminali.addAll(parts);
        }

        terminali = terminali.stream().filter(terminal -> terminal.length()>0).distinct().collect(Collectors.toList());

    }

    public List<Productie> getProductii() {
        return productii;
    }

    public List<String> getTerminali() {
        return this.terminali;
    }

    public List<String> getNeterminali() {
        return this.neterminali;
    }
}
