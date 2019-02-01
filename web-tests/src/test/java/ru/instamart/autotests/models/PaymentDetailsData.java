package ru.instamart.autotests.models;

public class PaymentDetailsData {

    private PaymentTypeData paymentType;
    private boolean newCreditCard;
    private CreditCardData creditCard;
    private boolean newJuridical;
    private JuridicalData juridical;

    public PaymentDetailsData(PaymentTypeData paymentType, boolean newCreditCard, CreditCardData creditCard, boolean newJuridical, JuridicalData juridical) {
        this.paymentType = paymentType;
        this.newCreditCard = newCreditCard;
        this.creditCard = creditCard;
        this.newJuridical = newJuridical;
        this.juridical = juridical;
    }

    public PaymentDetailsData(PaymentTypeData paymentType, boolean newCreditCard, CreditCardData creditCard) {
        this.paymentType = paymentType;
        this.newCreditCard = newCreditCard;
        this.creditCard = creditCard;
    }

    public PaymentDetailsData(PaymentTypeData paymentType, boolean newJuridical, JuridicalData juridical) {
        this.paymentType = paymentType;
        this.newJuridical = newJuridical;
        this.juridical = juridical;
    }

    public PaymentDetailsData(PaymentTypeData paymentType) {
        this.paymentType = paymentType;
    }

    // Getters

    public PaymentTypeData getPaymentType() {
        return paymentType;
    }

    public boolean isNewCreditCard() {
        return newCreditCard;
    }

    public CreditCardData getCreditCard() {
        return creditCard;
    }

    public boolean isNewJuridical() {
        return newJuridical;
    }

    public JuridicalData getJuridical() {
        return juridical;
    }
}
