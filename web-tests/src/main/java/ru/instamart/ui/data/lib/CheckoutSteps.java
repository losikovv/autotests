package ru.instamart.ui.data.lib;

import ru.instamart.ui.data.pagesdata.CheckoutStepData;

public class CheckoutSteps {

    private final static int addressStepPosition = 1;
    private final static int contactsStepPosition = 2;
    private final static int replacementsStepPosition = 3;
    private final static int deliveryStepPosition = 4;
    private final static int paymentStepPosition = 5;


    public static CheckoutStepData getStepDetails(int step) {
        switch (step) {
            case addressStepPosition :
                return addressStep();
            case contactsStepPosition :
                return contactsStep();
            case replacementsStepPosition :
                return replacementsStep();
            case deliveryStepPosition :
                return deliveryStep();
            case paymentStepPosition :
                return paymentStep();
            default:
                return null;
        }
    }

    public static CheckoutStepData addressStep() {
        return new CheckoutStepData (addressStepPosition,"Адрес","Способ получения");
    }

    public static CheckoutStepData contactsStep() {
        return new CheckoutStepData (contactsStepPosition,"Контакты","Добавьте свои контакты");
    }

    public static CheckoutStepData replacementsStep() {
        return new CheckoutStepData (replacementsStepPosition,"Замены","Выберите способ осуществления замен");
    }

    public static CheckoutStepData paymentStep() {
        return new CheckoutStepData (paymentStepPosition,"Оплата","Выберите метод оплаты");
    }

    public static CheckoutStepData deliveryStep() {
        return new CheckoutStepData (deliveryStepPosition,"Доставка","Выберите время получения");
    }

    public static String getStepNameBy(String title){
        switch (title){
            case "Уточните ваш адрес":
                return "Адрес";
            case "Добавьте свои контакты":
                return "Контакты";
            case "Выберите способ осуществления замен":
                return "Замены";
            case "Выберите метод оплаты":
                return "Оплата";
            case "Выберите время доставки":
                return "Доставка";
            default:
                return null;
        }
    }

    public static String getStepTitleBy(String name){
        switch (name){
            case "Адрес":
                return "Уточните ваш адрес";
            case "Контакты":
                return "Добавьте свои контакты";
            case "Замены":
                return "Выберите способ осуществления замен";
            case "Оплата":
                return "Выберите метод оплаты";
            case "Доставка":
                return "Выберите время доставки";
            default:
                return null;
        }
    }
}
