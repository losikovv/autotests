package ru.instamart.kraken.data;

public class PaymentCards {

    public static PaymentCardData testCard() {
        return PaymentCardData.builder()
                .cardNumber("4111111111111111")
                .expiryMonth("12")
                .expiryYear("24")
                .cardholderName("IVAN IVANOV")
                .cvvNumber("123")
                .password("12345678")
                .secure(true)
                .build();
    }

    public static PaymentCardData testBusinessCard() {
        return PaymentCardData.builder()
                .cardNumber("5555555555555599")
                .expiryMonth("12")
                .expiryYear("24")
                .cardholderName("BUSINESS BUSINESSOFF")
                .cvvNumber("123")
                .secure(false)
                .build();
    }

    public static PaymentCardData testCardNo3ds() {
        return PaymentCardData.builder()
                .cardNumber("5555 5555 5555 5599")
                .expiryMonth("12")
                .expiryYear("24")
                .cardholderName("NO SECURE")
                .cvvNumber("123")
                .secure(false)
                .build();
    }

    public static PaymentCardData testCardNo3dsWithSpasibo() {
        return PaymentCardData.builder()
                .cardNumber("4276103199824468")
                .expiryMonth("09")
                .expiryYear("24")
                .cardholderName("NO SECURE")
                .cvvNumber("325")
                .secure(false)
                .build();
    }

    public static PaymentCardData testCardWithSpasibo() {
        return PaymentCardData.builder()
                .cardNumber("4276107290668435")
                .expiryMonth("12")
                .expiryYear("24")
                .cardholderName("IVAN IVANOV")
                .cvvNumber("788")
                .password("12345678")
                .secure(true)
                .build();
    }

    public static PaymentCardData testCardWithoutSpasiboWithdrawl() {
        return PaymentCardData.builder()
                .cardNumber("4276107667823373")
                .expiryMonth("12")
                .expiryYear("24")
                .cardholderName("IVAN IVANOV")
                .cvvNumber("809")
                .password("12345678")
                .secure(true)
                .build();
    }

    public static PaymentCardData testCardWithoutMoney() {
        return PaymentCardData.builder()
                .cardNumber("4408896253205448")
                .expiryMonth("12")
                .expiryYear("24")
                .cardholderName("IVAN IVANOV")
                .cvvNumber("123")
                .password("12345678")
                .secure(true)
                .build();
    }
}
