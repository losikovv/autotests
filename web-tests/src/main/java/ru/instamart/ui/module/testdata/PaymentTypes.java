package ru.instamart.ui.module.testdata;

import ru.instamart.ui.common.pagesdata.PaymentTypeData;


public class PaymentTypes {

    public static PaymentTypeData cash() {
        return new PaymentTypeData("Наличными");
    }

    public static PaymentTypeData cardOnline() {
        return new PaymentTypeData("Картой онлайн");
    }

    public static PaymentTypeData cardCourier() {
        return new PaymentTypeData("Картой при получении");
    }

    public static PaymentTypeData bankTransfer() {
        return new PaymentTypeData("Переводом");
    }
}
