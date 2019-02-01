package ru.instamart.autotests.models;

public class DeliveryTimeData {

    private int day;
    private int slot;

    public DeliveryTimeData(int day, int slot) {
        this.day = day;
        this.slot = slot;
    }

    // Setters

    public void setDay(int day) {
        this.day = day;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    // Getters

    public int getSlot() {
        return slot;
    }

    public int getDay() {
        return day;
    }
}
