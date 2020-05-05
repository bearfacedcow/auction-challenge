package com.sortable.challenge.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BidTest {

    private Bid bid;

    @BeforeEach
    void setUp() {
        bid = new Bid(new Bidder("TEST1", 0), "banner", 31);
}

    @Test
    void testInit() {
        Bidder bidder = bid.getBidder();
        assertEquals("TEST1", bidder.getName());
        assertEquals("banner", bid.getUnit());
        assertEquals(31, bid.getBid());
    }
}
