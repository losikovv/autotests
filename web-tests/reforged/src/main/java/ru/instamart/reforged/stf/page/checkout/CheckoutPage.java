package ru.instamart.reforged.stf.page.checkout;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.stf.block.helpdesk.HelpDesk;
import ru.instamart.reforged.stf.frame.checkout.subsections.add_payment_card.AddPaymentCard;
import ru.instamart.reforged.stf.frame.checkout.subsections.edit_payment_card.EditPaymentCard;
import ru.instamart.reforged.stf.frame.checkout.subsections.edit_phone_number.EditPhoneNumber;
import ru.instamart.reforged.stf.frame.checkout.subsections.EditRequisites;
import ru.instamart.reforged.stf.frame.checkout.subsections.create_company.AddCompany;
import ru.instamart.reforged.stf.frame.checkout.subsections.loyaltycard_modal.EditLoyaltyCard;
import ru.instamart.reforged.stf.frame.checkout.subsections.promocode_modal.EditPromoCode;
import ru.instamart.reforged.stf.frame.checkout.subsections.retailer_card.EditRetailerCard;
import ru.instamart.reforged.stf.page.StfPage;
import ru.instamart.reforged.stf.page.checkout.fourthStep.SlotStep;
import ru.instamart.reforged.stf.page.checkout.fifthStep.edit_company.EditCompany;
import ru.instamart.reforged.stf.page.checkout.firstStep.DeliveryOptionStep;
import ru.instamart.reforged.stf.page.checkout.fifthStep.PaymentStep;
import ru.instamart.reforged.stf.page.checkout.secondStep.ContactsStep;
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

    public EditCompany editCompany() {
        return editCompany;
    }

    public EditPromoCode interactEditPromoCodeModal() {
        return editPromoCode;
    }

    public EditRequisites interactEditRequisitesModal() {
        return requisitesModal;
    }

    public AddPaymentCard interactAddPaymentCardModal() {
        return addPaymentCardModal;
    }

    public EditPaymentCard interactEditPaymentCardModal() {
        return editPaymentCardModal;
    }

    public EditLoyaltyCard interactEditLoyaltyCardModal() {
        return editLoyaltyCardModal;
    }

    public EditRetailerCard interactEditRetailerCardModal() {
        return editRetailerCardModal;
    }

    public HelpDesk interactHelpDesk() {
        return helpDesk;
    }

    @Step("Нажать Оформить заказ в сайдбаре")
    public void clickToSubmitFromSidebar() {
        submitFromCheckoutSidebar.hoverAndClick();
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

    @Step("Нажать добавить карту ретейлера {0}")
    public void clickToAddRetailerCard() {
        addRetailerCard.click();
    }

    @Step("Нажать выбрать карту лояльности {0}")
    public void clickToSelectLoyaltyCard(String data) {
        selectLoyaltyCard.clickOnElementWithText(data);
    }

    @Step("Редактировать карту лояльности {0}")
    public void clickToEditLoyaltyCard(final String bonusCard) {
        editLoyaltyCard.click(bonusCard);
    }

    @Step("Получаем значение стоимости товаров в корзине")
    public double getOrderAmount() {
        return StringUtil.stringToDouble(orderAmount.getText());
    }

    @Step("Получаем значение количества позиций в корзине")
    public int getPositionsCount() {
        return StringUtil.extractNumberFromString(positionsCount.getText());
    }

    @Override
    @Step("Открыть старницу чекаута")
    public String pageUrl() {
        return "checkout/edit";
    }
}