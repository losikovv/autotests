package ru.instamart.reforged.admin.page.user_companies.edit_documents;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface EditUserCompanyCheck extends Check, EditUserCompanyElement {

    @Step("Проверяем, что отображается поле ввода 'Юридическое лицо покупателя'")
    default void checkJuridicalNameClicable() {
        Kraken.waitAction().shouldBeClickable(juridicalName);
        ThreadUtil.simplyAwait(1);
    }

    @Step("Проверяем, что значение в поле ввода 'ИНН': '{expectedText}'")
    default void checkInn(final String expectedText) {
        Assert.assertEquals(inn.getValue(), expectedText, "Текст в поле 'ИНН' отличается от ожидаемого");
    }

    @Step("Проверяем, что значение в поле ввода 'Юридическое лицо покупателя': '{expectedText}'")
    default void checkJuridicalName(final String expectedText) {
        Assert.assertEquals(juridicalName.getValue(), expectedText, "Текст в поле 'Юридическое лицо покупателя' отличается от ожидаемого");
    }

    @Step("Проверяем, что значение в поле ввода 'Юридический адрес покупателя': '{expectedText}'")
    default void checkJuridicalAddress(final String expectedText) {
        Assert.assertEquals(juridicalAddress.getValue(), expectedText, "Текст в поле 'Юридический адрес покупателя' отличается от ожидаемого");
    }

    @Step("Проверяем, что значение в поле ввода 'КПП': '{expectedText}'")
    default void checkKpp(final String expectedText) {
        Assert.assertEquals(kpp.getValue(), expectedText, "Текст в поле 'КПП' отличается от ожидаемого");
    }

    @Step("Проверяем, что значение в поле ввода 'Юридическое лицо грузополучателя': '{expectedText}'")
    default void checkConsigneeJuridicalName(final String expectedText) {
        Assert.assertEquals(consigneeName.getValue(), expectedText, "Текст в поле 'Юридическое лицо грузополучателя' отличается от ожидаемого");
    }

    @Step("Проверяем, что значение в поле ввода 'Юридический адрес грузополучателя': '{expectedText}'")
    default void checkConsigneeJuridicalAddress(final String expectedText) {
        Assert.assertEquals(consigneeAddress.getValue(), expectedText, "Текст в поле 'Юридический адрес грузополучателя' отличается от ожидаемого");
    }

    @Step("Проверяем, что значение в поле ввода 'КПП грузополучателя': '{expectedText}'")
    default void checkConsigneeKpp(final String expectedText) {
        Assert.assertEquals(consigneeKpp.getValue(), expectedText, "Текст в поле 'КПП грузополучателя' отличается от ожидаемого");
    }

    @Step("Проверяем, что значение в поле ввода 'БИК': '{expectedText}'")
    default void checkBik(final String expectedText) {
        Assert.assertEquals(bik.getValue(), expectedText, "Текст в поле 'БИК' отличается от ожидаемого");
    }

    @Step("Проверяем, что значение в поле ввода 'Корреспондентский счет': '{expectedText}'")
    default void checkCorrespondentAccount(final String expectedText) {
        Assert.assertEquals(correspondentAccount.getValue(), expectedText, "Текст в поле 'Корреспондентский счёт' отличается от ожидаемого");
    }

    @Step("Проверяем, что значение в поле ввода 'Расчётный счёт': '{expectedText}'")
    default void checkOperationalAccount(final String expectedText) {
        Assert.assertEquals(operationalAccount.getValue(), expectedText, "Текст в поле 'Расчётный счёт' отличается от ожидаемого");
    }

    @Step("Проверяем, что значение в поле ввода 'Банк>': '{expectedText}'")
    default void checkBank(final String expectedText) {
        Assert.assertEquals(bank.getValue(), expectedText, "Текст в поле 'Банк>' отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается ошибка в поле ввода 'Юридическое лицо покупателя'")
    default void checkJuridicalNameErrorVisible() {
        Kraken.waitAction().shouldBeVisible(juridicalNameError);
    }

    @Step("Проверяем, что ошибка в поле ввода 'Юридическое лицо покупателя' не отображается")
    default void checkJuridicalNameErrorNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(juridicalNameError);
    }

    @Step("Проверяем, что текст ошибки в поле ввода 'Юридическое лицо покупателя': '{expectedErrorText}'")
    default void checkJuridicalNameErrorText(final String expectedErrorText) {
        Assert.assertEquals(juridicalNameError.getText(), expectedErrorText, "Текст ошибки в поле 'Юридическое лицо покупателя' отличается от ожидаемого");
    }

}

