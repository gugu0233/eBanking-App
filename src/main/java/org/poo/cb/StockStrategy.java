package org.poo.cb;

interface StockStrategy {
    boolean shouldBuy(double sma5, double sma10);
}
