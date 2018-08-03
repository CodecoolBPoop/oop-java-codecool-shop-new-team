package com.codecool.shop.model;

import java.util.Currency;
import java.text.DecimalFormat;

public class OrderItem {
    private int id;
    private String name;
    private int quantity;
    private double price;
    private Currency currency;
    public static int totalItems = 0;
    public static double totalPrice = 0;

    static double roundedTotalPrice (double origTotalPrice){
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(origTotalPrice));
    }

    public static String getTotalPrice() {
        return "Total value: " + String.valueOf(roundedTotalPrice(totalPrice)) + " USD";
    }

    public static double getRoundedTotalPrice() {
        return roundedTotalPrice(totalPrice);
    }

    public OrderItem(int id, String name, int quantity, float price, Currency currency) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
        this.currency = currency;
        totalItems ++;
        totalPrice += price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getId() {
        return id;
    }

    public String getPrice() {
        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        return twoDecimals.format(price * quantity);
    }

    public Currency getCurrency() {
        return currency;
    }

    public void increaseQuantity() {
        this.quantity ++;
        totalItems ++;
    }

    public void decreaseQuantity() {
        this.quantity --;
        totalItems --;
    }

    public void increaseTotalPrice() {
        totalPrice += price;
    }

    public void decreaseTotalPrice() {
        totalPrice -= price;
    }

    public boolean notLastUnit() {
        if (quantity > 1){
            return true;
        }
        decreaseQuantity();
        decreaseTotalPrice();
        return false;
    }
}
