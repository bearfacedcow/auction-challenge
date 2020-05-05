package com.sortable.challenge.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SiteTest {

    private Site site;

    @BeforeEach
    void setUp() {
        site = new Site("test.com", 25);
    }

    @Test
    void testInit() {
        assertEquals("test.com", site.getName());
        assertEquals(25, site.getFloor());
    }

    @Test
    void testAddBidder() {
        site.addBidder(new Bidder("TEST1", 0));
        assertTrue(site.getBidder("TEST1").isPresent());
    }
}
