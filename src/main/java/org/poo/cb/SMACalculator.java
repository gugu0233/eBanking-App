package org.poo.cb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SMACalculator {

    public void afisare(String filename){
        List<StockData> stockDataList = readCSV(filename);
        if (stockDataList != null) {
            StockStrategy smaStrategy = new SMAStrategy();

            System.out.print("{\"stocksToBuy\":[");
            boolean isFirstStock = true;

            for (StockData stockData : stockDataList) {
                double sma5 = calculateSMA(stockData.getPrices(), 5);
                double sma10 = calculateSMA(stockData.getPrices(), 10);

                if (smaStrategy.shouldBuy(sma5, sma10)) {
                    if (!isFirstStock) {
                        System.out.print(",");
                    }
                    System.out.print("\"" + stockData.getSymbol() + "\"");
                    isFirstStock = false;
                }
            }
            System.out.println("]}");
        }
    }

    public List<String> getStocksToBuy(String filename) {
        List<StockData> stockDataList = readCSV(filename);
        List<String> stocksToBuy = new ArrayList<>();

        if (stockDataList != null) {
            StockStrategy smaStrategy = new SMAStrategy(); // strategie de cumparare (sma5>sma10)

            for (StockData stockData : stockDataList) {
                double sma5 = calculateSMA(stockData.getPrices(), 5);
                double sma10 = calculateSMA(stockData.getPrices(), 10);

                if (smaStrategy.shouldBuy(sma5, sma10)) {
                    stocksToBuy.add(stockData.getSymbol());
                }
            }
        }

        return stocksToBuy;
    }
    private static List<StockData> readCSV(String filePath) {
        List<StockData> stockDataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] values = line.split(",");
                String symbol = values[0];
                List<Double> prices = new ArrayList<>();

                for (int i = 1; i < values.length; i++) {
                    prices.add(Double.parseDouble(values[i]));
                }

                stockDataList.add(new StockData(symbol, prices));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stockDataList;
    }

    private static double calculateSMA(List<Double> prices, int period) {
        int startIndex = prices.size() - period;
        int endIndex = prices.size() - 1;
        double sum = 0;
        for (int i = startIndex; i <= endIndex; i++) {
            sum += prices.get(i);
        }
        return sum / period;
    }
}
