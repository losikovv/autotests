package ru.instamart.ab;

import lombok.Getter;

public final class AbApi {

    @Getter
    private final AbApiClient abApiClient;

    public AbApi(final String basicUrl) {
        this.abApiClient = new AbApiClient(basicUrl);
    }
}
