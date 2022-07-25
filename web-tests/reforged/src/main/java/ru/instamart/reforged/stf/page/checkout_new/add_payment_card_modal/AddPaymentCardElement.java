package ru.instamart.reforged.stf.page.checkout_new.add_payment_card_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface AddPaymentCardElement {

    Input cardNumber = new Input(By.xpath("//div[contains(@class, 'AddCardModal')]//input[@id='FormGroup_number']"), "Поле ввода номера карты");
    Input expPeriod = new Input(By.xpath("//div[contains(@class, 'AddCardModal')]//input[@id='FormGroup_expiryMMYY']"), "Поле ввода месяца/года эксплуатации карты");
    Input cvv = new Input(By.xpath("//div[contains(@class, 'AddCardModal')]//input[@id='FormGroup_cvc']"), "Поле ввода cvv");
    Input holderName = new Input(By.xpath("//div[contains(@class, 'AddCardModal')]//input[@id='FormGroup_name']"), "Поле ввода имени владельца карты");
    Button addCard = new Button(By.xpath("//div[contains(@class, 'AddCardModal')]//button"), "Кнопка 'Добавить'");
    Element cardInputError = new Element(ByKraken.xpathExpression("//div[contains(@class, 'AddCardModal')]//label[contains(@class,'description')][.='%s']"), "Сообщение об ошибке в поле ввода");
    Element cardModalError = new Element(By.xpath("//div[@class='checkout-form__error']"), "Сообщение об ошибке добавления карты");
}
