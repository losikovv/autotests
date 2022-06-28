package ru.instamart.reforged.business.page.checkout;

import io.qameta.allure.Step;
import ru.instamart.reforged.business.frame.checkout.subsections.B2BEditPaymentCard;
import ru.instamart.reforged.business.page.BusinessPage;
import ru.instamart.reforged.business.page.checkout.contactsStep.B2BContactsStep;
import ru.instamart.reforged.business.page.checkout.deliveryStep.B2BDeliveryOptionStep;
import ru.instamart.reforged.business.page.checkout.paymentStep.B2BPaymentStep;
import ru.instamart.reforged.business.page.checkout.replacementPolicyStep.B2BReplacementPolicyStep;
import ru.instamart.reforged.business.page.checkout.slotsStep.B2BSlotStep;


public final class B2BCheckoutPage implements BusinessPage, B2BCheckoutCheck {

    public B2BDeliveryOptionStep setDeliveryOptions() {
        return deliveryOptionsStep;
    }

    public B2BContactsStep setContacts() {
        return contactsStep;
    }

    public B2BReplacementPolicyStep setReplacementPolicy() {
        return replacementPolicy;
    }

    public B2BSlotStep setSlot() {
        return slot;
    }

    public B2BPaymentStep setPayment() {
        return payment;
    }

    public B2BEditPaymentCard interactEditPaymentCardModal() {
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