package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoMemTest {

    SupplierDaoMem testSupplierDaoMem = SupplierDaoMem.getInstance();
    Supplier testSupplier = new Supplier("TestSupplier", "Testing");

    @BeforeEach
    public void init(){
        testSupplierDaoMem.add(testSupplier);
    }

    @Test
    public void testAddOneSupplier() {
        assertEquals(1, testSupplierDaoMem.getAll().size());
        assertEquals(testSupplier, testSupplierDaoMem.getAll().get(0));
    }

    @Test
    public void testAddSameSupplier(){
        Supplier testSupplier = new Supplier("TestSupplier", "Testing");
        testSupplierDaoMem.add(testSupplier);
        System.out.println(testSupplierDaoMem.getAll());
    }

    @Test
    public void testFind() {
        Supplier testing = testSupplierDaoMem.find(1);
        assertEquals(testSupplier, testing);
    }

    @Test
    public void testNotFound() {
        Supplier testing = testSupplierDaoMem.find(2);
        assertEquals(null, testing);
    }

    @Test
    public void testRemove() {
        testSupplierDaoMem.remove(1);
        assertEquals(0, testSupplierDaoMem.getAll().size());
    }

    @Test
    public void testGetAll() {
        List<Supplier> expected = new ArrayList<>();
        expected.add(testSupplier);
        assertEquals(expected.get(0).getName(), testSupplierDaoMem.getAll().get(0).getName());
        assertEquals(expected.size(), testSupplierDaoMem.getAll().size());
    }

    @AfterEach
    public void destroy(){
        testSupplierDaoMem.remove(1);
    }
}