package ru.instamart.reforged.business.page.checkout;

import org.openqa.selenium.By;
import ru.instamart.reforged.business.frame.checkout.subsections.EditPaymentCard;
import ru.instamart.reforged.business.page.checkout.contactsStep.ContactsStep;
import ru.instamart.reforged.business.page.checkout.deliveryStep.DeliveryOptionStep;
import ru.instamart.reforged.business.page.checkout.paymentStep.PaymentStep;
import ru.instamart.reforged.business.page.checkout.replacementPolicyStep.ReplacementPolicyStep;
import ru.instamart.reforged.business.page.checkout.slotsStep.SlotStep;
import ru.instamart.reforged.core.component.Button;

public interface CheckoutElement {

    DeliveryOptionStep deliveryOptionsStep = new DeliveryOptionStep();
    ContactsStep contactsStep = new ContactsStep();
    ReplacementPolicyStep replacementPolicy = new ReplacementPolicyStep();
    SlotStep slot = new SlotStep();
    PaymentStep payment = new PaymentStep();
    EditPaymentCard editPaymentCardModal = new EditPaymentCard();

    Button submitCheckoutInLastStep = new Button(By.xpath("//div[@class='checkout-panels']//button[@data-qa='checkout_order_button']"), "Кнопка 'Оформить заказ' в последнем шаге чекаута");
    Button submitFromCheckoutSidebar = new Button(By.xpath("//aside[@class='checkout-sidebar']//button[@data-qa='checkout_order_button']"), "Кнопка 'Оформить заказ' в сайдбаре корзины");
}
