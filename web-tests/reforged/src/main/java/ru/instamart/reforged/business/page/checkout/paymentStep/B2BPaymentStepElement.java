package ru.instamart.reforged.business.page.checkout.paymentStep;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.*;

public interface B2BPaymentStepElement {

    Button byCardToCourier = new Button(By.xpath("//button[@data-qa='checkout_form_payment_method_lifepay']"), "Кнопка 'Картой при получении'");
    Button byBusinessAccount = new Button(By.xpath("//button[@data-qa='checkout_form_payment_method_sber_bank_invoice']"), "Кнопка 'По счёту'");
    Button byCardOnline = new Button(By.xpath("//button[@data-qa='checkout_form_payment_method_sber_gateway']"), "Кнопка 'Картой онлайн'");

    // Вкладка "По счёту"
    Input inn = new Input(By.xpath("//input[@name='requisites.inn']"), "Поле ввода 'ИНН'");
    Input juridicalName = new Input(By.xpath("//input[@name='requisites.name']"), "Поле ввода 'Юридическое лицо'");
    Input juridicalAddress = new Input(By.xpath("//input[@name='requisites.address']"), "Поле ввода 'Юридический адрес'");
    Input kpp = new Input(By.xpath("//input[@name='requisites.kpp']"), "Поле ввода 'КПП'");
    Checkbox needInvoice = new Checkbox(By.xpath("//div[contains(@class, 'checkout-panel--active')]//input[@type='checkbox'][./parent::label[contains(.,'ТОРГ-12')]]"), "Чекбокс 'Необходима накладная ТОРГ-12'");
    Input account = new Input(By.xpath("//input[@name='requisites.operatingAccount']"), "Поле ввода 'Расчётный счёт'");
    Input bik = new Input(By.xpath("//input[@name='requisites.bik']"), "Поле ввода 'БИК'");
    Input bank = new Input(By.xpath("//input[@name='requisites.bank']"), "Поле ввода 'Название банка'");
    Input correspondentAccount = new Input(By.xpath("//input[@name='requisites.correspondentAccount']"), "Поле ввода 'Корреспондентский счёт'");
    Button save = new Button(By.xpath("//div[contains(@class, 'checkout-panel--active')]//div[@class='button-block']//button"), "Кнопка 'Сохранить'");
    Button addNewRequisites = new Button(By.xpath("//span[contains(text(),'Добавить реквизиты')]"), "Кнопка 'Добавить реквизиты'");
    ElementCollection requisites = new ElementCollection(By.xpath("//button[contains(.,'ИНН')]"), "Добавленные реквизиты");
    ElementCollection requisiteEditButtons = new ElementCollection(By.xpath("//button[contains(.,'ИНН')]/following-sibling::button"), "Кнопки 'Изменить' реквизитов");

    //Вкладка "Картой онлайн"
    Button addNewPaymentCard = new Button(By.xpath("//span[contains(text(),'Добавить новую карту')]"), "Кнопка 'Добавить новую карту'");
    RadioButton getClosingDocuments = new RadioButton(By.xpath("//input[@id='requisites-switch']"), "Радио-баттон 'Получить закрывающие документы'");

    ElementCollection paymentCards = new ElementCollection(By.xpath("//button[contains(.,'карта')]"), "Добавленные карты");
    ElementCollection paymentCardEditButtons = new ElementCollection(By.xpath("//button[contains(.,'карта')]/following-sibling::button"), "Кнопки 'Изменить' добавленных карт");
}
