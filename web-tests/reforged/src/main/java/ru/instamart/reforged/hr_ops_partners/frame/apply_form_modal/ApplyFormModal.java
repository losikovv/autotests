package ru.instamart.reforged.hr_ops_partners.frame.apply_form_modal;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.ThreadUtil;

public class ApplyFormModal implements ApplyFormModalCheck {

    @Step("Нажимаем 'Х' (закрыть)")
    public void close() {
        close.click();
    }

    @Step("Кликаем в поле 'Имя'")
    public void clickName() {
        ThreadUtil.simplyAwait(1);
        nameInputLabel.click();
    }

    @Step("Вводим в поле 'Имя' текст: '{inputText}'")
    public void fillName(final String inputText) {
        nameInput.fill(inputText);
    }

    @Step("Кликаем в поле 'Фамилия'")
    public void clickSurname() {
        ThreadUtil.simplyAwait(1);
        surnameInputLabel.click();
    }

    @Step("Вводим в поле 'Фамилия' текст: '{inputText}'")
    public void fillSurname(final String inputText) {
        surnameInput.fill(inputText);
    }

    @Step("Кликаем в поле 'Город'")
    public void clickRegion() {
        ThreadUtil.simplyAwait(1);
        regionInputLabel.click();
    }

    @Step("Вводим в поле 'Город' текст: '{inputText}'")
    public void fillRegion(final String inputText) {
        regionInput.fill(inputText);
    }

    @Step("Выбираем из списка: {regionName}")
    public void selectRegionByName(final String regionName) {
        regionsList.clickOnElementWithText(regionName);
    }

    @Step("Кликаем в поле 'Телефон'")
    public void clickPhone() {
        ThreadUtil.simplyAwait(1);
        phoneInputLabel.click();
    }

    @Step("Вводим в поле 'Номер телефона' текст: '{inputText}'")
    public void fillPhone(final String inputText) {
        phoneInputLabel.click();
        ThreadUtil.simplyAwait(1);
        phoneInput.fillField(inputText);
    }

    @Step("Нажимаем на ссылку 'согласие на обработку персональных данных'")
    public void clickPersonalDataConfirmInfo() {
        personalDataConfirmInfo.click();
    }

    @Step("Отмечаем чекбокс 'Согласие на обработку персональных данных'")
    public void checkPersonalDataCheckbox() {
        personalDataConfirmCheckbox.check();
    }

    @Step("Нажимаем на кнопку 'Получиь код в СМС'")
    public void clickSendSMS() {
        sendSMS.click();
    }
}
