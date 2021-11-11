package ru.instamart.kraken.data;

import lombok.Data;

@Data
public final class Company {

    private final String inn;
    private final String companyName;
    private final String ownerEmail;
}
