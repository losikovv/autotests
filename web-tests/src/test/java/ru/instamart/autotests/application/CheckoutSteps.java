package ru.instamart.autotests.application;

import ru.instamart.autotests.models.CheckoutStepData;

public class CheckoutSteps {

    private final static int addressStepPosition = 1;
    private final static int contactsStepPosition = 2;
    private final static int replacementsStepPosition = 3;
    private final static int paymentStepPosition = 4;
    private final static int deliveryStepPosition = 5;

    public static CheckoutStepData getStepDetails(int step) {
        switch (step) {
            case addressStepPosition :
                return address();
            case contactsStepPosition :
                return contacts();
            case replacementsStepPosition :
                return replacements();
            case paymentStepPosition :
                return payment();
            case deliveryStepPosition :
                return delivery();
            default:
                return null;
        }

    }

    public static CheckoutStepData address() {
        return new CheckoutStepData (addressStepPosition,"Адрес","Уточните ваш адрес");
    }

    public static CheckoutStepData contacts() {
        return new CheckoutStepData (contactsStepPosition,"Контакты","Добавьте свои контакты");
    }

    public static CheckoutStepData replacements() {
        return new CheckoutStepData (replacementsStepPosition,"Замены","Выберите способ осуществления замен");
    }

    public static CheckoutStepData payment() {
        return new CheckoutStepData (paymentStepPosition,"Оплата","Выберите метод оплаты");
    }

    public static CheckoutStepData delivery() {
        return new CheckoutStepData (deliveryStepPosition,"Доставка","Выберите время доставки");
    }
}
