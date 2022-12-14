package ru.instamart.kraken.service.testit;

import lombok.Getter;

public enum TestItService {

    INSTANCE;

    @Getter
    private final TestItClient client;

    TestItService() {
        this.client = new TestItClient();
    }
}
