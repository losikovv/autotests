package ru.instamart.reforged.business.page.checkout;

import io.qameta.allure.Step;
import ru.instamart.reforged.business.frame.checkout.subsections.EditPaymentCard;
import ru.instamart.reforged.business.page.BusinessPage;
import ru.instamart.reforged.business.page.checkout.contactsStep.ContactsStep;
import ru.instamart.reforged.business.page.checkout.deliveryStep.DeliveryOptionStep;
import ru.instamart.reforged.business.page.checkout.paymentStep.PaymentStep;
import ru.instamart.reforged.business.page.checkout.replacementPolicyStep.ReplacementPolicyStep;
import ru.instamart.reforged.business.page.checkout.slotsStep.SlotStep;


public final class CheckoutPage implements BusinessPage, CheckoutCheck {

    public DeliveryOptionStep setDeliveryOptions() {
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

    public PaymentStep setPayment() {
        return payment;
    }

    public EditPaymentCard interactEditPaymentCardModal() {
        return editPaymentCardModal;
    }


    @Step("Нажимаем 'Оформить заказ' в последнем шаге чекаута")
    public void clickToSubmitFromCheckoutColumn() {
        submitCheckoutInLastStep.scrollTo();
        submitCheckoutInLastStep.click();
    }

    @Override
    @Step("Открываем страницу чекаута")
    public String pageUrl() {
        return "checkout/edit";
    }
}