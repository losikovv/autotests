package ru.instamart.autotests.models;

public class ReplacementPolicyData {

    private int position;
    private String description;

    public ReplacementPolicyData (int position, String description) {
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
