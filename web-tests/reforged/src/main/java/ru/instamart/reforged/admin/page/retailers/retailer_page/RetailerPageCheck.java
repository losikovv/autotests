package ru.instamart.reforged.admin.page.retailers.retailer_page;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface RetailerPageCheck extends Check, RetailerPageElements {

    @Step("Проверяем, что кнопка 'Добавить магазин' отображается")
    default void checkAddNewStoreButtonVisible() {
        waitAction().shouldBeVisible(addNewStoreButton);
    }

    @Step("Проверяем, что все магазины ретейлера недоступны")
    default void checkAllStoresDisable() {
        Assert.assertEquals(storesInTable.elementCount(), inactiveStoresInTable.elementCount());
    }

    @Step("Проверяем, что хотя бы один магазин доступен")
    default void checkSomeStoreEnable() {
        Assert.assertTrue(activeStoresInTable.elementCount() > 0);
    }

    @Step("Проверяем, что наименование ритейлера соответствует ожидаемому: {expectedName}")
    default void checkRetailerName(final String expectedName) {
        Assert.assertEquals(retailerName.getText(), expectedName, "Наименование ритейлера отличается от ожидаемого");
    }

    @Step("Проверяем, что наименование ритейлера в url соответствует ожидаемому: {expectedUrlName}")
    default void checkRetailerUrlName(final String expectedUrlName) {
        Assert.assertEquals(retailerUrl.getText(), expectedUrlName, "Наименование ритейлера в url отличается от ожидаемого");
    }

    @Step("Проверяем, что Глубина вложения категорий на главной соответствует ожидаемой: {expectedCategoriesDepth}")
    default void checkRetailerCategoriesDepth(final String expectedCategoriesDepth) {
        Assert.assertEquals(categoriesDepth.getText(), expectedCategoriesDepth, "Глубина вложения категорий на главной отличается от ожидаемой");
    }

    @Step("Проверяем, что Ключ в файле импорта соответствует ожидаемому: {expectedImportKey}")
    default void checkImportKey(final String expectedImportKey) {
        Assert.assertEquals(importKey.getText(), expectedImportKey, "Ключ в файле импорта отличается от ожидаемого");
    }

    @Step("Проверяем, что Юридическое имя соответствует ожидаемому: {expectedJuridicalName}")
    default void checkJuridicalName(final String expectedJuridicalName) {
        Assert.assertEquals(juridicalName.getText(), expectedJuridicalName, "Юридическое имя отличается от ожидаемого");
    }

    @Step("Проверяем, что ИНН соответствует ожидаемому: {expectedINN}")
    default void checkINN(final String expectedINN) {
        Assert.assertEquals(inn.getText(), expectedINN, "ИНН отличается от ожидаемого");
    }

    @Step("Проверяем, что телефон соответствует ожидаемому: {expectedPhone}")
    default void checkPhone(final String expectedPhone) {
        Assert.assertEquals(phone.getText(), expectedPhone, "Номер телефона отличается от ожидаемого");
    }

    @Step("Проверяем, что юридический адрес соответствует ожидаемому: {expectedAddress}")
    default void checkJuridicalAddress(final String expectedAddress) {
        Assert.assertEquals(juridicalAddress.getText(), expectedAddress, "Юридический адрес отличается от ожидаемого");
    }

    @Step("Проверяем, что тип договора соответствует ожидаемому: {expectedContractType}")
    default void checkContractType(final String expectedContractType) {
        Assert.assertEquals(contractType.getText(), expectedContractType, "Тип договора отличается от ожидаемого");
    }
}
