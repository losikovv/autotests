package ru.instamart.kraken.testdata.pagesdata;

public class PaymentCardData {

    private final String cardNumber;
    private final String expiryMonth;
    private final String expiryYear;
    private final String cardholderName;
    private final String cvvNumber;
    private final boolean secure;

    public PaymentCardData(String cardNumber, String expiryMonth, String expiryYear, String cardholderName, String cvvNumber, boolean secure) {
        this.cardNumber = cardNumber;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cardholderName = cardholderName;
        this.cvvNumber = cvvNumber;
        this.secure = secure;
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
    public String getCvvNumber() { return cvvNumber;}
    public boolean getSecure(){return secure;}
}
