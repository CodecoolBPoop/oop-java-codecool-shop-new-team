package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoMemTest {

    ProductCategoryDaoJDBC testCategoryDao = ProductCategoryDaoJDBC.getInstance();
    ProductCategory testCategory = new ProductCategory("Phones", "Telecommunication", "Nice phone");
    ProductCategory testCategory2 = new ProductCategory("Phones 2", "Telecommunication", "Nice phone");

    ProductCategoryDaoMemTest() throws SQLException {
    }

    @BeforeEach
    void setUp() {
        testCategoryDao.add(testCategory);
    }

    @Test
    public void testAdd() throws SQLException {
        int lastCategory = testCategoryDao.getAll().size() - 1;
        int currentSize = testCategoryDao.getAll().size();
        testCategoryDao.add(testCategory2);
        assertEquals(currentSize + 1, testCategoryDao.getAll().size());
        assertEquals(testCategory.getName(), testCategoryDao.getAll().get(lastCategory).getName());
        assertEquals(testCategory.getDescription(), testCategoryDao.getAll().get(lastCategory).getDescription());
        int id = testCategoryDao.getLastId();
        testCategoryDao.remove(id);
    }

    @Test
    public void testFind() throws SQLException {
        int lastId = testCategoryDao.getLastId();
        int lastCategory = testCategoryDao.getAll().size();
        ProductCategory latestCategory = testCategoryDao.getAll().get(lastCategory-1);
        assertEquals(latestCategory.getId(), testCategoryDao.find(lastId).getId());
    }

    @Test
    public void testRemove() throws SQLException {
        testCategoryDao.add(testCategory2);
        int currentSize = testCategoryDao.getAll().size();
        int lastId = testCategoryDao.getLastId();
        testCategoryDao.remove(lastId);
        assertEquals(currentSize - 1, testCategoryDao.getAll().size());
    }

    @Test
    public void testGetAll() throws SQLException {
        int lastCategory = testCategoryDao.getAll().size() - 1;
        assertEquals(testCategory.getName(), testCategoryDao.getAll().get(lastCategory).getName());
        assertEquals(testCategory.getDepartment(), testCategoryDao.getAll().get(lastCategory).getDepartment());
        assertEquals(testCategory.getDescription(), testCategoryDao.getAll().get(lastCategory).getDescription());

    }

    @AfterEach
    void tearDown() throws SQLException {
        if (testCategoryDao.getAll().size() != 0) {
            int id = testCategoryDao.getLastId();
            testCategoryDao.remove(id);
        }
    }
}