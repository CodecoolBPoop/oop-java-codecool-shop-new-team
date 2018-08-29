package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJDBC implements SupplierDao {

    private static SupplierDaoJDBC instance;

    JDBCConnection conn = new JDBCConnection();
    PreparedStatement preparedStatement = null;
    Connection con = conn.getConnection();

    private SupplierDaoJDBC() throws SQLException {
    }

    public static SupplierDaoJDBC getInstance() throws SQLException {
        if (instance == null) {
            instance = new SupplierDaoJDBC();
        }
        return instance;
    }


    @Override
    public void add(Supplier supplier) {
        String addSupplierQuery = "INSERT INTO supplier (name,  description) " +
                "VALUES (?,?);";

        try {
            preparedStatement = con.prepareStatement(addSupplierQuery);
            preparedStatement.setString(1,supplier.getName());
            preparedStatement.setString(2, supplier.getDescription());
            preparedStatement.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /*private void setId(Supplier supplier) throws SQLException {
        String findSupplierIdQuery = "SELECT id FROM supplier WHERE name = ?";
        preparedStatement = con.prepareStatement(findSupplierIdQuery);
        preparedStatement.setString(1, supplier.getName());
        //preparedStatement.setString(2, supplier.getDescription());
        preparedStatement.executeQuery();
        con.commit();
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("the id:" + resultSet.getString("id"));
        supplier.setId(resultSet.getInt("id"));
    }*/

    public int getLastId() throws SQLException {
        String findSupplierIdQuery = "SELECT id FROM supplier WHERE id = (SELECT max(id) FROM supplier)";

        preparedStatement = con.prepareStatement(findSupplierIdQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        int lastId = 0;
        if (resultSet.next()) {
            lastId = resultSet.getInt("id");
            return lastId;
        } return lastId;
    }

    @Override
    public Supplier find(int id) throws SQLException {
        String findSupplierQuery = "SELECT * FROM supplier WHERE id = ?";

        preparedStatement = con.prepareStatement(findSupplierQuery);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Supplier supplier = new Supplier(resultSet.getString("name"), resultSet.getString("description"));
            supplier.setId(resultSet.getInt("id"));
            return supplier;
        } return null;
    }

    @Override
    public void remove(int id) {
        String removeSupplierQuery = "DELETE FROM supplier WHERE id = ?";

        try {
            preparedStatement = con.prepareStatement(removeSupplierQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Supplier> getAll() throws SQLException {
        String findSupplierQuery = "SELECT * FROM supplier";

        preparedStatement = con.prepareStatement(findSupplierQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Supplier> allSupplier = new ArrayList<>();

        while(resultSet.next()) {
            Supplier supplier = new Supplier(resultSet.getString("name"), resultSet.getString("description"));
            supplier.setId(resultSet.getInt("id"));
            allSupplier.add(supplier);
        }
        return allSupplier;
    }
}
