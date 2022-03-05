package ru.instamart.kraken.data;

public class PaymentCards {

    public static PaymentCardData testCard() {
        return PaymentCardData.builder()
                .cardNumber("4242424242424242")
                .expiryMonth("12")
                .expiryYear("49")
                .cardholderName("IVAN IVANOV")
                .cvvNumber("404")
                .secure(true)
                .build();
    }

    public static PaymentCardData testCardNo3ds() {
        return PaymentCardData.builder()
                .cardNumber("5200 8282 8282 8210")
                .expiryMonth("12")
                .expiryYear("49")
                .cardholderName("NO SECURE")
                .cvvNumber("404")
                .secure(false)
                .build();
    }
}
