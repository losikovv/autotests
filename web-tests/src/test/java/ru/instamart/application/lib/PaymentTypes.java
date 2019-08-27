package ru.instamart.application.lib;

import ru.instamart.application.models.PaymentTypeData;

public class PaymentTypes {

    private static int position = cash().getPosition();
    private static String description = cash().getDescription();


    public static PaymentTypeData cardOnline() {
        position = 1;
        description = "Картой онлайн";
        return new PaymentTypeData(position,description);
    }

    public static PaymentTypeData cardCourier() {
        position = 2;
        description = "Картой курьеру";
        return new PaymentTypeData(position,description);
    }

    public static PaymentTypeData cash() {
        position = 3;
        description = "Наличными";
        return new PaymentTypeData(position,description);
    }

    public static PaymentTypeData bankTransfer() {
        position = 4;
        description = "Переводом";
        return new PaymentTypeData(position,description);
    }

    public static int getPosition(){
        return position;
    }

    public static String getDescription(){
        return description;
    }

    public static int getPosition(String type){
        final int position;
        switch (type) {
            case "card-online":
                position = 1;
                break;
            case "card-courier":
                position = 2;
                break;
            case "cash":
                position = 3;
                break;
            case "bank":
                position = 4;
                break;
            default:
                position = 0;
        }
        return position;
    }
}
