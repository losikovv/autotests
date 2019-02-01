package ru.instamart.autotests.models;

public class LoyaltyProgramData {

    private final String name;
    private final String cardNumber;
    private final int position;

    public LoyaltyProgramData(String name, String cardNumber, int position) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getPosition() {
        return position;
    }
}
