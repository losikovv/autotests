package ru.instamart.autotests.models;

import ru.instamart.autotests.application.Config;

public class OrderDetailsData {

    private AddressDetailsData addressDetails;
    private ContactsDetailsData contactsDetails;
    private ReplacementPolicyData replacementPolicy;
    private PaymentDetailsData paymentDetails;
    private DeliveryTimeData deliveryTime;
    private String promocode;
    private BonusProgramData bonus;
    private LoyaltyProgramData loyalty;

    public OrderDetailsData(AddressDetailsData addressDetails, ContactsDetailsData contactsDetails, ReplacementPolicyData replacementPolicy, PaymentDetailsData paymentDetails, DeliveryTimeData deliveryTime, String promocode, BonusProgramData bonus, LoyaltyProgramData retailerBonus) {
        this.addressDetails = addressDetails;
        this.contactsDetails = contactsDetails;
        this.replacementPolicy = replacementPolicy;
        this.paymentDetails = paymentDetails;
        this.deliveryTime = deliveryTime;
        this.promocode = promocode;
        this.bonus = bonus;
        this.loyalty = retailerBonus;
    }

    public OrderDetailsData(AddressDetailsData addressDetails, ContactsDetailsData contactsDetails, ReplacementPolicyData replacementPolicy, PaymentDetailsData paymentDetails, DeliveryTimeData deliveryTime) {
        this.addressDetails = addressDetails;
        this.contactsDetails = contactsDetails;
        this.replacementPolicy = replacementPolicy;
        this.paymentDetails = paymentDetails;
        this.deliveryTime = deliveryTime;
    }

    public OrderDetailsData(AddressDetailsData addressDetails) {
        this.addressDetails = addressDetails;
    }

    public OrderDetailsData(ContactsDetailsData contactsDetails) {
        this.contactsDetails = contactsDetails;
    }

    public OrderDetailsData(ReplacementPolicyData replacementPolicy) {
        this.replacementPolicy = replacementPolicy;
    }

    public OrderDetailsData(PaymentDetailsData paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public OrderDetailsData(DeliveryTimeData deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public OrderDetailsData(String promocode) {
        this.promocode = promocode;
    }

    public OrderDetailsData(BonusProgramData bonus) {
        this.bonus = bonus;
    }

    public OrderDetailsData(LoyaltyProgramData retailerBonus) {
        this.loyalty = retailerBonus;
    }

    public OrderDetailsData() {
        this.addressDetails = Config.testOrderDetails().getAddressDetails();
        this.contactsDetails = Config.testOrderDetails().getContactsDetails();
        this.replacementPolicy = Config.testOrderDetails().getReplacementPolicy();
        this.paymentDetails = Config.testOrderDetails().getPaymentDetails();
        this.deliveryTime = Config.testOrderDetails().getDeliveryTime();
    }



    public void setAddressDetails(String kind, String apartment, String floor, boolean elevator, String entrance, String domofon, String comments) {
        this.addressDetails = new AddressDetailsData(kind, apartment, floor, elevator, entrance, domofon, comments);
    }

    public void setAddressDetails(AddressDetailsData addressDetails) {
        this.addressDetails = addressDetails;
    }

    public AddressDetailsData getAddressDetails() {
        return addressDetails;
    }



    public void setContactsDetails(String name, String surname, String email, boolean addNewPhone, String phone, boolean sendEmail) {
        this.contactsDetails = new ContactsDetailsData(name, surname, email, addNewPhone, phone, sendEmail);
    }

    public void setContactsDetails(ContactsDetailsData contactsDetails) {
        this.contactsDetails = contactsDetails;
    }

    public ContactsDetailsData getContactsDetails() {
        return contactsDetails;
    }



    public void setReplacementPolicy(ReplacementPolicyData replacementPolicy) {
        this.replacementPolicy = replacementPolicy;
    }

    public ReplacementPolicyData getReplacementPolicy() {
        return replacementPolicy;
    }



    public void setPaymentDetails(PaymentTypeData paymentType, boolean addNewCreditCard, CreditCardData creditCard, boolean addNewJuridical, JuridicalData juridical) {
        this.paymentDetails = new PaymentDetailsData (paymentType, addNewCreditCard, creditCard, addNewJuridical, juridical);
    }

    public void setPaymentDetails(PaymentTypeData paymentType, boolean addNewCreditCard, CreditCardData creditCard) {
        this.paymentDetails = new PaymentDetailsData (paymentType, addNewCreditCard, creditCard);
    }

    public void setPaymentDetails(PaymentTypeData paymentType, boolean addNewJuridical, JuridicalData juridical) {
        this.paymentDetails = new PaymentDetailsData (paymentType, addNewJuridical, juridical);
    }

    public void setPaymentDetails(PaymentTypeData paymentType) {
        this.paymentDetails = new PaymentDetailsData (paymentType);
    }

    public void setPaymentDetails(PaymentDetailsData paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public PaymentDetailsData getPaymentDetails() {
        return paymentDetails;
    }



    public void setDeliveryTime(int day, int slot) {
        this.deliveryTime = new DeliveryTimeData(day,slot);
    }

    public void setDeliveryTime(DeliveryTimeData deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public DeliveryTimeData getDeliveryTime() {
        return deliveryTime;
    }



    public void setPromocode (String promocode) {
        this.promocode = promocode;
    }

    public String getPromocode() {
        return promocode;
    }



    public void setBonus(BonusProgramData bonus) {
        this.bonus = bonus;
    }

    public BonusProgramData getBonus() {
        return bonus;
    }



    public void setLoyalty(LoyaltyProgramData loyalty) {
        this.loyalty = loyalty;
    }

    public LoyaltyProgramData getLoyalty() {
        return loyalty;
    }
}
