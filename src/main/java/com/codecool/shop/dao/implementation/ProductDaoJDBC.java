package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProductDaoJDBC implements ProductDao {

    JDBCConnection conn = new JDBCConnection();
    PreparedStatement preparedStatement = null;
    Connection con = conn.getConnection();

    public ProductDaoJDBC() throws SQLException {
    }

    @Override
    public void add(Product product) {
        String addProductQuery = "INSERT INTO product (name, price, currency, description, category_id, supplier_id) " +
                "VALUES (?,?,?,?,?,?);";

        try {
            preparedStatement = con.prepareStatement(addProductQuery);
            preparedStatement.setString(1,product.getName());
            preparedStatement.setFloat(2, product.getDefaultPrice());
            preparedStatement.setString(3, product.getDefaultCurrency().toString());
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.setInt(5, product.getProductCategory().getId());
            preparedStatement.setInt(6, product.getSupplier().getId());
            preparedStatement.executeQuery();
            con.commit();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Product find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }
}
