package ru.instamart.jdbc.entity.shippingcalc;

import lombok.Data;

@Data
public class IntervalsSurgeEntity {
    private int leftBoundary;
    private int rightBoundary;
    private int priceAddition;
    private int percentAddition;
}
