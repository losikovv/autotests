package ru.instamart.reforged.stf.frame.checkout.subsections;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.kraken.data.PaymentCardData;
import ru.instamart.reforged.core.component.Input;

public class EditPaymentCard implements CommonFrameButtons {

    private final Input cardNumber = new Input(By.xpath("//input[@data-cp='cardNumber']"), "empty");
    private final Input expMonth = new Input(By.xpath("//input[@data-cp='expDateMonth']"), "empty");
    private final Input expYear = new Input(By.xpath("//input[@data-cp='expDateYear']"), "empty");
    private final Input cvv = new Input(By.xpath("//input[@data-cp='cvv']"), "empty");
    private final Input holderName = new Input(By.xpath("//input[@data-cp='name']"), "empty");

    @Step("Заполнить данные карты {0}")
    public void fillCardData(final PaymentCardData data) {
        fillCardNumber(data.getCardNumber());
        fillExpMonth(data.getExpiryMonth());
        fillExpYear(data.getExpiryYear());
        fillCvv(data.getCvvNumber());
        fillHolderName(data.getCardholderName());
    }

    @Step("Заполнить поле Номер карты")
    public void fillCardNumber(String data) {
        cardNumber.fill(data);
    }

    @Step("Заполнить поле Месяц")
    public void fillExpMonth(String data) {
        expMonth.fill(data);
    }

    @Step("Заполнить поле Год")
    public void fillExpYear(String data) {
        expYear.fill(data);
    }

    @Step("Заполнить поле CVV")
    public void fillCvv(String data) {
        cvv.fill(data);
    }

    @Step("Заполнить поле Имя Держателя")
    public void fillHolderName(String data) {
        holderName.fill(data);
    }
}
