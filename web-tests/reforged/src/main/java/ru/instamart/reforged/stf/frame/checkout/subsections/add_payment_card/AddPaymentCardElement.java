package ru.instamart.reforged.stf.frame.checkout.subsections.add_payment_card;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface AddPaymentCardElement {

    Input cardNumber = new Input(By.xpath("//input[@data-cp='cardNumber']"), "Поле ввода номера карты");
    Input expMonth = new Input(By.xpath("//input[@data-cp='expDateMonth']"), "Поле ввода месяца эксплуатации карты");
    Input expYear = new Input(By.xpath("//input[@data-cp='expDateYear']"), "Поле ввода года эксплуатации карты");
    Input cvv = new Input(By.xpath("//input[@data-cp='cvv']"), "Поле ввода cvv");
    Input holderName = new Input(By.xpath("//input[@data-cp='name']"), "Поле ввода имени владельца карты");
    Element cardError = new Element(ByKraken.xpathExpression("//div[text()='%s']"), "сообщение об ошибке при добавлении карты");
}
