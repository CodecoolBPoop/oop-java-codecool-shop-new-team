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

    private CartItems cartItemsTest = CartItems.getInstance();
    ProductDao productDataStoreTest = ProductDaoMem.getInstance();
    Supplier testSupplier = new Supplier("Test", "Testsupplier");
    ProductCategory testCategory = new ProductCategory("Testcategory", "Testdepartment", "Testdescr");
    Product testProduct = new Product("TV", 100, "USD", "nice TV", testCategory, testSupplier);
    Product testProduct2 = new Product("DVD", 150, "USD", "nice DVD", testCategory, testSupplier);

    @BeforeEach
    public void init() throws SQLException {
    productDataStoreTest.add(testProduct);
    cartItemsTest.add(1);
    }

    @Test
    public void testAddProductNotInCart() throws SQLException {
        assertEquals(testProduct, productDataStoreTest.find(1));
    }

    @Test
    public void testAddSameProductToCart() throws SQLException {
        cartItemsTest.add(1);
        OrderItem testOrderItem = cartItemsTest.find(1);
        assertEquals(2, testOrderItem.getQuantity());
        assertEquals("200", testOrderItem.getPrice());
        cartItemsTest.remove(1);
    }

    @Test
    public void testDecreaseProductFromCart() throws SQLException {
        cartItemsTest.add(1);
        cartItemsTest.remove(1);
        OrderItem testOrderItem = cartItemsTest.find(1);
        assertEquals(1, testOrderItem.getQuantity());
        assertEquals("100", testOrderItem.getPrice());
    }

    @Test
    public void testRemoveLastItemFromCart() throws SQLException {
        cartItemsTest.remove(1);
        assertEquals(0, cartItemsTest.getAll().size());
        cartItemsTest.add(1);
    }

    @Test
    public void testGetAll() {
        List<OrderItem> expected = new ArrayList<>();
        OrderItem testOrderItem = new OrderItem(1, "TV", 1, 100, Currency.getInstance("USD"));
        expected.add(testOrderItem);
        assertEquals(expected.get(0).getName(), cartItemsTest.getAll().get(0).getName());
        assertEquals(expected.size(), cartItemsTest.getAll().size());
    }

    @AfterEach
    public void destroy() throws SQLException {
        cartItemsTest.remove(1);
        productDataStoreTest.remove(1);
    }


}