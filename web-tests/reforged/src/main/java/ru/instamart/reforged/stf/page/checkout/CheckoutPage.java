package ru.instamart.reforged.stf.page.checkout;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.stf.block.helpdesk.HelpDesk;
import ru.instamart.reforged.stf.frame.checkout.subsections.*;
import ru.instamart.reforged.stf.frame.checkout.subsections.create_company.AddCompany;
import ru.instamart.reforged.stf.frame.checkout.subsections.loyaltycard_modal.EditLoyaltyCard;
import ru.instamart.reforged.stf.frame.checkout.subsections.promocode_modal.EditPromoCode;
import ru.instamart.reforged.stf.page.StfPage;
import ru.instamart.reforged.stf.page.checkout.fifthStep.SlotStep;
import ru.instamart.reforged.stf.page.checkout.fourthStep.PaymentStep;
import ru.instamart.reforged.stf.page.checkout.secondStep.ContactsStep;
import ru.instamart.reforged.stf.page.checkout.firstStep.DeliveryOptionStep;
import ru.instamart.reforged.stf.page.checkout.thirdStep.ReplacementPolicyStep;

public final class CheckoutPage implements StfPage, CheckoutCheck {

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

    public AddCompany interactAddCompanyModal() {
        return addCompanyModal;
    }

    public EditPhoneNumber interactEditPhoneNumberModal() {
        return editPhoneNumberModal;
    }

    public EditPromoCode interactEditPromoCodeModal() {
        return editPromoCode;
    }

    public EditRequisites interactEditRequisitesModal() {
        return requisitesModal;
    }

    public EditPaymentCard interactEditPaymentCardModal() {
        return editPaymentCardModal;
    }

    public EditLoyaltyCard interactEditLoyaltyCardModal() {
        return EDIT_LOYALTY_CARD_MODAL;
    }

    public HelpDesk interactHelpDesk() {
        return helpDesk;
    }

    @Step("Нажать Оформить заказ в сайдбаре")
    public void clickToSubmitFromSidebar() {
        submitFromCheckoutSidebar.click();
    }

    @Step("Нажать Применить промокод")
    public void clickToAddPromoCode() {
        addPromoCode.click();
    }

    @Step("Нажать Удалить промокод")
    public void clickToDeletePromoCode() {
        deletePromoCode.click();
    }

    @Step("Нажать добавить карту лояльности {0}")
    public void clickToAddLoyaltyCard(final String data) {
        addLoyaltyCard.clickOnElementWithText(data);
    }

    @Step("Нажать выбрать карту лояльности {0}")
    public void clickToSelectLoyaltyCard(String data) {
        selectLoyaltyCard.clickOnElementWithText(data);
    }

    @Step("Редактировать карту лояльности {0}")
    public void clickToEditLoyaltyCard(String bonusCard) {
        Kraken.getWebDriver().findElement(By.xpath("//div[contains(text(), '" + bonusCard +
               "')]/ancestor::div[@class='loyalty-program__content']/following-sibling::div[@class='loyalty-program__edit']"))
               .click();
    }

    @Override
    @Step("Открыть старницу чекаута")
    public String pageUrl() {
        return "checkout/edit";
    }
}