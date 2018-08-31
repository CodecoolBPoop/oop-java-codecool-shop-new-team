package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.OrderItem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartItemsTest {

    private CartItemsJDBC cartItemsTest = CartItemsJDBC.getInstance();
    ProductDao productDataStoreTest = ProductDaoJDBC.getInstance();
    Supplier testSupplier = new Supplier("Test", "Testsupplier");
    ProductCategory testCategory = new ProductCategory("Testcategory", "Testdepartment", "Testdescr");
    Product testProduct = new Product("TV", 100, "USD", "nice TV", testCategory, testSupplier);
    Product testProduct2 = new Product("DVD", 150, "USD", "nice DVD", testCategory, testSupplier);


    CartItemsTest() throws SQLException {
    }

    @BeforeEach
    public void init() throws SQLException {
    productDataStoreTest.add(testProduct);
    cartItemsTest.add(productDataStoreTest.findByName("TV").getId());
    }

    @Test
    public void testAddProductNotInCart() throws SQLException {
        Product resultProduct = productDataStoreTest.find(cartItemsTest.findByName("TV").getId());
        assertEquals(testProduct.getName(), resultProduct.getName());
    }

    @Test
    public void testAddSameProductToCart() throws SQLException {
        cartItemsTest.add(cartItemsTest.getLastId());
        OrderItem testOrderItem = cartItemsTest.find(cartItemsTest.getLastId());
        assertEquals(2, testOrderItem.getQuantity());
        assertEquals("200", testOrderItem.getPrice());
        cartItemsTest.remove(cartItemsTest.getLastId());
    }

    @Test
    public void testDecreaseProductFromCart() throws SQLException {
        cartItemsTest.add(cartItemsTest.getLastId());
        cartItemsTest.remove(cartItemsTest.getLastId());
        OrderItem testOrderItem = cartItemsTest.find(cartItemsTest.getLastId());
        assertEquals(1, testOrderItem.getQuantity());
        assertEquals("100", testOrderItem.getPrice());
    }

    @Test
    public void testRemoveLastItemFromCart() throws SQLException {
        int lastSize = cartItemsTest.getAll().size();
        cartItemsTest.remove(cartItemsTest.getLastId());
        assertEquals(0,  lastSize - 1);
        cartItemsTest.add(productDataStoreTest.findByName("TV").getId());

    }

    @Test
    public void testGetAll() throws SQLException {
        List<OrderItem> expected = new ArrayList<>();
        OrderItem testOrderItem = new OrderItem(1, "TV", 1, 100, Currency.getInstance("USD"));
        expected.add(testOrderItem);
        assertEquals(expected.size(), cartItemsTest.getAll().size());
    }

    @AfterEach
    public void destroy() throws SQLException {
        cartItemsTest.remove(cartItemsTest.findByName("TV").getId());
        productDataStoreTest.remove(productDataStoreTest.findByName("TV").getId());
    }


}