package ru.instamart.core.testdata.pagesdata;

import ru.instamart.core.testdata.JuridicalData;
import ru.instamart.core.testdata.TestVariables;

public class OrderDetailsData {

    private AddressDetailsData addressDetails;
    private ContactsDetailsData contactsDetails;
    private ReplacementPolicyData replacementPolicy;
    private PaymentDetailsData paymentDetails;
    private DeliveryTimeData deliveryTime;
    private String promocode;
    private LoyaltiesData bonus;
    private LoyaltiesData retailerCard;

    public OrderDetailsData(AddressDetailsData addressDetails, ContactsDetailsData contactsDetails, ReplacementPolicyData replacementPolicy, PaymentDetailsData paymentDetails, DeliveryTimeData deliveryTime, String promocode, LoyaltiesData bonus, LoyaltiesData retailerCard) {
        this.addressDetails = addressDetails;
        this.contactsDetails = contactsDetails;
        this.replacementPolicy = replacementPolicy;
        this.paymentDetails = paymentDetails;
        this.deliveryTime = deliveryTime;
        this.promocode = promocode;
        this.bonus = bonus;
        this.retailerCard = retailerCard;
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

    public OrderDetailsData(LoyaltiesData bonus) {
        this.bonus = bonus;
    }

    public OrderDetailsData(LoyaltiesData bonus, LoyaltiesData retailerCard) {
        this.bonus = bonus;
        this.retailerCard = retailerCard;
    }

    public OrderDetailsData() {
        this.addressDetails = TestVariables.testOrderDetails().getAddressDetails();
        this.contactsDetails = TestVariables.testOrderDetails().getContactsDetails();
        this.replacementPolicy = TestVariables.testOrderDetails().getReplacementPolicy();
        this.paymentDetails = TestVariables.testOrderDetails().getPaymentDetails();
        this.deliveryTime = TestVariables.testOrderDetails().getDeliveryTime();
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



    public void setPaymentDetails(PaymentTypeData paymentType, boolean addNewCreditCard, PaymentCardData creditCard, boolean addNewJuridical, JuridicalData juridical) {
        this.paymentDetails = new PaymentDetailsData (paymentType, addNewCreditCard, creditCard, addNewJuridical, juridical);
    }

    public void setPaymentDetails(PaymentTypeData paymentType, boolean addNewCreditCard, PaymentCardData creditCard) {
        this.paymentDetails = new PaymentDetailsData (paymentType, addNewCreditCard, creditCard);
    }

    public void setPaymentDetails(PaymentTypeData paymentType, boolean addNewJuridical, JuridicalData juridical) {
        this.paymentDetails = new PaymentDetailsData (paymentType, addNewJuridical, juridical);
    }

    public void setPaymentDetails(PaymentTypeData paymentType) {
        this.paymentDetails = new PaymentDetailsData (paymentType);
    }

    public PaymentDetailsData getPaymentDetails() {
        return paymentDetails;
    }

    public void setDeliveryTime(int day, int slot) {
        this.deliveryTime = new DeliveryTimeData(day,slot);
    }

    public DeliveryTimeData getDeliveryTime() {
        return deliveryTime;
    }

    public String getPromocode() {
        return promocode;
    }

    public LoyaltiesData getBonus() {
        return bonus;
    }

    public LoyaltiesData getRetailerCard() {
        return retailerCard;
    }
}
