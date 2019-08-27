package ru.instamart.application.models;

public class PaymentTypeData {

    private int position;
    private String description;

    public PaymentTypeData (int position, String description) {
        this.position = position;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getPosition() {
        return position;
    }
}
