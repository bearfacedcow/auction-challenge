package com.sortable.challenge.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuctionTest {

    private Auction auction;
    private Bidder test1;
    private Bidder test2;

    @BeforeEach
    void setUp() {
        Site site = new Site("test.com", 30);
        test1 = new Bidder("TEST1", 0);
        test2 = new Bidder("TEST2", -0.01);
        site.addBidder(test1);
        site.addBidder(test2);

        auction = new Auction(site);
        auction.addUnit("banner");
    }

    @Test
    void testInit() {
        assertNotNull(auction.getSite());
    }

    @Test
    void testAddUnit() {
        auction.addUnit("sidebar");
        assertEquals(2, auction.getUnits().size());
    }

    @Test
    void testGetUnit() {
        Optional<String> unit = auction.getUnit("banner");
        assertTrue(unit.isPresent());
        assertEquals("banner", unit.get());
    }

    @Test
    void testAddBid() {
        boolean isAdded = auction.addBid(new Bid(test1, "banner", 31));
        assertTrue(isAdded);
    }

    @Test
    void testAddBitInvalidUser() {
        Bidder badBidder = new Bidder("BAD1", 0);
        assertFalse(auction.addBid(new Bid(badBidder, "banner", 31)));
    }

    @Test
    void testAddBidBelowFloor() {
        assertFalse(auction.addBid(new Bid(test2, "banner", 30)));
    }

    @Test
    void testAddBidInvalidUnit() {
        assertFalse(auction.addBid(new Bid(test1, "badunit", 31)));
    }

    @Test
    void testHighestBidForSingleUnit() {
        auction.addBid(new Bid(test1, "banner", 31));
        auction.addBid(new Bid(test2, "banner", 31));
        List<Bid> highestBidders = auction.getHighestBidders();
        assertTrue(highestBidders.size() > 0);
        assertTrue(highestBidders.stream().anyMatch(bid -> bid.getBidder().getName().equalsIgnoreCase("test1")));
    }

    @Test
    void testHighestBidForTwoUnits() {
        auction.addUnit("sidebar");
        auction.addBid(new Bid(test1, "banner", 31));
        auction.addBid(new Bid(test2, "banner", 31));
        auction.addBid(new Bid(test2, "sidebar", 35));
        auction.addBid(new Bid(test1, "sidebar", 32));
        
        List<Bid> highestBidders = auction.getHighestBidders();
        assertEquals(2, highestBidders.size());

        testHighestBid(highestBidders, "banner", "test1");

        testHighestBid(highestBidders, "sidebar", "test2");
    }

    private void testHighestBid(List<Bid> highestBidders, String auctionUnit, String expectedName) {
        Optional<Bid> bannerBid = highestBidders.stream().filter(bid -> bid.getUnit().equalsIgnoreCase(auctionUnit)).findFirst();
        if (bannerBid.isPresent()) {
            assertTrue(bannerBid.get().getBidder().getName().equalsIgnoreCase(expectedName));
        } else {
            fail(String.format("Bid for %s missing...", auctionUnit));
        }
    }
}
