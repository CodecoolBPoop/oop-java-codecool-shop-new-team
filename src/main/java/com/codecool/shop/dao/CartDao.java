package com.codecool.shop.dao;

import com.codecool.shop.model.OrderItem;

import java.sql.SQLException;
import java.util.List;

public interface CartDao {

    void add(int id) throws SQLException;
    void remove(int id) throws SQLException;
    OrderItem find(int id) throws SQLException;
    List<OrderItem> getAll() throws SQLException;


}
