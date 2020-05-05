package com.sortable.challenge.data;

import java.util.Optional;

public class Site {
    private final String name;
    private final double floor;
    private BidderContainer bidders;

    public Site(String name, double floor) {
        this.name = name;
        this.floor = floor;
        bidders = new BidderContainer();
    }

    public String getName() {
        return name;
    }

    public double getFloor() {
        return floor;
    }

    public void addBidder(Bidder bidder) {
        bidders.add(bidder);
    }

    public Optional<Bidder> getBidder(String name) {
        return bidders.getBidder(name);
    }
}
