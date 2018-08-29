package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoMemTest {

    ProductDaoJDBC testProductDao = ProductDaoJDBC.getInstance();
    Supplier testSupplier = new Supplier("Test", "Testsupplier");
    ProductCategory testCategory = new ProductCategory("Testcategory", "Testdepartment", "Testdescr");
    Product testProduct = new Product("Test Product", 100, "USD", "Testing", testCategory, testSupplier);

    ProductDaoMemTest() throws SQLException {
    }

    @BeforeEach
    public void init(){
        testProductDao.add(testProduct);
        System.out.println(testProduct.getId());
    }

    @Test
    public void testAddProduct() {
        assertEquals(1, testProductDao.getAll().size());
        assertEquals(testProduct, testProductDao.getAll().get(0));
    }

    @Test
    public void testFind() {
        Product testing = testProductDao.find(1);
        assertEquals(testProduct, testing);
    }

    @Test
    public void testNotFound() {
        Product testing = testProductDao.find(2);
        assertEquals(null, testing);
    }

    @Test
    public void testRemove() {
        testProductDao.remove(1);
        assertEquals(0, testProductDao.getAll().size());
    }

    @Test
    public void testGetAll() {
        List<Product> expected = new ArrayList<>();
        expected.add(testProduct);
        assertEquals(expected.get(0).getName(), testProductDao.getAll().get(0).getName());
        assertEquals(expected.size(), testProductDao.getAll().size());
    }

    @AfterEach
    public void destroy(){
        testProductDao.remove(1);
    }

}