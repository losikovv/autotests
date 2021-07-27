package ru.instamart.reforged.stf.page.checkout;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.frame.checkout.subsections.*;
import ru.instamart.reforged.stf.page.StfPage;
import ru.instamart.reforged.stf.page.checkout.step.*;

public final class CheckoutPage implements StfPage, CheckoutCheck {

    public DeliveryOptionsStep setDeliveryOptions() {
        return deliveryOptionsStep;
    }

    public ContactsStep setContacts() {
        return contactsStep;
    }

    public ReplacementPolicyStep setReplacementPolicy() {
        return replacementPolicy;
    }

    public SlotStep setSlot() {
        return slot;
    }

    public PaymentStep paymentStep() {
        return payment;
    }

    public AddCompany interactAddCompanyModal() {
        return addCompanyModal;
    }

    public EditPhoneNumber interactEditPhoneNumberModal() {
        return editPhoneNumberModal;
    }

    public EditRequisites interactEditRequisitesModal() {
        return requisitesModal;
    }

    public EditPaymentCard interactEditPaymentCardModal() {
        return editPaymentCardModal;
    }

    public EditLoyaltyPromoCode interactEditLoyaltyPromoCodeModal() {
        return editLoyaltyPromoCodeModal;
    }

    @Step("Нажать Оформить заказ в сайдбаре")
    public void clickToSubmitFromSidebar() {
        submitFromCheckoutSidebar.click();
    }

    @Step("Нажать Применить промокод")
    public void clickToAddPromoCode() {
        addPromoCode.click();
    }

    @Step("Нажать Добавить карту лояльности {0}")
    public void clickToAddLoyaltyCard(String data) {
        addLoyaltyCard.clickOnElementWithText(data);
    }

    @Override
    @Step("Открыть старницу чекаута")
    public String pageUrl() {
        return "checkout/edit";
    }
}