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
    ProductCategoryDaoJDBC testProductCategory = ProductCategoryDaoJDBC.getInstance();
    SupplierDaoJDBC testSupplierJDBC = SupplierDaoJDBC.getInstance();

    ProductCategory testCategory = new ProductCategory("Testcategory", "Testdepartment", "Testdescr");
    Supplier testSupplier = new Supplier("Test", "Testsupplier");
    Product testProduct = new Product("Test Product", 100, "USD", "Testing", testProductCategory.find(testProductCategory.getLastId()), testSupplierJDBC.find(testSupplierJDBC.getLastId()));
    Product testProduct2 = new Product("Test Product 2", 100, "USD", "Testing", testProductCategory.find(testProductCategory.getLastId()), testSupplierJDBC.find(testSupplierJDBC.getLastId()));

    ProductDaoMemTest() throws SQLException {
    }

    @BeforeEach
    public void init() throws SQLException {
        //testProductCategory.add(testCategory);
        //testSupplierJDBC.add(testSupplier);
        testProductDao.add(testProduct);
    }

    @Test
    public void testAddProduct() throws SQLException {
        int lastSize = testProductDao.getAll().size();
        testProductDao.add(testProduct2);
        assertEquals(lastSize + 1, testProductDao.getAll().size());
        assertEquals(testProduct2.getName(), testProductDao.getAll().get(lastSize).getName());
        int productLastId = testProductDao.getLastId();
        testProductDao.remove(productLastId);

    }

    @Test
    public void testFind() throws SQLException {
        int productLastId = testProductDao.getLastId();
        Product testing = testProductDao.find(productLastId);
        assertEquals(testProduct.getName(), testing.getName());
        assertEquals(testProduct.getDescription(), testing.getDescription());
    }

    @Test
    public void testNotFound() throws SQLException {
        Product testing = testProductDao.find(2);
        assertEquals(null, testing);
    }

    @Test
    public void testRemove() throws SQLException {
        int lastSize = testProductDao.getAll().size();
        int productLastId = testProductDao.getLastId();
        testProductDao.remove(productLastId);
        assertEquals(lastSize - 1, testProductDao.getAll().size());
    }

    @Test
    public void testGetAll() throws SQLException {
        List<Product> expected = new ArrayList<>();
        expected.add(testProduct);
        int productLast = testProductDao.getAll().size();
        System.out.println(testProductDao.getAll().get(0).getId());
        assertEquals(expected.get(0).getName(), testProductDao.getAll().get(productLast-1).getName());
    }

    @AfterEach
    public void destroy() throws SQLException {
        int productLastId = testProductDao.getLastId();
        //int supplierLastId = testSupplierJDBC.getLastId();
        //int categoryLastId = testProductCategory.getLastId();
        testProductDao.remove(productLastId);
        //testSupplierJDBC.remove(supplierLastId);
        //testProductCategory.remove(categoryLastId);
    }

}