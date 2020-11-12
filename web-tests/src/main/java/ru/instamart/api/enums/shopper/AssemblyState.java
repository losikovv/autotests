package instamart.api.enums.shopper;

public enum AssemblyState {
    COLLECTING("collecting"),
    PAYMENT_VERIFICATION("payment_verification"),
    ASSEMBLED("assembled"),
    ON_CASH_DESK("on_cash_desk"),
    PACKAGING("packaging"),
    READY_TO_SHIP("ready_to_ship");

    private final String state;

    AssemblyState(String state) {
        this.state = state;
    }

    public String getState() { return state; }
}
