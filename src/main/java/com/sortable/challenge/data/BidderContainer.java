package com.sortable.challenge.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BidderContainer {
    List<Bidder> bidders;

    public List<Bidder> getBidders() {
        return bidders;
    }

    public BidderContainer() {
        bidders = new ArrayList<Bidder>();
    }

    public void add(Bidder bidder) {
        bidders.add(bidder);
    }

    public Optional<Bidder> getBidder(final String bidder) {
        return bidders.stream().filter(b -> b.getName().equalsIgnoreCase(bidder)).findFirst();
    }
}
