package ru.instamart.reforged.admin.page.companies;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CompaniesCheck extends Check, CompaniesElement {

    @Step("Проверяем, что отобразился список компаний")
    default void checkCompaniesVisible() {
        waitAction().shouldBeVisible(companiesTable);
    }

    @Step("Проверяем, что количество найденных записей соответствует ожидаемому: '{expectedRowsCount}'")
    default void checkCompaniesCount(final int expectedRowsCount) {
        Assert.assertEquals(companies.getRowsCount(), expectedRowsCount, "Количество записей в таблице отличается от ожидаемого");
    }

    @Step("Проверяем что Название найденной компании: '{expectedCompanyName}'")
    default void checkFirstCompanyName(final String expectedCompanyName) {
        Assert.assertEquals(companies.getCompanyName(0), expectedCompanyName, "Название компании отличается от ожидаемого");
    }

    @Step("Проверяем что ИНН найденной компании: '{expectedINN}'")
    default void checkFirstCompanyINN(final String expectedINN) {
        Assert.assertEquals(companies.getInn(0), expectedINN, "ИНН компании отличается от ожидаемого");
    }

    @Step("Проверяем что Статус найденной компании: '{expectedStatus}'")
    default void checkFirstCompanyStatus(final String expectedStatus) {
        Assert.assertEquals(companies.getStatus(0).getLabel(), expectedStatus, "Статус компании отличается от ожидаемого");
    }

    @Step("Проверяем что Тип оплаты найденной компании: '{expectedPaymentType}'")
    default void checkFirstCompanyPaymentType(final String expectedPaymentType) {
        Assert.assertEquals(companies.getPaymentType(0).getLabel(), expectedPaymentType, "Тип оплаты компании отличается от ожидаемого");
    }
}
