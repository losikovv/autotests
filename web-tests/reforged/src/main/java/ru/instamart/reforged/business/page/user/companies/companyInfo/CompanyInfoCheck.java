package ru.instamart.reforged.business.page.user.companies.companyInfo;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static org.testng.Assert.assertTrue;
import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CompanyInfoCheck extends Check, CompanyInfoElement {

    @Step("Проверяем, что информация о компании видна")
    default void checkCompanyInfoIsVisible() {
        waitAction().shouldBeVisible(companyInfo);
    }

    @Step("Проверяем что в блоке информации о компании присутствует текст '{expectedText}'")
    default void checkCompanyInfoContainsText(final String expectedText) {
        assertTrue(companyInfo.getText().contains(expectedText),
                String.format("В блоке информации о компании '%s' не найден текст '%s'", companyInfo.getText(), expectedText));
    }

    @Step("Проверяем, что отображается информация о персональном менеджере")
    default void checkManagerInfoDisplayed() {
        waitAction().shouldBeVisible(managerInfo);
    }

    @Step("Проверяем что в блоке информации о персональном менеджере компании присутствует текст '{expectedText}'")
    default void checkManagerInfoContainsText(final String expectedText) {
        assertTrue(managerInfo.getText().contains(expectedText),
                String.format("В блоке информации о персональном менеджере компании '%s' не найден текст '%s'", managerInfo.getText(), expectedText));
    }
}
