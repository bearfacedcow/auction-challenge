package com.sortable.challenge.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Auction {
    private final Site site;
    private List<String> units;
    private List<Bid> bids;

    public Auction(Site site) {
        this.site = site;
        units = new ArrayList<>();
        bids = new ArrayList<>();
    }

    public Site getSite() {
        return site;
    }

    public List<String> getUnits() {
        return units;
    }

    public void addUnit(String unit) {
        units.add(unit);
    }

    public Optional<String> getUnit(String name) {
        return units.stream().filter(s -> s.equalsIgnoreCase(name)).findFirst();
    }

    public boolean addBid(Bid bid) {
        if (site.getBidder(bid.getBidder().getName()).isPresent() && units.contains(bid.getUnit())) {
            if (bid.getBidder().calculateAdjustment(bid.getBid()) >= site.getFloor()) {
                bids.add(bid);
                return true;
            }
        }
        return false;
    }

    public List<Bid> getHighestBidders() {
        List<Bid> highestBids = new ArrayList<>();

        units.stream().map(unit -> bids.stream().filter(bid -> bid.getUnit().equalsIgnoreCase(unit)).max(Bid::compareTo)).forEach(highestBid -> highestBid.ifPresent(highestBids::add));

        return highestBids;
    }

    public JsonNode getHighestBiddersJson() {
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayNode bidNode = objectMapper.createArrayNode();

        getHighestBidders().forEach(bid -> {
            ObjectNode bidObj = objectMapper.createObjectNode();
            bidObj.put("bidder", bid.getBidder().getName());
            bidObj.put("unit", bid.getUnit());
            bidObj.put("bid", bid.getBid());

            bidNode.add(bidObj);
        });

        return bidNode;
    }
}
