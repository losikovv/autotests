package ru.instamart.core.testdata.pagesdata;

public class DeliveryTimeData {

    private int day;
    private int slot;

    public DeliveryTimeData(int day, int slot) {
        this.day = day;
        this.slot = slot;
    }

    public DeliveryTimeData(int day) {
        this.day = day;
        this.slot = 0;
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
