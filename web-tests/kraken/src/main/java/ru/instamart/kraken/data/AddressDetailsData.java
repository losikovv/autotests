package ru.instamart.kraken.data;

import lombok.Data;

@Data
public final class AddressDetailsData {

    private final String kind;
    private final String apartment;
    private final String floor;
    private final boolean elevator;
    private final String entrance;
    private final String domofon;
    private final String comments;
}
