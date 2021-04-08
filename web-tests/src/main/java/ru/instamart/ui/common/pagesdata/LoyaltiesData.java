package ru.instamart.ui.common.pagesdata;

public class LoyaltiesData {

    private final String name;
    private final String cardNumber;
    private final String description;

    public LoyaltiesData(String name, String cardNumber, String description) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getDescription() {
        return description;
    }
}
