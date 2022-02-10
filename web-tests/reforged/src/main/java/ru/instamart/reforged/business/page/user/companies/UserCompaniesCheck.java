package ru.instamart.reforged.business.page.user.companies;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public interface UserCompaniesCheck extends Check, UserCompaniesElement {

    @Step("Проверяем, что список компаний пуст")
    default void checkCompaniesListIsEmpty() {
        Kraken.waitAction().shouldBeVisible(emptyCompaniesListPlaceholder);
    }

    @Step("Проверяем, что список компаний не пуст")
    default void checkCompaniesListIsNotEmpty() {
        Kraken.waitAction().shouldNotBeVisible(emptyCompaniesListPlaceholder);
    }

    @Step("Проверяем, что количество компаний в списке: {expectedCount}")
    default void checkCompaniesCount(final int expectedCount) {
        assertEquals(companiesList.elementCount(), expectedCount,
                String.format("Количество компаний в списке: %s отличается от ожидаемого: %s", companiesList.elementCount(), expectedCount));
    }

    @Step("Проверяем, что список компаний содержит компанию '{companyName}'")
    default void checkCompaniesListContains(final String companyName) {
        assertTrue(companiesList.getTextFromAllElements().stream().anyMatch(name -> name.equals(companyName)),
                String.format("В списке компаний: '%s' не найдена компания '%s'", companiesList.getTextFromAllElements(), companyName));
    }
}
