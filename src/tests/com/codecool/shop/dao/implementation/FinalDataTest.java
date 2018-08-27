package com.codecool.shop.dao.implementation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FinalDataTest {
    FinalData testFinalData = new FinalData();

    @Test
    public void testSetFinalPrice() {
        testFinalData.setFinalOrderPrice(300);
        assertEquals(300, testFinalData.getFinalOrderPrice());
    }
}