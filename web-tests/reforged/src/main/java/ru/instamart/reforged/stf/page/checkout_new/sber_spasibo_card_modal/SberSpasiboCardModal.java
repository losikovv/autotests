package ru.instamart.reforged.stf.page.checkout_new.sber_spasibo_card_modal;

import io.qameta.allure.Step;
import ru.instamart.kraken.data.PaymentCardData;

public class SberSpasiboCardModal implements SberSpasiboCardCheck {

    @Step("Заполняем данные карты {0}")
    public void fillCardData(final PaymentCardData data) {
        fillCardNumber(data.getCardNumber());
        fillExpMonth(data.getExpiryMonth() + data.getExpiryYear());
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

    @Step("Заполняем поле количества бонусов к списанию: '{data}'")
    public void fillBonusesAmount(String data) {
        bonusesAmount.fill(data);
    }

    @Step("Нажимаем кнопку 'Списать'")
    public void clickWithdraw() {
        withdraw.click();
    }
}
