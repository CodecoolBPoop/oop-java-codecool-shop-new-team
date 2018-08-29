package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.OrderItem;
import com.codecool.shop.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class CartItemsJDBC implements CartDao {

    private static CartItemsJDBC instance = null;
    JDBCConnection conn = new JDBCConnection();
    PreparedStatement preparedStatement = null;
    Connection con = conn.getConnection();

    ProductDaoJDBC productDB = ProductDaoJDBC.getInstance();

    public CartItemsJDBC() throws SQLException {
    }

    public static CartItemsJDBC getInstance() throws SQLException {
        if (instance == null) {
            instance = new CartItemsJDBC();
        }
        return instance;
    }

    @Override
    public void add(int id) throws SQLException {
        Product productToAddCart = productDB.find(id);

        String checkExistenceQuery = "SELECT (quantity, totalprice) FROM orderitem WHERE id =?";

        preparedStatement = con.prepareStatement(checkExistenceQuery);
        preparedStatement.setInt(1, id);
        ResultSet resultOrderItem = preparedStatement.executeQuery();

        if(resultOrderItem.next()) {
            String updateOrderItemQuery = "UPDATE orderitem SET quantity = ?, total_price = ? WHERE id = ?";

            preparedStatement = con.prepareStatement(updateOrderItemQuery);
            preparedStatement.setInt(1, resultOrderItem.getInt("quantity") + 1);
            preparedStatement.setFloat(2, resultOrderItem.getFloat("total_price") + productToAddCart.getDefaultPrice());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        }else{
            String createOrderItemQuery = "INSERT INTO orderitem (id, name, quantity, price, total_price, currency) VALUES (?,?,?,?,?,?)";

            preparedStatement = con.prepareStatement(createOrderItemQuery);
            preparedStatement.setInt(1, productToAddCart.getId());
            preparedStatement.setString(2, productToAddCart.getName());
            preparedStatement.setInt(3, 1);
            preparedStatement.setFloat(4, productToAddCart.getDefaultPrice());
            preparedStatement.setFloat(5, productToAddCart.getDefaultPrice());
            preparedStatement.setString(6, productToAddCart.getDefaultCurrency().toString());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void remove(int id) throws SQLException {
        Product productToRemoveFromCart = productDB.find(id);

        String checkIfLastOneQuery = "SELECT (quantity, totalprice) FROM orderitem WHERE id =?";

        preparedStatement = con.prepareStatement(checkIfLastOneQuery);
        preparedStatement.setInt(1, id);
        ResultSet resultOrderItem = preparedStatement.executeQuery();

        if(resultOrderItem.getInt("quantity") != 1) {
            String updateOrderItemQuery = "UPDATE orderitem SET quantity = ?, total_price = ? WHERE id = ?";

            preparedStatement = con.prepareStatement(updateOrderItemQuery);
            preparedStatement.setInt(1, resultOrderItem.getInt("quantity") - 1);
            preparedStatement.setFloat(2, resultOrderItem.getFloat("total_price") - productToRemoveFromCart.getDefaultPrice());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        }else{
            String deleteOrderItemQuery = "DELETE FROM orderitem WHERE id = ?";

            preparedStatement = con.prepareStatement(deleteOrderItemQuery);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public OrderItem find(int id) throws SQLException {
        String findOrderItemQuery = "SELECT * FROM orderitem WHERE id =?";

            preparedStatement = con.prepareStatement(findOrderItemQuery);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getOrderItem(id, resultSet);
    }

    @Override
    public List<OrderItem> getAll() throws SQLException {
        String getAllOrderItemsQuery = "SELECT * FROM orderitems";

            preparedStatement = con.prepareStatement(getAllOrderItemsQuery);
            ResultSet allOrderItems = preparedStatement.executeQuery();
            List <OrderItem> orderItemList = new ArrayList<OrderItem>();
            while (allOrderItems.next()) {
                orderItemList.add(getOrderItem(allOrderItems.getInt("id"), allOrderItems));
            }
            return orderItemList;
    }

    private OrderItem getOrderItem(int OrderItemId, ResultSet resultSet) throws SQLException {
        OrderItem resultOrderItem = new OrderItem(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("quantity"),
                resultSet.getFloat("price"),
                Currency.getInstance(resultSet.getString("curency")));

        return resultOrderItem;
    }
}
