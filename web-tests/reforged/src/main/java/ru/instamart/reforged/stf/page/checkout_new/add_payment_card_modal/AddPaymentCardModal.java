package ru.instamart.reforged.stf.page.checkout_new.add_payment_card_modal;

import io.qameta.allure.Step;
import ru.instamart.kraken.data.PaymentCardData;

public class AddPaymentCardModal implements AddPaymentCardCheck {

    @Step("Заполняем данные карты {0}")
    public void fillCardData(final PaymentCardData data) {
        fillCardNumber(data.getCardNumber());
        fillExpMonth(data.getExpiryMonth());
        fillExpYear(data.getExpiryYear());
        fillCvv(data.getCvvNumber());
        fillHolderName(data.getCardholderName());
    }

    @Step("Заполняем поле 'Номер карты': '{data}'")
    public void fillCardNumber(String data) {
        cardNumber.fill(data);
    }

    @Step("Заполняем 'Месяц': '{data}'")
    public void fillExpMonth(String data) {
        expPeriod.fill(data);
    }

    @Step("Заполняем 'Год': '{data}'")
    public void fillExpYear(String data) {
        expPeriod.fill(data);
    }

    @Step("Заполняем поле 'CVV': '{data}'")
    public void fillCvv(String data) {
        cvv.fill(data);
    }

    @Step("Заполняем поле 'Имя Держателя': '{data}'")
    public void fillHolderName(String data) {
        holderName.fill(data);
    }

    @Step("Нажимаем кнопку 'Добавить'")
    public void clickAdd() {
        addCard.click();
    }
}
