package com.sortable.challenge.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SiteContainerTest {
    private SiteContainer siteContainer;

    @BeforeEach
    void setUp() {
        siteContainer = new SiteContainer();
    }

    @Test
    void testAddSite() {
        siteContainer.add(new Site("test.com", 30));
        assertEquals(1, siteContainer.getSites().size());
    }

    @Test
    void testGetSite() {
        siteContainer.add(new Site("test1.com", 30));
        siteContainer.add(new Site("test2.com", 30));
        Optional<Site> site = siteContainer.getSite("test1.com");
        assertTrue(site.isPresent());
        assertEquals("test1.com", site.get().getName());
    }

    @Test
    void testForNonExistentSite() {
        siteContainer.add(new Site("test1.com", 30));
        siteContainer.add(new Site("test2.com", 30));
        Optional<Site> site = siteContainer.getSite("test3.com");
        assertTrue(site.isEmpty());
    }
}
