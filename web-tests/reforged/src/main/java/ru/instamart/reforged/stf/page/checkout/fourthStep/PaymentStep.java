package ru.instamart.reforged.stf.page.checkout.fourthStep;

import io.qameta.allure.Step;

public class PaymentStep implements PaymentStepElement {

    @Step("Выбрать метод оплаты Картой при получении")
    public void clickToByCardToCourier() {
        byCardToCourier.click();
    }

    @Step("Выбрать метод оплаты По счету бизнеса")
    public void clickToByBusinessAccount() {
        byBusinessAccount.click();
    }

    @Step("Выбрать метод оплаты по карте онлайн")
    public void clickToByCardOnline() {
        byCardOnline.click();
    }

    //действия с элементами метода оплаты По счету бизнеса
    @Step("Заполнить поле Юридического лицо")
    public void fillRequisitesName(String data) {
        requisitesName.fill(data);
    }

    @Step("Заполнить поле Юридического лицо")
    public void fillRequisitesAddress(String data) {
        requisitesAddress.fill(data);
    }

    @Step("Выбрать чекбокс Необходима накладная ТОРГ-12")
    public void checkNeedInvoice(String data) {
        needInvoice.check();
    }

    @Step("Снять чекбокс Необходима накладная ТОРГ-12")
    public void uncheckNeedInvoice(String data) {
        needInvoice.uncheck();
    }

    @Step("Нажать Сохранить")
    public void clickToSave() {
        save.click();
    }

    @Step("Нажать Добавить реквизиты")
    public void clickToAddRequisites() {
        addNewRequisites.click();
    }

    @Step("Нажать Изменить")
    public void clickToChange() {
        change.click();
    }

    @Step("Нажать добавить новую карту")
    public void clickToAddNewPaymentCard() {
        addNewPaymentCard.click();
    }

    @Step("Нажать Оформить заказ в блоке заполнения чекаута")
    public void clickToSubmitFromCheckoutColumn() {
        submitFromCheckoutColumn.scrollTo();
        submitFromCheckoutColumn.click();
    }

    @Step("Выбрать карту {0}")
    public void clickToPaymentCard(String data) {
        selectPaymentCard.clickOnElementWithText(data);
    }

    @Step("Изменить карту {0}")
    public void clickToChangePaymentCard(String data) {
        changeFirstPaymentCard.clickOnElementWithText(data);
    }
}
