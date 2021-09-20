package ru.instamart.reforged.stf.page.checkout;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.stf.block.helpdesk.HelpDesk;
import ru.instamart.reforged.stf.frame.checkout.subsections.*;
import ru.instamart.reforged.stf.frame.checkout.subsections.loyaltycard_modal.EditLoyaltyCard;
import ru.instamart.reforged.stf.frame.checkout.subsections.promocode_modal.EditPromoCode;
import ru.instamart.reforged.stf.page.checkout.fifthStep.SlotStep;
import ru.instamart.reforged.stf.page.checkout.fourthStep.PaymentStep;
import ru.instamart.reforged.stf.page.checkout.secondStep.ContactsStep;
import ru.instamart.reforged.stf.page.checkout.firstStep.DeliveryOptionStep;
import ru.instamart.reforged.stf.page.checkout.thirdStep.ReplacementPolicyStep;

public interface CheckoutElement {

    DeliveryOptionStep deliveryOptionsStep = new DeliveryOptionStep();
    AddCompany addCompanyModal = new AddCompany();
    ContactsStep contactsStep = new ContactsStep();
    EditPhoneNumber editPhoneNumberModal = new EditPhoneNumber();
    EditPromoCode editPromoCode = new EditPromoCode();
    ReplacementPolicyStep replacementPolicy = new ReplacementPolicyStep();
    SlotStep slot = new SlotStep();
    PaymentStep payment = new PaymentStep();
    EditRequisites requisitesModal = new EditRequisites();
    EditPaymentCard editPaymentCardModal = new EditPaymentCard();
    EditLoyaltyCard EDIT_LOYALTY_CARD_MODAL = new EditLoyaltyCard();
    HelpDesk helpDesk = new HelpDesk();

    Button submitFromCheckoutSidebar = new Button(By.xpath("//aside[@class='checkout-sidebar']//button[@data-qa='checkout_order_button']"), "Кнопка оформления заказа в сайдбаре корзины");

    Button addPromoCode = new Button(By.xpath("//button[@data-qa='checkout_apply_coupon_code_button']"), "Кнопка добавления промокода в корзине");
    Button deletePromoCode = new Button(By.xpath("//button[@data-qa='checkout_delete_coupon_code_button']"), "Кнопка удаления промокода из корзины");
    ElementCollection addLoyaltyCard = new ElementCollection(By.xpath("//div[@class='loyalty-program__name']"), "Коллекция элементов кнопок добавления карт лояльности");
    ElementCollection selectLoyaltyCard = new ElementCollection(By.xpath("//div[@class='loyalty-program__name']"), "Коллекция имен карт лояльности");
    Element editLoyaltyCardModal = new Element(By.id("LoyaltyProgramSelector"), "Модальное окно добавления карт лояльности");

    Element minimizedDeliveryOptionStep = new Element(By.xpath("//div[@class='panel-header__text' and text() = 'Способ получения']//ancestor::div[@class='checkout-panel']"),
            "панель свернутого шага 'Способ получения'");

    Element loyaltyCardLoader = new Element(By.xpath("//div[@class='loyalty-programs__list']//div[contains(@class, 'Loading')]"), "Лоадер в списке карт лояльности");
}
