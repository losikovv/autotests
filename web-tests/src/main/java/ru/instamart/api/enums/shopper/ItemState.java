package instamart.api.enums.shopper;

public enum ItemState {
    APPROVED("approved");

    private final String state;

    ItemState(String state) {
        this.state = state;
    }

    public String getState() { return state; }
}
