package com.codecool.shop.dao;

import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    void add(Product product) throws SQLException;
    Product find(int id) throws SQLException;
    void remove(int id) throws SQLException;

    List<Product> getAll() throws SQLException;
    List<Product> getBy(Supplier supplier) throws SQLException;
    List<Product> getBy(ProductCategory productCategory) throws SQLException;

}
