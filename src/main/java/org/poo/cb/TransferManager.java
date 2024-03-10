package org.poo.cb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TransferManager {
    private Map<String, Map<String, Double>> rates;

    public TransferManager(String csvFilePath) {
        rates = new HashMap<>();
        loadExchangeRates(csvFilePath);
    }

    private void loadExchangeRates(String csvFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            String[] currencies = br.readLine().split(",");
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String baseCurrency = values[0];
                Map<String, Double> rateMap = new HashMap<>();
                for (int i = 1; i < values.length; i++) {
                    rateMap.put(currencies[i], Double.parseDouble(values[i]));
                }
                rates.put(baseCurrency, rateMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void transferMoney(Cont sursa, Cont destinatie, double suma) {
        if (sursa == null || destinatie == null) {
            System.out.println("Eroare");
            return;
        }

        String valutaSursa = sursa.getValuta();
        String valutaDestinatie = destinatie.getValuta();

        if (!rates.containsKey(valutaSursa) || !rates.containsKey(valutaDestinatie)) {
            System.out.println("Eroare");
            return;
        }

        double rateSursaToUSD = rates.get(valutaSursa).get("USD");
        double rateDestinatieToUSD = rates.get(valutaDestinatie).get("USD");

        double sumaInUSD = suma / rateSursaToUSD;
        double sumaInDestinatie = sumaInUSD * rateDestinatieToUSD;

        if (sursa.getSuma() >= suma) {
            sursa.setSuma(sursa.getSuma() - suma);
            destinatie.setSuma(destinatie.getSuma() + sumaInDestinatie);
            System.out.println("Transfer realizat cu succes: " + suma + " " + valutaSursa +
                    " din " + sursa.getValuta() + " către " + destinatie.getValuta());
        } else {
            System.out.println("Eroare: Fonduri insuficiente în contul sursă.");
        }
    }
}

