package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoMemTest {

    SupplierDaoJDBC testSupplierDao = SupplierDaoJDBC.getInstance();
    Supplier testSupplier = new Supplier("TestSupplier", "Testing");
    Supplier testSupplier2 = new Supplier("TestSupplier2", "Testing");

    SupplierDaoMemTest() throws SQLException {
    }

    @BeforeEach
    public void init(){
        testSupplierDao.add(testSupplier);
    }

    @Test
    public void testAddOneSupplier() throws SQLException {
        int lastSupplier = testSupplierDao.getAll().size();
        int currentSize = testSupplierDao.getAll().size();
        testSupplierDao.add(testSupplier2);
        assertEquals(currentSize + 1, testSupplierDao.getAll().size());
        assertEquals(testSupplier.getName(), testSupplierDao.getAll().get(lastSupplier-1).getName());
        assertEquals(testSupplier.getDescription(), testSupplierDao.getAll().get(lastSupplier-1).getDescription());
        int id = testSupplierDao.getLastId();
        testSupplierDao.remove(id);
    }

    @Test
    public void testFind() throws SQLException {
        int lastId = testSupplierDao.getLastId();
        int lastSupplier = testSupplierDao.getAll().size();
        Supplier latestSupplier = testSupplierDao.getAll().get(lastSupplier-1);
        assertEquals(latestSupplier.getId(), testSupplierDao.find(lastId).getId());
    }

    @Test
    public void testNotFound() throws SQLException {
        Supplier testing = testSupplierDao.find(2);
        assertEquals(null, testing);
    }

    @Test
    public void testRemove() throws SQLException {
        int lastSupplierSize = testSupplierDao.getAll().size();
        int id = testSupplierDao.getLastId();
        testSupplierDao.remove(id);
        assertEquals(lastSupplierSize - 1, testSupplierDao.getAll().size());
    }

    @Test
    public void testGetAll() throws SQLException {
        List<Supplier> expected = new ArrayList<>();
        expected.add(testSupplier);
        int lastSupplier = testSupplierDao.getAll().size() - 1;
        assertEquals(expected.get(0).getName(), testSupplierDao.getAll().get(lastSupplier).getName());
    }

    @AfterEach
    public void destroy() throws SQLException {
        int id = testSupplierDao.getLastId();
        testSupplierDao.remove(id);
    }
}