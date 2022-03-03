package ru.instamart.reforged.business.page.checkout;

import org.openqa.selenium.By;
import ru.instamart.reforged.business.frame.checkout.subsections.B2BEditPaymentCard;
import ru.instamart.reforged.business.page.checkout.contactsStep.B2BContactsStep;
import ru.instamart.reforged.business.page.checkout.deliveryStep.B2BDeliveryOptionStep;
import ru.instamart.reforged.business.page.checkout.paymentStep.B2BPaymentStep;
import ru.instamart.reforged.business.page.checkout.replacementPolicyStep.B2BReplacementPolicyStep;
import ru.instamart.reforged.business.page.checkout.slotsStep.B2BSlotStep;
import ru.instamart.reforged.core.component.Button;

public interface B2BCheckoutElement {

    B2BDeliveryOptionStep deliveryOptionsStep = new B2BDeliveryOptionStep();
    B2BContactsStep contactsStep = new B2BContactsStep();
    B2BReplacementPolicyStep replacementPolicy = new B2BReplacementPolicyStep();
    B2BSlotStep slot = new B2BSlotStep();
    B2BPaymentStep payment = new B2BPaymentStep();
    B2BEditPaymentCard editPaymentCardModal = new B2BEditPaymentCard();

    Button submitCheckoutInLastStep = new Button(By.xpath("//div[@class='checkout-panels']//button[@data-qa='checkout_order_button']"), "Кнопка 'Оформить заказ' в последнем шаге чекаута");
    Button submitFromCheckoutSidebar = new Button(By.xpath("//aside[@class='checkout-sidebar']//button[@data-qa='checkout_order_button']"), "Кнопка 'Оформить заказ' в сайдбаре корзины");
}
