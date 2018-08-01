package com.codecool.shop.model;

import java.util.Currency;

public class OrderItem {
    private int id;
    private String name;
    private int quantity;
    private float price;
    private Currency currency;
    public static int totalItems = 0;
    public static float totalPrice = 0;

    public static String getTotalPrice() {
        return "Total value: " + String.valueOf(totalPrice) + " kUSD";
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

    public float getPrice() {
        return price * quantity;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void increaseQuantity() {
        this.quantity ++;
        totalItems ++;
    }
    public void increaseTotalPrice() {
        totalPrice += price;
    }
}
