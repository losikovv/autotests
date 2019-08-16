package ru.instamart.autotests.models;

public class CheckoutStepData {

    private int position;
    private String name;
    private String title;

    public CheckoutStepData( int position, String name, String title) {
        this.position = position;
        this.name = name;
        this.title = title;
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }
}
