package com.codecool.shop.dao.implementation;

public class FinalData {
    private double finalOrderPrice;
    private static FinalData instance = null;

    public static FinalData getInstance() {
        if (instance == null) {
            instance = new FinalData();
        }
        return instance;
    }

    public void setFinalOrderPrice(double finalOrderPrice) {
        this.finalOrderPrice = finalOrderPrice;
    }

    public double getFinalOrderPrice() {
        return finalOrderPrice;
    }
}
