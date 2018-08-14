package ru.instamart.autotests.models;



    // Модель данных банковских карт



public class CreditCardData {

    private final String cardNumber;
    private final String expiryMonth;
    private final String expiryYear;
    private final String cardholderName;
    private final String cvvNumber;

    public CreditCardData(String cardNumber, String expiryMonth, String expiryYear, String cardholderName, String cvvNumber) {
        this.cardNumber = cardNumber;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cardholderName = cardholderName;
        this.cvvNumber = cvvNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }
    public String getExpiryMonth() {
        return expiryMonth;
    }
    public String getExpiryYear() {
        return expiryYear;
    }
    public String getCardholderName() {
        return cardholderName;
    }
    public String getCvvNumber() {
        return cvvNumber;
    }
}
