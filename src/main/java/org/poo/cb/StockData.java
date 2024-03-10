package org.poo.cb;

import java.util.List;

class StockData {
    private String symbol;
    private List<Double> prices;

    public StockData(String symbol, List<Double> prices) {
        this.symbol = symbol;
        this.prices = prices;
    }

    public String getSymbol() {
        return symbol;
    }

    public List<Double> getPrices() {
        return prices;
    }
}

