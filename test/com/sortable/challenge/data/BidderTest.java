package com.sortable.challenge.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BidderTest {

    private Bidder bidder;

    @BeforeEach
    void setUp() {
        bidder = new Bidder("TEST", -0.01);
    }

    @Test
    void testInit() {
        assertEquals("TEST", bidder.getName());
        assertEquals(-0.01, bidder.getAdjustment());
    }

    @Test
    void testCalculateAdjustment() {
        assertEquals(99, bidder.calculateAdjustment(100));
    }
}
