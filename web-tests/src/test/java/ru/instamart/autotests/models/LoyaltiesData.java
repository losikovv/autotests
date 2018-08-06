package ru.instamart.autotests.models;



    // Данные программ лояльности



public class LoyaltiesData {
    private final String cardName;
    private final String cardNumber;
    private final int position;

    public LoyaltiesData(String cardName, String cardNumber, int position) {
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.position = position;
    }

    public String name(String name) { return cardName;}
    public String number(String name) { return cardNumber;}
    public int position(String name) { return position;}

    public static String getNumber(String name) {
        String cardNumber = null;
        switch (name) {
            case "mnogoru":
                cardNumber = "11600350";
                break;
            case "aeroflot":
                cardNumber = "71891831";
                break;
            case "familyteam":
                cardNumber = "7005992006136053";
                break;
            case "svyaznoyclub":
                cardNumber = "2981796259309";
                break;
            case "vkusvill":
                cardNumber = "2281023";
                break;
        }
        return cardNumber;
    }

    public static int getPosition(String name) {
        int position = 0;
        switch (name) {
            case "mnogoru":
                position = 1;
                break;
            case "aeroflot":
                position = 2;
                break;
            case "familyteam":
                position = 3;
                break;
            case "svyaznoyclub":
                position = 4;
                break;
            case "vkusvill":
                position = 5;
                break;
        }
        return position;
    }

}
