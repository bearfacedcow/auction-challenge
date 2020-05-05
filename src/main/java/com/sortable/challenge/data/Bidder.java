package com.sortable.challenge.data;

public class Bidder {
    private final String name;
    private final double adjustment;

    public String getName() {
        return name;
    }

    public double getAdjustment() {
        return adjustment;
    }

    public Bidder(String name, double adjustment) {
        this.name = name;
        this.adjustment = adjustment;
    }

    public double calculateAdjustment(double bid) {
        return bid + (bid * adjustment);
    }
}
