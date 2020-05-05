package com.sortable.challenge.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SiteContainer {
    private List<Site> sites;

    public SiteContainer() {
        sites = new ArrayList<>();
    }

    public List<Site> getSites() {
        return sites;
    }

    public void add(Site site) {
        sites.add(site);
    }

    public Optional<Site> getSite(String name) {
        return sites.stream().filter(site -> site.getName().equalsIgnoreCase(name)).findFirst();
    }
}
