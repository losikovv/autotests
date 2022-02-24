package ru.instamart.reforged.stf.page.checkout;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.stf.block.helpdesk.HelpDesk;
import ru.instamart.reforged.stf.frame.checkout.subsections.*;
import ru.instamart.reforged.stf.frame.checkout.subsections.create_company.AddCompany;
import ru.instamart.reforged.stf.frame.checkout.subsections.edit_payment_card.EditPaymentCard;
import ru.instamart.reforged.stf.frame.checkout.subsections.edit_phone_number.EditPhoneNumber;
import ru.instamart.reforged.stf.frame.checkout.subsections.loyaltycard_modal.EditLoyaltyCard;
import ru.instamart.reforged.stf.frame.checkout.subsections.promocode_modal.EditPromoCode;
import ru.instamart.reforged.stf.frame.checkout.subsections.retailer_card.EditRetailerCard;
import ru.instamart.reforged.stf.page.checkout.fourthStep.SlotStep;
import ru.instamart.reforged.stf.page.checkout.fifthStep.edit_company.EditCompany;
import ru.instamart.reforged.stf.page.checkout.fifthStep.PaymentStep;
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
    EditLoyaltyCard editLoyaltyCardModal = new EditLoyaltyCard();
    EditRetailerCard editRetailerCardModal = new EditRetailerCard();
    HelpDesk helpDesk = new HelpDesk();
    EditCompany editCompany = new EditCompany();

    Button submitFromCheckoutSidebar = new Button(By.xpath("//aside[@class='checkout-sidebar']//button[@data-qa='checkout_order_button']"), "Кнопка оформления заказа в сайдбаре корзины");

    Button addRetailerCard = new Button(By.xpath("//div[@class='retailer-card__label']"),  "добавить карту ретейлера");

    Button addPromoCode = new Button(By.xpath("//button[@data-qa='checkout_apply_coupon_code_button']"), "Кнопка добавления промокода в корзине");
    Button deletePromoCode = new Button(By.xpath("//button[@data-qa='checkout_delete_coupon_code_button']"), "Кнопка удаления промокода из корзины");
    ElementCollection addLoyaltyCard = new ElementCollection(By.xpath("//div[@class='loyalty-program__name']"), "Коллекция элементов кнопок добавления карт лояльности");
    ElementCollection selectLoyaltyCard = new ElementCollection(By.xpath("//div[@class='loyalty-program__name']"), "Коллекция имен карт лояльности");

    Element minimizedDeliveryOptionStep = new Element(By.xpath("//div[@class='panel-header__text' and text() = 'Способ получения']//ancestor::div[@class='checkout-panel']"),
            "панель свернутого шага 'Способ получения'");

    Element loyaltyCardLoader = new Element(By.xpath("//div[@class='loyalty-programs__list']//div[contains(@class, 'Loading')]"), "Лоадер в списке карт лояльности");
    Element editLoyaltyCard = new Element(ByKraken.xpath("//div[contains(text(), '%s')]/ancestor::div[@class='loyalty-program__content']/following-sibling::div[@class='loyalty-program__edit']"),
            "редактирование карты лояльности");
    Element activeLoyaltyCard = new Element(ByKraken.xpath("//div[@class='loyalty-program__name' and text()='%s']/ancestor::div[@class='loyalty-program loyalty-program--active']"),
            "карта лояльности активна");

    Element checkoutLoader = new Element(By.xpath("//div[contains(@class, 'Loading')]"), "Лоадер чекаута");

    Element sideBarSpinner = new Element(By.xpath("//div[@class='checkout-summary']//span[contains(text(), 'Загрузка...')]"), "Лоадер сайдбара");

}
