package org.poo.cb;

class SMAStrategy implements StockStrategy {
    @Override
    public boolean shouldBuy(double sma5, double sma10) {
        return sma5 > sma10;
    }
}
