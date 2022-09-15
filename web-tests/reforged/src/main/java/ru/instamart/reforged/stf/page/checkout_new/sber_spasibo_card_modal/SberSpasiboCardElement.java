package ru.instamart.reforged.stf.page.checkout_new.sber_spasibo_card_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface SberSpasiboCardElement {

    Element modal = new Element(By.xpath("//div[contains(@class, 'SberSpasiboPaymentToolModal')]"), "Модальное окно добавления карты со сбер спасибо");
    Input cardNumber = new Input(By.xpath("//div[contains(@class, 'AddCardModal')]//input[@id='FormGroup_number']"), "Поле ввода номера карты");
    Input expPeriod = new Input(By.xpath("//div[contains(@class, 'AddCardModal')]//input[@id='FormGroup_expiryMMYY']"), "Поле ввода месяца/года эксплуатации карты");
    Input cvv = new Input(By.xpath("//div[contains(@class, 'AddCardModal')]//input[@id='FormGroup_cvc']"), "Поле ввода cvv");
    Input holderName = new Input(By.xpath("//div[contains(@class, 'AddCardModal')]//input[@id='FormGroup_name']"), "Поле ввода имени владельца карты");
    Input bonusesAmount = new Input(By.xpath("//div[contains(@class, 'AddCardModal')]//input[@id='FormGroup_spasiboAmount']"), "Поле ввода количества бонусов спасибо");
    Button withdraw = new Button(By.xpath("//div[contains(@class, 'SberSpasiboPaymentToolModal')]//button[@type='submit']"), "Кнопка 'Списать'");
    Element cardInputError = new Element(ByKraken.xpathExpression("//div[contains(@class, 'AddCardModal')]//label[contains(@class,'description')][.='%s']"), "Сообщение об ошибке в поле ввода");
    Element cardModalError = new Element(By.xpath("//div[@class='checkout-form__error']"), "Сообщение об ошибке добавления карты");
    Element bonusesField = new Element(By.xpath("//div[contains(@class,'bonusesField')]"), "Поле с количеством бонусов");
}