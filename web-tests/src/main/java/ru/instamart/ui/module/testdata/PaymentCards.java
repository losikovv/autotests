package ru.instamart.ui.module.testdata;

import ru.instamart.ui.common.pagesdata.PaymentCardData;

public class PaymentCards {

    public static PaymentCardData testCard() {
        return new PaymentCardData(
                "4242424242424242",
                "12",
                "2049",
                "IVAN IVANOV",
                "404",
                true);
    }

    public static PaymentCardData testCardNo3ds(){
        return new PaymentCardData(
                "5200 8282 8282 8210",
                "12",
                "2049",
                "NO SECURE",
                "404",
                false);
    }

    public static PaymentCardData realCard() {
        return new PaymentCardData(
                "fill your card requisites here",
                "fill your card requisites here",
                "fill your card requisites here",
                "fill your card requisites here",
                "fill your card requisites here",
                false);
    }
}
