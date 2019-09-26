package ru.instamart.application.lib;

import ru.instamart.application.models.PaymentTypeData;

public class PaymentTypes {

    public static PaymentTypeData cash() {
        return new PaymentTypeData("Наличными");
    }

    public static PaymentTypeData cardOnline() {
        return new PaymentTypeData("Картой онлайн");
    }

    public static PaymentTypeData cardCourier() {
        return new PaymentTypeData("Картой курьеру");
    }

    public static PaymentTypeData bankTransfer() {
        return new PaymentTypeData("Переводом");
    }
}
