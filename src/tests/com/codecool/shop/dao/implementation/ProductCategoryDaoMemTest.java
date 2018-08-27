package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoMemTest {

    ProductCategoryDaoMem testCategoryDao = ProductCategoryDaoMem.getInstance();
    ProductCategory testCategory = new ProductCategory("Phones", "Telecommunication", "Nice phone");

    @BeforeEach
    void setUp() {
        testCategoryDao.add(testCategory);
    }

    @Test
    public void testAdd() {
        assertEquals(1, testCategoryDao.getAll().size());
    }

    @Test
    public void testFind() {
        assertEquals(testCategory, testCategoryDao.find(1));
    }

    @Test
    public void testRemove() {
        testCategoryDao.remove(1);
        assertEquals(0, testCategoryDao.getAll().size());
    }

    @Test
    public void testGetAll() {
        assertEquals(testCategory.getName(), testCategoryDao.getAll().get(0).getName());
        assertEquals(testCategory.getDepartment(), testCategoryDao.getAll().get(0).getDepartment());
        assertEquals(testCategory.getDescription(), testCategoryDao.getAll().get(0).getDescription());

    }

    @AfterEach
    void tearDown() {
        if (testCategoryDao.getAll().size() != 0) {
            testCategoryDao.remove(1);
        }
    }
}