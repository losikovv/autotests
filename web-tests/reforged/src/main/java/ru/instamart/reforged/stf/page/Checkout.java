package ru.instamart.reforged.stf.page;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.stf.frame.checkout.*;
import ru.instamart.reforged.stf.page.checkout.*;

@Slf4j
public final class Checkout implements StfPage {

    private static final String PAGE = "checkout/edit";

    private final Button submitFromCheckoutSidebar = new Button(By.xpath("//aside[@class='checkout-sidebar']//button[@data-qa='checkout_order_button']"));

    private final Button addPromoCode = new Button(By.xpath("//button[@data-qa='checkout_apply_coupon_code_button']"));
    private final Button deletePromoCode = new Button(By.xpath("//button[@data-qa='checkout_delete_coupon_code_button']"));
    private final ElementCollection addLoyaltyCard = new ElementCollection(By.xpath("//div[@class='loyalty-program__name']"));
    private final Element editLoyaltyCard = new Element(By.xpath(""));

    private final DeliveryOptionsStep deliveryOptionsStep = new DeliveryOptionsStep();
    private final AddCompany addCompanyModal = new AddCompany();
    private final ContactsStep contactsStep = new ContactsStep();
    private final EditPhoneNumber editPhoneNumberModal = new EditPhoneNumber();
    private final ReplacementPolicyStep replacementPolicy = new ReplacementPolicyStep();
    private final SlotStep slot = new SlotStep();
    private final PaymentStep payment = new PaymentStep();
    private final EditRequisites requisitesModal = new EditRequisites();
    private final EditPaymentCard editPaymentCardModal = new EditPaymentCard();
    private final EditLoyaltyPromoCode editLoyaltyPromoCodeModal = new EditLoyaltyPromoCode();

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

    @Override
    @Step("Открыть старницу чекаута")
    public String pageUrl() {
        return PAGE;
    }

    @Step("Нажать Оформить заказ в сайдбаре")
    public void clickToSubmitFromSidebar(){
        submitFromCheckoutSidebar.click();
    }

    @Step("Нажать Применить промокод")
    public void clickToAddPromoCode(){
        addPromoCode.click();
    }

    @Step("Нажать Добавить карту лояльности {0}")
    public void clickToAddLoyaltyCard(String data){
        addLoyaltyCard.clickOnElementWithText(data);
    }
}