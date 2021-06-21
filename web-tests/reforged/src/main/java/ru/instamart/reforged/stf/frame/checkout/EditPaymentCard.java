package ru.instamart.reforged.stf.frame.checkout;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;

public class EditPaymentCard implements CommonFrameButtons {

    private final Input cardNumber = new Input(By.xpath("//input[@data-cp='cardNumber']"));
    private final Input expMonth = new Input(By.xpath("//input[@data-cp='expDateMonth']"));
    private final Input expYear = new Input(By.xpath("//input[@data-cp='expDateYear']"));
    private final Input cvv = new Input(By.xpath("//input[@data-cp='cvv']"));
    private final Input holderName = new Input(By.xpath("//input[@data-cp='name']"));

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
