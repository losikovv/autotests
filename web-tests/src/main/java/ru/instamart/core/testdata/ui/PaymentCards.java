package instamart.core.testdata.ui;

import instamart.ui.common.pagesdata.PaymentCardData;

public class PaymentCards {

    public static PaymentCardData testCard() {
        return new PaymentCardData(
                "4242424242424242",
                "12",
                "2049",
                "IVAN IVANOV",
                "404");
    }

    public static PaymentCardData realCard() {
        return new PaymentCardData(
                "fill your card requisites here",
                "fill your card requisites here",
                "fill your card requisites here",
                "fill your card requisites here",
                "fill your card requisites here");
    }
}
