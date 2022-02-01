package ru.instamart.reforged.business.frame.checkout.subsections;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.kraken.data.PaymentCardData;
import ru.instamart.reforged.core.component.Input;

public class EditPaymentCard implements CommonFrameButtons {

    private final Input cardNumber = new Input(By.xpath("//input[@data-cp='cardNumber']"), "Поле 'Номер карты'");
    private final Input expMonth = new Input(By.xpath("//input[@data-cp='expDateMonth']"), "Срок действия карты, поле 'Месяц'");
    private final Input expYear = new Input(By.xpath("//input[@data-cp='expDateYear']"), "Срок действия карты, поле 'Год'");
    private final Input cvv = new Input(By.xpath("//input[@data-cp='cvv']"), "Поле 'CVV'");
    private final Input holderName = new Input(By.xpath("//input[@data-cp='name']"), "Поле 'Имя владельца'");

    @Step("Заполняем данные карты {0}")
    public void fillCardData(final PaymentCardData data) {
        fillCardNumber(data.getCardNumber());
        fillExpMonth(data.getExpiryMonth());
        fillExpYear(data.getExpiryYear());
        fillCvv(data.getCvvNumber());
        fillHolderName(data.getCardholderName());
    }

    @Step("Заполняем поле 'Номер карты'")
    public void fillCardNumber(String data) {
        cardNumber.fill(data);
    }

    @Step("Заполняем поле 'Месяц'")
    public void fillExpMonth(String data) {
        expMonth.fill(data);
    }

    @Step("Заполняем поле 'Год'")
    public void fillExpYear(String data) {
        expYear.fill(data);
    }

    @Step("Заполняем поле 'CVV'")
    public void fillCvv(String data) {
        cvv.fill(data);
    }

    @Step("Заполняем поле 'Имя Держателя'")
    public void fillHolderName(String data) {
        holderName.fill(data);
    }
}
