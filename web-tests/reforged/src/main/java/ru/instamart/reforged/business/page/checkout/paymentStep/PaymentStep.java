package ru.instamart.reforged.business.page.checkout.paymentStep;

import io.qameta.allure.Step;

public class PaymentStep implements PaymentStepCheck {

    @Step("Выбираем метод оплаты 'Картой при получении'")
    public void clickToByCardToCourier() {
        byCardToCourier.click();
    }

    @Step("Выбираем метод оплаты 'По счёту'")
    public void clickToByBusinessAccount() {
        byBusinessAccount.click();
    }

    @Step("Выбираем метод оплаты 'Картой онлайн'")
    public void clickToByCardOnline() {
        byCardOnline.click();
    }

    @Step("Заполняем поле 'Юридическое лицо'")
    public void fillRequisitesName(String data) {
        juridicalName.fill(data);
    }

    @Step("Заполняем поле 'Юридический адрес'")
    public void fillRequisitesAddress(String data) {
        juridicalAddress.fill(data);
    }

    @Step("Заполняем поле 'КПП'")
    public void fillRequisitesKPP(String data) {
        kpp.fill(data);
    }

    @Step("Отмечаем чекбокс 'Необходима накладная ТОРГ-12'")
    public void checkNeedInvoice() {
        needInvoice.check();
    }

    @Step("Переключаем радио-баттон 'Получить закрывающие документы'")
    public void switchGetClosingDocuments() {
        getClosingDocuments.set();
    }

    @Step("Снимаем чекбокс 'Необходима накладная ТОРГ-12'")
    public void uncheckNeedInvoice() {
        needInvoice.uncheck();
    }

    @Step("Нажимаем 'Сохранить'")
    public void clickToSave() {
        save.click();
    }

    @Step("Нажимаем 'Добавить новую карту'")
    public void clickToAddNewPaymentCard() {
        addNewPaymentCard.click();
    }

    @Step("Нажимаем 'Добавить реквизиты'")
    public void clickToAddRequisites() {
        addNewRequisites.click();
    }

    @Step("Получаем название первой карты из списка")
    public String getFirstCardName() {
        return paymentCards.getElementText(0);
    }

    @Step("Получаем первые добавленные реквизиты из списка")
    public String getFirstRequisites() {
        return requisites.getElementText(0);
    }
}
