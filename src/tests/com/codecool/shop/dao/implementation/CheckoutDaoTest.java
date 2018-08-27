package com.codecool.shop.dao.implementation;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutDaoTest {

    CheckoutDao checkoutDaoTest = new CheckoutDao();

    @Test
    public void testAdd() {
        HashMap<String, String[]> testMap = new HashMap<>();
        testMap.put("TestKey", new String[] {"Value1", "Value2"});
        checkoutDaoTest.add(testMap);
        assertEquals(testMap, checkoutDaoTest.getAll());
    }
}