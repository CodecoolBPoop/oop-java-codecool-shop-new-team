package com.codecool.shop.dao.implementation;

import java.sql.*;

public class JDBCConnection {

    private static final String DATABASE = System.getenv("DATABASE_ENV");
    private static final String DB_USER = System.getenv("USER_ENV");
    private static final String DB_PASSWORD = System.getenv("PASSWORD_ENV");


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }



    public void executeQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
        ){
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
