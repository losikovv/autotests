package ru.instamart.autotests.application;



// Способы оплаты



public class PaymentTypes {

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
