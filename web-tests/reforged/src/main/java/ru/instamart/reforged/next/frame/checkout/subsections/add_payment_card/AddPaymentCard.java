package ru.instamart.reforged.next.frame.checkout.subsections.add_payment_card;

import io.qameta.allure.Step;
import ru.instamart.kraken.data.PaymentCardData;
import ru.instamart.reforged.next.frame.checkout.subsections.CommonFrameButtons;

public class AddPaymentCard implements CommonFrameButtons, AddPaymentCardCheck {

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
