package ru.instamart.reforged.business.page.home;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import java.util.List;
import java.util.stream.Collectors;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface B2BHomeCheck extends Check, B2BHomeElement {

    @Step("Проверяем, что открылся лендинг бизнес-витрины")
    default void checkLandingVisible() {
        waitAction().shouldBeVisible(b2bLandingStores);
    }

    @Step("Проверяем, что отображается кнопка 'Заказать обратный звонок'")
    default void checkButtonGetCallBackDisplayed() {
        waitAction().shouldBeVisible(getCallback);
    }

    @Step("Проверяем, что отображается кнопка 'Создать профиль компании'")
    default void checkButtonAddCompanyDisplayed() {
        waitAction().shouldBeVisible(addCompany);
    }

    @Step("Проверяем, что отображается кнопка 'Доставка'")
    default void checkButtonDeliveryDisplayed() {
        waitAction().shouldBeVisible(delivery);
    }

    @Step("Проверяем, что отображается кнопка 'Преимущества'")
    default void checkButtonBenefitsDisplayed() {
        waitAction().shouldBeVisible(benefits);
    }

    @Step("Проверяем, что отображается кнопка 'Как заказать'")
    default void checkButtonHowToOrderDisplayed() {
        waitAction().shouldBeVisible(howToOrder);
    }

    @Step("Проверяем, что отображается кнопка 'Города'")
    default void checkButtonCitiesDisplayed() {
        waitAction().shouldBeVisible(cities);
    }

    @Step("Проверяем, что отображается кнопка 'Ответы на вопросы'")
    default void checkButtonFAQDisplayed() {
        waitAction().shouldBeVisible(FAQ);
    }

    @Step("Проверяем, что отображается кнопка 'Сертификаты'")
    default void checkButtonCertificatesDisplayed() {
        waitAction().shouldBeVisible(certificates);
    }

    @Step("Проверяем соответствие первых 3-х ритейлеров в блоке 'До 20% от чека'")
    default void checkRetailersListOnPage(List<String> expectedRetailerNames) {
        List<String> topThreeRetailers = retailerNames.getTextFromAllElements().stream().limit(3).collect(Collectors.toList());
        Assert.assertEquals(topThreeRetailers, expectedRetailerNames, "Список ритейлеров в блоке 'До 20% от чека' отличается от ожидаемого");
    }
}
