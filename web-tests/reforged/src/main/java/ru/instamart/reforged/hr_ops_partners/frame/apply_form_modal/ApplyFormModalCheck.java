package ru.instamart.reforged.hr_ops_partners.frame.apply_form_modal;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface ApplyFormModalCheck extends ApplyFormModalElement, Check {

    @Step("Проверяем, что окно отклика отображается")
    default void checkModalVisible() {
        Kraken.waitAction().shouldBeVisible(modalTitle);
        modalTitle.should().animationFinished();
    }

    @Step("Проверяем, что окно отклика не отображается")
    default void checkModalNotVisible() {
        modalTitle.should().invisible();
    }

    @Step("Проверяем, что список регионов отображается")
    default void checkRegionsListVisible() {
        Kraken.waitAction().shouldBeVisible(regionsList);
    }

    @Step("Проверяем, что отображается плейсхолдер пустого списка регионов")
    default void checkEmptyRegionsListVisible() {
        Kraken.waitAction().shouldBeVisible(emptyListPlaceholder);
    }

    @Step("Проверяем, что в списке отображается '{expectedCount}' регион(а)")
    default void checkRegionsCountInList(final int expectedCount) {
        Assert.assertEquals(regionsList.elementCount(), expectedCount, "Текущее количество регионов в списке отличается от ожидаемого");
    }

    @Step("Проверяем, что названия регионов в списке содержат текст: '{expectedText}'")
    default void checkFoundedRegionNameContainsText(final String expectedText) {
        regionsList.getTextFromAllElements().forEach(name -> krakenAssert.assertTrue(
                name.contains(expectedText), "Название региона в списке: '" + name + "' не содержит ожидаемый текст: '" + expectedText + "'"));
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что отображается ошибка в поле ввода 'Имя'")
    default void checkNameInputErrorVisible() {
        Kraken.waitAction().shouldBeVisible(nameInputError);
    }

    @Step("Проверяем, что текст ошибки в поле ввода 'Имя' соответствует ожидаемому: '{expectedErrorText}'")
    default void checkNameInputErrorText(final String expectedErrorText) {
        Assert.assertEquals(nameInputError.getText(), expectedErrorText, "Текст ошибки в поле ввода 'Имя' отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается ошибка в поле ввода 'Фамилия'")
    default void checkSurnameInputErrorVisible() {
        Kraken.waitAction().shouldBeVisible(surnameInputError);
    }

    @Step("Проверяем, что текст ошибки в поле ввода 'Фамилия' соответствует ожидаемому: '{expectedErrorText}'")
    default void checkSurnameInputErrorText(final String expectedErrorText) {
        Assert.assertEquals(surnameInputError.getText(), expectedErrorText, "Текст ошибки в поле ввода 'Фамилия' отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается ошибка в поле ввода 'Город'")
    default void checkRegionInputErrorVisible() {
        Kraken.waitAction().shouldBeVisible(regionInputError);
    }

    @Step("Проверяем, что текст ошибки в поле ввода 'Город' соответствует ожидаемому: '{expectedErrorText}'")
    default void checkRegionInputErrorText(final String expectedErrorText) {
        Assert.assertEquals(regionInputError.getText(), expectedErrorText, "Текст ошибки в поле ввода 'Город' отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается ошибка в поле ввода 'Телефон'")
    default void checkPhoneInputErrorVisible() {
        Kraken.waitAction().shouldBeVisible(phoneInputError);
    }

    @Step("Проверяем, что текст ошибки в поле ввода 'Телефон' соответствует ожидаемому: '{expectedErrorText}'")
    default void checkNamePhoneErrorText(final String expectedErrorText) {
        Assert.assertEquals(phoneInputError.getText(), expectedErrorText, "Текст ошибки в поле ввода 'Телефон' отличается от ожидаемого");
    }
}
