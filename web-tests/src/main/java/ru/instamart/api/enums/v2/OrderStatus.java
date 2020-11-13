package instamart.api.enums.v2;

public enum OrderStatus {
    ACTIVE("active"),
    COMPLETE("complete"),
    CANCELED("canceled");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String toString() {
        return status;
    }
}
