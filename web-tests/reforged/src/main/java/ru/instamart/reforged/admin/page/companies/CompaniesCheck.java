package ru.instamart.reforged.admin.page.companies;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static org.testng.Assert.assertNotNull;

public interface CompaniesCheck extends Check, CompaniesElement {

    @Step("Проверяем что компания '{0}' отображается в таблице")
    default void checkCompanyNameExist(final String companyName) {
        assertNotNull(companyTable.getLine(companyName), "Компания не отображается в таблице");
    }

    @Step("Проверяем что ИНН '{0}' отображается в таблице")
    default void checkInnExist(final String inn) {
        assertNotNull(companyTable.getLine(inn), "ИНН не отображается в таблице");
    }
}
