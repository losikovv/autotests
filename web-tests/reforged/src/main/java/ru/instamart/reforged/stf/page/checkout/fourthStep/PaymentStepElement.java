package ru.instamart.reforged.stf.page.checkout.fourthStep;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Checkbox;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Input;

public interface PaymentStepElement {

    Button byCardToCourier = new Button(By.xpath("//button[@data-qa='checkout_form_payment_method_lifepay']"));
    Button byBusinessAccount = new Button(By.xpath("//button[@data-qa='checkout_form_payment_method_sber_bank_invoice']"));
    Button byCardOnline = new Button(By.xpath("//button[@data-qa='checkout_form_payment_method_cloud_payments_gateway']"));
    //byBankInvoice
    Input inn = new Input(By.xpath("//input[@name='requisites.inn']"));
    Input requisitesName = new Input(By.xpath("//input[@name='requisites.name']"));
    Input requisitesAddress = new Input(By.xpath("//input[@name='requisites.address']"));
    Checkbox needInvoice = new Checkbox(By.xpath("//div[contains(@class, 'checkout-panel--active')]//input[@type='checkbox']"));
    Button save = new Button(By.xpath("//div[contains(@class, 'checkout-panel--active')]//div[@class='button-block']//button"));
    Button addNewRequisites = new Button(By.xpath("//span[contains(text(),'Добавить реквизиты')]"));
    Button change = new Button(By.xpath("//div[contains(@class, 'checkout-panel--active')]//button[contains(text(),'Изменить')]"));//общий локтатор для карты и бизнеса

    Button addNewPaymentCard = new Button(By.xpath("//span[contains(text(),'Добавить новую карту')]"));
    Button submitFromCheckoutColumn = new Button(By.xpath("//div[@class='checkout-column']//button[@data-qa='checkout_order_button']"));
    ElementCollection selectPaymentCard = new ElementCollection(By.xpath("//div[contains(@class, 'payment_tools')]//div"));
    ElementCollection changeFirstPaymentCard = new ElementCollection(By.xpath("//div[contains(@class, 'checkout-panel--active')]//button[contains(text(),'Изменить')]"));
}