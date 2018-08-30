package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoJDBC implements ProductCategoryDao {

    private static ProductCategoryDaoJDBC instance = null;

    JDBCConnection conn = new JDBCConnection();
    PreparedStatement preparedStatement = null;
    Connection con = conn.getConnection();

    private ProductCategoryDaoJDBC() throws SQLException {
    }

    public static ProductCategoryDaoJDBC getInstance() throws SQLException {
        if (instance == null) {
            instance = new ProductCategoryDaoJDBC();
        }
        return instance;
    }


    @Override
    public void add(ProductCategory category) {
        String addCategoryQuery = "INSERT INTO category (name,  description, department) " +
                "VALUES (?,?,?);";

        try {
            preparedStatement = con.prepareStatement(addCategoryQuery);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDepartment());
            preparedStatement.setString(3, category.getDescription());
            preparedStatement.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ProductCategory find(int id) throws SQLException {
        String findCategoryQuery = "SELECT * FROM category WHERE id = ?";

        preparedStatement = con.prepareStatement(findCategoryQuery);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            ProductCategory category = new ProductCategory(resultSet.getString("name"), resultSet.getString("description"), resultSet.getString("department"));
            category.setId(resultSet.getInt("id"));
            return category;
        } return null;
    }

    @Override
    public ProductCategory findByName(String name) throws SQLException {
        String findCategoryQuery = "SELECT * FROM category WHERE name = ?";

        preparedStatement = con.prepareStatement(findCategoryQuery);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            ProductCategory category = new ProductCategory(resultSet.getString("name"), resultSet.getString("description"), resultSet.getString("department"));
            category.setId(resultSet.getInt("id"));
            return category;
        } return null;
    }

    @Override
    public void remove(int id) {
        String removeCategoryQuery = "DELETE FROM category WHERE id = ?";
        try {
            preparedStatement = con.prepareStatement(removeCategoryQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public int getLastId() throws SQLException {
        String findCategoryIdQuery = "SELECT id FROM category WHERE id = (SELECT max(id) FROM category)";

        preparedStatement = con.prepareStatement(findCategoryIdQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        int lastId = 0;
        if (resultSet.next()) {
            lastId = resultSet.getInt("id");
            return lastId;
        } return lastId;
    }

    @Override
    public List<ProductCategory> getAll() throws SQLException {
        String findCategoryQuery = "SELECT * FROM category ORDER BY id;";

        preparedStatement = con.prepareStatement(findCategoryQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<ProductCategory> allCategory = new ArrayList<>();

        while(resultSet.next()) {
            ProductCategory category = new ProductCategory(resultSet.getString("name"), resultSet.getString("description"), resultSet.getString("department"));
            category.setId(resultSet.getInt("id"));
            allCategory.add(category);
        }
        return allCategory;
    }

    public void deleteDataFromTable(){

        String removeCategoryQuery = "DELETE FROM category";

        try {
            preparedStatement = con.prepareStatement(removeCategoryQuery);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void resetId() {
        String resetIdQuery = "ALTER SEQUENCE category_id_seq RESTART WITH 1;";
        try {
            preparedStatement = con.prepareStatement(resetIdQuery);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void startId() {
        String resetIdQuery =
                "UPDATE category SET id=nextval('category_id_seq');";
        try {
            preparedStatement = con.prepareStatement(resetIdQuery);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
