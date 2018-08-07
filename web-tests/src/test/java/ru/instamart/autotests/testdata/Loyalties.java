package ru.instamart.autotests.testdata;



    // Тестовые карты программ лояльности



public class Loyalties {

    public static String getNumber(String name) {
        final String cardNumber;
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
            default: cardNumber = null;
        }
        return cardNumber;
    }

    public static int getPosition(String name) {
        final int position;
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
            default: position = 0;
        }
        return position;
    }

}
