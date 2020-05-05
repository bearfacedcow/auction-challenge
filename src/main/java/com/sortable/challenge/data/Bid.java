package com.sortable.challenge.data;

public class Bid implements Comparable<Bid> {
    private final Bidder bidder;
    private final String unit;
    private final double bid;

    public Bid(Bidder bidder, String unit, double bid) {
        this.bidder = bidder;
        this.unit = unit;
        this.bid = bid;
    }

    public Bidder getBidder() {
        return bidder;
    }

    public String getUnit() {
        return unit;
    }

    public double getBid() {
        return bid;
    }

    @Override
    public int compareTo(Bid compareBid) {
        return Double.compare(bidder.calculateAdjustment(bid), compareBid.getBidder().calculateAdjustment(compareBid.getBid()));
    }
}
