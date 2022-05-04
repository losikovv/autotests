package ru.instamart.reforged.admin.page.retailers.add_new_retailer;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface RetailerAddCheck extends Check, RetailerAddElements {

    @Step("Проверяем, что инпут ввода имени отображается")
    default void checkNameInputVisible() {
        waitAction().shouldBeVisible(nameInput);
    }

    @Step("Проверяем, что отображается ошибка ввода в поле 'Наименование'")
    default void checkNameInputErrorVisible() {
        waitAction().shouldBeVisible(nameInputError);
    }

    @Step("Проверяем, что отображается ошибка ввода в поле 'Короткий URL'")
    default void checkURLInputErrorVisible() {
        waitAction().shouldBeVisible(urlInputError);
    }

    @Step("Проверяем, что отображается ошибка ввода в поле 'Ключ в файле импорта'")
    default void checkImportKeyInputErrorVisible() {
        waitAction().shouldBeVisible(importKeyInputError);
    }

    @Step("Проверяем, что ошибка ввода в поле 'Ключ в файле импорта' не отображается")
    default void checkImportKeyInputErrorNotVisible() {
        waitAction().shouldNotBeVisible(importKeyInputError);
    }

    @Step("Проверяем, что текст в поле 'Наименование' соответствует ожидаемому: {expectedText}")
    default void checkNameInputText(final String expectedText) {
        Assert.assertEquals(nameInput.getValue(), expectedText, "Текст в поле 'Наименование' отличается от ожидаемого");
    }

    @Step("Проверяем, что текст в поле 'Короткий URL' соответствует ожидаемому: {expectedText}")
    default void checkURLInputText(final String expectedText) {
        Assert.assertEquals(urlInput.getValue(), expectedText, "Текст в поле 'Короткий URL' отличается от ожидаемого");
    }

    @Step("Проверяем, что текст в поле 'Ключ в файле импорта' соответствует ожидаемому: {expectedText}")
    default void checkImportKeyInputText(final String expectedText) {
        Assert.assertEquals(importKeyInput.getValue(), expectedText, "Текст в поле 'Ключ в файле импорта' отличается от ожидаемого");
    }

    @Step("Проверяем, что текст ошибки ввода в поле 'Наименование' соответствует ожидаемому: {expectedErrorText}")
    default void checkNameInputErrorText(final String expectedErrorText) {
        Assert.assertEquals(nameInputError.getText(), expectedErrorText, "Текст ошибки ввода в поле 'Наименование' отличается от ожидаемого");
    }

    @Step("Проверяем, что текст ошибки ввода в поле 'Короткий URL' соответствует ожидаемому: {expectedErrorText}")
    default void checkURLInputErrorText(final String expectedErrorText) {
        Assert.assertEquals(urlInputError.getText(), expectedErrorText, "Текст ошибки ввода в поле 'Короткий URL' отличается от ожидаемого");
    }

    @Step("Проверяем, что текст ошибки ввода в поле 'Файле импорта' соответствует ожидаемому: {expectedErrorText}")
    default void checkImportKeyInputErrorText(final String expectedErrorText) {
        Assert.assertEquals(importKeyInputError.getText(), expectedErrorText, "Текст ошибки ввода в поле 'Файл импорта' отличается от ожидаемого");
    }
}
