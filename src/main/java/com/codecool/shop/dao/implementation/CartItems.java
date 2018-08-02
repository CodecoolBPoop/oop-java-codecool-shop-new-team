package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.OrderItem;
import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.List;


public class CartItems implements CartDao {

    private List<OrderItem> productList = new ArrayList<>();
    private static CartItems instance = null;

    private CartItems(){
    }

    public static CartItems getInstance() {
        if (instance == null) {
            instance = new CartItems();
        }
        return instance;
    }

    @Override
    public void add(int id) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        Product product = productDataStore.find(id);
        if (findBoolean(id)) {
            OrderItem item = find(id);
            item.increaseQuantity();
            item.increaseTotalPrice();
        } else {
            productList.add(new OrderItem(product.getId(), product.getName(), 1, product.getDefaultPrice(), product.getDefaultCurrency()));
        }

    }

    @Override
    public void remove(int id) {
        OrderItem item = find(id);
        item.decreaseTotalPrice();
        if (item.notLastUnit()) {
            item.decreaseQuantity();
        } else {
            productList.remove(id-1);
        }
    }

    @Override
    public OrderItem find(int id) {
        return productList.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    public boolean findBoolean(int id){
        OrderItem temp = find(id);
        if (temp == null){
            return false;
        }
        return true;
    }


    @Override
    public List<OrderItem> getAll() {
        return productList;
    }
}
