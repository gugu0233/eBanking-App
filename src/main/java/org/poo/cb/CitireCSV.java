package org.poo.cb;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CitireCSV {
    public static double getRata(String primaValoare,String aDouaValoare) {
        
        String fileName = "src/main/resources/common/exchangeRates.csv";

        // Citirea și stocarea datelor într-un vector
        String[] lines = readCSV(fileName);
        int indice = 0;
        String[] values = lines[0].split(",");
        for (int i = 0; i < values.length; i++)
            if (values[i].equals(aDouaValoare))
                indice = i;

        for (int i = 1; i < lines.length; i++) {
            values = lines[i].split(",");
            if (values[0].equals(primaValoare))
                //System.out.println(values[0] + values[indice]);
                //return values[indice];
                return Double.parseDouble(values[indice]);
        }
       return 0;
    }

    private static  String[] readCSV(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            long lineCount = br.lines().count();
            String[] lines = new String[(int) lineCount];
            try (BufferedReader br2 = new BufferedReader(new FileReader(fileName))) {
                for (int i = 0; i < lineCount; i++) {
                    lines[i] = br2.readLine();
                }
            }
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0];
        }
    }
}
