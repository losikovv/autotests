package ru.instamart.application.models;

public class PaymentTypeData {

    private String description;

    public PaymentTypeData (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
