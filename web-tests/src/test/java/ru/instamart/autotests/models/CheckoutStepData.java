package ru.instamart.autotests.models;

public class CheckoutStepData {

    private int position;
    private String name;
    private String description;

    public CheckoutStepData( int position, String name, String description) {
        this.position = position;
        this.name = name;
        this.description = description;
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
