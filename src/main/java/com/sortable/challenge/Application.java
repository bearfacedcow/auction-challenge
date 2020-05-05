package com.sortable.challenge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sortable.challenge.config.ConfigLoader;
import com.sortable.challenge.data.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Application {
    private final SiteContainer siteContainer;
    private final BidderContainer bidderContainer;
    private List<Auction> auctions;

    public Application(ConfigLoader configLoader) {
        siteContainer = new SiteContainer();
        bidderContainer = new BidderContainer();
        auctions = new ArrayList<>();

        try {
            configureApplication(configLoader);
        } catch (Exception e) {
            System.out.printf("Application Error: %s%n", e.getMessage());
        }
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public Application() {
        this(new ConfigLoader());
    }

    public SiteContainer getSiteContainer() {
        return siteContainer;
    }

    public BidderContainer getBidderContainer() {
        return bidderContainer;
    }

    private void configureApplication(ConfigLoader loader) throws JsonProcessingException, IOException {
        String config = loader.loadConfig();
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode configTree = objectMapper.readTree(config);

        configureBidders(objectMapper, configTree);

        configureSites(objectMapper, configTree);
    }


    private void configureBidders(ObjectMapper objectMapper, JsonNode configTree) throws JsonProcessingException {
        ArrayNode bidders = (ArrayNode) configTree.get("bidders");

        for (JsonNode bidder : bidders) {
            Bidder aBidder = new Bidder(bidder.get("name").asText(), bidder.get("adjustment").asDouble());
            bidderContainer.add(aBidder);
        }
    }

    private void configureSites(ObjectMapper objectMapper, JsonNode configTree) throws JsonProcessingException, IOException {
        ArrayNode sites = (ArrayNode) configTree.get("sites");

        for (JsonNode site : sites) {
            Site aSite = new Site(site.get("name").asText(), site.get("floor").asDouble());
            ArrayNode bidderList = (ArrayNode) site.get("bidders");

            for (JsonNode bidderName : bidderList) {
                bidderContainer.getBidder(bidderName.textValue().replace("\"", "")).ifPresent(aSite::addBidder);
            }
            siteContainer.add(aSite);
        }
    }

    public void runAuction(String auctionStr) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ArrayNode auctionList = (ArrayNode) objectMapper.readTree(auctionStr);

            for (JsonNode auctionNode : auctionList) {
                Optional<Site> site = siteContainer.getSite(auctionNode.get("site").asText());
                if (site.isPresent()) {
                    Auction auction = new Auction(site.get());
                    ArrayNode unitNode = (ArrayNode) auctionNode.get("units");
                    for (JsonNode unit : unitNode) {
                        auction.addUnit(unit.textValue().replace("\"", ""));
                    }

                    ArrayNode bids = (ArrayNode) auctionNode.get("bids");
                    for (JsonNode bidJson : bids) {
                        Optional<Bidder> bidder = bidderContainer.getBidder(bidJson.get("bidder").asText());
                        if (bidder.isPresent()) {
                            Bid bid = new Bid(bidder.get(), bidJson.get("unit").asText(), bidJson.get("bid").asDouble());
                            auction.addBid(bid);
                        }
                    }

                    if (auction.getHighestBidders().size() > 0) {
                        auctions.add(auction);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to run auction: " + e.getMessage());
        }
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayNode root = objectMapper.createArrayNode();

        auctions.forEach(auction -> root.add(auction.getHighestBiddersJson()));

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
    }
}
