package ru.instamart.core.testdata.pagesdata;

public class ReplacementPolicyData {

    private int position;
    private String instruction;
    private String userDescription;

    public ReplacementPolicyData (int position, String instruction, String userDescription) {
        this.position = position;
        this.instruction = instruction;
        this.userDescription = userDescription;
    }

    public int getPosition() {
        return position;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getUserDescription() {
        return userDescription;
    }
}
