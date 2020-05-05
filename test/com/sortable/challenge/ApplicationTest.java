package com.sortable.challenge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sortable.challenge.config.ConfigLoader;
import com.sortable.challenge.data.Bid;
import com.sortable.challenge.data.SiteContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationTest {
    private Application application;

    @Mock
    private ConfigLoader configLoader;

    @BeforeEach
    void setUp() {
        when(configLoader.loadConfig()).thenReturn("{\n" +
                "    \"sites\": [\n" +
                "        {\n" +
                "            \"name\": \"test.com\",\n" +
                "            \"bidders\": [\"TEST2\", \"TEST1\"],\n" +
                "            \"floor\": 32\n" +
                "        }\n" +
                "    ],\n" +
                "    \"bidders\": [\n" +
                "        {\n" +
                "            \"name\": \"TEST1\",\n" +
                "            \"adjustment\": -0.0625\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"TEST2\",\n" +
                "            \"adjustment\": 0\n" +
                "        }\n" +
                "    ]\n" +
                "}\n");

        application = new Application(configLoader);
    }

    @Test
    void testInit() {
        assertNotNull(application);
    }

    @Test
    void testConfiguration() {
        assertTrue(application.getSiteContainer().getSite("test.com").isPresent());
        assertTrue(application.getBidderContainer().getBidder("TEST1").isPresent());
    }

    @Test
    void testAuction() {
        String auctionStr = "[\n" +
                "    {\n" +
                "        \"site\": \"test.com\",\n" +
                "        \"units\": [\"banner\", \"sidebar\"],\n" +
                "        \"bids\": [\n" +
                "            {\n" +
                "                \"bidder\": \"TEST1\",\n" +
                "                \"unit\": \"banner\",\n" +
                "                \"bid\": 35\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidder\": \"TEST2\",\n" +
                "                \"unit\": \"sidebar\",\n" +
                "                \"bid\": 60\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidder\": \"TEST1\",\n" +
                "                \"unit\": \"sidebar\",\n" +
                "                \"bid\": 55\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "]\n";

        application.runAuction(auctionStr);
        assertEquals(1, application.getAuctions().size());

        List<Bid> highestBidders = application.getAuctions().get(0).getHighestBidders();
        Optional<Bid> sidebarBid = highestBidders.stream().filter(bid -> bid.getUnit().equalsIgnoreCase("sidebar")).findFirst();
        assertTrue(sidebarBid.isPresent());
        assertEquals("TEST2", sidebarBid.get().getBidder().getName());
    }

    @Test
    void testAuctionBadSite() {
        String auctionStr = "[\n" +
                "    {\n" +
                "        \"site\": \"badsite.com\",\n" +
                "        \"units\": [\"banner\", \"sidebar\"],\n" +
                "        \"bids\": [\n" +
                "            {\n" +
                "                \"bidder\": \"TEST1\",\n" +
                "                \"unit\": \"banner\",\n" +
                "                \"bid\": 35\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidder\": \"TEST2\",\n" +
                "                \"unit\": \"sidebar\",\n" +
                "                \"bid\": 60\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidder\": \"TEST1\",\n" +
                "                \"unit\": \"sidebar\",\n" +
                "                \"bid\": 55\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "]\n";

        application.runAuction(auctionStr);
        assertTrue( application.getAuctions().isEmpty());
    }

    @Test
    void testAuctionNoBids() {
        String auctionStr = "[\n" +
                "    {\n" +
                "        \"site\": \"test.com\",\n" +
                "        \"units\": [\"banner\", \"sidebar\"],\n" +
                "        \"bids\": [\n" +
                "            {\n" +
                "                \"bidder\": \"BAD1\",\n" +
                "                \"unit\": \"banner\",\n" +
                "                \"bid\": 35\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidder\": \"BAD2\",\n" +
                "                \"unit\": \"sidebar\",\n" +
                "                \"bid\": 60\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidder\": \"BAD3\",\n" +
                "                \"unit\": \"sidebar\",\n" +
                "                \"bid\": 55\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "]\n";

        application.runAuction(auctionStr);
        assertTrue( application.getAuctions().isEmpty());
    }

    @Test
    void testJsonOutput() throws JsonProcessingException {
        String auctionStr = "[\n" +
                "    {\n" +
                "        \"site\": \"test.com\",\n" +
                "        \"units\": [\"banner\", \"sidebar\"],\n" +
                "        \"bids\": [\n" +
                "            {\n" +
                "                \"bidder\": \"TEST1\",\n" +
                "                \"unit\": \"banner\",\n" +
                "                \"bid\": 35\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidder\": \"TEST2\",\n" +
                "                \"unit\": \"sidebar\",\n" +
                "                \"bid\": 60\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidder\": \"TEST1\",\n" +
                "                \"unit\": \"sidebar\",\n" +
                "                \"bid\": 55\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "]\n";

        application.runAuction(auctionStr);
        String json = application.toJson();

        assertTrue(json != "");
        assertTrue(json.indexOf("TEST1") > 0);
    }
}


