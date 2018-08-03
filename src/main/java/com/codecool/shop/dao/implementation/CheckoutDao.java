package com.codecool.shop.dao.implementation;

import java.util.Map;

public class CheckoutDao {
    private Map<String, String[]> data;
    private static CheckoutDao instance = null;

    public static CheckoutDao getInstance() {
        if (instance == null) {
            instance = new CheckoutDao();
        }
        return instance;
    }

    public void add(Map<String, String[]> data) {
        this.data = data;
    }

    public Map<String, String[]> getAll() {
        return data;
    }
}
