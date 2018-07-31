package com.codecool.shop.dao;

import com.codecool.shop.model.OrderItem;

import java.util.List;

public interface CartDao {

    void add(int id);
    void remove(int id);
    OrderItem find(int id);
    List<OrderItem> getAll();


}
