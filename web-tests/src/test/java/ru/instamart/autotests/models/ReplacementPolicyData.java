package ru.instamart.autotests.models;

public class ReplacementPolicyData {

    private int position;
    private String description;
    private String userDescription;

    public ReplacementPolicyData (int position, String description, String userDescription) {
        this.position = position;
        this.description = description;
        this.userDescription = description;
    }

    public int getPosition() {
        return position;
    }

    public String getDescription() {
        return description;
    }

    public String getUserDescription() {
        return userDescription;
    }
}
