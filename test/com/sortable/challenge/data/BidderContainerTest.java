package com.sortable.challenge.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BidderContainerTest {

    private BidderContainer bidderContainer;

    @BeforeEach
    void setUp() {
        bidderContainer = new BidderContainer();
    }

    @Test
    void testInit() {
        assertNotNull(bidderContainer.getBidders());
        assertEquals(0, bidderContainer.getBidders().size());
    }

    @Test
    void testAddBidder() {
        bidderContainer.add(new Bidder("TEST", 0));
        assertEquals(1, bidderContainer.getBidders().size());
    }

    @Test
    void testGetBidder() {
        bidderContainer.add(new Bidder("TEST1", 0));
        bidderContainer.add(new Bidder("TEST2", .02));
        Optional<Bidder> test1 = bidderContainer.getBidder("TEST1");
        assertTrue(test1.isPresent());
        assertEquals("TEST1", test1.get().getName());
    }

    @Test
    void testForNonExistentBidder() {
        bidderContainer.add(new Bidder("TEST1", 0));
        bidderContainer.add(new Bidder("TEST2", .02));
        assertFalse(bidderContainer.getBidder("TEST3").isPresent());
    }
}
