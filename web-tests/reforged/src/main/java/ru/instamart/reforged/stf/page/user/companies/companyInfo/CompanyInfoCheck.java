package ru.instamart.reforged.stf.page.user.companies.companyInfo;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static org.testng.Assert.assertTrue;
import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CompanyInfoCheck extends Check, CompanyInfoElement {

    @Step("Проверяем, что не отображается спиннер загрузки информации")
    default void checkLoadingSpinnerNotVisible(){
        waitAction().shouldNotBeVisible(loadingSpinner);
    }

    @Step("Проверяем, что информация о компании видна")
    default void checkCompanyInfoIsVisible() {
        waitAction().shouldBeVisible(companyInfo);
    }

    @Step("Проверяем, что виден список пользователей")
    default void checkCompanyUsersDisplayed() {
        waitAction().shouldBeVisible(companyUserInfo);
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

    @Step("Проверяем, что отображается всплывающая подсказка о времени обновления данных по счёту")
    default void checkPaymentAccountUpdateInfoDisplayed() {
        waitAction().shouldBeVisible(accountUpdateInfo);
    }

    @Step("Проверяем, что отображается всплывающая подсказка о возможной задержке обновления информации о счёте")
    default void checkPaymentAccountWarningDisplayed() {
        waitAction().shouldBeVisible(accountUpdateWarning);
    }

    @Step("Проверяем, что сумма на счёте содержит текст {expectedText}")
    default void checkPaymentAccountAmountContainsText(String expectedText) {
        assertTrue(paymentAccountAmount.getText().contains(expectedText),
                String.format("Отображаемая сумма на счёте: '%s' не содержит ожидаемый текст: '%s'", paymentAccountAmount.getText(), expectedText));
    }

    @Step("Проверяем, что отображается блок 'Код безопасности'")
    default void checkSecurityBlockDisplayed() {
        waitAction().shouldBeVisible(companySecurityInfo);
    }

    @Step("Проверяем, что не отображается блок 'Код безопасности'")
    default void checkSecurityBlockNotDisplayed() {
        waitAction().shouldNotBeVisible(companySecurityInfo);
    }
}
