package ru.instamart.api.enums.shopper;

public enum ItemStateSHP {
    APPROVED("approved");

    private final String state;

    ItemStateSHP(String state) {
        this.state = state;
    }

    public String getState() { return state; }
}
