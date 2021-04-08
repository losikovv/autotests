package ru.instamart.api.enums.v2;

public enum OrderStatusV2 {
    ACTIVE("active"),
    COMPLETE("complete"),
    CANCELED("canceled");

    private final String status;

    OrderStatusV2(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String toString() {
        return status;
    }
}
