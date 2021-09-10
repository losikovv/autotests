package ru.instamart.reforged.stf.page.checkout;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.stf.block.helpdesk.HelpDesk;
import ru.instamart.reforged.stf.frame.checkout.subsections.*;
import ru.instamart.reforged.stf.frame.checkout.subsections.promocode_modal.EditPromoСode;
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
    EditPromoСode editPromoCode = new EditPromoСode();
    ReplacementPolicyStep replacementPolicy = new ReplacementPolicyStep();
    SlotStep slot = new SlotStep();
    PaymentStep payment = new PaymentStep();
    EditRequisites requisitesModal = new EditRequisites();
    EditPaymentCard editPaymentCardModal = new EditPaymentCard();
    EditLoyaltyPromoCode editLoyaltyPromoCodeModal = new EditLoyaltyPromoCode();
    HelpDesk helpDesk = new HelpDesk();

    Button submitFromCheckoutSidebar = new Button(By.xpath("//aside[@class='checkout-sidebar']//button[@data-qa='checkout_order_button']"));

    Button addPromoCode = new Button(By.xpath("//button[@data-qa='checkout_apply_coupon_code_button']"));
    Button deletePromoCode = new Button(By.xpath("//button[@data-qa='checkout_delete_coupon_code_button']"));
    ElementCollection addLoyaltyCard = new ElementCollection(By.xpath("//div[@class='loyalty-program__name']"));
    Element editLoyaltyCard = new Element(By.xpath(""));

    Element minimizedDeliveryOptionStep = new Element(By.xpath("//div[@class='panel-header__text' and text() = 'Способ получения']//ancestor::div[@class='checkout-panel']"),
            "панель свернутого шага 'Способ получения'");
}
