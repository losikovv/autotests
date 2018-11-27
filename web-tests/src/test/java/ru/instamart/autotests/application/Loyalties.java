package ru.instamart.autotests.application;



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
            case "vkusvill":
                position = 3;
                break;
            default: position = 0;
        }
        return position;
    }

}
