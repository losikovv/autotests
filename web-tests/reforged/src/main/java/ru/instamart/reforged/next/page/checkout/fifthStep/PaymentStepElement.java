package ru.instamart.reforged.next.page.checkout.fifthStep;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.*;

public interface PaymentStepElement {

    Button byCardToCourier = new Button(By.xpath("//button[@data-qa='checkout_form_payment_method_lifepay']"), "empty");
    Button byBusinessAccount = new Button(By.xpath("//button[@data-qa='checkout_form_payment_method_sber_bank_invoice']"), "empty");
    Button byCardOnline = new Button(By.xpath("//button[@data-qa='checkout_form_payment_method_sber_gateway']"), "empty");
    //byBankInvoice
    Input inn = new Input(By.xpath("//input[@name='requisites.inn']"), "empty");
    Input requisitesName = new Input(By.xpath("//input[@name='requisites.name']"), "empty");
    Input requisitesAddress = new Input(By.xpath("//input[@name='requisites.address']"), "empty");
    Checkbox needInvoice = new Checkbox(By.xpath("//div[contains(@class, 'checkout-panel--active')]//input[@type='checkbox']"), "empty");
    Button save = new Button(By.xpath("//div[contains(@class, 'checkout-panel--active')]//div[@class='button-block']//button"), "empty");
    Button addNewRequisites = new Button(By.xpath("//span[contains(text(),'Добавить реквизиты')]"), "empty");
    Button change = new Button(By.xpath("//div[contains(@class, 'checkout-panel--active')]//button[contains(text(),'Изменить')]"), "empty");//общий локтатор для карты и бизнеса

    Button addNewPaymentCard = new Button(By.xpath("//span[contains(text(),'Добавить новую карту')]"), "empty");
    Button submitFromCheckoutColumn = new Button(By.xpath("//div[@class='checkout-column']//button[@data-qa='checkout_order_button']"), "Кнопка 'Оформить заказ'");
    ElementCollection selectPaymentCard = new ElementCollection(By.xpath("//input[contains(@name,'payment_tool_id')]/parent::div"), "Коллекция элементов привязанных карт");
    ElementCollection changeFirstPaymentCard = new ElementCollection(By.xpath("//div[contains(@class, 'checkout-panel--active')]//button[contains(text(),'Изменить')]"), "empty");
    Element changeButtonOnCardWithNumber = new Element(ByKraken.xpathExpression("//div[contains(text(),'%s')]/ancestor::button/following-sibling::button[text()='Изменить']"), "Кнопка изменить у определенной карты");
}