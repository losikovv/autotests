package ru.instamart.reforged.next.page.user.companies;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ru.instamart.reforged.core.Kraken.waitAction;

public interface UserCompaniesCheck extends Check, UserCompaniesElement {

    @Step("Проверяем, что отображается кнопка 'Личный профиль'")
    default void checkProfileButtonVisible() {
        waitAction().shouldBeVisible(profileEdit);
    }

    @Step("Проверяем, что отображается кнопка 'Добавить компанию'")
    default void checkAddCompanyButtonVisible() {
        waitAction().shouldBeVisible(addCompany);
    }

    @Step("Проверяем, что список компаний пуст")
    default void checkCompaniesListIsEmpty() {
        waitAction().shouldBeVisible(emptyCompaniesListPlaceholder);
    }

    @Step("Проверяем, что список компаний не пуст")
    default void checkCompaniesListIsNotEmpty() {
        waitAction().shouldNotBeVisible(emptyCompaniesListPlaceholder);
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
